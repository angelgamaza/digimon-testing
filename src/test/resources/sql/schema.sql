----------------------------------------------------
-- Deletions
----------------------------------------------------

-- Tables
DROP TABLE IF EXISTS
    type,
    level,
    digimon
    CASCADE;

----------------------------------------------------
-- Tables
----------------------------------------------------

-- Table: Type
CREATE TABLE type
(
    id            BIGSERIAL,
    name          TEXT      NOT NULL,
    created_date  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_type PRIMARY KEY (id),
    CONSTRAINT uk_type_name UNIQUE (name)
);

-- Comments: Type
COMMENT ON TABLE type IS 'Table created in order to store all Digimon types reated for the system';

-- Indexes: Type
CREATE UNIQUE INDEX ui_type_name ON type USING btree (name);

-- Table: Level
CREATE TABLE level
(
    id            BIGSERIAL,
    name          TEXT      NOT NULL,
    created_date  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_level PRIMARY KEY (id),
    CONSTRAINT uk_level_name UNIQUE (name)
);

-- Comments: Level
COMMENT ON TABLE level IS 'Table created in order to store all Digimon levels reated for the system';

-- Indexes: Level
CREATE UNIQUE INDEX ui_level_name ON level USING btree (name);

-- Table: Digimon
CREATE TABLE digimon
(
    id            BIGSERIAL,
    name          TEXT      NOT NULL,
    type          BIGINT    NOT NULL,
    level         BIGINT    NOT NULL,
    created_date  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_digimon PRIMARY KEY (id),
    CONSTRAINT uk_digimon_name UNIQUE (name),
    CONSTRAINT fk_digimon_type FOREIGN KEY (type) REFERENCES type (id)
        MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_digimon_level FOREIGN KEY (level) REFERENCES level (id)
        MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- Comments: Digimon
COMMENT ON TABLE digimon IS 'Table created in order to store all Digimons created for the system';

-- Indexes: Digimon
CREATE UNIQUE INDEX ui_digimon_name ON digimon USING btree (name);