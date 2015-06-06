<?php
session_start();
require_once('connectvars.php');
require_once('header.php');
$dbc = mysqli_connect(DBHOST,RT,CLAVE,DB);
$page_title = ' Mi equipo ';
require_once('header.php');
$id = $_SESSION['user_id'];
$ide = $_GET['ide'];
$semana_actual = date("Y-W");
//crea partido semanal si no existe en la base de datos
$jugadores=array();
$query=mysqli_query($dbc,"SELECT idu FROM jugador WHERE ide ='$ide' AND idu !='$id'");
if (mysqli_num_rows($query) > 0){
    while($rowq=mysqli_fetch_array($query)){
        array_push($jugadores,$rowq['idu']);
    }
    if((COUNT($jugadores)+1)%2==0 && $jugadores!=0){
        //crear convocatoria semanal
        //comprueba que no exista partido creado en esa fecha
        $resultP=mysqli_query($dbc,"SELECT * FROM partido WHERE ide ='$ide' AND fecha='$semana_actual'");
        if($resultP){
            if($row = mysqli_fetch_array($resultP)==0) {
                echo 'nohay partido';
                //comprueba si hay el numero suficiente de jugadores
                $resultUJ=mysqli_query($dbc,"SELECT j.idu, u.valoracion, u.nombre FROM jugador as j inner join usuario as u  ON j.ide ='$ide' and j.idu=u.idu ORDER BY u.valoracion DESC");
                $contador=0;
                while($row=mysqli_fetch_array($resultUJ)){
                    $nombre=$row['nombre'];
                    $idusuario=$row['idu'];
                    $valoracion=$row['valoracion'];
                    if(($contador+1)%2==0 ){
                        $equipo=1;
                    }else{
                        $equipo=2;
                    }
                    $resultIP=mysqli_query($dbc,"INSERT INTO partido(fecha,ide,idu,equipo) VALUES('$semana_actual', '$ide','$idusuario','$equipo') ");
                    $contador++;
                }    

                //actualiacion semanal de valoraciones por partido
                $semant=explode("-",$semana_actual);
                $semana_act=$semant[1];
                //comprobar si es semana 0 o 1
                if($semana_act==1){
                    $ano_act= $semant[0];
                    $ano_anterior= $ano_act-1;
                    $semana_anterior=$ano_anterior."-".(52);
                }else{
                    $semana_anterior=$semant[0]."-".($semana_act-1);
                }            

                $ids=array();
                //busca jugadores del equipo donde juegue el usuario
                $query=mysqli_query($dbc,"SELECT distinct idu from jugador where ide='$ide' ");
                while($rowq=mysqli_fetch_array($query)){
                    array_push($ids,$rowq['idu']);
                }
                $nids=COUNT($ids);
                foreach($ids as $id){   
                    $idvotado=$id;                    
                    //recorre todas las votaciones recibidas en el partido de la semana anterior
                    $query=mysqli_query($dbc,"SELECT  val from voto where ide='$ide' and fecha='$semana_anterior'and idu='$idvotado'");
                    $sum=0;
                    $nvotos=1;
                    if($query){
                        while($rowq=mysqli_fetch_array($query)){
                            $valoracion=$rowq['val'];
                            $sum=$sum+$valoracion;
                            $nvotos++;                    
                        }
                        //realiza la media de todas las votaciones y actualiza la valoracion del 
                        //user en ese partido
                        $media=intval($media);       
                        $query1=mysqli_query($dbc,"UPDATE partido SET valoracion='$media' where ide='$ide' and fecha='$semana_anterior' and idu='$idvotado'");
                        $result3=mysqli_query($dbc,"SELECT valoracion FROM partido WHERE idu='$idvotado' and fecha!= '$semana_actual'");
                        $i=0;
                        $sumatoria=0;
                        while ($row = mysqli_fetch_array($result3)) {
                            $val=$row['valoracion'];
                            $sumatoria=$val+$sumatoria;
                            $i++;
                        }
                        //Tras esto realiza una media general de todos los partidos y equipos 
                        //del usuario y acutaliza esta info en la tabla USUARIO
                        $valactual=$sumatoria/$i;
                        $valactual=intval($valactual);       
                        $result4 = mysqli_query($dbc,"update usuario SET valoracion='$valactual' WHERE idu='$idvotado' ");
                    }
                }
            }else {
            }
        }
    }else{

    }
}else{
    $redirect = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) .     '/misequipos.php';
