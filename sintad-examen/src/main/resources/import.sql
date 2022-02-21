
INSERT INTO tb_tipo_contribuyente (nombre,estado) VALUES ('Natural Sin Negocio', 1);
INSERT INTO tb_tipo_contribuyente (nombre,estado) VALUES ('Juridica', 1);
INSERT INTO tb_tipo_contribuyente (nombre,estado) VALUES ('Natural Con Negocio', 1);
INSERT INTO tb_tipo_contribuyente (nombre,estado) VALUES ('No Domiciliado', 1);

INSERT INTO tb_tipo_documento (codigo,nombre,descripcion,estado) VALUES ( 4, 'CARNET DE EXTRANJERIA', 'CARNET DE EXTRANJERIA', 1);
INSERT INTO tb_tipo_documento (codigo,nombre,descripcion,estado) VALUES ( 7, 'PASAPORTE', 'PASAPORTE', 1);
INSERT INTO tb_tipo_documento (codigo,nombre,descripcion,estado) VALUES ( 11, 'PARTIDA DE NACIMIENTO - IDENTIDAD', 'PARTIDA DE NACIMIENTO - IDENTIDAD', 1);
INSERT INTO tb_tipo_documento (codigo,nombre,descripcion,estado) VALUES ( 99, 'OTROS', 'OTROS', 1);
INSERT INTO tb_tipo_documento (codigo,nombre,descripcion,estado) VALUES ( 6, 'RUC', 'REGISTRO UNICO DEL CONTRIBUYENTE', 1);
INSERT INTO tb_tipo_documento (codigo,nombre,descripcion,estado) VALUES ( 1, 'DNI', 'DOCUMENTO NACIONAL DE IDENTIDAD', 1);

INSERT INTO tb_entidad (direccion,estado,nombre_comercial,nro_documento,razon_social,telefono,tipocontribuyente_id,tipodocumento_id) VALUES ('Jr. Comandante Jimenez Nro. 166 Int',1, 'SYL CARGO NOMBRE COMERCIAL',124545,'SYL S.A.C','79845612',2, 1);

INSERT INTO usuarios (username,password,enabled,nombre,apellido,email) VALUES ('erick','$2a$10$7SKuFjrml41fXe6NyTGsPelVBnORIFDg2MJU5hy4ttdYfO4SA0h5u',1,'Erick','Huamanttupa Cuevas','erickhc.dev@gmail.com');
INSERT INTO usuarios (username,password,enabled,nombre,apellido,email) VALUES ('admin','$2a$10$sA8ZiahIn8wGrykoD7S83uVNc5d.X84eN8aLKkaYHfsAxO8E6Xjbm',1,'Marcos','Guierrez Perez','marcos@hotmail.com');

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1,1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2,2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2,1);