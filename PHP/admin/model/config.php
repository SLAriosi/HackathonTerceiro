<?php
    $host = "localhost";
    $user  = "root";
    $password = "";
    $database  = "alfacare";

    try {
        $pdo = new PDO("mysql:host={$host};dbname={$database};port=3306;charset=utf8;",$user,$password);
    } catch (Exception $e) {
        echo "<p>Erro ao tentar conectar</p>";
        echo $e->getMessage();
    }