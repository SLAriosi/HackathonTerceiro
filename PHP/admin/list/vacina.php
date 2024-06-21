<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $nome = filter_input(INPUT_POST, "nome", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $data_campanha_inicio = filter_input(INPUT_POST, "dataInicio", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
    $data_campanha_fim = filter_input(INPUT_POST, "dataTermino", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;

    $url = 'http://localhost:3001/api/vacina';
    $method = 'POST';

    if ($id) {
        $url .= '/' . $id;
        $method = 'PUT';
    }

    $data = array(
        'nome' => $nome,
        'data_campanha_inicio' => $data_campanha_inicio,
        'data_campanha_fim' => $data_campanha_fim
    );

    $ch = curl_init($url);

    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json'));
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST, $method);
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));

    $result = curl_exec($ch);
    $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);

    if (curl_errno($ch)) {
        mensagemErro('Erro ao enviar dados para a API: ' . curl_error($ch));
    } else {
        if ($httpCode >= 200 && $httpCode < 300) {
            mensagemSucesso("Cadastrado ou Atualizado com sucesso!");
        } else {
            mensagemErro("Erro ao Cadastrar/Atualizar a vacina!");
        }
    }

    curl_close($ch);
}

?>

<div class="container my-5 ">
    <div class="row d-flex justify-content-center align-items-center mb-5">
        <div class="col-lg-6 col-md-12 mt-4 mt-lg-0 px-lg-5">
            <h3 class="text-primary">Vacina</h3>
            <form method="POST" action="">
                <div class="mb-3">
                    <label for="id" class="form-label">ID</label>
                    <input name="id" required type="text" class="form-control" id="id" placeholder="" disabled value="<?php if ($id) echo $id ?>">
                </div>
                <div class="mb-3">
                    <label for="nome" class="form-label">Nome</label>
                    <input name="nome" required type="text" class="form-control" id="nome" placeholder="Nome">
                </div>
                <div class="row mb-3">
                    <div class="col-md-6 mb-3 mb-md-0">
                        <label for="dataInicio" class="form-label">Escolha a data de inicio</label>
                        <div class="input-group">
                            <input name="dataInicio" required type="date" class="form-control" id="dataInicio">
                            <span class="input-group-text"><i class="bi bi-calendar"></i></span>
                        </div>
                    </div>
                    <div class="col-md-6 mb-3 mb-md-0">
                        <label for="dataTermino" class="form-label">Escolha a data de término</label>
                        <div class="input-group">
                            <input name="dataTermino" required type="date" class="form-control" id="dataTermino">
                            <span class="input-group-text"><i class="bi bi-calendar"></i></span>
                        </div>
                    </div>
                </div>
                <div class="d-grid mt-3">
                    <button type="submit" class="btn btn-block btn-primary">Cadastrar Vacina</button>
                </div>
            </form>
        </div>
    </div>
    <div class="card mb-5">
        <div class="card-body">
            <table class="table table-bordered table-hover table-striped">
                <thead>
                    <tr>
                        <td>ID</td>
                        <td>Nome da vacina</td>
                        <td>Data de início</td>
                        <td>Data de Término</td>
                        <td>Opções</td>
                    </tr>
                </thead>
                <tbody>
                    <?php
                    $url = 'http://localhost:3001/api/vacina';

                    $response = file_get_contents($url);
                    if ($response === FALSE) {
                        die('Erro ao obter dados da API');
                    }
                    $dados = json_decode($response, true);

                    if (json_last_error() !== JSON_ERROR_NONE) {
                        die('Erro ao decodificar JSON');
                    }
                    foreach ($dados as $dado) {
                        $data_inicio = new DateTime($dado["data_campanha_inicio"]);
                        $data_termino = new DateTime($dado["data_campanha_fim"]);
                    ?>
                        <tr>
                            <td><?= $dado["id"] ?></td>
                            <td><?= htmlspecialchars($dado["nome"]) ?></td>
                            <td><?= $data_inicio->format('d/m/Y') ?></td>
                            <td><?= $data_termino->format('d/m/Y') ?></td>
                            <td>
                                <a class="bi bi-pencil-square text-primary" style="font-size: 25px;" href="list/vacina/<?= $dado["id"] ?>"></a>
                                <a class="bi bi-trash-fill text-danger" style="font-size: 25px;" href="javascript:excluir(<?= $dado["id"] ?>)"></a>
                            </td>
                        </tr>
                    <?php
                    }
                    ?>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script>
    function excluir(id) {
        Swal.fire({
            title: "Você deseja realmente excluir este item?",
            showCancelButton: true,
            cancelButtonText: 'Cancelar',
            confirmButtonText: 'Excluir'
        }).then((result) => {
            if (result.isConfirmed) {
                location.href = 'excluir/vacina/' + id;
            }
        });
    }
</script>