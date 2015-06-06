<?php
session_start();

$page_title = 'Borrar usuario';
  require_once('header.php');
  require_once('connectvars.php');

    $dbc = mysqli_connect(DBHOST,RT,CLAVE,DB);
$idu = $_SESSION['user_id'];
$ides=array();
//recogemos datos del usuario
$query=mysqli_query($dbc,"SELECT * from usuario where idu='$idu' ");
    $lista[]=array();

if($query){
//recogemos los equipos del usuario
	$query1=mysqli_query($dbc,"SELECT * from equipo where idusuario='$idu'");
		while($row1=mysqli_fetch_array($query1)){
			array_push($ides, $row1['ide']);
		}
    //borramos todos los equipos donde el usuario es administrador
		foreach($ides as $ide){
			$query=mysqli_query($dbc,"DELETE  from voto where ide='$ide'");
			$query2=mysqli_query($dbc,"DELETE from peticion where ide='$ide'");
			$query3=mysqli_query($dbc,"DELETE  from jugador where ide='$ide'");
			$query4=mysqli_query($dbc,"DELETE from partido where ide='$ide'");
			$query5=mysqli_query($dbc,"DELETE  from equipo where ide='$ide'");
		}
    //borramos los datos en las demas tablas donde pueda estar el usuario
		$query=mysqli_query($dbc,"DELETE from voto where idu='$idu'");
		$query2=mysqli_query($dbc,"DELETE from peticion where ida='$idu' or idj='$idu'");
		$query3=mysqli_query($dbc,"DELETE from jugador where idu='$idu'");
		$query4=mysqli_query($dbc,"DELETE from partido where idu='$idu'");
		$query5=mysqli_query($dbc,"DELETE from usuario where idu='$idu'");
        $home_url = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) .     '/logout.php';
                header('Location: ' . $home_url);
}else{
    
          echo 'error intentelo de nuevo';
}
require_once('footer.php');

?>
