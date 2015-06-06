<?php
$idu = $_POST['idusuario'];
require_once 'funciones_bd.php';
$db = new funciones_BD();
$ides=array();
$query=mysql_query("SELECT * from usuario where idu='$idu' ");
 $lista[]=array();
if($query){
    //recoge ides de equipos donde  el usuario sea administrador
	$query1=mysql_query("SELECT * from equipo where idusuario='$idu'");
		while($row1=mysql_fetch_array($query1)){
			array_push($ides, $row1['ide']);
		}
    //borra datos del user en las siguietnes tablas de todos los equipos del user 
		foreach($ides as $ide){
			$query=mysql_query("DELETE  from voto where ide='$ide'");
			$query2=mysql_query("DELETE from peticion where ide='$ide'");
			$query3=mysql_query("DELETE  from jugador where ide='$ide'");
			$query4=mysql_query("DELETE from partido where ide='$ide'");
			$query5=mysql_query("DELETE  from equipo where ide='$ide'");
		}//borra datos de las otras tablas donde aparezca el usuario, por ultimo vota al usuario
		$query=mysql_query("DELETE from voto where idu='$idu'");
		$query2=mysql_query("DELETE from peticion where ida='$idu' or idj='$idu'");
		$query3=mysql_query("DELETE from jugador where idu='$idu'");
		$query4=mysql_query("DELETE from partido where idu='$idu'");
		$query5=mysql_query("DELETE from usuario where idu='$idu'");
           $lista[0]=array("borrado"=>"1");
}else{
           $lista[0]=array("borrado"=>"0");
}
 echo json_encode ($lista);
?>



