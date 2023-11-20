    create table consolidado (
        totalDebitos numeric(17,2) not null,
        totalCreditos numeric(17,2) not null,
        saldo numeric(17,2) not null,
        quantidadeDebitos integer not null,
        quantidadeCreditos integer not null,
        data date not null,
        primary key (data)
    );

    create table lancamento (
        valor numeric(17,2) not null,
        data timestamp(6) not null,
        id bigserial not null,
        tipo varchar(10) not null,
        descricao varchar(100) not null,
        primary key (id)
    );
