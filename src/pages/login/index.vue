<template>
  <view class="container">
    <view class="login-page pc-style">
      <img src="/static/logo.png" alt="logo" class="logo-icon" />
      <view class="mail-login" type="login">
        <view class="common-input">
          <!-- <img :src="" class="left-icon" /> -->
          <view class="input-view">
            <input
              v-model="pageData.loginForm.username"
              placeholder="请输入用户名"
              type="text"
              class="input"
            />
            <text class="err-view" />
          </view>
          <!---->
        </view>
        <view class="common-input">
          <!-- <img :src="PwdIcon" class="left-icon" /> -->
          <view class="input-view">
            <input
              v-model="pageData.loginForm.password"
              placeholder="请输入密码"
              password
              class="input"
            />
            <text class="err-view"></text>
          </view>
          <!--          <img src="@/assets/pwd-hidden.svg" class="right-icon">-->
          <!---->
        </view>
        <view class="next-btn-view">
          <button
            class="next-btn btn-active"
            type="primary"
            style="margin: 16px 0px"
            @click="handleLogin"
          >
            登录
          </button>
        </view>
      </view>
      <view class="operation">
        <navigator url="/pages/register/index" class="forget-pwd" style="text-align: left"
          >注册新帐号</navigator
        >
        <navigator class="forget-pwd" style="text-align: right">忘记密码？</navigator>
      </view>

      <checkbox-group
        class="agreement"
        @change="
          (e) => {
            agreement = !agreement;
          }
        "
      >
        <label>
          <checkbox :checked="agreement" />我已阅读并同意<navigator>《用户协议》</navigator
          ><navigator>《隐私政策》</navigator>
        </label>
      </checkbox-group>

      <button class="useWechat" type="primary" @tap="handleWechatLogin">微信登陆</button>
    </view>
  </view>
</template>

<script setup lang="ts">
import { useUserStore } from "@/store";
const agreement = ref(false);
const userStore = useUserStore();
const pageData = reactive({
  loginForm: {
    username: "",
    password: "",
  },
});
const forceAgreement = () => {
  return new Promise((resolve, rej) => {
    if (!agreement.value) {
      uni
        .showModal({
          title: "阅读并同意",
          content: "《用户协议》《隐私政策》",
          confirmText: "同意",
          cancelText: "取消",
        })
        .then((res) => {
          agreement.value = true;
          resolve(res.confirm);
        })
        .catch((err) => {
          rej("请先同意用户协议！");
        });
    } else {
      resolve(true);
    }
  });
};
const handleLogin = () => {
  forceAgreement()
    .then((res) => {
      return userStore.login({
        username: pageData.loginForm.username,
        password: pageData.loginForm.password,
      });
    })
    .then((res) => {
      loginSuccess();
      console.log("success==>", userStore.user_name);
      console.log("success==>", userStore.user_id);
      console.log("success==>", userStore.user_token);
    })
    .catch((err) => {
      console.log(err);
      uni
        .showToast({
          icon: "error",
          title: err.msg || "登录失败",
        })
        .catch((err) => {
          console.log(err);
        });
      // message.warn(err.msg || "登录失败");
    });
};

const loginSuccess = () => {
  // router.push({ name: "portal" });
  // message.success("登录成功！");
  const duration = 1500;
  uni
    .showToast({
      icon: "success",
      title: "登录成功！",
      duration,
    })
    .then(() => {
      return new Promise((res) => {
        setTimeout(res, duration);
      });
    })
    .then(() => {
      uni.switchTab({
        url: "/pages/index/index",
      });
    })
    .catch((err) => {
      console.log(err);
    });
};

const handleWechatLogin = () => {
  forceAgreement()
    .then(() => {
      return Promise.all([
        uni.getUserProfile({
          desc: "获取用户信息",
        }),
        uni.login({
          provider: "weixin",
        }),
      ]);
    })
    .then(([UserProfileData, loginRes]) => {
      console.log("获取用户信息和获取code成功！");
      return userStore.wxLogin({ code: loginRes.code, userProfileData: UserProfileData });
    })
    .then((res) => {
      loginSuccess();
      console.log("success==>", userStore.user_name);
      console.log("success==>", userStore.user_id);
      console.log("success==>", userStore.user_token);
    })
    .catch((err) => {
      console.log(err);
      uni
        .showToast({
          icon: "error",
          title: err.msg || "登录失败",
        })
        .catch((err) => {
          console.log(err);
        });
      // message.warn(err.msg || "登录失败");
    });
};
</script>
<style scoped lang="scss">
view {
  display: block;
}

