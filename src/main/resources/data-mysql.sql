insert into responsabilidades
    (permissao)
values
    ('ADMIN'),
    ('VETERINARIO'),
    ('FUNCIONARIO'),
    ('TUTOR');

INSERT INTO SEXO_ANIMAL
    (bit_value, genero)
VALUES
    (false, 'F'),
    (true, 'M');

INSERT INTO apetite
    (code, status)
VALUES
    (0,'NORMAL'),
    (1,'REDUZIDO'),
    (2,'AUMENTADO');

INSERT INTO linfonodos(LINFONODO)
VALUES
    ('Mandibular'),
    ('Cervical'),
    ('Mamários'),
    ('Inguinal'),
    ('Popiliteo'),
    ('Outras');

INSERT INTO tipo_exame(tipo_exame_enum)
VALUES
    ('BIOQUIMICO'),
    ('CITOLOGIA'),
    ('HEMATOLOGIA'),
    ('IMAGEM'),
    ('OUTROS');

INSERT INTO tipos_procedimentos (descricao) VALUES
    ("Coleta de Sangue"),
    ("Coleta de Material Biológico"),
    ("Curativo"),
    ("Fluidoterapia"),
    ("Limpeza Otológica"),
    ("Medicação"),
    ("Profilaxia Dentária - Tartarectomia"),
    ("Quimioterapia"),
    ("Sedação"),
    ("Sutura"),
    ("Tala / Imobilização"),
    ("Vacinação"),
    ("Vermifugação"),
    ("Emissão de Termos"),
    ("Outros");
