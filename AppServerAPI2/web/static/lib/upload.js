
accessid= '';
accesskey= '';
host = '';
var signature;

g_dirname = ''
g_object_name = ''
g_object_name_type = ''
now = timestamp = Date.parse(new Date()) / 1000;

//生成oss图片上传签名
$.ajax({
    url: "/GoodsInfo/getSigna",
    data: null,
    type: 'POST',
    async: false,
    success: function (result) {
        if (result.status == "1") {
             // result = eval('(' + result + ')');
//                        respMap = result.obj;
            signature = result.obj.signature;
            accessid = result.obj.accessid;
            accesskey = result.obj.accessKey;
//                        policy = result.policy;
//                        dir = result.dir;
            host = result.obj.host;
        }else{
            alert("签名失败")
        }
    }
});

var policyText = {
    "expiration": "2020-01-01T12:00:00.000Z", //设置该Policy的失效时间，超过这个失效时间之后，就没有办法通过这个policy上传文件了
    "conditions": [
    ["content-length-range", 0, 1048576000] // 设置上传文件的大小限制
    ]
};

var policyBase64 = Base64.encode(JSON.stringify(policyText))
message = policyBase64
var bytes = Crypto.HMAC(Crypto.SHA1, message, accesskey, { asBytes: true }) ;
signature = Crypto.util.bytesToBase64(bytes);

function check_object_radio() {
    var tt = document.getElementsByName('myradio');
    for (var i = 0; i < tt.length ; i++ )
    {
        if(tt[i].checked)
        {
            g_object_name_type = tt[i].value;
            break;
        }
    }
}

function get_dirname()
{
    // dir = document.getElementById("dirname").value;
    // if (dir != '' && dir.indexOf('/') != dir.length - 1)
    // {
    //     dir = dir + '/'
    // }
    //alert(dir)
    g_dirname = 'goodimg/';
}

function random_string(len) {
　　len = len || 32;
　　var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';   
　　var maxPos = chars.length;
　　var pwd = '';
　　for (i = 0; i < len; i++) {
    　　pwd += chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}

function get_suffix(filename) {
    pos = filename.lastIndexOf('.')
    suffix = ''
    if (pos != -1) {
        suffix = filename.substring(pos)
    }
    return suffix;
}

function calculate_object_name(filename)
{
    /*if (g_object_name_type == 'local_name')
    {
        g_object_name += "${filename}"
    }
    else if (g_object_name_type == 'random_name')
    {
        suffix = get_suffix(filename)
        g_object_name = g_dirname + random_string(10) + suffix
    }*/
    suffix = get_suffix(filename);
    g_object_name = g_dirname + random_string(10) + suffix
    return ''
}

function get_uploaded_object_name(filename)
{
    // if (g_object_name_type == 'local_name')
    // {
    //     tmp_name = g_object_name
    //     tmp_name = tmp_name.replace("${filename}", filename);
    //     return tmp_name
    // }
    // else if(g_object_name_type == 'random_name')
    // {
    //     return g_object_name
    // }
    return g_object_name
}

function set_upload_param(up, filename, ret)
{
    g_object_name = g_dirname;
    if (filename != '') {
        suffix = get_suffix(filename)
        calculate_object_name(filename)
    }
    new_multipart_params = {
        'key' : g_object_name,
        'policy': policyBase64,
        'OSSAccessKeyId': accessid, 
        'success_action_status' : '200', //让服务端返回200,不然，默认会返回204
        'signature': signature,
    };

    up.setOption({
        'url': host,
        'multipart_params': new_multipart_params
    });

    up.start();
}

var uploader = new plupload.Uploader({
	runtimes : 'html5,flash,silverlight,html4',
	browse_button : 'selectfiles', 
    //multi_selection: false,
	container: document.getElementById('container'),
	flash_swf_url : 'lib/plupload-2.1.2/js/Moxie.swf',
    silverlight_xap_url : 'lib/plupload-2.1.2/js/Moxie.xap',
    url : 'http://caidashi.oss-cn-hangzhou.aliyuncs.com',

	init: {
		PostInit: function() {
			document.getElementById('ossfile').innerHTML = '';
			document.getElementById('postfiles').onclick = function() {
            set_upload_param(uploader, '', false);
            return false;
			};
		},

		FilesAdded: function(up, files) {
			plupload.each(files, function(file) {
				document.getElementById('ossfile').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
				+'<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
				+'</div>';
			});
		},

		BeforeUpload: function(up, file) {
            //check_object_radio();
            get_dirname();
            set_upload_param(up, file.name, true);
        },

		UploadProgress: function(up, file) {
			var d = document.getElementById(file.id);
			d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
            var prog = d.getElementsByTagName('div')[0];
			var progBar = prog.getElementsByTagName('div')[0]
			progBar.style.width= 2*file.percent+'px';
			progBar.setAttribute('aria-valuenow', file.percent);
		},

		FileUploaded: function(up, file, info) {
            if (info.status == 200)
            {
                //document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = 'upload to oss success, object name:' + get_uploaded_object_name(file.name);
                $.messager.show({title:'提示信息', msg:'上传成功',showType:'show'});
                var key = get_uploaded_object_name(file.name)
                document.querySelector('img').src="http://caidashi.oss-cn-hangzhou.aliyuncs.com/"+key;
                $("#class_pic").val(key);
            }
            else
            {
                //document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;s
            } 
		},

		Error: function(up, err) {
			document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
            //$.messager.show({title:'提示信息', msg:'上传失败，请联系工作人员',showType:'show'});
		}
	}
});

uploader.init();
