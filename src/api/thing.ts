// 权限问题后期增加
import { get } from "@/utils/http/axios";
// import axios from 'axios';
enum URL {
  list = "/api/thing/list",
  create = "/api/thing/create",
  update = "/api/thing/update",
  delete = "/api/thing/delete",
  detail = "/api/thing/detail",
  listUserThing = "/api/thing/listUserThingApi",
}
const listApi = async (params: any) =>
  get<any>({ url: URL.list, params: params, data: {}, headers: {} });
export { listApi };
