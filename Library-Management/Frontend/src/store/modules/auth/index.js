import mutations from "./mutations";
import actions from "./actions";
import getters from "./getters";

export default {
  namespaced: true,
  state() {
    return {
      displayName: "",
      token: "",
      email: "",
      userId: "",
      refreshToken: "",
      expiresIn: "sdsds",
    };
  },
  mutations,
  getters,
  actions,
};
