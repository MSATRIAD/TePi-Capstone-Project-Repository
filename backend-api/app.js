const express = require('express');
const admin = require('firebase-admin');
const firebase = require('firebase/app');
require('firebase/auth');
require('firebase/firestore');
const cors = require('cors');
const bodyParser = require('body-parser');
const axios = require('axios');
const { Storage } = require('@google-cloud/storage');
require('dotenv').config();
const path = require('path');
const { Firestore } = require('@google-cloud/firestore');
const multer = require('multer');
const { authUser } = require('./middleware');
const { createUserWithEmailAndPassword, signInWithEmailAndPassword, sendEmailVerification, confirmPasswordReset, sendPasswordResetEmail, getAuth } = require('firebase/auth')


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

const firestoreDb = new Firestore();

const storage = new Storage({
  credentials: serviceAccount2,
});

const auth = getAuth(app);

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
  const { email, password, username } = req.body;

  if (!email || !password || !username) {
    return res.status(400).json({
      email: "Email is required",
      password: "Password is required",
      username: "Username is required"
    });
  }

  try {
    const userCredential = await createUserWithEmailAndPassword(auth, email, password);
    const user = userCredential.user;
    const uid = user.uid;

    await user.sendEmailVerification();

    const uniqueProfileImage = `profile-${uid}.jpg`;

    const bucketName = "user-image-tepi";
    const bucket = storage.bucket(bucketName);
    const defaultProfileImage = "profile.jpg";

    const fileExists = await bucket.file(defaultProfileImage).exists();
    if (!fileExists) {
      return res.status(500).json({ message: 'Default profile image not found in storage' });
    }

    await bucket.file(defaultProfileImage).copy(bucket.file(uniqueProfileImage));
    const profileImageUrl = `https://storage.googleapis.com/${bucketName}/${uniqueProfileImage}`;

    await firestoreDb.collection('users').doc(uid).set({
      email,
      username,
      profileImageUrl,
      createdAt: Firestore.Timestamp.now(),
    });

    res.status(201).json({
      message: 'User registered successfully. Please check your email for verification.',
      user: {
        uid,
        email,
        username,
        profileImageUrl,
      },
    });

  } catch (error) {
    console.error(error);
    let errorMessage = 'An error occurred during registration.';
    let statusCode = 500;

    switch (error.code) {
      case 'auth/email-already-in-use':
        errorMessage = 'The email address is already in use by another account.';
        statusCode = 400;
        break;
      case 'auth/invalid-email':
        errorMessage = 'The email address is not valid.';
        statusCode = 400;
        break;
      case 'auth/operation-not-allowed':
        errorMessage = 'Email/password accounts are not enabled. Please contact the administrator.';
        statusCode = 500;
        break;
      case 'auth/weak-password':
        errorMessage = 'The password is too weak. Please use a stronger password.';
        statusCode = 400;
        break;
    }

    res.status(statusCode).json({
      message: errorMessage,
      error: error.code,
    });
  }
});

app.post('/login', async (req, res) => {
  const { email, password } = req.body;

  if (!email || !password) {
    return res.status(400).json({ message: 'Email dan password diperlukan.' });
  }

  try {
    const userCredential = await signInWithEmailAndPassword(email, password);
    const user = userCredential.user;

    const idToken = await user.getIdToken();

    return res.status(200).json({
      message: 'Login berhasil',
      uid: user.uid,
      email: user.email,
      token: idToken,
    });
    
  } catch (error) {
    let errorMessage = 'Terjadi kesalahan saat login.';
    switch (error.code) {
      case 'auth/invalid-email':
        errorMessage = 'Email tidak valid.';
        break;
      case 'auth/user-disabled':
        errorMessage = 'Akun pengguna dinonaktifkan.';
        break;
      case 'auth/user-not-found':
        errorMessage = 'Pengguna dengan email tersebut tidak ditemukan.';
        break;
      case 'auth/wrong-password':
        errorMessage = 'Password salah.';
        break;
      default:
        errorMessage = error.message;
    }

    return res.status(400).json({ message: errorMessage });
  }
});

