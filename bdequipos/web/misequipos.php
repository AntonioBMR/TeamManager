<?php
session_start();
$page_title = 'Crear equipo';
require_once('connectvars.php');
require_once('header.php');

$dbc = mysqli_connect(DBHOST,RT,CLAVE,DB);

if (isset($_SESSION['user_id'])) {
    $id=$_SESSION['user_id'];
    $nombrej=$_SESSION['user_name'];
    //busca los equipos donde juega el usuario
    $query = "SELECT e.nombree, e.ide,u.idu from equipo as e inner join jugador as j inner join usuario as u ON j.idu = u.idu and u.idu='$id' and j.ide=e.ide";
    $data = mysqli_query($dbc, $query);
    //si no es una consulta vacia muestra si no redirige
    if (mysqli_num_rows($data) > 0) {
        //muestra los equipos
        echo '<div id="misequipos">';
        echo '<table class="misequipos"  >';
        echo '<tr>';
        echo '<th>Mis Equipos</th>';
        echo '</tr>';
        while ($row = mysqli_fetch_array($data)) {
            $id=$row['idu'];
            $nombre = $row['nombree'];
            $ide = $row['ide'];
            echo '<tr><td> <a href="verequipo.php?ide='.$ide.'">'.$nombre.'</a></td></tr>';     
        }
        echo '</table>';
        echo '</div>';
    }else{
        $sign = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) .     '/index.php';
        ?><script>

        alert("se han realizado las peticiones");
        window.location.href = "<?php echo $sign; ?>"
        </script> <?php
    }
}

require_once('footer.php');
?>


