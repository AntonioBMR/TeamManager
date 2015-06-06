<?php
$idu = $_POST['ida'];
$idj = $_POST['idj'];
$ide = $_POST['ide'];
require_once 'funciones_bd.php';
$db = new funciones_BD();
//inserta en la tabla peticiones la peticin del jugador al administrador del equipo
    $result = mysql_query("INSERT INTO peticion(ida,idj,ide) VALUES('$idu', '$idj','$ide')");
if ($result)  {
    $resultado[]=array("peticion"=>"1");      
}else{
    $resultado[]=array("peticion"=>"0");
}
        echo json_encode($resultado);
?>