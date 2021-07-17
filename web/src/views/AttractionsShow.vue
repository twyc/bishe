<template>

  <a-layout>
    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">

      <div id="tabler">
        <h2>景点列表</h2>
        <ul style="font-size: 18px">
          <li v-for="it in showItems" @click="clickAttraction(it)" v-bind:key="it">
            <a href="#">
              {{ it.name }}
            </a>
          </li>
        </ul>

        <a-pagination v-model:current="pageNo" :default-page-size="itemNum" v-bind:total="allItems.length" @change="pageList"/>

      </div>

      <div id="container"></div>

    </a-layout-content>
  </a-layout>

</template>

<script>
import AMap from 'AMap'
import axios from "axios";
export default {
  name: "AttractionsShow",
  data() {
    return {
          name:"请登录",
          status:"login",//"sign up"
          map:null,
          allItems : [],
          showItems : null,
          pageNo: 1,
          pages: 0,
          itemNum: 15
    }
  },
  mounted:function() {
    var url = "/attraction/list";
    var that = this;
    axios.get(url,{
      withCredentials: true
    }).then(function (response){
      that.allItems = response.data.data.reverse();
      that.pages = Math.ceil(that.allItems.length/that.itemNum);
      console.log("pages = " + that.pages);
      that.showItems = that.allItems.slice(that.itemNum*(that.pageNo-1)+1,that.itemNum*that.pageNo+1);
    }).catch(function (error){
      console.log("error");
    });
    that.map = new AMap.Map('container', {
      resizeEnable: true,
      center:[112.982279,28.19409],
      zoom:11
    });
    url = "/user/getUser";
    axios.get(url,{
      withCredentials: true
    }).then(function (response) {
          console.log(response);
          var serverStatus = response.data.status;
          if (serverStatus=="not login") {
            return;
          }
          let user = response.data.data;
          that.isLogin = false;
          that.name = user.name;
          that.status = "sign up";
        }).catch(function (error) {
          console.log(error);
        });
  },
  methods:{
    pageList:function(curPage) {
      //根据当前页获取数据
      this.pageNo = curPage;
      this.showItems = this.allItems.slice(this.itemNum*(this.pageNo-1)+1,this.itemNum*this.pageNo+1);
      console.log("当前页：" + this.pageNo);
    },
    clickAttraction:function(it){
      this.map.clearMap();
      console.log("当前点击的景点 " + it.webgeonameid);
      axios.get("/attraction/didClickAttraction?id="+it.webgeonameid,{
        withCredentials: true
      });
      var marker = new AMap.Marker({
        position: new AMap.LngLat(it.latitude,it.longitude),
        title: it.name
      });
      console.log(marker);
      console.log(this.map);
      marker.setLabel({
        offset: new AMap.Pixel(20,20),
        content:it.name
      });
      // 将创建的点标记添加到已有的地图实例：
      this.map.add(marker);
      this.map.setFitView(marker);

      //把这个景点的chart更新给user
      var url = "/user/updateChart?chart="+it.keywords;
      var that = this;
      axios.get(url,{
        withCredentials: true
      }).then(function (response){
        console.log(response.data.data);
      }).catch(function (error){
        console.log("error");
      });
    }
  },
}
</script>

<style scoped>

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