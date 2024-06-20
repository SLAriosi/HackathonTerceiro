<header style="box-shadow: 0px 10px 15px 0px rgba(0,0,0,0.10);" class="bg-dark">
    <div class="container d-flex justify-content-between">
        <div class="logo d-flex align-items-center">
            <a href="../public/view/campanha"><img style="width: 120px;" class="" src="./assets/logo.png" alt=""></a>
            <h3 style="margin-bottom: 0 !important;" class="text-white">Painel de Administração</h3>
        </div>
        <nav class="navbar">
            <div class="container-fluid">
                <div class="navbar-nav d-flex flex-row mx-3">
                    <a class="nav-link mx-2 text-white" href="list/vacina">Lista de vacinas</a>
                </div>
                <ul style="margin-bottom: 0; padding-left: 20px;" class="list-unstyled d-flex justify-content-center align-item-center bg-primary p-2 rounded">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle buttonSair" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="#fff" width="25" height="25" viewBox="0 0 24 24">
                                <path d="M12 0c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12-5.373-12-12-12zm7.753 18.305c-.261-.586-.789-.991-1.871-1.241-2.293-.529-4.428-.993-3.393-2.945 3.145-5.942.833-9.119-2.489-9.119-3.388 0-5.644 3.299-2.489 9.119 1.066 1.964-1.148 2.427-3.393 2.945-1.084.25-1.608.658-1.867 1.246-1.405-1.723-2.251-3.919-2.251-6.31 0-5.514 4.486-10 10-10s10 4.486 10 10c0 2.389-.845 4.583-2.247 6.305z" />
                            </svg>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item text-danger" href="disconnect.php">Sair</a></li>
                        </ul>
                    </li>
                    <div style="font-size: 15px; padding-top: 3px;" class="text-white px-2">Bem vindo, <?= $_SESSION["usuario"]["login"] ?></div>
                </ul>

            </div>
        </nav>
    </div>
</header>