insert into authority
    (authority)
values
    ('ADMIN'),
    ('VETERINARIO'),
    ('FUNCIONARIO'),
    ('TUTOR');

INSERT INTO Clinicas
    (bairro, celular, cep, cidade, cnae, cnpj, email, estado, logradouro, nome_fantasia, numero, razao_social, telefone, code)
VALUES
    ('Vila Paranaguá', '(11) 99999-9999', '03808-130','São Paulo', '46546', '65.622.164/0001-04', 'contato@clinica.com', 'AM', 'Rua Miguel Rachid', 'Nome fantasia clinica', '546', 'Razão social Clinica', '(11) 1234-5678', 1);

INSERT INTO usuarios
    (id, bairro, celular, cep, cidade, cpf, crmv, deleted_at, email, estado, logradouro, nome, numero, password, rg, telefone, username, clinica_id)
VALUES
    (1, 'Bairro do usuario', '(11) 98765-4321','09089-777', 'São Paulo', '489.576.098-00', 'CRMV123456', null, 'mario@quem.viu','SP', 'Rua das camelias', 'Mario Viu', '12', '$2a$10$yE9FznmKoPgAVRz5Yklkw.f1ImjjkxuYIHfSiwlT1EYK/y24laFHm', 'RG123654', '(11) 1234-5678','mario@quem.viu','1');

INSERT INTO animal(
    especie, forma_identificacao, idade, nome, pelagem, raca, sexo, ano_nascimento, peso
) VALUES (
    'especie', 'forma_identificacao', 2, 'nome', 'pelagem', 'raca',  1,  2020, 2.5
);

INSERT INTO PRONTUARIO(
    apetite, codigo, conciencia, data_atendimento, deambulacao, diarreia, escore_corporal, espasmos_convulsao, febre, frequencia_cardiaca, frequencia_respiratoria, hidratacao, lesoes_nodulos, linfonodos, linfonodos_obs, mucosa, prostracao, regiao_abdomen, regiao_cabeca, regiao_cervical, regiaompelvicos, regiaomtoracicos, regiao_torax, sensibilidade_dor, supeita_diagnostica, temperatura, tpc, vomito, animal_id, clinica_id, tutor_id, usuario_id, tutor, criado_em, versao
) VALUES (
     'apetite', 'codigo', 'conciencia', NOW(), 0, 0, 'escore_corporal', 0, 0, 1, 1, 'hidratacao', 0, 'linfonodos', 'linfonodos_obs', 'mucosa', 0, 'regiao_abdomen', 0, 'regiao_cervical', 'regiaompelvicos', 'regiaomtoracicos', 0, 0, 'supeita_diagnostica', 2, 'tpc', 0, 1, 1, 1, 1, 1, NOW(), 1
 );

INSERT INTO documento(
    assinatura_responsavel, assinatura_vet, cabecalho, caminho, causa_mortis, declara_ciencia_riscos, declara_consentimento, explica_duas_vias, identifica_animal, identifica_responsavel, local_data, observacoes_responsavel, observacoes_veterinario, orienta_destino_corpo, outras_observacoes, tipo, titulo, prontuario_id, algorithm, caminho_arquivo, criado_em, etag, md5, versao, clinica_id, veterinario_id, name
) VALUES (
     'assinatura_responsavel', 'assinatura_vet', 'cabecalho', 'caminho', 'causa_mortis', 'declara_ciencia_riscos', 'declara_consentimento', 'explica_duas_vias', 'identifica_animal', 'identifica_responsavel', 'local_data', 'observacoes_responsavel', 'observacoes_veterinario', 'orienta_destino_corpo', 'outras_observacoes', 'tipo', 'titulo', 7, 'algorithm', 'caminho_arquivo', NOW(), 'etag', 'md5', 1, 1, 1, 'name'
 );


