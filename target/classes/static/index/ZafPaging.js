if (!ZafUtil) {
	alert("ZafUtil.js is required to run this plugin.");
}

var ZafPaging = function() {
	this.total = -1;
	this.totalPage = -1;
	this.page = 1;
	this.pageSize = -1;
	this.isLoading = false;
	this.setToLastPage = false;
}

ZafPaging.prototype.isLastPage = function() {
	return this.setToLastPage || (this.totalPage > 0 && this.page > 0 && this.page > this.totalPage);
}

ZafPaging.prototype.nextPage = function() {
	if (this.page <= 0) {
		this.page = 1;
	}
	this.page++;
}

ZafPaging.prototype.forceLastPage = function() {
	this.setToLastPage = true;
}

ZafPaging.prototype.reset = function() {
	this.total = -1;
	this.totalPage = -1;
	this.page = 1;
	this.pageSize = -1;
	this.isLoading = false;
	this.setToLastPage = false;
}

ZafPaging.prototype.calTotalPage = function() {
	this.totalPage = Math.ceil(this.total / (this.pageSize || 1))
}