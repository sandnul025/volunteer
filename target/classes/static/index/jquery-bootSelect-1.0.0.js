/**
 * jquery-bootModal
 * @author ineztia
 * version: 1.0.0
 */
if (!ZafUtil) {
  alert("ZafUtil.js is required to run this plugin.");
}

(function($) {
  "use strict";

  var pluginName = "bootSelect";

  var defaults = {};
  defaults.options = { preserveFirstEmpty: true };

  var Plugin = function(el, options) {
    this.$el = $(el);
    this.init($.extend({}, defaults.options, options));
    return {
      options: this.options,
      init: $.proxy(this.init, this),
      select: $.proxy(this.select, this)
    };
  };

  Plugin.prototype.init = function(options) {
    var _this = this;
    if (!options) {
      return;
    }
    var src = options.src;
    var selected = options.selected;
    var readonly = options.readonly;
    var onChange = options.onChange;
    var html = "";
    this.$el.data("x-boot-select-src", src);
    if (onChange) {
      this.$el.on("change", function(ev) {
        var val = _this.$el.children("option:selected").val();
        var text = _this.$el.children("option:selected").text();
        onChange(val, text);
      });
    }
    if (src) {
      if (options.preserveFirstEmpty) {
        var first = this.$el.children("option").first();
        if (first.length > 0 && first.val() == -1) {
          html += first.prop("outerHTML");
        }
      }
      $.each(src, function(i, p) {
        var value = i;
        var text = (typeof p === "string" ? p : p.V);
        var stateSelected = "";
        if ((value == selected) || ($.isArray(selected) && $.inArray(value, selected) >= 0)) {
          stateSelected = " selected=true";
        }
        if (readonly && stateSelected.length <= 0) {
          return;
        }
        html += ZafUtil.sprintf("<option value=\"%s\"%s>%s</option>", value, stateSelected, text);
      });
      this.$el.html(html);
    }
  };

  Plugin.prototype.select = function(values) {
  }

  $.fn[pluginName] = function(options, args) {
    var result;
    this.each(function() {
      var _this = $.data(this, pluginName);
      if (typeof options === "string") {
        if (!_this) {
          alert("Not inialized, can not call method.");
        } else if (!$.isFunction(_this[options])) {
          alert(ZafUtil.sprintf("No such method: [%s].", options));
        } else {
          if (!(args instanceof Array)) {
            args = [args];
          }
          result = _this[options].apply(_this, args);
        }
      } else if (typeof options === "boolean") {
        result = _this;
      } else {
        $.data(this, pluginName, new Plugin(this, $.extend(true, {}, options)));
      }
    });
    return result || this;
  };
})(jQuery);

$(function() {
});