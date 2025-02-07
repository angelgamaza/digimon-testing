----------------------------------------------------
-- Insertions
----------------------------------------------------

-- Tables truncation
TRUNCATE TABLE type RESTART IDENTITY CASCADE;

-- Sequences reset
ALTER SEQUENCE type_id_seq RESTART WITH 1;

-- Inserts: Type
INSERT INTO type(name)
VALUES ('Vaccine'),
       ('Virus'),
       ('Data'),
       ('No Relation');