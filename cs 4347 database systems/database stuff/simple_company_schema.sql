-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema simple_company
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema simple_company
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `simple_company` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `simple_company` ;

-- -----------------------------------------------------
-- Table `simple_company`.`CUSTOMER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`CUSTOMER` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `first_name` VARCHAR(45) NOT NULL COMMENT '',
  `last_name` VARCHAR(45) NOT NULL COMMENT '',
  `gender` CHAR(1) NOT NULL COMMENT '',
  `dob` DATE NOT NULL COMMENT '',
  `email` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)  COMMENT '',
  UNIQUE INDEX `email_UNIQUE` (`email` ASC)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `simple_company`.`ADDRESS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`ADDRESS` (
  `address1` VARCHAR(45) NOT NULL COMMENT '',
  `address2` VARCHAR(45) NULL COMMENT '',
  `city` VARCHAR(45) NOT NULL COMMENT '',
  `state` VARCHAR(45) NOT NULL COMMENT '',
  `zipcode` VARCHAR(45) NOT NULL COMMENT '',
  `CUSTOMER_id` INT NOT NULL COMMENT '',
  CONSTRAINT `fk_ADDRESS_CUSTOMER1`
    FOREIGN KEY (`CUSTOMER_id`)
    REFERENCES `simple_company`.`CUSTOMER` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `simple_company`.`CREDIT_CARD`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`CREDIT_CARD` (
  `name` VARCHAR(45) NOT NULL COMMENT '',
  `cc_number` VARCHAR(45) NOT NULL COMMENT '',
  `exp_date` VARCHAR(45) NOT NULL COMMENT '',
  `security_code` VARCHAR(45) NOT NULL COMMENT '',
  `CUSTOMER_id` INT NOT NULL COMMENT '',
  INDEX `fk_CREDIT_CARD_CUSTOMER1_idx` (`CUSTOMER_id` ASC)  COMMENT '',
  CONSTRAINT `fk_CREDIT_CARD_CUSTOMER1`
    FOREIGN KEY (`CUSTOMER_id`)
    REFERENCES `simple_company`.`CUSTOMER` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `simple_company`.`PRODUCT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`PRODUCT` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `prod_name` VARCHAR(45) NOT NULL COMMENT '',
  `prod_desc` VARCHAR(1024) NOT NULL COMMENT '',
  `prod_category` INT NOT NULL COMMENT '',
  `prod_upc` CHAR(12) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `simple_company`.`PURCHASE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`PURCHASE` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `purchase_date` TIMESTAMP NOT NULL COMMENT '',
  `purchase_amt` DECIMAL(9,2) NOT NULL COMMENT '',
  `PRODUCT_id` INT NOT NULL COMMENT '',
  `CUSTOMER_id` INT NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)  COMMENT '',
  INDEX `fk_PURCHASE_PRODUCT1_idx` (`PRODUCT_id` ASC)  COMMENT '',
  INDEX `fk_PURCHASE_CUSTOMER1_idx` (`CUSTOMER_id` ASC)  COMMENT '',
  CONSTRAINT `fk_PURCHASE_PRODUCT1`
    FOREIGN KEY (`PRODUCT_id`)
    REFERENCES `simple_company`.`PRODUCT` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PURCHASE_CUSTOMER1`
    FOREIGN KEY (`CUSTOMER_id`)
    REFERENCES `simple_company`.`CUSTOMER` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
