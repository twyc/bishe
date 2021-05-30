<template>
  <a-layout class="layout">
    <Header></Header>
    <router-view/>
  </a-layout>
</template>
<script>
import { defineComponent, ref } from 'vue';
import Header from "@/components/Header";
export default defineComponent({
  components: {Header},
  setup() {
    return {
      selectedKeys: ref(['2']),
    };
  },
  data() {
    return {
      contentStyle : {
        background: '#fff',
        padding: '24px',
        margin: 0,
        minHeight: '280px'
      }
    }
  }
});
</script>
<style>
#container {
  width: 80%;
  min-height: 600px;
  /*height: fit-content;*/
  /*height: 1080px;*/
  /*height: 100%;*/
}

</style>