----------------------------------------------------
-- Insertions
----------------------------------------------------

-- Tables truncation
TRUNCATE TABLE type, level, digimon RESTART IDENTITY CASCADE;

-- Sequences reset
ALTER SEQUENCE type_id_seq RESTART WITH 1;
ALTER SEQUENCE level_id_seq RESTART WITH 1;
ALTER SEQUENCE digimon_id_seq RESTART WITH 1;

-- Inserts: Type
INSERT INTO type(name)
VALUES ('Vaccine'),
       ('Virus'),
       ('Data'),
       ('No Relation');

-- Inserts: Level
INSERT INTO level(name)
VALUES ('Baby'),
       ('In-Training'),
       ('Rookie'),
       ('Champion'),
       ('Mega'),
       ('Ultimate');

-- Inserts: Digimon
INSERT INTO digimon(name, type, level)
VALUES ('Agumon', (SELECT id FROM type WHERE name = 'Vaccine'), (SELECT id FROM level WHERE name = 'Rookie')),
       ('Greymon', (SELECT id FROM type WHERE name = 'Vaccine'), (SELECT id FROM level WHERE name = 'Champion')),
       ('MetalGreymon', (SELECT id FROM type WHERE name = 'Vaccine'), (SELECT id FROM level WHERE name = 'Mega')),
       ('WarGreymon', (SELECT id FROM type WHERE name = 'Vaccine'), (SELECT id FROM level WHERE name = 'Ultimate'));