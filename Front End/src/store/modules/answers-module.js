import service from "@/store/services/answers-service";
import Jsona from "jsona";

const jsona = new Jsona();

export const state = () => ({
  list: {},
  answer: {},
  meta: {},
  url: null
});

export const mutations = {
  SET_LIST: (state, list) => {
    state.list = list;
  },
  SET_RESOURCE: (state, answer) => {
    state.answer = answer;
  },
  SET_META: (state, meta) => {
    state.meta = meta;
  },
  SET_URL: (state, url) => {
    state.url = url;
  }
};

export const actions = {
  list({ commit, dispatch }, params) {
    return service.list(params, this.$axios).then(({ list, meta }) => {
      commit("SET_LIST", list);
      commit("SET_META", meta);
    });
  },

  get({ commit, dispatch }, params) {
    return service.get(params, this.$axios).then(answer => {
      commit("SET_RESOURCE", answer);
    });
  },

  add({ commit, dispatch }, params) {
    return service.add(params, this.$axios).then(answer => {
      commit("SET_RESOURCE", answer);
    });
  },

  update({ commit, dispatch }, params) {
    return service.update(params, this.$axios).then(answer => {
      commit("SET_RESOURCE", answer);
    });
  },

  destroy({ commit, dispatch }, params) {
    return service.destroy(params, this.$axios);
  },

  upload({ commit, dispatch }, { answer, image }) {
    return service.upload(answer, image, this.$axios).then(url => {
      commit("SET_URL", url);
    });
  }
};

const getters = {
  list: state => state.list,
  listTotal: state => state.meta.page.total,
  answer: state => state.answer,
  url: state => state.url
};

const answers = {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};

export default answers;
