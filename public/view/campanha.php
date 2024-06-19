<div style="background-color: #0d6efd;
    background-image: linear-gradient(180deg, #0d6efd 0%, #5498ff 100%);">
    <div class="container py-5">
        <h3 class="text-light">Campanhas de Vacinação</h3>
        <h5 class="text-light pb-5">Junte-se a nós na campanha de vacinação e proteja sua saúde e a de seus entes queridos. Faça parte desse movimento para um futuro mais seguro e saudável!</h5>
        <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
            </div>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="./assets/slide1.jpg" class="d-block w-100" alt="Slide 1">
                    <div class="carousel-caption d-none d-md-block">
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="./assets/slide2.jpg" class="d-block w-100" alt="Slide 2">
                    <div class="carousel-caption d-none d-md-block">
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="./assets/slide3.jpg" class="d-block w-100" alt="Slide 3">
                    <div class="carousel-caption d-none d-md-block">
                    </div>
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Anterior</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Próximo</span>
            </button>
        </div>
    </div>
</div>

<div style="padding: 150px;" class="bg-light">
    <div class="container">

        <div class="titulo">
            <h1 class="text-primary">Vacinas Dísponiveis</h1>
        </div>
        <div class="card mb-5">
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
    </div>
</div>