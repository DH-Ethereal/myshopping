<template>
  <div class="index-container">
    <el-container>
      <el-header>
        <div class="win-header">
          <div class="header-logo" @click="showIndex()">
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
        <div class="desc-div">
          <h2 align="center">商品详情</h2>
          <div class="goodsDesc">
            <div class="baseInfo">
              <div class="photo">
                <el-image :src="require('../assets/images/books/'+book.book_image)">
                  <div slot="placeholder" class="image-slot">
                    加载中<span class="dot">...</span>
                  </div>
                </el-image>

              </div>
              <div class="bookInfo">
                <div class="book">
                  <p class="pl">书名:</p>
                  <p class="pInfo">{{ book.book_name }}</p>
                  <p class="pl">价格:</p>
                  <p class="pInfo">￥{{ book.book_price }}</p>
                </div>
                <div>
                  <p style="margin: 22px 0 14px 35px">
                    <el-input-number v-model="car.car_count" :min="1"></el-input-number>
                  </p>
                  <p style="margin-left:35px;">
                    <el-button type="success" round plain @click="addCar">
                      <span class="el-icon-shopping-cart-full" style="font-size:20px;">
                        加入购物车
                      </span>
                    </el-button>
                  </p>
                </div>
              </div>
            </div>
            <div class="bookDesc">
              <p>图书详情:</p>
              <span>{{ book.book_desc }}</span>
            </div>
            <div class="book-comment">
              <p>
                图书评论
                <el-button type="primary" plain :disabled="disable" size="mini" @click="dialogVisible=true">添加评论
                </el-button>
              </p>
              <div class="comment-info" v-for="commentView in commentList" :key="commentView.comment_id">
                <div class="comment-base-info">
                  <label style="margin-right:20px">用户:{{ commentView.user_name }}</label>
                  <p>{{ commentView.comment_message }}</p>
                  <label>时间:{{ commentView.comment_time }}</label>
                </div>
              </div>
              <div class="pagin">
                <el-pagination
                    small
                    background
                    layout="prev, pager, next,total"
                    :current-page.sync="paginParams.pageNum"
                    :page-size="paginParams.pageSize"
                    :total="total"
                    @current-change="queryCommentInfo(book.book_id)">
                </el-pagination>
              </div>

              <div class="win-addComment">
                <el-dialog
                    title="添加评论"
                    :visible.sync="dialogVisible"
                    width="45%"
                    :close-on-click-modal="false"
                >
                                          <span>
                                              <el-form ref="form" :model="comment" label-width="80px">
                                              <el-input
                                                  type="textarea"
                                                  :rows="5"
                                                  placeholder="请输入评论内容"
                                                  v-model="comment.comment_message">
                                                  </el-input>

                                                  <el-button type="primary" @click="addComment"
                                                             style="width:100%;margin-top:10px">添加评论</el-button>

                                              </el-form>
                                          </span>

                </el-dialog>
              </div>
            </div>
          </div>
        </div>
      </el-main>

      <el-footer>地址</el-footer>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      book: {},
      commentList: [],

      //分页
      paginParams: {
        pageNum: 1,//当前页
        pageSize: 5,//每页显示条目数
      },
      total: 0,//总条目数
      disable: false,//用于登录后该变量设置为false
      dialogVisible: false,//是否显示评论对话框
      comment: {},//评论对象

      //用户
      curUser: null,
      //购物车
      car: {
        car_count: 1,
      },
      car_count: 0,
      result: {
        success: false,
        message: "添加购物车失败"
      }
    }

  },
  methods: {
    //跳转购物车视图
    showCar() {
      //编程编程式路由跳转
      this.$router.push("/car/showCar");
    },
    showIndex() {
      this.$router.push("/");
    },
    //获得购物车中的商品的数量
    readCarCount() {
      if (this.curUser == null) {
        this.readCarCountForLocal();
      } else {
        this.readCarCountForServer();
      }
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
      this.$axios.get('car/readCarInfo', {params: {'user_id': this.curUser.user_id}})
          .then(response => {
            let carList = response.data;
            this.car_count = carList.length;
          })
          .catch(err => {
            console.log(err);
          })
    },

    //获取图书详情
    queryGoodsDesc(book_id) {
      this.$axios.get('book/queryBookById', {params: {'book_id': book_id}})
          .then(response => {
            this.book = response.data;
          })
          .catch(err => {
            console.log(err);
          })
    },

    //获取评论
    queryCommentInfo(book_id) {
      //将book_id设置为paginParams的属性
      this.paginParams.book_id = book_id;
      this.$axios.get('comment/queryCommentInfo',
          {params: this.paginParams})
          .then(response => {
            let map = response.data;
            this.total = map.total;
            this.commentList = map.commentList;
          })
          .catch(err => {
            console.log(err);
          })
    },
    init(book_id) {
      this.queryGoodsDesc(book_id);
      this.queryCommentInfo(book_id);
    },
    //添加评论
    addComment() {
      //设置图书编号
      this.comment.book_id = this.book.book_id;
      //设置用户编号
      if (this.curUser != null) {
        this.comment.user_id = this.curUser.user_id;
      }
      this.$axios.post('comment/addComment', this.comment)
          .then(response => {
            let result = response.data;
            if (result.success) {
              this.comment = {};
              this.dialogVisible = false;
              this.$swal.fire({
                icon: 'success',
                title: result.message,
                showConfirmButton: false,
                timer: 1500,
                willClose: () => {
                  this.init(this.book.book_id);
                }
              })
            }
          })
          .catch(() => {
            this.dialogVisible = false;
          })
    },
    //添加购物车
    addCar() {
      //将图书的编号设置为car的属性
      this.car.book_id = this.book.book_id;
      //判断用户是否登录，如果已登录，发送到服务端，如果未登录，放到本地存储
      if (this.curUser == null) {
        // console.log("未登录")
        //本地存储(local Storage)
        this.addCarForLocal();
      } else {
        // console.log("已登录")
        //服务端存储
        this.addCarForServer();
      }
    },

    returnResult(result) {
      if (result.success) {
        this.$swal.fire({
          icon: 'success',
          title: result.message,
          showConfirmButton: false,
          timer: 1500,
          willClose: () => {
            this.car.car_count = 1;
          }
        })
      } else {
        this.$swal.fire({
          icon: 'error',
          title: result.message,
          showConfirmButton: false,
          timer: 1500,
        })
      }
      this.readCarCount()
    },

    //本地存储购物车
    addCarForLocal() {
      //创建数组
      let cars = new Array();
      //获取本地存储的购物车
      let localCars = window.localStorage.getItem("cars");
      if (localCars == null) {
        //创建购物车
        cars.push(this.car);
      } else {
        //本地存储有购物车数据
        let localCarsObj = JSON.parse(localCars);
        let exits = true;//假设购物车不存在新添商品
        //将localCars转为数组
        for (let localCarsKey in localCarsObj) {
          let carObj = localCarsObj[localCarsKey];
          //判断购物车是否存在新添加图书，如果存在则数量更新
          if (carObj.book_id == this.car.book_id) {
            carObj.car_count = parseInt(carObj.car_count) + this.car.car_count;
            exits = false;//已存在
          }
          cars.push(carObj);
        }
        //判断exits是否true,若为true则为不存在新添加的商品
        if (exits) {
          cars.push(this.car);
        }
      }
      //将购物车car对象的数据存入到localStorage中
      window.localStorage.setItem("cars", JSON.stringify(cars));
      this.result.success = true;
      this.result.message = '已加入购物车';
      this.returnResult(this.result);
    },
    //服务器端
    addCarForServer() {
      this.$axios.post('car/addCar', this.car)
          .then(response => {
            this.result = response.data;
            this.returnResult(this.result);
          })
          .catch(err => {
            console.log(err);
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
    //获取index视图提交的参数
    let book_id = this.$route.query.book_id;
    this.init(book_id);
    //获取curUser
    let curUser = window.sessionStorage.getItem("curUser");
    if (curUser != null) {
      curUser = JSON.parse(curUser);
      this.car.user_id = curUser.user_id;
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

.el-footer {
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
  cursor: pointer;
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
}


.desc-div {
  width: 100%;
  border: 0px solid red;
}

.goodsDesc {

  width: 80%;
  margin: 0 auto;
  border: 0px solid blue;

}

.baseInfo {
  display: flex;
}

.baseInfo .photo {
  width: 50%;
  text-align: center;
  margin-bottom: 10px;
}

.bookInfo {
  width: 60%;
  display: flex;
}

.book {
  width: 35%;
}


.pl {
  width: 90%;
  height: 40px;
  line-height: 40px;
  letter-spacing: 4px;
  text-align: left;
  padding-left: 10px;
  color: #fff;
  background-color: #be4747;
}

.pInfo {
  height: 40px;
  line-height: 40px;
  text-align: left;
  padding-left: 10px;
}

.bookDesc > p {
  width: 100%;
  height: 40px;
  line-height: 40px;
  letter-spacing: 2px;
  padding-left: 10px;
  box-sizing: border-box;
  color: #fff;
  background-color: #be4747;
}

.bookDesc span {
  font-family: "华文中宋";
  display: inline-block;
  width: 95%;
  height: 40px;
  line-height: 30px;
  text-align: left;
  text-indent: 2em;
}

.book-comment > p {
  width: 100%;
  height: 40px;
  line-height: 40px;
  padding-left: 10px;
  box-sizing: border-box;
  color: #fff;
  background-color: darkgray;
  margin-top: 10px;
}

.comment-base-info {
  width: 100%;
  text-align: left;
  padding-left: 10px;
  background-color: moccasin;
  box-sizing: border-box;
  font-size: 14px;
  letter-spacing: 2px;
  border-bottom: 1px solid darkcyan;
}

.comment-base-info p {
  margin-left: -5px;
  padding-left: 30px;
  line-height: 35px;
  font-size: 18px;
  background-color: white;
  font-family: Avenir, Helvetica, Arial, sans-serif;
}

</style>