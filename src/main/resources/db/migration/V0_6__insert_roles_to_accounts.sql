USE
`account-micro`;
#
# ADD ROLES TO ACCOUNTS
#
START TRANSACTION;
INSERT INTO `account_has_role` (`account_id`, `role_id`)
VALUES (1, 1),
       (2, 5);
COMMIT;