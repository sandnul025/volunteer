var VmsUtil = {
	isVolunteerNumber: function(num) {
		if (!num || typeof num !== "string") {
			return false;
		}
		var result = num.match(/^\d{10}$/g) + ":" + num.match(/^\@\d+$/);
		return num.match(/^\d{10}$/);
	},

	errorListToHTML: function(jQueryValidateErrorList) {
		var error = jQueryValidateErrorList;
		if (ZafUtil.isEmpty(error)) {
			return "";
		}
		var ul = $("<ul class='list-unstyled' style='margin:0'></ul>");
		$.each(error, function(k, v) {
			var li = $("<li><i class='fa fa-close text-danger' style='padding-right:8px'></i></li>");
			li.append(v.message);
			ul.append(li);
		});
		return ul.children().length > 0 ? ul.prop("outerHTML") : "";
	},

	isCNID: function(id) {
		if (typeof id !== 'string') {
			return false;
		}
		var city = { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江 ", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北 ", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏 ", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外" };
		var birthday = id.substr(6, 4) + '/' + Number(id.substr(10, 2)) + '/' + Number(id.substr(12, 2));
		var d = new Date(birthday);
		var newBirthday = d.getFullYear() + '/' + Number(d.getMonth() + 1) + '/' + Number(d.getDate());
		var currentTime = new Date().getTime();
		var time = d.getTime();
		var arrInt = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
		var arrCh = ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'];
		var sum = 0;
		var i = 0;
		var residue = null;
		if (!/^\d{17}(\d|x)$/i.test(id)) {
			return false;
		}
		if (city[id.substr(0, 2)] === undefined) {
			return false;
		}
		if (time >= currentTime || birthday !== newBirthday) {
			return false;
		}
		for (i = 0; i < 17; i++) {
			sum += id.substr(i, 1) * arrInt[i];
		}
		residue = arrCh[sum % 11];
		if (residue !== id.substr(17, 1)) {
			return false;
		}
		return true;
	},

	isValidDate: function(dateStr) { // yyyy-MM-dd
		var tmp = dateStr.split("-");
		var year = tmp[0];
		var month = tmp[1];
		var day = tmp[2];
		var d = new Date(parseInt(year, 10), parseInt(month, 10) - 1, parseInt(day, 10), 0, 0, 0);
		return d.getFullYear() == year && (d.getMonth() + 1) == month && d.getDate() == day;
	},

	isValidDateTime: function(dateTimeStr) { // yyyy-MM-dd HH:mm:ss
		alert("Not implemented.");
	},

	signInVolunteer: function() {
		window.location.href = ZafUtil.sprintf("Default.aspx?_c=SignIn&redir=%s", encodeURIComponent(window.location.href));
	},

	signInAdmin: function() {
		window.location.href = ZafUtil.sprintf("Default.aspx?_c=SignIn&redir=%s", encodeURIComponent(window.location.href));
	}
}