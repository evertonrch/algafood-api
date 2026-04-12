INSERT INTO tb_cozinha (nome) VALUES ('BRASILEIRA');
INSERT INTO tb_cozinha (nome) VALUES ('MEXICANA');

INSERT INTO tb_restaurante (nome, taxa_frete, cozinha_id) VALUES ('coma bem', 23.00, 1);
INSERT INTO tb_restaurante (nome, taxa_frete, cozinha_id) VALUES ('tacos mexicanos', 15.00, 2);
INSERT INTO tb_restaurante (nome, taxa_frete, cozinha_id) VALUES ('algum restaurante', 15.00, 2);
INSERT INTO tb_restaurante (nome, taxa_frete, cozinha_id) VALUES ('outro restaurante', 15.00, 1);

INSERT INTO tb_forma_pagamento (descricao, restaurante_id) VALUES ('PIX', 2);
INSERT INTO tb_forma_pagamento (descricao, restaurante_id) VALUES ('PIX', 1);
INSERT INTO tb_forma_pagamento (descricao, restaurante_id) VALUES ('BOLETO', 2);
INSERT INTO tb_forma_pagamento (descricao, restaurante_id) VALUES ('BOLETO', 4);

INSERT INTO tb_estado (nome) VALUES ('Rio de Janeiro');
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Niteroi', 1);


