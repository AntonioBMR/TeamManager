<?php
require_once('header.php');
require_once('connectvars.php');
$semana_actual = date("Y-W");
$id=$_SESSION['user_id'];
// Conexion a bd
$dbc = mysqli_connect(DBHOST,RT,CLAVE,DB);
//consulta y visualizacion de convocatorias de la semana	
echo'</br><div class="contconv">';
	echo '<h3>Bienvenido '.$_SESSION['user_name'].'</h3>';
	echo '</div>';
$query = "SELECT e.nombree, p.fecha from partido as p inner join equipo as e ON p.idu='$id' and p.fecha='$semana_actual' and e.ide=p.ide";
$data = mysqli_query($dbc, $query);
if (mysqli_num_rows($data) >= 1) {
    echo'</br><div class="contconv">';
	echo '<h3>Convocatorias semanales </h3>';
	echo '<table >
		<TR> 

	</TR> ';
	while($row=mysqli_fetch_array($data)){
		echo '<td>'.$row['fecha'].'</td>';
		echo '<td>'.$row['nombree'].'</td></tr>';
	}
	echo '</table>';
	echo '</div>';
}

echo '</div><div class="clear"></div></div>';
    mysqli_close($dbc);

require_once('footer.php');
?>
