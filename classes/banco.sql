-- -----------------------------------------------------
-- Table categoria
-- -----------------------------------------------------
DROP TABLE IF EXISTS categoria ;

CREATE TABLE IF NOT EXISTS categoria (
  id INT NOT NULL,
  nome VARCHAR(35) NOT NULL,
  PRIMARY KEY (id))
;


-- -----------------------------------------------------
-- Table subcategoria
-- -----------------------------------------------------
DROP TABLE IF EXISTS subcategoria ;

CREATE TABLE IF NOT EXISTS subcategoria (
  titulo VARCHAR(45) NOT NULL,
  Text TEXT NULL,
  categoria_id INT NOT NULL,
  URL VARCHAR(85) NOT NULL,
  id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_subcategoria_categoria
    FOREIGN KEY (categoria_id)
    REFERENCES categoria (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

CREATE INDEX fk_subcategoria_categoria_idx ON subcategoria (categoria_id ASC) ;

CREATE unique INDEX titulo_unique ON subcategoria (titulo ASC) ;


-- -----------------------------------------------------
-- Table usuario
-- -----------------------------------------------------
DROP TABLE IF EXISTS usuario ;
CREATE TABLE IF NOT EXISTS usuario (
  email VARCHAR(45) NOT NULL,
  senha VARCHAR(16) NOT NULL,
  usuarionome VARCHAR(45) NOT NULL,
  cep INT NOT NULL,
  numerosres INT NOT NULL,
  telefone VARCHAR(14) NULL,
  numerocartao VARCHAR(16) NOT NULL,
  cvc VARCHAR(3) NOT NULL,
  datavalidade DATE NOT NULL,
  id INT NOT NULL,
  PRIMARY KEY (id))
  ;

CREATE unique INDEX usuarionome_unique ON usuario (usuarionome ASC) ;

CREATE unique INDEX email_unique ON usuario (email ASC) ;

DROP TABLE IF EXISTS acessar;

CREATE TABLE IF NOT EXISTS acessar(
  usuario_id INT NOT NULL,
  categoria_id INT NOT NULL,
  PRIMARY KEY (usuario_id, categoria_id),
  CONSTRAINT fk_usuario_has_categoria_usuario1
    FOREIGN KEY (usuario_id)
    REFERENCES usuario (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_usuario_has_categoria_categoria1
    FOREIGN KEY (categoria_id)
    REFERENCES categoria (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

CREATE INDEX fk_usuario_has_categoria_categoria1_idx ON acessar (categoria_id ASC);

CREATE INDEX fk_usuario_has_categoria_usuario1_idx ON acessar (usuario_id ASC);
