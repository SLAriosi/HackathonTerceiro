<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $nome = filter_input(INPUT_POST, "nome", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $vacina = filter_input(INPUT_POST, "vacina", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $data = filter_input(INPUT_POST, "data", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $horario = filter_input(INPUT_POST, "horario", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $cpf = filter_input(INPUT_POST, "cpf", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $telefone = filter_input(INPUT_POST, "telefone", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $cep = filter_input(INPUT_POST, "cep", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;

    $url = 'http://localhost:3001/api/idosos';
    $response = file_get_contents($url);
    if ($response === FALSE) {
        mensagemErro('Erro ao buscar dados da API');
        exit();
    }

    $idosos = json_decode($response, true);
    if (json_last_error() !== JSON_ERROR_NONE) {
        mensagemErro('Erro ao decodificar JSON');
        exit();
    }

    $cpfExistente = false;
    foreach ($idosos as $idoso) {
        if ($idoso['cpf'] == $cpf) {
            $cpfExistente = true;
            break;
        }
    }

    if ($cpfExistente) {
        $data = array(
            'nome' => $nome,
            'vacina' => $vacina,
            'data' => $data,
            'horario' => $horario,
            'cpf' => $cpf,
            'telefone' => $telefone,
            'cep' => $cep
        );

        $options = array(
            'http' => array(
                'header'  => "Content-type: application/json\r\n",
                'method'  => 'POST',
                'content' => json_encode($data)
            )
        );

        $context  = stream_context_create($options);
        $url = 'http://localhost:3001/api/agendamento';
        $result = file_get_contents($url, false, $context);

        if ($result === FALSE) {
            mensagemErro('Erro ao enviar dados para a API');
        } else {
            mensagemSucesso('Agendamento realizado!');
        }
    } else {
        mensagemErro('CPF não encontrado na base de dados. Vá ate a unidade mais proxima e solicite o cadastro do Idoso');
    }
}
