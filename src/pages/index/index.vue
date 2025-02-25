<template>
  <view class="content">
    <view class="top">
      <swiper class="swiper" circular :indicator-dots="true" :autoplay="true">
        <swiper-item v-for="i in swiperItems">
          <view class="swiper-item"
            ><img :src="BASE_URL + '/api/staticfiles/image/' + i" alt="" srcset=""
          /></view>
        </swiper-item>
      </swiper>
      <view class="quick-links">
        <view class="link"
          ><img class="icon" src="/static/images/wap_icon_qzzx.png" /><text class="title">
            春招早鸟</text
          ></view
        >
        <view class="link"
          ><img class="icon" src="/static/images/wap_icon_mqnt.png" /><text class="title">
            金融实习</text
          ></view
        >
        <view class="link"
          ><img class="icon" src="/static/images/wap_icon_hdzx.png" /><text class="title">
            大一大二</text
          ></view
        >
      </view>
      <view class="filter">
        <view class="major">Java开发</view>
        <view class="search">
          <input confirm-type="search" placeholder="🔎︎ 搜索职位/公司" />
        </view>
      </view>
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
    </view>

    <view class="joblist-wrapper">
      <view v-if="!contentData.loading" class="joblist">
        <uni-transition
          v-for="job in contentData.thingData"
          :key="job.id"
          :show="true"
          mode-class="slide-left"
        >
          <uni-card
            :title="job.title"
            :extra="job.salary"
            :subTitle="job.location"
            :thumbnail="job.cover"
            class="job"
          >
            <text class="uni-body">{{ job.companyName }}</text>
          </uni-card>
        </uni-transition>
      </view>
      <view v-else class="loading"
        ><uni-icons class="spinner" type="spinner-cycle" size="48"></uni-icons
      ></view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { listApi as listThingList } from "@/api/thing";
import { BASE_URL } from "@/store/constants";
import { IResponse } from "@/utils/http/axios/type";
const items = reactive(["最新", "推荐"]);
const current = ref(0);
onShow(() => {
  uni.switchTab({ url: "/pages/index/index" });
});
onMounted(() => {
  getThingList({});
});
const swiperItems = reactive(["swiperitem1.png", "swiperitem2.png", "swiperitem3.png"]);
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
    .then((res): Promise<IResponse<any>> => {
      return new Promise((resolve, rej) => {
        setTimeout(() => {
          contentData.loading = false;
          resolve(res);
        }, 1000);
      });
    })
    .then((res: IResponse<any>) => {
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
  height: 100%;
  .top {
    background-color: white;
    .swiper {
      margin: 0 auto;
      height: 330rpx;
      width: 90%;
      border-radius: 20px;
      border: solid 1px transparent;
      overflow: hidden;
      .swiper-item {
        display: block;
        height: 100%;
        image {
          width: 100%;
          height: 100%;
        }
      }
    }
    .quick-links {
      margin: 20rpx auto;
      display: flex;
      justify-content: space-around;
      .link {
        display: flex;
        flex-direction: column;
        align-items: center;
        .icon {
          display: block;
          width: 50px;
          height: 50px;
        }
        .title {
          color: #3a3a3a;
          font-size: 28rpx;
        }
      }
    }
    .filter {
      padding: 10rpx 40rpx;
      display: flex;
      align-items: center;
      .major {
        font-weight: bold;
        font-size: 35rpx;
        color: black;
        &::after {
          color: #909399;
          font-weight: normal;
          padding-left: 15rpx;
          content: ">";
        }
      }
      .search {
        padding-left: 25rpx;
        flex-grow: 1;
        input {
          display: block;
          padding: 20rpx;
          padding-right: 0;
          height: 75rpx;
          // margin-right: 25rpx;
          width: 100%;
          background-color: rgba(0, 0, 0, 0.03);
          border-radius: 30rpx;
        }
      }
    }
    .show-list-options {
      width: 100%;
      display: flex;
      .left {
        width: 40%;
        flex-grow: 0;
        flex-shrink: 0;
        .segmented-control {
          height: auto !important;
          .segmented-control__item {
            .segmented-control__text {
              font-size: 4.25vw !important;
              line-height: 3 !important;
            }
          }
        }
      }
      .right {
        flex-grow: 1;
      }
    }
  }

  .joblist-wrapper {
    min-height: 40vh;
    position: relative;
    .loading {
      position: absolute;
      width: 100%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      .spinner {
        animation: rotating 2s linear infinite;
      }
    }

    @keyframes rotating {
      0% {
        transform: rotate(0deg);
      }
      100% {
        transform: rotate(1turn);
      }
    }
  }
}
</style>
