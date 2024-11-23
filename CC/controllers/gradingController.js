const { Firestore } = require("@google-cloud/firestore");
const { Storage } = require("@google-cloud/storage");
const multer = require("multer");

const firestore = new Firestore();
const storage = new Storage();
const bucketName = "your-bucket-name";
const bucket = storage.bucket(bucketName);

// Multer configuration for uploading files
const upload = multer({
  storage: multer.memoryStorage(),
});

exports.uploadOCRImage = async (req, res) => {
  const fileName = `ocr-images/${Date.now()}_${req.file.originalname}`;
  const file = bucket.file(fileName);

  try {
    await file.save(req.file.buffer, {
      contentType: req.file.mimetype,
    });
    const publicUrl = `https://storage.googleapis.com/${bucketName}/${fileName}`;
    res.json({ message: "Image uploaded successfully", url: publicUrl });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

exports.processProductDetails = async (req, res) => {
  const productDetails = req.body;
  try {
    const ref = await firestore.collection("products").add(productDetails);
    res.json({ message: "Product processed successfully", id: ref.id });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

exports.getAllProducts = async (req, res) => {
  try {
    const snapshot = await firestore.collection("products").get();
    const products = snapshot.docs.map((doc) => ({
      id: doc.id,
      ...doc.data(),
    }));
    res.json(products);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

exports.getProductById = async (req, res) => {
  const { id } = req.params;
  try {
    const productDoc = await firestore.collection("products").doc(id).get();
    if (!productDoc.exists) {
      return res.status(404).json({ error: "Product not found" });
    }
    res.json(productDoc.data());
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

exports.updateProductDetails = async (req, res) => {
  const { id } = req.params;
  const details = req.body;
  try {
    await firestore.collection("products").doc(id).set(details, { merge: true });
    res.json({ message: "Product details updated successfully" });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};
