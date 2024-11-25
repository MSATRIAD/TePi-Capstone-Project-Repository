const express = require('express');
const admin = require('firebase-admin');
const cors = require('cors');
require('dotenv').config();

const app = express();
app.use(cors());
app.use(express.json());

// Initialize Firebase Admin
const serviceAccount = {
  // You'll need to fill this with your service account credentials
  
	"type": "service_account",
	"project_id": "testing-442012",
	"private_key_id": "6ec200738be1a21a419e6cb6ff1933c1ae2725a0",
	"private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDsew9Wf5ta4vUG\nerEqfWPqSkeERiYYPJGcqLsIFLnGq4SEPJrudqlK49RU7b3YId3QMmEuucVBnYZy\nWjs58YbksYgqEd6ScVUkOWtJC6USwA3caTzTB+kkda7pue5cSAUPBrZVEuFXwig8\nBR+qTT8/4G44aVRASgUSAA3Afv1jz9mmuY/BQo4aIKP+p2/EsZd9Htztv4btHQlb\nBCfSVv7+wczySaD0nQbwSGv7ikaM5H/UfQhJbL6d7JPIv+ssJHSQvKu8EqvDI2Tu\n0cJTJvPMq4GK7hWVifDWTF1+2giJjkyBnaC4Buzrw/23ObTHe07lKQlap3vE+klg\nEqDZ2EvvAgMBAAECggEAUKUY0nE6/DAoyjheIbTYNYZ2RCQeCqbhK3EZM0TKI/fK\n/cOjPE5IJmtytjHLS6ElAUKlBKfCnsFHH8koqHZ2/5eRbXNhmUQ/+lYAgc0QUsgf\nEfrrjLYGanIdn8RSBrNi0kCCPpntMh3a1bkTluK3/qMdQ2ycGD6F4yEUj4BfDkGk\nPpR9Iot7KqcwwTRRuszw7dVTGUCE5eJAZziiK/yIKbqMVwfZyPqu9vC2q0cmwO7y\nHnrpYrOLZcMMh3AfMCLONs4tmzGXA7dclaU4cJ0rYvc1D/+687RCM7EFUn4eOIh2\nzzrUXKkLBImnYfPckSHIb+yp6LyTgzkQXiiWt/fdkQKBgQD9aiRbq4IDQincEtVx\nfgitXN26yR1+eO8NNdzwD+s4HZFc+ziYdhws9G/6UQoesiNmrr/PytP1WS6vgfEI\nrYIHz4LUoaP8Ea9q+Op/Ovqw1IjQN6Ix6PYgOJTkhaZeQI5lB0BTaKRKZh7xXtwj\n6ONzkiqQvA6X8UxWV4FeTFOgUQKBgQDu5LDJ9alLEdm0e/kWfrW/H4BSg53+Xo1R\nC6BseKEkJOHcA3U/8mNG4pBHsnyDSFMirC9L6gXNYgObBSlJgEc7fPhuUeXlQZLa\noIHiTQuNtqBzPX1ZmQ6D9Ub6kX0aQvd+JZmwQk4yu2GMYwy1h4TC73cz0GmdY+9t\nZ2kKDahYPwKBgB4wCbq92JQIyrHDsJd/ewsj1+oLUS8iut9o/jO+wbeCljFsX9aR\no1ObnsdD5h+3+s2dnY3kTJGamRwhL5RoDuhdSOmJg95gQPROSKj1Xq2388BSEEk/\nxioac2oxnZr2t7/DOF93RLL0LVV1hmXktUusKtPpAxgIj55teCTClOexAoGAZ6WN\nFnKhOtLJ08ad8aaUEBIIyMQV/lDjmzAiMC2+LEJcQ+q0KkRt0klmM9U6tHLirv5p\nntQwJEXMLqv0l9EyFptH5gVeazMiFfzhnV+0DY4Y2ybohcEkTRhT7U7GKmi3nGGP\n5qs95kDqN9CZqQDdFv5yh2Z7DDBZpuvg5N28Fl0CgYBmXh3rctn+P5jjUCx5KXZm\nyKXcmOZWbQT5y7zbE0cS6WrZmR8Xa2ynelVy/HUw9SL2TbQU/lxF9V0GzCcHAI9z\nYTij2QLYTlLzB9He4bfgpIMxPEx7Bmg8mEQ11PhXklRVZBKHdC2OglMSL8R46xDO\nV+MDs+tFQpu5J1HI2X8avw==\n-----END PRIVATE KEY-----\n",
	"client_email": "firebase-adminsdk-lll6a@testing-442012.iam.gserviceaccount.com",
	"client_id": "104875396769177569282",
	"auth_uri": "https://accounts.google.com/o/oauth2/auth",
	"token_uri": "https://oauth2.googleapis.com/token",
	"auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
	"client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-lll6a%40testing-442012.iam.gserviceaccount.com",
	"universe_domain": "googleapis.com"
};

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://product-tepi.asia-southeast1.firebasedatabase.app/"
});

const db = admin.database();

// GET endpoint to fetch all data
app.get('/api/data', async (req, res) => {
  try {
    const ref = db.ref('/');
    const snapshot = await ref.once('value');
    const fullData = snapshot.val();
    
    // Transform the data to only include categories and product_name
    const filteredData = {};
    
    Object.keys(fullData).forEach(key => {
      const item = fullData[key];
      if (item.categories && item.product_name) {
        filteredData[key] = {
          categories: item.categories,
          product_name: item.product_name
        };
      }
    });
    
    res.status(200).json(filteredData);
  } catch (error) {
    console.error('Error fetching data:', error);
    res.status(500).json({ error: 'Failed to fetch data' });
  }
});


const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});