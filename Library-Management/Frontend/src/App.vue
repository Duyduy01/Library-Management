<template>
  <the-loader v-if="showLoading"></the-loader>
  <div class="container">
    <div class="row">
      <div class="col-md-12">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { AUTO_LOGIN_ACTION } from "./store/storeconstants";
import { defineAsyncComponent } from "vue";

const TheLoader = defineAsyncComponent(() =>
  import(/* webpackChunkName: "Loader" */ "./components/TheLoader.vue")
);
export default {
  name: "App",
  computed: {
    ...mapState({
      showLoading: (state) => state.showLoading,
    }),
  },

  watch: {
    autoLogout(curValue, oldValue) {
      if (curValue && curValue != oldValue) {
        this.$router.replace("/login");
      }
    },
  },

  components: {
    TheLoader,
  },
  created() {
    this.$store.dispatch(`auth/${AUTO_LOGIN_ACTION}`);
  },
};
</script>
