<?php
session_start();
$page_title = 'Borrar usuario';
require_once('connectvars.php');
require_once('header.php');
$dbc = mysqli_connect(DBHOST,RT,CLAVE,DB);
$id = $_SESSION['user_id'];
if(isset($id)){
    //busca todos los equipos donde sea jugador y donde haya hecho peticiones para ser jugador
    $result=mysqli_query($dbc,"SELECT ide from jugador where idu = '$id' "); 
    $jides=array();
    while ($row = mysqli_fetch_array($result)) {
        $idej = $row[0];
        array_push($jides,$idej);
    }
    $result1=mysqli_query($dbc,"SELECT ide from peticion where idj = '$id' "); 
    $pides=array();
    while ($row = mysqli_fetch_array($result1)) {
        $idep = $row[0];
        array_push($pides,$idep);
    }
    $r1=array();
    $r2=array();
    if(count($jides)>1){
        $r1=implode("' and ide != '",$jides);
    }else{
        $r1= $jides[0];
    }
    if(count($pides)>1){
        $r2=implode("' AND ide != '",$pides);
    }else{
        $r2= $pides[0];
    }
    //busca equipos donde  no se den las condiciones anteriores
    $resultado=mysqli_query($dbc,"SELECT * FROM equipo WHERE ide != '$r1' and ide != '$r2' "); 
    if (mysqli_num_rows($resultado) >= 1) {
        //lista de equipos disponibles
        while ($row = mysqli_fetch_array($resultado)) {
            $ide = $row['ide'];
            $ne = $row['nombree'];
            $iduser = $row['idusuario'];
            $resultadof[]=array("ide"=>"$ide","nombree"=>"$ne","idusuario"=>"$iduser");      
        }
        //lista de equipos en checkbox
?><form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>"><?php
        echo '<h3>Lista de equipos disponibles</h3>';
        echo'<ul>';
        foreach($resultadof as $res){
            echo '<li><input type="checkbox"  name="'.$res['ide'].'" value="'.$res['nombree'].'" >'.$res['nombree'].' </li>';
        }
        echo'</ul>';
        echo '<input type="submit" name="submit" value="Enviar ">';
        echo'</form>';
        //manda las peticiones a los equipos seleccionados
        if(isset($_POST['submit'])){
            foreach($resultadof as $rest){
                $ide=$rest['ide'];
                $nombree=$rest['nombree'];
                $iduser = $rest['idusuario'];
                if(isset($_POST[$ide])){
                    //hace peticiones y redirige a indes
                    $result1 = mysqli_query($dbc,"INSERT INTO peticion (ida,idj,ide,status)values (" . $iduser . ",".$id.",".$ide.",'0')");
                    if ($result1) {
                         $sign = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) .     '/index.php';
        ?><script>

        alert("se han realizado las peticiones");
        window.location.href = "<?php echo $sign; ?>"
        </script> <?php
                    }
                }
            }
        }
    }else{
        //redirige si no hay equipos dispnnibles
        $home_url = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) .     '/index.php';
        ?><script>
        
        alert("no hay equipos disponibles");
        window.location.href = "<?php echo $home_url; ?>"
      </script>
      <?php
    }
}
require_once('footer.php');

?>