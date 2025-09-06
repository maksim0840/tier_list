-- Таблица тир-листов
CREATE TABLE tiers (
    id SERIAL PRIMARY KEY,
    userId BIGINT,      -- из сервиса users
    name VARCHAR(50),
    description VARCHAR(200)
);

-- Таблица рядов, доступных в конкретном тир-листе
CREATE TABLE tier_rows (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    color VARCHAR(10),      -- в формате "FFFFFF"
    priorityIndex INTEGER,
    tierId FOREIGN KEY REFERENCES tiers(id)     -- в каком тире лежит данный ряд
);

-- Таблица объектов, которые можно перемещать по рядам
CREATE TABLE tier_objects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    pictureId BIGINT,       -- из сервиса pictures
    description VARCHAR(200),
    priorityIndex INTEGER,
    rowId FOREIGN KEY REFERENCES tier_rows(id),     -- в каком ряду лежит данный объект
    tierId FOREIGN KEY REFERENCES tiers(id)     -- в каком тире можно использовать данный объект
);

-- Таблица тегов
CREATE TABLE tier_tags (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    color VARCHAR(10),      -- в формате "FFFFFF"
    tierId FOREIGN KEY REFERENCES tiers(id)    -- в каком тире можно использовать данный тег
);

-- Таблица соответствия (ManyToMany) между объектами и присвоенными им тегами
CREATE TABLE tags_by_object (
    objectId BIGINT,
    tagId BIGINT,
    PRIMARY KEY (objectId, tagId),
    FOREIGN KEY (objectId) REFERENCES tier_objects(id),
    FOREIGN KEY (tagId) REFERENCES tier_tags(id)
);
