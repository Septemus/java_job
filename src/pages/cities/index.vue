<template>
  <view class="citysIndex" v-if="update">
    <scroll-view :scroll-into-view="scrollTop" :scroll-y="true" style="height: 100%">
      <view class="citysHead">
        <uni-search-bar
          v-model="searchValue"
          placeholder="请输入城市名"
          :cancelButton="'none'"
          bgColor="#EEEEEE"
          @input="search"
        />
        <view class="selectedCities">
          <view class="citysTitle">
            <label>已选城市</label>
          </view>
        </view>
        <!-- 热门城市 -->
        <uni-list v-if="searchList.length > 0">
          <uni-list-item
            v-for="(item, index) in searchList"
            :key="index"
            :title="item"
            clickable
            @click="handelCityClickFn(item)"
          />
        </uni-list>
        <view v-if="!searchList.length" class="citysHot">
          <view class="citysTitle">
            <label>热门城市</label>
          </view>
          <view class="citysHot-list">
            <!-- <a v-for="(v,i) in hotCitys" @click="checkCity(v.name)"> :class="[q.checked?'checkedBtn':'']" -->
            <label
              v-for="(v, i) in hotCitys"
              :key="i"
              :class="[city_done === v ? 'checkedBtn' : '']"
              @click="checkCity(v)"
              >{{ v }}</label
            >
            <!-- </a> -->
          </view>
        </view>
      </view>
      <view v-if="!searchList.length" class="citysList">
        <!-- :class="[q.checked?'cityChecked':'']" -->
        <view class="citysTitle">
          <label>所有城市</label>
        </view>
        <view class="citysIndexList">
          <view class="van-index-bar">
            <template v-for="(v, i) in citys.cityList">
              <view :id="'city_' + v.title" class="listIndexTitle">
                <label>{{ v.title }}</label>
              </view>
              <view class="listIndexBoth">
                <view
                  v-for="(q, w) in v.cities"
                  :key="q"
                  class="listIndexText"
                  :class="[q === city_done ? 'checkedBtn' : '']"
                  @click="checkCity(q)"
                >
                  <label>{{ q }}</label>
                </view>
              </view>
            </template>
            <!-- 右边字母坐标 -->
            <view class="index-sidebar">
              <template v-for="(v, i) in alphabet">
                <view class="index-bar" @click="findCity(v)">{{ v }}</view>
              </template>
            </view>
          </view>
        </view>
      </view>
    </scroll-view>
    <view class="submit">
      <button type="primary">确定</button>
    </view>
  </view>
</template>

<script lang="ts">
import citys from "./city";
export default {
  props: {},
  data() {
    return {
      alphabet: [
        "A",
        "B",
        "C",
        "D",
        "E",
        "F",
        "G",
        "H",
        "I",
        "J",
        "K",
        "L",
        "M",
        "N",
        "O",
        "P",
        "Q",
        "R",
        "S",
        "T",
        "U",
        "V",
        "W",
        "X",
        "Y",
        "Z",
      ],
      hotCitys: [
        "全国",
        "北京",
        "上海",
        "成都",
        "广州",
        "重庆",
        "杭州",
        "西安",
        "南京",
        "武汉",
        "天津",
        "苏州",
      ],
      citys: citys,
      city_done: "",
      searchValue: "",
      searchList: [],
      indexList: [],
      cityData: {},
      update: true,
      scrollTop: "",
      prevpage: null,
      timerOut: null,
    };
  },
  computed: {
    // 获取除了热门城市以外数据
    allCityList() {
      return this.citys.cityList.reduce((accu, item) => {
        accu.push(...item.cities);
        return accu;
      }, [] as string[]);
    },
  },
  created() {
    // this.getLocation();
  },
  onShow() {
    let page = getCurrentPages(); //以数组形式按栈的顺序给出，第一个元素为首页，最后一个元素为当前页面
    // let currentPage = page[page.length - 1]; // 当前页面对象
    this.prevpage = page[page.length - 2]; //上一个页面对象
  },
  methods: {
    findCity(v: string) {
      console.log(v);
      this.scrollTop = "city_" + v;
    },
    checkCity(city: string) {
      this.city_done = city;
      this.handelCityClickFn(city);
    },
    search(e: string | number) {
      const that = this;
      if (this.timerOut) clearTimeout(this.timerOut);
      this.timerOut = setTimeout(function () {
        if (e) {
          debugger;
          const newData = that.allCityList.filter((item) => item.includes(e.toString()));
          that.searchList = newData.length > 0 ? newData : [];
        }
      }, 1000);
    },
    handelCityClickFn(val) {
      if (Object.keys(this.prevpage).length > 0) {
        uni.navigateTo({
          url: "/" + this.prevpage.route + "?item=" + JSON.stringify(val),
        });
      }
    },
    getLocation: function () {
      let that = this;
      uni.getLocation({
        type: "wgs84",
        geocode: true,
        success: function (res) {},
        fail: (err) => {
          console.log(err, "<<<<<<S");
        },
      });
    },
  },
};
</script>

