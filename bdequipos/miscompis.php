
<?php

/*mis compañeros de equipo*/

$idu = $_POST['idu'];
$ide = $_POST['ide'];
require_once 'funciones_bd.php';
$db = new funciones_BD();
$jugadores=array();
//busca idjugadores de mi equipo
$query=mysql_query("SELECT idu FROM jugador WHERE ide ='$ide' AND idu !='$idu'");
while($rowq=mysql_fetch_array($query)){
     array_push($jugadores,$rowq['idu']);
}
$nj=COUNT($jugadores);  
if(($nj+1)%2==0 && !empty($jugadores)){
      //crear convocatoria semanal
    $semana_actual = date("Y-W");
    //comprueba que no exista partido creado en esa fecha
    $resultP=mysql_query("SELECT * FROM partido WHERE ide ='$ide' AND fecha='$semana_actual'");
    if($resultP){
        if($row = mysql_fetch_array($resultP)==0) {
            //comprueba si hay el numero suficiente de jugadores
         $resultUJ=mysql_query("SELECT j.idu, u.valoracion, u.nombre FROM jugador as j inner join usuario as u  ON j.ide ='$ide' and j.idu=u.idu ORDER BY u.valoracion DESC");
            $contador=0;
            while($row1=mysql_fetch_array($resultUJ)){
                $idusuario=$row1['idu'];
                if(($contador+1)%2==0 ){
                        $equipo=1;
                    }else{
                        $equipo=2;
                    }
                $resultIP=mysql_query("INSERT INTO partido(fecha,ide,idu,equipo) VALUES('$semana_actual', '$ide','$idusuario','$equipo') ");
                $contador++;
            }    
              //actualiacion de valoraciones por partido
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
                $query=mysql_query("SELECT distinct idu from jugador where ide='$ide' ");
                while($rowq=mysql_fetch_array($query)){
                    array_push($ids,$rowq['idu']);
                }
                $nids=COUNT($ids);
                foreach($ids as $id){   
                    $idvotado=$id;
                    //acutaliza media del jugador con los votos recibidos en ese partido
                    $query=mysql_query("SELECT  val from voto where ide='$ide' and fecha='$semana_anterior'and idu='$idvotado'");
                    $sum=0;
                    $nvotos=0;
                    if($query){
                        while($rowq=mysql_fetch_array($query)){
                            $valoracion=$rowq['val'];
                            $sum=$sum+$valoracion;
                            $nvotos++;                    
                        }
                        $media=$sum/$nvotos;
                        $media=intval($media);       
                        $query1=mysql_query("UPDATE partido SET valoracion='$media' where ide='$ide' and fecha='$semana_anterior' and idu='$idvotado'");
                         $result3=mysql_query("SELECT valoracion FROM partido WHERE idu='$idvotado' and fecha!= '$semana_actual'");
                        $i=0;
                        $sumatoria=0;
                        while ($row = mysql_fetch_array($result3)) {
                            $val=$row['valoracion'];
                            $sumatoria=$val+$sumatoria;
                            $i++;
                        }
                        $valactual=$sumatoria/$i;
                        $valactual=intval($valactual);      
                        //actualiza media de valoraciones recibidas en todos los partidos
                        $result4 = mysql_query("update usuario SET valoracion='$valactual' WHERE idu='$idvotado' ");
                        if($result4){
                        }
                    }
                }
            }else {
            }
        }
}
////devuelve lista de jugadores del equipo del jugador
$result=mysql_query("SELECT * FROM jugador WHERE idu !='$idu' AND ide='$ide'");
if($result){
    $lista[]=array();
    $i=0;
    while ($row = mysql_fetch_array($result)) {
 $lista[$i]=array("idj"=>$row['idj'],"ide"=>$row['ide'],"nombrej"=>$row['nombrej'],"idu"=>$row['idu']);
        $i++;
    }
}else{
        array_push($lista,"0");   
}
echo json_encode ($lista);
?>