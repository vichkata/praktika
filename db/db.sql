-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema kursach
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema kursach
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `kursach` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `kursach` ;

-- -----------------------------------------------------
-- Table `kursach`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kursach`.`role` (
  `id_role` INT NOT NULL AUTO_INCREMENT,
  `name_role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_role`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `kursach`.`clients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kursach`.`clients` (
  `id_clients` INT NOT NULL AUTO_INCREMENT,
  `fio_clients` VARCHAR(45) NOT NULL,
  `login_clients` VARCHAR(45) NOT NULL,
  `password_clients` VARCHAR(45) NOT NULL,
  `email_clients` VARCHAR(45) NOT NULL,
  `role_idrole` INT NOT NULL,
  PRIMARY KEY (`id_clients`),
  INDEX `fk_idx` (`role_idrole` ASC) VISIBLE,
  CONSTRAINT `fk`
    FOREIGN KEY (`role_idrole`)
    REFERENCES `kursach`.`role` (`id_role`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `kursach`.`tour`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kursach`.`tour` (
  `id_tour` INT NOT NULL AUTO_INCREMENT,
  `name_tour` VARCHAR(45) NOT NULL,
  `description_tour` VARCHAR(100) NOT NULL,
  `price_tour` INT NOT NULL,
  `photo` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id_tour`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `kursach`.`booking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kursach`.`booking` (
  `id_booking` INT NOT NULL AUTO_INCREMENT,
  `id_clients` INT NOT NULL,
  `id_tour` INT NOT NULL,
  `count_people` INT NOT NULL,
  `final_price` INT NOT NULL,
  `start_date` DATE NOT NULL,
  PRIMARY KEY (`id_booking`),
  INDEX `fk_cl_idx` (`id_clients` ASC) VISIBLE,
  INDEX `fk_t_idx` (`id_tour` ASC) VISIBLE,
  CONSTRAINT `fk_cl`
    FOREIGN KEY (`id_clients`)
    REFERENCES `kursach`.`clients` (`id_clients`),
  CONSTRAINT `fk_t`
    FOREIGN KEY (`id_tour`)
    REFERENCES `kursach`.`tour` (`id_tour`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `kursach`.`country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kursach`.`country` (
  `id_country` INT NOT NULL AUTO_INCREMENT,
  `name_country` VARCHAR(45) NOT NULL,
  `climate_country` VARCHAR(45) NOT NULL,
  `proximity_sea` INT NOT NULL,
  PRIMARY KEY (`id_country`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `kursach`.`hotel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kursach`.`hotel` (
  `id_hotel` INT NOT NULL AUTO_INCREMENT,
  `name_hotel` VARCHAR(45) NOT NULL,
  `available_seats` INT NOT NULL,
  `climate_country` VARCHAR(45) NULL DEFAULT NULL,
  `proximity_sea` INT NULL DEFAULT NULL,
  `id_country` INT NOT NULL,
  PRIMARY KEY (`id_hotel`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `kursach` ;

-- -----------------------------------------------------
-- procedure AddCustomer
-- -----------------------------------------------------

DELIMITER $$
USE `kursach`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddCustomer`(
    IN p_username VARCHAR(255),
    IN p_email VARCHAR(255)
)
BEGIN
    DECLARE client_count INT;
    
    -- Проверяем наличие клиента с таким же именем пользователя или адресом электронной почты
    SELECT COUNT(*) INTO client_count
    FROM clients
    WHERE fio_clients = p_username OR email_clients = p_email;
    
    IF client_count > 0 THEN
        -- Если клиент уже существует, выводим сообщение об ошибке
        SELECT 'Клиент с таким же именем пользователя или адресом электронной почты уже существует.' AS Error;
    ELSE
        -- Если клиента нет, добавляем нового клиента
        INSERT INTO clients (fio_clients, email_clients)
        VALUES (p_username, p_email);
        
        SELECT 'Клиент успешно добавлен.' AS Success;
    END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure AddCustomer1
-- -----------------------------------------------------

DELIMITER $$
USE `kursach`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddCustomer1`(
    IN p_username VARCHAR(255),
    in p_login varchar(45),
    in p_password varchar(45),
    in p_role int,
    IN p_email VARCHAR(255)
)
BEGIN
    DECLARE client_count INT;
    
    
    -- Проверяем наличие клиента с таким же именем пользователя или адресом электронной почты
    SELECT COUNT(*) INTO client_count
    FROM clients
    WHERE fio_clients = p_username OR email_clients = p_email;
    
    IF client_count > 0 THEN
        -- Если клиент уже существует, выводим сообщение об ошибке
        SELECT 'Клиент с таким же именем пользователя или адресом электронной почты уже существует.' AS Error;
    ELSE
        -- Если клиента нет, добавляем нового клиента
        INSERT INTO clients (fio_clients, p_login, p_password, email_clients, p_role)
        VALUES (p_username, p_email);
        
        SELECT 'Клиент успешно добавлен.' AS Success;
    END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure AddCustomer2
-- -----------------------------------------------------

DELIMITER $$
USE `kursach`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddCustomer2`(
    IN p_username VARCHAR(255),
    in p_login varchar(45),
    in p_password varchar(45),
    in p_role int,
    IN p_email VARCHAR(255)
)
BEGIN
    DECLARE client_count INT;
    
    
    -- Проверяем наличие клиента с таким же именем пользователя или адресом электронной почты
    SELECT COUNT(*) INTO client_count
    FROM clients
    WHERE fio_clients = p_username OR email_clients = p_email;
    
    IF client_count > 0 THEN
        -- Если клиент уже существует, выводим сообщение об ошибке
        SELECT 'Клиент с таким же именем пользователя или адресом электронной почты уже существует.' AS Error;
    ELSE
        -- Если клиента нет, добавляем нового клиента
        INSERT INTO clients (fio_clients, login_clients, password_clients,email_clients,role_idrole)
        VALUES (p_username,p_login, p_password, email_clients, p_role);
        
        SELECT 'Клиент успешно добавлен.' AS Success;
    END IF;
END$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
