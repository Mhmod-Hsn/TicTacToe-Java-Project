-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 30, 2021 at 04:34 AM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 7.4.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tictactoedb`
--
CREATE DATABASE IF NOT EXISTS `tictactoedb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `tictactoedb`;

-- --------------------------------------------------------

--
-- Table structure for table `games`
--

CREATE TABLE `games` (
  `gid` bigint(20) UNSIGNED NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `turn` enum('X','O','') NOT NULL DEFAULT '',
  `cell0` enum('X','O','') NOT NULL DEFAULT '',
  `cell1` enum('X','O','') NOT NULL DEFAULT '',
  `cell2` enum('X','O','') NOT NULL DEFAULT '',
  `cell3` enum('X','O','') NOT NULL DEFAULT '',
  `cell4` enum('X','O','') NOT NULL DEFAULT '',
  `cell5` enum('X','O','') NOT NULL DEFAULT '',
  `cell6` enum('X','O','') NOT NULL DEFAULT '',
  `cell7` enum('X','O','') NOT NULL DEFAULT '',
  `cell8` enum('X','O','') NOT NULL DEFAULT '',
  `player1` bigint(20) UNSIGNED NOT NULL,
  `player2` bigint(20) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `players`
--

CREATE TABLE `players` (
  `pid` bigint(20) UNSIGNED NOT NULL,
  `username` longtext NOT NULL,
  `passwd` longtext NOT NULL,
  `email` longtext DEFAULT NULL,
  `status` enum('offline','online','busy','none') NOT NULL DEFAULT 'none',
  `score` bigint(20) UNSIGNED NOT NULL DEFAULT 0,
  `avatar` longblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `players`
--

INSERT INTO `players` (`pid`, `username`, `passwd`, `email`, `status`, `score`, `avatar`) VALUES
(1, 'user1', '123456789', NULL, 'offline', 30, NULL),
(2, 'user2', '123456789', NULL, 'offline', 300, NULL),
(3, 'user3', '123456789', NULL, 'offline', 0, NULL),
(5, 'userpass', '$2a$10$j2d3Vzab4uK.3uzqfABrgOeh9og.qwiKaUSUa.IBEhb/SGtoEB4v6', NULL, 'offline', 0, NULL),
(6, 'ahmed', '123456789', 'null', 'offline', 0, NULL),
(7, 'ali', '123456789', 'null', 'offline', 0, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `games`
--
ALTER TABLE `games`
  ADD PRIMARY KEY (`gid`),
  ADD UNIQUE KEY `gid` (`gid`),
  ADD KEY `player1` (`player1`),
  ADD KEY `player2` (`player2`);

--
-- Indexes for table `players`
--
ALTER TABLE `players`
  ADD PRIMARY KEY (`pid`),
  ADD UNIQUE KEY `pid` (`pid`),
  ADD UNIQUE KEY `username` (`username`) USING HASH;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `games`
--
ALTER TABLE `games`
  MODIFY `gid` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `players`
--
ALTER TABLE `players`
  MODIFY `pid` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `games`
--
ALTER TABLE `games`
  ADD CONSTRAINT `games_ibfk_1` FOREIGN KEY (`player1`) REFERENCES `players` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `games_ibfk_2` FOREIGN KEY (`player2`) REFERENCES `players` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
