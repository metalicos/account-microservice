-- DROP SCHEMA IF EXISTS `account-micro`;
-- CREATE SCHEMA IF NOT EXISTS `account-micro` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `account-micro`;

-- DROP TABLE IF EXISTS `account-micro`.`account`;
CREATE TABLE IF NOT EXISTS `account-micro`.`account`
(
    `id`                         BIGINT       NOT NULL AUTO_INCREMENT,
    `username`                   VARCHAR(200) NOT NULL UNIQUE,
    `password`                   VARCHAR(200) NOT NULL,
    `is_credentials_non_expired` BIT(1)       NULL DEFAULT NULL,
    `is_enabled`                 BIT(1)       NULL DEFAULT NULL,
    `is_non_expired`             BIT(1)       NULL DEFAULT NULL,
    `is_non_locked`              BIT(1)       NULL DEFAULT NULL,
    `created_timestamp`          DATETIME(6)  NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_gex1lmaqpg0ir5g1f5eftyaa1` (`username` ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- DROP TABLE IF EXISTS `account-micro`.`account_detail`;
CREATE TABLE IF NOT EXISTS `account-micro`.`account_detail`
(
    `id`                BIGINT       NOT NULL AUTO_INCREMENT,
    `account_id`        BIGINT       NOT NULL,
    `first_name`        VARCHAR(25)  NOT NULL,
    `last_name`         VARCHAR(25)  NOT NULL,
    `patronymic`        VARCHAR(25)  NOT NULL,
    `gender`            VARCHAR(255) NULL DEFAULT NULL,
    `birth_date`        DATE         NULL DEFAULT NULL,
    `photo`             LONGBLOB     NULL DEFAULT NULL,
    `created_timestamp` DATETIME(6)  NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_rwnepdd7ryfamhfwa07vuj31f` (`account_id` ASC) VISIBLE,
    CONSTRAINT `FKgmcrvfpbrxux7hy3svk9oy0cx`
        FOREIGN KEY (`account_id`)
            REFERENCES `account-micro`.`account` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- DROP TABLE IF EXISTS `account-micro`.`account_role`;
CREATE TABLE IF NOT EXISTS `account-micro`.`account_role`
(
    `id`                BIGINT       NOT NULL AUTO_INCREMENT,
    `role`              VARCHAR(200) NOT NULL,
    `created_timestamp` DATETIME(6)  NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_opkdgh6cj6ia2uuk1c80xbylk` (`role` ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- DROP TABLE IF EXISTS `account-micro`.`account_has_role`;
CREATE TABLE IF NOT EXISTS `account-micro`.`account_has_role`
(
    `account_id` BIGINT NOT NULL,
    `role_id`    BIGINT NOT NULL,
    PRIMARY KEY (`account_id`, `role_id`),
    INDEX `FKnmt9nx6gnuket30mxe897v4ca` (`role_id` ASC) VISIBLE,
    CONSTRAINT `FKnmt9nx6gnuket30mxe897v4ca`
        FOREIGN KEY (`role_id`)
            REFERENCES `account-micro`.`account_role` (`id`),
    CONSTRAINT `FKtlr2y26fej3r0fy7h8f5x777h`
        FOREIGN KEY (`account_id`)
            REFERENCES `account-micro`.`account` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- ADD ROLES
START TRANSACTION;
INSERT INTO `account-micro`.`account_role` (`id`, `created_timestamp`, `role`)
VALUES (1, '2021-08-15 16:52:00', 'SUPER_ADMIN');
INSERT INTO `account-micro`.`account_role` (`id`, `created_timestamp`, `role`)
VALUES (2, '2021-08-15 16:52:00', 'ADMIN');
INSERT INTO `account-micro`.`account_role` (`id`, `created_timestamp`, `role`)
VALUES (3, '2021-08-15 16:52:00', 'MANAGER');
INSERT INTO `account-micro`.`account_role` (`id`, `created_timestamp`, `role`)
VALUES (4, '2021-08-15 16:52:00', 'USER');
INSERT INTO `account-micro`.`account_role` (`id`, `created_timestamp`, `role`)
VALUES (5, '2021-08-15 16:52:00', 'TEACHER');
INSERT INTO `account-micro`.`account_role` (`id`, `created_timestamp`, `role`)
VALUES (6, '2021-08-15 16:52:00', 'STUDENT');
COMMIT;

-- ADD ACCOUNT
START TRANSACTION;
INSERT INTO `account-micro`.`account` (`id`, `created_timestamp`, `is_credentials_non_expired`, `is_enabled`,
                                       `is_non_expired`, `is_non_locked`, `password`, `username`)
VALUES (1, '2021-08-15 16:52:00', 1, 1, 1, 1, 'password', 'ostap.ja@gmail.com');
COMMIT;

-- ADD ROLES TO ACCOUNT
START TRANSACTION;
INSERT INTO `account-micro`.`account_has_role` (`account_id`, `role_id`)
VALUES (1, 1);
INSERT INTO `account-micro`.`account_has_role` (`account_id`, `role_id`)
VALUES (1, 2);
COMMIT;
