USE `account-micro`;

CREATE TABLE IF NOT EXISTS `account-micro`.`invalid_tokens`
(
    `id`                BIGINT        NOT NULL AUTO_INCREMENT,
    `account_id`        BIGINT        NOT NULL,
    `invalid_token`     VARCHAR(1000) NOT NULL,
    `expiration_date`   DATE          NOT NULL,
    `created_timestamp` DATETIME(6)   NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `account-micro`.`account`
(
    `id`                         BIGINT       NOT NULL AUTO_INCREMENT,
    `first_name`                 VARCHAR(25)  NOT NULL,
    `last_name`                  VARCHAR(25)  NOT NULL,
    `patronymic`                 VARCHAR(25)  NOT NULL,
    `username`                   VARCHAR(100) NOT NULL UNIQUE,
    `password`                   VARCHAR(300)  NOT NULL,
    `photo`                      LONGBLOB     NULL DEFAULT NULL,
    `is_credentials_non_expired` BIT(1)       NULL DEFAULT NULL,
    `is_enabled`                 BIT(1)       NULL DEFAULT NULL,
    `is_non_expired`             BIT(1)       NULL DEFAULT NULL,
    `is_non_locked`              BIT(1)       NULL DEFAULT NULL,
    `created_timestamp`          DATETIME(6)  NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_username` (`username` ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `account-micro`.`account_detail`
(
    `id`                BIGINT       NOT NULL AUTO_INCREMENT,
    `account_id`        BIGINT       NOT NULL,
    `gender`            VARCHAR(255) NULL DEFAULT NULL,
    `birth_date`        DATE         NULL DEFAULT NULL,
    `created_timestamp` DATETIME(6)  NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_account_id` (`account_id` ASC) VISIBLE,
    CONSTRAINT `FK_ACCOUNT_DETAILS_account_id_ACCOUNT_id`
        FOREIGN KEY (`account_id`)
            REFERENCES `account-micro`.`account` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `account-micro`.`account_role`
(
    `id`                BIGINT       NOT NULL AUTO_INCREMENT,
    `role`              VARCHAR(200) NOT NULL UNIQUE,
    `read_permissions`   VARCHAR(1000),
    `write_permissions`  VARCHAR(1000),
    `update_permissions` VARCHAR(1000),
    `delete_permissions` VARCHAR(1000),
    `created_timestamp` DATETIME(6)  NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_role` (`role` ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `account-micro`.`account_has_role`
(
    `account_id` BIGINT NOT NULL,
    `role_id`    BIGINT NOT NULL,
    PRIMARY KEY (`account_id`, `role_id`),
    INDEX `FK_role_id_ACCOUNT_ROLE_id` (`role_id` ASC) VISIBLE,
    CONSTRAINT `FK_role_id_ACCOUNT_ROLE_id`
        FOREIGN KEY (`role_id`)
            REFERENCES `account-micro`.`account_role` (`id`),
    CONSTRAINT `FK_account_id_ACCOUNT_id`
        FOREIGN KEY (`account_id`)
            REFERENCES `account-micro`.`account` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- ADD ROLES
START TRANSACTION;
INSERT INTO `account-micro`.`account_role`
(`id`, `created_timestamp`, `role`, `read_permissions`, `write_permissions`, `update_permissions`, `delete_permissions`)
VALUES (1, '2021-08-15 00:00:00', 'OWNER', 'all', 'all', 'all', 'all'),
       (2, '2021-08-15 00:00:00', 'SUPER_ADMIN', 'all', 'w_admin,w_device,w_roles', 'u_roles,u_device',
        'd_roles,d_device'),
       (3, '2021-08-15 00:00:00', 'USER', 'r_user_data', 'w_user_data', 'u_user_data', 'd_user_data');
COMMIT;

-- ADD ACCOUNT
START TRANSACTION;
INSERT INTO `account-micro`.`account`
(`id`, `first_name`, `last_name`, `patronymic`, `password`, `username`, `created_timestamp`,
 `is_credentials_non_expired`, `is_enabled`, `is_non_expired`,
 `is_non_locked`)
VALUES (1, 'Ostap', 'Komplikevych', 'Yaroslavovych', '$2a$12$/OMN12e9NG0CsYSYSd7a6.FY0pjPYCh9oL7TGlWcOd4gYKcT70.PK',
        'ostap.ja@gmail.com', '2021-08-15 16:52:00', 1, 1, 1, 1);
COMMIT;

-- ADD ROLES TO ACCOUNT
START TRANSACTION;
INSERT INTO `account-micro`.`account_has_role` (`account_id`, `role_id`)
VALUES (1, 1),
       (1, 2);
COMMIT;
