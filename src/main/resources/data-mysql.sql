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

INSERT INTO tipos_procedimentos (nome) VALUES
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

INSERT INTO coluna_regioes (nome) VALUES
    ("Cervical"),
    ("Torácica"),
    ("Lombar"),
    ("Sacral"),
    ("Caudal");

INSERT INTO abdomen_regioes (nome) VALUES
    ("Epigástrica"),
    ("Mesogástrica"),
    ("Hipogástrica");

INSERT INTO musculos (nome) VALUES
    ("Pélvicos Direito"),
    ("Pélvicos Esquerdo"),
    ("Pélvicos Proximal"),
    ("Pélvicos Distrital"),
    ("Torácico Direito"),
    ("Torácico Esquerdo"),
    ("Torácico Proximal"),
    ("Torácico Distrital");