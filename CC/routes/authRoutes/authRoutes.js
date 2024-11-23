const express = require("express");
const { registerUser, loginUser, resetPassword, getUserProfile } = require("../../controllers/authController");

const router = express.Router();

// Routes untuk autentikasi
router.post("/register", registerUser);
router.post("/login", loginUser);
router.post("/reset-password", resetPassword);
router.get("/profile", getUserProfile);

module.exports = router;
