<template>

  <a-layout>
    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">

      <div id="tabler">
        <div id="input">
          想去的景点  <input placeholder="多个景点用空格分隔" v-model="attsWant">
          <br>
          <button @click="onSubmit()">提交</button>
        </div>

        <div id="output">
          <ul>
            <li v-for="it in items" v-bind:key="it">
              <a href="#" @click="showAtts(it)">{{it.name}}</a>
            </li>
          </ul>
        </div>
      </div>
      <div id="container"></div>

    </a-layout-content>
  </a-layout>

</template>

<script>
import AMap from 'AMap'
import axios from "axios";
export default {
  name: "AttractionsRecommend",
  mounted() {
    this.map = new AMap.Map('container', {
      resizeEnable: true,
      center:[112.982279,28.19409],
      zoom:11
    });
  },
  data() {
    return {
      attsWant : "",
      items : null,
      map:null
    }
  },
  methods:{
    onSubmit:function(){
      console.log(this.attsWant);
      if (this.attsWant=="") {
        alert("请输入至少一个景点");
        return;
      }
      var url = "/attraction/getattraction?attsWant="+this.attsWant;
      var that=this;
      axios.get(url)
          .then(function (response) {
            that.items = response.data.data;
            if (!that.items||that.items.length==0) {
              alert("抱歉！没有关联的景点推荐");
              return;
            }
            console.log("返回推荐的值" + that.items);
          })
          .catch(function (error) {
            console.log(error);
          });

      url = "/user/updateChartByAttsWant?attsWant="+this.attsWant;
      axios.get(url,{
        withCredentials: true
      }).then(function (response){
        console.log("更新用户画像" + response.data.data);
      }).catch(function (error){
        console.log("error");
      });
    },
    //把返回结果里的景点展示出来
    showAtts:function(it){
      this.map.clearMap();
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
    }
  }
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