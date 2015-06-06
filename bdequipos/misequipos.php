
<?php

/*Mis equipos*/

$idu = $_POST['idusuario'];
require_once 'funciones_bd.php';
$db = new funciones_BD();
//devuelve lista de equipos donde juega el usser
$result=mysql_query("SELECT e.nombree,e.ide,j.idu FROM jugador as j inner join equipo as e on j.idu='$idu' and j.ide=e.ide");
if($result){
    $lista[]=array();
    $i=0;
    while ($row = mysql_fetch_array($result)) {
        $lista[$i]=array("ide"=>$row['ide'],"nombree"=>$row['nombree'],"idusuario"=>$row['idu']);
$i++;
    }
    $lista1=1;
}else{
        array_push($lista,"0");
}
echo json_encode ($lista);
?>