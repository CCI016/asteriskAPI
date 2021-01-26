INSERT INTO ari_settings
    (id, server_IP, user, password, ari_version, application_name)
VALUES
    (1, 'http://127.0.0.1:8088/', 'unifun', 'unifun', 'ARI_4_0_0', 'Unifun-ARI');


INSERT INTO `provider`
    (`id`, `name`)
VALUES
    ('1', 'Bucuria'),
    ('2', 'Rochen'),
    ('3', 'Ferrero');

INSERT INTO `prompt`
    (`id`, `ariName`, `originalName`)
VALUES
    ('1', 'hello-world', 'hello'),
    ('2', 'hello-world', 'Nice');

INSERT INTO `candy`
    (`id`, `is_deleted`, `name`, `prompt`, `provider`, `numberOfPlays`)
VALUES
    ('1', b'0', 'DoReMi', '1', '1', '1'),
    ('2', b'0', 'Albinuta', '2', '2', '2'),
    ('3', b'0', 'Rafaello', '1', '3', '3'),
    ('4', b'0', 'Rocher', '2', '3', '5'),
    ('5', b'0', 'HubaBuba', '1', '1', '1'),
    ('6', b'0', 'Tofix', '2', '2', '2'),
    ('7', b'0', 'Aleonka', '1', '3', '3'),
    ('8', b'0', 'test1', '2', '3', '5'),
    ('9', b'0', 'test2', '1', '1', '1'),
    ('10', b'0', 'test3', '2', '2', '2'),
    ('11', b'0', 'test4', '1', '3', '3'),
    ('12', b'0', 'test5', '2', '3', '5');