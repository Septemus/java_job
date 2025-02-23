import { pages } from "@/pages.json";
/**
 * 得到所有的需要登录的pages，包括主包和分包的
 * 这里设计得通用一点，可以传递key作为判断依据，默认是 needLogin, 与 route-block 配对使用
 * 如果没有传 key，则表示所有的pages，如果传递了 key, 则表示通过 key 过滤
 */
export const getAllPages = (key: keyof (typeof pages)[number] = "needLogin") => {
  // 这里处理主包
  const mainPages = [
    ...pages
      .filter((page) => !key || page[key])
      .map((page) => ({
        ...page,
        path: `/${page.path}`,
      })),
  ];
  // 这里处理分包
  //   const subPages: any[] = [];
  //   subPackages.forEach((subPageObj) => {
  //     // console.log(subPageObj)
  //     const { root } = subPageObj;

  //     subPageObj.pages
  //       .filter((page) => !key || page[key])
  //       .forEach((page: { path: string } & Record<string, any>) => {
  //         subPages.push({
  //           ...page,
  //           path: `/${root}/${page.path}`,
  //         });
  //       });
  //   });
  const result = [...mainPages];
  // console.log(`getAllPages by ${key} result: `, result)
  return result;
};

/**
 * 得到所有的需要登录的pages，包括主包和分包的
 * 只得到 path 数组
 */
export const getNeedLoginPages = (): string[] => getAllPages("needLogin").map((page) => page.path);

/**
 * 得到所有的需要登录的pages，包括主包和分包的
 * 只得到 path 数组
 */
export const needLoginPages: string[] = getAllPages("needLogin").map((page) => page.path);
