import Vue from "vue";
import Vuex from "vuex";

import auth from "./modules/auth";
import users from "./modules/users-module";
import questions from "./modules/questions-module";
import answers from "./modules/answers-module";
import comments from "./modules/comments-module";
Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    auth,
    users,
    questions,
    answers,
    comments
  }
});
