
<?php
$ide = $_POST['ide'];
require_once 'funciones_bd.php';
$db = new funciones_BD();
//crear convocatoria semanal
$semana_actual = date("Y-W");
//comprueba que no exista partido creado en esa fecha

//comprueba si hay el numero suficiente de jugadores
$result=mysql_query("SELECT p.idu, u.nombre, u.valoracion FROM partido as p inner join usuario as u  ON p.ide ='$ide' and p.idu=u.idu and p.fecha='$semana_actual' ORDER BY u.valoracion DESC");
$i=0;
while($row = mysql_fetch_array($result)){
    $nombre=$row['nombre'];
    $valoracion=$row['valoracion'];
    $idu=$row['idu'];
    $lista[]=array("nombreu"=>$nombre,"valoracion"=>$valoracion,"idu"=>$idu);
$i++;
    
}
echo json_encode ($lista);




?>