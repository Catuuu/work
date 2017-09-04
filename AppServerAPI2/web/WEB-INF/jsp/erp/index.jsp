<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <link href="/static/new/style/common/index.css" rel="stylesheet">
  <link rel="stylesheet" href="/static/new/style/erp/style.min.css">
  <style>
    html, body, #app {
      height: 100%;
      width: 100%;
    }

    li {
      list-style: none;
    }

    #app {
      padding-bottom: 200px;
      -webkit-box-sizing: border-box;
      -moz-box-sizing: border-box;
      box-sizing: border-box;
    }

  </style>
</head>
<body>
<div id="app">
  <el-row class="head" type="flex" align="middle">
    <el-col :span="4" style="padding-left: 120px;"><span>商品列表</span></el-col>
    <el-col :span="8">
      <el-select v-model="status" placeholder="请选择上架状态" clearable @change="searchStatus">
        <el-option label="未上架" :value="2"></el-option>
        <el-option label="已上架" :value="1"></el-option>
      </el-select>
      <el-select v-model="value8" filterable placeholder="请选择商品种类" clearable @change="searchClass">
        <el-option v-for="item in classList" :label="item.class_name" :value="parseInt(item.class_id)"></el-option>
      </el-select>
    </el-col>
    <el-col :span="4">
      <el-input v-model="value9" placeholder="请输入商品关键字查询"></el-input>
    </el-col>
    <el-col :span="4">
      <el-button type="info" icon="search" @click="searchGoodsKey">查询</el-button>
    </el-col>
    <el-col :span="4">
      <el-button type="primary" icon="plus" @click="add">添加商品</el-button>
    </el-col>
    <el-col :span="4">
      <%--<el-button type="primary" icon="plus" @click="addMaterial">添加原料</el-button>--%>
    </el-col>
  </el-row>
  <%--商品列表--%>
    <el-table :data="searchGoods" style="width: 100%;" border height="70%" >
      <el-table-column prop="good_id" label="商品id" width="100"></el-table-column>
      <el-table-column prop="class_name" label="商品分类" :formatter="formatter"></el-table-column>
      <el-table-column prop="good_name" label="商品名称"></el-table-column>
      <el-table-column prop="good_num" label="商品编号"></el-table-column>
      <el-table-column label="是否备餐" align="center" width="100">
        <template scope="scope">
          <el-tag v-if="scope.row.isback===1" type="success">是</el-tag>
          <el-tag v-else type="warning">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="是否同步" align="center" width="100">
        <template scope="scope">
          <el-tag v-if="scope.row.is_sync===1" type="success">是</el-tag>
          <el-tag v-else type="warning">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="400">
        <template scope="scope">
          <el-button size="small" @click.stop.prevent="editGood(scope.$index, scope.row)" type="danger"
                     icon="edit">编辑
          </el-button>
          <el-button size="small" @click.stop.prevent="getMaterial(scope.$index, scope.row)" type="danger"
                     v-if="scope.row.status===1&&scope.row.is_sync===1" icon="delete">下架
          </el-button>
          <el-button size="small" @click.stop.prevent="getMaterial(scope.$index, scope.row)" type="success"
                     v-if="scope.row.status===2&&scope.row.is_sync===1" icon="plus">上架
          </el-button>
          <el-button size="small" @click.stop.prevent="editMaterial(scope.$index, scope.row)" type="warning"
                     icon="search">编辑原料
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="block" style="position: fixed; bottom: 10px;">
      <el-pagination
          @size-change="getPageSize"
          @current-change="getPage"
      <%--:current-page="currentPage"--%>
          :page-sizes="[100]"
          :page-size="100"
          layout="total,sizes, prev, pager, next, jumper"
          :total="totalCount">
      </el-pagination>
    </div>
  <el-dialog title="商品详情" v-model="listShow">
    <el-form>

    </el-form>
    <el-table border :data="goodMaterial">
      <el-table-column prop="mname" label="原料名称"></el-table-column>
      <el-table-column prop="mid" label="原料编号"></el-table-column>
      <el-table-column prop="count" label="数量"></el-table-column>
      <el-table-column prop="unit" label="单位"></el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel">取 消</el-button>
      <el-button type="primary" @click="confirmAdd">确 定</el-button>
    </div>
  </el-dialog>
  <%--添加商品表单--%>
  <el-dialog title="添加商品" v-model="goodFormShow">
    <el-form :model="goodForm" class="demo-ruleForm" label-width="100px" :rules="rules" ref="ruleForm">
      <el-row align="middle">
        <el-col :span="8">
          <el-form-item label="商品种类">
            <el-select v-model="goodForm.class_id" placeholder="请选择商品种类">
              <el-option v-for="item in classList" :value="item.class_id" :label="item.class_name"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="商品名称" prop="good_name">
            <el-input v-model="goodForm.good_name" auto-complete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="是否备餐" prop="resource">
            <el-radio-group v-model="goodForm.isback">
              <el-radio :label="0">否</el-radio>
              <el-radio :label="1">是</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="出餐时间(分钟)" prop="out_time">
            <el-input v-model="goodForm.out_time" auto-complete="off" type="number"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="成品重量(克)" prop="finished_weight">
            <el-input v-model="goodForm.finished_weight" auto-complete="off" type="number"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="销售价" prop="good_price">
            <el-input v-model="goodForm.good_price" auto-complete="off" type="number"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="起草人">
            <el-select v-model="goodForm.draftsman" placeholder="请选择起草人" filterable>
              <el-option v-for="item in userList" :value="item.id" :label="item.user_nicename"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="审核人">
            <el-select v-model="goodForm.auditor" placeholder="请选择审核人" filterable>
              <el-option v-for="item in userList" :value="item.id" :label="item.user_nicename"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="批准人">
            <el-select v-model="goodForm.approver" placeholder="请选择批准人" filterable>
              <el-option v-for="item in userList" :value="item.id" :label="item.user_nicename"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>

      </el-row>
      <el-row>
        <el-form-item label="商品关键字" prop="good_key">
          <el-input prop="good_key" type="textarea" v-model="goodForm.good_key"></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="操作要领" prop="gist">
          <el-input v-model="goodForm.gist" auto-complete="off" type="textarea"></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="制作流程" prop="process">
          <el-input v-model="goodForm.process" type="textarea" placeholder="请输入内容"></el-input>
        </el-form-item>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel2('ruleForm')">取 消</el-button>
      <el-button type="primary" @click="confirmAddGood('ruleForm')">确 定</el-button>
    </div>
  </el-dialog>
  <el-dialog title="添加商品原料" v-model="materialShow" size="full">
    <h3>你当前选中的菜品是:{{selectedGood.good_name}}</h3>
    <el-form>
      <el-row>
        <el-col :span="4">
          <el-select v-model="chooseMaterial" placeholder="请选择原料分类" @change="changeList">
            <el-option v-for="item in allMaterial" :label="item.cname" :value="item.cid"></el-option>
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-input placeholder="请输入原料名称查询" v-model="keywords"></el-input>
        </el-col>
      </el-row>
    </el-form>
    <el-row>
      <el-col :span="12">
        <el-table border :data="materialList" max-height="500" @selection-change="handleSelectionChange"
                  @select="select">
          <%--<el-table-column type="selection"></el-table-column>--%>
          <el-table-column prop="mname" label="原料名称"></el-table-column>
          <el-table-column prop="mid" label="原料编号"></el-table-column>
          <el-table-column prop="unit" label="单位"></el-table-column>
          <el-table-column label="操作">
            <template scope="scope">
              <el-button v-if="scope.row.add===false" @click="addMaterialItem(scope.$index,scope.row)" type="info">添加
              </el-button>
              <el-button v-else @click="deleteMaterialItem(scope.$index,scope.row)" type="danger">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="12" style="padding-left:10px;">
        <div>你已选择的原料</div>
        <ul>
          <li v-for="item in selectedMaterial">
            <el-row>
              <el-col :span="4" style="text-align: right;padding-right: 10px;">
                <span>{{item.mname}}</span>
              </el-col>
              <el-col :span="3" style="padding-right: 10px;">
                <el-input v-model="item.count" type="number" max-length="5" min="0"></el-input>
              </el-col>
              <el-col :span="8">
                <span>单位:({{item.unit}})</span>
              </el-col>
            </el-row>
          </li>
        </ul>
      </el-col>
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button @click="materialShow=false">取 消</el-button>
      <el-button type="primary" @click="confirmSync">确 定</el-button>
    </div>
  </el-dialog>
  <el-dialog title="请添加原料" v-model="remainAdd">
    <span>尚未添加原料,不能上架,请点击确定添加原料</span>
    <span slot="footer" class="dialog-footer">
    <el-button @click="remainAdd=false">取 消</el-button>
    <el-button type="primary" @click="confirm2">添加原料</el-button>
  </span>
  </el-dialog>
  <%--<el-dialog title="提示"  size="tiny" :before-close="handleClose">--%>
  <%--<span>这是一段信息</span>--%>
  <%--<span slot="footer" class="dialog-footer">--%>
  <%--<el-button @click="dialogVisible = false">取 消</el-button>--%>
  <%--<el-button type="primary" @click="dialogVisible = false">确 定</el-button>--%>
  <%--</span>--%>
  <%--</el-dialog>--%>
  <%--添加商品表单结束--%>
  <%--添加原料表单--%>

