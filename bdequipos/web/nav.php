<?php
// Generate the navigation menu
echo '<hr />';
echo '<div class="navbar">';
require_once('connectvars.php');
//barra nav solo si existe variable id session si no redirige a login
$id=$_SESSION['user_id'];
if (isset($_SESSION['user_name']) || basename($_SERVER['PHP_SELF']) == 'signup.php') {
    ?>   
    <a href="index.php"><img src="imagenes/logoprincipal.png" width="60px"></img></a> 
    <a class="navderecho" href="logout.php">Log Out <?php  $_SESSION['user_name'];?></a>

    <?php

     echo '<a class="navderecho" href="http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) .     '/borrar.php " onclick="confirmdelete($(this)); return false;">Borrar</a>';

    echo '<div class="clear"></div>';


}else {

    $home_url = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) .     '/login.php';
    header('Location: ' . $home_url);

}
echo '</div>';
echo '<hr />';

?>