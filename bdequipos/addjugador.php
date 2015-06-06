<?php

$nombre = $_POST['nombrej'];
$idu = $_POST['idusuario'];
$ide = $_POST['idequipo'];

require_once 'funciones_bd.php';
$db = new funciones_BD();
//agrega jugador si no devuelve error
if($db->addjugador($idu,$nombre,$ide))
{	
    $resultado[]=array("addjugador"=>"1");
}else{
    $resultado[]=array("addjugador"=>"2");
}		
echo json_encode($resultado);
?>