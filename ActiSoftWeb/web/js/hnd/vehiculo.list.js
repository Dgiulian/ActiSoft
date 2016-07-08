(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['vehiculo.list'] = template({"1":function(container,depth0,helpers,partials,data) {
    var helper, alias1=depth0 != null ? depth0 : {}, alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "  <tr class=\"\">\r\n      <td class=\"\">"
    + alias4(((helper = (helper = helpers.dominio || (depth0 != null ? depth0.dominio : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"dominio","hash":{},"data":data}) : helper)))
    + "</td>\r\n      <td class=\"\">"
    + alias4((helpers.convertirFecha || (depth0 && depth0.convertirFecha) || alias2).call(alias1,(depth0 != null ? depth0.vencimiento_vtv : depth0),{"name":"convertirFecha","hash":{},"data":data}))
    + "</td>\r\n      <td class=\"\">"
    + alias4(((helper = (helper = helpers.seguro || (depth0 != null ? depth0.seguro : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"seguro","hash":{},"data":data}) : helper)))
    + "</td>\r\n      <td class=\"\">"
    + alias4(((helper = (helper = helpers.poliza || (depth0 != null ? depth0.poliza : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"poliza","hash":{},"data":data}) : helper)))
    + "</td>\r\n      <td class=\"\">"
    + alias4((helpers.convertirFecha || (depth0 && depth0.convertirFecha) || alias2).call(alias1,(depth0 != null ? depth0.vencimiento_poliza : depth0),{"name":"convertirFecha","hash":{},"data":data}))
    + "</td>\r\n      <td class=\"\">"
    + alias4(((helper = (helper = helpers.rsv || (depth0 != null ? depth0.rsv : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"rsv","hash":{},"data":data}) : helper)))
    + "</td>\r\n      <td class=\"\">\r\n          <span data-index=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\" data-dominio=\""
    + alias4(((helper = (helper = helpers.dominio || (depth0 != null ? depth0.dominio : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"dominio","hash":{},"data":data}) : helper)))
    + "\" data-vencimiento_vtv=\""
    + alias4(((helper = (helper = helpers.vencimiento_vtv || (depth0 != null ? depth0.vencimiento_vtv : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"vencimiento_vtv","hash":{},"data":data}) : helper)))
    + "\" data-seguro=\""
    + alias4(((helper = (helper = helpers.vencimiento_vtv || (depth0 != null ? depth0.vencimiento_vtv : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"vencimiento_vtv","hash":{},"data":data}) : helper)))
    + "\" data-poliza=\""
    + alias4(((helper = (helper = helpers.poliza || (depth0 != null ? depth0.poliza : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"poliza","hash":{},"data":data}) : helper)))
    + "\" data-vencimiento_poliza=\""
    + alias4(((helper = (helper = helpers.vencimiento_poliza || (depth0 != null ? depth0.vencimiento_poliza : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"vencimiento_poliza","hash":{},"data":data}) : helper)))
    + "\" data-rsv=\""
    + alias4(((helper = (helper = helpers.rsv || (depth0 != null ? depth0.rsv : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"rsv","hash":{},"data":data}) : helper)))
    + "\" class=\"btn btn-xs btn-circle btn-warning btn-edit\"><span class=\"fa fa-edit fw\"></span></span>\r\n          <span data-index=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\" class=\"btn btn-xs btn-danger btn-circle btn-del\"><span class=\"fa fa-trash fw\"></span></span>\r\n      </td>\r\n  </tr>\r\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : {},(depth0 != null ? depth0.records : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "\r\n\r\n\r\n\r\n\r\n\r\n\r\n";
},"useData":true});
})();