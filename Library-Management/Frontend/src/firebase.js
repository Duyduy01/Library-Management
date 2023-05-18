// Import the functions you need from the SDKs you need
import firebase from "firebase/compat/app";
import "firebase/compat/firestore";
import "firebase/compat/auth";
import { initializeApp } from "firebase/app";

const firebaseConfig = {
  apiKey: "AIzaSyAridRkKK2BEXgoGtkw_vQUmKUg0qQkrQI",
  authDomain: "login-signup-d4f83.firebaseapp.com",
  databaseURL:
    "https://login-signup-d4f83-default-rtdb.asia-southeast1.firebasedatabase.app",
  projectId: "login-signup-d4f83",
  storageBucket: "login-signup-d4f83.appspot.com",
  messagingSenderId: "1014466129990",
  appId: "1:1014466129990:web:9a80776c6f48077b679a47",
  measurementId: "G-ZZ8946G0MG",
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
// const analytics = getAnalytics(app);
import { getStorage } from "firebase/storage";
const storage = getStorage(app);

firebase.initializeApp(firebaseConfig);
export const firestore = firebase.firestore();

export default firebase;
export { storage };
