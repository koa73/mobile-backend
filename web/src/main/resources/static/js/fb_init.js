/**
 * Created by oakutsenko on 01.02.2018.
 */

// Initialize Firebase
var config = {
    apiKey: "AIzaSyDrEYhBvV9JBq0kSd-Ws7osdiL7T3PkOVI",
    authDomain: "mobile-front.firebaseapp.com",
    databaseURL: "https://mobile-front.firebaseio.com",
    projectId: "mobile-front",
    storageBucket: "mobile-front.appspot.com",
    messagingSenderId: "952720125865"
};

firebase.initializeApp(config);
firebase.auth().languageCode = 'ru';
