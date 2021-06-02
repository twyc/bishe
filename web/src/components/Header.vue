<template>
  <a-layout-header>
    <div class="logo">旅游景点推荐</div>
    <a-menu
        theme="dark"
        mode="horizontal"
        v-model:selectedKeys="selectedKeys"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="/">
        <router-link to="/">
          首页
        </router-link>
      </a-menu-item>
      <a-menu-item key="/attractionsShow">
        <router-link to="/attractionsShow">
          景点展示
        </router-link>
      </a-menu-item>
      <a-menu-item key="/attractionsRecommend">
        <router-link to="/attractionsRecommend">
          景点推荐
        </router-link>
      </a-menu-item>
      <a-menu-item key="/RouteSearch">
        <router-link to="/RouteSearch">
          路线查询
        </router-link>
      </a-menu-item>

      <a-popconfirm
          title="确认退出登录?"
          ok-text="是"
          cancel-text="否"
          @confirm="logout()"
      >
        <a class="login-menu" v-show="user.id">
          <span>退出登录</span>
        </a>
      </a-popconfirm>
      <a class="login-menu" v-show="user.id">
        <span>您好：{{user.name}}</span>
      </a>
      <a class="login-menu" v-show="!user.id" @click="showLoginModal">
        <span>登录</span>
      </a>
    </a-menu>

    <a-modal
        title="登录"
        v-model:visible="loginModalVisible"
        :confirm-loading="loginModalLoading"
        @ok="login"
    >
      <a-form :model="loginUser" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
        <a-form-item label="登录名">
          <a-input v-model:value="loginUser.userName" />
        </a-form-item>
        <a-form-item label="密码">
          <a-input v-model:value="loginUser.password" type="password" />
        </a-form-item>
      </a-form>
    </a-modal>
  </a-layout-header>
</template>


<script lang="ts">
import { defineComponent, ref, computed } from 'vue';
import axios from 'axios';
import { message } from 'ant-design-vue';
import store from "@/store";

declare let b64Md5: any;
declare let KEY: any;

export default defineComponent({
  name : 'the-header',
  setup () {
    // 登录后保存
    const user = computed(() => store.state.user);

    // 用来登录
    const loginUser = ref({
      userName: "111",
      password: "111"
    });
    const loginModalVisible = ref(false);
    const loginModalLoading = ref(false);
    const showLoginModal = () => {
      loginModalVisible.value = true;
    };

    // 登录
    const login = () => {
      console.log("开始登录");
      loginModalLoading.value = true;
      console.log("loginUser.value.name = " + loginUser.value.userName.toString());
      console.log("loginUser.value.password = " + loginUser.value.password.toString());
      // loginUser.value.password = hexMd5(loginUser.value.password + KEY);
      loginUser.value.password = b64Md5(loginUser.value.password);
      console.log("after md5 loginUser.value = " + loginUser.value.toString());
      let params = new URLSearchParams();
      params.append('userName',loginUser.value.userName);
      params.append('password',loginUser.value.password);
      axios.post('/user/login', params).then((response) => {
        loginModalLoading.value = false;
        const data = response.data;
        console.log("/user/login response = ", response);
        console.log("/user/login data = ", data);
        if (data.status == "success") {
          loginModalVisible.value = false;
          message.success("登录成功！");
          store.commit("setUser", data.data.user);
        } else {
          message.error(data.status);
        }
      });
    };
    // 退出登录
    const logout = () => {
      console.log("退出登录开始");
      axios.get('/user/logout/' + user.value.token).then((response) => {
        const data = response.data;
        if (data.success) {
          message.success("退出登录成功！");
          store.commit("setUser", {});
        } else {
          message.error(data.message);
        }
      });
    };

    return {
      loginModalVisible,
      loginModalLoading,
      showLoginModal,
      loginUser,
      login,
      user,
      logout
    }
  }
});
</script>

<style>
.logo {
  width: 120px;
  height: 31px;
  /*background: rgba(255, 255, 255, 0.2);*/
  /*margin: 16px 28px 16px 0;*/
  float: left;
  color: white;
  font-size: 18px;
}
.login-menu {
  float: right;
  color: white;
  padding-left: 10px;
}
</style>