-- MySQL Script generated by MySQL Workbench
-- Wed Feb 24 08:32:48 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema repair_agency
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `repair_agency` ;

-- -----------------------------------------------------
-- Schema repair_agency
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `repair_agency` ;
USE `repair_agency` ;

-- -----------------------------------------------------
-- Table `repair_agency`.`employee`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `repair_agency`.`employee` ;

CREATE TABLE IF NOT EXISTS `repair_agency`.`employee` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `email` VARCHAR(20) NULL DEFAULT NULL,
  `password` VARCHAR(15) NULL DEFAULT NULL,
  `role` ENUM('MANAGER', 'MASTER') NULL,
  `phone` VARCHAR(25) NOT NULL,
  `salary` DECIMAL(10,2) NULL,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `usercol_UNIQUE` (`phone` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `repair_agency`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `repair_agency`.`customer` ;

CREATE TABLE IF NOT EXISTS `repair_agency`.`customer` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(20) NULL DEFAULT NULL,
  `password` VARCHAR(15) NULL DEFAULT NULL,
  `phone` VARCHAR(25) NOT NULL,
  `balance` DECIMAL(10,2) NULL DEFAULT 0,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `usercol_UNIQUE` (`phone` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `repair_agency`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `repair_agency`.`orders` ;

CREATE TABLE IF NOT EXISTS `repair_agency`.`orders` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `customer_id` INT UNSIGNED NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `price` DOUBLE NULL DEFAULT NULL,
  `description` VARCHAR(500) NULL DEFAULT NULL,
  `managment_state` ENUM('NEW', 'WAIT_FOR_PAYMENT', 'PAYED', 'CANCELED') NOT NULL DEFAULT 'NEW',
  `work_state` ENUM('NEW', 'IN_WORK', 'FINISHED') NOT NULL DEFAULT 'NEW',
  `feedback` VARCHAR(500) NULL,
  PRIMARY KEY (`id`, `customer_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_user_orders_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `fk_customer_orders`
    FOREIGN KEY (`customer_id`)
    REFERENCES `repair_agency`.`customer` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `repair_agency`.`order_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `repair_agency`.`order_master` ;

CREATE TABLE IF NOT EXISTS `repair_agency`.`order_master` (
  `master_id` INT UNSIGNED NOT NULL,
  `order_id` INT UNSIGNED NOT NULL,
  INDEX `fk_order_id_idx` (`order_id` ASC) VISIBLE,
  INDEX `index` (`master_id` ASC, `order_id` ASC) INVISIBLE,
  PRIMARY KEY (`master_id`, `order_id`),
  CONSTRAINT `fk_master_id`
    FOREIGN KEY (`master_id`)
    REFERENCES `repair_agency`.`employee` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `repair_agency`.`orders` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
