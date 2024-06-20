<?php
$login = $_POST["login"] ?? NULL;
$senha = $_POST["password"] ?? NULL;

$loginNotFound = !empty($login);
$senhaNotFound  = !empty($senha);

if ($loginNotFound && $senhaNotFound) {

    $sql = "SELECT `id`, `nome`, `username`, `password` FROM `agente-saude` WHERE `username` = :login";

    $consulta = $pdo->prepare($sql);
    $consulta->bindParam(":login", $login);
    $consulta->execute();
    $dados = $consulta->fetch(PDO::FETCH_OBJ);

    if (!$dados) {
        mensagemErro('Usuário não encontrado!');
    } elseif (!password_verify($senha, $dados->password)) {
        mensagemErro('Usuário ou Senha Incorretos!');
    } else {
        $_SESSION["usuario"] = [
            "nome" => $dados->nome,
            "login" => $dados->username
        ];
        echo "<script>window.location.href='list/vacina'</script>";
        exit;
    }
}

?>

<div class="container-login">
    <div class="card-l">
        <h4 class="title-l">Login</h4>
        <form method="POST" action="">
            <div class="field-l">
                <svg class="input-icon-l" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                    <path d="M19 7.001c0 3.865-3.134 7-7 7s-7-3.135-7-7c0-3.867 3.134-7.001 7-7.001s7 3.134 7 7.001zm-1.598 7.18c-1.506 1.137-3.374 1.82-5.402 1.82-2.03 0-3.899-.685-5.407-1.822-4.072 1.793-6.593 7.376-6.593 9.821h24c0-2.423-2.6-8.006-6.598-9.819z"></path>
                </svg>
                <input autocomplete="off" id="logemail" placeholder="Usuário" class="input-field-l" name="login" type="text">
            </div>
            <div class="field-l">
                <svg class="input-icon-l" viewBox="0 0 500 500" xmlns="http://www.w3.org/2000/svg">
                    <path d="M80 192V144C80 64.47 144.5 0 224 0C303.5 0 368 64.47 368 144V192H384C419.3 192 448 220.7 448 256V448C448 483.3 419.3 512 384 512H64C28.65 512 0 483.3 0 448V256C0 220.7 28.65 192 64 192H80zM144 192H304V144C304 99.82 268.2 64 224 64C179.8 64 144 99.82 144 144V192z"></path>
                </svg>
                <input autocomplete="off" id="logpass" placeholder="Senha" class="input-field-l" name="password" type="password">
                <button type="button" class="eye-btn" onclick="togglePassword()">
                    <svg class="eye-icon eye-open" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><path d="M15 12c0 1.654-1.346 3-3 3s-3-1.346-3-3 1.346-3 3-3 3 1.346 3 3zm9-.449s-4.252 8.449-11.985 8.449c-7.18 0-12.015-8.449-12.015-8.449s4.446-7.551 12.015-7.551c7.694 0 11.985 7.551 11.985 7.551zm-7 .449c0-2.757-2.243-5-5-5s-5 2.243-5 5 2.243 5 5 5 5-2.243 5-5z"/></svg>
                    <svg class="eye-icon eye-closed" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><path d="M11.885 14.988l3.104-3.098.011.11c0 1.654-1.346 3-3 3l-.115-.012zm8.048-8.032l-3.274 3.268c.212.554.341 1.149.341 1.776 0 2.757-2.243 5-5 5-.631 0-1.229-.13-1.785-.344l-2.377 2.372c1.276.588 2.671.972 4.177.972 7.733 0 11.985-8.449 11.985-8.449s-1.415-2.478-4.067-4.595zm1.431-3.536l-18.619 18.58-1.382-1.422 3.455-3.447c-3.022-2.45-4.818-5.58-4.818-5.58s4.446-7.551 12.015-7.551c1.825 0 3.456.426 4.886 1.075l3.081-3.075 1.382 1.42zm-13.751 10.922l1.519-1.515c-.077-.264-.132-.538-.132-.827 0-1.654 1.346-3 3-3 .291 0 .567.055.833.134l1.518-1.515c-.704-.382-1.496-.619-2.351-.619-2.757 0-5 2.243-5 5 0 .852.235 1.641.613 2.342z"/></svg>
                </button>
            </div>
            <button class="btn-l" type="submit">Entrar</button>
        </form>
    </div>
</div>
