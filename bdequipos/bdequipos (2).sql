-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 11-05-2015 a las 01:43:29
-- Versión del servidor: 5.6.20
-- Versión de PHP: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `bdequipos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipo`
--

CREATE TABLE IF NOT EXISTS `equipo` (
`ide` int(10) unsigned NOT NULL,
  `nombree` varchar(25) COLLATE latin1_spanish_ci NOT NULL,
  `idusuario` int(11) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci AUTO_INCREMENT=22 ;

--
-- Volcado de datos para la tabla `equipo`
--

INSERT INTO `equipo` (`ide`, `nombree`, `idusuario`) VALUES
(1, 'bobbbs', 1),
(3, 'bili', 1),
(5, 'antonio', 2),
(6, 'losbos', 4),
(10, 'biliyea', 1),
(11, 'buubu', 1),
(12, 'pullmantur', 3),
(13, 'popi', 1),
(17, 'iujj', 1),
(18, 'oo', 1),
(19, 'bob', 1),
(20, 'kniu', 1),
(21, 'n look m', 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jugador`
--

CREATE TABLE IF NOT EXISTS `jugador` (
`idj` int(10) unsigned NOT NULL,
  `idu` int(11) NOT NULL,
  `nombrej` varchar(25) COLLATE latin1_spanish_ci NOT NULL,
  `ide` int(11) NOT NULL,
  `adm` tinyint(1) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci AUTO_INCREMENT=48 ;

--
-- Volcado de datos para la tabla `jugador`
--

INSERT INTO `jugador` (`idj`, `idu`, `nombrej`, `ide`, `adm`) VALUES
(1, 1, 'p', 1, 0),
(2, 2, 'antonio', 5, 0),
(4, 1, 'p', 3, 0),
(17, 4, 'pe', 6, 0),
(21, 1, 'p', 10, 0),
(22, 1, 'p', 11, 0),
(23, 3, 'a', 12, 0),
(28, 2, 'antonio', 1, 1),
(29, 1, 'p', 12, 1),
(30, 8, 'qq', 1, 1),
(31, 8, 'qq', 12, 1),
(32, 3, 'a', 1, 1),
(33, 5, 'pepito', 1, 1),
(34, 6, 'peter', 1, 1),
(35, 3, 'a', 10, 1),
(36, 3, 'a', 11, 1),
(37, 5, 'pepito', 11, 1),
(38, 6, 'peter', 12, 1),
(39, 1, 'p', 13, 0),
(40, 1, 'p', 14, 0),
(41, 1, 'p', 15, 0),
(42, 1, 'p', 16, 0),
(43, 1, 'p', 17, 0),
(44, 1, 'p', 18, 0),
(45, 1, 'p', 19, 0),
(46, 1, 'p', 20, 0),
(47, 10, 'bobb', 21, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partido`
--

CREATE TABLE IF NOT EXISTS `partido` (
`id` int(11) NOT NULL,
  `fecha` varchar(9) COLLATE latin1_spanish_ci NOT NULL,
  `ide` int(11) NOT NULL,
  `idu` int(11) NOT NULL,
  `equipo` int(11) NOT NULL,
  `valoracion` int(11) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci AUTO_INCREMENT=124 ;

--
-- Volcado de datos para la tabla `partido`
--

INSERT INTO `partido` (`id`, `fecha`, `ide`, `idu`, `equipo`, `valoracion`) VALUES
(5, '2015-18', 10, 1, 2, 0),
(6, '2015-18', 11, 1, 2, 0),
(7, '2015-18', 3, 1, 2, 0),
(8, '2015-18', 12, 8, 2, 1),
(9, '2015-18', 12, 3, 1, 0),
(10, '2015-18', 12, 1, 2, 9),
(83, '2015-18', 1, 2, 2, 7),
(84, '2015-18', 1, 8, 1, 5),
(85, '2015-18', 1, 3, 2, 4),
(86, '2015-18', 1, 5, 1, 0),
(87, '2015-18', 1, 6, 2, 3),
(88, '2015-18', 1, 1, 1, 10),
(101, '2015-19', 1, 2, 2, 0),
(102, '2015-19', 1, 8, 1, 0),
(103, '2015-19', 1, 3, 2, 0),
(104, '2015-19', 1, 6, 1, 0),
(105, '2015-19', 1, 1, 2, 0),
(106, '2015-19', 1, 5, 1, 0),
(107, '2015-19', 10, 3, 2, 0),
(108, '2015-19', 10, 1, 1, 0),
(109, '2015-19', 11, 1, 2, 0),
(110, '2015-19', 11, 3, 1, 0),
(111, '2015-19', 11, 5, 2, 0),
(112, '2015-19', 12, 6, 2, 0),
(114, '2015-19', 12, 1, 2, 0),
(115, '2015-19', 12, 8, 1, 0),
(116, '2015-19', 3, 1, 2, 0),
(117, '2015-19', 13, 1, 2, 0),
(118, '2015-19', 14, 1, 2, 0),
(119, '2015-19', 17, 1, 2, 0),
(120, '2015-19', 18, 1, 2, 0),
(121, '2015-19', 19, 1, 2, 0),
(122, '2015-19', 20, 1, 2, 0),
(123, '2015-19', 21, 10, 2, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `peticion`
--

CREATE TABLE IF NOT EXISTS `peticion` (
`idp` int(10) unsigned NOT NULL,
  `ida` int(10) unsigned NOT NULL,
  `idj` int(10) unsigned NOT NULL,
  `ide` int(10) unsigned NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci AUTO_INCREMENT=24 ;

--
-- Volcado de datos para la tabla `peticion`
--

INSERT INTO `peticion` (`idp`, `ida`, `idj`, `ide`, `status`) VALUES
(7, 2, 1, 5, 0),
(8, 4, 1, 6, 0),
(10, 1, 2, 1, 1),
(11, 1, 4, 1, 2),
(12, 3, 1, 12, 1),
(13, 1, 8, 1, 1),
(14, 2, 8, 5, 0),
(15, 3, 8, 12, 1),
(16, 1, 3, 1, 1),
(17, 1, 3, 11, 1),
(18, 1, 3, 10, 1),
(19, 1, 5, 1, 1),
(20, 1, 5, 11, 1),
(21, 1, 6, 1, 1),
(22, 3, 6, 12, 1),
(23, 2, 6, 5, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
`idu` int(10) unsigned NOT NULL,
  `nombre` varchar(25) COLLATE latin1_spanish_ci NOT NULL,
  `passw` varchar(25) COLLATE latin1_spanish_ci NOT NULL,
  `valoracion` int(2) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci AUTO_INCREMENT=11 ;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idu`, `nombre`, `passw`, `valoracion`) VALUES
(1, 'p', 'p', 3),
(2, 'antonio', 'antonio', 7),
(3, 'a', 'a', 2),
(4, 'pe', 'pe', 0),
(5, 'pepito', 'pepito', 0),
(6, 'peter', 'peter', 3),
(7, 'q', 'q', 0),
(8, 'qq', 'qq', 3),
(9, 'efffff', 't', 0),
(10, 'bobb', 'bob', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `voto`
--

CREATE TABLE IF NOT EXISTS `voto` (
`idv` int(11) NOT NULL,
  `idu` int(11) NOT NULL,
  `iduv` int(11) NOT NULL,
  `ide` int(11) NOT NULL,
  `val` int(2) NOT NULL,
  `fecha` varchar(9) COLLATE latin1_spanish_ci NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci AUTO_INCREMENT=51 ;

--
-- Volcado de datos para la tabla `voto`
--

INSERT INTO `voto` (`idv`, `idu`, `iduv`, `ide`, `val`, `fecha`) VALUES
(28, 8, 1, 1, 5, '2015-18'),
(29, 2, 1, 1, 5, '2015-18'),
(30, 8, 3, 12, 1, '2015-18'),
(31, 1, 3, 12, 9, '2015-18'),
(32, 6, 1, 1, 1, '2015-18'),
(33, 5, 1, 1, 0, '2015-18'),
(34, 3, 1, 1, 6, '2015-18'),
(35, 2, 5, 1, 9, '2015-18'),
(36, 3, 5, 1, 3, '2015-18'),
(37, 6, 5, 1, 5, '2015-18'),
(38, 1, 5, 1, 10, '2015-18'),
(39, 2, 1, 1, 0, '2015-19'),
(40, 8, 1, 1, 2, '2015-19'),
(41, 3, 1, 1, 3, '2015-19'),
(42, 5, 1, 1, 6, '2015-19'),
(43, 6, 1, 1, 5, '2015-19'),
(44, 3, 1, 10, 7, '2015-19'),
(45, 3, 1, 12, 7, '2015-19'),
(46, 8, 1, 12, 7, '2015-19'),
(47, 6, 1, 12, 7, '2015-19'),
(48, 5, 1, 11, 3, '2015-19'),
(49, 3, 1, 11, 4, '2015-19'),
(50, 0, 0, 0, 0, '2015-19');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `equipo`
--
ALTER TABLE `equipo`
 ADD PRIMARY KEY (`ide`);

--
-- Indices de la tabla `jugador`
--
ALTER TABLE `jugador`
 ADD PRIMARY KEY (`idj`);

--
-- Indices de la tabla `partido`
--
ALTER TABLE `partido`
 ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `peticion`
--
ALTER TABLE `peticion`
 ADD PRIMARY KEY (`idp`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
 ADD PRIMARY KEY (`idu`);

--
-- Indices de la tabla `voto`
--
ALTER TABLE `voto`
 ADD PRIMARY KEY (`idv`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `equipo`
--
ALTER TABLE `equipo`
MODIFY `ide` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT de la tabla `jugador`
--
ALTER TABLE `jugador`
MODIFY `idj` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=48;
--
-- AUTO_INCREMENT de la tabla `partido`
--
ALTER TABLE `partido`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=124;
--
-- AUTO_INCREMENT de la tabla `peticion`
--
ALTER TABLE `peticion`
MODIFY `idp` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=24;
--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
MODIFY `idu` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT de la tabla `voto`
--
ALTER TABLE `voto`
MODIFY `idv` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=51;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
