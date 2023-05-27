insert into authority
    (authority)
values
    ('ADMIN'),
    ('VETERINARIO'),
    ('FUNCIONARIO'),
    ('TUTOR');

insert into clinicas (
    id, bairro, celular, cep, cidade, cnae, cnpj, code, email, estado, logradouro, nome_fantasia, numero, razao_social, telefone, responsavel_tecnico_id
) values
      (1, 'Bairro Fictício', '(19) 99435-8082', '13060-587', 'Campinas', '7500-1/00', '69.136.908/0001-23', 'bnmta41', 'diretoria@marioeedsonmarketingme.com.br', 'SP', 'Avenida Gilberto Targon', 'Clinica Ficticia', '635', 'Mário e Edson Marketing ME', '(19) 3713-9577', NULL),
      (2, 'Vila Paranaguá', '(11) 99999-9999', '03808-130', 'São Paulo', '46546', '65.622.164/0001-04', 1, 'contato@clinica.com', 'AM', 'Rua Miguel Rachid', 'Nome fantasia clinica', '546', 'Razão social Clinica', '(11) 1234-5678', NULL);

INSERT INTO usuarios
    (id, bairro, celular, cep, cidade, cpf, crmv, deleted_at, email, estado, logradouro, nome, numero, password, reset_password_token, rg, telefone, username, clinica_id)
VALUES
    (1, 'Bairro do usuario', '(11) 98765-4321','09089-777', 'São Paulo', '489.576.098-00', 'CRMV123456', null, 'mario@quem.viu','SP', 'Rua das camelias', 'Mario Viu', '12', '$2a$10$yE9FznmKoPgAVRz5Yklkw.f1ImjjkxuYIHfSiwlT1EYK/y24laFHm', NULL, 'RG123654', '(11) 1234-5678','mario@quem.viu','1'),
    (2, 'Velame', '(83) 98920-8496', '58420-290', 'Campina Grande', '759.755.587-35', 'SP-123456', NULL, NULL, 'PB', 'Rua Frutuoso Maria Vasconcelos', 'Sarah Cristiane Moreira', '862', '$2a$10$fuus0V3IbYjKbNt0f1QCDOd2JstI.FKBIISEwmRS5yRnzuXJy5Kr6', NULL, '28.563.840-3', '(83) 2999-8303', 'sarah_cristiane_moreira@fundasa.com.br', 1),
    (3, 'Planalto', '(84) 98799-1434', '59073-305', 'Natal', '971.636.160-23', 'SP-123456', NULL, NULL, 'RN', 'Rua Campestre Menino Jesus', 'Murilo Rodrigo Julio Pinto', '558', NULL, NULL, '11.214.014-2', '(84) 3830-7003', 'murilo_rodrigo_pinto@corp.inf.br', 1),
    (4, 'vjidsbvdfl', '12345678', '12345-025', 'fdsgvfdbdf', '261.499.740-49', 'SP-123456', NULL, NULL, 'SP', 'rua rua rau', 'vet cadastrado', '123s', '$2a$10$CajaIRqJ1bN9gdu83zkc7OV.6YPDg3aYAeycfELOQlkEGohOtUuIW', NULL, '123456', '145648', 'email@validado.com', 1);

UPDATE clinicas
SET responsavel_tecnico_id = 1
WHERE id = 1;

insert into documento
    (id, caminho, tipo, algorithm, caminho_arquivo, criado_em, etag, md5, versao, clinica_id, veterinario_id, codigo)
values
    (1, 'caminho','tipo', 'algorithm', 'caminho_arquivo', '2023-04-27 00:17:27', 'etag', 'md5', 1, 1, 1, 'VT-D-2023_05_03_10_26_39');

INSERT INTO animal
    (id, especie, forma_identificacao, nome, pelagem, raca, sexo, ano_nascimento, peso)
VALUES
      (1, 'especie', 'forma_identificacao', 'nome', 'pelagem', 'raca',  1,  2020, 2.5);

