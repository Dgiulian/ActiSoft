(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['vehiculo.edit'] = template({"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var helper, alias1=depth0 != null ? depth0 : {}, alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "<div class=\"row\">\r\n  <div class=\"col-md-12\">\r\n    <form class=\"form-vertical\">\r\n      <input id=\"id\" name=\"id\" type=\"hidden\" class=\"\" value=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\" >\r\n      <div class=\"form-group\">\r\n        <label class=\"col-md-4 control-label\" for=\"dominio\">Dominio:</label>\r\n        <div class=\"col-md-8\">\r\n        <input id=\"dominio\" name=\"dominio\" type=\"text\" class=\"form-control input-md\" value=\""
    + alias4(((helper = (helper = helpers.dominio || (depth0 != null ? depth0.dominio : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"dominio","hash":{},"data":data}) : helper)))
    + "\">\r\n      </div>\r\n\r\n      <div class=\"form-group\">\r\n        <label class=\"col-md-4 control-label\" for=\"vencimiento_vtv\">Vencimiento vtv: </label>\r\n        <div class=\"col-md-8\">\r\n        <input id=\"vencimiento_vtv\" name=\"vencimiento_vtv\" type=\"text\" class=\"form-control input-md date-picker\" value=\""
    + alias4(((helper = (helper = helpers.vencimiento_vtv || (depth0 != null ? depth0.vencimiento_vtv : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"vencimiento_vtv","hash":{},"data":data}) : helper)))
    + "\">\r\n        </div>\r\n      </div>\r\n      <div class=\"form-group\">\r\n        <label class=\"col-md-4 control-label\" for=\"seguro\">Seguro:</label>\r\n        <div class=\"col-md-8\">\r\n        <input id=\"seguro\" name=\"seguro\" type=\"text\" class=\"form-control input-md\" value=\""
    + alias4(((helper = (helper = helpers.seguro || (depth0 != null ? depth0.seguro : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"seguro","hash":{},"data":data}) : helper)))
    + "\">\r\n     </div>\r\n      <div class=\"form-group\">\r\n        <label class=\"col-md-4 control-label\" for=\"poliza\">Poliza:</label>\r\n        <div class=\"col-md-8\">\r\n        <input id=\"poliza\" name=\"poliza\" type=\"text\" class=\"form-control input-md\" value=\""
    + alias4(((helper = (helper = helpers.poliza || (depth0 != null ? depth0.poliza : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"poliza","hash":{},"data":data}) : helper)))
    + "\">\r\n      </div>\r\n      <div class=\"form-group\">\r\n        <label class=\"col-md-4 control-label\" for=\"vencimiento_poliza\">Vencimiento Poliza: </label>\r\n        <div class=\"col-md-8\">\r\n        <input id=\"vencimiento_poliza\" name=\"vencimiento_poliza\" type=\"text\" class=\"form-control input-md date-picker\" value=\""
    + alias4(((helper = (helper = helpers.vencimiento_poliza || (depth0 != null ? depth0.vencimiento_poliza : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"vencimiento_poliza","hash":{},"data":data}) : helper)))
    + "\">\r\n        </div>\r\n      </div>\r\n      <div class=\"form-group\">\r\n        <label class=\"col-md-4 control-label\" for=\"rsv\">RSV:</label>\r\n        <div class=\"col-md-8\">\r\n          <input id=\"rsv\" name=\"rsv\" type=\"text\" class=\"form-control input-md\" value=\""
    + alias4(((helper = (helper = helpers.rsv || (depth0 != null ? depth0.rsv : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"rsv","hash":{},"data":data}) : helper)))
    + "\">\r\n        </div>\r\n     </div>\r\n  </form>\r\n  </div>\r\n</div>";
},"useData":true});
})();