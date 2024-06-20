function togglePassword() {
    var passwordField = document.getElementById("logpass");
    var passwordFieldType =
        passwordField.type === "password" ? "text" : "password";
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
    var toastElement = document.getElementById("toast");

    // Atualiza a mensagem do corpo do toast
    var toastBody = toastElement.querySelector(".toast-body");
    toastBody.textContent = mensagem;

    // Adiciona a classe de estilo ao toast
    toastElement.classList.remove("hide");
    toastElement.classList.add("show", classe);

    // Cria um novo objeto Toast a partir do elemento toast e exibe-o
    var bsToast = new bootstrap.Toast(toastElement);
    bsToast.show();
}

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('cep').addEventListener('input', function() {
        var cep = this.value.replace(/\D/g, '');

        if (cep.length === 8) {
            fetch(`https://viacep.com.br/ws/${cep}/json/`)
                .then(response => response.json())
                .then(data => {
                    if (data.erro) {
                        console.log('CEP não encontrado');
                    } else {
                        document.getElementById('rua').value = data.logradouro;
                        document.getElementById('bairro').value = data.bairro;
                        document.getElementById('cidade').value = data.localidade;
                    }
                })
                .catch(error => {
                    console.error('Erro ao buscar o CEP:', error);
                });
        }
    });
});

let currentSlide = 0;

function showSlide(index) {
    const slides = document.querySelectorAll('.carousel-item');
    const totalSlides = slides.length;

    if (index >= totalSlides) {
        currentSlide = 0;
    } else if (index < 0) {
        currentSlide = totalSlides - 1;
    } else {
        currentSlide = index;
    }

    const newTransform = -currentSlide * 100;
    document.querySelector('.carousel-inner').style.transform = `translateX(${newTransform}%)`;
}

function nextSlide() {
    showSlide(currentSlide + 1);
}

function prevSlide() {
    showSlide(currentSlide - 1);
}

// Inicia o carrossel mostrando o primeiro slide
showSlide(currentSlide);