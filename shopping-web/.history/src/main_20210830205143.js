import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import axios from "axios";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import Swal from "sweetalert2";
import Qs from "qs";

Vue.config.productionTip = false;
Vue.use(ElementUI);

Vue.prototype.$axios = axios.create({
    baseURL: 'http://47.98.173.209:8081/',
    headers: {'X-Requested-With': 'XMLHttpRequest'},
    withCredentials: true,
})

Vue.prototype.$swal = Swal;
Vue.prototype.$qs = Qs;

//响应拦截器,当服务端向客户端响应时首先进入响应拦截器
Vue.prototype.$axios.interceptors.response.use((response) => {
    // console.log(response.status,"响应拦截器被执行...");
    // 对响应数据做点什么
    return response;
}, (error) => {
    //获得服务端响应的错误代码
    let code = error.response.status;
    if (code === 401) {
        Vue.prototype.$swal.fire({
            icon: 'error',
            title: '请登录后访问...',
            showConfirmButton: false,
            timer: 1500,
            willClose: () => {//当弹框关闭时自动执行
                router.push("/login");
            }
        });
    }
    return Promise.reject(error)
});

new Vue({
    router,
    render: (h) => h(App),
}).$mount("#app");
