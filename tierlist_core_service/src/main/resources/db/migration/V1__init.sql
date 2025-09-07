-- Таблица тир-листов
CREATE TABLE tiers (
    id BIGSERIAL PRIMARY KEY,
    userId BIGINT,      -- из сервиса users
    name VARCHAR(100),
    description VARCHAR(500)
);
CREATE INDEX ON tiers(userId);

-- Таблица рядов, доступных в конкретном тир-листе
CREATE TABLE tier_rows (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100),
    color VARCHAR(6) CHECK (color ~ '^[0-9A-Fa-f]{6}$'),      -- в формате "FFFFFF"
    priorityIndex INTEGER,
    tierId BIGINT REFERENCES tiers(id) ON DELETE CASCADE     -- в каком тире лежит данный ряд
);
CREATE INDEX ON tier_rows(tierId);

-- Таблица объектов, которые можно перемещать по рядам
CREATE TABLE tier_objects (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100),
    pictureId BIGINT,       -- из сервиса pictures
    description VARCHAR(500),
    priorityIndex INTEGER,
    rowId BIGINT REFERENCES tier_rows(id) ON DELETE SET NULL,     -- в каком ряду лежит данный объект
    tierId BIGINT REFERENCES tiers(id) ON DELETE CASCADE     -- в каком тире можно использовать данный объект
);
CREATE INDEX ON tier_objects(rowId);
CREATE INDEX ON tier_objects(tierId);

-- Таблица тегов
CREATE TABLE tier_tags (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100),
    color VARCHAR(6) CHECK (color ~ '^[0-9A-Fa-f]{6}$'),      -- в формате "FFFFFF"
    tierId BIGINT REFERENCES tiers(id) ON DELETE CASCADE    -- в каком тире можно использовать данный тег
);
CREATE INDEX ON tier_tags(tierId);

-- Таблица соответствия (ManyToMany) между объектами и присвоенными им тегами
CREATE TABLE tags_by_object (
    objectId BIGINT,
    tagId BIGINT,
    PRIMARY KEY (objectId, tagId),
    FOREIGN KEY (objectId) REFERENCES tier_objects(id) ON DELETE SET NULL,
    FOREIGN KEY (tagId) REFERENCES tier_tags(id) ON DELETE SET NULL
);
CREATE INDEX ON tags_by_object(tagId);