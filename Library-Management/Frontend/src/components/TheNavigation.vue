<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary">
    <!-- <div class="container"> -->
    <div class="container-fluid">
      <a class="navbar-brand" href="#">Thư viện PTIT</a>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarNav"
        aria-controls="navbarNav"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item" v-if="!isAuthenticated">
            <router-link class="nav-link" to="/">Home</router-link>
          </li>
          <li class="nav-item" v-if="!isAuthenticated">
            <router-link class="nav-link" to="/log-in">Login</router-link>
          </li>
          <li class="nav-item" v-if="!isAuthenticated">
            <router-link class="nav-link" to="/sign-up">Signup</router-link>
          </li>
          <li class="nav-item" v-if="isAuthenticated">
            <router-link class="nav-link" to="/posts">Home</router-link>
          </li>
          <li class="nav-item" v-if="isAuthenticated">
            <router-link class="nav-link" to="/add-book">Add Book</router-link>
          </li>
          <li class="nav-item" v-if="isAuthenticated">
            <a href="#" class="nav-link" @click.prevent="onLogout()">Logout</a>
          </li>
        </ul>
      </div>
    </div>
    <!-- </div> -->
  </nav>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import { IS_AUTHENTICATE_GETTER, LOGOUT_ACTION } from "../store/storeconstants";

export default {
  computed: {
    ...mapGetters("auth", {
      isAuthenticated: IS_AUTHENTICATE_GETTER,
    }),
  },
  methods: {
    ...mapActions("auth", {
      logout: LOGOUT_ACTION,
    }),
    onLogout() {
      this.logout();
      this.$router.replace("/log-in");
    },
  },
};
</script>

<style scoped lang="css">
.bg-body-tertiary {
  background: linear-gradient(to right, #80878d, #2c3e50);
  z-index: 100;
  transition: 0.3s all ease;
  box-shadow: -33px -14px 37px 17px #888888;
  width: 100%;
  height: 10%;
}

.container {
  width: 100%;
  padding-right: 15px;
  padding-left: 15px;
  margin-right: auto;
  margin-left: auto;
}

.container-fluid {
  height: 100%;
  width: 100%;
  padding-right: 15px;
  padding-left: 15px;
  /* display: flex; */
  /* flex-wrap: wrap; */
  margin-right: 15px;
  margin-left: 15px;
}

.navbar-brand {
  color: #fff;
  font-size: 20px;
  font-weight: bold;
  text-transform: uppercase;
}

.navbar-nav {
  font-size: 20px;
}

.nav-item {
  text-decoration: none;
}

#navbarNav {
  justify-content: flex-end;
}

.nav-item {
  display: inline-block;
  padding: 10px 15px 10px 0;
}

.nav-item a {
  border-radius: 10px;
  color: #f5f5f5;
}

.nav-item a:hover {
  /* height: 100%; */
  border-radius: 10px;
  background: #ddd;
  color: #333;
}

.nav-item:last-child .nav-link:last-child {
  margin-right: 30px;
}

.navbar-toggler-icon {
  filter: brightness(0) invert(1);
  -webkit-filter: brightness(0) invert(1);
}
</style>
