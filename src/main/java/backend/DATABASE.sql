-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 28, 2022 at 09:54 AM
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
  `DNI_cliente` varchar(40) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `clientes`
--

INSERT INTO `clientes` (`id_cliente`, `DNI_cliente`) VALUES
(1, '1'),
(4, '4'),
(5, 'a'),
(6, 'Dhaka, Bangladesh');

-- --------------------------------------------------------

--
-- Table structure for table `contacto`
--

CREATE TABLE `contacto` (
  `DNI` varchar(30) NOT NULL,
  `nacionalidad` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `telefono` int(15) DEFAULT NULL,
  `nombre` varchar(30) DEFAULT NULL,
  `apellidos` varchar(30) DEFAULT NULL,
  `estado_civil` varchar(30) DEFAULT NULL,
  `ocupacion` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `habitacion`
--

CREATE TABLE `habitacion` (
  `id_habitacion` int(15) NOT NULL,
  `id_reserva` int(11) NOT NULL,
  `num_habitacion` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `planta` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `disponibilidad` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tipo` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `caracteristicas` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `habitacion`
--

INSERT INTO `habitacion` (`id_habitacion`, `id_reserva`, `num_habitacion`, `planta`, `disponibilidad`, `tipo`, `caracteristicas`) VALUES
(1, 0, 'AC', 'Single', '1500', 'Booked', ''),
(2, 0, 'AC-Room', 'Double', '2000', 'Available', ''),
(3, 0, 'AC', 'Double', '600', 'Available', ''),
(9, 0, '9', '9', '9', 'Available', ''),
(11, 0, 'Non-Ac', 'Double', '500', 'Booked', ''),
(12, 0, '12', '12', '12', 'Booked', ''),
(13, 0, 'Ac', '12', '12', 'Available', ''),
(111, 0, 'AC', 'Double', '1000', 'Booked', ''),
(123, 0, '1222', '222', '222', 'Booked', ''),
(1111, 0, 'dc', '2', '23', 'Available', '');

-- --------------------------------------------------------

--
-- Table structure for table `recepcionista`
--

CREATE TABLE `recepcionista` (
  `id_recepcionista` int(20) NOT NULL,
  `DNI_recepcionista` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `usuario` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `contraseña` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `recepcionista`
--

INSERT INTO `recepcionista` (`id_recepcionista`, `DNI_recepcionista`, `usuario`, `contraseña`) VALUES
(1, '1', '1', '1'),
(2, '111', '123', '1111'),
(3, '2', '2', '2'),
(4, '3', '3', '3'),
(5, 'mursalin', 'Md. Mursalin', 'mursalin'),
(6, 'rakib', 'rakib', 'rakib');

-- --------------------------------------------------------

--
-- Table structure for table `reserva`
--

CREATE TABLE `reserva` (
  `id_reserva` int(15) NOT NULL,
  `dni_cliente` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_recepcionista` int(30) DEFAULT NULL,
  `id_habitacion` int(30) NOT NULL,
  `fecha_inicio` int(30) DEFAULT NULL,
  `fecha_final` int(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `reserva`
--

INSERT INTO `reserva` (`id_reserva`, `dni_cliente`, `id_recepcionista`, `id_habitacion`, `fecha_inicio`, `fecha_final`) VALUES
(16, '3', 3, 0, 3, 3),
(17, '2', 2, 0, 2, 2),
(18, '4', 4, 0, 4, 4),
(19, '8', 8, 0, 8, 8),
(20, '3', 3, 0, 3, 3),
(21, '2', 3, 0, 2, 2),
(22, '2', 3, 0, 2, 2),
(23, '2', 3, 0, 2, 2),
(24, '23', 3, 0, 2, 2),
(25, 'Md. Mursalin', 0, 0, 0, 0),
(26, 'Md. Mursalin', 0, 0, 0, 0),
(27, 'mursalin', 0, 0, 0, 0),
(28, 'mursalin', 0, 0, 0, 0),
(29, '1', 1, 0, 1, 1),
(30, 'mursalin', 0, 0, 0, 0),
(31, '1', 1, 0, 1, 1),
(32, '4', 4, 0, 4, 4),
(33, 'mursalin', 0, 0, 0, 0),
(34, '1', 1, 0, 1, 1),
(35, 'mursalin', 0, 0, 0, 0),
(36, 'mursalin', 0, 0, 0, 0),
(37, 'mursalin', 0, 0, 0, 0),
(38, 'mursalin', 0, 0, 0, 0),
(39, '1', 1, 0, 1, 1),
(40, 'mursalin', 0, 0, 0, 0);

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
-- Indexes for table `contacto`
--
ALTER TABLE `contacto`
  ADD UNIQUE KEY `DNI` (`DNI`) USING BTREE;

--
-- Indexes for table `habitacion`
--
ALTER TABLE `habitacion`
  ADD PRIMARY KEY (`id_habitacion`),
  ADD KEY `id_reserva` (`id_reserva`);

--
-- Indexes for table `recepcionista`
--
ALTER TABLE `recepcionista`
  ADD PRIMARY KEY (`id_recepcionista`),
  ADD UNIQUE KEY `DNI_recepcionista_2` (`DNI_recepcionista`),
  ADD UNIQUE KEY `DNI_recepcionista_3` (`DNI_recepcionista`),
  ADD KEY `DNI_recepcionista` (`DNI_recepcionista`) USING BTREE;

--
-- Indexes for table `reserva`
--
ALTER TABLE `reserva`
  ADD PRIMARY KEY (`id_reserva`),
  ADD UNIQUE KEY `id_reserva` (`id_reserva`,`dni_cliente`,`id_recepcionista`),
  ADD UNIQUE KEY `id_reserva_2` (`id_reserva`,`dni_cliente`,`id_recepcionista`),
  ADD KEY `id_habitacion` (`id_habitacion`);

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
  MODIFY `id_cliente` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `habitacion`
--
ALTER TABLE `habitacion`
  MODIFY `id_habitacion` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1112;

--
-- AUTO_INCREMENT for table `recepcionista`
--
ALTER TABLE `recepcionista`
  MODIFY `id_recepcionista` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `reserva`
--
ALTER TABLE `reserva`
  MODIFY `id_reserva` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
