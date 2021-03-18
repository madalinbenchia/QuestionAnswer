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

  return axios
    .get(`${url}/answer/singleanswer?answerId=${id}`, options)
    .then(response => {
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

function update(payload) {
  //   const payload = jsona.serialize({
  //     stuff: user,
  //     includeNames: []
  //   });
  console.log(payload);
  const options = {};
  return axios
    .put(
      `${url}/answer/update?userId=${payload.u.userId}&userDisplayName=${payload.u.displayName}`,
      payload.a,
      options
    )
    .then(response => {
      return response.data;
    });
}

function destroy(id) {
  const options = {};

  return axios.delete(`${url}/answer/delete?id=${id}`, options);
}

export default {
  list,
  get,
  add,
  update,
  destroy
};
