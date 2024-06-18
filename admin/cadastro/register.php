<?php
$nome = $_POST["nome"] ?? NULL;
$email = $_POST["email"] ?? NULL;
$login = $_POST["login"] ?? NULL;
$senha = $_POST["senha"] ?? NULL;

if ($senha) {
    $senha = password_hash($senha, PASSWORD_DEFAULT);
}
if ($_POST) {
    $sql = "INSERT INTO `usuario`(`nome`, `email`, `login`, `senha`) VALUES (:nome, :email, :login, :senha)";

    $consulta = $pdo->prepare($sql);
    $consulta->bindParam(":nome", $nome);
    $consulta->bindParam(":email", $email);
    $consulta->bindParam(":login", $login);
    $consulta->bindParam(":senha", $senha);
    
    $consulta->execute();

    $_SESSION["usuario"] = [
        "nome" => $nome,
        "login" => $login
    ];
    echo "<script>window.location.href='view/login'</script>";
    exit;
}

?>

<div>
    <form method="POST" action="" class="form-c">
        <p class="title-c">Cadastre-se </p>
        <p class="message-c">Faça o cadastro para ter acesso.</p>

        <label>
            <input name="nome" required="" placeholder="" type="text" class="input-c">
            <span>Nome</span>
        </label>
        <label>
            <input name="email" required="" placeholder="" type="email" class="input-c">
            <span>Email</span>
        </label>
        <label>
            <input name="login" required="" placeholder="" type="text" class="input-c">
            <span>Login</span>
        </label>
        <label>
            <input name="senha" required="" placeholder="" type="password" class="input-c">
            <span>Senha</span>
        </label>
        <button class="submit-c">Cadastrar</button>
        <p class="signin-c">Já possui uma conta? <a href="view/home">Login</a> </p>
    </form>
</div>