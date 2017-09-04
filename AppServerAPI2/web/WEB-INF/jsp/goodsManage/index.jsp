<%--
  Created by IntelliJ IDEA.
  User: pg243
  Date: 2017/5/17
  Time: 9:32
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

    .header {
      height: 50px;
      display: flex;
      align-items: center;
      background: #fff;
      padding-left: 20px;
    }

    .control-wrapper {
      height: 70px;
      display: flex;
      align-items: center;
    }

    #app {
      padding-bottom: 200px;
      -webkit-box-sizing: border-box;
      -moz-box-sizing: border-box;
      box-sizing: border-box;
    }

    li {
      list-style: none;
    }

    .list-item {
      width: 500px;
      height: 50px;
      border: 1px solid #bfcbd9;
      position: relative;
      display: flex;
      align-items: center;
      justify-content: space-around;
      margin-bottom: 5px;
      border-radius: 5px;
    }

    .delete {
      right: 0;
      height: 14px;
      color: #FF4949;
      padding: 10px;
      cursor: pointer;
    }

    .chose-erp-goods {
      box-sizing: border-box;
      padding-left: 20px;
    }

    .search-box-erp {
      height: 100px;
    }

    .el-input-number__decrease, .el-input-number__increase {
      height: 100%;
      display: -webkit-box;
      display: -webkit-flex;
      display: -ms-flexbox;
      display: flex;
      -webkit-box-align: center;
      -webkit-align-items: center;
      -ms-flex-align: center;
      align-items: center;
      -webkit-box-pack: center;
      -webkit-justify-content: center;
      -ms-flex-pack: center;
      justify-content: center;
    }

    .erp-good-name {
      width: 150px;

    }
  </style>
