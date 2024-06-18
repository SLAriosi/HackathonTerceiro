<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $nome = $_POST['nome'];
    $email = $_POST['email'];
    $senha = $_POST['senha'];

    $url = 'http://localhost:3001/usuarios/';
    $data = array(
        'nome' => $nome,
        'email' => $email,
        'senha' => $senha
    );

    $options = array(
        'http' => array(
            'header'  => "Content-type: application/json\r\n",
            'method'  => 'POST',
            'content' => json_encode($data)
        )
    );

    $context  = stream_context_create($options);
    $result = file_get_contents($url, false, $context);

    if ($result === FALSE) {
        echo 'Erro ao enviar dados para a API';
    } else {
        echo 'Dados enviados com sucesso!';
    }
}

?>

<form method="POST" action="">
    <input type="text" name="nome" placeholder="Digite seu nome">
    <input type="email" name="email" placeholder="Digite seu email">
    <input type="password" name="senha" placeholder="Digite sua senha">
    <select id="cars" name="cars">
        <?php 
            for ($i=0; $i < 5; $i++) {?>    
            <option value="volvo"><?php echo "numero" ?></option>
        <?php 
            }
        ?>
        
    </select>
    <input class="botao" type="submit" value="Enviar">
</form>