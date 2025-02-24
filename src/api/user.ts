// 权限问题后期增加
import { type UserEssential } from "@/store/modules/user/types";
import { get, post } from "@/utils/http/axios";
import { type ILogin } from "@/utils/http/axios/type";
// import { UserState } from "@/store/modules/user/types";
// import axios from 'axios';
enum URL {
  login = "/api/user/login",
  userList = "/api/user/list",
  detail = "/api/user/detail",
  create = "/api/user/create",
  update = "/api/user/update",
  delete = "/api/user/delete",
  userLogin = "/api/user/userLogin",
  userRegister = "/api/user/userRegister",
  updateUserPwd = "/api/user/updatePwd",
  updateUserInfo = "/api/user/updateUserInfo",
  wxUserLogin = "/api/user/WxUserLogin",
  verify = "/api/user/verify",
}
// interface LoginRes {
//   token: string;
// }

export interface regData extends ILogin {
  rePassword: string;
}
export interface wxLoginData {
  code: string;
  userProfileData: UniApp.GetUserProfileRes;
}

const loginApi = async (data: ILogin) =>
  post<any>({
    url: URL.login,
    data,
    headers: { "Content-Type": "multipart/form-data;charset=utf-8" },
  });
const listApi = async (params: any) =>
  get<any>({ url: URL.userList, params: params, data: {}, headers: {} });
const detailApi = async (params: any) =>
  get<any>({ url: URL.detail, params: params, data: {}, headers: {} });
const createApi = async (data: any) =>
  post<any>({
    url: URL.create,
    params: {},
    data: data,
    headers: { "Content-Type": "multipart/form-data;charset=utf-8" },
  });
const updateApi = async (data: any) =>
  post<any>({
    url: URL.update,
    data: data,
    headers: { "Content-Type": "multipart/form-data;charset=utf-8" },
  });
const deleteApi = async (params: any) =>
  post<any>({ url: URL.delete, params: params, headers: {} });
const wxUserLoginApi = async (data: wxLoginData) => {
  return await post<UserEssential>({
    url: URL.wxUserLogin,
    data: {
      code: data.code,
      userInfo: data.userProfileData.userInfo,
    },
  });
};
const userLoginApi = async (data: ILogin) => {
  return await post<UserEssential>({
    url: URL.userLogin,
    data,
    headers: { "Content-Type": "multipart/form-data;charset=utf-8" },
  });
};
const userRegisterApi = async (data: regData) =>
  post<any>({
    url: URL.userRegister,
    params: {},
    data: data,
    headers: { "Content-Type": "multipart/form-data;charset=utf-8" },
  });
const updateUserPwdApi = async (params: any) =>
  post<any>({ url: URL.updateUserPwd, params: params });
const updateUserInfoApi = async (data: any) =>
  post<any>({
    url: URL.updateUserInfo,
    data: data,
    headers: { "Content-Type": "multipart/form-data;charset=utf-8" },
  });

const verifyApi = async (token: string) => {
  return await get<any>({
    url: URL.verify,
    params: {
      token,
    },
  });
};

export {
  loginApi,
  listApi,
  detailApi,
  createApi,
  updateApi,
  deleteApi,
  userLoginApi,
  userRegisterApi,
  updateUserPwdApi,
  updateUserInfoApi,
  wxUserLoginApi,
  verifyApi,
};
