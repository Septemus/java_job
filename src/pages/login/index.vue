<template>
  <view class="container">
    <view class="login-page pc-style">
      <img src="/static/images/k-logo.png" alt="logo" class="logo-icon" />
      <view class="mail-login" type="login">
        <view class="common-input">
          <!-- <img :src="" class="left-icon" /> -->
          <view class="input-view">
            <input placeholder="请输入注册邮箱" type="text" class="input" />
            <text class="err-view" />
          </view>
          <!---->
        </view>
        <view class="common-input">
          <!-- <img :src="PwdIcon" class="left-icon" /> -->
          <view class="input-view">
            <input placeholder="请输入密码" type="password" class="input" />
            <text class="err-view"></text>
          </view>
          <!--          <img src="@/assets/pwd-hidden.svg" class="right-icon">-->
          <!---->
        </view>
        <view class="next-btn-view">
          <button class="next-btn btn-active" style="margin: 16px 0px">登录</button>
        </view>
      </view>
      <view class="operation">
        <navigator class="forget-pwd" style="text-align: left">注册新帐号</navigator>
        <navigator class="forget-pwd" style="text-align: right">忘记密码？</navigator>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts"></script>
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
  display: flex;
  justify-content: center;
  align-items: center;
  .login-page {
    overflow: hidden;
    width: 100%;
    height: 100%;

    .logo-icon {
      display: block;
      margin: 60px auto;
      width: 48px;
      height: 48px;
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

.login-tab {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  color: #1e1e1e;
  font-size: 14px;
  color: #1e1e1e;
  font-weight: 500;
  height: 46px;
  line-height: 44px;
  margin-bottom: 40px;
  border-bottom: 1px solid #c3c9d5;

  view {
    position: relative;
    -webkit-box-flex: 1;
    -ms-flex: 1;
    flex: 1;
    text-align: center;
    cursor: pointer;
  }

  .tabline {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    margin: 0 auto;
    display: inline-block;
    width: 0;
    height: 2px;
    background: #3d5b96;
    -webkit-transition: width 0.5s cubic-bezier(0.46, 1, 0.23, 1.52);
    transition: width 0.5s cubic-bezier(0.46, 1, 0.23, 1.52);
  }

  tab-selected {
    color: #1e1e1e;
    font-weight: 500;
  }

  .mail-login,
  .tel-login {
    padding: 0 28px;
  }
}

.mail-login {
  margin: 0px 24px;
}

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
      color: #1e1e1e;
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
      color: #f62a2a;
    }
  }
}

.next-btn {
  background: #3d5b96;
  border-radius: 4px;
  color: #fff;
  font-size: 14px;
  font-weight: 500;
  height: 40px;
  line-height: 40px;
  text-align: center;
  width: 100%;
  outline: none;
  cursor: pointer;
}

button {
  background: transparent;
  padding: 0;
  border-width: 0px;
}

button,
input,
select,
textarea {
  margin: 0;
  padding: 0;
  outline: none;
}

.operation {
  display: flex;
  flex-direction: row;
  margin: 0 24px;
}

.forget-pwd {
  //text-align: center;
  display: block;
  overflow: hidden;
  flex: 1;
  margin: 0 auto;
  color: #3d5b96;
  font-size: 14px;
  cursor: pointer;
}
</style>
