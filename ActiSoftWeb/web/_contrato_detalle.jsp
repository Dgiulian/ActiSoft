<%@page import="bd.Contrato_detalle"%>

<tr>
    <td width="30px">
        <input class="form-control form-inline" type="number" name="posicion" min="0" value="<%= detalle.getNumero() %>">
    </td>
    <td style="width:150px">
        <span class="input-group">
            <span class="input-group-addon" data-toggle="modal" data-target="#mdlSubrubro"><span class="fa fa-search fa-fw"></span></span>
            <input type="text" class="form-control inCodigo" data-index="1" name="codigo" placeholder="C&oacute;digo" size="20" value="<%= detalle.getCodigo() %>">
        </span>
    </td>
    <td class="subrubro"></td>    
    <td>
        <input class="form-control form-inline" name="descripcion"></td><td style="width:110px;">
        <select class="form-control" name="divisa">
            <option value="0">Pesos</option>
            <option value="1" selected>Dolares</option>
        </select></td><td style="width:75px">
            <input class="form-control " name="precio">
        </td><td style="width:75px">  
            <span class="form-group">                                    
                <input class="form-control cant" name="porcentaje" id="porcentaje" type="number" value="100">
            </span>
        </td>
            <td style="width:60px">
                <span class="btn btn-xs btn-circle btn-danger btnDelItem">
                    <span class="fa fa-minus fw"></span>
                    
                </span>
            </td>
</tr>