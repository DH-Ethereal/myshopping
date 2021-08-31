<template>
  <div>
    <el-container>
      <el-header>
        <div class="header-logo" @click="showIndex()">
          殇何故图书集
        </div>
      </el-header>
      <el-main>
        <div class="showCar">
          <div>
            <div style="margin:10px;">
              <el-button type="danger" @click="delGoods(-1)" icon="el-icon-delete">删除选中的商品</el-button>
            </div>
            <div class="car-title">
              购物车
            </div>
          </div>
          <div class="carList">
            <el-table
                :data="carList"
                style="width: 100%"
                empty-text="购物车中没有商品"
                @selection-change="selectionHandler">
              <el-table-column type="selection" width="60" align="center">
              </el-table-column>
              <el-table-column
                  type="index"
                  label="序号"
                  width="100"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="book_name"
                  label="图书名"
                  width="180"
                  align="center">
              </el-table-column>
              <el-table-column
                  label="图书封面"
                  width="320"
                  align="center">
                <template slot-scope="scope">
                  <el-image :src="require('../assets/images/books/'+scope.row.book_image)">
                    <div slot="placeholder" class="image-slot">
                      加载中<span class="dot">...</span>
                    </div>
                  </el-image>
                </template>
              </el-table-column>
              <el-table-column
                  prop="book_price"
                  label="图书单价"
                  align="center">
              </el-table-column>
              <el-table-column
                  label="购买数量"
                  align="center">
                <template slot-scope="scope">
                  <el-input-number v-model="scope.row.car_count" :min="1"
                                   @change="handleChange(scope)"></el-input-number>
                </template>
              </el-table-column>
              <el-table-column
                  prop="total"
                  label="合计"
                  :formatter="formatMoney"
                  align="center">
              </el-table-column>
              <el-table-column
                  align="center"
                  label="操作">
                <template slot-scope="scope">
                  <el-button type="danger" plain icon="el-icon-delete" @click="delGoods(scope.row.book_id)">删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div style="margin:10px 10px;text-align:right">
            <label style="margin-right:10px;font-size:18px">总价:¥{{sum.toFixed(2)}}</label>
            <el-button type="danger" :disabled="sum==0" @click="dialogVisible=true">确认购买</el-button>
          </div>
          <div class="confrim">
            <el-dialog
                title="收货人信息"
                :visible.sync="dialogVisible"
                width="45%"
                :close-on-click-modal="false"
            >
              <h3>总价格:¥{{sum.toFixed(2)}}</h3>
              <span>
                    <el-form ref="form" :model="order">
                       <el-form-item label="收货人姓名">
                        <el-input v-model="order.order_to_name"></el-input>
                      </el-form-item>

                      <el-form-item label="收货人电话">
                        <el-input v-model="order.order_to_phone"></el-input>
                      </el-form-item>

                      <el-form-item label="收货人地址">
                        <el-input v-model="order.order_to_address"></el-input>
                      </el-form-item>


                      <el-button
                          type="primary"
                          @click="pay"
                          style="width: 100%; margin-top: 10px"
                      >购      买</el-button
                      >
                    </el-form>
                  </span>
            </el-dialog>
          </div>
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
      carList: [],
      book_ids: [],//复选框
      curUser:null,
      sum:0,
      dialogVisible:false,
      order:{
      }
    }
  },
  methods: {
    /**
     * 购买
     */
    pay(){
      this.order.order_total_price = this.sum;
      this.order.book_ids = this.book_ids;
      this.$axios.post('orders/addOrder',this.order)
          .then(response => {
            this.dialogVisible = false;
            this.$swal.fire({
              icon: "success",
              title: response.data.message,
              showConfirmButton: false,
              timer: 1500,
              willClose: () => {
                this.delServer(this.book_ids,JSON.parse(window.sessionStorage.getItem("curUser")).user_id)
                location.reload()
              },
            });
          })
          .catch(err=>{
            alert(err);
          });
    },
    showIndex() {
      this.$router.push("/");
    },
    readCarInfo() {
      //判断用户是否已登录
      if (this.curUser == null) {
        //未登录
        this.readCarInfoForLocal();
      } else {
        this.readCarInfoForServer(this.curUser.user_id);
      }
    },

    //本地
    readCarInfoForLocal() {
      let cars = window.localStorage.getItem("cars");
      //如果不存在则显示
      if (cars == null || cars === "") {
        this.carList = [];
        return;
      }
      cars = JSON.parse(cars);
      this.getCarInfo(cars);
    },

    getCarInfo(cars) {
      this.$axios.post('/car/queryCarInfo', cars)
          .then(response => {
            this.carList = response.data;
          })
          .catch(err => {
            console.log(err);
          })
    },

    //服务器端
    readCarInfoForServer(user_id) {
      this.$axios.get('car/readCarInfo',{params:{"user_id":user_id}})
          .then(response => {
            let cars = response.data;
            this.getCarInfo(cars);
          })
          .catch(err => {
            console.log(err);
          })
    },
    //格式化货币
    formatMoney(row, column, cellVal) {
      return "￥" + cellVal.toFixed(2);
    },
    //修改购物车的购买数量
    handleChange(scope) {
      let book_id = scope.row.book_id;
      let car_count = scope.row.car_count;
      //从sessionStorage中获取到当前登录者信息
      if (this.curUser == null) {
        //本地存储
        this.changeLocal(book_id, car_count);
      } else {
        this.changeServer(book_id,car_count,this.curUser.user_id);
      }
      scope.row.total = scope.row.book_price * car_count;
    },
    //修改本地数据
    changeLocal(book_id, car_count) {
      let carsObj = JSON.parse(window.localStorage.getItem("cars"));
      let cars = [];
      for (let carsObjKey in carsObj) {
        let carObj = carsObj[carsObjKey];
        if (carObj.book_id === book_id) {
          carObj.car_count = car_count
        }
        cars.push(carObj);
      }
      window.localStorage.setItem("cars", JSON.stringify(cars));
    },
    //修改服务器端存储
    changeServer(book_id, car_count,user_id) {
      this.$axios.put('car/updateCarCount',{book_id:book_id,car_count:car_count,user_id:user_id})
      // .then(response => {
      //
      // })
      // .catch(err => {
      //   console.log(err)
      // });
    },
    //设置当前选中的数据
    selectionHandler(selection) {
      this.book_ids = [];
      for (let book of selection) {
        this.book_ids.push(book.book_id);
        this.sum = this.sum + book.book_price * book.car_count;
      }
    },
    //删除当前购物车商品
    delGoods(book_id) {
      if (book_id === -1) {
        //判断是否有值
        if (this.book_ids.length === 0) {
          alert("请选择你要删除的数据!");
          return;
        }
      } else {
        this.book_ids = [];
        this.book_ids.push(book_id);
      }
      book_id = this.book_ids;

      this.$swal
          .fire({
            title: "确认删除吗?",
            text: "购物车商品删除后无法恢复!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "确认删除",
            cancelButtonText: "取消",
          })
          .then((result) => {
            if (result.isConfirmed) {
              //单击确认按钮
              let curUser = window.sessionStorage.getItem("curUser");
              if (curUser == null) {
                //删除本地
                this.delLocal(book_id);
              } else {
                curUser = JSON.parse(curUser);
                this.delServer(book_id,curUser.user_id);
              }
              this.$swal.fire({
                icon: "success",
                title: "删除成功",
                showConfirmButton: false,
                timer: 1500,
                willClose: () => {
                  this.carList = [];
                  //重新加载购物车数据
                  this.readCarInfo();
                },
              });
            }
          })
    },
    //本地删除
    delLocal(book_id) {
      let carsObj = JSON.parse(window.localStorage.getItem("cars"));
      let cars = [];
      for (let carsObjKey in carsObj) {
        let carObj = carsObj[carsObjKey];
        let exits = true;
        for (let id of book_id) {
          if (carObj.book_id === id) {
            exits = false;
            break;
          }
        }
        if (exits) {
          cars.push(carObj);
        }
      }
      window.localStorage.setItem("cars", JSON.stringify(cars));
    },
    //服务端删除
    delServer(book_id,user_id){
      this.$axios.delete('car/delCars', {params:{"book_ids": book_id,"user_id": user_id},
      paramsSerializer: params => {
        return this.$qs.stringify(params,{ indices : false})
      },})
    }

  },
  created() {
    let curUser = window.sessionStorage.getItem("curUser");
    if (curUser != null){
      curUser = JSON.parse(curUser);
      this.curUser = curUser;
    }
    this.readCarInfo();
  }
}
</script>
<style scoped>

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

.header-logo {
  width: 50%;
  height: 100%;
  font-size: 55px;
  font-weight: bold;
  font-family: 华文行楷;
  color: #183939;
  cursor: pointer;
}

.showCar > div:first-of-type {
  display: flex;
}

.car-title {
  width: 77%;
  text-align: center;
  line-height: 60px;
  font-size: 28px;
  font-family: '楷体', serif;
  font-weight: bold;
  letter-spacing: 2px;
}

.el-image {
  width: 100px;
}
</style>