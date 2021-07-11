<template>
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

    <div v-show="!user.id">
      <h1>
        右上角登录后使用
      </h1>
    </div>
  </div>
  <a-layout v-show="user.id">
    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
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
  </a-layout-content>
  </a-layout>
</template>

<script>
import {computed, defineComponent} from 'vue';
import AMap from 'AMap'
import store from "@/store";
import axios from "axios";

export default defineComponent({
  name: 'Home',
  setup() {
    const user = computed(() => store.state.user);
    return {
      user
    }
  },
  mounted() {
    if (this.user.id) {
      console.log("created: 已经登录");
      this.loadChart();
    } else {
      console.log("created: 未登录");
    }
  },
  watch: {
    user(newValue, oldValue) {
      if (newValue.id) {
        this.loadChart();
      }
    }
  },
  data() {
    return {
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
  },
  methods:{
    loadChart:function(){
      console.log("loadChart is in");
      let url = "/route/getRouteByChart?chart="+"111";
      var that = this;

      axios.get("/attraction/getAttractionsByChart?chart=111")
          .then(function (response) {
            console.log(response);
            that.atts = response.data.data.reverse();
          })
          .catch(function (error) {
            console.log(error);
          });

      axios.get(url)
          .then(function (response) {
            that.routes = response.data.data;
            console.log(that.routes);
          })
          .catch(function (error) {
            console.log(error);
          });
      this.recommend=true;
      this.map = new AMap.Map('container', {
        resizeEnable: true,
        center:[112.982279,28.19409],
        zoom:11
      });
      console.log("map has loaded: " + this.map.map);
    },
    fun:function(it){
      this.map.clearMap();
      var marker = new AMap.Marker({
        position: new AMap.LngLat(it.latitude,it.longitude),
        title: it.name
      });
      marker.setLabel({
        offset: new AMap.Pixel(20,20),
        content:it.name
      });
      // 将创建的点标记添加到已有的地图实例：
      this.map.add(marker);
      this.map.setFitView(marker);
    },
    showRoute:function(str){
      var loc = [];
      var arr = str.split("&");
      for(var i in arr){
        if (i==arr.length-1) {
          break;
        }
        var loca = arr[i].split(",");
        loc.unshift(new AMap.LngLat(Number(loca[0]),Number(loca[1])));
      }
      this.map.clearMap();
      var that=this;
      loc.forEach(function(it,index,loc){
        var tit=null;
        let marker = new AMap.Marker({
          position:it,
          title:tit
        });
        marker.setLabel({
          offset: new AMap.Pixel(20,20),
          content:tit
        });
        console.log(it);
        that.map.add(marker);
        if (index==0) {
          that.map.setFitView(marker);
        }
      })
      var line = new AMap.Polyline({
        path:loc,
        borderWeight:2,
        strokeColor:'red',
        lineJoin: 'round'
      });
      this.map.add(line);
    }
  }
});
</script>

<style>

#tabler{
  width: 20%;
  height: 100%;
  margin: auto;
  float: left;
}

#container{
  width: 80%;
  height: 830px;
}

</style>

