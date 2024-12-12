# TegukPintar Capstone Project 
TegukPintar (TePi) is an innovative Android application designed to empower users in making healthier and more informed beverage choices. The app leverages Machine Learning technology to analyze nutritional information on packaged drinks. By inputting nutrition information, users receive a health rating based on sugar, fat, and other nutritional content.

TePi aims to address the lack of accessible tools for evaluating the full nutritional profile of beverages. It uses a custom TensorFlow model to classify beverages into health grades (Nutri-Score A, B, C, D, E) based on recognized standards.

This project aligns with global health goals by promoting awareness of healthier beverage options, encouraging better lifestyle habits, and ultimately contributing to improved public health outcomes.

## Our Team Member
| Nama    | Bangkit ID    | Learning Path    |
|:-------------:|:-------------:|:-------------:|
| Jason Natanael Krisyanto | M011B4KY2042 | Machine Learning |
| Muhammad Faiz Fahri | M011B4KY2812 | Machine Learning |
| Anel Fuad Abiyyu | M011B4KY0538 | Machine Learning |
| Muhammad Adzikra Dhiya Alfauzan | A011B4KY2689 | Mobile Development |
| Hosea Javier | A011B4KY1800 | Mobile Development |
| Ivan Arsy Himawan | C011B4KY2024 | Cloud Computing |
| Muhammad Satria Dharma | c011b4ky3083 | Cloud Computing |


## Machine Learning
- Preprocessed the data by cleaning and sorting beverage categories for the training and application catalog.
- Built 2 models, one with with TensorFlow to categorize outputs based on nutritional features with 97% accuracy, and the second one is the Vertex AI Gemini Pro model that is fine-tuned with 1,000 data for the chatbot's explanation capabilities.
- Developed the model's API using Flask and FastAPI. 

## Mobile Development

## Cloud Computing
- The backend of TePi is deployed using Google Cloud Run, providing scalable and efficient serverless deployment for handling user requests.
- The backend is built using Node.js and Express.js, offering a robust and fast runtime environment to handle API requests and interact with the database.
- Firebase Authentication is used to securely manage user login and authentication, enabling users to sign up and sign in with various authentication methods.
- Firestore is utilized to store user data, providing a flexible, scalable NoSQL database solution that ensures quick and reliable access to user profiles and preferences.
- Realtime Database is employed to store product data, ensuring real-time updates for the beverage information available in the app.
- Google Cloud Storage to store image profile user.
  
## Resources
[Node.js](https://nodejs.org/en) is an open-source and cross-platform JavaScript runtime environment. Event-driven, Fast, and Asynchronus Nature.

[Express.js](https://expressjs.com/)  is the most popular web framework for Node.js. It is designed for building web applications and APIs. Fast, unopinionated, minimalist web framework for Node.js

[FastAPI](https://fastapi.tiangolo.com/) is a modern, fast (high-performance), web framework for building APIs with Python based on standard Python type hints.
