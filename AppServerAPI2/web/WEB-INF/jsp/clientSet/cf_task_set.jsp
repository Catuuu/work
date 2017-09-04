<%--
  Created by IntelliJ IDEA.
  User: pg243
  Date: 2017/4/24
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/inc/tld.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/ui.jsp" %>
<html>
<head>
  <meta name="viewport" content="width=device-width"/>
  <title>厨房平板设置</title>
  <link href="https://cdn.bootcss.com/element-ui/1.2.9/theme-default/index.css" rel="stylesheet">
  <style>
    html, body, #app, .el-menu {
      height: 100%;
    }

    .el-menu li {
      height: 50px;
    }

    .search-bar {
      height: 50px;
      line-height: 50px;
      margin-bottom: 10px;
    }
    .title {
      font-size: 20px;
      display: inline-block;
      height: 80px;
      color: #333;
      border-bottom: 2px solid #c30d22;
    }
  </style>
  <script>

  </script>
</head>
<body>
<div id="app">
  <el-row style="height: 100%">
    <el-col :span="4" style="margin-right: 10px; height: 100%">
      <el-menu default-active="2" class="el-menu-vertical-demo"
               theme="light" style="height: 100%;width: 200px">
        <el-menu-item v-for="(item,index ) in options" :index="item.stores_id.toString()" @click="chooseShop(item)">
          {{item.name}}
        </el-menu-item>
      </el-menu>
    </el-col>
    <el-col :span="16">
      <el-row style="height: 90px;line-height: 90px;" type="flex" justify="space-between">
        <el-col :span="4">
          <div>
            <span class="title">厨房平板任务机</span>
          </div>
        </el-col>
        <el-col :span="6" style="text-align: right;">
          <el-button type="primary" @click="add" :offset="20" :span="4" icon="plus">添加平板任务机</el-button>
        </el-col>
      </el-row>
      <el-table :data="tableData" stripe style="width: 100%" max-height="600" border>
        <el-table-column prop="es_id" label="ID" width="70"></el-table-column>
        <el-table-column prop="ct_name" label="平板名称" width="180"></el-table-column>
        <el-table-column prop="ct_user" label="平板账户" width="180"></el-table-column>
        <el-table-column prop="print_ip" label="标签打印机IP" ></el-table-column>

        <%--<el-table-column prop="status" label="状态"></el-table-column>--%>
        <el-table-column label="操作" width="180">
          <template scope="scope">
            <el-button size="small" @click="Edit(scope.$index, scope.row)" type="warning" icon="edit">编辑
            </el-button>
            <el-button size="small" @click="openMeal(scope.$index, scope.row)" type="success"
                       v-show="scope.row.status===0">开启
            </el-button>
            <el-button size="small" @click="closeMeal(scope.$index, scope.row)" type="warning"
                       v-show="scope.row.status===1">关闭
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-col>
  </el-row>
  <el-row style="height: 100%">
    <el-col :span="4" style="margin-right: 10px; height: 100%">
    </el-col>
    <el-col :span="16">
    </el-col>
  </el-row>
  <el-dialog title="平板任务机信息" v-model="dialogFormVisible">
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="平板名称">
        <el-input v-model="form.ct_name"></el-input>
      </el-form-item>
      <el-form-item label="平板帐户">
        <el-input v-model="form.ct_user"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="pass">
        <el-input type="password" v-model="form.ct_password" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="标签打印机IP">
        <el-input v-model="form.print_ip"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="resource">
        <el-radio-group v-model="form.status">
          <el-radio :label=1>启用</el-radio>
          <el-radio :label=0>禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="addTask" v-if="created">立即创建</el-button>
        <el-button type="primary" v-else="!created" @click="edit">确认编辑</el-button>
        <el-button @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</div>