app.post('/resetPass/code', async (req, res) => {
  const { email } = req.body;

  if (!email) {
    return res.status(400).json({ message: 'Email is required' });
  }

  try {
    await sendPasswordResetEmail(email);
    return res.status(200).json({ message: 'Password reset email sent.' });
  } catch (error) {
    console.error('Error sending reset email:', error);
    return res.status(400).json({ message: error.message });
  }
});


app.post('/resetPass/confirmResetPassword', async (req, res) => {
  const { code, newPassword } = req.body;

  if (!code || !newPassword) {
    return res.status(400).json({ message: 'Code and new password are required' });
  }

  try {
    await confirmPasswordReset(code, newPassword);
    return res.status(200).json({ message: 'Password has been reset successfully.' });
  } catch (error) {
    console.error('Error confirming reset password:', error);
    return res.status(400).json({ message: error.message });
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


app.get('/user/products/:id', authUser, async (req, res) => {
  const userId = req.user.uid;
  const productId = req.params.id;

  try {
    const productRef = FirestoreDb.collection('users').doc(userId).collection('savedProducts').doc(productId);
    const doc = await productRef.get();

    if (!doc.exists) {
      return res.status(404).json({ message: 'Product not found' });
    }

    const productData = doc.data();

    return res.status(200).json({ product: productData });
  } catch (error) {
    console.error('Error fetching product:', error);
    return res.status(500).json({ message: 'Error fetching product' });
  }
});

app.get('/userProfile', authUser, async (req, res) => {
  const userId = req.user.uid;

  try {
    const userRef = admin.firestore().collection('users').doc(userId);
    const doc = await userRef.get();

    if (!doc.exists) {
      return res.status(404).json({ message: 'User profile not found' });
    }

    const userData = doc.data();

    return res.status(200).json({
      email: userData.email,
      username: userData.username,
      profileImageUrl: userData.profileImageUrl,
    });
  } catch (error) {
    console.error('Error fetching user profile:', error);
    return res.status(500).json({ message: 'Error fetching user profile' });
  }
});

const upload = multer({
  storage: multer.memoryStorage(),
  limits: { fileSize: 5 * 1024 * 1024 },
});


app.post('/userProfile/updateProfilePic', authUser, upload.single('profileImage'), async (req, res) => {
  const userId = req.user.uid;

  if (!req.file) {
    return res.status(400).json({ message: 'No file uploaded' });
  }

  try {
    const bucketName = "user-image-tepi";
    const bucket = storage.bucket(bucketName);
    const fileName = `profile-${userId}.jpg`;
    const file = bucket.file(fileName);

    await file.save(req.file.buffer, {
      contentType: req.file.mimetype,
      public: true,
    });

    const fileUrl = `https://storage.googleapis.com/${bucketName}/${fileName}`;

    const userRef = admin.firestore().collection('users').doc(userId);
    await userRef.update({ profileImageUrl: fileUrl });

    return res.status(200).json({
      message: 'Profile picture updated successfully',
      profileImageUrl: fileUrl,
    });
  } catch (error) {
    console.error('Error uploading profile picture:', error);
    return res.status(500).json({ message: 'Error uploading profile picture' });
  }
});

app.get('/user/products', authUser, async (req, res) => {
  const uid = req.user.uid;
  try {
    const productsRef = firestoreDb.collection('users').doc(uid).collection('savedProducts');
    const snapshot = await productsRef.get();

    if (snapshot.empty) {
      return res.status(404).json({ message: 'No products found for this user.' });
    }

    const products = snapshot.docs.map(doc => {
      const data = doc.data();
      return {
        id: doc.id,
        product_name: data.product_name,
        nutriscore_grade: data.nutriscore_grade,
      };
    });

    res.status(200).json({ products });
  } catch (error) {
    console.error('Error retrieving products:', error);
    res.status(500).json({ message: 'An error occurred while retrieving products.', error });
  }
});



const PORT = process.env.PORT || 3000;
app.listen(PORT, async () => {
  console.log(`Server is running on port ${PORT}`);
});


module.exports = { admin };