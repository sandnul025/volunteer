/**
 * jquery-bootPage
 * @author ineztia
 * version: 1.0.0
 */
if (!ZafUtil) {
  alert("ZafUtil.js is required to run this plugin.");
}

document.write("<style>.x-pagination{}.x-pagination li{display:inline-block !important;padding:4px;border:0;}.x-pagination li.active{color:#ff3300;font-weight:bold;}</style>");

(function($) {
	"use strict";

	var bootPage = function(options) {
		this.html("");
		var defaults = {
			"continuousButtonCount": 3, // first and last pages are not included
			"showTotal": true,
			"showPageSizeSelection": true,
			"pageParam": "_p",
			"pageSizeParam": "_ps"
		};
		var _this = this;
		var ul = $("<ul class='x-pagination'></ul>");
		this.append(ul);
		var options = $.extend({}, defaults, options);
		var continuousButtonCount = options.continuousButtonCount || 3;
		var total = options.total || 1;
		var page = options.page || 1;
		var pageSize = options.pageSize || 20;
		var url = options.url;
		var method = options.method;
		var params = options.params;
		var pageKeywords = options.pageKeywords;
		var maxPageIndex = Math.ceil(total / pageSize);
		var prevPageIndex = page - 1;
		var nextPageIndex = page + 1;
		var pages = {};
		// button styles:
		// << 1 2 3 [4] >>
		// << 1 ... 4 [5] 6 7 ... 10 >>
		// << 1 [2] 3 4 ... 10 >>
		// << 1 ... 7 8 [9] 10 >>
		var buttons = [];
		var prev = { "id": "prev", "content": "<span class='fa fa-arrow-left' style='background-color:#ccc'></span>", "index": prevPageIndex, "class": page > 1 ? null : "disabled" };
		var dots = { "content": "...", "class": "disabled" };
		var first = { "content": 1, "index": 1 };
		var last = { "content": maxPageIndex, "index": maxPageIndex };
		console.log(page + ":" + maxPageIndex);
		var next = { "id": "next", "content": "<span class='fa fa-arrow-right' style='background-color:#ccc'></span>", "index": nextPageIndex, "class": page < maxPageIndex ? null : "disabled" };
		// count start/end page for continuous buttons
		var start = Math.ceil(page - continuousButtonCount / 2);
		var end = Math.ceil(page + continuousButtonCount / 2) - 1;
		start = (start + continuousButtonCount - 1 > maxPageIndex) ? (maxPageIndex - continuousButtonCount + 1) : start;
		start = start > 0 ? start : 1;
		end = end <= continuousButtonCount ? continuousButtonCount : end;
		end = end <= maxPageIndex ? end : maxPageIndex;
		buttons.push(prev);
		start > 1 && buttons.push(first);
		(start > 2) && buttons.push(dots);
		for (var i = start; i <= Math.min(end, maxPageIndex); i++) {
			var button = { "content": i.toString(), "index": i };
			buttons.push(button);
		}
		(end < maxPageIndex - 1) && buttons.push(dots);
		end < maxPageIndex && buttons.push(last);
		buttons.push(next);
		$.each(buttons, function(i, p) {
			var li = $("<li></li>");
			var a = $("<a></a>");
			(p.index == page) && li.addClass("active");
			p["class"] && li.addClass(p["class"]);
			p.content && a.html(p.content);
			if (p.index > 0) {
				if (p["class"] != "disabled") {
					if (options.onSelect) {
						a.on("click", function() {
							options.onSelect(p.index, pageSize);
						});
						li.css("cursor", "pointer");
					}
				}
			}
			li.append(a);
			ul.append(li);
		});
		if (options.showTotal) {
			ul.prepend(ZafUtil.sprintf("<li><div class='label' style='background-color:gray'>%s</div></li>", total));
		}
		if (options.showPageSizeSelection) {
			var params = ZafUtil.params();
			var pageSizeOptionsHtml = "";
			var pageSizeOptions = [20, 40, 80];
			var currentPageSize = ZafUtil.nonnegtive(options.pageSize, parseInt(params[options.pageSizeParam]), pageSizeOptions[0]);
			for (var k in pageSizeOptions) {
				if (pageSizeOptions[k] == currentPageSize) {
					pageSizeOptionsHtml += ZafUtil.sprintf("<option value=%s selected>%s</option>", pageSizeOptions[k], pageSizeOptions[k]);
				} else {
					pageSizeOptionsHtml += ZafUtil.sprintf("<option value=%s>%s</option>", pageSizeOptions[k], pageSizeOptions[k]);
				}
			}
			var elPageSize = $(ZafUtil.sprintf("<li><a><select>%s</select></a></li>", pageSizeOptionsHtml));
			elPageSize.find("select").on("change", function() {
				var pageSizeSelected = $(this).val();
        options.pageSize = pageSizeSelected;
				var url = window.location.href;
				url = url.replace(/&_ps=\d*/gi, "");
				if (url.indexOf("#") > 0) {
					url = url.replace("#", ZafUtil.sprintf("&%s=%s#", options.pageSizeParam, pageSizeSelected));
				} else {
					url = ZafUtil.sprintf("%s&%s=%s", url, options.pageSizeParam, pageSizeSelected);
				}
        if ($.isFunction(options.onPageSizeChange)) {
          options.onPageSizeChange(options.pageSize);
        } else {
          window.location.href = url;
        }
			});
			ul.prepend(elPageSize);
		}
	};

	$.fn.extend({ bootPage: bootPage });
})(jQuery);