<%--<script src="https://unpkg.com/vue/dist/vue.js"></script>--%>
<script src="https://cdn.bootcss.com/vue/2.2.6/vue.min.js"></script>
<!-- 引入组件库 -->
<%--<script src="https://unpkg.com/element-ui/lib/index.js"></script>--%>
<script src="https://cdn.bootcss.com/element-ui/1.2.9/index.js"></script>
<script>
  new Vue({
    el: '#app',
    data: function () {
      return {
        visible: false,
        options: [{name: '', stores_id: ''}],
        value: '',
        row: {},
        tableData: [],
        form: {
          ct_name: '',
          status: 1,
          ct_user: '',
          ct_password: '',
          print_ip: ''
        },
        dialogFormVisible: false,
        created: true
      }
    },
    created: function () {
      var that = this
      $.get('/ErpApi/getStoresInfo', function (data) {
        console.log(data)
        data = data.obj
        that.options = data.storesList
      })
      this.getTask();
    },
    methods: {
      getTask: function () {
        var that = this
        $.post('/ErpApi/getChufangTask', {stores_id: this.value}, function (data) {
          that.tableData = data.obj
        })
      },
      // 添加平板任务机
      addTask: function () {
        var that = this
        if (!this.form.ct_name) {
          alert('请填写平板名称')
          return false
        } else if (!this.form.ct_user) {
          alert('请填写平板账户')
          return false
        } else if (!this.form.ct_password) {
          alert('请填写平板密码')
          return false
        }else if (!this.form.print_ip) {
          alert('请填写标签打印机IP')
          return false
        } else if (!this.form.status) {
          alert('请填写下达机状态')
          return false
        }
        $.post('/ErpApi/addChufangTask', {
          stores_id: that.value,
          ct_name: that.form.ct_name,
          es_id: that.form.es_id,
          status: that.form.status,
          ct_user: that.form.ct_user,
          ct_password: that.form.ct_password,
          print_ip: that.form.print_ip
        }, function (data) {
          if (data.status === '1') {
            console.log('添加成功')
            that.dialogFormVisible = false
            that.$alert('添加成功', '添加成功', {
              confirmButtonText: '确定'
            });
            that.getTask()
          }
        })
      },


      add: function () {
        if (this.value === '') {
          this.$alert('请选择店铺', '请选择店铺', {
            confirmButtonText: '确定'
          });
          return false
        }
        this.form.ct_name = ''
        this.form.ct_user = ''
        this.form.ct_password = ''
        this.form.es_id = ''
        this.form.status = ''
        this.form.print_ip = ''
        this.dialogFormVisible = true
        this.created = true
      },
      Edit: function (index, row) {
        this.created = false
        this.form.ct_name = row.ct_name
        this.form.ct_user = row.ct_user
        this.form.ct_password = row.password
        this.form.es_id = row.es_id
        this.form.status = row.status
        this.form.print_ip = row.print_ip
        this.dialogFormVisible = true
      },
      edit: function () {
        var that = this
        if (!this.form.ct_name) {
          alert('请填写平板名称')
          return false
        } else if (!this.form.ct_user) {
          alert('请填写平板账户')
          return false
        } else if (!this.form.ct_password) {
          alert('请填写平板密码')
          return false
        }else if (!this.form.print_ip) {
          alert('请填写标签打印机IP')
          return false
        } else if (!this.form.status) {
          alert('请填写平板状态')
          return false
        }
        $.post('/ErpApi/updateChufangTask', {
          stores_id: that.value,
          ct_name: that.form.ct_name,
          es_id: that.form.es_id,
          status: that.form.status,
          ct_user: that.form.ct_user,
          ct_password: that.form.ct_password,
          print_ip: that.form.print_ip
        }, function (data) {
          console.log(data)
          if (data.status === '1') {
            that.dialogFormVisible = false
            that.dialogFormVisible = false
            that.$alert('修改成功', '修改成功', {
              confirmButtonText: '确定'
            });
            that.getTask()
          }
        })
      },
      handleSelect: function (key, keyPath) {
      },
      chooseShop: function (item) {
        this.value = item.stores_id
        this.getTask()
      },
      cancel: function () {
        this.dialogFormVisible = false
      },
      openMeal: function (index, row) {
//        开启2,关闭3
        console.log(row)
        var that = this
        $.post('/ErpApi/updateChufangTask', {
          stores_id: row.stores_id,
          status: 2,
          es_id: row.es_id
        }, function (data) {
//          console.log(data)
          if (data.status === '1') {
            that.dialogFormVisible = false
            that.dialogFormVisible = false
            that.$alert('修改成功', '修改成功', {
              confirmButtonText: '确定'
            });
            that.getTask()
          }
        })
      },
      closeMeal: function (index, row) {
        var that = this
        $.post('/ErpApi/updateChufangTask', {
          stores_id: row.stores_id,
          status: 3,
          es_id: row.es_id
        }, function (data) {
//          console.log(data)
          if (data.status === '1') {
            that.dialogFormVisible = false
            that.dialogFormVisible = false
            that.$alert('修改成功', '修改成功', {
              confirmButtonText: '确定'
            });
            that.getTask()
          }
        })
      }
    }
  })
</script>
</body>
</html>
