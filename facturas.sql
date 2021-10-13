use exemples_accessdades;
create table facturas(
num_factura integer auto_increment not null,
dni_cliente char(9) not null,
primary key(num_factura),
foreign key fk_fact_dni_clientes (dni_cliente) references clientes(dni)
);
create table lineas_factura(
num_factura integer not null,
linea_factura smallint not null,
concepto varchar(32) not null,
cantidad smallint not null,
primary key(num_factura, linea_factura),
foreign key fk_lineafact_num_factura(num_factura) references facturas(num_factura)
);
