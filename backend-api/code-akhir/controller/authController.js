const {createUserWithEmailAndPassword, getAuth, signInWithEmailAndPassword, signOut, updateEmail, updatePassword} = require("firebase/auth");
const { doc, setDoc, getDocs, getDoc, updateDoc, serverTimestamp, collection } = require('firebase/firestore');
const { db, auth } = require("../auth/firebase-config.js");
const { sendPasswordResetEmail } = require('firebase/auth');
const { Storage } = require('@google-cloud/storage');
const dotenv = require("dotenv");

dotenv.config();

const storage = new Storage({
    projectId: 'testing-442012',
    keyFilename: '../key2.json',
});

// Handler signup
const register = async (req, res) => {
  const { name, email, password } = req.body;
  try {
    const userCredential = await createUserWithEmailAndPassword(auth, email, password);
    const userRecord = userCredential.user;
    const uniqueProfileImage = `profile-${userRecord.uid}.jpg`;

    const bucketName = 'user-image-tepi';
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


// Handler signin
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

/*
// Fungsi untuk mengunggah file gambar ke Google Cloud Storage
const uploadProfilePicture = async(req, res) => {
    try {
        if (!req.file) { res.status(400).send('No file uploaded.'); return; }

    const imageFile = req.file;
    const bucket = storage.bucket('xdetect-img-profile');
    const fileName = Date.now() + '_' + imageFile.originalname;
    const fileUpload = bucket.file(fileName);

    const stream = fileUpload.createWriteStream({
        metadata: {
        contentType: imageFile.mimetype,
        },
    });

    stream.on('error', (error) => {
        console.error('Error uploading file:', error);
        res.status(500).send('Internal Server Error');
    });

    stream.on('finish', async () => {
    // Dapatkan URL publik file yang diunggah
    const [url] = await fileUpload.getSignedUrl({
        action: 'read',
        expires: '01-01-2025', // Tanggal kadaluarsa URL publik
    });

    res.status(200).json({
        status: 'Success',
        message: 'Profile picture berhasil ditambahkan',
        fileName,
        url,
        });
    });

    stream.end(imageFile.buffer);
    } catch (error) {
        console.error('Error uploading file:', error);
        res.status(500).send('Internal Server Error');
    }
}

// Upload PP dengan UID
module.exports =  const uploadProfilePictureWithUID = async (req, res) => {
    try {
      if (!req.file) {
        res.status(400).send('No file uploaded.');
        return;
      }
  
      const { uid } = req.body;
      const imageFile = req.file;
      const bucket = storage.bucket('xdetect-img-profile');
      const fileName = `${Date.now()}_${imageFile.originalname}`;
      const fileUpload = bucket.file(fileName);
  
      const stream = fileUpload.createWriteStream({
        metadata: {
          contentType: imageFile.mimetype,
        },
      });
  
      stream.on('error', (error) => {
        console.error('Error uploading file:', error);
        res.status(500).send('Internal Server Error');
      });
  
      stream.on('finish', async () => {
        // Dapatkan URL publik file yang diunggah
        const [url] = await fileUpload.getSignedUrl({
          action: 'read',
          expires: '01-01-2025',
        });
  
        // Update URL gambar profil pengguna di database
        try {
          console.log('uid',uid);
          const userDoc = doc(db, 'users2', uid);
          
          await updateDoc(userDoc, { imgUrl: url, profilePicture: url });
          console.log('Profile picture URL updated in the database');
        } catch (error) {
          console.error('Error updating profile picture URL in the database:', error);
        }
  
        res.status(200).json({
          status: 'Success',
          message: 'Profile picture berhasil ditambahkan',
          fileName,
          url,
        });
      });
  
      stream.end(imageFile.buffer);
    } catch (error) {
      console.error('Error uploading file:', error);
      res.status(500).send('Internal Server Error');
    }
};


// Handler reset password
module.exports =  const resetPassword = async(req, res) => {
    const { email } = req.body;
    try {
        await sendPasswordResetEmail(auth, email);
        console.log('Link reset email telah dikirimkan ke:', email);
        return res.status(200).json({msg: "Link Reset Password Telah Dikirim Ke Email"});
    } catch (error) {
        return res.status(200).json({msg: "Error melakukan reset password"});
    }
}


// Handler signout
module.exports =  const signOutUser = async(req, res) => {
    try {
        await signOut(auth);
        return res.status(200).json({msg: "Sign out Berhasil"});
    } catch (error) {
        console.log('Error melakukan sign out:', error);
        return res.status(500).json({msg: "Gagal Melakukan Sign Out"});
    }
}



function generateUniqueID() {
  const prefix = 'xdetect-article-';
  const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
  let uniqueID = '';

  for (let i = 0; i < 3; i++) {
    const randomIndex = Math.floor(Math.random() * characters.length);
    const randomCharacter = characters[randomIndex];
    uniqueID += randomCharacter;
  }

  return prefix + uniqueID;
}



// Handler get User
module.exports =  const getUsers = async(req, res) => {
    try {
        const UsersCollection = collection(db, 'users2');
        const userSnapshot = await getDocs(UsersCollection);
        const users = [];
    
        userSnapshot.forEach((doc) => {
            const usersData = doc.data();
            users.push({ ...usersData });
        });
    
        res.status(200).json({
            success: true,
            msg: 'Berhasil',
            data: users,
        });
    } catch (error) {
        console.log('Error getting Users:', error);
        res.status(500).json({
            success: false,
            msg: 'Terjadi kesalahan, tunggu beberapa saat',
        });
    }
}

// Handler get User UID
module.exports =  const getUserUid = async (req, res) => {
    const { uid } = req.params;
    try {
        const userDoc = doc(db, 'users2', uid);
        const docSnap = await getDoc(userDoc);
        if (docSnap.exists()) {
            const data = docSnap.data();
            res.status(200).json({
                success: true,
                msg: 'Berhasil',
                data
            });
        }
        res.status(404).json({
            success: false,
            msg: 'Users tidak ditemukan',
        });
    } catch (error) {
        console.log('Error mendapatkan data user:', error);
    }
};

*/

module.exports = { register, login };