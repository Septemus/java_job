/**
 * 路由拦截，通常也是登录拦截
 * 可以设置路由白名单，或者黑名单，看业务需要选哪一个
 * 我这里应为大部分都可以随便进入，所以使用黑名单
 */
import { verifyApi } from "@/api/user";
import { getNeedLoginPages } from "./pages";
import { USER_TOKEN } from "@/store/constants";

// TODO Check
const loginRoute = "/pages/login/index";

export const isLogined = async (): Promise<boolean> => {
  const token = uni.getStorageSync(USER_TOKEN);
  try {
    const val = await verifyApi(token);
    return val.code === 200;
  } catch (err) {
    console.log("verify failed:", err);
    return false;
  }
};

// 黑名单登录拦截器 - （适用于大部分页面不需要登录，少部分页面需要登录）
const navigateToInterceptor: UniNamespace.InterceptorOptions = {
  // 注意，这里的url是 '/' 开头的，如 '/pages/index/index'，跟 'pages.json' 里面的 path 不同
  invoke(args: { url: string }) {
    // console.log(url) // /pages/route-interceptor/index?name=feige&age=30
    const { url } = args;
    const path = url.split("?")[0];
    let needLoginPages: string[] = [];
    // 为了防止开发时出现BUG，这里每次都获取一下。生产环境可以移到函数外，性能更好
    needLoginPages = getNeedLoginPages();
    const isNeedLogin = needLoginPages.includes(path);
    if (!isNeedLogin) {
      return Promise.resolve(args);
    }
    return isLogined()
      .then((hasLogin) => {
        if (hasLogin) {
          return args;
        }
        return Promise.reject({ msg: "verify token failed!" });
        // const redirectRoute = `${loginRoute}?redirect=${encodeURIComponent(url)}`;
        // // uni.navigateTo({ url: redirectRoute });
        // args.url = redirectRoute;
        // return args;
      })
      .catch(() => {
        return uni
          .reLaunch({
            url: loginRoute,
          })
          .then(() => {
            return Promise.reject({ msg: "verify token failed!" });
          });
      });
  },
  success() {},
};

export const routeInterceptor = {
  install() {
    uni.addInterceptor("navigateTo", navigateToInterceptor);
    uni.addInterceptor("reLaunch", navigateToInterceptor);
    uni.addInterceptor("redirectTo", navigateToInterceptor);
    uni.addInterceptor("switchTab", navigateToInterceptor);
  },
};
