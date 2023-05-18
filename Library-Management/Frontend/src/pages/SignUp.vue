<template>
  <div class="bg">
    <the-navigation class="nav"></the-navigation>
    <div class="screen">
      <div id="login-signup">
        <div class="form-heading">
          <h3>Signup</h3>
          <hr />
        </div>
        <div class="alert alert-danger" v-if="error">
          {{ error }}
        </div>
        <form @submit.prevent="onSignup()">
          <div class="form-group">
            <input
              placeholder="Name"
              type="text"
              class="form-control"
              v-model.trim="displayName"
              required
            />
          </div>
          <div class="form-group">
            <input
              placeholder="Email"
              type="text"
              class="form-control"
              v-model.trim="email"
            />
            <div class="error" v-if="errors.email">
              {{ errors.email }}
            </div>
          </div>
          <div class="form-group">
            <input
              placeholder="Password"
              type="password"
              class="form-control"
              v-model.trim="password"
            />
            <div class="error" v-if="errors.password">
              {{ errors.password }}
            </div>
          </div>

          <div>
            <p class="checkAccount">
              <router-link to="/log-in"
                >Already have a account, let Login</router-link
              >
            </p>
          </div>

          <div class="my-3">
            <button type="submit" class="btn btn-primary">Signup</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import SignupValidations from "../services/SignupValidations";
import { mapActions, mapMutations } from "vuex";
import {
  SIGNUP_ACTION,
  LOADING_SPINNER_SHOW_MUTATION,
} from "../store/storeconstants";
import TheNavigation from "@/components/TheNavigation.vue";

export default {
  components: { TheNavigation },
  data() {
    return {
      displayName: "",
      email: "",
      password: "",
      errors: [],
      error: "",
    };
  },
  methods: {
    ...mapActions("auth", {
      signup: SIGNUP_ACTION,
    }),

    ...mapMutations({
      showLoading: LOADING_SPINNER_SHOW_MUTATION,
    }),
    async onSignup() {
      let validations = new SignupValidations(
        this.email,
        this.password,
        this.displayName
      );

      this.errors = validations.checkValidations();
      if ("email" in this.errors || "password" in this.errors) {
        return false;
      }
      //make spinner true
      this.showLoading(true);
      //signup registration
      await this.signup({
        displayName: this.displayName,
        email: this.email,
        password: this.password,
      }).catch((error) => {
        this.error = error;
        this.showLoading(false);
      });
      //make spinner false
      this.showLoading(false);
      alert("Đăng ký thành công");
      this.$router.push("/");
    },
  },
};
</script>

<style lang="css" scoped>
.bg {
  position: relative;
  width: 100%;
  height: 100%;
}

.bg::before {
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  background-image: url("../assets/bgr.jpg");
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  filter: brightness(0.5);
  z-index: -1; /* đảm bảo pseudo-element nằm dưới các phần tử khác */
}

.nav {
  background: none;
  background-color: transparent !important;
}

.screen {
  position: relative;
  height: 90%;
  width: 100%;
}

#login-signup {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -55%);
  width: 400px;
  background: rgba(0, 0, 0, 0.8);
  flex-grow: 1;
  padding: 30px 30px 20px;
  box-shadow: 0px 0px 17px 2px rgba(255, 255, 255, 0.8);
}

#login-signup h3 {
  font-size: 25px;
  color: #f5f5f5;
  text-align: center;
  margin-bottom: 30px;
}

.form-heading {
  font-size: 25px;
  color: #f5f5f5;
  text-align: center;
  margin-bottom: 30px;
}

.form-heading h3 {
  display: block;
}

.form-group {
  position: relative;
  border-bottom: 1px solid #fff;
  margin-top: 15px;
  margin-bottom: 20px;
  display: block;
}

.form-control {
  background: transparent;
  border: 0;
  outline: 0;
  color: #f5f5f5;
  flex-grow: 1;
  transition: 150ms cubic-bezier(0.4, 0, -2, 1);
}

.form-control:focus {
  background-color: transparent;
}

.form-control::placeholder {
  color: #f5f5f5;
}

.checkAccount {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 0 1rem;
  margin: 0;
}

.checkAccount a {
  text-decoration: none;
}

.checkAccount a:hover {
  text-decoration: underline;
}

.btn {
  background: transparent;
  border: 1px solid #f5f5f5;
  color: #fff;
  width: 100%;
  text-transform: uppercase;
  padding: 10px 0;
  transition: 0.25s ease-in-out;
  margin-top: 30px;
  text-align: center;
  display: block;
  text-decoration: none;
}

.btn:hover {
  border: 1px solid #54a0ff;
  background: transparent;
}

.error {
  color: red;
  padding-top: 10px;
  padding-left: 11px;
  padding-bottom: 2px;
}

.form-control {
  color: #f5f5f5;
}

.form-control {
  color: #f5f5f5;
}

@media (max-width: 700px) {
  #login-signup {
    width: 300px;
  }
}
</style>
