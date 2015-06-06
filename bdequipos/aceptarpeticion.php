<?php

$sino=$_POST['sino'];
$idu = $_POST['ida'];
$idj = $_POST['idj'];
$ide = $_POST['ide'];
require_once 'funciones_bd.php';
$db = new funciones_BD();

if(isset($_POST['sino'])){
    //segun aceptemos ono la peticion actualiza una u otra
    if($sino==1){
        $result = mysql_query("update peticion SET status='1' WHERE ida='$idu' and idj='$idj' and ide='$ide'");
        if ($result)  {
            //si acepta peticion crea nuevo jugador en ese equipo
            $result1=mysql_query("SELECT nombre FROM usuario WHERE idu='$idj'");
            if ($result1) {
                $row = mysql_fetch_array($result1);
                $nombre = $row['nombre'];
                $result2=mysql_query("INSERT INTO jugador(idu,nombrej,ide,adm) VALUES('$idj','$nombre', '$ide','1') ");     
                if ($result2)  {
                    $resultado[]=array("peticion"=>"1");      
                }else{
                    $resultado[]=array("peticion"=>"0");
                }
            }else{
                $resultado[]=array("peticion"=>"0");
            }
        }else{
            $resultado[]=array("peticion"=>"0");
        }
    }else if($sino==0){
        //si no acepta marca la peticion como rechazada
        $result = mysql_query("update peticion SET status='2' WHERE ida='$idu' and idj='$idj' and ide='$ide'");
        if ($result)  {
            $resultado[]=array("peticion"=>"1");      
        }
    }
    echo json_encode($resultado);
}
?>