const express = require("express");
const multer = require("multer");
const { register, login } = require("../controller/authController.js");
const { getProducts, getProductById, predictNutriscore, saveProduct } = require("../controller/appController.js");
const { authUser } = require("../auth/middleware.js")

const router = express.Router();
const multerStorage = multer.memoryStorage();
const upload = multer({ storage: multerStorage });

// Routes for users
router.post('/register', register);
router.post('/login', login);
router.get('/products', authUser, getProducts);
router.get('/products/:id', authUser, getProductById);
router.post('/nutriscore', authUser, predictNutriscore);
router.post('/saveProduct', authUser, saveProduct);

/*router.post('/signout', signOutUser)
router.post('/reset-password', resetPassword)

*/
module.exports = router;