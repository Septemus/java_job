<template>
  <view class="content">
    <view class="show-list-options"
      ><uni-segmented-control
        class="left"
        :current="current"
        :values="items"
        :styleType="'text'"
        activeColor="#1aad19"
        @clickItem="
          (e) => {
            current = e.currentIndex;
          }
        "
      ></uni-segmented-control>
      <view class="right"></view>
    </view>

    <view class="joblist">
      <view v-if="current === 0">
        <uni-card
          v-for="job in contentData.thingData"
          :title="job.title"
          :extra="job.salary"
          :subTitle="job.location"
          :thumbnail="job.cover"
        >
          <text class="uni-body">{{ job.companyName }}</text>
        </uni-card>
      </view>
      <view v-else> </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { listApi as listThingList } from "@/api/thing";
import { BASE_URL } from "@/store/constants";
const items = reactive(["最新", "推荐"]);
const current = ref(0);
onShow(() => {
  uni.switchTab({ url: "/pages/index/index" });
});
onMounted(() => {
  getThingList({});
});
const contentData = reactive({
  selectX: 0,
  selectTagId: -1,
  cData: [],
  selectedKeys: [],
  tagData: [],
  loading: false,

  selectTabIndex: 0,
  tabUnderLeft: 12,

  thingData: [],
  pageData: [],

  page: 1,
  total: 0,
  pageSize: 12,
});
const getThingList = (data) => {
  contentData.loading = true;
  listThingList(data)
    .then((res) => {
      debugger;
      contentData.loading = false;
      res.data.forEach((item, index) => {
        if (item.cover) {
          item.cover = BASE_URL + "/api/staticfiles/company/" + item.cover;
        }
      });
      console.log(res);
      contentData.thingData = res.data;
      contentData.total = contentData.thingData.length;
      // changePage(1);
    })
    .catch((err) => {
      console.log(err);
      contentData.loading = false;
    });
};
</script>

<style lang="scss">
page {
  background-color: #fafafa;
  width: 100%;
  height: 100%;
}
.content {
  background-color: white;
  .show-list-options {
    width: 100%;
    display: flex;
    .left {
      width: 40%;
      flex-grow: 0;
      flex-shrink: 0;
      .segmented-control {
        height: auto !important;
        .segmented-control__text {
          font-size: 4.25vw !important;
          line-height: 3 !important;
        }
      }
    }
    .right {
      flex-grow: 1;
    }
  }
}
</style>
