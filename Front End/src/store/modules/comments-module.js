import service from "@/store/services/comments-service";
import Jsona from "jsona";

const jsona = new Jsona();

export const state = () => ({
  list: {},
  comment: {},
  meta: {},
  url: null
});

export const mutations = {
  SET_LIST: (state, list) => {
    state.list = list;
  },
  SET_RESOURCE: (state, comment) => {
    state.comment = comment;
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
    return service.get(params, this.$axios).then(comment => {
      commit("SET_RESOURCE", comment);
    });
  },

  add({ commit, dispatch }, params) {
    return service.add(params, this.$axios).then(comment => {
      commit("SET_RESOURCE", comment);
    });
  },

  update({ commit, dispatch }, params) {
    return service.update(params, this.$axios).then(comment => {
      commit("SET_RESOURCE", comment);
    });
  },

  destroy({ commit, dispatch }, params) {
    return service.destroy(params, this.$axios);
  },

  upload({ commit, dispatch }, { comment, image }) {
    return service.upload(comment, image, this.$axios).then(url => {
      commit("SET_URL", url);
    });
  }
};

const getters = {
  list: state => state.list,
  listTotal: state => state.meta.page.total,
  comment: state => state.comment,
  url: state => state.url
};

const comments = {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};

export default comments;
