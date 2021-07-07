drop table if exists memo_slowo CASCADE;
drop table if exists memo_zdanie CASCADE;
drop table if exists memo_nauka CASCADE;
drop table if exists memo_nauczone CASCADE;

CREATE TABLE memo_slowo
(
    id_slowa SERIAL NOT NULL,
    slowo character varying(40) NOT NULL,
    tlumaczenie character varying(40)  NOT NULL,
    rodzaj character varying(40)  NOT NULL,
    dzwiek character varying(100),
    obraz character varying(100),
    CONSTRAINT memo_slowo_pkey PRIMARY KEY (id_slowa)
);
CREATE TABLE memo_zdanie
(
    id_zdania SERIAL NOT NULL,
    id_slowa integer NOT NULL,
    zdanie character varying(300) NOT NULL,
    zdanie_tlumaczenie character varying(300) NOT NULL,
    dzwiek character varying(100) ,
    CONSTRAINT memo_zdanie_pkey PRIMARY KEY (id_zdania),
    CONSTRAINT fk_zdanie_slowo FOREIGN KEY (id_slowa)
        REFERENCES memo_slowo (id_slowa)
);

CREATE TABLE memo_nauka
(
    pozycja SERIAL NOT NULL,
    id_slowa integer NOT NULL,
    czy_umiem boolean,
    wspolczynnik_powtorek double precision,
    CONSTRAINT memo_nauka_pkey PRIMARY KEY (pozycja),
    CONSTRAINT fk_nauka FOREIGN KEY (id_slowa)
        REFERENCES memo_slowo (id_slowa)
);
CREATE TABLE memo_nauczone
(
    id_slowa SERIAL NOT NULL,
    pierwsza_nauka date,
    ostatnia_nauka date,
    nastepna_nauka date,
    ilosc_powtorzen integer,
    CONSTRAINT memo_nauczone_pkey PRIMARY KEY (id_slowa),
    CONSTRAINT fk_nauczone FOREIGN KEY (id_slowa)
        REFERENCES memo_slowo (id_slowa)
);

COMMIT;
