<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<?php
?>
<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="script.js"></script>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
    <body>
<?php 

// Busca datos en las variables cookies y session 
if (!isset($_SESSION['user_id'])) {
    if (isset($_COOKIE['user_id']) && isset($_COOKIE['user_name'])) {
        $_SESSION['user_id'] = $_COOKIE['user_id'];
        $_SESSION['user_name'] = $_COOKIE['user_name'];
    }
}
//Nav y contenedor por defecto de la pagina
$idu=$_SESSION['user_id'];
require_once('nav.php');
?>
<div id="content">
   <div id="tab-container">
      <ul>
         <li><a href="equiposdisponibles.php">Buscar equipos</a></li>
         <li><a href="estadisticas.php">Ver estadisticas </a></li>
         <li><a href="crearequipo.php"s>Crear equipos</a></li>
         <li><a href="misequipos.php">Mis  equipos</a></li>
      </ul>
   </div>
   <div id="main-container">
   <div id="contenido" class="contenido">