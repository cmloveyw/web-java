/*
 * 公共方法
 */
var dl = function(url){
	if(url){
		$('<form action="'+ url +'" method="get"></form>')
			.appendTo('body').submit().remove();
	}else{
		alert("导出失败");
	}
};
$.fn.serializeJson = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

$.fn.serializeJsonEncode = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = encodeURIComponent(this.value) || '';
		}
	});
	return o;
};

/*
 * 重置表单，$("#***").resetForm()即可
 */
$.fn.resetForm = function() {
	$(this).find("input[type='text']").each(function() {
		$(this).val("");
	});
	$(this).find(" input[type='hidden']").each(function() {
		$(this).val("");
	});
	$(this).find("textarea").each(function() {
		$(this).val("");
	});
	$(this).find("select").each(function() {
		$(this).val(0);
	});
    $(this).find('input, textarea, select').each(function() {
        $("#res_" + $(this).id).remove();
	});
};

/*
 * 表单控件验证
 */
var globalOptions = {};
$.fn.validation = function() {
	globalOptions = $.extend({}, $.fn.validation.defaults);
	$('input, textarea')
			.each(
					function() {
						var el = $(this), valid = (el.attr('check-type') == undefined) ? null
								: el.attr('check-type').split(' ');
						if (valid != null && valid.length > 0) {
							el.blur(function() { // 失去焦点时
								var result = validateField(this, valid);
								$("#res_" + el.id).remove();
								if (result != null && result != '') {
									var msg = "<span id='res_" + el.id
											+ "' style='color:red'>" + result
											+ "</span>";
									el.after(msg);
									// el.focus();
								}
							});
						}
					});
};

$.fn.validation.defaults = {
	validRules: [{
		name: 'required',
		validate: function(value) {
			return ($.trim(value) == '');
		},
		defaultMsg: '请输入内容'
	}, {
		name: 'number',
		validate: function(value) {
			return (!/^[0-9]\d*$/.test(value));
		},
		defaultMsg: '请输入非负整数'
	}, {
		name: 'nullOrNumber',
		validate: function(value) {
			return (!/^\s*$|^[0-9]\d*$/.test(value));
		},
		defaultMsg: '请输入非负整数'
	},{
		name: 'float',
		validate: function(value) {
			return (!/^\d+(\.\d+)?$/.test(value));
		},
		defaultMsg: '请输入正确的金额'
	}, {
		name: 'mail',
		validate: function(value) {
			return (!/^[a-zA-Z0-9]{1}([\._a-zA-Z0-9-]+)(\.[_a-zA-Z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+){1,3}$/
				.test(value));
		},
		defaultMsg: '请输入邮箱地址'
	}, {
		name: 'char',
		validate: function(value) {
			return (!/^[a-z\_\-A-Z]*$/.test(value));
		},
		defaultMsg: '请输入英文字符'
	}, {
		name: 'chinese',
		validate: function(value) {
			return (!/^[\u4e00-\u9fff]$/.test(value));
		},
		defaultMsg: '请输入汉字'
	}, {
		name: 'url',
		validate: function(value) {
			return (!/^(http):\/\/|^(https):\/\//
				.test(value));
		},
		defaultMsg: '请输入正确的URL地址'
	},{
		name: 'urlOrNull',
		validate: function(value) {
				return (!/^\s*$|^(http):\/\//
						.test(value));
		},
		defaultMsg: '请输入正确的URL地址'
	},{
		name: 'mobile',
		validate: function(value) {
			return (!/^(86)?1[3|4|5|7|8][0-9]\d{8}$/.test(value));
		},
		defaultMsg: '请输入正确的手机号码'
	}, {
		name: 'gt',
		validate: function(value) {
			return (!/^[1-9]\d*$/.test(value));
		},
		defaultMsg: '请输入大于0的整数'
	}, {
		name: 'gtChinese',
		validate: function(value) {
			return (!/^[\s\S]{0,20}$/.test(value));
		},
		defaultMsg: '不能大于20个字'
	}, {
		name: 'fiveChinese',
		validate: function(value) {
			return (!/^[\s\S]{0,49}$/.test(value));
		},
		defaultMsg: '不能大于50个字'
	}, {
		name: 'btChinese',
		validate: function(value) {
			return (!/^[\s\S]{0,250}$/.test(value));
		},
		defaultMsg: '不能大于250个字'
	}, {
		name: 'eqZeroMoney',
		validate: function(value) {
			return (!/^(([1-9]\d*)|0)(\.(\d){1,2})?$/.test(value));
		},
		defaultMsg: '大于或等于0,且最多两位小数'
	}, {
		name: 'money',
		validate: function(value) {
			return (!/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/.test(value));
		},
		defaultMsg: '大于0,且最多两位小数'
	},{
		name: 'shakeProbability',
		validate: function(value) {
				return (!/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,3})?$/.test(value));
		},
		defaultMsg: '大于0,且最多三位小数'
	},{
		name: 'time',
		validate: function(value) {
			return (!/^\d*\.{0,1}\d{0,1}$/.test(value));
		},
		defaultMsg: '只能包含数字,且小数点最多一位'
	}, {
		name: 'fullCoupon',
		validate: function(value) {
			return (!/\d+(\/{1})+\d/.test(value));
		},
		defaultMsg: '格式为：100/20'
	}, {
		name: 'detailGtChinese',
		validate: function(value) {
			return (!/^[\s\S]{0,32762}$/.test(value));
		},
		defaultMsg: '内容过长'
	}, {
		name: 'spacebar',
		validate: function(value) {
			return (!/^((?! {2,}).)+$/.test(value));
		},
		defaultMsg: '不能连续有两个空格'
	}, {
		name: 'percent',
		validate: function(value) {
			return (!/^[1-9]\d*\%$/.test(value));
		},
		defaultMsg: '格式：10%'
	}, {
		name: 'age',
		validate: function(value) {
			return (!/^\s*$|^[0-9]\d*$/.test(value));
		},
		defaultMsg: '请输入正确的年龄'
	},{
		name: 'cardCode',
		validate: function(value) {
			return (!/^\d{16}$/.test(value));
		},
		defaultMsg: '会员卡号输入不正确'
	},{
		name: 'phoneOrMobile',
		validate: function(value) {
			if(!/^0\d{2,3}-?\d{7,8}$/.test(value)){//先看是不是电话,如果不是在看是不是手机号
				if(!/^(86)?1[3|4|5|7|8][0-9]\d{8}$/.test(value)){
					return true;
				}else {
					return false;
				}
			}else{
				return false;
			}
		},
		defaultMsg: '请输入正确的手机号或者电话号'
	},{
        name: 'telephone',
        validate: function(value) {
            return (!/^0\d{2,3}-?\d{7,8}$/.test(value));
        },
        defaultMsg: '请输入正确的座机号码'
    }, {
		name: 'vipCardCode',
		validate: function(value) {
			return (!/^(86)?1[3|4|5|7|8][0-9]\d{8}$|^\d{16}$/.test(value));
		},
		defaultMsg: '请输入正确的手机号码或会员卡号'
	}]
};

