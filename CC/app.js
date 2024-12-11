const express = require('express');
const admin = require('firebase-admin');
const cors = require('cors');
require('dotenv').config();

const app = express();
app.use(cors());
app.use(express.json());

const serviceAccount = {};

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://product-tepi.asia-southeast1.firebasedatabase.app/"
});

const db = admin.database();

app.get('/api/data', async (req, res) => {
  try {
    const ref = db.ref('/');
    const snapshot = await ref.once('value');
    const fullData = snapshot.val();
    
    const filteredData = {};
    
    Object.keys(fullData).forEach(key => {
      const item = fullData[key];
      if (item.categories && item.product_name) {
        filteredData[key] = {
          categories: item.categories,
          product_name: item.product_name
        };
      }
    });
    
    res.status(200).json(filteredData);
  } catch (error) {
    console.error('Error fetching data:', error);
    res.status(500).json({ error: 'Failed to fetch data' });
  }
});


const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
