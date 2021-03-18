import store from "../store";

export default function auth({ next, router }) {
  console.log(store.getters);
  if (store.getters.currentUser == null) {
    return router.push({ name: "Login" });
  }

  return next();
}
