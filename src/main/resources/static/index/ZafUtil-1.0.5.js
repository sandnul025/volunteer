String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

var ZafUtil = {
	age: function(birthdate) {
		var b = birthdate instanceof Date ? birthdate : new Date(birthdate);
		var age = Math.floor((new Date() - new Date(b)) / 31536000000);
		return age || "?";
	},

	cut: function(str, len) {
		if (str && str.length > len) {
			return str.substr(0, len) + "...";
		}
		return str || "";
	},

	globals: {},
	getGlobal: function(key) {
		return this.globals[key];
	},
	setGlobal: function(key, val) {
		this.globals[key] = val;
	},

	ajaxOnce: function(method, url, args, callback) {
		var _this = this;
		method = method ? method.toLowerCase() : "get";
		var key = url + JSON.stringify(args);
		var data = this.getGlobal(key);
		if (!this.isEmpty(data)) {
			callback(data);
			return;
		}
		var cb = function(result) {
			_this.setGlobal(key, result);
			callback(result);
		}
		if (method == "get") {
			$.get(url, args, cb);
		} else if (method == "post") {
			$.post(url, args, cb);
		} else {
			alert("Parameter [method] must be set to get or post.");
		}
	},

	isEmpty: function(value, checkStringEmpty, checkStringTrim) {
		var empty = typeof(value) === "undefined" || value == null;
		if (checkStringEmpty) {
			empty = (empty || value == "");
		}
		if (checkStringTrim && typeof value === "string") {
			empty = (empty || value.trim() == "");
		}
		return empty;
	},

	guid: function() {
		return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g,
			function(c) {
				var r = Math.random() * 16 | 0;
				var v = c == "x" ? r : (r & 0x3 | 0x8);
				return v.toString(16);
			}
		);
	},

	sprintf: function(str) {
		var args = arguments;
		var i = 1;
		str = str.replace(/%s/g, function() {
			var arg = args[i++];
			if (typeof arg === "undefined") {
				return "";
			}
			return arg;
		});
		return str;
	},

	bool: function(val) {
		if ($.isNumeric(val)) {
			return val > 0;
		}
		return val ? true : false;
	},

	obj: function(obj) {
		return obj;
	},

	child: function(obj, path) {
		if (!this.isEmpty(obj)) {
			if (!(path instanceof Array)) {
				path = [path];
			}
			if (path.length <= 0) {
				return obj;
			}
			var key = path[0];
			path.shift();
			return this.child(obj[key], path);
		}
		return null;
	},

	params: function(key) {
		// var p = (new URI()).query(true);
		var p = $.url("?");
		if (!p) {
			p = {};
		}
		return this.isEmpty(key) ? p : p[key];
	},

	pageReload: function() {
		window.location.href = window.location.href;
		window.location.reload(true);
	},

	debug: function(obj) {
		console.log(JSON.stringify(obj));
	},

	date: function(dateStr, fmt, defaultValue) {
		defaultValue = defaultValue || "";
		if (dateStr && fmt) {
			return $.format.date(dateStr, fmt) || defaultValue;
		}
		return defaultValue;
	},

	dateTimeDiff: function(startStr, endStr) { // return in seconds
		try {
			var d1 = Date.parse(startStr);
			var d2 = Date.parse(endStr);
			// console.log(d1);
			// console.log(d2);
			return (d2 - d1) / 1000;
		} catch (e) {
			console.log(e.message);
			return 2147483647;
		}
	},

	isValidDate: function(dateStr, sep) { // YYYY-mm-dd
		sep = sep || "-";
		var arrDate = dateStr.split(sep);
		var year = parseInt(arrDate[0]);
		var month = parseInt(arrDate[1]);
		var day = parseInt(arrDate[2]);
		if (year < 1753 || year > 9999 || month < 1 || month > 12 || day < 1 || day > 31) {
			return false;
		} else {
			if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
				if ((month == 2 && day > 29) || ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) || ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && day > 31)) {
					return false;
				} else {
					return true;
				}
			} else {
				if ((month == 2 && day > 28) || ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) || ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && day > 31)) {
					return false;
				} else {
					return true;
				}
			}
		}
	},

	htmlEncode: function(str) {
  	var s = "";
	  if (this.isEmpty(str) || str.length == 0) return "";
	  s = str.replace(/&/g, "&gt;");
	  s = s.replace(/</g, "&lt;");
	  s = s.replace(/>/g, "&gt;");
	  s = s.replace(/ /g, "&nbsp;");
	  s = s.replace(/\'/g, "&#39;");
	  s = s.replace(/\"/g, "&quot;");
	  s = s.replace(/\n/g, "<br />");
	  return s;
	},

	htmlDecode: function(str) {
	  var s = "";
	  if (this.isEmpty(str) || str.length == 0) return "";
	  s = str.replace(/&gt;/g, ">");
	  s = s.replace(/&lt;/g, "<");
	  s = s.replace(/&gt;/g, ">");
	  s = s.replace(/&nbsp;/g, " ");
	  s = s.replace(/&#39;/g, "\'");
	  s = s.replace(/&quot;/g, "\"");
	  s = s.replace(/<br\s*\/*>/g, "\n");
	  return s;
	},

	sanitizeParam: function(str) {
		return (str + "").replace(/[\\"']/g, "\\$&").replace(/\u0000/g, "\\0");
	},

	text: function(str) {
		var div = $("<div></div>");
		div.text(str);
		return div.text();
	},

	isMobile: function() {
		var check = false;
		(function(a) {
			if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino/i.test(a) || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0, 4))) check = true; })(navigator.userAgent || navigator.vendor || window.opera);
		return check;
	},

	isWeixinBrowser: function() {
		var ua = navigator.userAgent.toLowerCase();
		return (/micromessenger/.test(ua)) ? true : false ;
	},

	getGeoLocation: function(cbSuccess) {
		if (!$.isFunction(cbSuccess)) {
			console.log("Callback parameter required: callback(lat, lng)");
			return;
		}
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(function(pos) {
				cbSuccess(pos.coords.latitude, pos.coords.longitude);
				return;
			}, function(err) {
				console.log("Geo-Location fetching failed.");
			}, {
				enableHighAccuracy: true,
				timeout: 5000,
				maximumAge: 0
			});
		} else {
			console.log("Geo-Location not supported.");
		}
	},

	extend: function(paramsDefault, paramsNew) {
		var _this = this;
		var p = paramsDefault;
		if (!paramsNew) {
			return p;
		}
		$.each(paramsNew, function(k, v) {
			if (!_this.isEmpty(v, true, true)) {
				p[k] = v;
			}
		});
		return p;
	},

	pad: function(str, len, prefix) {
		return (Array(len).join(prefix) + str).slice(-len);
	},

	isChinese: function(str) {
		var reg = /^[\u4e00-\u9fa5]+$/;
		if (!str || !reg.test(str.trim())) {
			return false;
		} else {
			return true;
		}
	},

	getFileExt: function(filename) {
		return this.isEmpty(filename, true, true) ? "" : filename.replace(/.+\./, "");
	},

	highlight: function(str, cssClass, color) {
		if (cssClass) {
			return ZafUtil.sprintf("<span class='%s'>%s</span>", cssClass, str);
		} else if (color) {
			return ZafUtil.sprintf("<span style='color:%s'>%s</span>", color, str);
		} else {
			return ZafUtil.sprintf("<span class='text-danger'>%s</span>", str);
		}
	},

	objLen: function(obj) {
		return $.map(obj, function(n, i) { return i; }).length;
	},

	loading: function(container, isLoading) {
		if (!container instanceof jQuery) {
			return;
		}
		var template = "<div id='DIV_LoadingMask' style='z-index:50;background:rgba(255,255,255,0.7);border-radius:3px;position:absolute;top:0;left:0;width:100%;height:100%;'><i class='fa fa-spinner fa-spin' style='position:absolute;top:50%;left:50%;margin-left:-15px;margin-top:-15px;color:#000;font-size:30px;'></i></div>";
		var mask = container.find("#DIV_LoadingMask");
		if (isLoading) {
			if (mask.length <= 0) {
				mask = $(template);
				container.prepend(mask);
			}
			mask.show();
		} else {
			mask.hide();
		}
	},

	nonnegtive: function() {
		var len = arguments.length;
		for (i = 0; i < len; i++) {
			if (arguments[i] > 0) {
				return arguments[i];
			}
		}
		return 0;
	},

	getCookie: function(name) {
		var arr,reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
		if (arr = document.cookie.match(reg)) {
			return unescape(arr[2]);
		} else {
			return null;
		}
	},

	setCookie: function(name, value) {
		document.cookie = name + "=" + escape(value) + ";";
	}
};

$(function() {});
