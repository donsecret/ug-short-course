import firebase from 'firebase/app'

let config = {
  apiKey: process.env.api_key,
  authDomain: process.env.project_id + '.firebaseapp.com',
  databaseURL: 'https://' + process.env.project_id + '.firebaseio.com',
  projectId: process.env.project_id,
  storageBucket: process.env.project_id + '.appspot.com',
  messagingSenderId: process.env.messaging_id
}

let firebaseApp = firebase.initializeApp(config)

export default {
  app: firebaseApp
}