<style lang="scss">
.citysIndex {
  width: 100%;
  // height: 100%;
  height: 100vh;
  .submit {
    position: fixed;
    bottom: 0;
    height: 11vh;
    width: 100%;
    padding: 30rpx;
    box-sizing: border-box;
    z-index: 999;
    background-color: $uni-bg-color;
    button {
      // width: 100%;
      border-radius: 45rpx;
    }
  }
}

.citysHead {
  padding: 0 36rpx;
}

.citysTitle {
  margin-top: 34rpx;
  font-size: 28rpx;
  font-weight: 600;
}

.citysTitle label:nth-child(2) {
  display: inline-block;
  font-weight: 500;
  margin-left: 40rpx;
}

/* 热门城市 */
.citysHot {
  width: 100%;
  padding-bottom: 20rpx;
  border-bottom: 2rpx solid #eee;
}

.citysHot-list {
  margin-top: 20rpx;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
}

.citysHot-list view {
  display: inline-block;
}

.citysHot-list label,
.listIndexText {
  display: inline-block;
  margin-right: 20rpx;
  margin-bottom: 20rpx;
  width: 28%;
  height: 60rpx;
  // background: rgba(237, 237, 237, 1);
  background: $uni-bg-color-grey;
  line-height: 60rpx;
  box-sizing: border-box;
  // border: 2px solid transparent;
  text-align: center;
  font-size: 24rpx;
  border-radius: 8rpx;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.citysHot-list view:nth-child(3n + 3) {
  margin-right: 0;
}

/* 所有城市 */
.citysList {
  padding: 0 36rpx;
}

.citysIndexList {
  position: relative;
}

.index-sidebar {
  display: flex;
  flex-direction: column;
  position: fixed;
  right: 36rpx;
  text-align: center;
  top: 50%;
  transform: translateY(-50%);
  -webkit-user-select: none;
  user-select: none;
}

.index-bar {
  font-size: 14px;
  color: #333;
  margin-bottom: 2px;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  line-height: 19px;
  text-align: center;
}

.index-bar:active {
  background-color: $uni-color-success;
  color: white;
  font-size: 14px;
}

.listIndexTitle {
  width: 100%;
  height: 80rpx;
  line-height: 80rpx;
  // border-bottom: 2rpx solid #eee;
  color: $uni-color-success;
  font-size: 18px;
}
.listIndexBoth {
  width: 100%;
  display: flex;
  align-items: center;
  flex-wrap: wrap;
}
// .listIndexText {
// 	// color: #666;

// }

.listIndexText label {
  display: block;
  width: 100%;
  height: 100%;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.checkedBtn {
  color: #000000;
  // font-weight: 600;
  border: 2rpx solid $uni-color-success;
}
.cityChecked {
  color: #1474ed;
}
</style>
