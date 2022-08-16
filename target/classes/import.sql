INSERT INTO clientes(id, nome, profissao, endereco, data_cadastro, cpf) VALUES (10, 'Barbara Pereira', 'Dentista', 'Rua Marajor, 31, Centro, Nova Lima, MG', '2022-08-01', '111.111.111-11');
INSERT INTO clientes(id, nome, profissao, endereco, data_cadastro, cpf) VALUES (11, 'Fabiano Souza', 'Carpinteiro', 'Rua Marajor, 56, Centro, Nova Lima, MG', '2022-08-01', '111.111.111-12');
INSERT INTO clientes(id, nome, profissao, endereco, data_cadastro, cpf) VALUES (12, 'Carlos Caetano' , 'Médico', 'Av. Afonso Pena, 442, Centro, Ubá, MG', '2022-08-01', '111.111.111-13');

INSERT INTO contas(agencia, conta, saldo, data_criacao, cliente_id) VALUES('10102', '102213', 5142.32, '2022-08-01', 10);

INSERT INTO usuarios(id, nome, email, password) VALUES(500, 'theboss', 'theboss@email.com', '$2a$12$dh3Tr9qGMmV8VQR/iX1FBumGqeBoQ31ctu8GnCD9yUqLGBzOiLyry');