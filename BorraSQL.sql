delete from activo;
delete from subrubro;
delete from rubro;
delete from clase;
delete from remito;
delete from remito_detalle;

ALTER TABLE activo MODIFY COLUMN id char(10) ;
ALTER TABLE subrubro MODIFY COLUMN id char(10) ;
ALTER TABLE rubro MODIFY COLUMN id char(10) ;
ALTER TABLE clase MODIFY COLUMN id char(10) ;
COMMIT;
ALTER TABLE activo MODIFY COLUMN id INT(10) UNSIGNED AUTO_INCREMENT;
ALTER TABLE subrubro MODIFY COLUMN id INT(10) UNSIGNED AUTO_INCREMENT;
ALTER TABLE rubro MODIFY COLUMN id INT(10) UNSIGNED AUTO_INCREMENT;
ALTER TABLE clase MODIFY COLUMN id INT(10) UNSIGNED AUTO_INCREMENT;
COMMIT;