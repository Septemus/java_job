<template>
  <view class="preference">
    <uni-section
      class="header"
      title="编辑求职偏好"
      sub-title="为您精准匹配优质岗位"
      title-font-size="28px"
      sub-title-font-size="14px"
    ></uni-section>
    <view class="conditions">
      <uni-forms :model="formData" label-position="top">
        <uni-forms-item label="意向城市" name="cityList" @tap="toCities">
          <input
            type="text"
            placeholder-class="input-placeholder"
            :value="cityListStr"
            placeholder="请选择城市"
            :disabled="true"
          />
        </uni-forms-item>

        <uni-forms-item label="期望职位" name="tagList" @tap="">
          <input
            type="text"
            placeholder-class="input-placeholder"
            :value="formData.tagList.join(',')"
            placeholder="请选择职位"
            :disabled="true"
          />
        </uni-forms-item>
        <uni-forms-item label="到岗时间" name="arriveTime" @tap="">
          <input
            type="text"
            placeholder-class="input-placeholder"
            placeholder="任意"
            :disabled="true"
          />
        </uni-forms-item>
        <uni-forms-item label="求职性质" name="jobType"></uni-forms-item>
        <uni-forms-item label="实习要求" name="jobType"></uni-forms-item>
      </uni-forms>
    </view>
  </view>
</template>
<script lang="ts" setup>
type PreferenceForm = {
  tagList: number[];
  cityList: string[];
  arriveTime: number;
  jobType: number;
};
const formData = reactive<PreferenceForm>({
  tagList: [],
  cityList: [],
  arriveTime: 0,
  jobType: 0,
});
const cityListStr = computed(() => {
  return formData.cityList.toString();
});
const toCities = () => {
  uni.navigateTo({
    url: "/pages/cities/index",
  });
};
onLoad((options) => {
  if (options) {
    if (options.cities) {
      formData.cityList = JSON.parse(options.cities);
    }
  }
});
</script>
<style lang="scss" scoped>
.preference {
  width: 100%;
  height: 100vh;
  box-sizing: border-box;
  padding: 10px 16px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  .header {
    flex-grow: 0;
    flex-shrink: 0;
    margin-bottom: 48rpx;
    :deep(.uni-section) {
      .uni-section-header {
        padding: 0;
        .uni-section__content-title {
          padding: 30rpx 0;
          font-weight: bold;
        }
      }
    }
  }
  .conditions {
    flex-grow: 1;
    // background-color: $uni-color-error;
    flex-shrink: 0;
    :deep(.uni-forms) {
      .uni-forms-item {
        padding-bottom: 24px;
        border-bottom: solid 1px $uni-border-color;
        .uni-forms-item__label {
          font-size: $uni-font-size-lg !important;
        }
        .uni-forms-item__content {
          .input-placeholder {
            color: $uni-text-color;
            opacity: 1;
          }
          input {
            line-height: 36px;
            height: 36px;
            font-size: 24px;
            font-weight: bold;
            color: $uni-text-color;
          }
        }
      }
    }
  }
}
</style>
