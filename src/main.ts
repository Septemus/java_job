import { createSSRApp } from "vue";
import App from "./App.vue";
import "uno.css";
import { setupStore } from "@/store";
import { routeInterceptor } from "./utils/interceptors/route";
export function createApp() {
  const app = createSSRApp(App);
  setupStore(app);
  app.use(routeInterceptor);
  return {
    app,
  };
}
