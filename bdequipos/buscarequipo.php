<?php
$idu = $_POST['idusuario'];
require_once 'funciones_bd.php';
$db = new funciones_BD();
//busca los equipos donde juege el user y guarda los ide
$result=mysql_query("SELECT ide from jugador where idu = '$idu' "); 
$jides=array();
 while ($row = mysql_fetch_array($result)) {
         $idej = $row[0];
     array_push($jides,$idej);
}
//busca las peticiones que haga el user y guarda las ide del equipo
$result1=mysql_query("SELECT ide from peticion where idj = '$idu' "); 
$pides=array();
 while ($row = mysql_fetch_array($result1)) {
         $idep = $row[0];
         array_push($pides,$idep);
}
$r1=array();
$r2=array();
//compone las sentencias con los arrays de ides y filtra si tiene tamaño =0 a 1 o mas
if(count($jides)>1){
    $r1=implode("' and ide != '",$jides);
}else{
    $r1= $jides[0];    
}
if(count($pides)>1){
    $r2=implode("' AND ide != '",$pides);
}else{
    $r2= $pides[0];
}
//muestra los equipos disponibles que los cuales son distintos a las ides recogidas antes
$resultado=mysql_query("SELECT * FROM equipo WHERE ide != '$r1' and ide != '$r2' "); 
if($resultado){
    while ($row = mysql_fetch_array($resultado)) {
        $ide = $row['ide'];
        $ne = $row['nombree'];
        $iduser = $row['idusuario'];
        $resultadof[]=array("ide"=>"$ide","nombree"=>"$ne","idusuario"=>"$iduser");      
    }
    echo json_encode($resultadof);
}else{
     echo json_encode(array("vacio"=>"0"));
}
?>