.container {
  //background-color: #f1f1f1;
  //   background-image: url("../images/admin-login-bg.jpg");
  background-size: cover;
  object-fit: cover;
  height: 100vh;
  width: 100%;
  // display: flex;
  // justify-content: center;
  // align-items: center;
  .login-page {
    overflow: hidden;
    width: 100%;
    height: 100%;
    position: relative;
    .mail-login {
      margin: 0px 24px;

      .common-input {
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: start;
        -ms-flex-align: start;
        align-items: flex-start;
        margin-bottom: 20px;

        .left-icon {
          margin-right: 12px;
          width: 24px;
        }

        .input-view {
          -webkit-box-flex: 1;
          -ms-flex: 1;
          flex: 1;

          .input {
            font-weight: 500;
            font-size: 14px;
            color: $uni-text-color;
            height: 40px;
            line-height: 40px;
            border: none;
            padding: 0;
            display: block;
            width: 100%;
            letter-spacing: 1.5px;
          }

          .err-view {
            margin-top: 4px;
            height: 16px;
            line-height: 16px;
            font-size: 12px;
            color: $uni-color-error;
          }
        }
      }
      .next-btn {
        border-radius: 4px;
        font-size: 14px;
        font-weight: 500;
        height: 40px;
        line-height: 40px;
        text-align: center;
        width: 100%;
        outline: none;
        cursor: pointer;
      }
    }
    .operation {
      display: flex;
      flex-direction: row;
      margin: 0 24px;
      .forget-pwd {
        //text-align: center;
        display: block;
        overflow: hidden;
        flex: 1;
        margin: 0 auto;
        color: $uni-color-link;
        font-size: 14px;
        cursor: pointer;
      }
    }
    .logo-icon {
      display: block;
      margin: 60px auto;
      width: 48px;
      height: 48px;
    }

    button.useWechat {
      position: absolute;
      bottom: 40px;
      left: 50%;
      transform: translateX(-50%);
      width: 50%;
    }
    .agreement {
      margin-top: 40px;
      font-size: 13px;
      text-align: center;
      color: $uni-text-color-placeholder;
      navigator {
        display: inline;
        color: $uni-color-link;
      }
    }
  }
}

.new-content {
  position: absolute;
  left: 0;
  right: 0;
  margin: 80px auto 0;
  width: 980px;
}

// .pc-style {
//   position: relative;
//   width: 400px;
//   height: 464px;

// }

// .login-tab {
//   display: -webkit-box;
//   display: -ms-flexbox;
//   display: flex;
//   color: #1e1e1e;
//   font-size: 14px;
//   color: #1e1e1e;
//   font-weight: 500;
//   height: 46px;
//   line-height: 44px;
//   margin-bottom: 40px;
//   border-bottom: 1px solid #c3c9d5;

//   view {
//     position: relative;
//     -webkit-box-flex: 1;
//     -ms-flex: 1;
//     flex: 1;
//     text-align: center;
//     cursor: pointer;
//   }

//   .tabline {
//     position: absolute;
//     bottom: 0;
//     left: 0;
//     right: 0;
//     margin: 0 auto;
//     display: inline-block;
//     width: 0;
//     height: 2px;
//     background: $uni-color-link;
//     -webkit-transition: width 0.5s cubic-bezier(0.46, 1, 0.23, 1.52);
//     transition: width 0.5s cubic-bezier(0.46, 1, 0.23, 1.52);
//   }

//   tab-selected {
//     color: #1e1e1e;
//     font-weight: 500;
//   }

//   .mail-login,
//   .tel-login {
//     padding: 0 28px;
//   }
// }

button,
input,
select,
textarea {
  margin: 0;
  padding: 0;
  outline: none;
}
</style>
