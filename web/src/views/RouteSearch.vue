<template>

  <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
    <div id="tabler">
      <div id="input">
        想去的景点  <input placeholder="多个景点用空格分隔" v-model="attsWant">
        <br>
        <button @click="onSubmit()">提交</button>
      </div>

      <div id="output">
        <ul>
          <li v-for="(it,index) in items" v-bind:key="it">
            <a v-bind="{href:it.source}">第{{index+1}}条路线</a>：<a href="#" @click="showRoute(it.attsId)">{{it.attractions}}</a>
          </li>
        </ul>
      </div>
    </div>
    <div id="container"></div>
  </a-layout-content>

</template>

<script>
import AMap from 'AMap'
import axios from "axios";
export default {
  name: "RouteSearch",
  data() {
    return {
      attsWant : "",
      items : null,
      map : null
    }
  },
  mounted() {
    // console.log("did mounted " + this);
    this.map = new AMap.Map('container', {
      resizeEnable: true,
      center:[112.982279,28.19409],
      zoom:11
    });
    console.log("did mount map " + this.map);
  },
  methods:{
    onSubmit:function(){
      console.log(this.attsWant);
      console.log("map = " + this.map);
      if (this.attsWant=="") {
        alert("请输入至少一个景点");
        return;
      }
      var url = "/route/getRoute?attsWant="+this.attsWant;
      var that=this;
      axios.get(url)
          .then(function (response) {
            that.items = response.data.data;
            if (that.items.length==0) {
              alert("抱歉！没有满足要求的景点");
              return;
            }
            console.log(that.items);
          })
          .catch(function (error) {
            console.log(error);
          });

      url = "/user/updateChartByAttsWant?attsWant="+this.attsWant;
      axios.get(url,{
        withCredentials: true
      }).then(function (response){
        console.log(response.data.data);
      }).catch(function (error){
        console.log("error");
      });
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
      for (let it of loc) {
        var tit=null;
        var marker = new AMap.Marker({
          position:it,
          title:tit
        });
        marker.setLabel({
          offset: new AMap.Pixel(20,20),
          content:tit
        });
        this.map.add(marker);
      }
      var line = new AMap.Polyline({
        path:loc,
        borderWeight:2,
        strokeColor:'red',
        lineJoin: 'round'
      });
      this.map.add(line);
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