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

function get(id) {
  const options = {
    headers: {}
  };

  return axios.get(`${url}/question?id=${id}`, options).then(response => {
    let answer = response.data;
    return answer;
  });
}

function add(payload) {
  //   const payload = jsona.serialize({
  //     stuff: user,
  //     includeNames: null
  //   });

  const options = {};
  return axios
    .post(`${url}/answer/add?questionId=${payload.qId}`, payload.answ, options)
    .then(response => {
      return response.data;
    });
}

function update(answer) {
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
    .put(`${url}/post/updateanswer?id=${answer.Id}`, answer, options)
    .then(response => {
      return response.data;
    });
}

function destroy(id) {
  const options = {};

  return axios.delete(`${url}/post/deleteanswer?id=${id}`, options);
}

export default {
  list,
  get,
  add,
  update,
  destroy
};
