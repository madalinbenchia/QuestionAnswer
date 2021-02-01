import qs from "qs";
import axios from "axios";

const url = process.env.VUE_APP_API_BASE_URL;

function list() {
  const options = {
    headers: {}
  };
  const max = "10";
  return axios.get(`${url}/posts?maxNumber=${max}`, options).then(response => {
    return {
      list: response.data,
      meta: response.data.meta
    };
  });
}

function search(id) {
  const options = {
    headers: {}
  };

  return axios
    .get(`${url}/search?searchString=${id}`, options)
    .then(response => {
      console.log(response);
      return {
        search: response.data,
        meta: response.data.meta
      };
    });
}

function vote(v) {
  const options = {
    headers: {}
  };

  return axios.post(`${url}/vote/add`, v, options).then(response => {
    return response.data;
  });
}

function comment(c) {
  const options = {
    headers: {}
  };

  return axios.post(`${url}/comment/addcomment`, c, options).then(response => {
    return response.data;
  });
}

function getcomments(id) {
  const options = {
    headers: {}
  };

  return axios.get(`${url}/comments?PostId=${id}`, options).then(response => {
    let comments = response.data;
    return comments;
  });
}
function get(id) {
  const options = {
    headers: {}
  };

  return axios.get(`${url}/question?id=${id}`, options).then(response => {
    let question = response.data;
    return question;
  });
}

function add(question) {
  //   const payload = jsona.serialize({
  //     stuff: user,
  //     includeNames: null
  //   });

  const options = {};

  return axios
    .post(`${url}/post/addquestion`, question, options)
    .then(response => {
      return response.data;
    });
}

function update(question) {
  //   const payload = jsona.serialize({
  //     stuff: user,
  //     includeNames: []
  //   });

  const options = {};

  return axios
    .put(`${url}/post/updatequestion`, question, options)
    .then(response => {
      return response.data;
    });
}

function destroy(id) {
  const options = {};

  return axios.delete(`${url}/post/deletequestion?id=${id}`, options);
}

export default {
  list,
  search,
  vote,
  comment,
  getcomments,
  get,
  add,
  update,
  destroy
};
