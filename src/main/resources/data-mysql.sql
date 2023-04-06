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