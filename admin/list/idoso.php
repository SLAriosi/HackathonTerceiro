<div class="card mb-5">
    <div class="card-body">
        <table class="table table-bordered table-hover table-light table-striped">
            <thead>
                <tr>
                    <td>ID</td>
                    <td>Nome</td>
                    <td>Telefone</td>
                    <td>CEP</td>
                    <td>SUS</td>
                    <td>Condições</td>
                </tr>
            </thead>
            <tbody>
                <?php
                $sql = "SELECT * FROM `vacina`";

                $consulta = $pdo->prepare($sql);
                $consulta->execute();
                $dados = $consulta->fetchAll(PDO::FETCH_OBJ);

                foreach ($dados as $dado) {
                    $data_inicio = new DateTime($dado->data_inicio);
                    $data_termino = new DateTime($dado->data_termino);
                ?>
                    <tr>
                        <td>...</td>
                        <td>...</td>
                        <td>...</td>
                        <td>...</td>
                        <td>...</td>
                        <td>...</td>
                    </tr>
                <?php
                }
                ?>
            </tbody>
        </table>
    </div>
</div>
</div>