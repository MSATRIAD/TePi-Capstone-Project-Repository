const express = require("express");
const multer = require("multer");
const { register, login, resetPassword, signOutUser } = require("../controller/authController.js");
const { getProducts, getProductById, predictNutriscore, saveProduct, getSavedProductById, deleteSavedProductById, allSavedProducts, profile, editProfile } = require("../controller/appController.js");
const { authUser } = require("../auth/middleware.js")

const router = express.Router();
const storage = multer.memoryStorage(); 
const upload = multer({
  storage: storage,
  limits: { fileSize: 5 * 1024 * 1024 }, 
});

module.exports = upload;

router.post('/register', register);
router.post('/login', login);
router.get('/products', authUser, getProducts);
router.get('/products/:id', authUser, getProductById);
router.post('/nutriscore', authUser, predictNutriscore);
router.post('/products/save', authUser, saveProduct);
router.get('/saved-products', authUser, allSavedProducts);
router.get('/profile', authUser, profile);
router.put('/profile', authUser, upload.single('imageFile'), editProfile);
router.get('/saved-products/:id', authUser, getSavedProductById);
router.delete('/saved-products/:id', authUser, deleteSavedProductById);
router.post('/logout', authUser, signOutUser);
router.post('/login/resetPassword', resetPassword);

module.exports = router;