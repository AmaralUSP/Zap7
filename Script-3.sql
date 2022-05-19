DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

create table cupom(
	codigo smallint,
	desconto numeric(1, 1),
	validade date check (validade > CURRENT_DATE),
	compra_minima money check (compra_minima::money::numeric::float8 > 0.0),
primary key(codigo));

create table usuario (
	CPF varchar(11),
	nome varchar(50) not null,
	telefone varchar(13),
	email varchar(50) not null,
	CEP varchar(8) not null,
	logradouro varchar(50),
	numero smallint not null,
	bairro varchar(20),
	cidade varchar(30),
	estado varchar(30),
	complemento varchar(30),
primary key(CPF));

create table carrinho(
	valor_total money check (valor_total::money::numeric::float8 >= 0.0),
	cupom smallint REFERENCES cupom(codigo),
    usuario varchar(11) REFERENCES usuario(CPF)
    	on delete cascade
		on update cascade,		 
PRIMARY KEY (usuario));

create table produto (
	identificador varchar(11),
	nome varchar(50) not null,
	preco money check (preco::money::numeric::float8 > 0.0),
	altura smallint,
	largura smallint,
	comprimento smallint,
	peso smallint,
primary key (identificador));

create table compra(
	data_e_hora date,
	carrinho varchar(11) REFERENCES carrinho(usuario)
		on delete cascade
		on update cascade,
    produto varchar(11) REFERENCES produto(identificador)
    	on delete cascade
		on update cascade,
	quantidade smallint not null check (quantidade > 0),
PRIMARY KEY (carrinho, produto, data_e_hora));
