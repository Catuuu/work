if(window.WebSocket) {
    var client, destination;
    //$('#connect_form').submit(function () {
        var url = "ws://192.168.1.233:61614";
        var login = "admin";
        var passcode = "password";
        destination = "/topic/order.print.test";
        client = Stomp.client(url);
        // this allows to display debug logs directly on the web page
        /*client.debug = function (str) {
            $("#debug").append(document.createTextNode(str + "\n"));
        };*/

        // the client is notified when it is connected to the server.
        client.connect(login, passcode, function (frame) {
            //client.debug("connected to Stomp");
            client.subscribe(destination, function (message) {
                //alert(message);
                alert(message.body);
                //viewToWord();
            });
        });
}else {
    alert("当前打印机不支持websoct打印，请更换浏览器");
}




function viewToWord() {
        alert("打印1111111");
             //当前时间
             var Time;
             var today = new Date();
             Time = today.toLocaleString();
        alert("打印222");
        try {
            alert("打印333");
            // 创建ActiveXObject对象
            wdapp = new ActiveXObject("Word.Application");
        }catch (e) {
            alert("打印44444");
            console.log("无法调用Office对象，！", e)
            wdapp = null;
            return;
        }
        alert("打印");
        wdapp.Documents.Open("f:\\PrinterTemplate1.doc"); //打开本地(客户端)word模板
        wddoc = wdapp.ActiveDocument;
        wddoc.Bookmarks("OrderNum").Range.Text = "201509080959" + "\n";
        wddoc.Bookmarks("OrderName").Range.Text = "郑斌" + "\n";
        wddoc.Bookmarks("OrderAddress").Range.Text = "www.cnblogs.com/zhengbin" + "\n";
        wddoc.Bookmarks("OrderPhoneNum").Range.Text = "QQ:1746788394" + "\n";
        wddoc.Bookmarks("OrderDaocanTime").Range.Text = "10:00-11:00" + "\n";
        wddoc.Bookmarks("OrderTime").Range.Text = "09-08 10:15";
        //添加表格
        var myTable = wddoc.Tables.Add (wddoc.Bookmarks("OrderCart").Range,3,3);//(赋值区域,行数,列数)
        //隐藏边框
        var table=wdapp.ActiveDocument.Tables(1);
        table.Borders(-1).LineStyle=0;
        table.Borders(-2).LineStyle=0;
        table.Borders(-3).LineStyle=0;
        table.Borders(-4).LineStyle=0;
        table.Borders(-5).LineStyle=0;
        table.Borders(-6).LineStyle=0;
        for(i=1;i<=3;i++){//行
                //第一列
                with (myTable.Cell(i,1).Range){
                        font.Size = 8;//调整字体大小
                        InsertAfter("博客园"+i);//插入的内容
                    }
                //第二列
                with(myTable.Cell(i,2).Range){
                        font.Size = 8;
                        InsertAfter(i);
                        ParagraphFormat.Alignment=1;//表格内容对齐:0-左对齐 1-居中 2-右对齐
                    }
                 //第三列
                 with(myTable.Cell(i,3).Range){
                         font.Size = 8;
                         InsertAfter("无价");
                         ParagraphFormat.Alignment=2;
                     }
             }
           wddoc.saveAs("f:\\PrinterTemp_cnblogs.doc"); //保存临时文件word
           wddoc.Bookmarks("TotalPrice").Range.Text = "无价" + "\n";
           wddoc.Bookmarks("Time").Range.Text = Time;
           //wdapp.ActiveDocument.ActiveWindow.View.Type = 1;
           wdapp.visible = false; //word模板是否可见
           wdapp.Application.Printout(); //调用自动打印功能
           wdapp.quit();
           wdapp = null;
       }