var validateForm = function(obj) { // 验证表单
	var flag = true;
	$(obj)
			.find('input, textarea, select')
			.each(
					function() {
						var el = $(this), valid = (el.attr('check-type') == undefined) ? null
								: el.attr('check-type').split(' ');
						if (valid != null && valid.length > 0) {
							var result = validateField(this, valid);
							$("#res_" + el.id).remove();
							if (result != null && result != '') {
								var msg = "<span id='res_" + el.id
										+ "' style='color:red'>" + result
										+ "</span>";
								el.after(msg);
								// el.focus();
								flag = false;
							}
						}
						return flag;
					});
	return flag;
};

var validateField = function(field, valid) { // 验证字段
	var el = $(field), error = false;
	for (var i = 0; i < valid.length; i++) {
		var x = true, flag = valid[i], msg = (el.attr(flag + '-message') == undefined) ? null
				: el.attr(flag + '-message');
		;
		if (flag.substr(0, 1) == '!') {
			x = false;
			flag = flag.substr(1, flag.length - 1);
		}

		var rules = globalOptions.validRules;
		for (var j = 0; j < rules.length; j++) {
			var rule = rules[j];
			if (flag == rule.name) {
				if (rule.validate.call(field, el.val()) == x) {
					error = true;
					return (msg == null) ? rule.defaultMsg : msg;
					break;
				}
			}
		}
		if (error) {
			break;
		}
	}
};

var getScript = function(src, callback) {
	var script = document.createElement("script");
	script.type = "text/javascript";
	if (script.readyState) { // IE
		script.onreadystatechange = function() {
			if (script.readyState == "loaded" || script.readyState == "complete") {
				script.onreadystatechange = null;
				callback;
			}
		};
	} else { // Others
		script.onload = callback;
	}
	script.src = src;
	document.getElementsByTagName("head")[0].appendChild(script);
};

