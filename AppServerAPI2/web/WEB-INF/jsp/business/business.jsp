<%--
  Created by IntelliJ IDEA.
  User: pg243
  Date: 2017/5/26
  Time: 10:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <link rel="stylesheet" href="/static/new/style/common/index1.3.3.css">
  <style>
    * {
      margin: 0;
      padding: 0;
    }

    html, body, #app {
      height: 100%;
    }

    .main {
      height: 100%;
    }

    .header {
      height: 50px;
      display: flex;
      align-items: center;
      background: #fff;
      padding-left: 20px;
    }
  </style>
</head>
<body>
<div id="app">
  <el-row class="main">
    <el-col :span="3" style="height: 100%;">
      <el-menu default-active="2" class="el-menu-vertical-demo" theme="light" style="height: 100%;">
        <el-menu-item :index="index" v-for="(item ,index) in business " @click="getNowBu(index,item)">{{item.bu_name}}
        </el-menu-item>
      </el-menu>
    </el-col>
    <el-col :span="21">
      <el-row>
        <el-col class="header">
          <el-breadcrumb>
            <el-breadcrumb-item>设置</el-breadcrumb-item>
            <el-breadcrumb-item>事业部管理</el-breadcrumb-item>
          </el-breadcrumb>
        </el-col>
      </el-row>
      <el-row justify="end" type="flex" style="height: 100px;" align="middle">
        <el-button-group>
          <el-button @click="delBusiness" type="danger" icon="delete">删除事业部</el-button>
          <el-button @click="upBusiness" type="Warning" icon="edit">编辑事业部</el-button>
          <el-button @click="addBusinessVisible=true" type="success" icon="plus">添加事业部</el-button>
          <el-button @click="addShop" type="info" icon="plus">添加店铺</el-button>
        </el-button-group>
      </el-row>
      <el-row>
        <el-table :data="shopTableData" border max-height="300">
          <el-table-column label="店铺名称" prop="stores_name"></el-table-column>
          <el-table-column label="操作" prop="good_name">
            <template scope="scope">
              <el-button @click="delShop(scope.row)" type="danger" icon="delete">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
      <el-row type="flex" justify="end">
        <el-button @click="addPersonVisible=true" type="info" icon="plus">添加人员</el-button>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-table :data="personTableData" height="300" border>
            <el-table-column label="头像">
              <template scope="scope">
                <img :src="scope.row.head_pic" alt="" width="50" height="50">
              </template>
            </el-table-column>
            <el-table-column label="名称" prop="name"></el-table-column>
            <el-table-column label="电话号码" prop="phone"></el-table-column>
            <el-table-column label="操作">
              <template scope="scope">
                <el-button @click="delPerson(scope.row)" type="danger" icon="delete">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </el-col>
  </el-row>
  <%--添加店铺弹窗--%>
  <el-dialog :visible.sync="addShopVisible" title="添加店铺">
    <el-table :data="addShopTableData" @selection-change="selectShop">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="店铺名称" prop="name"></el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button @click="addShopVisible = false">取 消</el-button>
      <el-button type="primary" @click="confirmAddShop" type="info">确 定</el-button>
    </div>
  </el-dialog>
  <el-dialog :visible.sync="addPersonVisible" title="添加人员">
    <el-row>
      <el-col :span="8">
        <el-input type="number" placeholder="请输入电话号码查询" v-model="phone"></el-input>
      </el-col>
      <el-col :span="8">
        <el-button @click="getUser" type="info" icon="search">点击查询</el-button>
      </el-col>
    </el-row>
    <el-table :data="personData">
      <el-table-column label="头像">
        <template scope="scope">
          <img :src="scope.row.head_pic" alt="" width="50" height="50">
        </template>
      </el-table-column>
      <el-table-column label="昵称" prop="name"></el-table-column>
      <el-table-column label="号码" prop="phone"></el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button @click="addPersonVisible = false">取 消</el-button>
      <el-button type="primary" @click="confirmAddPerson" type="info">确 定</el-button>
    </div>
  </el-dialog>
  <el-dialog :visible.sync="addBusinessVisible" title="添加事业部" size="tiny">
    <el-form :rules="businessFormRule" ref="businessForm" class="demo-ruleForm" :model="businessForm">
      <el-form-item label='事业部名称' prop="bu_name">
        <el-input placeholder="请输入事业部名称" v-model="businessForm.bu_name"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="addBusiness('businessForm')" v-if="bu_status===0" type="info" icon="plus">
          立即添加
        </el-button>
        <el-button type="primary" @click="editBusiness('businessForm')" v-else type="info" icon="edit">确认编辑</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</div>
