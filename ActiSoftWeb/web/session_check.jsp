 <%@page import="utils.PathCfg"%>
<%
//    session = request.getSession(true);
    if (session==null) {
        response.sendRedirect(PathCfg.LOGIN);
    }
    
    Boolean logged = (Boolean) session.getAttribute("logged");
    String email = (String) session.getAttribute("email");
    Integer id_tipo_usuario = (Integer) session.getAttribute("id_tipo_usuario");
    if(email==null|| email.equals("")) response.sendRedirect(PathCfg.LOGIN);
    if(id_tipo_usuario ==null)  response.sendRedirect(PathCfg.LOGIN);
%>