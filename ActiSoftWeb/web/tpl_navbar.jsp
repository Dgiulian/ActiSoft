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
                <a class="navbar-brand" href="<%= request.getContextPath() %>" style="text-transform: uppercase;">
<!--                    <img src="images/logo.jpg" width="40" height="40" /> -->
                     <img src="images/logo.png" with="45" height="45"><%= PathCfg.PAGE_TITLE %>
                    </a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">

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
                            <a href="<%=PathCfg.POZO%>"><i class="fa fa-tag fa-fw"></i> Pozo</a>
                        </li>
                        <li>
                            <a href="<%=PathCfg.EQUIPO%>"><i class="fa fa-tags fa-fw"></i> Equipo</a>
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
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>