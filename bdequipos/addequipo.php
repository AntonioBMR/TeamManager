<?php

$nombre = $_POST['nombreequipo'];
$idu = $_POST['idusuario'];

require_once 'funciones_bd.php';
$db = new funciones_BD();
//añade equipo si no existe el nombre elegido
if($db->addequipo($nombre,$idu))
{		
    $resultado[]=array("addequipo"=>"1");
}else{
    $resultado[]=array("addequipo"=>"2");
}		
echo json_encode($resultado);
?>