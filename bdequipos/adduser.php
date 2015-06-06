<?php
$usuario = $_POST['usuario'];
$passw = $_POST['password'];
require_once 'funciones_bd.php';
$db = new funciones_BD();
//comprueba si hay usuario con ese nombre
if($db->isuserexist($usuario,$passw)){
    $resultado[]=array("useradd"=>"0");
}else{
    //si no existe ese usuario lo crea
    if($db->adduser($usuario,$passw))
    {	
        $resultado[]=array("useradd"=>"1");
    }else{
        $resultado[]=array("useradd"=>"2");
    }		
}
echo json_encode($resultado);
?>



