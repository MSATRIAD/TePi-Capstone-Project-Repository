const express = require('express');
const admin = require('firebase-admin');
const cors = require('cors');
const bodyParser = require('body-parser');
const axios = require('axios');
const { Storage } = require('@google-cloud/storage');
require('dotenv').config();
const multer = require('multer');
const { authUser } = require('./middleware');

const app = express();
app.use(cors());
app.use(express.json());
app.use(bodyParser.json());

const serviceAccount = require('./key.json');
const serviceAccount2 = require('./key2.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://product-tepi.asia-southeast1.firebasedatabase.app/"
});

const db = admin.database();

const firestoreDb = admin.firestore();

const storage = new Storage({
  projectId: 'testing-442012',
  keyFilename: './key2.json',
});


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
        nutriscore_grade: productData.nutriscore_grade,
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
    const response = await axios.post('https://your-model-service-2138847083.asia-southeast2.run.app/predict/', {
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

app.post('/register', async (req, res) => {
  try {
    const { name, email, password } = req.body;

    const userRecord = await admin.auth().createUser({
      displayName: name,
      email: email,
      password: password
    });

    const uniqueProfileImage = `profile-${userRecord.uid}.jpg`;

    const bucketName = 'user-image-tepi';
    const bucket = storage.bucket(bucketName);
    const defaultProfileImage = 'profile.jpg';

    const fileExists = await bucket.file(defaultProfileImage).exists();
    if (!fileExists) {
      return res.status(500).json({ message: 'Default profile image not found in storage' });
    }

    await bucket.file(defaultProfileImage).copy(bucket.file(uniqueProfileImage));
    const profileImageUrl = `https://storage.googleapis.com/${bucketName}/${uniqueProfileImage}`;

    const userData = {
      displayName: name,
      email: email,
      userId: userRecord.uid,
      profileImage: profileImageUrl
    };
    await firestoreDb.collection('users').doc(userRecord.uid).set(userData);

    res.status(200).json({ error: false, message: 'Pengguna berhasil terdaftar', userId: userRecord.uid });
  } catch (error) {
    console.error('Detailed Registration Error:', {
      message: error.message,
      code: error.code,
      stack: error.stack
    });
    res.status(400).json({
      error: true,
      message: error.message,
      code: error.code
    });
  };
});

app.post('/login', async (req, res) => {
  try {
    const { email, password } = req.body;

    const userRecord = await admin.auth().getUserByEmail(email);
    const token = await admin.auth().createCustomToken(userRecord.uid);

    const doc = await firestoreDb.collection('users').doc(userRecord.uid).get();
    const userData = doc.data();

    res.status(200).json({
      error: false,
      message: 'success',
      loginResult: {
        userId: userRecord.uid,
        name: userData.name,
        token: token
      }
    });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
});

app.post('/saveProduct', authUser, async (req, res) => {
  const { productData } = req.body;
  const userId = req.user.uid;

  if (!productData) {
    return res.status(400).json({ message: 'Product data are required' });
  }

  try {
    const userRef = firestoreDb.collection('users').doc(userId);
    const savedProductsRef = userRef.collection('savedProducts');

    await savedProductsRef.add(productData);

    return res.status(200).json({ message: 'Product saved successfully.' });
  } catch (error) {
    console.error('Error saving product:', error);
    return res.status(500).json({ message: 'Error saving product' });
  }
});

app.post('/logout', (req, res) => {
  try {
    res.status(200).json({ error: false, message: 'Pengguna berhasil logout' });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, async () => {
  console.log(`Server is running on port ${PORT}`);
});