/*
 * 接收url传参
 */
var get_param = function(path, name) {
	var result = path.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
	if (result == null || result.length < 1) {
		return "";
	}
	return result[1];
};

/*
 * 判断客户端类型
 */
var isMobile = function() {
	var type = navigator.userAgent.match(/.*Mobile.*/) ? true : false;
	return type;
};

var dateFormat = {
	DateToUnix : function(string) {
		var f = string.split(' ', 2);
		var d = (f[0] ? f[0] : '').split('-', 3);
		var t = (f[1] ? f[1] : '').split(':', 3);
		return (new Date(parseInt(d[0], 10) || null,
				(parseInt(d[1], 10) || 1) - 1, parseInt(d[2], 10) || null,
				parseInt(t[0], 10) || null, parseInt(t[1], 10) || null,
				parseInt(t[2], 10) || null)).getTime() / 1000;
	},
	UnixToDate : function(unixTime, isFull, timeZone) {
		if (typeof (timeZone) == 'number') {
			unixTime = parseInt(unixTime) + parseInt(timeZone) * 60 * 60;
		}
		var time = new Date(unixTime * 1000);
		var ymdhis = "";
		ymdhis += time.getUTCFullYear() + "-";
		ymdhis += ((time.getUTCMonth() + 1) < 10 ? ("0" + (time.getUTCMonth() + 1))
				: (time.getUTCMonth() + 1))
				+ "-";
		ymdhis += time.getUTCDate() < 10 ? ("0" + time.getUTCDate()) : time
				.getUTCDate();
		if (isFull === true) {
			ymdhis += " " + time.getUTCHours() + ":";
			ymdhis += (time.getUTCMinutes() < 10 ? ("0" + time.getUTCMinutes())
					: time.getUTCMinutes())
					+ ":";
			ymdhis += (time.getUTCSeconds() < 10 ? ("0" + time.getUTCSeconds())
					: time.getUTCSeconds());
		}
		return ymdhis;
	},
	//强时间戳转为时间字符串（yyyy-MM-dd）
	UnixToTime : function(unixTime, isFull, timeZone) {
		if (typeof (timeZone) == 'number') {
			unixTime = parseInt(unixTime) + parseInt(timeZone) * 60 * 60;
		}
		var time = new Date(unixTime * 1000);
		var ymdhis = "";
		ymdhis += time.getFullYear() + "-";
		ymdhis += ((time.getMonth() + 1) < 10 ? ("0" + (time.getMonth() + 1))
				: (time.getMonth() + 1))
			+ "-";
		ymdhis += time.getDate() < 10 ? ("0" + time.getDate()) : time
			.getDate();
		if (isFull === true) {
		 ymdhis += " " + time.getHours() + ":";
		 ymdhis += (time.getMinutes() < 10 ? ("0" + time.getMinutes())
		 : time.getMinutes()) + ":";
		 ymdhis += (time.getSeconds() < 10 ? ("0" + time.getSeconds())
			 : time.getSeconds());
		 }
		return ymdhis;
	},
	getDate : function(day) {
		var zdate = new Date();
		var sdate = zdate.getTime() - (1 * 24 * 60 * 60 * 1000);
		var edate = new Date(sdate - (day * 24 * 60 * 60 * 1000))
				.format("yyyy-MM-dd");
		;
		return edate;
	},
	
	getMonth : function(day) {
		var zdate = new Date();
		var sdate = zdate.getTime() - (1 * 24 * 60 * 60 * 1000);
		var edate = new Date(sdate - (day * 24 * 60 * 60 * 1000))
				.format("yyyy-MM");
		;
		return edate;
	},
	getDateByBeginDate : function(bedinDate, day) {
		var zdate = new Date(bedinDate);
		var sdate = zdate.getTime() - (1 * 24 * 60 * 60 * 1000);
		var edate = new Date(sdate - (day * 24 * 60 * 60 * 1000))
				.format("yyyy-MM-dd");
		;
		return edate;
	},
	getLastDay : function(year, month) {
		var new_year = year; // 取当前的年份
		var new_month = month++; // 取下一个月的第一天，方便计算（最后一天不固定）
		if (month > 12) // 如果当前大于12月，则年份转到下一年
		{
			new_month -= 12; // 月份减
			new_year++; // 年份增
		}
		var new_date = new Date(new_year, new_month, 1); // 取当年当月中的第一天
		return (new Date(new_date.getTime() - 1000 * 60 * 60 * 24)).getDate(); // 获取当月最后一天日期
	},
	getTime:function(day){
		 var d = new Date(),str = '';
		 str += dateFormat.getDate(day)+' ';
		 str += d.getHours()+':'; 
		 str  += d.getMinutes()+':'; 
		str+= d.getSeconds(); 
		return str;
	},

/**
 * 获取月份第一天
 * @param str 日期字符串 格式yyyy-MM-dd
 */
getFirst:function(str){
	var reg = /\d+$/;
	var d = str.match(reg);
	if(d){
		if(d.length===1){
			return str.replace(reg,"1");
		}else if(d.length===2){
			return str.replace(reg,"01");
		}else{
			return str.replace(reg,"01");
		}
	}else{
		return str.replace(reg,"01");
	}
}
};


