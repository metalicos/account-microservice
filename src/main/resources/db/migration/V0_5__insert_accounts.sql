USE `account-micro`;
#
# ADD ACCOUNTS
#
START TRANSACTION;
INSERT INTO `account`
(`id`, `first_name`, `last_name`, `patronymic`, `password`, `username`, `created_timestamp`,
 `is_credentials_non_expired`, `is_enabled`, `is_non_expired`, `is_non_locked`)
VALUES (1, 'Ostap', 'Komplikevych', 'Yaroslavovych',
        '$2a$14$HD1r9/AN7It1RLICBvI1auI2u7Nka9jnLWaHHh50r2I/WXhwfKAEy', -- Qwert1234
        'ostap.ja@gmail.com', '2021-08-15 16:52:00', 1, 1, 1, 1),
       (2, 'Ostap', 'Komplikevych', 'Yaroslavovych',
        '$2a$14$HD1r9/AN7It1RLICBvI1auI2u7Nka9jnLWaHHh50r2I/WXhwfKAEy', -- Qwert1234
        'user@gmail.com', '2021-08-15 16:52:00', 1, 1, 1, 1);
COMMIT;