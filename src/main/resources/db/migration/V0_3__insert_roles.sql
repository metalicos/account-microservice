USE `account-micro`;
#
# ADD DEFAULT ACCOUNT ROLES
#
START TRANSACTION;
INSERT INTO `account_role`(`id`, `role`, `created_timestamp`)
VALUES (1, 'OWNER', '2021-08-15 00:00:00'),
       (2, 'SUPER_ADMIN', '2021-08-15 00:00:00'),
       (3, 'ADMIN', '2021-08-15 00:00:00'),
       (4, 'MANAGER', '2021-08-15 00:00:00'),
       (5, 'USER', '2021-08-15 00:00:00');
COMMIT;