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
    <title>厨房打印机设置</title>
    <%--<link rel="stylesheet" href="/WEB-INF/jsp/inc/index.css">--%>
    <%--<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-default/index.css">--%>
    <link href="https://cdn.bootcss.com/element-ui/1.2.9/theme-default/index.css" rel="stylesheet">
    <style>
        html, body, #app, .el-menu {
            height: 100%;
            overflow: auto;
        }

        body {
            background: #fff;
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
            <%--左边菜单--%>
            <el-menu default-active="2" class="el-menu-vertical-demo"
                     theme="light" style="height: 100%;width: 200px">
                <el-menu-item v-for="(item,index ) in options" :index="item.stores_id.toString()"
                              @click="chooseShop(item)">
                    {{item.name}}
                </el-menu-item>
            </el-menu>
        </el-col>
        <el-col :span="16">
            <el-row style="height: 90px;line-height: 90px;" type="flex" justify="space-between">
                <el-col :span="4">
                    <div>
                        <span class="title">厨房打印机</span>
                    </div>
                </el-col>
                <el-col :span="6" style="text-align: right;">
                    <el-button type="warning" @click.native.prevent="add" icon="plus" size="small">添加打印机</el-button>

                </el-col>
            </el-row>
            <%--接单机表格--%>
            <el-table :data="tableData" highlight-current-row style="width: 100%;" max-height="400" border
                      @current-change="chooseMeal">
                <el-table-column prop="ct_num" label="编号"></el-table-column>
                <el-table-column prop="cm_name" label="名称"></el-table-column>
                <el-table-column prop="ct_user" label="IP地址"></el-table-column>
                <el-table-column prop="wash_group" label="洗菜组"></el-table-column>
                <el-table-column prop="jardiniere_group" label="配菜组"></el-table-column>
                <el-table-column prop="cook_group" label="厨师"></el-table-column>
                <el-table-column label="操作" prop="status" width="180">
                    <template scope="scope">
                        <el-button size="small" @click.native.prevent="Edit(scope.$index, scope.row)" type="warning"
                                   icon="edit">
                            编辑
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
            <el-row style="height: 50px;line-height: 50px;">
                <el-col :span="20">
                    你当前选择的打印机编号{{nowMeal}}
                </el-col>
                <el-col style="text-align: right;" :span="4">
                    <el-button type="warning" @click.native.prevent="getErpGoods" icon="plus" size="small">添加商品
                    </el-button>
                </el-col>
            </el-row>
            <%--商品表格--%>
            <el-table :data="goods" highlight-current-row @current-change="handleCurrentChange" style="width: 100%"
                      border
                      max-height="400">
                <el-table-column type="index" width="60px">
                </el-table-column>
                <el-table-column property="good_id" label="商品ID"></el-table-column>
                <el-table-column property="good_name" label="商品名称"></el-table-column>
                <el-table-column property="good_num" label="商品编号"></el-table-column>
                <el-table-column label="操作">
                    <template scope="scope">
                        <el-button size="small" @click.stop.prevent="deleteGood(scope.$index, scope.row)" type="danger"
                                   icon="delete">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-col>
    </el-row>
    <%--接单机信息填写表单--%>
    <el-dialog title="打印机信息" v-model="dialogFormVisible">
        <el-form ref="form" :model="form" label-width="120px" :rules="rules" class="demo-ruleForm">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="打印机名称" show-message>
                        <el-input v-model="form.cm_name"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="IP地址">
                        <el-input v-model="form.ct_user"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <%--<el-col :span="12">
                  <el-form-item label="密码" prop="pass">
                    <el-input type="password" v-model="form.ct_password" auto-complete="off"></el-input>
                  </el-form-item>
                </el-col>--%>
                <el-col :span="12">
                    <el-form-item label="洗菜组">
                        <el-input v-model="form.wash_group" auto-complete="off"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="12">
                    <el-form-item label="配菜组">
                        <el-input v-model="form.jardiniere_group" auto-complete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>

                <el-col :span="12">
                    <el-form-item label="厨师">
                        <el-input v-model="form.cook_group" auto-complete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="状态" prop="resource">
                        <el-radio-group v-model="form.status">
                            <el-radio :label=1>启用</el-radio>
                            <el-radio :label=0>禁用</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-form-item>
                <el-button type="primary" @click="addTask('form')" v-if="created">立即创建</el-button>
                <el-button type="primary" v-else="!created" @click="edit">确认编辑</el-button>
                <el-button @click="cancel">取消</el-button>
            </el-form-item>
        </el-form>
    </el-dialog><!--接单机信息表结束-->
    <%--商品信息填写表单--%>
    <el-dialog title="商品信息表" v-model="goodsShow">
        <el-form ref="form" :model="addGood" label-width="130px" class="demo-form-inline">
            <el-row type="flex" justify="space-between">
                <el-col :span="6">
                    <el-input placeholder="请输入编号">
                        <template slot="prepend">编号</template>
                    </el-input>
                </el-col>
                <el-col :span="6">
                    <el-input placeholder="请输入名称">
                        <template slot="prepend">名称</template>
                    </el-input>
                </el-col>
                <el-col :span="6">
                    <el-button type="primary" icon="search">查询</el-button>
                </el-col>
            </el-row>
            <el-table :data="goodList" highlight-current-row style="width: 100%;margin-top: 20px;margin-bottom: 20px;"
                      border
                      max-height="400" @selection-change="selectGoods">
                <el-table-column type="selection" :selectable="available"></el-table-column>
                <el-table-column property="good_id" label="商品ID"></el-table-column>
                <el-table-column property="good_name" label="商品名称"></el-table-column>
                <el-table-column property="good_num" label="商品编号"></el-table-column>
                <el-table-column property="cm_id" label="接单机编号"></el-table-column>
            </el-table>
            <el-form-item>
                <el-button type="primary" @click="confirmAdd">确定</el-button>
                <el-button @click="cancel">取消</el-button>
            </el-form-item>
        </el-form>
    </el-dialog><!--商品信息表结束-->
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
                meal: null,
                value1: '0',
                row: {},
                tableData: [],
                form: {
                    cm_name: '',
                    es_id: '',
                    status: 1,
//          ct_num: '',
                    ct_user: '',
                    ct_password: '',
                    wash_group: '',
                    jardiniere_group: '',
                    cook_group: ''
                },
                rules: {
                    ct_name: [
                        {required: true, message: '请输入活动名称', trigger: 'blur'},
                        {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
                    ],
                    ct_user: [
                        {required: true, message: '请输入活动名称', trigger: 'blur'},
                        {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
                    ]
                },
                dialogFormVisible: false,
                created: true,
                goods: [],
                goodsShow: false,
                goodList: [],
                addGood: {},
                addGoods: [],
                addGoodsDetail: [],
                nowMeal: ''
            }
        },
//    获取商铺
        created: function () {
            var that = this
            $.get('/ErpApi/getStoresInfo', function (data) {
                console.log(data)
                data = data.obj
                that.options = data.storesList
            })
        },
        methods: {
//    获取接单机
            getTask: function () {
                var that = this
                $.post('/ErpApi/getMealErpInfo', {stores_id: this.value}, function (data) {
//          console.log(data)
                    that.tableData = data.obj
                    that.nowMeal = tableData[0].ct_num
                    if (that.tableData[0]) {
                        that.goods = that.tableData[0].cdsMsGoods
                        that.nowMeal = that.tableData[0].ct_num
                    } else {
                        that.goods = []
                    }
                })
            },addTask: function (formName) {     //添加接单机
                var that = this
                if (!this.form.cm_name) {
                    alert('请填写接单机名称')
                    return false
                } else if (!this.form.ct_user) {
                    alert('请填写接单机账户')
                    return false
                }  else if (!this.form.wash_group || this.form.wash_group.length > 3) {
                    alert('请填洗菜组,不能超过3位')
                    return false
                } else if (!this.form.status) {
                    alert('请填写状态')
                    return false
                } else if (!this.form.jardiniere_group || this.form.jardiniere_group.length > 3) {
                    alert('请填写配菜组')
                    return false
                } else if (!this.form.cook_group || this.form.cook_group.length > 3) {
                    alert('请填写厨师')
                    return false
                }
                $.post('/ErpApi/addChufangMeal', {
                    stores_id: that.value,
                    cm_name: that.form.cm_name,
                    status: that.form.status,
                    ct_user: that.form.ct_user,
                    ct_password: that.form.ct_password,
                    wash_group: that.form.wash_group,
                    jardiniere_group: that.form.jardiniere_group,
                    cook_group: that.form.cook_group
                }, function (data) {
                    if (data.status === '1') {
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
                for (var i in this.form) {
                    this.form[i] = ''
                }
                this.form.status = 1
                this.dialogFormVisible = true
                this.created = true
            },
            Edit: function (index, row) {
                this.created = false
                this.form.cm_name = row.cm_name
                this.form.ct_user = row.ct_user
                this.form.ct_password = row.password
                this.form.ct_num = row.ct_num
                this.form.status = row.status
                this.form.ct_password = row.ct_password
                this.form.wash_group = row.wash_group
                this.form.jardiniere_group = row.jardiniere_group
                this.form.cook_group = row.cook_group
                this.dialogFormVisible = true
            },
            edit: function () {
                var that = this
                if (!this.form.cm_name) {
                    alert('请填写接单机名称')
                    return false
                } else if (!this.form.ct_user) {
                    alert('请填写接单机账户')
                    return false
                }  else if (!this.form.wash_group || this.form.wash_group.length > 3) {
                    alert('请填入规范的洗菜组,不能超过3位')
                    return false
                } else if (!this.form.status) {
                    alert('请填写状态')
                    return false
                } else if (!this.form.jardiniere_group || this.form.jardiniere_group.length > 3) {
                    alert('请填写配菜组,不能超过3位')
                    return false
                } else if (!this.form.cook_group || this.form.cook_group.length > 3) {
                    alert('请填写厨师,不能超过3位')
                    return false
                } else if (this.form.wash_group.length > 3) {
                    alert('请填写洗碗组,不能超过3位')
                }
                $.post('/ErpApi/updateChufangMeal', {
                    stores_id: that.value,
                    cm_name: that.form.cm_name,
                    status: that.form.status,
                    ct_user: that.form.ct_user,
                    ct_num: that.form.ct_num,
                    ct_password: that.form.ct_password,
                    cm_id: that.meal,
                    wash_group: that.form.wash_group,
                    jardiniere_group: that.form.jardiniere_group,
                    cook_group: that.form.cook_group

                }, function (data) {
                    console.log(data)
                    if (data.status === '1') {
                        that.dialogFormVisible = false
                        that.dialogFormVisible = false
                        that.$alert('修改成功', '修改成功', {
                            confirmButtonText: '确定'
                        });
                        that.getTask()
                    } else {
                        alert(data.message)
                    }
                })
            },
//      选择店铺
            chooseShop: function (item) {
                this.value = item.stores_id
                this.getTask()
                this.nowMeal = ''
                this.goods = []
            },
            cancel: function () {
                this.dialogFormVisible = false
                this.goodsShow = false
            },
//      点击添加商品按钮获取菜品弹出选择菜品弹窗
            getErpGoods: function () {
                var that = this
                if (this.meal === null) {
                    this.$alert('请选择接单机', '请选择接单机', {
                        confirmButtonText: '确定'
                    });
                    return false
                }
//        getErpGoods
//        getChuErpGoods
                $.get('/ErpApi/getChuErpGoods', {stores_id: this.value, rows: 100}, function (data) {
                    console.log(data)
                    console.log(that.value)
                    that.goodList = data.obj.goodsList
                    that.goodsShow = true
                })
            },
            editGood: function () {
                $.post('')
            },
//      选择接单机
            chooseMeal: function (val) {
                this.goods = val.cdsMsGoods
                this.meal = val.cm_id
                this.nowMeal = val.ct_num
            },
            handleCurrentChange: function (val) {
            },
            confirmAdd: function () {
                var str = JSON.stringify(this.addGoods)
                var that = this
                $.post('/ErpApi/addMealErpGoods', {list: str}, function (data) {
                    console.log(data)
                    if (data.status === '1') {
                        that.$alert('添加成功', '添加成功', {
                            confirmButtonText: '确定'
                        });
                        that.goodsShow = false
                        console.log(that.goods)
                        if (that.goods) {
                            that.goods = that.goods.concat(that.addGoodsDetail)
                        } else {
                            that.goods = that.addGoodsDetail
                        }
                    }
                })
            },
            selectGoods: function (val) {
                this.addGoodsDetail = val
                var obj = []
                for (var i = 0; i < val.length; i++) {
                    obj.push({
                        good_id: val[i].good_id,
                        stores_id: this.value,
                        cm_id: this.meal
                    })
                }
                this.addGoods = obj
            },
            deleteMeal: function (index, row) {
                $.post('/ErpApi/delChufangMeal', {cm_id: row.cm_id}, function (data) {
                })
            },
            deleteGood: function (index, row) {
                var that = this
                this.$confirm('此操作将删除该商品, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    $.post('/ErpApi/delMealErpGoods', {
                        good_id: row.good_id, stores_id: that.value,
                        cm_id: that.meal
                    }, function (data) {
                        if (data.status === '1') {
                            that.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                            that.goods.splice(index, 1)
                        }
                    })
                }).catch(function () {
                    that.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            available: function (row, index) {
                if (row.type === 0) {
                    return true
                } else {
                    return false
                }
            },
            openMeal: function (index, row) {
//        开启2,关闭3
                var that = this
                $.post('/ErpApi/updateChufangMeal', {
                    stores_id: row.stores_id,
                    status: 2,
                    cm_id: row.cm_id
                }, function (data) {
                    if (data.status === '1') {
                        that.dialogFormVisible = false
                        that.dialogFormVisible = false
                        that.$alert('修改成功', '修改成功', {
                            confirmButtonText: '确定'
                        });
                        that.getTask()
                    } else {
                        alert(data.message)
                    }
                })
            },
            closeMeal: function (index, row) {
                var that = this
                $.post('/ErpApi/updateChufangMeal', {
                    stores_id: row.stores_id,
                    status: 3,
                    cm_id: row.cm_id
                }, function (data) {
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
        },
        computed: {}
    })
</script>
</body>
</html>
