(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['parametro.list'] = template({"1":function(container,depth0,helpers,partials,data) {
    var helper, alias1=depth0 != null ? depth0 : {}, alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "  <tr class=\"\">\r\n    <td class=\"\">"
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "</td>\r\n    <td class=\"\">"
    + alias4(((helper = (helper = helpers.numero || (depth0 != null ? depth0.numero : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"numero","hash":{},"data":data}) : helper)))
    + "</td>\r\n    <td class=\"\">"
    + alias4(((helper = (helper = helpers.codigo || (depth0 != null ? depth0.codigo : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"codigo","hash":{},"data":data}) : helper)))
    + "</td>\r\n    <td class=\"\">"
    + alias4(((helper = (helper = helpers.nombre || (depth0 != null ? depth0.nombre : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"nombre","hash":{},"data":data}) : helper)))
    + "</td>\r\n    <td class=\"\">"
    + alias4(((helper = (helper = helpers.valor || (depth0 != null ? depth0.valor : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"valor","hash":{},"data":data}) : helper)))
    + "</td>\r\n    <td class=\"\">\r\n      <span href=\"\" data-index=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\" data-codigo=\""
    + alias4(((helper = (helper = helpers.codigo || (depth0 != null ? depth0.codigo : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"codigo","hash":{},"data":data}) : helper)))
    + " \" data-nombre=\""
    + alias4(((helper = (helper = helpers.nombre || (depth0 != null ? depth0.nombre : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"nombre","hash":{},"data":data}) : helper)))
    + "\" data-valor=\""
    + alias4(((helper = (helper = helpers.valor || (depth0 != null ? depth0.valor : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"valor","hash":{},"data":data}) : helper)))
    + "\" class=\"btn btn-xs btn-circle  btn-warning  btn-edit\"><span class=\"fa fa-edit fw\"></span></span>\r\n      <span href=\"\" data-index=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\" class=\"btn btn-xs btn-danger btn-circle btn-del\"><span class=\"fa fa-trash fw\"></span></span>\r\n      </td>\r\n  </tr>\r\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : {},(depth0 != null ? depth0.records : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"useData":true});
})();