(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['pozo.list'] = template({"1":function(container,depth0,helpers,partials,data) {
    var helper, alias1=depth0 != null ? depth0 : {}, alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "  <tr class=\"\">\r\n      <td class=\"\">"
    + alias4(((helper = (helper = helpers.nombre || (depth0 != null ? depth0.nombre : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"nombre","hash":{},"data":data}) : helper)))
    + "</td>\r\n      <td class=\"\">\r\n          <span data-index=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\" data-nombre=\""
    + alias4(((helper = (helper = helpers.nombre || (depth0 != null ? depth0.nombre : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"nombre","hash":{},"data":data}) : helper)))
    + "\" data-descripcion=\""
    + alias4(((helper = (helper = helpers.descripcion || (depth0 != null ? depth0.descripcion : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"descripcion","hash":{},"data":data}) : helper)))
    + "\"  class=\"btn btn-xs btn-circle btn-warning btn-edit\"><span class=\"fa fa-edit fw\"></span></span>\r\n          <span data-index=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\" class=\"btn btn-xs btn-danger btn-circle btn-del\"><span class=\"fa fa-trash fw\"></span></span>\r\n      </td>\r\n  </tr>\r\n";
},"3":function(container,depth0,helpers,partials,data) {
    return "<tr class=\"\">\r\n      <td colspan=\"2\" class=\"\"><center>No se ha encontrado ning&uacute;n pozo</center></td>\r\n</tr>\r\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : {},(depth0 != null ? depth0.records : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.program(3, data, 0),"data":data})) != null ? stack1 : "")
    + "\r\n\r\n\r\n\r\n\r\n";
},"useData":true});
})();