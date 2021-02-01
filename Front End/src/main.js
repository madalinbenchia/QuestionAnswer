import Vue from "vue";
import axios from "axios";
import App from "./App.vue";

//plugins
import DashboardPlugin from "./plugins/dashboard-plugin";
import VueAxios from "vue-axios";

import "./assets/css/style.css";

// router&store setup
import router from "./router";
import store from "./store";
// plugin setup
Vue.use(DashboardPlugin);
Vue.use(VueAxios, axios);

/* eslint-disable no-new */
// new Vue({
//   el: '#app',
//   render: h => h(App),
//   router
// });
/* eslint-disable no-new */
const app = new Vue({
  router: router,
  store: store,
  el: "#app",
  render: h => h(App)
});

store.$app = app;
