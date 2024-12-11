const { initializeApp } = require('firebase/app');
const { getAuth } = require('firebase/auth');
const { getFirestore, collection, doc, setDoc, getDocs, getDoc, deleteDoc, updateDoc } = require('firebase/firestore');
const { getDatabase, ref, get } = require('firebase/database');

const firebaseConfig = { 
    apiKey: "AIzaSyARSIfOKnEsYA88YIPH6T84W84W37LqmM8", 
    projectId: "tepi-teguk-pintar", 
    storageBucket: "tepi-teguk-pintar.firebasestorage.app", 
    messagingSenderId: "186840913924", 
    appId: "1:186840913924:android:1b1f115b8d51fad4127d51", 
    databaseURL: "https://tepi-teguk-pintar-default-rtdb.asia-southeast1.firebasedatabase.app"
};

const app = initializeApp(firebaseConfig);
const auth = getAuth(app);
const db = getFirestore(app);
const realtimeDb = getDatabase(app); 

module.exports = { auth, db, realtimeDb, ref, get, collection, doc, setDoc, getDocs, getDoc, deleteDoc, updateDoc };