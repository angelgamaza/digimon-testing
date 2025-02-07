----------------------------------------------------
-- Insertions
----------------------------------------------------

-- Tables truncation
TRUNCATE TABLE level RESTART IDENTITY CASCADE;

-- Sequences reset
ALTER SEQUENCE level_id_seq RESTART WITH 1;

-- Inserts: Level
INSERT INTO level(name)
VALUES ('Baby'),
       ('In-Training'),
       ('Rookie'),
       ('Champion'),
       ('Mega'),
       ('Ultimate');