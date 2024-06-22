<?php
require ("../model/config.php");
require ("../functions.php");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $nome = filter_input(INPUT_POST, "nome", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $vacina = filter_input(INPUT_POST, "vacina", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $data = filter_input(INPUT_POST, "data", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $horario = filter_input(INPUT_POST, "horario", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $cpf = filter_input(INPUT_POST, "cpf", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $telefone = filter_input(INPUT_POST, "telefone", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $cep = filter_input(INPUT_POST, "cep", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $agente = filter_input(INPUT_POST, "agente", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $agente = intval($agente);
    try {
        $stmt = $pdo->prepare("SELECT cpf FROM idoso WHERE cpf = :cpf");
        $stmt->bindParam(':cpf', $cpf);
        $stmt->execute();

        if ($stmt->rowCount() > 0) {
            $data = array(
                'nome' => $nome,
                'vacina' => $vacina,
                'data' => $data,
                'horario' => $horario,
                'cpf' => $cpf,
                'telefone' => $telefone,
                'cep' => $cep,
                'agente-saude_id' => $agente
            );

            $options = array(
                'http' => array(
                    'header'  => "Content-type: application/json\r\n",
                    'method'  => 'POST',
                    'content' => json_encode($data)
                )
            );

            $context  = stream_context_create($options);
            $url = 'http://localhost:3001/api/agenda';
            $result = file_get_contents($url, false, $context);

            if ($result === FALSE) {
                mensagemErro('Erro ao enviar dados para a API');
            } else {
                mensagemSucesso('Agendamento realizado!');
            }
        } else {
            mensagemErro('CPF não encontrado na base de dados. Vá até a unidade mais próxima e solicite o cadastro do Idoso.');
        }
    } catch (PDOException $e) {
        echo $e->getMessage();
    }
}