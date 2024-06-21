<div class="container my-5">
    <div class="row">
        <div class="col-lg-5 col-md-12">
            <div class="mr-lg-5">
                <div>
                    <h5 class="text-center fs-2 pb-3">Agendamento de Visitas Domiciliar</h5>
                    <div class="texto-visita">
                        Estamos comprometidos com o bem-estar dos nossos idosos, oferecendo um serviço de agendamento para vacinação domiciliar.<br>
                        Este serviço é especialmente destinado àqueles que possuem dificuldades de locomoção ou condições de saúde que impossibilitam a visita aos centros de vacinação.<br>
                        O agendamento é simples e rápido, permitindo que nossos profissionais de saúde cheguem até você com todo o cuidado necessário.<br>
                        Proteja a saúde dos seus entes queridos sem sair de casa. Agende agora e garanta a proteção contra doenças através da vacinação no conforto do seu lar.
                    </div>
                </div>
                <div class="pt-5 text-center">
                    <img class="rounded img-fluid" src="./assets/visitaVacina.jpg" alt="">
                </div>
            </div>
        </div>
        <div class="col-lg-7 col-md-12 mt-4 mt-lg-0 px-lg-5">
            <form method="POST" action="salvar/visitas.php">
                <div class="mb-3">
                    <label for="vacina" class="form-label">Vacinas</label>
                    <select name="vacina" class="form-select" id="vacina">
                        <option selected>Selecione a vacina</option>
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
                        ?>
                            <option><?= $dado["nome"] ?></option>
                        <?php
                        }
                        ?>
                    </select>
                </div>
                <div class="row mb-3">
                    <div class="col-md-6 mb-3 mb-md-0">
                        <label for="data" class="form-label">Escolha o dia</label>
                        <div class="input-group">
                            <input name="data" type="date" class="form-control" id="data">
                            <span class="input-group-text"><i class="bi bi-calendar"></i></span>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <label for="horario" class="form-label">Escolha o horário</label>
                        <select name="horario" class="form-select" id="horario">
                            <option selected>Escolha o horário</option>
                            <option>09:00</option>
                            <option>13:00</option>
                            <option>16:00</option>
                        </select>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="nome" class="form-label">Nome</label>
                    <input name="nome" required type="text" class="form-control" id="nome" placeholder="Nome">
                </div>
                <div class="row mb-3">
                    <div class="col-md-6 mb-3 mb-md-0">
                        <label for="cpf" class="form-label">CPF</label>
                        <input name="cpf" required type="text" class="form-control" id="cpf" placeholder="999.999.999-99">
                    </div>
                    <div class="col-md-6">
                        <label for="dataNascimento" class="form-label">Data de Nascimento</label>
                        <input name="dataNascimento" required type="text" class="form-control" id="dataNascimento" placeholder="dd/mm/aaaa">
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col-md-6 mb-3 mb-md-0">
                        <label for="telefone" class="form-label">Telefone</label>
                        <input name="telefone" required type="text" class="form-control" id="telefone" placeholder="(99)99999-9999">
                    </div>
                    <div class="col-md-6 mb-3 mb-md-0">
                        <label for="cep" class="form-label">CEP</label>
                        <input name="cep" required type="text" class="form-control" id="cep" placeholder="99999-999">
                    </div>
                </div>
                
                <div class="d-grid mt-3">
                    <button type="submit" class="btn btn-block btn-primary">Confirmar Agendamento</button>
                </div>
            </form>
        </div>
    </div>
</div>