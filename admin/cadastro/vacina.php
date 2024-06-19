<?php
$nome = filter_input(INPUT_POST, "nome", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
$dataInicio = filter_input(INPUT_POST, "dataInicio", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;
$dataTermino = filter_input(INPUT_POST, "dataTermino", FILTER_SANITIZE_SPECIAL_CHARS) ?? NULL;


echo $nome;
echo "<br>";
echo $dataInicio;
echo "<br>";
echo $dataTermino;
?>

<div class="container my-5 ">
    <div class="row d-flex justify-content-center align-items-center mb-5">
        <div class="col-lg-6 col-md-12 mt-4 mt-lg-0 px-lg-5">
            <h3 class="text-primary">Vacina</h3>
            <form method="POST" action="">
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
                        <td>Nome da vacina</td>
                        <td>Data de início</td>
                        <td>Data de Término</td>
                        <td>Opções</td>
                    </tr>
                </thead>
                <tbody>
                    <?php
                    $sql = "SELECT * FROM `vacina`";

                    $consulta = $pdo->prepare($sql);
                    $consulta->execute();
                    $dados = $consulta->fetchAll(PDO::FETCH_OBJ);

                    // Utilizado o foreach para selecionar os dados a serem exibidos
                    foreach ($dados as $dado) {
                        // Formatando as datas para o formato dd/mm/yyyy
                        $data_inicio = new DateTime($dado->data_inicio);
                        $data_termino = new DateTime($dado->data_termino);
                    ?>
                        <!-- Criação das TR -->
                        <tr>
                            <!-- Exibindo os objetos dos dados -->
                            <td><?= htmlspecialchars($dado->nome) ?></td>
                            <td><?= $data_inicio->format('d/m/Y') ?></td>
                            <td><?= $data_termino->format('d/m/Y') ?></td>
                            <td>
                                <a class="bi bi-pencil-square text-primary" style="font-size: 25px;" href="cadastros/categorias/<?= $dado->id ?>"><img src="_arquivos/updateLogo.png" alt=""></a>
                                <a class="bi bi-trash-fill text-danger"  style="font-size: 25px;" href="excluir/categorias/<?= $dado->id ?>"></a>
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