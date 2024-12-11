const {createUserWithEmailAndPassword, signInWithEmailAndPassword, signOut} = require("firebase/auth");
const { doc, setDoc, collection } = require('firebase/firestore');
const { db, auth } = require("../auth/firebase-config.js");
const { sendPasswordResetEmail } = require('firebase/auth');
const { Storage } = require('@google-cloud/storage');
const dotenv = require("dotenv");

dotenv.config();

const storage = new Storage({
    projectId: 'tepi-teguk-pintar',
    keyFilename: './storage-key.json',
});

// Handler signup
const register = async (req, res) => {
  const { name, email, password } = req.body;
  try {
    const userCredential = await createUserWithEmailAndPassword(auth, email, password);
    const userRecord = userCredential.user;
    const uniqueProfileImage = `profile-${userRecord.uid}.jpg`;

    const bucketName = 'users-image-tepi';
    const bucket = storage.bucket(bucketName);
    const defaultProfileImage = 'profile.jpg';

    const [fileExists] = await bucket.file(defaultProfileImage).exists();
    if (!fileExists) {
      return res.status(500).json({ message: 'Default profile image not found in storage' });
    }

    await bucket.file(defaultProfileImage).copy(bucket.file(uniqueProfileImage));
    const profileImageUrl = `https://storage.googleapis.com/${bucketName}/${uniqueProfileImage}`;

    const userData = {
      displayName: name,
      email: email,
      userId: userRecord.uid,
      profileImage: profileImageUrl,
    };

    const userDocRef = doc(collection(db, 'users'), userRecord.uid);
    await setDoc(userDocRef, userData);

    res.status(200).json({ error: false, message: 'Pengguna berhasil terdaftar', uid: userRecord.uid});
  } catch (error) {
    console.error('Detailed Registration Error:', {
      message: error.message,
      code: error.code,
      stack: error.stack,
    });
    res.status(400).json({
      error: true,
      message: error.message,
      code: error.code,
    });
  }
};

const login = async (req, res) => {
  const { email, password } = req.body;
  try {
      const userCredential = await signInWithEmailAndPassword(auth, email, password);
      const user = userCredential.user;

    
      const idToken = await user.getIdToken();

      res.json({
          error: false, 
          message: 'Berhasil Sign In', 
          uid: user.uid,
          userToken: idToken 
      });
  } catch (error) {
    console.error(error)
      res.status(404).json({ error: true, message: 'Error melakukan Sign In' });
  }
}

const resetPassword = async(req, res) => {
  const { email } = req.body;
  try {
      await sendPasswordResetEmail(auth, email);
      console.log('Link reset email telah dikirimkan ke:', email);
      return res.status(200).json({message: "Link Reset Password Telah Dikirim Ke Email"});
  } catch (error) {
      return res.status(200).json({message: "Error melakukan reset password"});
  }
}

const signOutUser = async(req, res) => {
  try {
      await signOut(auth);
      return res.status(200).json({message: "Sign out Berhasil"});
  } catch (error) {
      console.log('Error melakukan sign out:', error);
      return res.status(500).json({message: "Gagal Melakukan Sign Out"});
  }
}



module.exports = { register, login, signOutUser, resetPassword };