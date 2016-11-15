(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['transportista.edit'] = template({"1":function(container,depth0,helpers,partials,data) {
    return " checked";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : {}, alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "<div class=\"row\">\r\n  <div class=\"col-md-12\">\r\n    <form class=\"form-vertical\">\r\n      <input id=\"id\" name=\"id\" type=\"hidden\" class=\"\" value=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\" >\r\n      <input id=\"id_proveedor\" name=\"id_proveedor\" type=\"hidden\" class=\"\" value=\""
    + alias4(((helper = (helper = helpers.id_proveedor || (depth0 != null ? depth0.id_proveedor : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id_proveedor","hash":{},"data":data}) : helper)))
    + "\" >\r\n      <div class=\"form-group\">\r\n          <label class=\"col-md-6 control-label\" for=\"nombre\">Nombre:</label>\r\n          <div class=\"col-md-6\">\r\n          <input id=\"nombre\" name=\"nombre\" type=\"text\" class=\"form-control input-md\" value=\""
    + alias4(((helper = (helper = helpers.nombre || (depth0 != null ? depth0.nombre : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"nombre","hash":{},"data":data}) : helper)))
    + "\">\r\n          </div>\r\n      </div>\r\n      <div class=\"form-group\">\r\n          <label class=\"col-md-6 control-label\" for=\"nombre\">DNI:</label>\r\n          <div class=\"col-md-6\">\r\n          <input id=\"dni\" name=\"dni\" type=\"text\" class=\"form-control input-md\" value=\""
    + alias4(((helper = (helper = helpers.dni || (depth0 != null ? depth0.dni : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"dni","hash":{},"data":data}) : helper)))
    + "\">\r\n          </div>\r\n      </div>\r\n\r\n      <div class=\"form-group\">\r\n          <label class=\"col-md-6 control-label\" for=\"vencimiento_carnet\">Vencimiento Carnet:</label>\r\n          <div class=\"col-md-6\">\r\n          <input id=\"vencimiento_carnet\" name=\"vencimiento_carnet\" type=\"text\" class=\"form-control input-md date-picker\" value=\""
    + alias4(((helper = (helper = helpers.vencimiento_carnet || (depth0 != null ? depth0.vencimiento_carnet : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"vencimiento_carnet","hash":{},"data":data}) : helper)))
    + "\">\r\n          </div>\r\n      </div>\r\n      <div class=\"form-group\">\r\n          <label class=\"col-md-6 control-label\" for=\"vencimiento_seguro\">Vencimiento Seguro:</label>\r\n          <div class=\"col-md-6\">\r\n          <input id=\"vencimiento_seguro\" name=\"vencimiento_seguro\" type=\"text\" class=\"form-control input-md date-picker\" value=\""
    + alias4(((helper = (helper = helpers.vencimiento_seguro || (depth0 != null ? depth0.vencimiento_seguro : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"vencimiento_seguro","hash":{},"data":data}) : helper)))
    + "\">\r\n          </div>\r\n      </div>\r\n      <div class=\"form-group\">\r\n          <label class=\"col-md-6 control-label\" for=\"rsv_presentado\">RSV Presentada:</label>\r\n          <input id=\"rsv_presentado\" name=\"rsv_presentado\" type=\"checkbox\" class=\"\" value=\""
    + alias4(((helper = (helper = helpers.rsv_presentado || (depth0 != null ? depth0.rsv_presentado : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"rsv_presentado","hash":{},"data":data}) : helper)))
    + "\" "
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.rsv_presentado : depth0),{"name":"if","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ">\r\n      </div>\r\n\r\n     <div class=\"form-group\" >\r\n          <label class=\" col-md-6 control-label\" for=\"vencimiento_carnet_defensivo\">Vencimiento Carnet manejo defensivo:</label>\r\n          <div class=\"col-md-6\">\r\n          <input id=\"vencimiento_carnet_defensivo\" name=\"vencimiento_carnet_defensivo\" type=\"text\" class=\"form-control input-md date-picker\" value=\""
    + alias4(((helper = (helper = helpers.vencimiento_carnet_defensivo || (depth0 != null ? depth0.vencimiento_carnet_defensivo : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"vencimiento_carnet_defensivo","hash":{},"data":data}) : helper)))
    + "\">\r\n          </div>\r\n      </div>\r\n\r\n      <div class=\"form-group\">\r\n          <label class=\"col-md-6 control-label\" for=\"vencimiento_credencial_ipf\">Vencimiento credencial ingreso IPF:</label>\r\n          <div class=\"col-md-6\">\r\n          <input id=\"vencimiento_credencial_ipf\" name=\"vencimiento_credencial_ipf\" type=\"text\" class=\"form-control input-md date-picker\" value=\""
    + alias4(((helper = (helper = helpers.vencimiento_credencial_ipf || (depth0 != null ? depth0.vencimiento_credencial_ipf : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"vencimiento_credencial_ipf","hash":{},"data":data}) : helper)))
    + "\">\r\n          </div>\r\n      </div>\r\n\r\n\r\n\r\n    </form>\r\n  </div>\r\n</div>";
},"useData":true});
})();