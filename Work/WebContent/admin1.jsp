<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.cheea.entity.*" %>
<%@ page import="java.util.*" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>后台管理系统</title>
<script type="text/javascript">

var xmlhttp = false;
function CreateXMLHttp(){
        try{
            xmlhttp = new XMLHttpRequest();  //尝试创建 XMLHttpRequest 对象，除 IE 外的浏览器都支持这个方法。
        }
        catch (e){
            try{
                xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");  //使用较新版本的 IE 创建 IE 兼容的对象（Msxml2.XMLHTTP）
            }
            catch (e){
                try{
                  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //使用较老版本的 IE 创建 IE 兼容的对象（Microsoft.XMLHTTP）。
                }
                catch (failed){
                      xmlhttp = false;  //如果失败则保证 request 的值仍然为 false。
                }
            }
        }
        return xmlhttp;
}

function doUp(id){ 
  var url = "up.do?id="+id;
  CreateXMLHttp();
  if(xmlhttp){
     xmlhttp.open("GET",url, true); 
     xmlhttp.onreadystatechange = getResult; 
     xmlhttp.send(null); 
  } 
} 

function getResult(){
  if (xmlhttp.readyState == 4 && xmlhttp.status == 200){ 
	  var txt=xmlhttp.responseText;
	  if(txt=="ok"){
		  location="getAll.do";
	  }else{
		  alert("修改失败！");
	  }
  }
}

function doDown(id){
	var url = "down.do?id="+id;
	  CreateXMLHttp();
	  if(xmlhttp){
	     xmlhttp.open("GET",url, true); 
	     xmlhttp.onreadystatechange =function(){
	    	 if (xmlhttp.readyState == 4 && xmlhttp.status == 200){ 
	    		  var txt=xmlhttp.responseText;
	    		  if(txt=="ok"){
	    			  location="getAll.do";
	    		  }else{
	    			  alert("修改失败！");
	    		  }
	    	  } 
	     }; 
	     xmlhttp.send(null); 
	  } 
}

function doEqual(){
	var pwd=document.getElementById("pwd").value;
	var newpwd=document.getElementById("newpwd").value;
	if(pwd!=newpwd){
		alert("两次密码不等！");
	}
}

