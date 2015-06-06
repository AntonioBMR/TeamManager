<?php

$idu = $_POST['ida'];
//$idu=1;

require_once 'funciones_bd.php';
$db = new funciones_BD();
//comprueba peticiones recibidas en equipos donde el user es administrador
$result = mysql_query("select e.nombree ,e.ide, u.idu ,u.nombre from equipo as e inner join usuario as u inner join peticion as p on p.ida='$idu' and p.ide=e.ide and u.idu=p.idj and p.status='0'");
while ($row = mysql_fetch_array($result)) {
    $ne = $row['nombree'];
    $nj = $row['nombre'];
    $ide = $row['ide'];
    $idj = $row['idu'];
    $resultadof[]=array("ide"=>"$ide","nombree"=>"$ne","idj"=>"$idj","nombre"=>"$nj");      
}
echo json_encode($resultadof);
?>