<script src="/static/new/script/common/jquery.min.js"></script>
<script src="/static/new/script/common/vue2.3.3.min.js"></script>
<script src="/static/new/script/common/index1.3.3.js"></script>
<script>
  var vm = new Vue({
    el: '#app',
    data () {
      return {
//        personTableData: [],
        business: [],
//        shopTableData: [],
        addShopTableData: [],
        addShopVisible: false,
        addPersonVisible: false,
        addBusinessVisible: false,
        bu_status: 0,
        phone: '',
        businessForm: {
          bu_name: ''
        },
        businessFormRule: {
          bu_name: [
            {required: true, message: '请输入活动名称', trigger: 'blur'},
          ],
        },
        nowBusiness: {},
        selectedShop: [],
        personData: [],
        currentIndex: 0
      }
    },
    created () {
      this.getBusiness()
    },
    methods: {
      /**-------------------事业部增删改查------------------------***/
      getBusiness () {
        $.post('/ErpApi/business/sel', (res) => {
//          console.log(res)
          if (res.status === '1') {
            res = res.obj
            this.business = res
          }
        })
      },
      addBusiness (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            $.post('/ErpApi/business/add', {bu_name: this.businessForm.bu_name}, (res) => {
              console.log(res)
              if (res.status === '1') {
                this.addBusinessVisible = false
                this.getBusiness()
              }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      delBusiness () {
        this.$confirm('此操作将永久删除该事业部, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          $.post('/ErpApi/business/del', {bu_id: this.nowBusiness.bu_id}, (res) => {
            if (res.status === '1') {
              console.log(res)
              this.getBusiness()
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      upBusiness () {
        this.businessForm.bu_name = this.nowBusiness.bu_name
        this.addBusinessVisible = true
        this.bu_status = 1
      },
      editBusiness (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            $.post('/ErpApi/business/up', {
              bu_name: this.businessForm.bu_name,
              bu_id: this.nowBusiness.bu_id
            }, (res) => {
              console.log(res)
              if (res.status === '1') {
                this.addBusinessVisible = false
                this.getBusiness()
              }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      /****------------------------店铺增删改查--------------------------------****/
      addShop () {
        if (!this.nowBusiness.bu_id) {
          alert('请选择事业部')
          return false
        }
        this.getShop()
        this.addShopVisible = true
      },
      getShop() {
        $.post('/ErpApi/userAndShop/getShops', (res) => {
          console.log(res)
          this.addShopTableData = res.obj
        })
      },
      confirmAddShop () {
        let list = []
        for (let i = 0; i < this.selectedShop.length; i++) {
          list.push({stores_id: this.selectedShop[i].stores_id, bu_id: this.nowBusiness.bu_id})
        }
        list = JSON.stringify(list)
        console.log(list)
        $.post('/ErpApi/userAndShop/addUsersOrShops', {shops: list}, (res) => {
          console.log(res)
          if (res.status === '1') {
            this.addShopVisible = false
            this.$message({
              type: 'success',
              message: '添加店铺成功!'
            });
            this.getBusiness()
          }
        })
      },
      selectShop (val) {
        this.selectedShop = val
      },
      delShop (shop){
        this.$confirm('此操作将永久删除该店铺, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let list = []
          list.push({bsl_id: shop.bsl_id})
          list = JSON.stringify(list)
          $.post('/ErpApi/userAndShop/delUsersOrShops', {shops: list}, (res) => {
            console.log(res)
            if (res.status === '1') {
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.getBusiness()
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },

      /****-------------------------------人员---------------------------------------****/
      getUser () {
        if (!this.nowBusiness.bu_id) {
          alert('请选择事业部')
          return false
        }
        for (let i = 0; i < this.personTableData.length; i++) {
          if (this.personTableData[i].phone === this.phone) {
            alert('手机号重复')
            return false
          }
        }
        $.post('/ErpApi/userAndShop/getUser', {phone: this.phone}, (res) => {
          console.log(res)
          if (res.status === '1') {
            this.personData.push(res.obj)
          }
        })
      },
      confirmAddPerson () {
        let list = []
        list.push({
          bu_id: this.nowBusiness.bu_id,
          member_id: this.personData[0].member_id
        })
        list = JSON.stringify(list)
        $.post('/ErpApi/userAndShop/addUsersOrShops', {users: list}, (res) => {
          if (res.status === '1') {
            this.getBusiness()
            this.addPersonVisible = false
          }
          console.log(res)
        })
      },
      delPerson (per) {
        this.$confirm('此操作将永久删除该人员, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let list = []
          list.push({bsl_id: per.bsl_id})
          list = JSON.stringify(list)
          console.log(list)
          $.post('/ErpApi/userAndShop/delUsersOrShops', {users: list}, (res) => {
            console.log(res)
            if (res.status === '1') {
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.getBusiness()
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      /***--------------------------当前事业部----------------------------------------------***/
      getNowBu (index, item) {
        this.currentIndex = index
//        this.shopTableData = this.business[index].stores
//        this.personTableData = this.business[index].users
//        console.log(this.shopTableData)
        this.nowBusiness = item
//        console.log(this.personTableData)
      }
    },
    computed: {
      shopTableData () {
        if (!this.business[this.currentIndex].stores) {
          return []
        }
        return this.business[this.currentIndex].stores
      },
      personTableData () {
        return this.business[this.currentIndex].users
      }
    }
  })
</script>
</body>
</html>
