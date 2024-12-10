const express = require("express");
const multer = require("multer");
const { register, login } = require("../controller/authController.js");
const { getProducts, getProductById, predictNutriscore, saveProduct, getSavedProductById, deleteSavedProductById, allSaveProduct, profile, editProfile } = require("../controller/appController.js");
const { authUser } = require("../auth/middleware.js")

const router = express.Router();
const storage = multer.memoryStorage(); // Simpan file di memori sementara
const upload = multer({
  storage: storage,
  limits: { fileSize: 5 * 1024 * 1024 }, // Batas ukuran file 5 MB
});

module.exports = upload;

// Routes for users
router.post('/register', register);
router.post('/login', login);
router.get('/products', authUser, getProducts);
router.get('/products/:id', authUser, getProductById);
router.post('/nutriscore', authUser, predictNutriscore);
router.post('/saveProduct', authUser, saveProduct);
router.get('/allSaveProduct', authUser, allSaveProduct);
router.get('/profile', authUser, profile);
router.put('/editProfile', authUser, upload.single('imageFile'), editProfile);
router.get('/savedProduct/:id', authUser, getSavedProductById);
router.delete('/savedProduct/:id', authUser, deleteSavedProductById);

/*router.post('/signout', signOutUser)
router.post('/reset-password', resetPassword)

*/
module.exports = router;