<?php
ini_set('display_errors', 0);

session_start();

$page_title = 'Mis Estadisticas';
require_once('header.php');
require_once('connectvars.php');

$dbc = mysqli_connect(DBHOST,RT,CLAVE,DB);
$idusuario= $_SESSION['user_id'];

if(isset($_POST['enviar'])){
    //invierte valores selector valoraciones
    if($_POST['valmin']>$_POST['valmax']){
       $valmin =$_POST['valmax'];
       $valmax =$_POST['valmin'];
    }
//recoge los filtros de las estadisticas y si no existe define por defecto
    $orden = $_POST['orden'];
    $nombre = $_POST['nombre'];
    $valmin = $_POST['valmin'];
    $valmax = $_POST['valmax'];
    $ide = $_POST['ide'];
    $idujugador = $_POST['idujugador'];
    if(!($_POST['valmin'])){
        $valmin=0;
    }
    if(!($_POST['valmax'])){
        $valmax=10;
    }
    if($_POST['desc']==1){
        $desc=" DESC ";
    }else if($_POST['desc']==0){
        $desc=" ASC ";
    }else if(!$_POST['desc']){
        $desc=" DESC ";
    }
    $filtro = $_POST['filtro'];
    $semana_actual = date("Y-W");
    $senteciaorden="";
    if($orden==0){
        $senteciaorden=" ORDER BY p.fecha ".$desc."";
    }else if($orden==1){
        $senteciaorden=" ORDER BY p.valoracion ".$desc."";
    }else if($orden==2){
        $senteciaorden=" ORDER BY e.nombree ".$desc."";
    }
    //consultas segun filtros selecionados y si no hay tuplas devueltas redirecciona al formulario
    if($filtro==0){
        //filtro solo usuario
        $result1=mysqli_query($dbc,"SELECT p.fecha, u.nombre, p.valoracion, e.nombree FROM partido AS p INNER JOIN usuario AS u inner join equipo as e ON p.idu='$idusuario' AND p.idu=u.idu AND p.ide=e.ide AND p.fecha!='$semana_actual' AND p.valoracion<='$valmax' AND p.valoracion>='$valmin'  ". $senteciaorden."  ");
        if (mysqli_num_rows($result1) > 0) {
            echo '<div class="estadisticas" >';
            echo '<table >';
            echo '<tr> <th>Fecha</th>';
            echo ' <th>Nombre</th>';
            echo ' <th>Valoración</th>';
            echo ' <th>Equipo</th></tr>';
            while($row=mysqli_fetch_array($result1)){
                echo '<tr>';
                echo '<td> '.$row['fecha'].'</td>';
                echo '<td>'.$row['nombre'].'</td>';
                echo '<td>'.$row['valoracion'].'</td>';
                echo '<td> '.$row['nombree'].'</td>';
                echo '</tr>';
            }   
            echo '</table >';
            echo '</div >';
        }else{
            $redirect = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) .     '/estadisticas.php';
?><script>

alert("no hay resultados");
window.location.href = "<?php echo $redirect; ?>"
</script> <?php
        }
    }else if($filtro==1){
        //filtro todos los usuarios
        $query="SELECT p.fecha, u.nombre, p.valoracion, e.nombree FROM partido AS p INNER JOIN usuario AS u inner join equipo as e ON p.idu=u.idu AND p.ide=e.ide AND p.fecha!='$semana_actual' AND p.valoracion<='$valmax' AND p.valoracion>='$valmin'  ". $senteciaorden."  ";
        $result1=mysqli_query($dbc,$query);
        if (mysqli_num_rows($result1) > 0) {
            echo '<div class="estadisticas" >';
            echo '<table >';
            echo '<tr> <th>Fecha</th>';
            echo ' <th>Nombre</th>';
            echo ' <th>Valoracion</th>';
            echo ' <th>Equipo</th></tr>';
            while($row=mysqli_fetch_array($result1)){
                echo '<tr>';
                echo '<td>'.$row['fecha'].'</td>';
                echo '<td>'.$row['nombre'].'</td>';
                echo '<td>'.$row['valoracion'].'</td>';
                echo '<td> '.$row['nombree'].'</td>';
                echo '</tr>';
            }  
            echo '</table >';
            echo '</div >';

        }else{
            $redirect = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) .     '/estadisticas.php';
?><script>

alert("no hay resultados");
window.location.href = "<?php echo $redirect; ?>"
</script> <?php
        }
    }else if($filtro==3){
        //filtro buscar por equipo
        $result1=mysqli_query($dbc,"SELECT p.fecha, u.nombre, p.valoracion, e.nombree FROM partido AS p INNER JOIN usuario AS u inner join equipo as e ON    p.idu=u.idu AND e.nombree='$nombre' AND p.ide=e.ide AND p.fecha!='$semana_actual' AND p.valoracion<='$valmax' AND p.valoracion>='$valmin'  ". $senteciaorden."  ");
        if (mysqli_num_rows($result1) > 0) {
            echo '<div class="estadisticas" >';
            echo '<table >';
            echo '<tr> <th>Fecha</th>';
            echo ' <th>Nombre</th>';
            echo ' <th>Valoracion</th>';
            echo ' <th>Equipo</th></tr>';
            while($row=mysqli_fetch_array($result1)){
                echo '<tr>';
                echo '<td> '.$row['fecha'].'</td>';
                echo '<td> '.$row['nombre'].'</td>';
                echo '<td>'.$row['valoracion'].'</td>';
                echo '<td>'.$row['nombree'].'</td>';
                echo '</tr>';
            }
                        echo '</table >';
                        echo '</div >';

        }  else{
            $redirect = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) .     '/estadisticas.php';
?><script>

alert("no hay resultados");
window.location.href = "<?php echo $redirect; ?>"
</script> <?php
        }
    }else if($filtro==2){
        //mis equipos
        $result=mysqli_query($dbc,"SELECT ide FROM jugador where idu='$idusuario' ");
        $ides=array();
        $inicioIdes=" AND ( e.ide= ' ";
        $finIdes="' ) ";
        if (mysqli_num_rows($result) > 0) {
            echo '<div class="estadisticas" >';
            echo '<table >';
            echo '<tr> <th>Fecha</th>';
            echo ' <th>Nombre</th>';
            echo ' <th>Valoracion</th>';
            echo ' <th>Equipo</th></tr>';
            while($row=mysqli_fetch_array($result)){
                array_push($ides,$row['ide']);
            } 
            $nids=COUNT($ides);
            if($nids!=0){
                $idesequipos= implode(" ' OR  e.ide= ' " , $ides);
                $sentequipos=$inicioIdes ."  ". $idesequipos ." ".$finIdes;
            }else{
                $sentequipos="";
            }
            $result1=mysqli_query($dbc,"SELECT p.fecha, u.nombre, p.valoracion, e.nombree FROM partido AS p INNER JOIN usuario AS u inner join equipo as e ON p.idu=u.idu AND p.idu=u.idu AND p.ide=e.ide AND p.fecha!='$semana_actual'  AND p.valoracion<='$valmax' AND p.valoracion>='$valmin' ".$sentequipos."  ". $senteciaorden."  ");

            while($row=mysqli_fetch_array($result1)){
                 echo '<tr>';
                echo '<td>'.$row['fecha'].'</td>';
                echo '<td>'.$row['nombre'].'</td>';
                echo '<td>'.$row['valoracion'].'</td>';
                echo '<td>'.$row['nombree'].'</td>';
                echo '</tr>';
            }  
            echo '</table >';
            echo '</div >';

        }else{
            $redirect = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) .     '/estadisticas.php';
?><script>

alert("no hay resultados");
window.location.href = "<?php echo $redirect; ?>"
</script> <?php
        }
    }else if($filtro==4){
        //filtro jugador especifico
        
        $query="SELECT p.fecha, u.nombre, p.valoracion, e.nombree FROM partido AS p INNER JOIN usuario AS u inner join equipo as e ON p.idu=u.idu AND u.nombre='$nombre' AND p.ide=e.ide AND p.fecha!='$semana_actual' AND p.valoracion<='$valmax' AND p.valoracion>='$valmin'  ". $senteciaorden."  ";
        $result1=mysqli_query($dbc,$query);
        if (mysqli_num_rows($result1) > 0) {
            echo '<div class="estadisticas" >';
            echo '<table >';
            echo '<tr> <th>Fecha</th>';
            echo ' <th>Nombre</th>';
            echo ' <th>Valoracion</th>';
            echo ' <th>Equipo</th></tr>';
            while($row=mysqli_fetch_array($result1)){
                echo '<tr>';
                echo '<td>'.$row['fecha'].'</td>';
                echo '<td>'.$row['nombre'].'</td>';
                echo '<td>'.$row['valoracion'].'</td>';
                echo '<td>'.$row['nombree'].'</td>';
                echo '</tr>';
            }  
            echo '</table >';
        echo '</div >';

        }/*else{
            $redirect = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) .     '/estadisticas.php';
?><script>

alert("no hay resultados");
window.location.href = "<?php echo $redirect; ?>"
</script> <?php
        }*/
    }

}else{
    //formulario seleccion de filtros para estadisticas
?>

<div class="formulario">
    <form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>" >

        <input type="hidden"  value="'.$idusuario.'" name="idu"/>          
        <h3>Personaliza tus estadisticas</h3>

        <fieldset>
            <legend>Ordenar por:	 </legend>
            <input type="radio" name="orden"
                   <?php if (isset($orden) && $orden=="0") echo "checked";?>
                   value="fecha">Fecha
            <input type="radio" name="orden"
                   <?php if (isset($orden) && $orden=="1") echo "checked";?>
                   value="valoracion">Valoración
            <input type="radio" name="orden"
                   <?php if (isset($equipo) && $equipo=="2") echo "checked";?>
                   value="equipo">Equipo
        </fieldset>

        <fieldset>
            <legend>Orden descendente o ascendente</legend>
            <input type="radio" name="desc"
                   <?php if (isset($des) && $des=="1") echo "checked";?>
                   value="descendente">Descendente
            <input type="radio" name="desc"
                   <?php if (isset($des) && $des=="0") echo "checked";?>
                   value="ascenedente">Ascendente
        </fieldset>
        <fieldset>
            <legend>Rango de valoraciones:</legend>
            <p>valoración mínima:</p>

            <input id="valmin" name="valmin" type="number" min="1" max="10" step="1" value ="1"/></br> 	
        <p>valoración máxima:</p>
        <input id="valmax" name="valmax" type="number" min="1" max="10" step="1" value ="10"/> 
        </fieldset>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script>
        $( document ).ready(function() {
            $('input[name=filtro]').change(function(){
                if ($('#rbjugador').prop("checked") || $('#rbequipo').prop("checked")) {
                    $('#campoculto').show(400)
                } else {
                    $('#campoculto').hide(400)
                }
            });
        });
    </script>
    <fieldset>

        <legend>Filtrar por:</legend>	<br>
        <input type="radio" name="filtro"
               <?php if (isset($orden) && $orden=="solo") echo "checked";?>
               value="0">Solo
        <input type="radio" name="filtro"  
               <?php if (isset($orden) && $orden=="todos") echo "checked";?>
               value="1">Todos
        <input type="radio" name="filtro"
               <?php if (isset($equipo) && $equipo=="misequipos") echo "checked";?>
               value="2">Mis Equipos
        <input type="radio" id="rbequipo" name="filtro"
               <?php if (isset($equipo) && $equipo=="equipo") echo "checked";?>
               value="3">Equipo
        <input type="radio"  id="rbjugador" name="filtro"
               <?php if (isset($equipo) && $equipo=="jugador") 
    echo "checked";
               ?>value="4">Jugador
    </fieldset>
    <fieldset id="campoculto">
        <legend>nombre de jugador a buscar:</legend>
        <input type="text" name="nombre"/ >
    </fieldset>
    <input type="Submit" name="enviar" value="Buscar"/>
    </form>
</div>
<?php
     }

require_once('footer.php');

?>