var db_inicial = {
    "categorias": [
        {
            "id": 1,
            "nome": "Aplicativos de banco"
        },

        {
            "id": 2,
            "nome": "Aplicativos de redes sociais"
        },
        {
            "id": 3,
            "nome": "Aplicativos de filmes e séries"
        },
        {
            "id": 4,
            "nome": "Uso do celular"
        },
        {
            "id": 5,
            "nome": "Aplicativos de música"
        },
        {
            "id": 6,
            "nome": "Aplicativos de telefonia"
        },
        {
            "id": 7,
            "nome": "Gratuito"
        }
      ]
};

let db = JSON.parse(localStorage.getItem("db_categoria"));

if (!db) {
    localStorage.setItem("db_categoria", JSON.stringify(db_inicial));
    db = db_inicial;
}

function init() {
    let texto = "";
    for (let i = 0; i < db.categorias.length; i++) {
        let categoria = db.categorias[i];

        if(categoria.nome != "Gratuito")
        {
            texto += `
                        <div class="col-12 col-xl-6">
                            <a href="/tipos-para-cada-categoria/index.html?name=${categoria.nome}"><button class="custom-btn btn-13">${categoria.nome}</button></a>
                        </div>
                `;
        }
    }
    document.querySelector("#btn-area").innerHTML = texto;


     colocarSair();
}

function colocarSair(){
    let tipo = JSON.parse(sessionStorage.getItem('usuarioCorrente')).tipo;
    let nomeUsu = "";

    let textoNome;
    
    if (tipo == "user")
    {
        nomeUsu = JSON.parse(sessionStorage.getItem('usuarioCorrente')).nome;
    } else if (tipo == "adm")
    {
        nomeUsu = JSON.parse(sessionStorage.getItem('usuarioCorrente')).usuario;
    }
    
    textoNome = `<p>${nomeUsu} | <a onclick="logoutUser()" href="/inicio-login/login.html">Sair</a></p>`;

    document.querySelector('#insertSair').innerHTML = textoNome;
}


function logoutUser () {
    usuarioCorrente = {};
    sessionStorage.setItem ('usuarioCorrente', JSON.stringify (usuarioCorrente));
    window.location = LOGIN_URL;
}

testarLoginUser()
function testarLoginUser ()
{
    let tipoUsuarioLogado = JSON.parse(sessionStorage.getItem('usuarioCorrente')).tipo;
    

    if (tipoUsuarioLogado != "user" && tipoUsuarioLogado != "adm") {
        alert("Você não tem acesso a essa página.");
        window.location = "/inicio-login/login.html";
    }
}
