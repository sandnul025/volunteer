/**
 * jquery-bootModal
 * @author ineztia
 * version: 1.0.0
 */

if (!ZafUtil) {
  alert("ZafUtil.js is required to run this plugin.");
}

var BootModal = {
  templates: {
    dialog: "<div class='modal' tabindex='-1' role='dialog'><div class='modal-dialog'><div class='modal-content'><div class='modal-header'><span class='modal-title' style='display:-webkit-flex;display:flex;-webkit-align-items:center;align-items:center;line-height:1'></span></div><div class='modal-body' style='padding:15px'></div><div class='modal-footer'></div></div></div></div>",
    icons: {
      none: "",
      info: "<i class='fa fa-info-circle' style='color:#bbb;padding-right:8px'></i>",
      warning: "<i class='fa fa-warning' style='color:#bbb;padding-right:8px'></i>",
      success: "<i class='fa fa-check-circle' style='color:#bbb;padding-right:8px'></i>"
    },
    flags: {
      info: "<i class='fa fa-info-circle text-info' style='padding-right:8px'></i>",
      error: "<i class='fa fa-close text-danger' style='padding-right:8px'></i>",
      warning: "<i class='fa fa-warning text-warning' style='padding-right:8px'></i>",
      success: "<i class='fa fa-check-circle text-success' style='padding-right:8px'></i>"
    },
    buttons: {
      ok: "<button name='ok' type='button' class='btn btn-success btn-sm' style='width:40px'><i class='fa fa-check'></i></button>",
      cancel: "<button name='cancel' type='button' class='btn btn-danger btn-sm' style='width:40px'><i class='fa fa-times'></i></button>",
      close: "<button name='close' type='button' class='close' data-dismiss='modal'>&times;</button>"
    }
  },

  defaults: {
    titleIcon: false,
    type: "confirm",
    show: true,
    reusable: false
  },

  activated: false,

  init: function() {},

  alert: function(options) {
    options = this.mergeDialogOptions(options, "alert");
    return this.create(options);
  },

  success: function(options) {
    options = this.mergeDialogOptions(options, "success");
    return this.create(options);
  },

  confirm: function(options) {
    options = this.mergeDialogOptions(options, "confirm");
    return this.create(options);
  },

  create: function(options) {
    if (this.activated) {
      console.log("Can not activate two or more modal dialogs at same time.");
      return false;
    }
    var _this = this;
    options = this.sanitize(options);
    var reusable = options.reusable || false;
    var templates = this.templates;
    var dialog = $(templates.dialog);
    this.dialog = dialog;
    var src = options.src;
    if (src) {
      if (!(src instanceof jQuery) || src.length < 1) {
        alert("Source element must be a non-empty jQuery object.");
      }
      // replace original content of source element with templates
      src.html(dialog.prop("innerHTML"));
      dialog = src;
      dialog.addClass("modal");
      dialog.attr("tabindex", -1);
      dialog.attr("role", "dialog");
    } else {
      options.src = dialog;
    }
    var innerDialog = dialog.find(".modal-dialog");
    var header = dialog.find(".modal-header");
    var title = dialog.find(".modal-title");
    var body = dialog.find(".modal-body");
    var footer = dialog.find(".modal-footer");
    var container = options.container || $("body");
    if (!options.guid) {
      alert("Modal dialog must have a guid.");
    }
    dialog.attr("guid", options.guid);
    if (!container || container.length < 1) {
      alert("Modal dialog must have a container.");
    }
    if (options.title) {
      title.html(options.title);
    } else {
      header.remove();
    }
    options.content && body.html(options.content);
    // show corresponding title & buttons for different dialog types
    if (options.type == "success") {
      options.titleIcon && title.prepend(templates.icons.success);
      footer.append(templates.buttons.ok);
    } else if (options.type == "alert") {
      options.titleIcon && title.prepend(templates.icons.warning);
      footer.append(templates.buttons.ok);
    } else if (options.type == "confirm") {
      options.titleIcon && title.prepend(templates.icons.info);
      footer.append(templates.buttons.ok);
      footer.append(templates.buttons.cancel);
    } else {
      options.titleIcon && title.prepend(templates.icons.info);
      footer.append(templates.buttons.ok);
    }

    dialog.find("button[name=ok],button[name=cancel],button[name=close]").on("click.x-modal", function(ev) {
      console.log("dialog closed");
      dialog.modal("hide");
    });

    // bind user events
    if ($.isFunction(options.onShow)) {
      dialog.off("show.bs.modal.x-modal").on("show.bs.modal.x-modal", function() {
        options.onShow(dialog);
      });
    }
    if ($.isFunction(options.onShown)) {
      dialog.off("shown.bs.modal.x-modal").on("shown.bs.modal.x-modal", function() {
        options.onShown(dialog);
      });
    }
    if ($.isFunction(options.onHide)) {
      dialog.off("hide.bs.modal.x-modal").on("hide.bs.modal.x-modal", function() {
        options.onHide(dialog);
      });
    }
    if ($.isFunction(options.onHidden)) {
      dialog.off("hidden.bs.modal.x-modal").on("hidden.bs.modal.x-modal", function() {
        options.onHidden(dialog);
      });
    }
    if ($.isFunction(options.onOK)) {
      var btnOK = dialog.find("button[name=ok]");
      btnOK.off("click.x-modal");
      btnOK.on("click.x-modal", function(ev) {
        if (options.onOK(dialog) === false) {
          ev.preventDefault();
          ev.stopPropagation();
          ev.stopImmediatePropagation();
          return;
        }
        dialog.modal("hide");
      });
    }
    if ($.isFunction(options.onCancel)) {
      dialog.find("button[name=cancel]").on("click.x-modal", function(ev) {
        options.onCancel(dialog);
      });
    }
    // trigger click-ok events on key enter
    dialog.off("keyup.x-modal").on("keyup.x-modal", function(ev) {
      if (ev.keyCode == 13) {
        dialog.find("button[name=ok]").trigger("click");
        ev.preventDefault();
        ev.stopPropagation();
      }
    });
    dialog.off("hidden.bs.modal.x-modal-sys").on("hidden.bs.modal.x-modal-sys", function(ev) {
      _this.activated = false;
      if (ev.target === this) {
        if (!options.reusable) {
          dialog.remove();
        }
      }
    });
    // attach dialog to window and show it
    if (dialog.parent().length < 1) {
      container.append(dialog);
    }
    if (options.show) {
      this.show(dialog);
    }
    return dialog;
  },

  show: function(dialog) {
    if (this.activated) {
      console.log("Can not activate two or more modal dialogs at same time.");
      return false;
    }
    this.activated = true;
    dialog.modal();
  },

  hide: function(dialog) {
    this.activated = false;
    dialog.modal("hide");
  },

  mergeDialogOptions: function(options, dialogType) {
    if (!options || typeof options === "string") {
      options = {
        content: options,
        type: dialogType
      };
    }
    options.type = dialogType;
    return options;
  },

  sanitize: function(options) {
    options = $.extend({}, this.defaults, options);
    if (!options.guid) {
      options.guid = ZafUtil.guid();
    }
    if (options.src && typeof options.reusable != "undefined" && options.reusable != null) {
      options.reusable = true;
    }
    return options;
  },
};

$(function() {
  BootModal.init();
});

(function($) {
  "use strict";

  var pluginName = "bootModal";

  var defaults = {};
  defaults.options = {};

  var Plugin = function(element, options) {
    this.$e = $(element);
    this.init($.extend({}, defaults.options, options));
    return {
      options: this.options,
      init: $.proxy(this.init, this),
      show: $.proxy(this.show, this),
      hide: $.proxy(this.hide, this)
    };
  };

  Plugin.prototype.init = function(options) {
    options.src = this.$e;
    BootModal.create(options);
  };

  Plugin.prototype.show = function() {
    BootModal.show(this.$e);
  };

  Plugin.prototype.hide = function() {
    BootModal.hide(this.$e);
  };

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