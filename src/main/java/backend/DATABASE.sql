-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Сен 30 2022 г., 10:19
-- Версия сервера: 10.4.24-MariaDB
-- Версия PHP: 7.4.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `hotel`
--

-- --------------------------------------------------------

--
-- Структура таблицы `admin`
--

CREATE TABLE `admin` (
  `id_admin` int(25) NOT NULL,
  `username` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Дамп данных таблицы `admin`
--

INSERT INTO `admin` (`id_admin`, `username`, `password`) VALUES
(126, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Структура таблицы `clientes`
--

CREATE TABLE `clientes` (
  `id_cliente` int(30) NOT NULL,
  `name_cliente` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `surname` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `DNI_cliente` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `nationality_cliente` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `phone_cliente` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `email_cliente` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `occupation_cliente` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `civilstate_cliente` varchar(20) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Дамп данных таблицы `clientes`
--

INSERT INTO `clientes` (`id_cliente`, `name_cliente`, `surname`, `DNI_cliente`, `nationality_cliente`, `phone_cliente`, `email_cliente`, `occupation_cliente`, `civilstate_cliente`) VALUES
(1, 'Juanito', 'Vazquez Velazquez', 'E06362696', 'Hungria', '52363673', 'asgaehe@sdsdgsd.sdfg', 'Vacante libre', 'Soltero');

-- --------------------------------------------------------

--
-- Структура таблицы `contacto`
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
-- Структура таблицы `habitacion`
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
-- Дамп данных таблицы `habitacion`
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
-- Структура таблицы `recepcionista`
--

CREATE TABLE `recepcionista` (
  `id_recepcionista` int(20) NOT NULL,
  `name_recepcionisa` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `surname_recepcionista` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `DNI_recepcionista` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `nationality_recepcionista` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `phone_recepcionista` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `email_recepcionista` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Дамп данных таблицы `recepcionista`
--

INSERT INTO `recepcionista` (`id_recepcionista`, `name_recepcionisa`, `surname_recepcionista`, `DNI_recepcionista`, `nationality_recepcionista`, `phone_recepcionista`, `email_recepcionista`, `username`, `password`) VALUES
(1, 'juan', 'garcía Melendez', '06362696E', 'Ecuador', '+34644674246', 'dfgshdrh@hrsh.weg', '1', '1'),
(2, '', '', '111', '', '', '', '123', '1111'),
(3, '', '', '2', '', '', '', '2', '2'),
(4, '', '', '3', '', '', '', '3', '3'),
(5, '', '', 'mursalin', '', '', '', 'Md. Mursalin', 'mursalin'),
(6, '', '', 'rakib', '', '', '', 'rakib', 'rakib');

-- --------------------------------------------------------

--
-- Структура таблицы `reserva`
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
-- Дамп данных таблицы `reserva`
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
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_admin`);

--
-- Индексы таблицы `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id_cliente`),
  ADD UNIQUE KEY `DNI_cliente` (`DNI_cliente`) USING BTREE;

--
-- Индексы таблицы `contacto`
--
ALTER TABLE `contacto`
  ADD UNIQUE KEY `DNI` (`DNI`) USING BTREE;

--
-- Индексы таблицы `habitacion`
--
ALTER TABLE `habitacion`
  ADD PRIMARY KEY (`id_habitacion`),
  ADD KEY `id_reserva` (`id_reserva`);

--
-- Индексы таблицы `recepcionista`
--
ALTER TABLE `recepcionista`
  ADD PRIMARY KEY (`id_recepcionista`),
  ADD UNIQUE KEY `DNI_recepcionista_2` (`DNI_recepcionista`),
  ADD UNIQUE KEY `DNI_recepcionista_3` (`DNI_recepcionista`),
  ADD UNIQUE KEY `DNI_recepcionista_4` (`DNI_recepcionista`,`username`),
  ADD KEY `DNI_recepcionista` (`DNI_recepcionista`) USING BTREE;

--
-- Индексы таблицы `reserva`
--
ALTER TABLE `reserva`
  ADD PRIMARY KEY (`id_reserva`),
  ADD UNIQUE KEY `id_reserva` (`id_reserva`,`dni_cliente`,`id_recepcionista`),
  ADD UNIQUE KEY `id_reserva_2` (`id_reserva`,`dni_cliente`,`id_recepcionista`),
  ADD KEY `id_habitacion` (`id_habitacion`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `admin`
--
ALTER TABLE `admin`
  MODIFY `id_admin` int(25) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=127;

--
-- AUTO_INCREMENT для таблицы `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id_cliente` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT для таблицы `habitacion`
--
ALTER TABLE `habitacion`
  MODIFY `id_habitacion` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1112;

--
-- AUTO_INCREMENT для таблицы `recepcionista`
--
ALTER TABLE `recepcionista`
  MODIFY `id_recepcionista` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT для таблицы `reserva`
--
ALTER TABLE `reserva`
  MODIFY `id_reserva` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
