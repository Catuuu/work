<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="any" uri="http://www.anychart.com" %>
<%@ page import="com.cheea.entity.*"%>
<%@ page import="java.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>在线排课系统</title>
<link rel="stylesheet" type="text/css"
	href="EasyUI/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="EasyUI/themes/icon.css">
<script type="text/javascript" src="EasyUI/jquery-1.6.min.js"></script>
<script type="text/javascript" src="EasyUI/jquery.easyui.min.js"></script>
<script type="text/javascript">
	var xmlhttp = false;
	function CreateXMLHttp() {
		try {
			xmlhttp = new XMLHttpRequest(); //尝试创建 XMLHttpRequest 对象，除 IE 外的浏览器都支持这个方法。
		} catch (e) {
			try {
				xmlhttp = new ActiveXObject("Msxml2.XMLHTTP"); //使用较新版本的 IE 创建 IE 兼容的对象（Msxml2.XMLHTTP）
			} catch (e) {
				try {
					xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //使用较老版本的 IE 创建 IE 兼容的对象（Microsoft.XMLHTTP）。
				} catch (failed) {
					xmlhttp = false; //如果失败则保证 request 的值仍然为 false。
				}
			}
		}
		return xmlhttp;
	}

	function doUp(id) {
		var url = "up.do?id=" + id;
		CreateXMLHttp();
		if (xmlhttp) {
			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = getResult;
			xmlhttp.send(null);
		}
	}

	function getResult() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var txt = xmlhttp.responseText;
			if (txt == "ok") {
				location = "getAll.do";
			} else {
				alert("修改失败！");
			}
		}
	}

	function doDown(id) {
		var url = "down.do?id=" + id;
		CreateXMLHttp();
		if (xmlhttp) {
			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var txt = xmlhttp.responseText;
					if (txt == "ok") {
						location = "getAll.do";
					} else {
						alert("修改失败！");
					}
				}
			};
			xmlhttp.send(null);
		}
	}

	function doEqual() {
		var pwd = document.getElementById("pwd").value;
		var newpwd = document.getElementById("newpwd").value;
		if (pwd != newpwd) {
			alert("两次密码不等！");
		}
	}

	function doDelete(id) {
		var url = "delete.do?id=" + id;
		CreateXMLHttp();
		if (xmlhttp) {
			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var txt = xmlhttp.responseText;
					if (txt == "ok") {
						alert("删除成功！");
						location = "getAll.do";
					} else {
						alert("删除失败！");
					}
				}
			};
			xmlhttp.send(null);
		}
	}
	
	$(function(){//用于创建右键菜单
		$(document).bind('contextmenu',function(e){
			$('#mm').menu('show', {
				left: e.pageX,
				top: e.pageY
			});
			return false;
		});
	
        var today = new Date();
		$.messager.show({//欢迎信息
			title:'欢迎',
			msg:'现在时间是：'+today+',欢迎使用在线排课系统!',
			timeout:5000,
			showType:'slide'
		});
	});
	
	function addTabs() {
		$("#img").hide();//隐藏图片
		$("#t").tabs('close', '排课表');//关闭之前的tab
		$('#t').tabs('add', {
			title : '排课表',
			content : $("#w4").html(),
			closable : true
		});
		$('#ta5').datagrid({
			width:700,
			height:308,
			nowrap: false,
			fit: true,
			striped: true,
			collapsible:true,
			url:'getrea.do',
			loadMsg:'数据装载中......',
			sortName: 'id',
			sortOrder: 'desc',
			remoteSort: false,
			idField:'id',
			frozenColumns:[[
                {field:'id',checkbox:true},
			]],
			columns:[[
				{field:'studentName',title:'班级名称',width:120},
				{field:'courseName',title:'课程名',width:120},
				{field:'teacherName',title:'教师名',width:160},
				{field:'className',title:'教室名',width:160},
				{field:'time',title:'时间',width:160}
			]],
			rownumbers:true,
			toolbar:[{
				id:'btnedit',
				text:'调节',
				iconCls:'icon-edit',
				handler:function(){
					var selected = $('#ta5').datagrid('getSelected');
					var selections=$('#ta5').datagrid('getSelections');
					if(selected){
						if(selections.length==1){
							doWindow11();
							$('#rejss').combobox({   
								 url:'gettea.do',   
					             valueField:'name',   
								 textField:'name'  
					           });
							$('#rejs').combobox({   
								 url:'getcal.do',   
					             valueField:'className',   
								 textField:'className'  
					           });
							$('#resj').combobox({   
								 url:'gettime.do',   
					             valueField:'sid',   
								 textField:'name'  
					           });
							$("#rejss").combobox('select',selected.teacherName);
							$("#rejs").combobox('select',selected.className);
							$('#rebj').val(selected.studentName);
						    //$('#kcsided').val(selected.sid);
						    $('#rekc').val(selected.courseName);
						    $('#reid').val(selected.id);
						}else{
							$.messager.alert('警告','请勿选择多行数据！','警告');
						}
					}else{
						$.messager.alert('警告','请选择一行数据','警告');
					}
				}
			}]
		});
		 $('#ta5').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
	}
    
	function doWindow1() {
		$('#w9').window({
			title:'添加',
			width : 260,
			modal : true,
			shadow : false,
			closed : false,
			height : 160
		});
	}
	
	function doWindow2() {
		$('#w10').window({
			title:'修改',
			width : 260,
			modal : true,
			shadow : false,
			closed : false,
			height : 160
		});
	}
	
	function doWindow3() {
		$('#w11').window({
			title:'添加',
			width : 260,
			modal : true,
			shadow : false,
			closed : false,
			height : 200
		});
		 $('#jscourse').combobox({   
			 url:'getcou.do',   
             valueField:'cid',   
			 textField:'name'  
           });  
	}
	
	function doWindow4() {
		$('#w12').window({
			title:'修改',
			width : 260,
			modal : true,
			shadow : false,
			closed : false,
			height : 180
		});
	}
	
	function doWindow5() {
		$('#w13').window({
			title:'添加',
			width : 260,
			modal : true,
			shadow : false,
			closed : false,
			height : 130
		});
	}
	
	function doWindow6() {
		$('#w14').window({
			title:'修改',
			width : 260,
			modal : true,
			shadow : false,
			closed : false,
			height : 150
		});
	}
	
	function doWindow7() {
		$('#w15').window({
			title:'添加',
			width : 260,
			modal : true,
			shadow : false,
			closed : false,
			height : 180
		});
		 $('#kcsid').combobox({   
			 url:'getstu.do',   
             valueField:'sid',   
			 textField:'className'  
           });  
	}
	
	function doWindow8() {
		$('#w16').window({
			title:'修改',
			width : 260,
			modal : true,
			shadow : false,
			closed : false,
			height : 180
		});
	}
	
	function doWindow9() {
		$('#w8').window({
			width : 400,
			modal : true,
			shadow : false,
			closed : false,
			height : 300
		});
	}

	function doWindow10() {
		$('#w17').window({
			title:'调节',
			width : 260,
			modal : true,
			shadow : false,
			closed : false,
			height : 220
		});
	}
	
	function doWindow11() {
		$('#w18').window({
			title:'调节',
			width : 260,
			modal : true,
			shadow : false,
			closed : false,
			height : 220
		});
	}
	
	function addTab() {
		$("#img").hide();//隐藏图片
		$("#t").tabs('close', '班级管理');//关闭之前的tab
		$('#t').tabs('add', {
			title : '班级管理',
			content : $("#w").html(),
			closable : true
		});
		$('#ta1').datagrid({
			width:700,
			height:308,
			nowrap: false,
			fit: true,
			striped: true,
			collapsible:true,
			url:'getstu.do',
			loadMsg:'数据装载中......',
			sortName: 'id',
			sortOrder: 'desc',
			remoteSort: false,
			idField:'id',
			frozenColumns:[[
                {field:'id',checkbox:true},
                {title:'sid号',field:'sid',width:80,sortable:true}
			]],
			columns:[[
				{field:'className',title:'班级名称',width:120},
				{field:'number',title:'人数',width:120},
				{field:'sid',title:'编号',width:320}
			]],
			rownumbers:true,
			toolbar:[{
				id:'btnadd',
				text:'添加',
				iconCls:'icon-add',
				handler:function(){
					doWindow1();
				}
			},{
				id:'btnedit',
				text:'修改',
				iconCls:'icon-edit',
				handler:function(){
					var selected = $('#ta1').datagrid('getSelected');
					var selections=$('#ta1').datagrid('getSelections');
					if(selected){
						if(selections.length==1){
							doWindow2();
							$('#bjnameed').val(selected.className);
						    $('#bjsided').val(selected.sid);
						    $('#bjnumbered').val(selected.number);
						}else{
							$.messager.alert('警告','请勿选择多行数据！','警告');
						}
					}else{
						$.messager.alert('警告','请选择一行数据','警告');
					}
				}
			},'-',{
				id:'btnremove',
				text:'删除',
				iconCls:'icon-remove',
				handler:function(){
					var selected = $('#ta1').datagrid('getSelected');
					var selections=$('#ta1').datagrid('getSelections');
					if(selected){
						 $.messager.confirm('警告','确认删除么?',function(id){
							    if(id){
							    	var ids='';
							    	for(var i=0;i<selections.length;i++){
							    		if(i==selections.length-1){
							    			ids+=selections[i].sid;
							    		}else{
							    			ids+=selections[i].sid;
							    			ids+=',';
							    		}
							    	}
							    	 //var sid=selected.sid;
										var url = "deleteStudent.do?id="+ids;
										CreateXMLHttp();
										if (xmlhttp) {
											xmlhttp.open("GET", url, true);
											xmlhttp.onreadystatechange = function() {
												if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
													var txt = xmlhttp.responseText;
													if (txt == "ok") {
														alert("删除成功！");
														$('#ta1').datagrid({
														     url:'getstu.do',
														     loadMsg:'更新数据中......'
														 });
														$('#ta1').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
													} else {
														alert("删除失败！");
													}
												}
											};
											xmlhttp.send(null);
									}
							    }
						 });
					}else{
						$.messager.alert('警告','请选择一行数据','警告');
					}
				}
			}]
		});
		 $('#ta1').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
	}

	function addTab1() {
		$("#img").hide();//隐藏图片
		$("#t").tabs('close', '教师管理');//关闭之前的tab
		$('#t').tabs('add', {
			title : '教师管理',
			content : $("#w1").html(),
			closable : true
		});
		$('#ta2').datagrid({
			width:700,
			height:308,
			fit: true,
			nowrap: false,
			striped: true,
			collapsible:true,
			url:'gettea.do',
			loadMsg:'数据装载中......',
			sortName: 'id',
			sortOrder: 'desc',
			remoteSort: false,
			idField:'id',
			frozenColumns:[[
                {field:'id',checkbox:true},
                {title:'课程号',field:'courseId',width:80,sortable:true}
			]],
			columns:[[
				{field:'name',title:'教师姓名',width:120},
				{field:'age',title:'年龄',width:120},
				{field:'phone',title:'电话',width:198},
				{field:'courseId',title:'教授课程',width:120}
			]],
			rownumbers:true,
			toolbar:[{
				id:'btnadd1',
				text:'添加',
				iconCls:'icon-add',
				handler:function(){
					doWindow3();
				}
			},{
				id:'btnedit1',
				text:'修改',
				iconCls:'icon-edit',
				handler:function(){
					var selected = $('#ta2').datagrid('getSelected');
					var selections=$('#ta2').datagrid('getSelections');
					if(selected){
						if(selections.length==1){
							doWindow4();
							$('#jscourseed').combobox({   
								 url:'getcou.do',   
					             valueField:'cid',   
								 textField:'name'  
								 
					           });
							$("#jscourseed").combobox('select',selected.courseId);
							$('#jsnameed').val(selected.name);
						    $('#jsageed').val(selected.age);
						    $('#jsphoneed').val(selected.phone);
						   // $('#jscourseed').val(selected.courseId);
						    $('#jsid').val(selected.id);
						}else{
							$.messager.alert('警告','请勿选择多行数据！','警告');
						}
					}else{
						$.messager.alert('警告','请选择一行数据','警告');
					}
				}
			},'-',{
				id:'btnremove1',
				text:'删除',
				iconCls:'icon-remove',
				handler:function(){
					var selected = $('#ta2').datagrid('getSelected');
					var selections=$('#ta2').datagrid('getSelections');
					if(selected){
						 $.messager.confirm('警告','确认删除么?',function(id){
							    if(id){
							    	var ids='';
							    	for(var i=0;i<selections.length;i++){
							    		if(i==selections.length-1){
							    			ids+=selections[i].id;
							    		}else{
							    			ids+=selections[i].id;
							    			ids+=',';
							    		}
							    	}
							    	 //var sid=selected.id;
										var url = "deleteTeacher.do?id="+ids;
										CreateXMLHttp();
										if (xmlhttp) {
											xmlhttp.open("GET", url, true);
											xmlhttp.onreadystatechange = function() {
												if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
													var txt = xmlhttp.responseText;
													if (txt == "ok") {
														alert("删除成功！");
														$('#ta2').datagrid({
														     url:'gettea.do',
														     loadMsg:'更新数据中......'
														 });
														$('#ta2').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
													} else {
														alert("删除失败！");
													}
												}
											};
											xmlhttp.send(null);
									}
							    }
						 });
					}else{
						$.messager.alert('警告','请选择一行数据','警告');
					}
				}
			}]
		});
		 $('#ta2').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
	}

	function addTab2() {
		$("#img").hide();//隐藏图片
		$("#t").tabs('close', '教室管理');//关闭之前的tab
		$('#t').tabs('add', {
			title : '教室管理',
			content : $("#w2").html(),
			closable : true
		});
		$('#ta3').datagrid({
			width:700,
			height:308,
			fit: true,
			nowrap: false,
			striped: true,
			collapsible:true,
			url:'getcal.do',
			loadMsg:'数据装载中......',
			sortName: 'id',
			sortOrder: 'desc',
			remoteSort: false,
			idField:'id',
			frozenColumns:[[
                {field:'id',checkbox:true},
			]],
			columns:[[
				{field:'className',title:'教室名',width:120},
				{field:'classNumber',title:'教室人数',width:120},
				{field:'state',title:'状态',width:320}
			]],
			rownumbers:true,
			toolbar:[{
				id:'btnadd',
				text:'添加',
				iconCls:'icon-add',
				handler:function(){
					doWindow5();
				}
			},{
				id:'btnedit',
				text:'修改',
				iconCls:'icon-edit',
				handler:function(){
					var selected = $('#ta3').datagrid('getSelected');
					var selections=$('#ta3').datagrid('getSelections');
					if(selected){
						if(selections.length==1){
							doWindow6();
							$('#jssnameed').val(selected.className);
						    $('#jssnumbered').val(selected.classNumber);
						    $('#jssstateed').val(selected.state);
						    $('#jssid').val(selected.id);
						}else{
							$.messager.alert('警告','请勿选择多行数据！','警告');
						}
					}else{
						$.messager.alert('警告','请选择一行数据','警告');
					}
				}
			},'-',{
				id:'btnremove',
				text:'删除',
				iconCls:'icon-remove',
				handler:function(){
					var selected = $('#ta3').datagrid('getSelected');
					var selections=$('#ta3').datagrid('getSelections');
					if(selected){
						 $.messager.confirm('警告','确认删除么?',function(id){
							    if(id){
							    	var ids='';
							    	for(var i=0;i<selections.length;i++){
							    		if(i==selections.length-1){
							    			ids+=selections[i].id;
							    		}else{
							    			ids+=selections[i].id;
							    			ids+=',';
							    		}
							    	}
							    	 //var sid=selected.id;
										var url = "deleteClass.do?id="+ids;
										CreateXMLHttp();
										if (xmlhttp) {
											xmlhttp.open("GET", url, true);
											xmlhttp.onreadystatechange = function() {
												if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
													var txt = xmlhttp.responseText;
													if (txt == "ok") {
														alert("删除成功！");
														$('#ta3').datagrid({
														     url:'getcal.do',
														     loadMsg:'更新数据中......'
														 });
														$('#ta3').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
													} else {
														alert("删除失败！");
													}
												}
											};
											xmlhttp.send(null);
									}
							    }
						 });
					}else{
						$.messager.alert('警告','请选择一行数据','警告');
					}
				}
			}]
		});
		 $('#ta3').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
	}
	
	function addTab3() {
		$("#img").hide();//隐藏图片
		$("#t").tabs('close', '课程管理');//关闭之前的tab
		$('#t').tabs('add', {
			title : '课程管理',
			content : $("#w3").html(),
			closable : true
		});
		$('#ta4').datagrid({
			width:700,
			height:308,
			fit: true,
			nowrap: false,
			striped: true,
			collapsible:true,
			url:'getcou.do',
			loadMsg:'数据装载中......',
			sortName: 'id',
			sortOrder: 'desc',
			remoteSort: false,
			idField:'id',
			frozenColumns:[[
                {field:'id',checkbox:true},
			]],
			columns:[[
				{field:'cid',title:'课程编号',width:120},
				{field:'name',title:'课程名称',width:120},
				{field:'time',title:'课时',width:160},
				{field:'sid',title:'所属班级',width:140}
			]],
			rownumbers:true,
			toolbar:[{
				id:'btnadd',
				text:'添加',
				iconCls:'icon-add',
				handler:function(){
					doWindow7();
				}
			},{
				id:'btnedit',
				text:'修改',
				iconCls:'icon-edit',
				handler:function(){
					var selected = $('#ta4').datagrid('getSelected');
					var selections=$('#ta4').datagrid('getSelections');
					if(selected){
						if(selections.length==1){
							doWindow8();
							$('#kcsided').combobox({   
								 url:'getstu.do',   
					             valueField:'sid',   
								 textField:'className'  
					           });
							$("#kcsided").combobox('select',selected.sid);
							$('#kcnameed').val(selected.name);
						    //$('#kcsided').val(selected.sid);
						    $('#kccided').val(selected.cid);
						    $('#kctimeed').val(selected.time);
						    $('#kcid').val(selected.id);
						}else{
							$.messager.alert('警告','请勿选择多行数据！','警告');
						}
					}else{
						$.messager.alert('警告','请选择一行数据','警告');
					}
				}
			},'-',{
				id:'btnremove',
				text:'删除',
				iconCls:'icon-remove',
				handler:function(){
					var selected = $('#ta4').datagrid('getSelected');
					var selections=$('#ta4').datagrid('getSelections');
					if(selected){
						 $.messager.confirm('警告','确认删除么?',function(id){
							    if(id){
							    	var ids='';
							    	for(var i=0;i<selections.length;i++){
							    		if(i==selections.length-1){
							    			ids+=selections[i].id;
							    		}else{
							    			ids+=selections[i].id;
							    			ids+=',';
							    		}
							    	}
							    	 //var sid=selected.id;
										var url = "deleteCourse.do?id="+ids;
										CreateXMLHttp();
										if (xmlhttp) {
											xmlhttp.open("GET", url, true);
											xmlhttp.onreadystatechange = function() {
												if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
													var txt = xmlhttp.responseText;
													if (txt == "ok") {
														alert("删除成功！");
														$('#ta4').datagrid({
														     url:'getcou.do',
														     loadMsg:'更新数据中......'
														 });
														$('#ta4').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
													} else {
														alert("删除失败！");
													}
												}
											};
											xmlhttp.send(null);
									}
							    }
						 });
					}else{
						$.messager.alert('警告','请选择一行数据','警告');
					}
				}
			}]
		});
		 $('#ta4').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
	}
	
	
	function addTab4() {
		$('#w5').window({
			title:'查询',
			width : 800,
			modal : true,
			shadow : false,
			closed : false,
			height : 400
		});
		$('#sear').searchbox({   
		    width:200,   
		    searcher:function(value,name){  
				$('#ta6').datagrid({
					nowrap: false,
					fit: true,
					striped: true,
					collapsible:true,
					url:'search.do',
					loadMsg:'数据装载中......',
					sortName: 'id',
					sortOrder: 'desc',
					remoteSort: false,
					idField:'id',
					frozenColumns:[[
		                {field:'id',checkbox:true},
					]],
					columns:[[
						{field:'studentName',title:'班级名称',width:120},
						{field:'courseName',title:'课程名',width:120},
						{field:'teacherName',title:'教师名',width:160},
						{field:'className',title:'教室名',width:160},
						{field:'time',title:'时间',width:160}
					]],
					rownumbers:true
				});
				var queryParams = $('#ta6').datagrid('options').queryParams;
		    	queryParams.name = value;
		    	queryParams.type = name;
		    	$('#ta6').datagrid('options').queryParams=queryParams;
				$("#ta6").datagrid('reload'); 
				document.getElementById("pin").value=value;
				document.getElementById("pi").value=name;
		    },   
		    menu:'#mmm',   
		    prompt:'请输入查询内容'  
		});	
	}
	
	function addTab5() {
		$("#img").hide();//隐藏图片
		$("#t").tabs('close', '手动排课');//关闭之前的tab
		$('#t').tabs('add', {
			title : '手动排课',
			content : $("#w6").html(),
			closable : true
		});
		$('#ta7').datagrid({
			width:700,
			height:308,
			nowrap: false,
			fit: true,
			striped: true,
			collapsible:true,
			url:'getfal.do',
			loadMsg:'数据装载中......',
			sortName: 'id',
			sortOrder: 'desc',
			remoteSort: false,
			idField:'id',
			frozenColumns:[[
	            {field:'id',checkbox:true},
			]],
			columns:[[
				{field:'studentName',title:'班级名称',width:120},
				{field:'courseName',title:'课程名',width:120},
				{field:'teacherName',title:'教师名',width:160},
				{field:'className',title:'教室名',width:160},
				{field:'time',title:'时间',width:160}
			]],
			rownumbers:true,
			toolbar:[{
				id:'btnedit',
				text:'调节',
				iconCls:'icon-edit',
				handler:function(){
					var selected = $('#ta7').datagrid('getSelected');
					var selections=$('#ta7').datagrid('getSelections');
					if(selected){
						if(selections.length==1){
							doWindow10();
							$('#sdjss').combobox({   
								 url:'gettea.do',   
					             valueField:'name',   
								 textField:'name'  
					           });
							$('#sdjs').combobox({   
								 url:'getcal.do',   
					             valueField:'className',   
								 textField:'className'  
					           });
							$('#sdsj').combobox({   
								 url:'gettime.do',   
					             valueField:'sid',   
								 textField:'name'  
					           });
							//$("#kcsided").combobox('select',selected.sid);
							$('#sdbj').val(selected.studentName);
						    //$('#kcsided').val(selected.sid);
						    $('#sdkc').val(selected.courseName);
						    $('#sdid').val(selected.id);
						}else{
							$.messager.alert('警告','请勿选择多行数据！','警告');
						}
					}else{
						$.messager.alert('警告','请选择一行数据','警告');
					}
				}
			}]
		});
	}
	
	function addTab8() {
		$("#img").hide();
		$("#t").tabs('close', '用户管理');
		$('#t').tabs('add', {
			title : '用户管理',
			content : $("#w7").html(),
			closable : true
		});
	}
	
	
	function add(){
		var name=document.getElementById("bjname").value;
		var sid=document.getElementById("bjsid").value;
		var number=document.getElementById("bjnumber").value;
		var url = "addStudent.do?name=" +name+"&sid="+sid+"&number="+number;
		CreateXMLHttp();
		if (xmlhttp) {
			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var txt = xmlhttp.responseText;
					if (txt == "ok") {
						alert("添加成功！");
						$('#w9').window('close');
						$('#ta1').datagrid({
						     url:'getstu.do',
						     loadMsg:'更新数据中......'
						 });
						$('#ta1').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
					} else {
						alert("添加失败！");
					}
				}
			};
			xmlhttp.send(null);
	}
	}
     
	function add1(){
		var name=document.getElementById("jsname").value;
		var phone=document.getElementById("jsphone").value;
		var age=document.getElementById("jsage").value;
		var courseId = $('#jscourse').combobox('getValue');
		var url = "addTeacher.do?name=" +name+"&phone="+phone+"&age="+age+"&courseId="+courseId;
		CreateXMLHttp();
		if (xmlhttp) {
			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var txt = xmlhttp.responseText;
					if (txt == "ok") {
						alert("添加成功！");
						$('#w11').window('close');
						$('#ta2').datagrid({
						     url:'gettea.do',
						     loadMsg:'更新数据中......'
						 });
						$('#ta2').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
					} else {
						alert("添加失败！");
					}
				}
			};
			xmlhttp.send(null);
	}
	}
	
	function add2(){
		var name=document.getElementById("jssname").value;
		var number=document.getElementById("jssnumber").value;
		var url = "addClass.do?name=" +name+"&number="+number;
		CreateXMLHttp();
		if (xmlhttp) {
			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var txt = xmlhttp.responseText;
					if (txt == "ok") {
						alert("添加成功！");
						$('#w13').window('close');
						$('#ta3').datagrid({
						     url:'getcal.do',
						     loadMsg:'更新数据中......'
						 });
						$('#ta3').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
					} else {
						alert("添加失败！");
					}
				}
			};
			xmlhttp.send(null);
	}
	}
	
	function add3(){
		var name=document.getElementById("kcname").value;
		//var sid=document.getElementById("kcsid").value;
		var cid=document.getElementById("kccid").value;
		var time=document.getElementById("kctime").value;
		var sid = $('#kcsid').combobox('getValue');
		var url = "addCourse.do?name=" +name+"&cid="+cid+"&sid="+sid+"&time="+time;
		CreateXMLHttp();
		if (xmlhttp) {
			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var txt = xmlhttp.responseText;
					if (txt == "ok") {
						alert("添加成功！");
						$('#w15').window('close');
						$('#ta4').datagrid({
						     url:'getcou.do',
						     loadMsg:'更新数据中......'
						 });
						$('#ta4').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
					} else {
						alert("添加失败！");
					}
				}
			};
			xmlhttp.send(null);
	}
	}
	
	function edit(){
		var name=document.getElementById("bjnameed").value;
		var sid=document.getElementById("bjsided").value;
		var number=document.getElementById("bjnumbered").value;
		var url = "updateStudent.do?name=" +name+"&sid="+sid+"&number="+number;
		CreateXMLHttp();
		if (xmlhttp) {
			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var txt = xmlhttp.responseText;
					if (txt == "ok") {
						alert("修改成功！");
						$('#w10').window('close');
						$('#ta1').datagrid({
						     url:'getstu.do',
						     loadMsg:'更新数据中......'
						 });
						$('#ta1').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
					} else {
						alert("修改失败！");
					}
				}
			};
			xmlhttp.send(null);
	}
	}
	
	function edit1(){
		var name=document.getElementById("jsnameed").value;
		var phone=document.getElementById("jsphoneed").value;
		var age=document.getElementById("jsageed").value;
		var courseId = $('#jscourseed').combobox('getValue');
		//var courseId=document.getElementById("jscourseed").value;
		var id=document.getElementById("jsid").value;
		var url = "updateTeacher.do?name=" +name+"&phone="+phone+"&age="+age+"&courseId="+courseId+"&id="+id;
		CreateXMLHttp();
		if (xmlhttp) {
			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var txt = xmlhttp.responseText;
					if (txt == "ok") {
						alert("修改成功！");
						$('#w12').window('close');
						$('#ta2').datagrid({
						     url:'gettea.do',
						     loadMsg:'更新数据中......'
						 });
						$('#ta2').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
					} else {
						alert("修改失败！");
					}
				}
			};
			xmlhttp.send(null);
	}
	}
	
	function edit2(){
		var name=document.getElementById("jssnameed").value;
		var number=document.getElementById("jssnumbered").value;
		var state=document.getElementById("jssstateed").value;		
		var id=document.getElementById("jssid").value;
		var url = "updateClass.do?name=" +name+"&number="+number+"&id="+id+"&state="+state;
		CreateXMLHttp();
		if (xmlhttp) {
			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var txt = xmlhttp.responseText;
					if (txt == "ok") {
						alert("修改成功！");
						$('#w14').window('close');
						$('#ta3').datagrid({
						     url:'getcal.do',
						     loadMsg:'更新数据中......'
						 });
						$('#ta3').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
					} else {
						alert("修改失败！");
					}
				}
			};
			xmlhttp.send(null);
	}
	}
	
	function edit3(){
		var name=document.getElementById("kcnameed").value;
		//var sid=document.getElementById("kcsided").value;
		var sid = $('#kcsided').combobox('getValue');
		var cid=document.getElementById("kccided").value;	
		var time=document.getElementById("kctimeed").value;
		var id=document.getElementById("kcid").value;
		var url = "updateCourse.do?name=" +name+"&sid="+sid+"&id="+id+"&cid="+cid+"&time="+time;
		CreateXMLHttp();
		if (xmlhttp) {
			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var txt = xmlhttp.responseText;
					if (txt == "ok") {
						alert("修改成功！");
						$('#w16').window('close');
						$('#ta4').datagrid({
						     url:'getcou.do',
						     loadMsg:'更新数据中......'
						 });
						$('#ta4').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
					} else {
						alert("修改失败！");
					}
				}
			};
			xmlhttp.send(null);
	}
	}
	
	function edit4(){
		var student=document.getElementById("sdbj").value;
		var course=document.getElementById("sdkc").value;
		var teacher = $('#sdjss').combobox('getValue');
		var className = $('#sdjs').combobox('getValue');
		var time = $('#sdsj').combobox('getValue');
		var id=document.getElementById("sdid").value;
		var url = "updateFail.do?student="+student+"&id="+id+"&teacher="+teacher+"&time="+time+"&course="+course+"&className="+className;
		CreateXMLHttp();
		if (xmlhttp) {
			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var txt = xmlhttp.responseText;
					if (txt == "ok") {
						alert("调节成功！");
						$('#w17').window('close');
						$('#ta7').datagrid({
						     url:'getfal.do',
						     loadMsg:'更新数据中......'
						 });
						$('#ta7').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
					} else {
						alert("调节失败！");
					}
				}
			};
			xmlhttp.send(null);
	}
	}
	
	function edit5(){
		var student=document.getElementById("rebj").value;
		var course=document.getElementById("rekc").value;
		var teacher = $('#rejss').combobox('getValue');
		var className = $('#rejs').combobox('getValue');
		var time = $('#resj').combobox('getValue');
		var id=document.getElementById("reid").value;
		var url = "updateReady.do?student="+student+"&id="+id+"&teacher="+teacher+"&time="+time+"&course="+course+"&className="+className;
		CreateXMLHttp();
		if (xmlhttp) {
			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var txt = xmlhttp.responseText;
					if (txt == "ok") {
						alert("调节成功！");
						$('#w18').window('close');
						$('#ta5').datagrid({
						     url:'getrea.do',
						     loadMsg:'更新数据中......'
						 });
						$('#ta5').datagrid('getPager').pagination({displayMsg:'当前显示从{from}到{to}共{total}记录'});
					} else {
						alert("调节失败！");
					}
				}
			};
			xmlhttp.send(null);
	}
	}
	
	function close(){
		$('#w9').window('close');
	}
	
	function close1(){
		$('#w10').window('close');
	}
	
	function close2(){
		$('#w11').window('close');
	}
	
	function close3(){
		$('#w12').window('close');
	}
	
	function close4(){
		$('#w13').window('close');
	}
	
	function close5(){
		$('#w14').window('close');
	}
	
	function close6(){
		$('#w15').window('close');
	}
	
	function close7(){
		$('#w16').window('close');
	}
	
	function close8(){
		$('#w17').window('close');
	}
	
	function close9(){
		$('#w18').window('close');
	}
	
	function doExit(){
		location="exit.do";
	}
	
	function showPro(){
		var win = $.messager.progress({
			title:'请等待',
			msg:'排课进行中...'
		});
		setTimeout(function(){
			$.messager.progress('close');
			$('#ta5').datagrid('reload'); 
		},3200);
		var url = "doPaiKe.do";
		CreateXMLHttp();
		if (xmlhttp) {
			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var txt = xmlhttp.responseText;
					if (txt == "ok") {
						//alert("排课成功！");
						
					} else {
						alert("排课失败！");
						$.messager.progress('close');
					}
				}
			};
			xmlhttp.send(null);
		}
	}
	
	function doClean(){
		var url = "doClean.do";
		CreateXMLHttp();
		if (xmlhttp) {
			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var txt = xmlhttp.responseText;
					if (txt == "ok") {
						alert("清除成功！");
						$('#ta5').datagrid('reload');
					} else {
						alert("清除失败！");
					}
				}
			};
			xmlhttp.send(null);
		}
	}
	
	function showTu(){
	    location="any.jsp";
	}
	
	function doPrint(){
		location="getxsl.do";
	}
	
	function doPrintNew(){
		var name=document.getElementById("pin").value;
		var type=document.getElementById("pi").value;
		location="getxls.do?name="+name+"&type="+type;
	}
