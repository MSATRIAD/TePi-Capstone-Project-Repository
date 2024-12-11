const admin = require('firebase-admin');

var serviceAccount = require("../../firebase-adminsdk.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://tepi-teguk-pintar-default-rtdb.asia-southeast1.firebasedatabase.app/"
});

const authUser = async (req, res, next) => {
    const authHeader = req.headers.authorization;

    if (!authHeader || !authHeader.startsWith('Bearer ')) {
        return res.status(401).json({ message: 'Unauthorized' });
    }

    const idToken = authHeader.split('Bearer ')[1];

    try {
        const decodedToken = await admin.auth().verifyIdToken(idToken);
        req.user = decodedToken; 
        next(); 
    } catch (error) {
        console.error('Error verifying ID token:', error);
        return res.status(403).json({ message: 'Forbidden' });
    }
};

module.exports = { authUser };