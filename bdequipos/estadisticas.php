<?php
$filtro=1;
$orden = $_POST['orden'];
$nombre = $_POST['nombre'];
$valmin = $_POST['valmin'];
$valmax = $_POST['valmax'];
$idu = $_POST['idu'];
//da datos por defecto si no se selecionan filtros
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

$idujugador = $_POST['idujugador'];
$filtro = $_POST['filtro'];
//ORDEN 0=Valoracion 1=fecha 2=equipos
//filtro 0=Solo 1=todos 2=misequipos 3=unequipo 4=1jugador
require_once 'funciones_bd.php';
$db = new funciones_BD();
//valmin max
$semana_actual = date("Y-W");
$senteciaorden="";

if($orden==0){
    $senteciaorden=" ORDER BY p.fecha ".$desc."";
}else if($orden==1){
    $senteciaorden=" ORDER BY p.valoracion ".$desc."";
}else if($orden==2){
    $senteciaorden=" ORDER BY e.nombree ".$desc."";

}
$lista=array();

if($filtro==0){
    //yo solo
    $result1=mysql_query("SELECT p.fecha, u.nombre, p.valoracion, e.nombree FROM partido AS p INNER JOIN usuario AS u inner join equipo as e ON p.idu='$idu' AND p.idu=u.idu AND p.ide=e.ide AND p.fecha!='$semana_actual' AND p.valoracion<='$valmax' AND p.valoracion>='$valmin' ". $senteciaorden."  ");
    $i=0;
    while($row=mysql_fetch_array($result1)){
        $lista[$i]=array("fecha"=>$row['fecha'],"nombre"=>$row['nombre'],"valoracion"=>$row['valoracion'],"nombree"=>$row['nombree']);
        $i++;
    }    
}else if($filtro==1){
    //todos
    $result1=mysql_query("SELECT p.fecha, u.nombre, p.valoracion, e.nombree FROM partido AS p INNER JOIN usuario AS u inner join equipo as e ON p.idu=u.idu AND p.ide=e.ide AND p.fecha!='$semana_actual' AND p.valoracion<='$valmax' AND p.valoracion>='$valmin' ". $senteciaorden."  ");
    $i=0;
    while($row=mysql_fetch_array($result1)){
        $lista[$i]=array("fecha"=>$row['fecha'],"nombre"=>$row['nombre'],"valoracion"=>$row['valoracion'],"nombree"=>$row['nombree']);
        $i++;
    }  
}else if($filtro==3){
    //buscar por equipo
    $ide="";
    $query=mysql_query("SELECT ide from equipo where nombree='$nombre'");
    while($row1=mysql_fetch_array($query)){
        $ide=$row1['ide'];
    }
    $result1=mysql_query("SELECT p.fecha, u.nombre, p.valoracion, e.nombree FROM partido AS p INNER JOIN usuario AS u inner join equipo as e ON    p.idu=u.idu AND e.ide='$ide' AND p.ide=e.ide AND p.fecha!='$semana_actual' AND p.valoracion<='$valmax' AND p.valoracion>='$valmin'  ". $senteciaorden."  ");
    $i=0;
    while($row=mysql_fetch_array($result1)){
        $lista[$i]=array("fecha"=>$row['fecha'],"nombre"=>$row['nombre'],"valoracion"=>$row['valoracion'],"nombree"=>$row['nombree']);
        $i++;
    }  
}else if($filtro==2){
    // echo mis equipos
    $result=mysql_query("SELECT ide FROM jugador where idu='$idu' ");
    $ides=array();
    $inicioIdes=" AND ( e.ide= ' ";
    $finIdes="' ) ";
    //filtrar por si no hay equipos
    while($row=mysql_fetch_array($result)){
        array_push($ides,$row['ide']);
    } 
    $nids=COUNT($ides);
    if($nids!=0){
        $idesequipos= implode(" ' OR  e.ide= ' " , $ides);
        $sentequipos=$inicioIdes ."  ". $idesequipos ." ".$finIdes;
    }else{
        $sentequipos="";
    }
    $result1=mysql_query("SELECT p.fecha, u.nombre, p.valoracion, e.nombree FROM partido AS p INNER JOIN usuario AS u inner join equipo as e ON p.idu=u.idu AND p.idu=u.idu AND p.ide=e.ide AND p.fecha!='$semana_actual'  AND p.valoracion<='$valmax' AND p.valoracion>='$valmin' ".$sentequipos."  ". $senteciaorden."  ");
    $i=0;
    while($row=mysql_fetch_array($result1)){
        $lista[$i]=array("fecha"=>$row['fecha'],"nombre"=>$row['nombre'],"valoracion"=>$row['valoracion'],"nombree"=>$row['nombree']);
        $i++;
    }  
}
else if($filtro==4){
    // 'buscar por jugador
    $idjugador="";
    $query=mysql_query("SELECT idu from usuario where nombre='$nombre'");
    while($row1=mysql_fetch_array($query)){
        $idjugador=$row1['idu'];
    }
    $result1=mysql_query("SELECT p.fecha, u.nombre, p.valoracion, e.nombree FROM partido AS p INNER JOIN usuario AS u inner join equipo as e ON p.idu=u.idu AND u.idu='$idjugador' AND p.ide=e.ide AND p.fecha!='$semana_actual' AND p.valoracion<='$valmax' AND p.valoracion>='$valmin'   ". $senteciaorden."  ");
    $i=0;

    while($row=mysql_fetch_array($result1)){
        $lista[$i]=array("fecha"=>$row['fecha'],"nombre"=>$row['nombre'],"valoracion"=>$row['valoracion'],"nombree"=>$row['nombree']);
        $i++;
    }  
}
echo json_encode($lista);
?>