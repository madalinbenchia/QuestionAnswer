import qs from "qs";
import axios from "axios";

const url = "https://localhost:44329/api";

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

function get(id) {
  const options = {
    headers: {}
  };

  return axios.get(`${url}/comments?postId=${id}`, options).then(response => {
    let comment = response.data;
    return comment;
  });
}

function add(comment) {
  //   const payload = jsona.serialize({
  //     stuff: user,
  //     includeNames: null
  //   });

  const options = {};

  return axios
    .post(`${url}/post/addcomment`, comment, options)
    .then(response => {
      return response.data;
    });
}

function update(comment) {
  //   const payload = jsona.serialize({
  //     stuff: user,
  //     includeNames: []
  //   });

  const options = {
    // headers: {
    //   Accept: "application/vnd.api+json",
    //   "Content-Type": "application/vnd.api+json"
    // }
  };

  return axios
    .patch(`${url}/posts/${comment.id}`, comment, options)
    .then(response => {
      return response.data;
    });
}

function destroy(id) {
  const options = {};

  return axios.delete(`${url}/posts/${id}`, options);
}

export default {
  list,
  get,
  add,
  update,
  destroy
};
