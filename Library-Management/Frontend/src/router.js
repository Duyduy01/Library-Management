import { createRouter, createWebHistory } from "vue-router";
import Home from "./pages/HomeA.vue";
import Login from "./pages/LoginUp.vue";
import Signup from "./pages/SignUp.vue";
import Post from "./pages/PostA.vue";
import AddBook from "./pages/AddBook";
import ViewBook from "./pages/ViewBook";
import UpdateBook from "./pages/UpdateBook";
import store from "./store/store";
import { IS_AUTHENTICATE_GETTER } from "./store/storeconstants";

const routes = [
  { path: "", component: Home },
  { path: "/log-in", component: Login, meta: { auth: false } },
  { path: "/sign-up", component: Signup, meta: { auth: false } },
  { path: "/posts", component: Post, meta: { auth: true } },
  { path: "/add-book", component: AddBook, meta: { auth: true } },
  { path: "/view-book/:id", component: ViewBook, meta: { auth: true } },
  { path: "/update-book/:id", component: UpdateBook, meta: { auth: true } },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  if (
    "auth" in to.meta &&
    to.meta.auth &&
    !store.getters[`auth/${IS_AUTHENTICATE_GETTER}`]
  ) {
    next("/log-in");
  } else if (
    "auth" in to.meta &&
    !to.meta.auth &&
    store.getters[`auth/${IS_AUTHENTICATE_GETTER}`]
  ) {
    next("/posts");
  } else {
    next();
  }
});
export default router;