function doDelete(id){
	var url = "delete.do?id="+id;
	  CreateXMLHttp();
	  if(xmlhttp){
	     xmlhttp.open("GET",url, true); 
	     xmlhttp.onreadystatechange =function(){
	    	 if (xmlhttp.readyState == 4 && xmlhttp.status == 200){ 
	    		  var txt=xmlhttp.responseText;
	    		  if(txt=="ok"){
	    			  alert("删除成功！");
	    			  location="getAll.do";
	    		  }else{
	    			  alert("删除失败！");
	    		  }
	    	  } 
	     }; 
	     xmlhttp.send(null); 
	  } 
}
</script>
<style type="text/css">
body{
	font-size:12px;
	background-image: url(images/bg.gif);
	background-repeat: repeat;
}
ul,li,h2{margin:0;padding:0;}
ul{list-style:none;}
#top{
	width:909px;
	height:26px;
	background-image: url(images/h2bg.gif);
	margin-top: 0;
	margin-right: auto;
	margin-bottom: 0;
	margin-left: auto;
	background-repeat: repeat-x;
}
#top h2{
	width:150px;
	height:24px;
	float:left;
	font-size:12px;
	text-align:center;
	line-height:20px;
	color:#0066FF;
	font-weight: bold;
	padding-top: 2px;
	border-right-width: 1px;
	border-left-width: 1px;
	border-right-style: solid;
	border-left-style: solid;
	border-right-color: #99BBE8;
	border-left-color: #99BBE8;
}
#top .jg {
	width: 5px;
	float: left;
	background-color: #DCE6F5;
	height: 26px;
}
#topTags{
	width:740px;
	height:24px;
	float:left;
	margin-top: 0;
	margin-right: auto;
	margin-bottom: 0;
	margin-left: auto;
	padding-top: 2px;
	border-right-width: 1px;
	border-left-width: 1px;
	border-right-style: solid;
	border-left-style: solid;
	border-right-color: #99BBE8;
	border-left-color: #99BBE8;
	padding-left: 10px;
}
#topTags ul li{
	float:left;
	width:100px;
	height:21px;
	margin-right:4px;
	display:block;
	text-align:center;
	cursor:pointer;
	padding-top: 3px;
	color: #15428B;
	font-size: 12px;
}
#main{
	width:909px;
	height:501px;
	background-color:#F5F7E6;
	margin-top: 0;
	margin-right: auto;
	margin-bottom: 0;
	margin-left: auto;
}
#main .jg {
	width: 5px;
	float: left;
	background-color: #DFE8F6;
	height: 500px;
}
#leftMenu{
	width:150px;
	height:500px;
	background-color:#DAE7F6;
	float:left;
	border-right-width: 1px;
	border-left-width: 1px;
	border-right-style: solid;
	border-left-style: solid;
	border-right-color: #99BBE8;
	border-left-color: #99BBE8;
}
#leftMenu ul{margin:10px;}
#leftMenu ul li{
	width:130px;
	height:22px;
	display:block;
	cursor:pointer;
	text-align:center;
	margin-bottom:5px;
	background-color: #D9E8FB;
	background-image: url(images/tabbg01.gif);
	background-repeat: no-repeat;
	background-position: 0px 0px;
	padding-top: 2px;
	line-height: 20px;
}
#leftMenu ul li a{
	color:#000000;
	text-decoration:none;
	background-image: url(images/tb-btn-sprite_03.gif);
	background-repeat: repeat-x;
}
#content{
	width:750px;
	height:500px;
	float:left;
	border-right-width: 1px;
	border-left-width: 1px;
	border-right-style: solid;
	border-left-style: solid;
	border-right-color: #99BBE8;
	border-left-color: #99BBE8;
	background-color: #DAE7F6;
}
.content{
	width:740px;
	height:490px;
	display:none;
	padding:5px;
	overflow-y:auto;
	line-height:30px;
	background-color: #FFFFFF;
}
#footer{
	width:907px;
	height:26px;
	background-color:#FFFFFF;
	line-height:20px;
	text-align:center;
	margin-top: 0;
	margin-right: auto;
	margin-bottom: 0;
	margin-left: auto;
	border-right-width: 1px;
	border-left-width: 1px;
	border-right-style: solid;
	border-left-style: solid;
	border-right-color: #99BBE8;
	border-left-color: #99BBE8;
	background-image: url(images/h2bg.gif);
	background-repeat: repeat-x;
}
.content1 {width:740px;height:490px;display:block;padding:5px;overflow-y:auto;line-height:30px;}
</style>
<script type="text/javascript">
window.onload=function(){
function $(id){return document.getElementById(id)}
var menu=$("topTags").getElementsByTagName("ul")[0];//顶部菜单容器
var tags=menu.getElementsByTagName("li");//顶部菜单
var ck=$("leftMenu").getElementsByTagName("ul")[0].getElementsByTagName("li");//左侧菜单
var j;
//点击左侧菜单增加新标签
for(i=0;i<ck.length;i++){
ck[i].onclick=function(){	
$("welcome").style.display="none"//欢迎内容隐藏
clearMenu();
this.style.background='url(images/tabbg02.gif)'
//循环取得当前索引
for(j=0;j<8;j++){
if(this==ck[j]){
if($("p"+j)==null){
openNew(j,this.innerHTML);//设置标签显示文字
 }
clearStyle();
$("p"+j).style.background='url(images/tabbg1.gif)';
clearContent();
$("c"+j).style.display="block";
   }
 }
return false;
  }
 }
//增加或删除标签
function openNew(id,name){
var tagMenu=document.createElement("li");
tagMenu.id="p"+id;
tagMenu.innerHTML=name+"&nbsp;&nbsp;"+"<img src='images/off.gif' style='vertical-align:middle'/>";
//标签点击事件
tagMenu.onclick=function(evt){
clearMenu();
ck[id].style.background='url(images/tabbg02.gif)'
clearStyle();
tagMenu.style.background='url(images/tabbg1.gif)';
clearContent();
$("c"+id).style.display="block";
}
//标签内关闭图片点击事件
tagMenu.getElementsByTagName("img")[0].onclick=function(evt){
evt=(evt)?evt:((window.event)?window.event:null);
if(evt.stopPropagation){evt.stopPropagation()} //取消opera和Safari冒泡行为;
this.parentNode.parentNode.removeChild(tagMenu);//删除当前标签
var color=tagMenu.style.backgroundColor;
//设置如果关闭一个标签时，让最后一个标签得到焦点
if(color=="#ffff00"||color=="yellow"){//区别浏览器对颜色解释
if(tags.length-1>=0){
clearStyle();
tags[tags.length-1].style.background='url(images/tabbg1.gif)';
clearContent();
var cc=tags[tags.length-1].id.split("p");
$("c"+cc[1]).style.display="block";
clearMenu();
ck[cc[1]].style.background='url(images/tabbg1.gif)';
 }
else{
clearContent();
clearMenu();
$("welcome").style.display="block"
   }
  }
}
menu.appendChild(tagMenu);
}
//清除菜单样式
function clearMenu(){
for(i=0;i<ck.length;i++){
ck[i].style.background='url(images/tabbg01.gif)';
 }
}
//清除标签样式
function clearStyle(){
for(i=0;i<tags.length;i++){
menu.getElementsByTagName("li")[i].style.background='url(images/tabbg2.gif)';
  }
}
//清除内容
function clearContent(){
for(i=0;i<7;i++){
$("c"+i).style.display="none";
 }
}
}
</script>
</head>
<body>
<div id="top">
<h2>管理菜单</h2>
<div class=jg></div>
<div id="topTags">
<ul>
</ul>
</div>
</div>
<div id="main"> 
<div id="leftMenu">
<ul>
<li>用户管理</li>
<li>用户添加</li>
<li>教师管理</li>
<li>教室管理</li>
<li>课程管理</li>
<li>相关数据</li>
<li>统计图表</li>
</ul>
</div>
<div class=jg></div>
<div id="content">
<div id="welcome" class="content" style="display:block;">
  <div align="center">
    <p>&nbsp;</p>
    <p><strong>欢迎使用后台管理系统！</strong></p>
    <p>&nbsp;</p>
    </div>
