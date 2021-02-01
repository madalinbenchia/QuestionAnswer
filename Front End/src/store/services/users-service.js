import qs from "qs";
import axios from "axios";
import Jsona from "jsona";

const url = process.env.VUE_APP_API_BASE_URL;
const jsona = new Jsona();

function list(params) {
  const options = {};

  const max = "10";
  return axios.get(`${url}/user?maxNumber=${max}`, options).then(response => {
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
  return axios.get(`${url}/me?id=${id}`, options).then(response => {
    let user = response.data;
    return user;
  });
}

function getdetails(id) {
  const options = {
    headers: {}
  };
  return axios.get(`${url}/userdetails?id=${id}`, options).then(response => {
    let user = response.data;
    return user;
  });
}

function add(user) {
  // const payload = jsona.serialize({
  //   stuff: user,
  //   includeNames: null
  // });

  const options = {
    headers: {}
  };

  return axios.post(`${url}/user`, payload, options).then(response => {
    return response.data;
  });
}

function update(user) {
  const options = {
    headers: {}
  };
  return axios.put(`${url}/user/updateuser`, user, options).then(response => {
    return response.data;
  });
}

function destroy(id) {
  const options = {};

  return axios.delete(`${url}/users/${id}`, options);
}

export default {
  list,
  get,
  getdetails,
  add,
  update,
  destroy
};