insert into prontuario
    (id, apetite, codigo, conciencia, data_atendimento, deambulacao, diarreia, escore_corporal, espasmos_convulsao, febre, frequencia_cardiaca, frequencia_respiratoria, hidratacao, lesoes_nodulos, linfonodos, linfonodos_obs, mucosa, prostracao, regiao_abdomen, regiao_cabeca, regiao_cervical, regiaompelvicos, regiaomtoracicos, regiao_torax, sensibilidade_dor, supeita_diagnostica, temperatura, tpc, vomito, animal_id, cirurgia_id, clinica_id, tutor_id, usuario_id, criado_em, versao)
values
    (1, 'apetite', 'vet-132', 'conciencia', '2023-04-27 00:34:14', 0, 0, 'escore_corporal', 0, 0, '1', '1', 'hidratacao', 0, 'linfonodos', 'linfonodos_obs', 'mucosa', 0, 'regiao_abdomen', 0, 'regiao_cervical', 'regiaompelvicos', 'regiaomtoracicos', 0, 0, 'supeita_diagnostica', '2', 'tpc', 0, 1, NULL, 1, 1, 1, '2023-04-27 00:34:14', 1);

insert into prontuario_documentos
    (prontuario_id, documentos_id)
values
    (1,1);

alter table usuario_authorities
    add constraint key_name
        unique (authorities_id, users_id);


insert into usuario_authorities
(users_id, authorities_id)
values
    (1,3),
    (1,1),
    (2,4),
    (3,2);

INSERT INTO certvet.usuarios (bairro, celular, cep, cidade, cpf, crmv, deleted_at, email, estado, logradouro, nome,
                              numero, password, reset_password_token, rg, telefone, username, clinica_id)
VALUES
    ("Bairro", '(11)987452541', '05856-160', 'Aracaju', '260.604.320-00', null, null, 'ilana.s@gmail.teste', 'SE', 'RUA ', 'Ilana Silveira', '23', null, null, '43.776.341-9', '(11)987452541', 'ilana.s@gmail.teste', 3),
    ('Calhau','(98) 99967-5470','65071-485','São Luís','479.048.782-24',null,null,'julia.teresinha.mendes@vemter.com.br','MA','Rua Tremembes','Vera Ivanovna',114,null,null,'12.602.923-4','(98) 3708-8624','julia.teresinha.mendes@vemter.com.br',3);

INSERT INTO certvet.animal (ano_nascimento, especie, forma_identificacao, nome, pelagem, peso, raca, sexo)
VALUES (2017, 'Canina', null, 'Bidu', 'Longa', 10, 'SRD', 1),
       (2015, 'Felina', null, 'Fifi', 'Curta', 3, 'Siames', 2);

INSERT INTO certvet.prontuario (apetite, codigo, conciencia, criado_em, data_atendimento, deambulacao, diarreia,
                                escore_corporal, espasmos_convulsao, febre, frequencia_cardiaca,
                                frequencia_respiratoria, hidratacao, lesoes_nodulos, linfonodos, linfonodos_obs, mucosa,
                                prostracao, regiao_abdomen, regiao_cabeca, regiao_cervical, regiaompelvicos,
                                regiaomtoracicos, regiao_torax, sensibilidade_dor, supeita_diagnostica, temperatura,
                                tpc, versao, vomito, animal_id, cirurgia_id, clinica_id, tutor_id, usuario_id)
VALUES (null, 'VET-123', 'Consciente', '2023-05-13 18:18:37.000000', null, true, false, 'Ideal', false, false, 112, 24,
        '>= 3s', false, 'null', 'null', 'Rosácea', true, null, false, null, null, null, false, false,
        'Intoxicação por planta domestica', 38, '< 2s', 1, true, 3, null, 3, 6, null);

INSERT INTO certvet.procedimento (descricao, prontuario_id)
VALUES ('Consulta', 2);

