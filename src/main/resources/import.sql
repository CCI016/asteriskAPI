INSERT INTO ari_settings
    (id, server_IP, user, password, ari_version, application_name)
VALUES
    (1, 'http://127.0.0.1:8088/', 'unifun', 'unifun', 'ARI_4_0_0', 'Unifun-ARI');


INSERT INTO `provider`
    (`id`, `name`)
VALUES
    ('1', 'Bucuria'),
    ('2', 'Rochen');

INSERT INTO `prompt`
    (`id`, `ariName`, `numberOfPlays`, `originalName`)
VALUES
    ('1', 'prompt1', '0', 'hello'),
    ('2', 'prompt2', '2', 'Nice');

INSERT INTO `candy`
    (`id`, `is_deleted`, `name`, `prompt`, `provider`)
VALUES
    ('1', b'0', 'DoReMi', '1', '1'),
    ('2', b'0', 'Albinuta', '2', '2');