</head>
<body>
<div id="app">
  <%--商品表格--%>
  <el-row class="header">
    <el-breadcrumb>
      <el-breadcrumb-item>设置</el-breadcrumb-item>
      <el-breadcrumb-item>菜品管理</el-breadcrumb-item>
    </el-breadcrumb>
  </el-row>
  <el-row class="control-wrapper">
    <el-col :span="4" :offset="18">
      <el-button type="primary"  @click="addGood" style="float: right;" icon="plus">添加商品
      </el-button>
    </el-col>
  </el-row>
  <el-table :data="goodsData" border height="70%">
    <el-table-column label="商品类别" prop="class_nick_name"></el-table-column>
    <el-table-column label="商品名称" prop="good_name"></el-table-column>
    <el-table-column label="商品价格" prop="market_price"></el-table-column>
    <el-table-column label="商品描述" prop="class_desc"></el-table-column>
    <el-table-column label="商品信息" prop="good_info"></el-table-column>
    <el-table-column label="商品图片">
      <template scope="scope">
        <img :src="'http://caidashi.pro/api/img/index/f/'+scope.row.good_pic+'/w/120/h/120'" alt="" width="50"
             height="50">
      </template>
    </el-table-column>
    <el-table-column label="描述">
      <template scope="scope">
        <div v-html="scope.row.introduce"></div>
      </template>
    </el-table-column>
    <el-table-column width="350" label="操作">
      <template scope="scope">
        <el-button type="warning" icon="edit" @click="editGood(scope.$index,scope.row)">编辑</el-button>
        <el-button type="info" icon="plus" @click="_getErpGoods(scope.$index,scope.row)">添加菜品</el-button>
      </template>
    </el-table-column>
  </el-table>
  <%--商品添加表单--%>
  <el-dialog title="添加商品表单" v-model="goodFormVisible">
    <el-form :model="goodForm" :rules="rules" ref="ruleForm" class="demo-ruleForm" label-position="right">
      <el-form-item>
        <%--<el-upload--%>
        <%--class="upload-demo"--%>
        <%--ref="upload"--%>
        <%--action="http://localhost/ErpApi/addGoods"--%>
        <%--:on-preview="handlePreview"--%>
        <%--:on-remove="handleRemove"--%>
        <%--:file-list="goodForm.fileList2"--%>
        <%--list-type="picture"--%>
        <%--:auto-upload="false" id="input">--%>
        <%--<el-button slot="trigger" size="small" type="primary">选取文件</el-button>--%>
        <%--<el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>--%>
        <%--<div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>--%>
        <%--</el-upload>--%>
        <input type="file" id="file" name="file">
      </el-form-item>
      <el-row>
        <el-col :span="6" style="margin-right: 20px;">
          <el-form-item label="商品分类" prop="class_id">
            <el-select v-model="goodForm.class_id" placeholder="请选择菜品分类" filterable>
              <el-option :label="item.class_name" :value="item.class_id"
                         v-for="item in goodsClassList"></el-option>
            </el-select>
            <%--<input type="text" v-model="goodForm.class_id" name="class_id" style="display: none;">--%>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="商品名称" prop="good_name">
            <el-input v-model="goodForm.good_name"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">

        </el-col>
      </el-row>
      <el-form-item label="会员是否折扣" prop="vip_discounts">
        <el-radio-group v-model="goodForm.vip_discounts">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-row>
        <el-col :span="8">
          <el-form-item label="商品价格" style="margin-right: 20px" prop="market_price">
            <el-input v-model="goodForm.market_price" type="number"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="餐盒费" prop="box_price">
            <el-input v-model="goodForm.box_price" type="number"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-form-item label="商品详情" prop="introduce">
          <el-input type="textarea" v-model="goodForm.introduce"></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="商品简介" prop="info">
          <el-input type="textarea" v-model="goodForm.info"></el-input>
        </el-form-item>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="goodFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="goodFormConfirm('ruleForm')" v-if="add">确 定</el-button>
      <el-button type="primary" @click="editFormConfirm('ruleForm')" v-else>确定编辑</el-button>
    </div>
  </el-dialog>
  <el-dialog title="Erp商品" v-model="erpGoodFormVisible" size="full">
    <div>你当前选择的商品名称: <strong>{{nowGood.good_name}}</strong></div>
    <el-row>
      <el-col :span="12">
        <el-row class="search-box-erp">
          <el-col :span="6" style="margin-right: 20px;">
            <el-input v-model="erp_keywords" placeholder="请输入关键字查询"></el-input>
          </el-col>
          <el-col :span="6">
            <el-select v-model="value1" clearable placeholder="请选择商品类别" filterable @change="erp_keywords=''">
              <el-option v-for="item in erpClassList" :label="item.class_name" :value="item.class_id">
              </el-option>
            </el-select>
          </el-col>
        </el-row>
        <el-table :data="searchErp" height="400" @select="select" border ref="table2">
          <%--<el-table-column type="selection" width="55"></el-table-column>--%>
          <el-table-column label="商品名称" prop="good_name"></el-table-column>
          <el-table-column label="商品类别" prop="good_name" :formatter="formatter"></el-table-column>
          <el-table-column label="操作">
            <template scope="scope">
              <el-button v-if="scope.row.add===false" icon="add" type="info"
                         @click="addErpGood(scope.$index,scope.row)">添加
              </el-button>
              <el-button v-else @click="deleteErpGood(scope.$index,scope.row)">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="12" class="chose-erp-goods">
        <h4>你本次添加的商品如下:</h4>
        <ul>
          <li class="list-item" v-for="item in selectErpGoods">
            <h5 class="erp-good-name">{{item.good_name}}</h5>
            <el-input-number v-model="item.good_count" :min="1" :max="10" size="small"></el-input-number>
            <div class="delete" @click="deleteSelectErpGood(item)">
              <i class="el-icon-delete"></i>
            </div>
          </li>
        </ul>
      </el-col>
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button @click="erpGoodFormVisible=false">取消</el-button>
      <el-button type="primary" @click="confirmAddErpGoods">确定</el-button>
    </div>
  </el-dialog>
