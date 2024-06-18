function togglePassword() {
    var passwordField = document.getElementById("logpass");
    var passwordFieldType = passwordField.type === "password" ? "text" : "password";
    passwordField.type = passwordFieldType;

    var eyeOpenIcon = document.querySelector(".eye-open");
    var eyeClosedIcon = document.querySelector(".eye-closed");

    if (passwordFieldType === "text") {
        eyeOpenIcon.style.display = "none";
        eyeClosedIcon.style.display = "block";
    } else {
        eyeOpenIcon.style.display = "block";
        eyeClosedIcon.style.display = "none";
    }
}
function mostrarToast(mensagem, classe) {
    // Seleciona o elemento do toast
    var toastElement = document.getElementById('toast');

    // Atualiza a mensagem do corpo do toast
    var toastBody = toastElement.querySelector('.toast-body');
    toastBody.textContent = mensagem;

    // Adiciona a classe de estilo ao toast
    toastElement.classList.remove('hide');
    toastElement.classList.add('show', classe);

    // Cria um novo objeto Toast a partir do elemento toast e exibe-o
    var bsToast = new bootstrap.Toast(toastElement);
    bsToast.show();
}