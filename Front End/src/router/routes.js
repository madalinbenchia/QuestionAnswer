import DashboardLayout from "@/views/Layout/DashboardLayout.vue";
import AuthLayout from "@/views/Pages/AuthLayout.vue";

// Example pages
import UserProfile from "@/views/Examples/UserProfile.vue";
import ViewUserPage from "@/views/Examples/ViewUserPage.vue";
import ListQuestionsPage from "@/views/Examples/QuestionManagement/ListQuestionsPage.vue";
import ViewQuestion from "@/views/Examples/QuestionManagement/ViewQuestion.vue";
import EditQuestion from "@/views/Examples/QuestionManagement/EditQuestion.vue";
import EditAnswer from "@/views/Examples/QuestionManagement/EditAnswer.vue";
import Search from "@/views/Examples/QuestionManagement/Search.vue";
import AddQuestionPage from "@/views/Examples/QuestionManagement/AddQuestionPage.vue";

// Calendar
const Calendar = () =>
  import(/* webpackChunkName: "extra" */ "@/views/Calendar/Calendar.vue");

const Login = () =>
  import(/* webpackChunkName: "pages" */ "@/views/Pages/Login.vue");
const Register = () =>
  import(/* webpackChunkName: "pages" */ "@/views/Pages/Register.vue");

//import middleware
import auth from "@/middleware/auth";
import guest from "@/middleware/guest";

let examplesMenu = {
  path: "/",
  component: DashboardLayout,
  name: "Examples",
  children: [
    {
      path: "user-profile",
      name: "User Profile",
      components: { default: UserProfile },
      meta: { middleware: auth }
    },
    {
      path: "users/:id",
      name: "View User",
      components: { default: ViewUserPage }
      // meta: { middleware: auth }
    },
    {
      path: "question-management/list-questions",
      name: "List Questions",
      components: { default: ListQuestionsPage }
      // meta: { middleware: auth }
    },
    {
      path: "question-management/add-question",
      name: "Add Question",
      components: { default: AddQuestionPage },
      meta: { middleware: auth }
    },
    {
      path: "question-management/question/:id",
      name: "View Question",
      components: { default: ViewQuestion }
      // meta: { middleware: auth }
    },
    {
      path: "question-management/edit-question/:id",
      name: "Edit Question",
      components: { default: EditQuestion },
      meta: { middleware: auth }
    },
    {
      path: "question-management/edit-answer/:id",
      name: "Edit Answer",
      components: { default: EditAnswer },
      meta: { middleware: auth }
    },
    {
      path: "search?q=:id",
      name: "Search",
      components: { default: Search }
      // meta: { middleware: auth }
    },
    {
      path: "/calendar",
      name: "Calendar",
      components: { default: Calendar },
      meta: { middleware: auth }
    }
  ]
};

let authPages = {
  path: "/",
  component: AuthLayout,
  name: "Authentication",
  children: [
    {
      path: "/login",
      name: "Login",
      component: Login,
      meta: { middleware: guest }
    },
    {
      path: "/register",
      name: "Register",
      component: Register,
      meta: { middleware: guest }
    }
  ]
};

const routes = [
  {
    path: "/",
    redirect: "/question-management/list-questions"
  },
  examplesMenu,
  authPages
];

export default routes;
