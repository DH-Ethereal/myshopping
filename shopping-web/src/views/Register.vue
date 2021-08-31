<template>
  <div class="win-add-user" style="width: 40%;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);margin:auto; padding: 80px">

    <span style="font-size: 28px;font-weight: bold;font-family: 华文行楷;display: block;padding: 10px">用户注册</span>
    <span>
      <el-form ref="users" :model="users" :rules="rules" label-width="80px" >
        <el-form-item label="用户名" prop="user_name">
          <el-input v-model="users.user_name" placeholder="请输入用户名"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="user_password">
          <el-input placeholder="请输入密码" v-model="users.user_password" show-password></el-input>
        </el-form-item>

        <el-form-item label="E-mail">
          <el-input v-model="users.user_email" placeholder="请输入E-mail"></el-input>
        </el-form-item>

        <el-form-item label="出生日期">
          <el-date-picker
              v-model="users.user_birthday"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd"
              format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>


        <el-form-item label="爱好">
          <el-checkbox-group v-model="userHobbysArr" size="medium">
            <el-checkbox-button v-for="hobby in hobbyArr" :label="hobby"
                                :key="hobby">{{ hobby }}</el-checkbox-button>
          </el-checkbox-group>
        </el-form-item>

        <el-form-item label="性别">
          <el-radio v-model="users.user_sex" label="1">男</el-radio>
          <el-radio v-model="users.user_sex" label="0">女</el-radio>
        </el-form-item>

        <el-form-item label="地址">
          <el-input v-model="users.user_address" placeholder="请输入地址"></el-input>
        </el-form-item>

      </el-form>
    </span>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" style="width: 100%;font-size:16px" @click="addUser">注册</el-button>
    </span>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userHobbysArr: [],//存储用户选择爱好
      users: {},//user对象,JS对象中的属性可以任意
      hobbyArr: ['看电影', '睡觉', '逛街', '学习', '游戏'],
      rules:{
        user_name: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          // { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        user_password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    addUser() {
      //将userHobbysArr数组中的数据添加user对象中的userHobbys属性中
      let userHobbys = "";
      //将数组中的元素追加到userHobbys字符串中
      for (let hobbys of this.userHobbysArr) {
        userHobbys = userHobbys + "_" + hobbys;
      }

      //将userHobbys设置为user对象的一个属性
      this.users.user_hobbys = userHobbys.substring(1, userHobbys.length);//动态向user对象中添加一个属性
      this.$axios.post('user/addUser', this.users)
          .then(response => {
            //获得服务端回传的结果
            let result = response.data;
            if (result.success) {
              this.user = {};//重置user对象
              this.dialogVisible = false;//关闭添加用户弹出框
              this.$swal.fire({
                icon: 'success',
                title: result.message,
                showConfirmButton: false,
                timer: 1500,//显示时间
                willClose: () => {//在弹出层关闭后自动执行
                  this.$router.push("/login")
                }
              })

            } else {
              this.$swal.fire({
                icon: 'error',
                title: result.message,
                showConfirmButton: false,
                timer: 1500,//显示时间
                willClose: () => {//在弹出层关闭后自动执行
                  location.reload();
                }
              })
            }
          })
          .catch(err => {
            console.log(err);
          })
    },
  },
  created() {

  }
}
</script>

<style>

</style>