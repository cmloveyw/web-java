/*
 */
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

/*
 * 重置表单，$("#***").resetForm即可
 * 
 */
$.fn.resetForm = function() {
	return this.each(function() {
		var type = this.type, tag = this.tagName.toLowerCase();
		if (tag == 'form')
			return $(':input', this).clearForm();
		if (type == 'text' || type == 'password' || tag == 'textarea')
			this.value = '';
		else if (type == 'checkbox' || type == 'radio')
			this.checked = false;
		else if (tag == 'select')
			this.selectedIndex = -1;
	});
};

/*
 * 表单控件验证
 */
$.fn.validation = function() {
	var globalOptions = {};
	globalOptions = $.extend({}, $.fn.validation.defaults);
	$('input, textarea').each(function() {
		var el = $(this),
		valid = (el.attr('check-type') == undefined) ? null : el.attr('check-type').split(' ');
						if (valid != null && valid.length > 0) {
							el.blur(function() { // 失去焦点时
								var result = validateField(this, valid);
								$("#res_" + el.id).remove();
								if (result != null && result != '') {
					var msg = "<span id='res_" + el.id + "' style='color:red'>" + result + "</span>";
									el.after(msg);
									el.focus();
								}
							});
						}
	});
};

$.fn.validation.defaults = {
	validRules: [{
				name : 'required',
				validate : function(value) {
					return ($.trim(value) == '');
				},
				defaultMsg : '请输入内容'
	}, {
				name : 'number',
				validate : function(value) {
					return (!/^[0-9]\d*$/.test(value));
				},
				defaultMsg : '请输入正整数'
	}, {
				name: 'float',
				validate: function(value) {
					return (!/^\d+(\.\d+)?$/.test(value));
				},
				defaultMsg: '请输入正确的金额'
	}, {
				name : 'mail',
				validate : function(value) {
					return (!/^[a-zA-Z0-9]{1}([\._a-zA-Z0-9-]+)(\.[_a-zA-Z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+){1,3}$/
							.test(value));
				},
				defaultMsg : '请输入邮箱地址'
	}, {
				name : 'char',
				validate : function(value) {
					return (!/^[a-z\_\-A-Z]*$/.test(value));
				},
				defaultMsg : '请输入英文字符'
	}, {
				name : 'chinese',
				validate : function(value) {
					return (!/^[\u4e00-\u9fff]$/.test(value));
				},
				defaultMsg : '请输入汉字'
	}, {
				name : 'url',
				validate : function(value) {
					return (!/^(https?|ftp|http):\/\/([A-z0-9]+[_\-]?[A-z0-9]+\.)*[A-z0-9]+\-?[A-z0-9]+\.[A-z]{2,}(\/.*)*\/?(.com|.cn|.net)+/
							.test(value));
				},
				defaultMsg : '请输入正确的URL地址'
			} , {
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
				defaultMsg : '请输入大于0的整数'
			}, {
				name: 'gtChinese',
				validate: function(value) {
					return (!/^[\s\S]{0,19}$/.test(value));
				},
				defaultMsg: '不能大于20个字'
			}, {
				name : 'money',
				validate : function(value) {
					return (!/^\d*\.{0,1}\d{0,2}$/.test(value));
				},
				defaultMsg : '不能为负数,且最多两位小数'
			}, {
				name : 'time',
				validate : function(value) {
					return (!/^\d*\.{0,1}\d{0,1}$/.test(value));
				},
				defaultMsg : '只能包含数字,且小数点最多一位'
			}, {
				name : 'fullCoupon',
				validate : function(value) {
					return (!/\d+(\/{1})+\d/.test(value));
				},
				defaultMsg : '格式为：100/20'
			}, {
				name : 'detailGtChinese',
				validate : function(value) {
					return (!/^[\s\S]{0,32762}$/.test(value));
				},
				defaultMsg : '内容过长'
			}, {
				name : 'spacebar',
				validate : function(value) {
					return (!/^((?! {2,}).)+$/.test(value));
				},
				defaultMsg : '不能连续有两个空格'
			}]
};

var validateForm = function(obj) {// 验证表单
	var flag = true;
	$('input, textarea').each(function() {
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
						el.focus();
						flag = false;
					}
				}
			return flag;
	});
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