</div>
<script src="/static/new/script/common/vue2.3.3.min.js"></script>
<script src="/static/new/script/common/index1.3.3.js"></script>
<script src="/static/new/script/common/jquery.min.js"></script>
<script>
  var vm = new Vue({
    el: '#app',
    data: function () {
      return {
        goodFormVisible: false,
        goodsClassList: [],//erp商品分类
        goodForm: {
          fileList2: [],
          class_id: '',
          good_name: '',
          introduce: '',
          info: '',
          vip_discounts: 1,
          status: 0,
          box_price: '',
          market_price: '',
          file: ''
        },
        classList: [],
        a: false,
        region: '',
        rules: {
          region: [
            {required: true, message: '请选择商品种类', trigger: 'change'}
          ],
          class_id: [
            {required: true, message: '请选择商品种类', trigger: 'change', type: 'number'}
          ],
          good_name: [
            {required: true, message: '请输入商品名称', trigger: 'blur'}
          ],
          introduce: [
            {required: true, message: '请输入商品详情', trigger: 'blur'}
          ],
          info: [
            {required: true, message: '请输入商品简介', trigger: 'blur'}
          ],
          vip_discounts: [
//            {required: true, message: '请选择商品会员是否折扣', trigger: 'blur'}
          ],
          market_price: [
            {required: true, message: '请填写商品价格', trigger: 'blur', type: 'number'}
          ],
          box_price: [
            {required: true, message: '请填写餐盒费', trigger: 'blur', type: 'number'}
          ]
        },
        goodsData: [],
        erpGoodFormVisible: false,
        erpGoodsData: [],
        nowGood: {},
        erp_keywords: '',
        value1: '',// erp商品分类查询input,
        selectErpGoods: [],//选中的erp商品,
        erpClassList: [], //erp商品类别,
//        alreadyErpGoods: []
        add: true
      }
    },
    created: function () {
//      this._getErpGood()
      this._getGoodsData()
      this._getStoresInfo()
      this._getErpGood()
    },

    methods: {
      /**--------------------------商品表单-------------------------------**/
      // 获取所有erp商品列表
      _getErpGood: function () {
        $.get('/ErpApi/getErpGoods', function (data) {
//          vm.classList = data.obj.classList
//          console.log(data)
        })
      },
      goodFormConfirm: function (formName) {
        var that = this
        this.$refs[formName].validate(function (valid) {
          if (valid) {
            var file = new FormData
            file.append('file', $('#file')[0].files[0])
            for (var i in that.goodForm) {
              file.append(i, that.goodForm[i])
            }
            $.ajax({
              url: '/ErpApi/addGoods',
              type: 'POST',
              cache: false,
              data: file,
              processData: false,
              contentType: false
            }).done(function (res) {
              if (res.status === '1') {
                that.goodFormVisible = false
                that._getGoodsData()
              }
            }).fail(function (res) {

            })
          } else {
            console.log('erroo')
            return false
          }
        })

      },
      handleRemove: function (file, fileList) {
        console.log(file, fileList);
      },
      handlePreview: function (file) {
        console.log(file);
      },
      submitUpload: function () {
        var file = document.getElementById('file').files
        $.post('/ErpApi/addGoods', this.goodForm, function (data) {
          console.log(data)
        })
      },
      editGood: function (index, row) {
        this.goodForm = row
        this.nowGood = row
        this.goodFormVisible = true
        this.add = false
      },
      editFormConfirm: function (formName) {
        var file = new FormData
        var that = this
        this.$set(this.goodForm, 'good_id', this.nowGood.good_id)
        console.log(this.goodForm)
        file.append('file', $('#file')[0].files[0])
        for (var i in this.goodForm) {
          file.append(i, this.goodForm[i])
        }
        this.$refs[formName].validate((valid) => {
          if (valid) {
            $.ajax({
              url: '/ErpApi/updateGoods',
              type: 'POST',
              cache: false,
              data: file,
              processData: false,
              contentType: false
            }).done(function (res) {
              if (res.status === '1') {
                if (res.status === '1') {
                  that.goodFormVisible = false
                  that._getGoodsData()
                }
              }
            }).fail(function (res) {

            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      /**--------------------------获取商品列表-------------------------------------------**/
      _getStoresInfo: function () {
        $.get('/ErpApi/getStoresInfo', function (data) {
          vm.goodsClassList = data.obj.goodsClassList
          vm.erpClassList = data.obj.erpClassList
        })
      },
      _getGoodsData: function () {
        $.post('/ErpApi/getGoodsInfo', {}, function (data) {
          console.log(data)
          vm.goodsData = data.obj
        })
      },
      _getErpGoods: function (index, row) {
        console.log(row)
        this.erpGoodFormVisible = true
        vm.nowGood = row
        var that = this
        $.post('/ErpApi/getErpGoodsInfo', {good_id: row.good_id}, function (data) {
          console.log(data)
          vm.selectErpGoods = data.obj
        })
        $.get('/ErpApi/getChuErpGoods', {rows: 100}, function (data) {
          vm.erpGoodsData = data.obj.goodsList
          for (var i = 0; i < vm.erpGoodsData.length; i++) {
            vm.$set(vm.erpGoodsData[i], 'add', false)
            vm.$set(vm.erpGoodsData[i], 'good_count', 1)
          }
          for (let i = 0; i < vm.erpGoodsData.length; i++) {
            for (let j = 0; j < vm.selectErpGoods.length; j++) {
              if (vm.erpGoodsData[i].good_id === vm.selectErpGoods[j].good_id) {
                vm.erpGoodsData[i].add = true
                vm.erpGoodsData[i].disabled = true
              }
            }
          }
        })
      },
      formatter: function (row, column) {
        for (var i = 0; i < vm.erpClassList.length; i++) {
          if (row.class_id === vm.erpClassList[i].class_id) {
            return vm.erpClassList[i].class_name
          }
        }
      },
      go: function (value, row) {
        return false
      },
      select (selection, row) {
//        console.log(selection, row)
//        for (let i = 0; i < this.selectErpGoods.length; i++) {
//          if (this.selectErpGoods[i].good_id === row.good_id) {
//            return
//          }
//        }
//        this.selectErpGoods.push(row)

//        this.$refs.table2.setCurrentRow(row)
      },
      go1 () {
        console.log('aa')
        if (5 > 1) {
          this.searchErp.forEach((row) => {
            this.$refs.table2.setCurrentRow(row)
            console.log(row)
          })
        }
      },
      addGood: function () {
        this.goodFormVisible = true
        this.add = true
      },
      addErpGood: function (index, row) {
        for (let i = 0; i < this.selectErpGoods.length; i++) {
          if (this.selectErpGoods[i].good_id === row.good_id) {
//            return
            this.selectErpGoods[i].row = false
            return
          }
        }
        this.selectErpGoods.push(row)
        row.add = true
      },
      deleteErpGood: function (index, row) {
        row.add = false
        for (let i = 0; i < this.selectErpGoods.length; i++) {
          if (this.selectErpGoods[i].good_id === row.good_id) {
            console.log(this.selectErpGoods[i])
            this.selectErpGoods.splice(i, 1)
          }
        }
      },
      deleteSelectErpGood: function (item) {
        $.post('/ErpApi/delErpGoodsInfo', {erp_good_id: item.erp_good_id}, function (data) {
          console.log(data)
        })
        for (let i = 0; i < this.selectErpGoods.length; i++) {
          if (this.selectErpGoods[i].good_id === item.good_id) {
            this.selectErpGoods.splice(i, 1)
          }
        }
        for (let i = 0; i < this.erpGoodsData.length; i++) {
          if (this.erpGoodsData[i].good_id === item.good_id) {
            this.erpGoodsData[i].add = false
          }
        }
      },
      /***--------------------------------点击确认添加菜品---------------------------------****/
      confirmAddErpGoods: function () {
        let list = []
        for (let i = 0; i < this.selectErpGoods.length; i++) {
          list.push({
            ms_good_id: this.selectErpGoods[i].good_id,
            good_id: this.nowGood.good_id,
            good_count: this.selectErpGoods[i].good_count
          })
        }
        list = JSON.stringify(list)
        console.log(list)
        let data = {
          good_id: this.nowGood.good_id,
          list: list
        }
        var that = this
        $.post('/ErpApi/addErpGoodsToGoods', data, function (data) {
          console.log(data)
          if (data.status === '1') {
            that.erpGoodFormVisible = false
            that.$alert('添加菜品成功', '添加菜品成功', {
              confirmButtonText: '确定'
            });
            that.selectErpGoods = []
          }
        })
      }
    },
    computed: {
      goodsData: function () {
        return {}
      },
      searchErp: function () {
        let list = []
        if (this.erp_keywords) {
          for (let i = 0; i < this.erpGoodsData.length; i++) {
            if (this.erpGoodsData[i].good_name.indexOf(this.erp_keywords) !== -1) {
              list.push(this.erpGoodsData[i])
            }
          }
        } else {
          for (let i = 0; i < this.erpGoodsData.length; i++) {
            if (this.erpGoodsData[i].class_id === this.value1) {
              list.push(this.erpGoodsData[i])
            }
          }
        }
        return list
      }
    }
  })
</script>
</body>
</html>
