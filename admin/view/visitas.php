<div class="container">
    <div class="row container-visitas">
        <div class="col-5">
            <div class="mr-5">
                <div class="">
                    <h5 class="text-center fs-2 pb-3">Agendamento de Visitas Domiciliar</h5>
                    <div class="texto-visita">Estamos comprometidos com o bem-estar dos nossos idosos, oferecendo um serviço de agendamento para vacinação domiciliar.<br> Este serviço é especialmente destinado àqueles que possuem dificuldades de locomoção ou condições de saúde que impossibilitam a visita aos centros de vacinação.<br> O agendamento é simples e rápido, permitindo que nossos profissionais de saúde cheguem até você com todo o cuidado necessário.<br> Proteja a saúde dos seus entes queridos sem sair de casa. Agende agora e garanta a proteção contra doenças através da vacinação no conforto do seu lar.</div>
                </div>
                <div class="pt-5 text-center">
                    <img class="rounded img-fluid" src="./assets/visitaVacina.jpg" alt="">
                </div>
            </div>
        </div>
        <div class="col-7" style="padding-left: 100px;">
            <div class="">
                <form method="POST" action="">
                    <div class="mb-3">
                        <label for="municipio" class="form-label">Município</label>
                        <select class="form-select" id="municipio">
                            <option selected>Selecione um município</option>
                            <!-- Adicione as opções de municípios aqui -->
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="unidade" class="form-label">Escolha uma unidade</label>
                        <select class="form-select" id="unidade">
                            <option selected>Escolha uma unidade</option>
                            <!-- Adicione as opções de unidades aqui -->
                        </select>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="mb-3">
                                <label for="dia" class="form-label">Escolha o dia</label>
                                <div class="input-group">
                                    <input type="date" class="form-control" id="dia">
                                    <span class="input-group-text"><i class="bi bi-calendar"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="col">

                            <div class="mb-3">
                                <label for="horario" class="form-label">Escolha o horário</label>
                                <select class="form-select" id="horario">
                                    <option selected>Escolha o horário</option>
                                    <option>09:00</option>
                                    <option>12:00</option>
                                    <option>15:00</option>
                                    <!-- Adicione as opções de horários aqui -->
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="nome" class="form-label">Nome</label>
                        <input type="text" class="form-control" id="nome" placeholder="Nome">
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">E-mail</label>
                        <input type="email" class="form-control" id="email" placeholder="E-mail">
                    </div>
                    <div class="row mb-3">
                        <div class="col">
                            <label for="cpf" class="form-label">CPF</label>
                            <input type="text" class="form-control" id="cpf" placeholder="Somente números">
                        </div>
                        <div class="col">
                            <label for="dataNascimento" class="form-label">Data de Nascimento</label>
                            <input type="text" class="form-control" id="dataNascimento" placeholder="dd/mm/aaaa (Opcional)">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col">
                            <label for="telefoneFixo" class="form-label">Telefone Fixo</label>
                            <input type="text" class="form-control" id="telefoneFixo" placeholder="(xx)xxxx-xxxx (Opcional)">
                        </div>
                        <div class="col">
                            <label for="celular" class="form-label">Celular</label>
                            <input type="text" class="form-control" id="celular" placeholder="(xx)9xxxx-xxxx (Opcional)">
                        </div>
                    </div>
                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary btn-block">Confirmar Agendamento</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</div>
</div>