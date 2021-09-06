USE `account-micro`;
#
# ADD ACCOUNTS
#
START TRANSACTION;
INSERT INTO `account`
(`id`, `first_name`, `last_name`, `patronymic`, `password`, `username`, `created_timestamp`,
 `is_credentials_non_expired`, `is_enabled`, `is_non_expired`, `is_non_locked`)
VALUES (1, 'Ostap', 'Komplikevych', 'Yaroslavovych',
        '$2a$12$dBn3WC3BdHEyYGyf5QloTe8pDle14yJ7V1WVr2fjcIMe72HwTm54e', -- Qwert1234
        'ostap.ja@gmail.com', '2021-08-15 16:52:00', 1, 1, 1, 1),
       (2, 'Ostap', 'Komplikevych', 'Yaroslavovych',
        '$2a$12$dBn3WC3BdHEyYGyf5QloTe8pDle14yJ7V1WVr2fjcIMe72HwTm54e', -- Qwert1234
        'user@gmail.com', '2021-08-15 16:52:00', 1, 1, 1, 1);
COMMIT;