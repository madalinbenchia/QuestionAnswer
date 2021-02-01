import Vue from "vue";
import router from "@/router";
import { VueAuthenticate } from "vue-authenticate";

import axios from "axios";
import VueAxios from "vue-axios";
Vue.use(VueAxios, axios);

const vueAuth = new VueAuthenticate(Vue.prototype.$http, {
  baseUrl: process.env.VUE_APP_API_BASE_URL,
  tokenName: "Token",
  tokenPath: "Token",
  loginUrl: "/user/login",
  registerUrl: "/user/register"
});

export default {
  state: {
    isAuthenticated:
      localStorage.getItem("vue-authenticate.vueauth_Token") !== null,
    namespaced: true,
    currentUser: localStorage.getItem("currentUser")
  },

  getters: {
    isAuthenticated(state) {
      return state.isAuthenticated;
    },
    currentUser(state) {
      return JSON.parse(state.currentUser);
    }
  },

  mutations: {
    isAuthenticated(state, payload) {
      state.isAuthenticated = payload.isAuthenticated;
    },
    currentUser(state, payload) {
      state.currentUser = payload.currentUser;
    }
  },

  actions: {
    login(context, payload) {
      return vueAuth
        .login(payload.user, payload.requestOptions)
        .then(response => {
          context.commit("isAuthenticated", {
            isAuthenticated: vueAuth.isAuthenticated()
          });

          let me = JSON.stringify(response["data"]);
          localStorage.setItem("currentUser", me);

          window.location.replace("/");
          // router.push({ path: "/dashboard" });
        });
    },

    register(context, payload) {
      return vueAuth
        .register(payload.user, payload.requestOptions)
        .then(response => {
          context.commit("isAuthenticated", {
            isAuthenticated: vueAuth.isAuthenticated()
          });
          let me = JSON.stringify(response["data"]);
          localStorage.setItem("currentUser", me);
          window.location.replace("/");

          router.push({ path: "/question-management/list-questions" });
        });
    },

    logout(context, payload) {
      return vueAuth.logout().then(response => {
        console.log("in logout");
        context.commit("isAuthenticated", {
          isAuthenticated: vueAuth.isAuthenticated()
        });
        localStorage.removeItem("currentUser");

        //delete axios.defaults.headers.common["Authorization"];

        router.push({ name: "Login" });
        window.location.replace("/");
      });
    }
  }
};
