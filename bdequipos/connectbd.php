<?php
 //CLASE CONEXION AUTOMATICA
class DB_Connect {
    // constructor
    function __construct() {
    }
    // destructor
    function __destruct() {
     // $this->close();
    }
    // Conexion a database
    public function connect() {
        require_once 'config.php';
        // conexion a mysql
        $con = mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);
        // seleccion de db
        mysql_select_db(DB_DATABASE);
        // retorno de conexion
        return $con;
    }
    // Closing database connection
    public function close() {
        mysql_close();
    }
}
 
?>
