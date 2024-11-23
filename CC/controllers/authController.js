const admin = require("firebase-admin");

// Fungsi untuk autentikasi
exports.registerUser = async (req, res) => {
  const { email, password, username } = req.body;
  try {
    const user = await admin.auth().createUser({
      email,
      password,
      displayName: username,
    });
    res.json({ message: "User registered successfully", user });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

exports.loginUser = (req, res) => {
  res.status(400).json({
    error: "Use Firebase Client SDK for login authentication.",
  });
};

exports.resetPassword = async (req, res) => {
  const { email, newPassword } = req.body;
  try {
    const user = await admin.auth().getUserByEmail(email);
    await admin.auth().updateUser(user.uid, { password: newPassword });
    res.json({ message: "Password reset successfully" });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

exports.getUserProfile = (req, res) => {
  const { user } = req;
  res.json({ username: user.name || "Anonymous", email: user.email });
};
