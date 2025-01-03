const axios = require('axios');
const { Storage } = require('@google-cloud/storage');
const { db, realtimeDb, ref, get, collection, doc, setDoc, getDocs, getDoc, deleteDoc, updateDoc } = require('../auth/firebase-config.js');

const storage = new Storage({
  projectId: 'tepi-teguk-pintar',
  keyFilename: './storage-key.json',
});

// Fungsi untuk mendapatkan produk
const getProducts = async (req, res) => {
  try {
    const productsRef = ref(realtimeDb, '/'); 
    const snapshot = await get(productsRef);

    if (!snapshot.exists()) {
      return res.status(404).json({ error: 'No products found' });
    }

    const products = [];
    snapshot.forEach((childSnapshot) => {
      const productId = childSnapshot.key;
      const productData = childSnapshot.val();
      products.push({
        id: productId,
        product_name: productData.product_name,
        nutriscore_grade: productData.nutriscore_grade,
      });
    });
    res.status(200).json(products);
  } catch (error) {
    console.error('Error fetching data:', error);
    res.status(500).json({ error: 'Failed to fetch data' });
  }
};

const getProductById = async (req, res) => {
  try {
    const productId = req.params.id;

    const productRef = ref(realtimeDb, `/${productId}`);
    const snapshot = await get(productRef);

    if (!snapshot.exists()) {
      return res.status(404).json({ error: 'Product not found' });
    }

    const productData = snapshot.val();

    res.status(200).json({ id: productId, ...productData });
  } catch (error) {
    console.error('Error fetching product detail:', error);
    res.status(500).json({ message: 'Failed to fetch product detail' });
  }
};

const predictNutriscore = async (req, res) => {
  const { energy_kcal, sugars, saturated_fat, salt, fruits_veg_nuts, fiber, proteins } = req.body;
  try {
    const response = await axios.post('https://model-backend-186840913924.asia-southeast2.run.app/predict', {
      energy_kcal,
      sugars,
      saturated_fat,
      salt,
      fruits_veg_nuts,
      fiber,
      proteins,
    });

    res.json({ predicted_grade: response.data.predicted_grade });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Failed to get prediction from backend' });
  }
};


const saveProduct = async (req, res) => {
  const { productData } = req.body;
  const userId = req.user.uid;

  if (!productData) {
    return res.status(400).json({ message: 'Product data is required' });
  }

  try {
    const userDocRef = doc(collection(db, 'users'), userId);

    const savedProductsCollection = collection(userDocRef, 'savedProducts');

    const newProductRef = doc(savedProductsCollection);

    await setDoc(newProductRef, productData);

    res.status(200).json({ message: 'Product saved successfully.' });
  } catch (error) {
    console.error('Error saving product:', error);
    res.status(500).json({ message: 'Error saving product' });
  }
};

const allSavedProducts = async (req, res) => {
  try{
  const userId = req.user.uid;

  const products = [];

  const userDocRef = doc(collection(db, 'users'), userId);
  const savedProductsCollection = collection(userDocRef, 'savedProducts');
  const savedProductsSnapshot = await getDocs(savedProductsCollection);

  savedProductsSnapshot.forEach((doc) => {
    products.push({
      id: doc.id, 
      product_name: doc.data().product_name, 
      nutriscore_grade: doc.data().nutriscore_grade,
    });
  });
  res.status(200).json(products);
  }
  catch(error){
    console.error('Error fetching data:', error);
    res.status(500).json({ message: 'Failed to fetch data' });
  }
}

const profile = async (req, res) => {
  try {
    const userId = req.user.uid;

    const userDocRef = doc(db, "users", userId);

    const userDocSnap = await getDoc(userDocRef);

    if (userDocSnap.exists()) {
      const userData = userDocSnap.data();

      res.status(200).json({
        profileImage: userData.profileImage || null,
        email: userData.email || null,
        displayName: userData.displayName || null,
      });
    } else {
      res.status(404).json({ message: "User not found" });
    }
  } catch (error) {
    console.error("Error Get Profile Data:", error);
    res.status(500).json({ message: "Error Get Profile Data" });
  }
};

const editProfile = async (req, res) => {
  try {
    if (!req.file) {
      res.status(400).send("No file uploaded.");
      return;
    }

    const userId = req.user.uid;
    const { displayName } = req.body;
    const imageFile = req.file;

    const bucket = storage.bucket("users-image-tepi");
    const fileName = `profile-${userId}.jpg`;
    const fileUpload = bucket.file(fileName);

    const stream = fileUpload.createWriteStream({
      metadata: {
        contentType: imageFile.mimetype,
      },
    });

    stream.on("error", (error) => {
      console.error("Error uploading file:", error);
      res.status(500).send("Internal Server Error");
    });

    stream.on("finish", async () => {
      try {
        const publicUrl = `https://storage.googleapis.com/${bucket.name}/${fileName}`;

        const userDocRef = doc(db, "users", userId);

        await updateDoc(userDocRef, {
          displayName: displayName || null,
          profileImage: publicUrl,
        });

        res.status(200).json({
          status: "Success",
          message: "Profile berhasil diperbarui",
          displayName,
          profileImage: publicUrl,
        });
      } catch (error) {
        console.error("Error updating Firestore:", error);
        res.status(500).send("Error updating profile data");
      }
    });

    stream.end(imageFile.buffer);
  } catch (error) {
    console.error("Error uploading file:", error);
    res.status(500).send("Internal Server Error");
  }
};

const getSavedProductById = async (req, res) => {
  const productId = req.params.id;
  const userId = req.user.uid; 

  try {
    if (!productId) {
      return res.status(400).json({ message: 'Product ID is required' });
    }

    const userDocRef = doc(collection(db, 'users'), userId);
    const savedProductsCollection = collection(userDocRef, 'savedProducts');
    const productDocRef = doc(savedProductsCollection, productId);

    const productSnapshot = await getDoc(productDocRef);

    if (!productSnapshot.exists()) {
      return res.status(404).json({ message: 'Product not found' });
    }

    const productData = productSnapshot.data();

    res.status(200).json({ id: productSnapshot.id, ...productData });
  } catch (error) {
    console.error('Error fetching saved product detail:', error);
    res.status(500).json({ message: 'Failed to fetch product detail' });
  }
};

const deleteSavedProductById = async (req, res) => {
  const productId = req.params.id;
  const userId = req.user.uid; 

  try {
    if (!productId) {
      return res.status(400).json({ message: 'Product ID is required' });
    }

    const userDocRef = doc(collection(db, 'users'), userId);
    const savedProductsCollection = collection(userDocRef, 'savedProducts');
    const productDocRef = doc(savedProductsCollection, productId);

    const productSnapshot = await getDoc(productDocRef);

    if (!productSnapshot.exists()) {
      return res.status(404).json({ message: 'Product not found' });
    }

    await deleteDoc(productDocRef);

    res.status(200).json({ message: 'Product deleted successfully.' });
  } catch (error) {
    console.error('Error deleting saved product:', error);
    res.status(500).json({ message: 'Failed to delete product' });
  }
};



module.exports = {
  getProducts,
  getProductById,
  predictNutriscore,
  saveProduct,
  allSavedProducts,
  profile,
  editProfile,
  getSavedProductById,
  deleteSavedProductById
};
