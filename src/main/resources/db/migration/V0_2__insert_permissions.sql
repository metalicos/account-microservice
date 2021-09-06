USE `account-micro`;
#
# ADD DEFAULT PERMISSIONS
#
START TRANSACTION;
INSERT INTO `permission` (id, name, value, created_timestamp)
VALUES (1, 'Read All', 'r_all', '2021-08-15 00:00:00'),
       (2, 'Write All', 'w_all', '2021-08-15 00:00:00'),
       (3, 'Update All', 'u_all', '2021-08-15 00:00:00'),
       (4, 'Delete All', 'd_all', '2021-08-15 00:00:00'),

       (5, 'Read Permissions', 'r_permissions', '2021-08-15 00:00:00'),
       (6, 'Write Permissions', 'w_permissions', '2021-08-15 00:00:00'),
       (7, 'Update Permissions', 'u_permissions', '2021-08-15 00:00:00'),
       (8, 'Delete Permissions', 'd_permissions', '2021-08-15 00:00:00'),

       (9, 'Read Users', 'r_users', '2021-08-15 00:00:00'),
       (10, 'Write Users', 'w_users', '2021-08-15 00:00:00'),
       (11, 'Update Users', 'u_users', '2021-08-15 00:00:00'),
       (12, 'Delete Users', 'd_users', '2021-08-15 00:00:00'),

       (13, 'Read Admins', 'r_admins', '2021-08-15 00:00:00'),
       (14, 'Write Admins', 'w_admins', '2021-08-15 00:00:00'),
       (15, 'Update Admins', 'u_admins', '2021-08-15 00:00:00'),
       (16, 'Delete Admins', 'd_admins', '2021-08-15 00:00:00'),

       (17, 'Read Super Admins', 'r_super_admins', '2021-08-15 00:00:00'),
       (18, 'Write Super Admins', 'w_super_admins', '2021-08-15 00:00:00'),
       (19, 'Update Super Admins', 'u_super_admins', '2021-08-15 00:00:00'),
       (20, 'Delete Super Admins', 'd_super_admins', '2021-08-15 00:00:00'),

       (21, 'Read Self', 'r_self', '2021-08-15 00:00:00'),
       (22, 'Write Self', 'w_self', '2021-08-15 00:00:00'),
       (23, 'Update Self', 'u_self', '2021-08-15 00:00:00'),
       (24, 'Delete Self', 'd_self', '2021-08-15 00:00:00'),

       (25, 'Read Account Details', 'r_account_details', '2021-08-15 00:00:00'),
       (26, 'Add Account Details', 'w_account_details', '2021-08-15 00:00:00'),
       (27, 'Update Account Details', 'u_account_details', '2021-08-15 00:00:00'),
       (28, 'Delete Account Details', 'd_account_details', '2021-08-15 00:00:00'),

       (29, 'Read Role', 'r_role', '2021-08-15 00:00:00'),
       (30, 'Write Role', 'w_role', '2021-08-15 00:00:00'),
       (31, 'Update Role', 'u_role', '2021-08-15 00:00:00'),
       (32, 'Delete Role', 'd_role', '2021-08-15 00:00:00'),

       (33, 'Read Account Role', 'r_account_role', '2021-08-15 00:00:00'),
       (34, 'Add Account Role', 'w_account_role', '2021-08-15 00:00:00'),
       (35, 'Update Account Role', 'u_account_role', '2021-08-15 00:00:00'),
       (36, 'Delete Account Role', 'd_account_role', '2021-08-15 00:00:00');
COMMIT;
