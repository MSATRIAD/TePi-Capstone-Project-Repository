const express = require('express');
const admin = require('firebase-admin');
const cors = require('cors');
const { Storage } = require('@google-cloud/storage');
const tf = require('@tensorflow/tfjs-node');
require('dotenv').config();
const path = require('path');

const app = express();
app.use(cors());
app.use(express.json());

const serviceAccount = require('./key.json');
const serviceAccount2 = require('./key2.json'); 

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://product-tepi.asia-southeast1.firebasedatabase.app/"
});

const db = admin.database();

const storage = new Storage({
  credentials: serviceAccount2,
});

let model; 

async function loadModel() {
  const BUCKET_NAME = 'model_ml_test';
  const MODEL_FILE = 'model.json';

  try {
    const file = storage.bucket(BUCKET_NAME).file(MODEL_FILE);
    const TEMP_MODEL_PATH = path.join(__dirname, MODEL_FILE);

    console.log('Downloading model from Cloud Storage...');
    await file.download({ destination: TEMP_MODEL_PATH }); 
    model = await tf.loadLayersModel(`file://${TEMP_MODEL_PATH}`);
    console.log('Model loaded successfully');
  } catch (error) {
    console.error('Error loading model from Cloud Storage:', error);
    throw new Error('Failed to load model');
  }
}

async function predictNutriScore(energyKcal, sugars, saturatedFat, salt, fruitsVegNuts, fiber, proteins) {
  if (!model) {
    throw new Error('Model is not loaded');
  }

  const inputData = [[energyKcal, sugars, saturatedFat, salt, fruitsVegNuts, fiber, proteins]];
  const inputTensor = tf.tensor2d(inputData);
  const prediction = model.predict(inputTensor);
  const predictedGrade = await prediction.data();
  return predictedGrade[0];
}

app.get('/products', async (req, res) => {
  try {
    const ref = db.ref('/');
    const snapshot = await ref.once('value');

    const products = [];
    snapshot.forEach((childSnapshot) => {
      const productId = childSnapshot.key;
      const productData = childSnapshot.val();

      products.push({
        id: productId,
        product_name: productData.product_name,
        grade: productData.nutriscore_grade,
      });
    });

    res.status(200).json(products);
  } catch (error) {
    console.error('Error fetching data:', error);
    res.status(500).json({ error: 'Failed to fetch data' });
  }
});

app.get('/products/:id', async (req, res) => {
  try {
    const productId = req.params.id;
    const ref = db.ref(`/${productId}`);
    const snapshot = await ref.once('value');
    const productData = snapshot.val();

    if (!productData) {
      return res.status(404).json({ error: 'Product not found' });
    }

    const response = {
      id: productId,
      ...productData,
    };

    res.status(200).json(response);
  } catch (error) {
    console.error('Error fetching product detail:', error);
    res.status(500).json({ error: 'Failed to fetch product detail' });
  }
});

app.post('/predict', async (req, res) => {
  const { energyKcal, sugars, saturatedFat, salt, fruitsVegNuts, fiber, proteins } = req.body;

  if (
    energyKcal === undefined ||
    sugars === undefined ||
    saturatedFat === undefined ||
    salt === undefined ||
    fruitsVegNuts === undefined ||
    fiber === undefined ||
    proteins === undefined
  ) {
    return res.status(400).json({ error: 'All input fields are required' });
  }

  try {
    const predictedGrade = await predictNutriScore(
      energyKcal,
      sugars,
      saturatedFat,
      salt,
      fruitsVegNuts,
      fiber,
      proteins
    );

    res.status(200).json({ grade: predictedGrade });
  } catch (error) {
    console.error('Prediction error:', error.message);
    if (error.message === 'Model is not loaded') {
      res.status(500).json({ error: 'Model not loaded. Please try again later.' });
    } else {
      res.status(500).json({ error: 'Failed to predict Nutri-Score' });
    }
  }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, async () => {
  console.log(`Server is running on port ${PORT}`);
  await loadModel(); 
});