</div>
<div id="c0" class="content">
<%
Object list=request.getAttribute("List");
%>
<table border="1">
<tr>
<td>id号</td>
<td>用户名</td>
<td>性别</td>
<td>年龄</td>
<td>电话</td>
<td>身份</td>
</tr>
<%
  List<User> l=(List<User>)list;
  for(int i=0;l!=null&&i<l.size();i++){
	  int state=l.get(i).getState();
	  if(state!=-1&&state!=0&&state!=3){
	  %>
	  <tr>
	  <td width="100"><%=l.get(i).getId() %></td>
	  <td width="100"><%=l.get(i).getName()%></td>
	  <td width="100"><%
	  String gender=l.get(i).getGender();
	  if(gender!=null&&gender.equals("1")){
		  out.print("男");
	  }else if(gender!=null&&gender.equals("2")){
		  out.print("女");
	  }
	  %></td>
	  <td width="100"><%=l.get(i).getAge()%></td>
	  <td width="100"><%=l.get(i).getPhone()%></td>
	  <td width="180"><%
	  if(state==1){
		  %>
		  普通用户
		 <input type="button" value="提权" id="<%=l.get(i).getId()%>" onclick="doUp(this.id)">
		 <input type="button" value="删除" id="<%=l.get(i).getId()%>" onclick="doDelete(this.id)">
		  <%
	  }else if(state==2){
		 %>
		 管理员
		 <input type="button" value="降权" id="<%=l.get(i).getId()%>" onclick="doDown(this.id)">
		 <input type="button" value="删除" id="<%=l.get(i).getId()%>" onclick="doDelete(this.id)">
		 <% 
	  }else{
		  
	  }
	  %></td>
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
<tr><td align="right">用户名：</td><td><input type="text" name="name" onblur=""></td></tr>
<tr><td align="right">请输入密码：</td><td><input type="password" name="password" id="pwd"></td></tr>
<tr><td align="right">再次输入密码：</td><td><input type="password" name="secondPassword" id="newpwd" onblur="doEqual()"></td></tr>
<tr><td align="right">性别：</td><td><select name="gender" style="width: 160px">
<option value="1">男</option>
<option value="2">女</option>
</select></td></tr>
<tr><td align="right">年龄：</td><td>
<select name="age" style="width: 160px">
<%
for(int i=0;i<120;i++){
	%>
	<option value="<%=i%>"><%=i %></option>
	<%
}
%>
</select>
</td></tr>
<tr><td align="right">电话：</td><td><input type="text" name="phone"></td></tr>
<tr><td></td><td><input type="submit" value="保存"></td></tr>
</table>
</form>
</div>
<div id="c2" class="content">日志管理</div>
<div id="c3" class="content">留言管理</div>
<div id="c4" class="content">风格管理</div>
<div id="c5" class="content">系统管理</div>
<div id="c6" class="content">帮助信息</div>
</div>
</div>
<div id="footer">08网一 殷涛 版权所有</div>
</body>
</html>