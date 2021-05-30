import { createApp } from 'vue'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'

axios.defaults.baseURL = process.env.VUE_APP_SERVER;


createApp(App).use(Antd).use(store).use(router).mount('#app')

console.log("server url : " + process.env.VUE_APP_SERVER);