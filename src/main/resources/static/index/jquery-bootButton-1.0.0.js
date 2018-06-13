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

  var pluginName = "bootButton";

  var defaults = {};
  defaults.options = {};

  var Plugin = function(el, options) {
    this.$el = $(el);
    this.init($.extend({}, defaults.options, options));
    return {
      options: this.options,
      init: $.proxy(this.init, this),
      busy: $.proxy(this.busy, this),
      free: $.proxy(this.free, this)
    };
  };

  Plugin.prototype.init = function(options) {
    var _this = this;
    if (!options) {
      return;
    }
    this.$el.data("x-value", this.$el.html());
  };

  Plugin.prototype.busy = function() {
    this.$el.html("<i class='fa fa-refresh fa-spin'></i>");
    this.$el.attr("disabled", true);
  };

  Plugin.prototype.free = function() {
    var v = this.$el.data("x-value");
    this.$el.html(v);
    this.$el.attr("disabled", false);
  };

  $.fn[pluginName] = function(options, args) {
    var result;
    this.each(function() {
      var _this = $.data(this, pluginName);
      if (typeof options === "string") {
        if (!_this) {
          _this = new Plugin(this);
          $.data(this, pluginName, _this);
          // alert("Not inialized, can not call method.");
        }
        if (!$.isFunction(_this[options])) {
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