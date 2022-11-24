-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 24, 2022 at 01:22 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotel`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id_admin` int(25) NOT NULL,
  `username` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id_admin`, `username`, `password`) VALUES
(126, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `clientes`
--

CREATE TABLE `clientes` (
  `id_cliente` int(30) NOT NULL,
  `name_cliente` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `surname_cliente` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `DNI_cliente` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `nationality_cliente` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `phone_cliente` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `email_cliente` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `occupation_cliente` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `civilstate_cliente` varchar(20) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `clientes`
--

INSERT INTO `clientes` (`id_cliente`, `name_cliente`, `surname_cliente`, `DNI_cliente`, `nationality_cliente`, `phone_cliente`, `email_cliente`, `occupation_cliente`, `civilstate_cliente`) VALUES
(1, 'Juanito', 'Vazquez Velazquez', 'E06362696', 'Hungria', '52363673', 'asgaehe@sdsdgsd.sdfg', 'Vacante libre', 'Soltero'),
(7, 'Manolito', 'Largas', '78396742E', 'Hungría', '+34 764 84 29 84', 'humaoig@jomaue.com', 'Vendedor ambulante de hurones.', '3 hijos'),
(9, 'Alvaro', 'Diaz', '3456345646E', 'Bulgaria', '76798324', 'ahaeh@asfsd.sgd', 'Empleado del estado', 'Casado');

-- --------------------------------------------------------

--
-- Table structure for table `habitacion`
--

CREATE TABLE `habitacion` (
  `id_habitacion` int(15) NOT NULL,
  `num_habitacion` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `planta` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tipo` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `precio` int(10) NOT NULL,
  `caracteristicas` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `habitacion`
--

INSERT INTO `habitacion` (`id_habitacion`, `num_habitacion`, `planta`, `tipo`, `precio`, `caracteristicas`) VALUES
(1, '3', '1', 'Familiar', 0, 'Tiene manchas en la alfombra.'),
(1116, '5', '4', 'Individual', 20, 'Sin características.'),
(1118, '6', '2', 'Individual', 30, 'Nova.');

-- --------------------------------------------------------

--
-- Table structure for table `recepcionista`
--

CREATE TABLE `recepcionista` (
  `id_recepcionista` int(20) NOT NULL,
  `name_recepcionista` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `surname_recepcionista` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `DNI_recepcionista` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `nationality_recepcionista` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `phone_recepcionista` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `email_recepcionista` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `active_recepcionista` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `recepcionista`
--

INSERT INTO `recepcionista` (`id_recepcionista`, `name_recepcionista`, `surname_recepcionista`, `DNI_recepcionista`, `nationality_recepcionista`, `phone_recepcionista`, `email_recepcionista`, `username`, `password`, `active_recepcionista`) VALUES
(1, 'juan', 'garcía Melendez', '06362696E', 'Ecuador', '+34644674246', 'dfgshdrh@hrsh.weg', '1', '1', 1),
(8, 'Pepe', 'Grillo Marasd', '86934529A', 'Esoala', '+35261623644', 'asfaheh@afadsgf.asd', '2', '2', 1),
(11, 'asdasd', 'asdasd', 'asdasd', 'asdasd', 'asdasd', 'asdasd', 'asdasd', 'asdasd', 1);

-- --------------------------------------------------------

--
-- Table structure for table `reserva`
--

CREATE TABLE `reserva` (
  `id_reserva` int(15) NOT NULL,
  `id_cliente` int(30) DEFAULT NULL,
  `id_recepcionista` int(30) DEFAULT NULL,
  `id_habitacion` int(30) NOT NULL,
  `fecha_inicio` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fecha_final` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `costo` int(10) NOT NULL,
  `estado` varchar(10) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `reserva`
--

INSERT INTO `reserva` (`id_reserva`, `id_cliente`, `id_recepcionista`, `id_habitacion`, `fecha_inicio`, `fecha_final`, `costo`, `estado`) VALUES
(42, 9, 1, 1, '2022-10-21', '2022-12-06', 260, 'Checked in'),
(43, 7, 1, 1, '2022-10-22', '2022-10-27', 1018, 'Checked in'),
(47, 1, 11, 1, '2022-11-24', '2022-11-25', 123, 'Checked in');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_admin`);

--
-- Indexes for table `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id_cliente`),
  ADD UNIQUE KEY `DNI_cliente` (`DNI_cliente`) USING BTREE;

--
-- Indexes for table `habitacion`
--
ALTER TABLE `habitacion`
  ADD PRIMARY KEY (`id_habitacion`);

--
-- Indexes for table `recepcionista`
--
ALTER TABLE `recepcionista`
  ADD PRIMARY KEY (`id_recepcionista`),
  ADD UNIQUE KEY `DNI_recepcionista_2` (`DNI_recepcionista`),
  ADD UNIQUE KEY `DNI_recepcionista_3` (`DNI_recepcionista`),
  ADD UNIQUE KEY `DNI_recepcionista_4` (`DNI_recepcionista`,`username`),
  ADD KEY `DNI_recepcionista` (`DNI_recepcionista`) USING BTREE;

--
-- Indexes for table `reserva`
--
ALTER TABLE `reserva`
  ADD PRIMARY KEY (`id_reserva`),
  ADD UNIQUE KEY `id_reserva` (`id_reserva`,`id_cliente`,`id_recepcionista`),
  ADD UNIQUE KEY `id_reserva_2` (`id_reserva`,`id_cliente`,`id_recepcionista`),
  ADD KEY `id_habitacion` (`id_habitacion`),
  ADD KEY `id_recepcionista` (`id_recepcionista`),
  ADD KEY `id_cliente` (`id_cliente`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id_admin` int(25) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=127;

--
-- AUTO_INCREMENT for table `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id_cliente` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `habitacion`
--
ALTER TABLE `habitacion`
  MODIFY `id_habitacion` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1119;

--
-- AUTO_INCREMENT for table `recepcionista`
--
ALTER TABLE `recepcionista`
  MODIFY `id_recepcionista` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `reserva`
--
ALTER TABLE `reserva`
  MODIFY `id_reserva` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `reserva`
--
ALTER TABLE `reserva`
  ADD CONSTRAINT `reserva_ibfk_1` FOREIGN KEY (`id_recepcionista`) REFERENCES `recepcionista` (`id_recepcionista`),
  ADD CONSTRAINT `reserva_ibfk_2` FOREIGN KEY (`id_habitacion`) REFERENCES `habitacion` (`id_habitacion`),
  ADD CONSTRAINT `reserva_ibfk_3` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_cliente`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
