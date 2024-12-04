const express = require('express');
const admin = require('firebase-admin');
const cors = require('cors');
const axios = require('axios');
require('dotenv').config();

const app = express();
app.use(cors());
app.use(express.json());

const serviceAccount = require('./key.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://product-tepi.asia-southeast1.firebasedatabase.app/"
});

const db = admin.database();

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

app.post('/nutriscore', async (req, res) => {
  const { energy_kcal, sugars, saturated_fat, salt, fruits_veg_nuts, fiber, proteins } = req.body;

  try {
    const response = await axios.post('http://0.0.0.0:8000/predict/', {
      energy_kcal,
      sugars,
      saturated_fat,
      salt,
      fruits_veg_nuts,
      fiber,
      proteins
    });

    res.json({ predicted_grade: response.data.predicted_grade });
  } catch (error) {
    res.status(500).json({ error: 'Failed to get prediction from backend' });
  }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, async () => {
  console.log(`Server is running on port ${PORT}`);
});