CREATE TABLE public.produto (
	id int4 NOT NULL,
	descricao varchar NOT NULL,
	preco numeric NOT NULL,
	quantidade int4 NOT NULL,
	datafabricacao date NULL,
	datavalidade date NULL,
	CONSTRAINT produto_pk PRIMARY KEY (id)
);
