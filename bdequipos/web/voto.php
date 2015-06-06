<?php
session_start();
require_once('connectvars.php');
require_once('header.php');
$dbc = mysqli_connect(DBHOST,RT,CLAVE,DB);
$id=$_SESSION['user_id'];
$ide = $_GET['ide'];
$idv = $_GET['idv'];
$idj = $_GET['idu'];
$semana_actual=date("Y-W");
$page_title = 'Votaciones';
$semana_actual = date("Y-W");
  $result1=mysqli_query($dbc,"SELECT * FROM voto WHERE idu='$idj' AND iduv='$id' AND ide='$ide' AND fecha='$semana_actual' ");
//consulta si se ha hecho votacion semanal
if (mysqli_num_rows($result1) >= 1) {
   $redirect = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) . '/verequipo.php?ide='.$ide;
   
?><script>
alert("Ya votó a este jugador");
window.location.href = "<?php echo $redirect; ?>"
</script> <?php  
}else{
//si no se ha hecho la votacion muestra form para votar
	?>
	<form method="post" action="<?php echo $_SERVER['PHP_SELF'].'?ide='.$ide.'&idu=' . $idj .'&idv='.$id; ?>">
		<p>Introduzca valoración del jugador</p>
        <input type="hidden"  value="'.$ide.'" name="ide"/>
        <input type="hidden" value="'.$idj.'" name="idu"/>
        <input type="hidden"  value="'.$id.'" name="idv"/>
            <input id="valmin" name="voto" type="number" min="1" max="10" step="1" value ="5"/> 
		<input type="submit" name="submit" value="Votar">
	</form>
		 <?php    
	if(isset($_POST['submit'])){
		if( $_POST['voto']>10|| $_POST['voto']<0){
            alert("Introduzca voto válido");
		}else{
			$valoracion=$_POST['voto'];
			$ide = $_GET['ide'];
			$idv = $_GET['idv'];
			$idj = $_GET['idu'];
			$result2=mysqli_query($dbc,"INSERT INTO voto(idu,iduv,ide,val,fecha) VALUES('".$idj."','".$id."', '".$ide."','".$valoracion."','".$semana_actual."') "); 
	if($result2){
	$redirect = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) . '/verequipo.php?ide='.$ide;
?><script>
alert("Voto realizado");
window.location.href = "<?php echo $redirect; ?>"
</script> <?php  
    }else{
		echo 'error';
    }
		}
	}else{

	}
}
  require_once('footer.php');

?>