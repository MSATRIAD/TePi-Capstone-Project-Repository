const express = require("express");
const multer = require("multer");
const { predict, resetPassword, signIn, signOutUser, signUp, uploadProfilePicture, uploadProfilePictureWithUID, postArticle, getAllArticles, getArticleByUID, getUsers, getUserUid } = require("./auth.js");

const router = express.Router();
const multerStorage = multer.memoryStorage();
const upload = multer({ storage: multerStorage });

// Routes for users
router.post('/signup', signUp)
router.post('/signin', signIn)
/*router.post('/signout', signOutUser)
router.post('/reset-password', resetPassword)


module.exports = router;
*/