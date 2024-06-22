<?php
//função para mostrar a janela de erro
function mensagemErro($msg)
{
?>
    <script>
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: '<?= $msg ?>',
        }).then((result) => {
            history.back();
        })
    </script>
<?php

    exit;
} ?>


<?php
function mensagemSucesso($msg)
{
?>
    <script>
        Swal.fire({
            icon: 'success',
            title: 'Sucesso!',
            text: '<?= $msg ?>',
        }).then((result) => {
            history.back();
        })
    </script>
<?php

    exit;
} ?>
