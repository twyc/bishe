import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/Home.vue'
import AttractionsShow from '../views/AttractionsShow.vue'
import AttractionsRecommend from '../views/AttractionsRecommend.vue'
import RouteSearch from '../views/RouteSearch.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/attractionsShow',
    name: 'AttractionsShow',
    component: AttractionsShow
  },
  {
    path: '/attractionsRecommend',
    name: 'AttractionsRecommend',
    component: AttractionsRecommend
  },
  {
    path: '/routeSearch',
    name: 'RouteSearch',
    component: RouteSearch
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
