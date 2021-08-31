<template>
  <div id="app-login">
    <div class="win-logoin" id="win-logoin-div">
      <div class="container" id="login-container">
        <div class="title">殇何故图书集</div>
        <div class="errMessage">{{errMessage}}</div>
        <div class="login-input">
          <label>账户：</label>
          <input type="text" v-model="user.username" />
        </div>
        <div class="login-input">
          <label>密码：</label>
          <input type="password" v-model="user.password" />
        </div>
        <div class="login-button">
          <button @click="login">登录</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data(){
    return{
      user: {},
      errMessage:''
    }
  },
  methods:{
    login(){
      //加载条
      const loading = this.$loading({
        lock: true,
        text: '登录中...',
        spinner: 'fa fa-spinner fa-pulse fa-3x fa-fw',
        background: 'rgba(0, 0, 0, 0.7)'
      });

      this.$axios.post('/login/login',this.$qs.stringify(this.user))
          .then(response => {
            loading.close();
            let result = response.data;
            if (result.success) {

              /***
               * 读取本地存储的购物数据，如果存在将这些数据一次性传入服务端，进行服务端存储
               */

              let cars = window.localStorage.getItem("cars");
              cars = JSON.parse(cars)
              for (let carsKey in cars) {
                cars[carsKey].user_id = result.data.user_id;
              }
              this.$axios.post('car/localAddCars',cars)
              window.localStorage.clear();

              //将当前登录者对象存入到sessionStorage中
              //sessionStorage中存放的数据都是字符串
              window.sessionStorage.setItem("curUser",JSON.stringify(result.data,{ indices: false }));
              setTimeout(()=>{
                //sweeteralter2
                this.$swal.fire({
                  icon: 'success',
                  title: result.message,
                  showConfirmButton: false,
                  timer: 1500,
                  willClose:()=>{//当弹框关闭时自动执行
                    this.$router.push("/");
                  }
                });
              },350);

            } else {//登录失败
              this.errMessage = result.message;
            }

          })
          .catch(err => {
            alert(err);
          })
    }
  }

}
</script>
<style scoped>
#app-login {
  width: 100%;
  height: 100%;
}
.win-logoin {
  position: absolute;
  top: 0px;
  bottom: 0px;
  margin: 0px;
  left: 0px;
  right: 0px;
  padding: 0px;
  display: flex;
  background-color: #b7b7b7;
  /*设置弹性布局*/
  flex-direction: row;
  /*设置水平方向*/
  justify-content: center;
  /*根据direction的方向设置居中*/
  align-items: center;
  /*根据direction的反方向设置居中*/
}

.container {
  width: 800px;
  height: 500px;
  border-radius: 5px;
  box-shadow: 0px 0px 10px 5px #838181;
  display: flex;
  background-color: ghostwhite;
  /*设置弹性布局*/
  flex-direction: column;
  /*列布局*/
  align-items: center;
}

.title {
  font-family: '华文行楷';
  font-size: 66px;
  font-weight: bold;
  color: #7c7777;
  text-shadow: 5px 5px 5px #a9a9a9;
  letter-spacing: 15px;
  margin: 50px 0;
}

.login-input {
  position: relative;
  width: 300px;
  height: 50px;
  border: 0px solid red;
  margin: 5px 0;
}

.login-input > label {
  box-sizing: border-box;
  position: absolute;
  border: 0px solid red;
  width: 65px;
  height: 50px;
  line-height: 50px;
  letter-spacing: 2px;
  padding-left: 10px;
  color: #666666;
}

.login-input > input {
  box-sizing: border-box;
  border-radius: 5px;
  border: 1px solid #dddddd;
  padding-left: 65px;
  outline: none;
  font-size: 18px;
  width: 100%;
  height: 100%;
}

.login-button {
  position: relative;
  width: 300px;
  height: 50px;
  margin: 15px 0;
}

.login-button > button {
  border: 0;
  width: 100%;
  height: 100%;
  border-radius: 5px;
  border: 1px solid #dddddd;
  outline: none;
  background-color: white;
  letter-spacing: 15px;
  padding-left: 15px;
  color: #666666;
  font-size: 18px;
}

.login-button > button:hover {
  cursor: pointer;
  background-color: cornflowerblue;
  color: white;
}

.login-button > button:active {
  font-weight: bold;
  outline: none;
  border: 0;
  box-shadow: 0px 0px 8px 1px #7088ff;
}


.errMessage{
  margin-bottom: 10px;
  color: red;
  font-size: 18px;
  letter-spacing: 2px;
}
</style>