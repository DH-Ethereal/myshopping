import Vue from "vue";
import VueRouter from "vue-router";
import Index from "../views/index"
import GoodsDesc from "../views/goodsDesc"
import ShowCar from "../views/showCar";
import Login from "../views/login";
import Register from "@/views/Register";

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'index',
    component: Index,
  },{
    // path:'/goodsDesc/:book_id',//:book_id参数占位符,取值时使用this.$router.params.book_id
    path:'/goodsDesc',
    name:'goodsDesc',
    component:GoodsDesc
  },
  {
    path: '/car/showCar',
    name: 'showCar',
    component: ShowCar
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: "/register",
    name: "register",
    component: Register
  }
];

const router = new VueRouter({
  routes,
});

export default router;
