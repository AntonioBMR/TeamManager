<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<?php
echo '<title>Team Manager- ' . $page_title . '</title>';
?>
<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="script.js"></script>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>

<?php

require_once('connectvars.php');
session_start();
$error_msg = "";

//comprueba si coincide user y passw
if (!isset($_SESSION['user_id'])) {
    if (isset($_POST['submit'])) {
        $dbc = mysqli_connect(DBHOST,RT,CLAVE,DB);  
        $usuario= mysqli_real_escape_string($dbc, trim($_POST['user_name']));
        $passw = mysqli_real_escape_string($dbc, trim($_POST['password']));
        if (isset($usuario) && isset($passw)) {
            $query = "SELECT * FROM usuario WHERE nombre='".$usuario."' AND passw='".$passw."'";
            $data = mysqli_query($dbc, $query);
            if (mysqli_num_rows($data) == 1) {
                $row = mysqli_fetch_array($data);
                $_SESSION['user_id']= $row['idu'];
                $_SESSION['user_name']= $row['nombre'];
                setcookie('user_id', $row['idu'],time()+2600000);
                setcookie('user_name', $row['nombre'],time()+2600000);
                $home_url = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) .     '/index.php';
                header('Location: ' . $home_url);
            }else {
                $error_msg = 'Lo sentimos, usuario y contrase&ntilde;a no son correctos';
            }
        }
        else {
            $error_msg = 'introduzca usuario y contrase&ntilde;a';
        }
    }
}
?>
<?php 
$page_title = 'Log in';

//formulario de autentificacion
?>
    <body>

<?php
    if (empty($_SESSION['user_id'])) {
        echo '<p class="error">' . $error_msg . '</p>';
?>
       <div class="login">
        <form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>">
            <fieldset>
                <legend>Introduzca nombre de usuario y contrase&ntilde;a</legend>
                <label for="user_name">Nombre de usuario:</label>
                <input type="text" id="user_name" name="user_name"
                       value="<?php if (!empty($usuario)) echo $usuario; ?>" /><br />
                <label for="password">Contrase&ntilde;a:</label>
                <input type="password" id="password" name="password" />
            </fieldset>
            <input type="submit" value="Log In" name="submit" />
                   <a href="signup.php">Registrarse</a>

        </form>
<?php
    }else {
        //si existe id de session redirige a index
$redirect = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) . '/index.php';
   
                header('Location: ' . $redirect);
      }

?>
</div>
<footer><p class="footer">Copyright &copy;1908 Team Manager Enterprises, Inc.</p>
</footer>
</body>
</html>