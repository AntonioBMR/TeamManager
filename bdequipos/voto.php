<?php
$idjv = $_POST['iduv'];
$idj = $_POST['idu'];
$ide = $_POST['ide'];
$val = $_POST['val'];
require_once 'funciones_bd.php';
$db = new funciones_BD();
$semana_actual = date("Y-W");
//consulta si se ha realizado voto semanal devuelve 2 si si se ha hecho
  $result1=mysql_query("SELECT * FROM voto WHERE idu='$idj' AND iduv='$idjv' AND ide='$ide' AND fecha='$semana_actual' ");
if (mysql_num_rows($result1) >= 1) {
    $resultado[]=array("voto"=>"2");
}else{
    //inserta valoracion si no se ha hecho devuelve 1 si el voto se realiza 2 si no
    $result2=mysql_query("INSERT INTO voto(idu,iduv,ide,val,fecha) VALUES('$idj','$idjv', '$ide','$val','$semana_actual') "); 
    if($result2){
         $resultado[]=array("voto"=>"1");
    }else{
         $resultado[]=array("voto"=>"0");
    }
}
    echo json_encode($resultado);
?>