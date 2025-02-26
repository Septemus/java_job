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
interface Job {
  id: number;
  companyName: string;
  cover: string;
  createTime: string;
  education: string;
  location: string;
  salary: string;
  tags: string[];
  title: string;
  workExpe: string;
  tagStr?: string;
}
const listApi = async (params: any) =>
  get<Job[]>({ url: URL.list, params: params, data: {}, headers: {} });
export { listApi, type Job };
