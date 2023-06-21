-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-06-2023 a las 17:57:58
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `belucky`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `puesto`
--

CREATE TABLE `puesto` (
  `id` int(11) NOT NULL,
  `uid_usuario` varchar(200) NOT NULL,
  `id_rifa` int(11) NOT NULL,
  `num_puesto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_spanish2_ci;

--
-- Volcado de datos para la tabla `puesto`
--

INSERT INTO `puesto` (`id`, `uid_usuario`, `id_rifa`, `num_puesto`) VALUES
(13, 'iEmSJ7zaZ5Z2OMX1RPrpKdn4yKH3', 21, 5),
(14, 'iEmSJ7zaZ5Z2OMX1RPrpKdn4yKH3', 21, 2),
(15, 'iEmSJ7zaZ5Z2OMX1RPrpKdn4yKH3', 21, 4),
(16, 'iEmSJ7zaZ5Z2OMX1RPrpKdn4yKH3', 21, 17),
(17, 'iEmSJ7zaZ5Z2OMX1RPrpKdn4yKH3', 21, 6),
(18, 'iEmSJ7zaZ5Z2OMX1RPrpKdn4yKH3', 20, 1),
(19, 'iEmSJ7zaZ5Z2OMX1RPrpKdn4yKH3', 20, 4),
(20, 'iEmSJ7zaZ5Z2OMX1RPrpKdn4yKH3', 21, 8),
(24, 'wfJfi0uTF4eVMI0rPFUkPXiy9Pm2', 20, 15),
(25, 'wfJfi0uTF4eVMI0rPFUkPXiy9Pm2', 21, 43);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rifa`
--

CREATE TABLE `rifa` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(200) NOT NULL,
  `premio` varchar(200) NOT NULL,
  `inicio` datetime NOT NULL,
  `fin` datetime NOT NULL,
  `puestos` int(11) NOT NULL,
  `valor_puesto` int(11) NOT NULL,
  `uid_usuario` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_spanish2_ci;

--
-- Volcado de datos para la tabla `rifa`
--

INSERT INTO `rifa` (`id`, `nombre`, `descripcion`, `premio`, `inicio`, `fin`, `puestos`, `valor_puesto`, `uid_usuario`) VALUES
(20, 'Prueba Rifa de Karelise', 'Karelise', '50000', '2023-06-10 03:16:00', '2023-06-25 03:16:00', 20, 2000, 'iEmSJ7zaZ5Z2OMX1RPrpKdn4yKH3'),
(21, 'Rifa de Jorge Marles', 'Descripcion', '100000', '2023-06-07 08:16:00', '2023-06-02 08:16:00', 100, 1999, 'wfJfi0uTF4eVMI0rPFUkPXiy9Pm2');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `uid` varchar(200) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `correo` varchar(200) NOT NULL,
  `registro` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_spanish2_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`uid`, `nombre`, `telefono`, `correo`, `registro`) VALUES
('iEmSJ7zaZ5Z2OMX1RPrpKdn4yKH3', 'Karen Quintero', '573124567897', 'wfvje@gmail.com', '2023-06-20 06:45:29'),
('wfJfi0uTF4eVMI0rPFUkPXiy9Pm2', 'Jorge Andres Marles', '573227398660', 'jamarlesf@gmail.com', '2023-06-19 07:41:36');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `puesto`
--
ALTER TABLE `puesto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `puesto_ibfk_1` (`id_rifa`),
  ADD KEY `correo_usuario` (`uid_usuario`);

--
-- Indices de la tabla `rifa`
--
ALTER TABLE `rifa`
  ADD PRIMARY KEY (`id`),
  ADD KEY `correo_usuario` (`uid_usuario`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`uid`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `puesto`
--
ALTER TABLE `puesto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT de la tabla `rifa`
--
ALTER TABLE `rifa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `puesto`
--
ALTER TABLE `puesto`
  ADD CONSTRAINT `puesto_ibfk_1` FOREIGN KEY (`id_rifa`) REFERENCES `rifa` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `puesto_ibfk_2` FOREIGN KEY (`uid_usuario`) REFERENCES `usuario` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `rifa`
--
ALTER TABLE `rifa`
  ADD CONSTRAINT `rifa_ibfk_1` FOREIGN KEY (`uid_usuario`) REFERENCES `usuario` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