</script>
</head>
<body class="easyui-layout">
	<div region="north" border="false" style="width: 1380px; height: 190px">
		<img alt="" src="images/mytop.jpg">
	</div>
	<div id="hy" region="south" title="欢迎栏" split="true"
		style="height: 60px; padding: 10px; background: #efefef; collapsible: false;">
		<marquee>
			<font color="blue">欢迎您,<%
			Object user=session.getAttribute("user");
			if(user!=null){
				out.print(((User)user).getName());
			}
			%>！本排课系统必须填完整信息后才能排课哦,排课前请清除上次的排课数据,请注意！</font>
		</marquee>
	</div>
	<div region="east" icon="icon-reload" title="工具栏" split="true" style="width:180px;">
	<div align="center">
	    <div class="toolbar">
		<a  class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="javascript:doPrint()">打印课表</a><br/>
		<a  class="easyui-linkbutton" iconCls="icon-no" plain="true" onclick="javascript:doExit()">退&nbsp;&nbsp;&nbsp;&nbsp;出</a>
	    </div>
	</div>    
	</div>
	<div region="west" title="选择栏"
		style="overflow: hidden; width: 150px;">
		<div class="easyui-accordion" fit="true" border="false">		   
		<div title="排课管理" iconCls="icon-ok"
			style="overflow: hidden; padding: 10px;">
			<table>
				<tr>
					<td><a href="javascript:addTabs()"><img alt=""
							src="images/b4.jpg" border="0"></a></td>
				</tr>
				<tr>
					<td><a href="javascript:addTab4()"><img alt=""
							src="images/b10.jpg" border="0"></a></td>
				</tr>
			</table>
		</div>
		</div>
	</div>
	<div region="center">
		<div id="t" class="easyui-tabs" fit="true" style="overflow: hidden;">
			 <img id="img" alt="" src="images\z.jpg">
		</div>
	</div>
	<div id="w" iconCls="icon-save" title="班级管理">
	   <table id="ta1"></table>
	</div>
	<div id="w1" iconCls="icon-save" title="教师管理">
	   <table id="ta2"></table>
	</div>
	<div id="w2" iconCls="icon-save" title="教室管理">
	   <table id="ta3"></table>
	</div>
	<div id="w3" iconCls="icon-save" title="教室管理">
	   <table id="ta4"></table>
	</div>
	<div id="w4" iconCls="icon-save" title="排课表">
	    <table id="ta5"></table>
	</div>
	<div id="w5" iconCls="icon-search" title="查询" align="center" style="overflow:hidden">
	         <input id="sear"/><input type="hidden" id="pin" value=""><input type="hidden" id="pi" value=""><a href="javascript:doPrintNew()" id="xls" plain="true"  iconCls="icon-print">导出xls</a>  	
	         <table id="ta6"></table>
	</div>
	<div id="w6" iconCls="icon-save" title="手动排课">
	    <table id="ta7"></table>
	</div>
	<div id="w7" iconCls="icon-save" title="用户管理">
		<%
			Object list = request.getAttribute("List");
		%>
		<table border="1">
			<thead>
				<tr>
					<th>id</th>
					<th>用户名</th>
					<th>性别</th>
					<th>年龄</th>
					<th>电话</th>
					<th>身份</th>
				</tr>
			</thead>
			<tbody>
				<%
				    int userState=((User)user).getState();
					List<User> l = (List<User>) list;
					for (int i = 0; l != null && i < l.size(); i++) {
						int state = l.get(i).getState();
						if (state != -1 && state != 0&&userState==3) {
				%>
				<tr>
					<td width="100"><%=l.get(i).getId()%></td>
					<td width="100"><%=l.get(i).getName()%></td>
					<td width="100">
						<%
							String gender = l.get(i).getGender();
									if (gender != null && gender.equals("1")) {
										out.print("男");
									} else if (gender != null && gender.equals("2")) {
										out.print("女");
									}
						%>
					</td>
					<td width="100"><%=l.get(i).getAge()%></td>
					<td width="100"><%=l.get(i).getPhone()%></td>
					<td width="180">
						<%
							if (state == 1) {
						%> 普通用户 <input type="button" value="提权" id="<%=l.get(i).getId()%>"
						onclick="doUp(this.id)"> <input type="button" value="删除"
						id="<%=l.get(i).getId()%>" onclick="doDelete(this.id)"> <%
 	} else if (state == 2) {
 %> 管理员 <input type="button" value="降权" id="<%=l.get(i).getId()%>"
						onclick="doDown(this.id)"> <input type="button" value="删除"
						id="<%=l.get(i).getId()%>" onclick="doDelete(this.id)"> <%
 	} else  if (state == 3){%>
     超级管理员<%
 			}
 %>
					</td>
				</tr>
				<%
					}else if (state != -1 && state != 0&&userState==2 ) {
							%>
							<tr>
								<td width="100"><%=l.get(i).getId()%></td>
								<td width="100"><%=l.get(i).getName()%></td>
								<td width="100">
									<%
										String gender = l.get(i).getGender();
												if (gender != null && gender.equals("1")) {
													out.print("男");
												} else if (gender != null && gender.equals("2")) {
													out.print("女");
												}
									%>
								</td>
								<td width="100"><%=l.get(i).getAge()%></td>
								<td width="100"><%=l.get(i).getPhone()%></td>
								<td width="180">
									<%
										if (state == 1) {
									%> 普通用户 <input type="button" value="删除"
									id="<%=l.get(i).getId()%>" onclick="doDelete(this.id)"> <%
			 	} else if (state == 2) {
			 %> 管理员<%
			 	} else  if (state == 3){%>
			     超级管理员<%
			 			}
			 %>
								</td>
							</tr>
							<%
								}
				}
				%>
			
		</table>
	</div>
	<div id="c1" class="content">
		<form action="addUser.do">
			<table>
				<tr>
					<td align="right">用户名：</td>
					<td><input type="text" name="name" onblur=""></td>
				</tr>
				<tr>
					<td align="right">请输入密码：</td>
					<td><input type="password" name="password" id="pwd"></td>
				</tr>
				<tr>
					<td align="right">再次输入密码：</td>
					<td><input type="password" name="secondPassword" id="newpwd"
						onblur="doEqual()"></td>
				</tr>
				<tr>
					<td align="right">性别：</td>
					<td><select name="gender" style="width: 160px">
							<option value="1">男</option>
							<option value="2">女</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">年龄：</td>
					<td><select name="age" style="width: 160px">
							<%
								for (int i = 0; i < 120; i++) {
							%>
							<option value="<%=i%>"><%=i%></option>
							<%
								}
							%>
					</select></td>
				</tr>
				<tr>
					<td align="right">电话：</td>
					<td><input type="text" name="phone"></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="保存"></td>
				</tr>
			</table>
		</form>
		</tbody>
	</div>
	<div id="w8" iconCls="icon-save" title="用户添加">
		<form action="addUser.do">
			<table>
				<tr>
					<td align="right">用户名：</td>
					<td><input type="text" name="name" onblur=""></td>
				</tr>
				<tr>
					<td align="right">请输入密码：</td>
					<td><input type="password" name="password" id="pwd"></td>
				</tr>
				<tr>
					<td align="right">再次输入密码：</td>
					<td><input type="password" name="secondPassword" id="newpwd"
						onblur="doEqual()"></td>
				</tr>
				<tr>
					<td align="right">性别：</td>
					<td><select name="gender" style="width: 160px">
							<option value="1">男</option>
							<option value="2">女</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">年龄：</td>
					<td><select name="age" style="width: 160px">
							<%
								for (int i = 0; i < 120; i++) {
							%>
							<option value="<%=i%>"><%=i%></option>
							<%
								}
							%>
					</select></td>
				</tr>
				<tr>
					<td align="right">电话：</td>
					<td><input type="text" name="phone"></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="保存"></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="w9" iconCls="icon-save" title="班级添加">
	             班级名称：<input class="easyui-validatebox" type="text" id="bjname" name="name" required="true"><br/>
	             班级编号：<input class="easyui-validatebox" type="text" id="bjsid" name="sid" value="编号后台自动生成" readonly="readonly" required="true"><br/>
	             人&nbsp;&nbsp;&nbsp;&nbsp;数：<input class="easyui-validatebox" type="text" id="bjnumber" name="number" required="true"><br/>	
	             <div align="center">           
                   <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:add()">添加</a>
                   <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:close()">取消</a>
                 </div>                    
	</div>
	<div id="w10" iconCls="icon-save" title="班级修改">
	             班级名称：<input class="easyui-validatebox" type="text" id="bjnameed" name="name" required="true"><br/>
	           <input class="easyui-validatebox" type="hidden" id="bjsided" name="name" required="true"><br/>
	             人&nbsp;&nbsp;&nbsp;&nbsp;数：<input class="easyui-validatebox" type="text" id="bjnumbered" name="number" required="true"><br/>	
	             <div align="center">           
                   <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:edit()">修改</a>
                   <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:close1()">取消</a>
                 </div>
	</div>
	<div id="w11" iconCls="icon-save" title="教师添加">
	             教师名称：<input class="easyui-validatebox" type="text" id="jsname" name="jsname" required="true"><br/>
	             教师电话：<input class="easyui-validatebox" type="text" id="jsphone" name="jsphone" required="true"><br/>
	             年&nbsp;&nbsp;&nbsp;&nbsp;龄：<input class="easyui-validatebox" type="text" id="jsage" name="number" required="true"><br/>
	             教授课程：<input id="jscourse" name="sid" style="width: 155px"><br/>	
	             <div align="center">           
                   <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:add1()">添加</a>
                   <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:close2()">取消</a>
                 </div>                    
	</div>
	<div id="w12" iconCls="icon-save" title="教师修改">
	             教师名称：<input class="easyui-validatebox" type="text" id="jsnameed" name="jsname" required="true"><br/>
	             教师电话：<input class="easyui-validatebox" type="text" id="jsphoneed" name="jsphone" required="true"><br/>
	             年&nbsp;&nbsp;&nbsp;&nbsp;龄：<input class="easyui-validatebox" type="text" id="jsageed" name="number" required="true"><br/>
	             教授课程：<input id="jscourseed" name="sid" style="width: 155px"><br/>
	              <input type="hidden" id="jsid">	
	             <div align="center">           
                   <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:edit1()">修改</a>
                   <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:close3()">取消</a>
                 </div>
	</div>	
	<div id="w13" iconCls="icon-save" title="教室添加">
	             教室名称：<input class="easyui-validatebox" type="text" id="jssname" name="jsname" required="true"><br/>
	             教室人数：<input class="easyui-validatebox" type="text" id="jssnumber" name="jsphone" required="true"><br/>
	             <div align="center">           
                   <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:add2()">添加</a>
                   <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:close4()">取消</a>
                 </div>                    
	</div>
	<div id="w14" iconCls="icon-save" title="教室修改">
	              教室名称：<input class="easyui-validatebox" type="text" id="jssnameed" name="jsname" required="true"><br/>
	             教室人数：<input class="easyui-validatebox" type="text" id="jssnumbered" name="jsphone" required="true"><br/>
	             教室状态：<input class="easyui-validatebox" type="text" id="jssstateed" name="jsphone" required="true"><br/>        
	              <input type="hidden" id="jssid">	
	             <div align="center">           
                   <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:edit2()">修改</a>
                   <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:close5()">取消</a>
                 </div>
	</div>
	<div id="w15" iconCls="icon-save" title="课程添加">
	             课程编号：<input class="easyui-validatebox" type="text" id="kccid" name="jsname" value="编号后台生成" readonly="readonly" required="true"><br/>
	             课程名称：<input class="easyui-validatebox" type="text" id="kcname" name="jsphone" required="true"><br/>
	             课程课时：<input class="easyui-validatebox" type="text" id="kctime" name="jsphone" required="true"><br/>
	             所属班级：<input id="kcsid" name="jsphone" style="width: 155px"><br/>
	             <div align="center">           
                   <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:add3()">添加</a>
                   <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:close6()">取消</a>
                 </div>                    
	</div>
	<div id="w16" iconCls="icon-save" title="课程修改">
	               课程编号：<input class="easyui-validatebox" type="text" id="kccided" name="jsname" required="true"><br/>
	             课程名称：<input class="easyui-validatebox" type="text" id="kcnameed" name="jsphone" required="true"><br/>
	             课程课时：<input class="easyui-validatebox" type="text" id="kctimeed" name="jsphone" required="true"><br/>
	             所属班级：<input id="kcsided" name="jsphone" style="width: 155px"><br/>
	              <input type="hidden" id="kcid">	
	             <div align="center">           
                   <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:edit3()">修改</a>
                   <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:close7()">取消</a>
                 </div>
	</div>
	<div id="w17" iconCls="icon-save" title="手动排课">
	             班级名称：<input class="easyui-validatebox" type="text" id="sdbj" name="jsname" required="true" readonly="readonly"><br/>
	             课程名称：<input class="easyui-validatebox" type="text" id="sdkc" name="jsphone" required="true" readonly="readonly"><br/>
	             教师名称：<input id="sdjss" name="jsphone" style="width: 155px"><br/>
	             教室名称：<input id="sdjs" name="jsphone" style="width: 155px"><br/>
	             时间&nbsp;&nbsp;段：<input id="sdsj" name="jsphone" style="width: 155px"><br/>        
	              <input type="hidden" id="sdid">	
	             <div align="center">           
                   <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:edit4()">调节</a>
                   <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:close8()">取消</a>
                 </div>
	</div>		
	<div id="w18" iconCls="icon-save" title="自动排课修改">
	             班级名称：<input class="easyui-validatebox" type="text" id="rebj" name="jsname" required="true" readonly="readonly"><br/>
	             课程名称：<input class="easyui-validatebox" type="text" id="rekc" name="jsphone" required="true" readonly="readonly"><br/>
	             教师名称：<input id="rejss" name="jsphone" style="width: 155px"><br/>
	             教室名称：<input id="rejs" name="jsphone" style="width: 155px"><br/>
	             时间&nbsp;&nbsp;段：<input id="resj" name="jsphone" style="width: 155px"><br/>        
	              <input type="hidden" id="reid">	
	             <div align="center">           
                   <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:edit5()">调节</a>
                   <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:close9()">取消</a>
                 </div>
	</div>	
	<div id="mmm" style="width:120px">
		<div name="1">班级名称</div>
		<div name="2">教师姓名</div>
		<div name="3">教室名称</div>
	</div>	
	
	<div id="mm" class="easyui-menu" style="width:120px;">
		<div iconCls="icon-print" onclick="javascript:doPrint()">打印</div>
		<div class="menu-sep"></div>  
		<div onclick="javascript:doExit()">退出</div>
	</div>
</body>
</html>