?><script>

alert("no hay jugadores en tu equipo");
window.location.href = "<?php echo $redirect; ?>"
</script> <?php
}
//lista de jugadores de mi equipo
$result=mysqli_query($dbc,"SELECT * FROM jugador WHERE idu !='$id' AND ide='$ide'");
if($result){
    $lista[]=array();
    $i=0;
    while ($row = mysqli_fetch_array($result)) {
        $lista[$i]=array("idj"=>$row['idj'],"ide"=>$row['ide'],"nombrej"=>$row['nombrej'],"idu"=>$row['idu']);
        $i++;
    }
}else{
    array_push($lista,"0");

}
//muestra los jugadores de mi equipo
if (isset($ide)) {
    $result = mysqli_query($dbc,"SELECT * from jugador where idu != '" . $id . "' and ide='" . $ide . "'");
    if (mysqli_num_rows($result) >= 1) {
        echo '<table>';
        echo '<tr><th>Compañeros de equipo</th></tr>';
        while ($row = mysqli_fetch_array($result)) {
            echo '<tr><td><a href="voto.php?ide='.$ide.'&idu=' . $row['idu'] .'&idv='.$id.'">' . $row['nombrej'] . '</a></td><tr>';
        }
        echo '</table>';
    }
//peticiones recibidas si el user es el administrador de ese equipo
    $result = mysqli_query($dbc,"SELECT u.nombre, u.idu from usuario as u inner join peticion as p on p.ida= '" . $id . "' and p.ide='" . $ide . "' and p.status='0' and u.idu=p.idj");
   
    if (mysqli_num_rows($result) >= 1) {
?> <form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>?ide=<?php echo $ide ;?>"><?php
                                        echo'<h3>Jugadores que quieren ser de tu equipo</h3></br>';

                                        while ($row = mysqli_fetch_array($result)) {
                                            $nombre = "'" . $row['nombre'] . "'";
                                            $valor = $row['idu'] . ',' . '' . $nombre . ',' . $ide . ',' . '1';

                                            echo '<input type="checkbox" value="' . $valor . '" name="validacion[]">'.$nombre.'</input>';
                                            echo "<br/>";
                                        }
                                        echo '<input type="submit" name="submitval" value="Aceptar ">';
                                        echo'</form>';
                                       }
}
//validacion de peticiones
if(isset($_POST['submitval'])){
    if(isset($_POST['validacion'])){
        foreach ((array) $_POST['validacion'] as $acept) {
            $ac = explode(",", $acept);
            $result1 = mysqli_query($dbc,"INSERT INTO jugador (idu,nombrej,ide,adm)value (" . $acept . ")");
            if ($result1) {
                $result2 = mysqli_query($dbc,"UPDATE peticion set status='1' where idj=" . $ac[0] . " and
						ide=" . $ide . "");
                if ($result2) {

                    $home_url = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) . '/verequipo.php?ide='.$ide.'';
                    header('Location: ' . $home_url);

                }
            }
        }
    }
}else{

}
//consulta sobre la convocatoria semanal
$convocatoria=mysqli_query($dbc,"select u.nombre, u.valoracion, p.equipo from partido  as p inner join usuario as u  ON p.ide='$ide' AND p.fecha='$semana_actual' AND u.idu=p.idu ");
if (mysqli_num_rows($convocatoria) >= 1) {
    echo'</br><div class="contconv"';
    echo '<h3>Convocatoria semana '.$semana_actual.'</h3></br>';
    echo '<table >
		<TR> 
	   <TH>Nombre jugador</TH> 
	   <TH>Valoracion</TH> 
	   <TH>Equipo</TH> 
	</TR> ';
    while($row=mysqli_fetch_array($convocatoria)){
        echo '<tr><td>'.$row['nombre'].'</td>';
        echo '<td>'.$row['valoracion'].'</td>';
        echo '<td>'.$row['equipo'].'</td></tr>';
    }
    echo '</table>';
    echo '</div>';
}
require_once('footer.php');

?>
