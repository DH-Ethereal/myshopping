<template>
  <div class="index-container">
    <el-container>
      <el-header>
        <div class="win-header">
          <div class="header-logo">
            殇何故图书集
          </div>
          <div class="header-info">
            <div v-if="curUser == null">
              <el-link style="color: darksalmon">用户注册</el-link>
              <span style="padding: 0 4px 0 4px; color: #be4747">/</span>
              <el-link style="margin-right: 10px;color: moccasin" @click="login">用户登录</el-link>
            </div>
            <div v-else style="font-size: 17px;padding-right: 10px">
              欢迎您:<label>{{ curUser.user_name }}</label>
              <el-link style="font-size: 17px; color: #be4747;padding-left: 5px" @click="logout">退出</el-link>
            </div>
            <div>
              <el-button class="btn-car" @click="showCar" style="color: orange">
                <el-badge :value="car_count">
                  <i class="el-icon-shopping-cart-full" style="font-size: 22px;font-weight: bold;"></i>
                </el-badge>
                <label style="margin-left: 12px;">
                  我的购物车
                </label>

              </el-button>
            </div>
          </div>

        </div>
      </el-header>
      <el-main>
        <div class="index-goodsList">
          <el-row>
            <el-col style="margin-bottom:10px" :span="4" :offset="index%5==0?0:1" v-for="(goods,index) in booksList"
                    :key="goods.book_id">
              <el-card :body-style="{ padding: '18px 0 18px 0' }" shadow="hover">
                <el-tooltip class="item" effect="light" :content="goods.book_name" placement="top-start">
                  <router-link :to="{name: 'goodsDesc',query: {'book_id':goods.book_id}}">
                    <img :src="require('../assets/images/books/'+goods.book_image)" class="image">
                  </router-link>
                </el-tooltip>

                <div style="padding: 7px;">
                  <p>
                    <el-tooltip :content="goods.book_name" placement="top" effect="light">
                      <router-link :to="{name:'goodsDesc',query:{'book_id':goods.book_id}}" style="margin-left:-10px;text-decoration: none;color: #2c3e50">
                        {{ subBookName(goods.book_name) }}
                      </router-link>

                    </el-tooltip>

                    <label style="font-size: 19px;color: orange;margin-left:3px">¥{{ goods.book_price }}</label>
                  </p>
<!--                  <div class="bottom clearfix">-->
<!--                    <el-input-number v-model="num" size="mini" :min="1"-->
<!--                                     style="width: 100px;margin-right: 20px;margin-left: 0px"></el-input-number>-->
<!--                    <el-button type="success" plain icon="el-icon-shopping-cart-full" size="small">加入购物车</el-button>-->

<!--                  </div>-->
                </div>
              </el-card>
            </el-col>
          </el-row>
        </div>
      </el-main>
      <el-footer>版权归属@:dh.ethereal@gmail.com</el-footer>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      booksList: [],
      num: 1,
      car_count: 0,
      curUser: null
    };
  },
  methods: {
    initBooks() {
      this.$axios
          .get("/book/queryBooks")
          .then((response) => {
            this.booksList = response.data;
          })
          .catch((err) => {
            console.log(err)
          });
    },
    //截取字符串
    subBookName(bookName) {
      if (bookName.length > 12) {
        bookName = bookName.substring(0, 12);
      }
      return bookName;
    },
    //获得购物车中的商品的数量
    readCarCount() {
      if (this.curUser == null) {
        this.readCarCountForLocal();
      } else {
        this.readCarCountForServer();
      }
    },
    //跳转购物车视图
    showCar() {
      //编程编程式路由跳转
      this.$router.push("/car/showCar");
    },
    //从本地中读取购物车数量
    readCarCountForLocal() {
      let carsObj = JSON.parse(window.localStorage.getItem("cars"))
      let cars = [];
      for (let index in carsObj) {
        cars.push(carsObj[index]);
      }
      this.car_count = cars.length;
    },
    //从服务器端读取购物车数量
    readCarCountForServer() {
      this.$axios.get('car/readCarInfo',{params:{"user_id" : this.curUser.user_id}})
      .then(response => {
        let carList = response.data;
        this.car_count = carList.length;
      })
      .catch(err => {
        console.log(err)
      })
    },
    login() {
      this.$router.push("/login");
    },
    logout(){
      sessionStorage.clear();
      location.reload();
    }
  },
  created() {
    this.initBooks();
    let curUser = window.sessionStorage.getItem("curUser");
    if (curUser != null){
      curUser = JSON.parse(curUser);
      this.curUser = curUser;
    }
    this.readCarCount();
  }
}
</script>

<style scoped>

.btn-car {
  font-size: 16px;
  background-color: #fff;
  color: orange;
}

.el-header {
  background-color: #80a98f;
  padding-left: 10%;
  text-align: left;
  line-height: 60px;
}

.el-footer{
  background-color: #80a98f;
  line-height: 60px;
}

.win-header {
  width: 100%;
  height: 100%;
  display: flex;
}

.win-header > .header-logo {
  width: 50%;
  height: 100%;
  font-size: 55px;
  font-weight: bold;
  font-family: 华文行楷;
  color: #183939;
}

.win-header > .header-info {
  width: 50%;
  height: 100%;
  font-size: 20px;
  border: 0px solid red;
  display: flex;
  justify-content: flex-end;
  padding-right: 20px;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.btn-car:hover {
  border: 1px solid red;
  background-color: #ffffff;
  cursor: pointer;
}


.btn-car label:hover {
  cursor: pointer;
}

.el-main {
  background-color: #fff;
  color: #333;
  text-align: center;
}

.image {
  width: 180px;
  height: 190px;
}
</style>