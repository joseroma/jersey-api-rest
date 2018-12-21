-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `filmAffinity3` DEFAULT CHARACTER SET utf8 ;
USE `filmAffinity3` ;

-- -----------------------------------------------------
-- Table `mydb`.`PELICULA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `filmAffinity3`.`PELICULA` (
  `oid_pelicula` INT NOT NULL,
  `titulo` VARCHAR(100) NOT NULL,
  `img` VARCHAR(500) NOT NULL,
  `fecha_lanzamiento` DATE NOT NULL,
  `genero` VARCHAR(45) NOT NULL,
  `pais` VARCHAR(45) NOT NULL,
  `sinopsis` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`oid_pelicula`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ACTOR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `filmAffinity3`.`ACTOR` (
  `oid_ACTOR` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `apellido` VARCHAR(45) NOT NULL,
  `birth_date` DATE NOT NULL,
  PRIMARY KEY (`oid_ACTOR`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`CRITICA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `filmAffinity3`.`CRITICA` (
  `oid_critica` INT NOT NULL,
  `titulo_pelicula` VARCHAR(45) NOT NULL,
  `autor` VARCHAR(45) NOT NULL,
  `PELICULA_oid_pelicula` INT NOT NULL,
  `contenidoCritica` VARCHAR(2000) NULL,
  PRIMARY KEY (`oid_critica`, `PELICULA_oid_pelicula`),
  INDEX `fk_CRITICA_PELICULA_idx` (`PELICULA_oid_pelicula` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PELICULA_has_ACTOR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `filmAffinity3`.`PELICULA_has_ACTOR` (
  `PELICULA_oid_pelicula` INT NOT NULL,
  `ACTOR_oid_ACTOR` INT NOT NULL,
  PRIMARY KEY (`PELICULA_oid_pelicula`, `ACTOR_oid_ACTOR`),
  INDEX `fk_PELICULA_has_ACTOR_ACTOR1_idx` (`ACTOR_oid_ACTOR` ASC),
  INDEX `fk_PELICULA_has_ACTOR_PELICULA1_idx` (`PELICULA_oid_pelicula` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`DIRECTOR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `filmAffinity3`.`DIRECTOR` (
  `oid_director` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`oid_director`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PELICULA_has_DIRECTOR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `filmAffinity3`.`PELICULA_has_DIRECTOR` (
  `PELICULA_oid_pelicula` INT NOT NULL,
  `DIRECTOR_oid_director` INT NOT NULL,
  PRIMARY KEY (`PELICULA_oid_pelicula`, `DIRECTOR_oid_director`),
  INDEX `fk_PELICULA_has_DIRECTOR_DIRECTOR1_idx` (`DIRECTOR_oid_director` ASC),
  INDEX `fk_PELICULA_has_DIRECTOR_PELICULA1_idx` (`PELICULA_oid_pelicula` ASC))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
