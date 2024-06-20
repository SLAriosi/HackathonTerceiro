<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $nome = filter_input(INPUT_POST, "nome", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $nomeResponsavel = filter_input(INPUT_POST, "nomeResponsavel", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $vacina = filter_input(INPUT_POST, "vacina", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $data = filter_input(INPUT_POST, "data", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $horario = filter_input(INPUT_POST, "horario", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $cpf = filter_input(INPUT_POST, "cpf", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $dataNascimento = filter_input(INPUT_POST, "dataNascimento", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $telefone = filter_input(INPUT_POST, "telefone", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $data = filter_input(INPUT_POST, "data", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $cep = filter_input(INPUT_POST, "cep", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;

    echo $nome;
    echo "<br>";
    echo $vacina;
    echo "<br>";
    echo $data;
    echo "<br>";
    echo $horario;
}
