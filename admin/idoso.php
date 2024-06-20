<?php
$nome = $_POST["nome"] ?? NULL;
$nomeResponsavel = $_POST["nomeResponsavel"] ?? NULL;
$celularResponsavel = $_POST["celularResponsavel"] ?? NULL;
$cep = $_POST["cep"] ?? NULL;
$numeroCasa = $_POST["numeroCasa"] ?? NULL;
$rua = $_POST["rua"] ?? NULL;
$bairro = $_POST["bairro"] ?? NULL;
$cidade = $_POST["cidade"] ?? NULL;
$email = $_POST["email"] ?? NULL;
$cpf = $_POST["cpf"] ?? NULL;
$dataNascimento = $_POST["dataNascimento"] ?? NULL;
$cartaoSus = $_POST["cartaoSus"] ?? NULL;
$celular = $_POST["celular"] ?? NULL;
$condicoes = $_POST["condicoes"] ?? NULL;

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $cep = preg_replace('/\D/', '', $cep);
    $urlCep = "https://viacep.com.br/ws/$cep/json/";
    $dados = json_decode(file_get_contents($urlCep));

    $rua = $dados->logradouro;
    $bairro = $dados->bairro;
    $cidade = $dados->localidade;
}
?>


<div class="container my-5">
    <div class="row">
        <div class="col-lg-5 col-md-12">
            <div class="mr-lg-5">
                <div>
                    <h5 class="text-center fs-2 pb-3">Cadastro de Idoso</h5>
                    <div class="texto-visita">
                        Estamos realizando o cadastro de idosos em nosso banco de dados para garantir um processo eficiente de vacinação. Este registro nos permitirá organizar e priorizar a imunização, garantindo que cada idoso receba a vacina no momento adequado e conforme as diretrizes de saúde pública.<br>
                        Ao preencher estas informações, estaremos assegurando um acompanhamento mais preciso de cada pessoa, levando em consideração suas condições de saúde e necessidades individuais. Assim, podemos assegurar que a vacinação seja feita de maneira segura e eficaz para todos.<br>
                        Agradecemos pela sua colaboração neste importante processo de cuidado com nossa comunidade idosa.
                    </div>
                </div>
                <div class="pt-5 text-center">
                    <img class="rounded img-fluid" src="./assets/vacinacao1.jpg" alt="">
                </div>
            </div>
        </div>
        <div class="col-lg-7 col-md-12 mt-4 mt-lg-0 px-lg-5">
            <form id="formCadastro" method="POST" action="">
                <div class="mb-3">
                    <label for="nome" class="form-label">Nome Completo</label>
                    <input name="nome" type="text" class="form-control" id="nome" placeholder="Nome" required>
                </div>
                <div class="mb-3">
                    <label for="nomeResponsavel" class="form-label">Nome Responsável (se houver)</label>
                    <input name="nomeResponsavel" type="text" class="form-control" id="nomeResponsavel" placeholder="Nome responsável">
                </div>
                <div class="mb-3">
                    <label for="celularResponsavel" class="form-label">Celular Responsável</label>
                    <input name="celularResponsavel" type="text" class="form-control" id="celularResponsavel" placeholder="(99)99999-9999">
                </div>
                <div class="row mb-3">
                    <div class="col-md-6 mb-3 mb-md-0">
                        <label for="cep" class="form-label">CEP</label>
                        <input name="cep" type="text" class="form-control" id="cep" placeholder="99999-999">
                    </div>
                    <div class="col-md-6">
                        <label for="numeroCasa" class="form-label">Número</label>
                        <input name="numeroCasa" type="text" class="form-control" id="numeroCasa" placeholder="">
                    </div>
                </div>
                <div class="mb-3">
                    <label for="rua" class="form-label">Rua</label>
                    <input name="rua" type="text" class="form-control" id="rua" disabled>
                </div>
                <div class="row mb-3">
                    <div class="col-md-6 mb-3 mb-md-0">
                        <label for="bairro" class="form-label">Bairro</label>
                        <input name="bairro" type="text" class="form-control" id="bairro" disabled>
                    </div>
                    <div class="col-md-6">
                        <label for="cidade" class="form-label">Cidade</label>
                        <input name="cidade" type="text" class="form-control" id="cidade" disabled>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">E-mail</label>
                    <input name="email" type="email" class="form-control" id="email" placeholder="E-mail">
                </div>
                <div class="row mb-3">
                    <div class="col-md-6 mb-3 mb-md-0">
                        <label for="cpf" class="form-label">CPF</label>
                        <input name="cpf" type="text" class="form-control" id="cpf" placeholder="999.999.999-99">
                    </div>
                    <div class="col-md-6">
                        <label for="dataNascimento" class="form-label">Data de Nascimento</label>
                        <input name="dataNascimento" type="text" class="form-control" id="dataNascimento" placeholder="dd/mm/aaaa">
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col-md-6 mb-3 mb-md-0">
                        <label for="cartaoSus" class="form-label">Cartão SUS</label>
                        <input name="cartaoSus" type="text" class="form-control" id="cartaoSus" placeholder="0000.0000.0000.0000">
                    </div>
                    <div class="col-md-6">
                        <label for="celular" class="form-label">Celular</label>
                        <input name="celular" type="text" class="form-control" id="celular" placeholder="(99)99999-9999">
                    </div>
                </div>
                <div class="mb-3">
                    <label for="condicoes" class="form-label">Condições de saúde relevantes</label>
                    <textarea name="condicoes" class="form-control" id="condicoes" rows="3"></textarea>
                </div>
                <div class="d-grid">
                    <button type="submit" class="btn btn-primary btn-block">Confirmar Agendamento</button>
                </div>
            </form>
        </div>
    </div>
</div>