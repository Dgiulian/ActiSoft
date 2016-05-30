select distinct(remito_detalle.id), remito_detalle.*,contrato_detalle.*
from remito_detalle, remito,contrato,contrato_detalle
where remito_detalle.id_remito = remito.id
and remito.id_contrato = contrato.id
and contrato_detalle.id_contrato = remito.id_contrato
and remito_detalle.item = contrato_detalle.posicion
and remito.id = 43