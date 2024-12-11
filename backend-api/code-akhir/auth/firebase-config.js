const { initializeApp } = require('firebase/app');
const { getAuth } = require('firebase/auth');
const { getFirestore, collection, doc, setDoc } = require('firebase/firestore');
const { getDatabase, ref, get } = require('firebase/database');


const firebaseConfig = { 
    apiKey: "AIzaSyBYxMvGnHj9f6wNARHCk0CpVNLQEAVNBjM", 
    projectId: "testing-442012", 
    storageBucket: "testing-442012.appspot.com", 
    messagingSenderId: "2138847083", 
    appId: "1:2138847083:android:0e9294714a09944857fe2e" 
};

const app = initializeApp(firebaseConfig);
const auth = getAuth(app);
const db = getFirestore(app);
const realtimeDb = getDatabase(app); 

module.exports = { auth, db, realtimeDb, ref, get, collection, doc, setDoc };