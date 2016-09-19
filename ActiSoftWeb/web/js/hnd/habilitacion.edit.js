(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['habilitacion.edit'] = template({"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var helper, alias1=depth0 != null ? depth0 : {}, alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "<form action=\"HabilitacionEdit\" method=\"POST\" enctype=\"multipart/form-data\" name=\"upload_form\" id=\"upload_form\">\r\n  <div class=\"row\">\r\n    <div class=\"col-md-12\">\r\n      <form class=\"form-vertical\">\r\n        <input id=\"id\" name=\"id\" type=\"hidden\" class=\"\" value=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\" >\r\n          <input id=\"id_responsable\" name=\"id_responsable\" type=\"hidden\" class=\"\" value=\""
    + alias4(((helper = (helper = helpers.id_responsable || (depth0 != null ? depth0.id_responsable : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id_responsable","hash":{},"data":data}) : helper)))
    + "\" >\r\n          <input id=\"id_proveedor\" name=\"id_proveedor\" type=\"hidden\" class=\"\" value=\""
    + alias4(((helper = (helper = helpers.id_proveedor || (depth0 != null ? depth0.id_proveedor : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id_proveedor","hash":{},"data":data}) : helper)))
    + "\" >\r\n         <div class=\"form-group\">\r\n            <label class=\"col-md-4 control-label\" for=\"fecha\">Fecha:</label>\r\n            <div class=\"col-md-8\">\r\n            <input id=\"fecha\" name=\"fecha\" type=\"text\" class=\"form-control input-md date-picker\" value=\""
    + alias4(((helper = (helper = helpers.fecha || (depth0 != null ? depth0.fecha : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"fecha","hash":{},"data":data}) : helper)))
    + "\">\r\n         </div>\r\n         <div class=\"form-group\">\r\n            <label class=\"col-md-4 control-label\" for=\"nombre\">Archivo:</label>\r\n            <div class=\"col-md-8\">\r\n            <input id=\"archivo\" name=\"archivo\" type=\"file\" class=\"\" value=\"\">\r\n            <div id=\"progress\">\r\n                  <div id=\"bar\"></div>\r\n                  <div id=\"percent\">0%</div >\r\n              </div>\r\n            <div id=\"message\"></div>\r\n         </div>\r\n      </div>\r\n      </form>\r\n    </div>\r\n  </div>\r\n</form>";
},"useData":true});
})();