// 获取前几天、后几天
Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

var cookie = {
	addCookie : function(name, value, options) {
		if (arguments.length > 1 && name != null) {
			if (options == null) {
				options = {};
			}
			if (value == null) {
				options.expires = -1;
			}
			if (typeof options.expires == "number") {
				var time = options.expires;
				var expires = options.expires = new Date();
				expires.setTime(expires.getTime() + time * 1000);
			}
			document.cookie = encodeURIComponent(String(name))
					+ "="
					+ encodeURIComponent(String(value))
					+ (options.expires ? "; expires="
							+ options.expires.toUTCString() : "")
					+ (options.path ? "; path=" + options.path : "")
					+ (options.domain ? "; domain=" + options.domain : ""),
					(options.secure ? "; secure" : "");
		}
	},
	getCookie : function(name) {
		if (name != null) {
			var value = new RegExp("(?:^|; )"
					+ encodeURIComponent(String(name)) + "=([^;]*)")
					.exec(document.cookie);
			return value ? decodeURIComponent(value[1]) : null;
		}
	},
	removeCookie : function(name, options) {
		cookie.addCookie(name, null, options);
	}
};

// 以模态框的样式弹出提示
var modalAlert = function(theme, msg, height, width) {
	if (window.WonderCityAdminAndroidPad) {
		window.WonderCityAdminAndroidPad.alertDialogInfo(msg);
	} else {
		var css = "alert-warning";
		switch (theme) {
		case "warn":
			css = "alert-warning";
			break;
		case "info":
			css = "alert-info";
			break;
		case "danger":
			css = "alert-danger";
			break;
		case "primary":
			css = "alert-primary";
			break;
		case "success":
			css = "alert-success";
			break;
		}
		var w = $(document.body).width();
		var h = isMobile() ? window.screen.height : $(document.body).height();
		var str = "<div style=\"width:100%;\">";
		str += "<div id=\"modalAlert\" class=\"alert "
				+ css
				+ " alert-dismissible fade in\" role=\"alert\" style=\"margin:0px auto;top:-100px;left:"
				+ (w * 0.5 - width * 0.5)
				+ "px;position:absolute;z-index:1000;\">";
		str += " <button type=\"button\"  class=\"close closeModalAlert\" data-dismiss=\"alert\">";
		str += "<span aria-hidden=\"true\">×</span><span class=\"sr-only\">Close</span></button>";
		str += msg;
		str += "</div></div>";
		$("body").append(str);

		$("#modalAlert").animate({
			top : h * 0.4 + 'px',
			height : height + 'px',
			width : width + 'px'
		});
		var t;
		(function() {
			t = setTimeout(function() {
				clearTimeout(t);
				$(".closeModalAlert").click();
			}, 10000);
		})();
	}
};


// loading效果
var loadStart = function(picUrl) {
	var isIE = (document.all) ? true : false;
	var isIE6 = isIE
			&& ([ /MSIE (\d)\.0/i.exec(navigator.userAgent) ][0][1] == 6);

    var str = "<div id='loading' style=\"display: none;margin:0px auto\">";
    if(picUrl)
        str += "<img alt=\"loading\" src="+picUrl+" style=\"padding-top: 25px\" />'";
    else
        str += "<img alt=\"loading\" src=\"resources/portal/images/loading.gif\" style=\"padding-top: 25px\" />'";
    str += " </div>";


	$("body").append(str);

	var newbox = document.getElementById('loading');
	var layer = document.createElement("div");
	newbox.style.zIndex = "9999";
	newbox.style.display = "block";
	newbox.style.position = !isIE6 ? "fixed" : "absolute";
	newbox.style.left = "50%";
	newbox.style.top = "40%";
	newbox.style.marginTop = -newbox.offsetHeight / 2 + "px";
	newbox.style.marginLeft = -newbox.offsetWidth / 2 + "px";

	layer.id = "layer";
	layer.style.width = layer.style.height = "100%";
	layer.style.position = !isIE6 ? "fixed" : "absolute";
	layer.style.top = layer.style.left = 0;
	layer.style.backgroundColor = "#f2f2f2";
	layer.style.zIndex = "9998";
	layer.style.opacity = "0.4";
	document.body.appendChild(layer);

	this.loadEnd = function() {
		$("body").removeChild(newbox);
		$("body").removeChild(layer);
	};
};

