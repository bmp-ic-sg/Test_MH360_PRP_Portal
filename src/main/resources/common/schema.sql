-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.3.12-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.1.0.6116
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for patient_registration
CREATE DATABASE IF NOT EXISTS `patient_registration` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `patient_registration`;

-- Dumping structure for table patient_registration.mobile_otp
CREATE TABLE IF NOT EXISTS `mobile_otp` (
  `mobileNo` varchar(50) NOT NULL,
  `otp` varchar(10) NOT NULL,
  `expiryDt` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`mobileNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `scheduled_tasks` (
  task_name varchar(40) not null,
  task_instance varchar(40) not null,
  task_data blob,
  execution_time timestamp(6) not null,
  picked BOOLEAN not null,
  picked_by varchar(50),
  last_success timestamp(6) null,
  last_failure timestamp(6) null,
  consecutive_failures INT,
  last_heartbeat timestamp(6) null,
  version BIGINT not null,
  PRIMARY KEY (task_name, task_instance)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table patient_registration.mobile_otp: ~2 rows (approximately)
/*!40000 ALTER TABLE `mobile_otp` DISABLE KEYS */;
/*!40000 ALTER TABLE `mobile_otp` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
