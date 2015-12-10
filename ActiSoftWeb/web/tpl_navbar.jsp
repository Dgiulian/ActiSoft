 <%@page import="utils.PathCfg"%>
<%
    String email = (String)    session.getAttribute("email");
    if(email==null) email = "";
%>
<!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="<%= request.getContextPath() %>">
<!--                    <img src="images/logo.jpg" width="40" height="40" /> -->
                     <%= PathCfg.PAGE_TITLE %>  
                    </a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
<!--                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-envelope fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-messages">
                        <li>
                            <a href="#">
                                <div>
                                    <strong>John Smith</strong>
                                    <span class="pull-right text-muted">
                                        <em>Yesterday</em>
                                    </span>
                                </div>
                                <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...</div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <strong>John Smith</strong>
                                    <span class="pull-right text-muted">
                                        <em>Yesterday</em>
                                    </span>
                                </div>
                                <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...</div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <strong>John Smith</strong>
                                    <span class="pull-right text-muted">
                                        <em>Yesterday</em>
                                    </span>
                                </div>
                                <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...</div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a class="text-center" href="#">
                                <strong>Read All Messages</strong>
                                <i class="fa fa-angle-right"></i>
                            </a>
                        </li>
                    </ul>
                     /.dropdown-messages
                </li>-->
                <!-- /.dropdown -->
<!--                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-tasks fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-tasks">
                        <li>
                            <a href="#">
                                <div>
                                    <p>
                                        <strong>Task 1</strong>
                                        <span class="pull-right text-muted">40% Complete</span>
                                    </p>
                                    <div class="progress progress-striped active">
                                        <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                                            <span class="sr-only">40% Complete (success)</span>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <p>
                                        <strong>Task 2</strong>
                                        <span class="pull-right text-muted">20% Complete</span>
                                    </p>
                                    <div class="progress progress-striped active">
                                        <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 20%">
                                            <span class="sr-only">20% Complete</span>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <p>
                                        <strong>Task 3</strong>
                                        <span class="pull-right text-muted">60% Complete</span>
                                    </p>
                                    <div class="progress progress-striped active">
                                        <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%">
                                            <span class="sr-only">60% Complete (warning)</span>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <p>
                                        <strong>Task 4</strong>
                                        <span class="pull-right text-muted">80% Complete</span>
                                    </p>
                                    <div class="progress progress-striped active">
                                        <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%">
                                            <span class="sr-only">80% Complete (danger)</span>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a class="text-center" href="#">
                                <strong>See All Tasks</strong>
                                <i class="fa fa-angle-right"></i>
                            </a>
                        </li>
                    </ul>
                     /.dropdown-tasks
                </li>-->
                <!-- /.dropdown -->
<!--                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-bell fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-alerts">
                        <li>
                            <a href="#">
                                <div>
                                    <i class="fa fa-comment fa-fw"></i> New Comment
                                    <span class="pull-right text-muted small">4 minutes ago</span>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <i class="fa fa-twitter fa-fw"></i> 3 New Followers
                                    <span class="pull-right text-muted small">12 minutes ago</span>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <i class="fa fa-envelope fa-fw"></i> Message Sent
                                    <span class="pull-right text-muted small">4 minutes ago</span>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <i class="fa fa-tasks fa-fw"></i> New Task
                                    <span class="pull-right text-muted small">4 minutes ago</span>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <i class="fa fa-upload fa-fw"></i> Server Rebooted
                                    <span class="pull-right text-muted small">4 minutes ago</span>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a class="text-center" href="#">
                                <strong>See All Alerts</strong>
                                <i class="fa fa-angle-right"></i>
                            </a>
                        </li>
                    </ul>
                     /.dropdown-alerts
                </li>-->
                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <%= email %> <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="#"><i class="fa fa-user fa-fw"></i> Perfil</a>
                        </li>
<!--                        <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a></li>-->
                        <li class="divider"></li>
                        <li><a href="<%= PathCfg.LOGOUT  %> "><i class="fa fa-sign-out fa-fw"></i> Cerrar sesi&oacute;n</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                    <!--
                        <li class="sidebar-search">
                            <div class="input-group custom-search-form">
                                <input type="text" class="form-control" placeholder="Search...">
                                <span class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                            </div>
                             /input-group
                        </li>-->
                        <li>
                            <a href="<%= request.getContextPath() %>/"><i class="fa fa-globe fa-fw"></i> Inicio</a>
                        </li>
                        <li>
                            <!--<a href="<%=PathCfg.ACTIVO %>"><i class="fa fa-wrench fa-fw"></i> Activos</a>-->
                            <a href="#" aria-expanded="true" class=""><i class="fa fa-wrench fa-fw"></i> Activos</a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="<%=PathCfg.ACTIVO %>"><span class="fa fa-list fa-fw"> </span> Listado</a>
                                </li>
                                <li>
                                    <a id="lnkVRCertificado" href="#"><span class="fa fa-certificate fa-fw"> </span> Certificado</a>
                                </li>
                                <li>
                                    <a id="" href="<%=PathCfg.KIT%>"><span class="fa fa-chain fa-fw"> </span> Kits</a>
                                </li>
                                 <li>
                                    <a id="" href="<%=PathCfg.ACTIVO_ETIQUETA%>"><span class="fa fa-tags fa-fw"> </span> Etiquetas</a>
                                </li>
                                 
