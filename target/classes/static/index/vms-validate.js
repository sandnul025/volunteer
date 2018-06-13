$(function() {
	// mobile number
	$.validator.addMethod("cnMobileNumber", function(val, el) {
		var length = val.length;
		var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
		return this.optional(el) || (length == 11 && mobile.test(val));
	}, "Error mobile number format.");

	// national id card
	$.validator.addMethod("cnID", function(val, el) {
		return this.optional(el) || VmsUtil.isCNID(val);
	}, "Error CN ID format.");

	// date
	$.validator.addMethod("validDate", function(val, el) {
		return this.optional(el) || VmsUtil.isValidDate(val);
	}, "Error date format.");

	$.validator.addMethod("dateAfter", function(val, el, startDate) {
		if (typeof startDate !== "string") {
			startDate = $(startDate).val();
		}
		var d1 = new Date(Date.parse(startDate.replace("-", "/")));
		var d2 = new Date(Date.parse(val.replace("-", "/")));
		return d1 < d2;
	}, "End date must after start date.");
});
