<div class="container">
        <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
            <ol class="carousel-indicators">
                <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
            </ol>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="../img/slide1.jpeg" class="d-block w-100" alt="Slide 1">
                    <div class="carousel-caption d-none d-md-block">

                    </div>
                </div>
                <div class="carousel-item">
                    <img src="../img/slide2.jpeg" class="d-block w-100" alt="Slide 2">
                    <div class="carousel-caption d-none d-md-block">

                    </div>
                </div>
                <div class="carousel-item">
                    <img src="../img/slide3.jpeg" class="d-block w-100" alt="Slide 3">
                    <div class="carousel-caption d-none d-md-block">

                    </div>
                </div>
            </div>
            <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Anterior</span>
            </a>
            <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Próximo</span>
            </a>
        </div>
    </div>

    <div class="titulo">
        <h1>Vacinas Dísponiveis</h1>
    </div>
    <div class="card">
        <div class="card-body">
            <table class="table table-bordered table-hover table-striped">
                <thead>
                    <tr>
                        <td>Nome da vacina</td>
                        <td>Data de início</td>
                        <td>Data de Término</td>
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
                        </tr>
                    <?php
                    }
                    ?>
                </tbody>
            </table>
        </div>
    </div>