var security = {
	generateMixed : function(n) {
		var chars = [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' ];
		var res = "";
		for (var i = 0; i < n; i++) {
			var id = Math.ceil(Math.random() * 35);
			res += chars[id];
		}
		return res;
	},
	CharToHex : function(str) {
		var out, i, len, c, h;
		out = "";
		len = str.length;
		i = 0;
		while (i < len) {
			c = str.charCodeAt(i++);
			h = c.toString(16);
			if (h.length < 2)
				h = "0" + h;
			out += "\\x" + h + " ";
			if (i > 0 && i % 8 == 0)
				out += "\r";
		}
		return out;
	}
};

// 动态加载js、CSS文件
$.extend({
	includePath : '',
	include : function(file) {
		var files = typeof file == "string" ? [ file ] : file;
		for (var i = 0; i < files.length; i++) {
			var name = files[i].replace(/^\s|\s$/g, "");
			var att = name.split('.');
			var ext = att[att.length - 1].toLowerCase();
			var isCSS = ext == "css";
			var tag = isCSS ? "link" : "script";
			var attr = isCSS ? " type='text/css' rel='stylesheet' "
					: " language='javascript' type='text/javascript' ";
			var link = (isCSS ? "href" : "src") + "='" + $.includePath + name
					+ "'";
			if ($(tag + "[" + link + "]").length == 0) {
				$("head").append("<" + tag + attr + link + "></" + tag + ">");
			}
		}
	}
});

// 获取项目根目录
var basePath = function() {
	var path = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = path.indexOf(pathName);
	var localhostPath = path.substring(0, pos);
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return localhostPath + projectName;
};

/* 验证是否为手机号码********************************************* */
String.prototype.Trim = function() {
	var m = this.match(/^\s*(\S+(\s+\S+)*)\s*$/);
	return (m == null) ? "" : m[1];
};
String.prototype.addTrim = function() {
	var m = this.match(/^\s*(\S+(\s+\S+)*)\s*$/);
	return (m == null) ? "" : m[1];
};
String.prototype.isMobile = function() {
	return (/^(86)?1[3|4|5|7|8][0-9]\d{8}$/.test(this.Trim()));
}
String.prototype.TrimEnd = function(c) {
	if (c == null || c == "") {
		var str = this;
		var rg = "//s/";
		var i = str.length;
		while (rg.test(str.charAt(--i)))
			;
		return str.slice(0, i + 1);
	} else {
		var str = this;
		var rg = new RegExp(c);
		var i = str.length;
		while (rg.test(str.charAt(--i)))
			;
		return str.slice(0, i + 1);
	}
};
/*
 * 数据增加判断是否存在某元素属性
 */
Array.prototype.contains = function(obj) {
	var i = this.length;
	while (i--) {
		if (this[i] === obj) {
			return true;
		}
	}
	return false;
}
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == val)
			return i;
	}
	return -1;
};
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};
/**屏蔽鼠标右键 */
/*document.oncontextmenu = function() {
	return false;
};*/

/*
 * 判断是否为空值
 */
var is_null = function(value) {
	if (value == "" || value == null || value == "undefined" || value == "null") {
		return true;
	}else{
		return false;
	}
};

function setOtherParam(param) {
	if(!param.mallId) {
		param.mallId = config.loginInfo.mallId;
	}
	if(!param.appUid) {
		param.appUid = config.loginInfo.managerId;
	}
	param.clientType = "adminweb";
	param.appVersion = "1.0";
	param.osVersion = navigator.platform;
	param.model = "PC";
	return param;
}
function getOtherParamString(param){
	return "mallId=" + param.mallId + "&appUid=" + param.appUid + "&clientType=" + param.clientType + "&appVersion=" + param.appVersion + "&osVersion=" + param.osVersion + "&model=" + param.model;
}
