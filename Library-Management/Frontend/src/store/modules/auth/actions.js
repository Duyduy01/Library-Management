import SignupValidations from "@/services/SignupValidations";
import Axios from "axios";
import {
  // LOADING_SPINNER_SHOW_MUTATION,
  SET_USER_TOKEN_DATA_MUTATION,
  SIGNUP_ACTION,
  LOGIN_ACTION,
  LOGOUT_ACTION,
  AUTH_ACTION,
  AUTO_LOGIN_ACTION,
  AUTH2_ACTION,
} from "../../storeconstants";

export default {
  [LOGOUT_ACTION](context) {
    context.commit(SET_USER_TOKEN_DATA_MUTATION, {
      displayName: null,
      email: null,
      token: null,
      expiresIn: null,
      refreshToken: null,
      userId: null,
    });
    localStorage.removeItem("userData");
  },

  async [LOGIN_ACTION](context, payload) {
    return context.dispatch(AUTH_ACTION, {
      ...payload,
      url: `https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyAridRkKK2BEXgoGtkw_vQUmKUg0qQkrQI`,
    });
  },

  async [SIGNUP_ACTION](context, payload) {
    return context.dispatch(AUTH2_ACTION, {
      ...payload,
      url: `https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=AIzaSyAridRkKK2BEXgoGtkw_vQUmKUg0qQkrQI`,
    });
  },

  [AUTO_LOGIN_ACTION](context) {
    let userData = localStorage.getItem("userData");
    if (userData) {
      context.commit(SET_USER_TOKEN_DATA_MUTATION, JSON.parse(userData));
    }
  },

  async [AUTH_ACTION](context, payload) {
    let postData = {
      displayName: payload.displayName,
      email: payload.email,
      password: payload.password,
      returnSecureToken: true,
    };
    let response = "";

    try {
      response = await Axios.post(payload.url, postData);
    } catch (err) {
      let errorMessage = SignupValidations.getErrorMessageFromCode(
        err.response.data.error.errors[0].message
      );
      throw errorMessage;
    }

    if (response.status === 200) {
      let tokenData = {
        displayName: response.data.displayName,
        email: response.data.email,
        token: response.data.idToken,
        expiresIn: response.data.expiresIn,
        refreshToken: response.data.refreshToken,
        userId: response.data.localId,
      };
      localStorage.setItem("userData", JSON.stringify(tokenData));
      context.commit(SET_USER_TOKEN_DATA_MUTATION, tokenData);
    }
  },

  async [AUTH2_ACTION](context, payload) {
    let postData = {
      displayName: payload.displayName,
      email: payload.email,
      password: payload.password,
      returnSecureToken: true,
    };
    // let response = "";

    try {
      await Axios.post(payload.url, postData);
    } catch (err) {
      let errorMessage = SignupValidations.getErrorMessageFromCode(
        err.response.data.error.errors[0].message
      );
      throw errorMessage;
    }
  },
};
