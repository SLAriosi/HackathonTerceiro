<section>
    <div class="row banner">
        <div class="col-12 d-flex justify-content-center align-items-center flex-column" style="background-color: rgba(0,0,0, 0.7);">
            <h1 class="text-white text-center">AlfaCare: Vacinação Domiciliar ao seu Alcance</h1><br>
            <div class="text-white text-center fs-5 w-100 w-md-50 px-3 px-md-0">
                Seja bem-vindo à AlfaCare, sua opção em vacinação domiciliar. Com um compromisso sólido com a saúde e o bem-estar, oferecemos conveniência e segurança através de uma equipe especializada. Agende sua vacina conosco e garanta proteção com qualidade e conforto para você e sua família.
            </div>
        </div>
    </div>
</section>

<div style="background-color: #0d6efd; background-image: linear-gradient(180deg, #0d6efd 0%, #5498ff 100%);">
    <div class="container py-5">
        <h3 class="text-light text-center">Campanhas de Vacinação</h3>
        <h5 class="text-light text-center pb-5">Junte-se a nós na campanha de vacinação e proteja sua saúde e a de seus entes queridos. Faça parte desse movimento para um futuro mais seguro e saudável!</h5>
        <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
            </div>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="./assets/slide1.jpg" class="d-block w-100" alt="Slide 1">
                    <div class="carousel-caption d-none d-md-block"></div>
                </div>
                <div class="carousel-item">
                    <img src="./assets/slide2.jpg" class="d-block w-100" alt="Slide 2">
                    <div class="carousel-caption d-none d-md-block"></div>
                </div>
                <div class="carousel-item">
                    <img src="./assets/slide3.jpg" class="d-block w-100" alt="Slide 3">
                    <div class="carousel-caption d-none d-md-block"></div>
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

<div class="bg-light py-5">
    <div class="container">
        <div class="titulo text-center">
            <h1 class="text-primary">Vacinas Disponíveis</h1>
        </div>
        <div class="card mb-5">
            <div class="card-body table-responsive">
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
                                <td><?= htmlspecialchars($dado["nome"]) ?></td>
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
