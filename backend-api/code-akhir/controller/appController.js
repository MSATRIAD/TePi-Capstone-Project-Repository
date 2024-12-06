const axios = require('axios');
const { Storage } = require('@google-cloud/storage');
const { db, realtimeDb, auth, ref, get, collection, doc, setDoc } = require('../auth/firebase-config.js')
const storage = new Storage({
  projectId: 'testing-442012',
  keyFilename: '../key2.json',
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
    res.status(500).json({ error: 'Failed to fetch product detail' });
  }
};

const predictNutriscore = async (req, res) => {
  const { energy_kcal, sugars, saturated_fat, salt, fruits_veg_nuts, fiber, proteins } = req.body;
  try {
    const response = await axios.post('https://your-model-service-2138847083.asia-southeast2.run.app/predict/', {
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
    res.status(500).json({ error: 'Failed to get prediction from backend' });
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


module.exports = {
  getProducts,
  getProductById,
  predictNutriscore,
  saveProduct
};
