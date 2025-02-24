import { defineStore } from "pinia";
import {
  loginApi as adminLogin,
  userLoginApi,
  type wxLoginData,
  wxUserLoginApi,
  type regData,
} from "@/api/user";
// import { setToken, clearToken } from "/@/utils/auth";
import { type UserEssential, type UserState } from "./types";
import {
  USER_ID,
  USER_NAME,
  USER_TOKEN,
  ADMIN_USER_ID,
  ADMIN_USER_NAME,
  ADMIN_USER_TOKEN,
} from "@/store/constants";
import { type ILogin } from "@/utils/http/axios/type";
import { userRegisterApi } from "@/api/user";
export const useUserStore = defineStore("user", {
  state: (): UserState => ({
    user_id: undefined,
    user_name: undefined,
    user_token: undefined,

    admin_user_id: undefined,
    admin_user_name: undefined,
    admin_user_token: undefined,
  }),
  getters: {},
  actions: {
    setToken(data: UserEssential) {
      this.$patch((state) => {
        state.user_id = data.id;
        state.user_name = data.username;
        state.user_token = data.token;
        console.log("state==>", state);
      });
      uni.setStorageSync(USER_TOKEN, data.token);
      uni.setStorageSync(USER_ID, data.id);
    },
    //注册
    async register(regForm: regData) {
      const result = await userRegisterApi(regForm);
      console.log("result==>", result);
      if (result.code === 200) {
        this.setToken(result.data);
      }

      return result;
    },
    //微信登陆
    async wxLogin(wxLoginForm: wxLoginData) {
      const result = await wxUserLoginApi(wxLoginForm);
      console.log("result==>", result);
      if (result.code === 200) {
        this.setToken(result.data);
      }

      return result;
    },
    // 用户登录
    async login(loginForm: ILogin) {
      const result = await userLoginApi(loginForm);
      console.log("result==>", result);

      if (result.code === 200) {
        this.setToken(result.data);
      }

      return result;
    },
    // 用户登出
    async logout() {
      // await userLogout();
      this.$patch((state) => {
        // localStorage.removeItem(USER_ID);
        // localStorage.removeItem(USER_NAME);
        // localStorage.removeItem(USER_TOKEN);
        uni.setStorageSync(USER_ID, null);
        uni.setStorageSync(USER_NAME, null);
        uni.setStorageSync(USER_TOKEN, null);

        state.user_id = undefined;
        state.user_name = undefined;
        state.user_token = undefined;
      });
    },

    // 管理员登录
    async adminLogin(loginForm: ILogin) {
      const result = await adminLogin(loginForm);
      console.log("result==>", result);

      if (result.code === 200) {
        this.$patch((state) => {
          state.admin_user_id = result.data.id;
          state.admin_user_name = result.data.username;
          state.admin_user_token = result.data.token;
          console.log("state==>", state);
        });

        localStorage.setItem(ADMIN_USER_TOKEN, result.data.token);
        localStorage.setItem(ADMIN_USER_NAME, result.data.username);
        localStorage.setItem(ADMIN_USER_ID, result.data.id);
      }

      return result;
    },
    // 管理员登出
    async adminLogout() {
      // await userLogout();
      this.$patch((state) => {
        localStorage.removeItem(ADMIN_USER_ID);
        localStorage.removeItem(ADMIN_USER_NAME);
        localStorage.removeItem(ADMIN_USER_TOKEN);

        state.admin_user_id = undefined;
        state.admin_user_name = undefined;
        state.admin_user_token = undefined;
      });
    },
  },
});
