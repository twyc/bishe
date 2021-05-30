<template>
  <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
    <div>
      <div v-if="firstLogin">
        <h1>你感兴趣的标签</h1>
        <div class="pure-control-group">
          <input type="checkbox" v-model="charact1"> 人文
          <input type="checkbox" v-model="charact2"> 自然
          <input type="checkbox" v-model="charact3"> 美食
          <button @click="onSubmit()" type="button" class="pure-button pure-button-primary">提交</button>
        </div>
      </div>

      <div v-if="isLogin">
        <h1>
          右上角登录后使用
        </h1>
      </div>
    </div>

    <div v-show="recommend">
      <div id="tabler">
        <h2>推荐了解一下这些景点</h2>
        <ul style="font-size: 21px">
          <li v-for="it in atts" @click="fun(it)" v-bind:key="it">
            <a href="#">
              {{ it.name }}
            </a>
          </li>
        </ul>
        <h2>以及这些路线</h2>
        <ul>
          <li v-for="(it,index) in routes" v-bind:key="it">
            <a v-bind="{href:it.source}">第{{index+1}}条路线</a>：<a href="#" @click="showRoute(it.attsId)">{{it.attractions}}</a>
          </li>
        </ul>
      </div>
      <div id="container"></div>
    </div>
  </a-layout-content>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'Home',
  components: {

  },
  data() {
    return {
      name:"请登录",
      status:"login",//"sign up"
      charact1:"",
      charact2:"",
      charact3:"",
      firstLogin: false,
      recommend: false,
      isLogin: true,
      map:null,
      atts : null,
      routes:null
    }
  }
});
</script>

<style>
.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 160px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>