<!--                               <li>
                                    <a id="lnkVRCompras" href="#"><span class="fa fa-dollar fa-fw"> </span> Compras</a>
                                </li>-->
                            </ul>
                            
                        </li>
<!--                        <li>
                            <a href="<%=PathCfg.CERTIFICADO %>"><i class="fa fa-certificate fa-fw"></i> Certificados</a>                            
                        </li>
                        <li>
                            <a href="<%=PathCfg.COMPRA %>"><i class="fa fa-money fa-fw"></i> Compras</a>
                        </li>-->
                        <li>
                            <a href="#"><i class="fa fa-users fa-fw"></i> Clientes</a>
                             <ul class="nav nav-second-level">
                                <li>
                                    <a href="<%=PathCfg.CLIENTE %>"><span class="fa fa-list fa-fw"> </span> Listado</a>
                                </li>
                                <li>
                                    <a  id="lnkVRSites" href="#" ><span class='fa fa-map-marker fw'></span> Sites</a>
                                </li>
                                <li>
                                    <a id="lnkVRHistorial" href="#"><span class='fa fa-history fw'></span> Historial</a>
                                </li>
                               
                            </ul>
                        </li>
                        <li>
                            <a href="<%=PathCfg.PROVEEDOR%>"><i class="fa fa-truck fa-fw"></i> Proveedores</a>
                        </li>
                        <li>
                            <a href="<%=PathCfg.CONTRATO %>"><i class="fa fa-file fa-fw"></i> Contratos</a>
                        </li>
                        <li>
                            <a href="<%=PathCfg.RUBRO %>"><i class="fa fa-th-list fa-fw"></i> Rubros</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="<%=PathCfg.REMITO%>"><i class="fa fa-file-text-o fa-fw"></i> Remitos</a>
                        </li>
                        <li>
                            <a href="<%=PathCfg.PRETICKET%>"><i class="fa fa-dollar fa-fw"></i> Preticket</a>
                        </li>
                        <li>
                            <a href="<%=PathCfg.USUARIO%>"><i class="fa fa-user fa-fw"></i> Usuarios</a>
                        </li>
                         <li>
                            <a href="<%=PathCfg.AGENDA%>"><i class="fa fa-calendar fa-fw"></i> Agenda</a>
                        </li>
                      
<!--                         <li>
                            <a href="#"><i class="fa fa-file-text-o fa-fw"></i> Remitos<span class="fa arrow"></span></a>
                            <a href="<%=PathCfg.REMITO%>"><i class="fa fa-file-text-o fa-fw"></i> Remitos</a>
                            <ul class="nav nav-second-level active">
                                <li>
                                    <a href="<%=PathCfg.REMITO%>">Presupuesto</a>
                                </li>
                                <li>
                                    <a href="<%=PathCfg.REMITO%>">Entrega</a>
                                </li>
                               
                            </ul>
                        </li>-->
<!--                        <li>
                            <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Charts<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="flot.html">Flot Charts</a>
                                </li>
                                <li>
                                    <a href="morris.html">Morris.js Charts</a>
                                </li>
                            </ul>
                             /.nav-second-level
                        </li>
                        <li>
                            <a href="tables.html"><i class="fa fa-table fa-fw"></i> Tables</a>
                        </li>
                        <li>
                            <a href="forms.html"><i class="fa fa-edit fa-fw"></i> Forms</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-wrench fa-fw"></i> UI Elements<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="panels-wells.html">Panels and Wells</a>
                                </li>
                                <li>
                                    <a href="buttons.html">Buttons</a>
                                </li>
                                <li>
                                    <a href="notifications.html">Notifications</a>
                                </li>
                                <li>
                                    <a href="typography.html">Typography</a>
                                </li>
                                <li>
                                    <a href="icons.html"> Icons</a>
                                </li>
                                <li>
                                    <a href="grid.html">Grid</a>
                                </li>
                            </ul>
                             /.nav-second-level
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-sitemap fa-fw"></i> Multi-Level Dropdown<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="#">Second Level Item</a>
                                </li>
                                <li>
                                    <a href="#">Second Level Item</a>
                                </li>
                                <li>
                                    <a href="#">Third Level <span class="fa arrow"></span></a>
                                    <ul class="nav nav-third-level">
                                        <li>
                                            <a href="#">Third Level Item</a>
                                        </li>
                                        <li>
                                            <a href="#">Third Level Item</a>
                                        </li>
                                        <li>
                                            <a href="#">Third Level Item</a>
                                        </li>
                                        <li>
                                            <a href="#">Third Level Item</a>
                                        </li>
                                    </ul>
                                     /.nav-third-level
                                </li>
                            </ul>
                             /.nav-second-level
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-files-o fa-fw"></i> Sample Pages<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="blank.html">Blank Page</a>
                                </li>
                                <li>
                                    <a href="login.html">Login Page</a>
                                </li>
                            </ul>
                             /.nav-second-level
                        </li>-->
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>