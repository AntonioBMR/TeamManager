
<?php

/*LOGIN*/
$usuario = $_POST['usuario'];
$passw = $_POST['password'];
require_once 'funciones_bd.php';
$db = new funciones_BD();
$result=mysql_query("SELECT * FROM usuario WHERE nombre='$usuario' AND passw='$passw' "); 
if (mysql_num_rows($result) == 1) {
    $row = mysql_fetch_array($result);
    $id = $row['idu'];
    $val = $row['valoracion'];
    $resultado[]=array("logstatus"=>"$id","valoracion"=>"$val" );      
}else{
    $resultado[]=array("logstatus"=>"0");
}
echo json_encode($resultado);
?>
