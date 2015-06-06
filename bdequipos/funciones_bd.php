<?php

class funciones_BD {
    private $db;
    // constructor
    function __construct() {
        require_once 'connectbd.php';
        // conexion to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }
    // destructor
    function __destruct() {
    }
    // agregar nuevo usuario
    public function adduser($username, $password) {
        $result = mysql_query("INSERT INTO usuario(nombre,passw) VALUES('$username', '$password')");
        if ($result) {
            return true;
        } else {
            return false;
        }
    }
    public function addequipo($nombre, $idu) {
        //pregunta si existe equipo con ese nombre si no, lo crea
        $queryexiste=mysql_query("SELECT ide FROM equipo WHERE nombree='$nombre' ");
        if(mysql_num_rows($queryexiste)==0){
            $result = mysql_query("INSERT INTO equipo(nombree,idusuario) VALUES('$nombre', '$idu')");
            if ($result) {
                $result1=mysql_query("SELECT ide FROM equipo WHERE nombree='$nombre' AND idusuario='$idu' ");
                $result3=mysql_query("SELECT nombre FROM usuario WHERE idu='$idu' ");
                if(($result1)&&($result3)){
                    $row1= mysql_fetch_array($result3);
                    $row = mysql_fetch_array($result1);
                    $ide=$row['ide'];
                    $nom=$row1['nombre'];
                    $result2 = mysql_query("INSERT INTO jugador(idu,nombrej,ide) VALUES('$idu','$nom', '$ide')"); 
                    if ($result) {
                        return true;
                    }
                    return false;
                }            
                return false;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }
    public function addjugador($idu,$nombre, $ide) {
        $result = mysql_query("INSERT INTO jugador(idu,nombrej,ide,adm) VALUES('$idu','$nombre', '$ide',1)");
        if ($result) {
            return true;
        } else {
            return false;
        }
    }
    public function isuserexist($username) {
        $result = mysql_query("SELECT nombre from usuario WHERE nombre = '$username'");
        $num_rows = mysql_num_rows($result); //numero de filas retornadas
        if ($num_rows > 0) {
            // el usuario existe 
            return true;
        } else {
            // no existe
            return false;
        }
    }
    public function login($user,$passw){
        $result=mysql_query("SELECT COUNT(*) FROM usuario WHERE nombre='$user' AND passw='$passw' "); 
        $count = mysql_fetch_row($result);
        /*como el usuario debe ser unico cuenta el numero de ocurrencias con esos datos*/
        if ($count[0]==0){
            return true;
        }else{
            return false;
        }
    }
}

?>
