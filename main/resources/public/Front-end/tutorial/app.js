let id;

window.onload = () => {
    let params = new URLSearchParams(window.location.search);
    id = params.get('id');
    carregaTutorial();
}

function carregaTutorial () {
    let db = JSON.parse(localStorage.getItem('db_tutorial'));
    let tut;
    let i = 0;
    
    while( i < db.data.length )
    {
        if ( db.data[i].id == id )
        {
            tut = db.data[i];
            i = db.data.length;
        }
        i++;
    }
    
    let texto = `
        <div class="row">
            <div class="col-12">
                <h1 id="title">Tutorial: ${tut.titulo}</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-12" id="colTutorial">
                <div id="divVideo">
                    <iframe
                        width="560"
                        height="315"
                        src="${tut.url}"
                        title="YouTube video player"
                        frameborder="0"
                        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                        allowfullscreen
                        class="w-100"
                    ></iframe>
                </div>
                <div id="textoTutorial">
                    <p>
                        ${tut.tutorial}
                    </p>
                </div>
            </div>
        </div>
    `;

    document.getElementById('telaTutorial').innerHTML = texto; 


    if(tut.categoria != "Gratuito" && tut.categoria != "Uso do site")
    {
        testarLoginUser()
    }
}

function goBack() {
    window.history.back()
}


colocarSair();

//apenas para pagina que podem ser acessadas com ou sem login
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

    if( nomeUsu )
    {
        document.querySelector('#insertSair').innerHTML = textoNome;
    }
}

function logoutUser () {
    usuarioCorrente = {};
    sessionStorage.setItem ('usuarioCorrente', JSON.stringify (usuarioCorrente));
    window.location = LOGIN_URL;
}

function testarLoginUser ()
{
    let tipoUsuarioLogado = JSON.parse(sessionStorage.getItem('usuarioCorrente')).tipo;

    if (tipoUsuarioLogado != "user" && tipoUsuarioLogado != "adm") {
        alert("Você não tem acesso a essa página.");
        window.location = "/inicio-login/login.html";
    }
}
