<?php
session_start();
$page_title = 'Crear equipo';
require_once('connectvars.php');
require_once('header.php');
$dbc = mysqli_connect(DBHOST,RT,CLAVE,DB);
$redirect = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) .     '/index.php';
$error_msg = "";
//solo accede si hay session iniciada
if (isset($_SESSION['user_id'])) {
    $id=$_SESSION['user_id'];
//si hay submit entra
    $nombrej=$_SESSION['user_name'];
    if(isset($_POST['enviar'])){
        $nombre=$_POST['nombreequipo'];
        $select = "SELECT * from equipo where nombree='$nombre'";
        $consulta=mysqli_query($dbc, $select);
        //si existe equipo no entra si existe entra y crea equipo nuevo
        if (mysqli_num_rows($consulta) == 0) {
            $query = "INSERT INTO equipo (nombree,idusuario) VALUES " .
                "('$nombre', '$id')";
            $insert=mysqli_query($dbc, $query);
            $select1 = "SELECT * from equipo where nombree='$nombre'";
            $consulta1=mysqli_query($dbc, $select);
            $rows1=mysqli_fetch_array($consulta1);
            $ide=$rows1['ide'];
            //al crear equipo tambien crea nuevo jugador al administrador
            $query2 = "INSERT INTO jugador (idu,nombrej,ide,adm) VALUES " .
                "('$id','$nombrej', '$ide','1')";
            $insert2=mysqli_query($dbc, $query2);
            if($insert2){
                //redirige a index
?><script>
alert("Se ha insertado correctamente el equipo");
window.location.href = "<?php echo $redirect; ?>"
</script> <?php
            }
        }else{
alert("Ya existe un equipo con ese nombre");
        }
    }else{
        //formulario creacion equipo
?>
<form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>" >
   
    <h3>Crear equipo nuevo</h3>
    <fieldset>
        <legend>Nombre del equipo a crear:</legend>
        <input type="text" name="nombreequipo"/ >
    </fieldset>
    <input type="submit" name="enviar" value="Crear"/>
    <input type="button" onclick="window.location='<?php echo $redirect; ?>'"  value="Cancelar"/>
</form>
<?php
         }
}
mysqli_close($dbc);
require_once('footer.php');

?>