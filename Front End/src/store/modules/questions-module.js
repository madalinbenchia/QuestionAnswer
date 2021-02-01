import service from "@/store/services/questions-service";
import Jsona from "jsona";

const jsona = new Jsona();

export const state = () => ({
  list: {},
  search: {},
  vote: {},
  comment: {},
  getcomments: {},
  question: {},
  meta: {},
  url: null
});

export const mutations = {
  SET_LIST: (state, list) => {
    state.list = list;
  },

  SET_VOTE: (state, vote) => {
    state.vote = vote;
  },

  SET_COMMENT: (state, comment) => {
    state.comment = comment;
  },

  SET_GET_COMMENTS: (state, getcomments) => {
    state.getcomments = getcomments;
  },

  SET_SEARCH: (state, search) => {
    state.search = search;
  },
  SET_RESOURCE: (state, question) => {
    state.question = question;
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

  search({ commit, dispatch }, params) {
    return service.search(params, this.$axios).then(({ search, meta }) => {
      commit("SET_SEARCH", search);
    });
  },

  vote({ commit, dispatch }, params) {
    return service.vote(params, this.$axios).then(({ vote, meta }) => {
      commit("SET_VOTE", vote);
    });
  },

  comment({ commit, dispatch }, params) {
    return service.comment(params, this.$axios).then(({ comment, meta }) => {
      commit("SET_COMMENT", comment);
    });
  },

  getcomments({ commit, dispatch }, params) {
    return service
      .getcomments(params, this.$axios)
      .then(({ getcomments, meta }) => {
        commit("SET_GET_COMMENTS", getcomments);
      });
  },

  get({ commit, dispatch }, params) {
    return service.get(params, this.$axios).then(question => {
      commit("SET_RESOURCE", question);
    });
  },

  add({ commit, dispatch }, params) {
    return service.add(params, this.$axios).then(question => {
      commit("SET_RESOURCE", question);
    });
  },

  update({ commit, dispatch }, params) {
    return service.update(params, this.$axios).then(question => {
      commit("SET_RESOURCE", question);
    });
  },

  destroy({ commit, dispatch }, params) {
    return service.destroy(params, this.$axios);
  },

  upload({ commit, dispatch }, { question, image }) {
    return service.upload(question, image, this.$axios).then(url => {
      commit("SET_URL", url);
    });
  }
};

const getters = {
  list: state => state.list,
  search: state => state.search,
  vote: state => state.vote,
  comment: state => state.comment,
  getcomments: state => state.getcomments,
  listTotal: state => state.meta.page.total,
  question: state => state.question,
  url: state => state.url
};

const questions = {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};

export default questions;
