(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['equipo.edit'] = template({"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var helper, alias1=depth0 != null ? depth0 : {}, alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "<div class=\"row\">\r\n  <div class=\"col-md-12\">\r\n    <form class=\"form-vertical\">\r\n      <input id=\"id\" name=\"id\" type=\"hidden\" class=\"\" value=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\" >\r\n      <div class=\"form-group\">\r\n          <label class=\"col-md-6 control-label\" for=\"nombre\">Nombre:</label>\r\n          <div class=\"col-md-6\">\r\n          <input id=\"nombre\" name=\"nombre\" type=\"text\" class=\"form-control input-md\" value=\""
    + alias4(((helper = (helper = helpers.nombre || (depth0 != null ? depth0.nombre : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"nombre","hash":{},"data":data}) : helper)))
    + "\">\r\n          </div>\r\n      </div>\r\n      <div class=\"form-group\">\r\n          <label class=\"col-md-6 control-label\" for=\"nombre\">Descripcion:</label>\r\n          <div class=\"col-md-6\">\r\n          <input id=\"descripcion\" name=\"descripcion\" type=\"text\" class=\"form-control input-md\" value=\""
    + alias4(((helper = (helper = helpers.descripcion || (depth0 != null ? depth0.descripcion : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"descripcion","hash":{},"data":data}) : helper)))
    + "\">\r\n          </div>\r\n      </div>\r\n    </form>\r\n  </div>\r\n</div>";
},"useData":true});
})();