</div>
<script src="/static/new/script/common/vue.min.js"></script>
<script src="/static/new/script/common/index.js"></script>
<script src="/static/new/script/common/jquery.min.js"></script>
<script>
  var resultList = []
  new Vue({
    el: '#app',
    data: {
      goods: [],
      list100: [],
      list11: [],
      num1: 0,
      listShow: false,
      goodFormShow: false,
      apiPath: 'http://114.55.97.90:8018/',
      goodMaterial: [],//获取单个商品的原料
      allMaterial: [],//所有的原料,
      chooseMaterial: '',
      classList: [],
      materialShow: false,
      status: '',
      userList: [],
      mowGoodName: '',
      goodForm: {
        good_name: '',
        class_id: '',
        good_key: '',
        good_price: '',
        isback: 0,
        out_time: '',
        draftsman: '',
        auditor: '',
        approver: '',
        finished_weight: '',
        gist: '',
        process: '',
        is_sync: 0,
        status: 2
      },
      selectedMaterial: [],
      selectedGood: {},
      keywords: '',
      nowGood: '',
      value8: '', // 商品种类,
      value9: '',// 商品关键字查询,
      remainAdd: false,//请添加原料提示框,
      rules: {
        class_id: [
          {required: true, message: '请选择商品种类', trigger: 'change'}
        ],
        good_name: [
          {required: true, message: '请输入商品名称', trigger: 'blur'}
        ],
        good_key: [
          {required: true, message: '请输入商品关键字', trigger: 'blur'}
        ],
        good_price: [
          {required: true, message: '请输入商品售价', trigger: 'blur'}
        ],
        draftsman: [
          {required: true, message: '请输入起草人', trigger: 'change'}
        ],
        auditor: [
          {required: true, message: '请输入审核人', trigger: 'change'}
        ],
        approver: [
          {required: true, message: '请输入批准人', trigger: 'change'}
        ],
        out_time: [
          {required: true, message: '请输入出餐时间', trigger: 'blur'}
        ],
        finished_weight: [
          {required: true, message: '请输入成品重量', trigger: 'blur'}
        ],
        gist: [
          {required: true, message: '请输入操作要领', trigger: 'blur'}
        ],
        process: [
          {required: true, message: '请输入制作流程', trigger: 'blur'}
        ]
      },
      currentPage: 1,
      pageSize: 20,
      totalCount: 0
    },
    created: function () {
      var that = this
      this._getErpGoods()
      $.get('/ErpApi/getErpGoods', {rows: 100}, function (data) {
        console.log(data)
        var data = data.obj
        that.goods = data.rows
        that.totalCount = data.total
      })
      $.get('/ErpApi/getStoresInfo', function (data) {
        that.classList = data.obj.erpClassList
        that.userList = data.obj.userList
      })
      $.get(this.apiPath + 'Handle/SalesHandler.ashx?m=GetMaterials', {}, function (data) {
        data = JSON.parse(data)
        that.allMaterial = data.data
        console.log(that.allMaterial)
        for (var i = 0; i < that.allMaterial.length; i++) {
          for (var j = 0; j < that.allMaterial[i].mlist.length; j++) {
            that.list11.push(that.allMaterial[i].mlist[j])
          }
        }
        for (let i = 0; i < that.list11.length; i++) {
          that.$set(that.list11[i], 'add', false)
          that.$set(that.list11[i], 'count', 1)
        }
        data = data.data
        var list = []
        for (var i = 0; i < data.length; i++) {
          for (var j = 0; j < data[i].mid.length; j++) {

          }
        }
      })
    },
    methods: {
      add: function () {
//        this.listShow = true
        for (var i in this.goodForm) {
          this.goodForm[i] = ''
        }
        this.goodFormShow = true
        var formName = 'ruleForm'
        this.resetForm(formName)
        var that = this
        $.get(this.apiPath + 'Handle/SalesHandler.ashx?m=GetMaterials', {}, function (data) {
          data = JSON.parse(data)
          console.log(data.data)
          that.allMaterial = data.data
        })
      },
      confirmAddGood: function (formName) {
        var that = this
        this.$refs[formName].validate(function (valid) {
          if (valid) {
            console.log(that.goodForm)
            $.post('/ErpApi/addErpGoods', that.goodForm, function (data) {
              console.log(data)
              if (data.status === '1') {
//            that.dialogFormVisible = false
                that.goodFormShow = false
                that.$alert('添加成功', '添加成功', {
                  confirmButtonText: '确定'
                });
                that._getErpGoods()
//            that.getTask()
              }
            })
          } else {
            console.log('erroo')
            return false
          }
        })
//
      },
      addMaterial: function (index, row) {
        this.materialShow = true
        this.selectedGood = row
      },
      deletedGood: function () {

      },
//      点击编辑按钮编辑商品信息
      editGood: function (index, row) {
        console.log(row)
        if (row.approver === 0) {
          row.approver = ''
        }
        if (row.auditor === 0) {
          row.auditor = ''
        }
        if (row.draftsman === 0) {
          row.draftsman = ''
        }
        this.goodFormShow = true
        this.goodForm = row
      },
      _getErpGoods: function () {
        var that = this
        $.get('/ErpApi/getErpGoods', {rows: 500}, function (data) {
//          console.log(data)
//          var data = data.obj
//          that.goods = data.goodsList
//          that.classList = data.classList
//          that.userList = data.userList
          data = data.obj
          that.goods = data.rows
        })
      },
      syncGood: function () {

      },
      searchGoodsKey () {
        $.get('/ErpApi/getErpGoods', {status: this.status, class_id: this.value8, good_key: this.value9}, (res) => {
          console.log(res)
          res = res.obj
          this.goods = res
        })
      },
      //添加原料
      addMaterialItem: function (index, row) {
        console.log(row)
        for (let i = 0; i < this.list11.length; i++) {
          if (this.list11[i].mid === row.mid) {
            this.list11[i].add = true
          }
        }
        this.selectedMaterial.push(row)
      },
//      删除原料
      deleteMaterialItem: function (index, row) {
        for (let i = 0; i < this.list11.length; i++) {
          if (this.list11[i].mid === row.mid) {
            this.list11[i].add = false
          }
        }
        for (let i = 0; i < this.selectedMaterial.length; i++) {
          if (this.selectedMaterial[i].mid === row.mid) {
            this.selectedMaterial.splice(i, 1)
          }
        }
      },
      // 确认上架
      confirmAdd: function () {
        var that = this
        $.post('/ErpApi/updateErpGoods', {good_id: this.nowGood, status: 0}, function (data) {
          if (data.status === '1') {
            that._getErpGoods()
            that.$alert('上架成功', '上架成功', {
              confirmButtonText: '确定'
            })
          }
        })
      },
      save: function () {
        console.log('aa')
      },
      cancel: function () {
        console.log('bbb')
        this.listShow = false
      },
      confirm: function () {
        console.log('')
      },
//      点击上架按钮
      getMaterial: function (index, row) {
        var that = this
        this.selectedGood = row
        $.get(this.apiPath + 'Handle/SalesHandler.ashx?m=GetFoodMate', {fids: row.good_id}, function (data) {
          var data = JSON.parse(data)
          that.goodMaterial = data.data[0].mates
          that.nowGood = row.good_id
          if (that.goodMaterial.length === 0) {
            that.remainAdd = true
          } else {
            that.confirmAdd()
            that.listShow = true
          }
        })
      },
//      点击查看原料按钮
      editMaterial: function (index, row) {
        var that = this
        this.selectedGood = row
//        console.log(row.good_id)
        $.get(this.apiPath + 'Handle/SalesHandler.ashx?m=GetFoodMate', {fids: row.good_id}, function (data) {
          that.materialShow = true
          data = JSON.parse(data)
          that.selectedMaterial = data.data[0].mates
//          console.log(that.selectedMaterial)
          for (let i = 0; i < that.list11.length; i++) {
            for (let j = 0; j < that.selectedMaterial.length; j++) {
              if (that.list11[i].mid === that.selectedMaterial[j].mid) {
                that.list11[i].add = true
              }
            }
          }
//          materials:0
        })
      },
      handleSelectionChange: function (val) {
      },
      select: function (selection, row) {
        console.log(row)
        for (var i = 0; i < this.selectedMaterial.length; i++) {
          if (row.mid === this.selectedMaterial[i].mid) {
            return
          }
        }
        this.selectedMaterial.push({mid: row.mid, count: 0, mname: row.mname, unit: row.unit})
      },
      changeList: function () {
        this.keywords = ''
      },
//    确认同步
      confirmSync: function () {
        var that = this
        var mates = []
        for (var j = 0; j < this.selectedMaterial.length; j++) {
          mates.push({mid: this.selectedMaterial[j].mid, count: parseFloat(this.selectedMaterial[j].count)})
        }
        var json = {
          fid: this.selectedGood.good_id,
          fname: this.selectedGood.good_name,
          mates: mates
        }
        json = JSON.stringify(json)
        console.log(json)
        $.post('/ErpApi/addMaterial', {json: json}, function (data) {
          console.log(data)
          if (data.status === '1') {
            that.materialShow = false
            that._getErpGoods()
            that.$alert('同步成功', '同步成功', {
              confirmButtonText: '确定'
            })
          }
        })
      },
      changeCount: function () {
        console.log(this.num1)
      },
      search: function () {
        var a = []
        for (var i = 0; i < this.materialList.length; i++) {
          if (this.materialList[i].mname.indexOf(this.keywords)) {
            a.push(this.materialList[i])
          }
        }
//        console.log(a)
      },
      cancel2: function (formName) {
        this.resetForm(formName)
        this.goodFormShow = false
      },
      resetForm: function (formName) {
        this.$refs[formName].resetFields();
      },
//     弹出请添加原料框的确定按钮
      confirm2: function () {
        this.materialShow = true
        this.remainAdd = false
      },
//      获取分页数据
      getPage: function (val) {
        console.log(val)
        $.get('/ErpApi/getErpGoods', {page: val, rows: 100}, (data) => {
//          console.log(res)
          data = data.obj
          this.goods = data.rows
        })
      },
//      获取每页的数据量/条
      getPageSize: function (val) {
//        this.pageSize = val
      },
      formatter: function (row, column) {
        for (var i = 0; i < this.classList.length; i++) {
          if (row.class_id === this.classList[i].class_id) {
            return this.classList[i].class_name
          }
        }
      },
      /***-------------------------状态查询------------------------*****/
      searchStatus () {
        $.get('/ErpApi/getErpGoods', {status: this.status, class_id: this.value8}, (res) => {
          console.log(res)
          res = res.obj
          this.goods = res
        })
      },
      searchClass () {
        console.log(this.value8)
        $.get('/ErpApi/getErpGoods', {status: this.status, class_id: this.value8}, (res) => {
          console.log(res)
          res = res.obj
          this.goods = res
        })
      }
    },
    computed: {
      /***--------------------原料列表---------------------------***/
      materialList: function () {
        if (this.keywords) {
          var list12 = []
          for (var i = 0; i < this.list11.length; i++) {
            if (this.list11[i].mname.indexOf(this.keywords) !== -1) {
              list12.push(this.list11[i])
            }
          }
          return list12
        } else {
          if (this.chooseMaterial === '') {
            return this.allMaterial[0].mlist
          } else {
            return this.allMaterial[this.chooseMaterial - 1].mlist
          }
        }
      },
      /***--------------------商品查询----------------------------------***/
      searchGoods: function () {
        return this.goods
//        var list = []
//        if (this.value9) {
//          for (var i = 0; i < this.goods.length; i++) {
//            if ((this.goods[i].good_name.indexOf(this.value9) !== -1)) {
//              list.push(this.goods[i])
//            }
//          }
//          return list
//        } else {
//          if (!this.status && !this.value8) {
//            return this.goods
//          } else if (!this.status && this.value8) {
//            for (var i = 0; i < this.goods.length; i++) {
//              if (this.goods[i].class_id === this.value8) {
//                list.push(this.goods[i])
//              }
//            }
//            return list
//          } else if (this.value8 && this.status) {
//            for (var i = 0; i < this.goods.length; i++) {
//              if (this.goods[i].class_id === this.value8 && this.goods[i].status === this.status) {
//                list.push(this.goods[i])
//              }
//            }
//            return list
//          } else {
//            for (var i = 0; i < this.goods.length; i++) {
//              if (this.goods[i].status === this.status) {
//                list.push(this.goods[i])
//              }
//            }
//            return list
//          }
//        }
      },
      /**----------------------原料查询---------------------------------------------***/
      searchMaterial: function () {
//        var list = []
//        for (var i = 0; i < this.materialList.length; i++) {
//          if (this.materialList[i].mname.indexOf(this.keywords) !== -1) {
//            list.push(this.materialList[i])
//          }
//        }
//        return list
      }
    }
  })
</script>
</body>
</html>
