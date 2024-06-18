<?php
require("../admin/model/config.php");
session_start();

?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hackathon</title>
    <base href="<?php echo "http://" . $_SERVER["HTTP_HOST"] . $_SERVER["SCRIPT_NAME"]; ?>">
    <link rel="stylesheet" href="../admin/css/style.css">
</head>

<body id="page-top">
    <?php
    $usuarioNaoExiste = !isset($_SESSION["usuario"]);

    if ($usuarioNaoExiste) {
        require "view/login.php";
    } else {

        if (isset($_GET['param'])) {
            $page = explode("/", $_GET['param']);

            $pasta = $page[0] ?? NULL;
            $arquivo = $page[1] ?? NULL;
            $id = $page[2] ?? NULL;
            $page = "$pasta/$arquivo";
            
            if (file_exists("$page.php")) {
                require "$page.php";
            } else {
                require "view/error.php";
            }
            require "footer.php";
        }
    }
    ?>
</body>
</html>