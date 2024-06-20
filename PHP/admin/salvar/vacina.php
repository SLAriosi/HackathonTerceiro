<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $nome = filter_input(INPUT_POST, "nome", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $data_campanha_inicio = filter_input(INPUT_POST, "dataInicio", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $data_campanha_fim = filter_input(INPUT_POST, "dataTermino", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;

    $url = 'http://localhost:3001/api/vacina';
    
    $data = array(
        'nome' => $nome,
        'data_campanha_inicio' => $data_campanha_inicio,
        'data_campanha_fim' => $data_campanha_fim
    );

    $options = array(
        'http' => array(
            'header'  => "Content-type: application/json\r\n",
            'method'  => 'POST',
            'content' => json_encode($data)
        )
    );

    $context  = stream_context_create($options);
    $result = file_get_contents($url, false, $context);

    if ($result === FALSE) {
        echo 'Erro ao enviar dados para a API';
    } else {
        echo "<script>window.location.href='../list/vacina'</script>";
    }
}
?>