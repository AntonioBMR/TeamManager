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
    <body>

        <?php
require_once('connectvars.php');

session_start();
$dbc = mysqli_connect(DBHOST,RT,CLAVE,DB);
$username='';
$password='';
$password1='';
if (isset($_POST['submit'])) {
    //quita espacios y caracteres raros de los input
    $username= mysqli_real_escape_string($dbc,trim($_POST['username']));
    $password= mysqli_real_escape_string($dbc, trim($_POST['password']));
    $password1= mysqli_real_escape_string($dbc, trim($_POST['password1']));
    if (!empty($username) && !empty($password1) && !empty($password) &&
        ( $password==$password1 )) {
        //si no estan vacios comprueba que no este creados el nombre d usuario
        $query = "SELECT * FROM usuario WHERE nombre = '$username'";
        $data = mysqli_query($dbc, $query);
        if (mysqli_num_rows($data) == 0) {
            //si no se repite lo crea y redirige al logeo
            $query = "INSERT INTO usuario (nombre, passw) VALUES " .
                "('$username', '$password')";
            mysqli_query($dbc, $query);
            $_SESSION['user_name']=$username;
            mysqli_close($dbc);
            $home_url = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) .     '/index.php';
            header('Location: ' . $home_url);

        }else {
            //si se repite lanza aviso
            $sign = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']) .     '/signup.php';
        ?><script>

        alert("Ya existe ese usuario");
        window.location.href = "<?php echo $sign; ?>"
        </script> <?php
        }
    }else {
        echo '<p class="error">Rellene los datos</p>';
    }
}else{
    mysqli_close($dbc);
    $page_title = 'Registrate';
//formulario de registro
        ?>
        <form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>">
            <h3>Introduzca Nombre de Usuario y una contrase&ntilde;a .</h3>
            <fieldset>
                <legend>Registro de usuario</legend>
                <label for="username">Nombre de usuario:</label>
                <input type="text" id="username" name="username" required
                       value="<?php if (!empty($username )) echo $username ; ?>" /><br />
                <label for="password">Contrase&ntilde;a:</label>
                <input type="password" id=" password" name="password" required/><br />
                <label for=" password1">Repita contrase&ntilde;a:</label>
                <input type="password" id="password1" name="password1" required /><br />
            </fieldset>
            <input type="submit" value="Sign Up" name="submit" />
        </form>
        <?php 
}
require_once('footer.php');
        ?>
