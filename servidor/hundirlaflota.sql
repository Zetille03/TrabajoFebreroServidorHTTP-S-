DROP DATABASE IF EXISTS hundirflota;
CREATE DATABASE hundirflota CHARACTER SET utf8mb4;
USE hundirflota;
SET SQL_SAFE_UPDATES = 0;
DROP TABLE IF EXISTS barcos;
DROP TABLE IF EXISTS partidas;
DROP TABLE IF EXISTS usuarios;


CREATE TABLE usuarios(
	id_usuario INTEGER NOT NULL,
    nombre_usuario VARCHAR(255) NOT NULL,
    contra_usuario VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_usuario)
);

INSERT INTO usuarios VALUES (1,'ricardo','1234');
INSERT INTO usuarios VALUES (2,'mario','1234');
INSERT INTO usuarios VALUES (3,'lolo','1234');
INSERT INTO usuarios VALUES (4,'dani','1234');
INSERT INTO usuarios VALUES (5,'gali','1234');
INSERT INTO usuarios VALUES (6,'pablo','1234');
INSERT INTO usuarios VALUES (7,'fachafriend','1234');
INSERT INTO usuarios VALUES (8,'agus','1234');

/*DELETE FROM partidas;
ALTER TABLE partidas AUTO_INCREMENT = 1;
*/

CREATE TABLE partidas(
	id_partida INTEGER AUTO_INCREMENT,
    id_jugador1 INTEGER NOT NULL,
    id_jugador2 INTEGER NOT NULL,
    id_ganador INTEGER,
    disparos VARCHAR(255),
    terminada boolean,
    fecha_creacion DATETIME,
    PRIMARY KEY (id_partida),
    FOREIGN KEY (id_jugador1) REFERENCES usuarios (id_usuario),
    FOREIGN KEY (id_jugador2) REFERENCES usuarios (id_usuario),
    FOREIGN KEY (id_ganador) REFERENCES usuarios (id_usuario)
);

/*DELETE FROM barcos;*/
CREATE TABLE barcos(
	id_jugador INTEGER NOT NULL,
    id_partida INTEGER NOT NULL,
    colocacion_barcos VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_jugador,id_partida),
    FOREIGN KEY (id_jugador) REFERENCES usuarios (id_usuario),
    FOREIGN KEY (id_partida) REFERENCES partidas (id_partida)
);