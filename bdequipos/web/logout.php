<?php

session_start();
if ( isset($_SESSION['user_id'])) {
// borra variables de session y cookies
    session_destroy();
    $_SESSION['user_id']=array();
    $_SESSION['user_name']=array();
    setcookie('user_id', '', time() - 2600000);
    setcookie('user_name', '', time() - 2600000);

}
// Redirige a login
$home_url = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) . '/login.php ';
header('Location: ' . $home_url);
?>