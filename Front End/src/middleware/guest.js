import store from "../store";

export default function guest({ next, router }) {
  if (store.getters.currentUser != null) {
    return router.push({ path: "/question-management/list-questions" });
  }

  return next();
}
