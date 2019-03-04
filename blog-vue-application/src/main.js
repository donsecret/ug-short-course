import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueFire from 'vuefire'
import firebaseapp from './firebaseapp'

Vue.config.productionTip = false
Vue.use(VueFire)

new Vue({
  router,
  store,
  firebaseapp,
  data: () => ({
    // Usually an array for collection
    users: [],
    // and null for documents
    currentUser: null
  }),
  firestore: {
    users: firebaseapp.app.database().ref('users'),
    currentUser: firebaseapp.app.database().ref('users').child('fT3P93IV2dGWlrSt17ua')
  },
  render: h => h(App)
}).$mount('#app')
