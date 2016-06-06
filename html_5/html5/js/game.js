// Criando canvas
var lastPosition;
var canvas = document.createElement("canvas");
var ctx = canvas.getContext("2d");
canvas.width = 512;
canvas.height = 480;
document.body.appendChild(canvas);

// Background
var bgReady = false;
var bgImage = new Image();
bgImage.onload = function () {
    bgReady = true;
};
bgImage.src = "../img/background.png";


// Hero imagem
var heroReady = false;
var heroImage = new Image();
heroImage.onload = function () {
    heroReady = true;
};
heroImage.src = "../img/caro.png";

// Monster imagem
var monsterReady = false;
var monsterImage = new Image();
monsterImage.onload = function () {
    monsterReady = true;
};
monsterImage.src = "../img/youWin.png";

// Objetos do jogo
var hero = {
    speed: 300 // movement in pixels per second
};

var monster = {};
var monstersCaught = 0;

// Controlando pelo teclado
var keysDown = {};

addEventListener("keydown", function (e) {
    keysDown[e.keyCode] = true;
}, false);

addEventListener("keyup", function (e) {
    delete keysDown[e.keyCode];
}, false);

// Resetando o jogo
var reset = function () {
    hero.x = canvas.width / 2;
    hero.y = 420;

    monster.x = canvas.width / 2;
    monster.y = 0;
};

// Controle das direções
var update = function (modifier) {
    if (38 in keysDown) { // para cima
        hero.y -= hero.speed * modifier;
        $('#dialogWin').text(' ');
    }
    if (40 in keysDown) { // para baixo
        hero.y += hero.speed * modifier;
    }
    if (37 in keysDown) { // para esquerda
        hero.x -= hero.speed * modifier;
    }
    if (39 in keysDown) { // para direita
        hero.x += hero.speed * modifier;
    }


    if (
        hero.x <= (monster.x + 32)
        && monster.x <= (hero.x + 32)
        && hero.y <= (monster.y + 32)
        && monster.y <= (hero.y + 32)

        ) {
        //++monstersCaught;
$('#dialogWin').text('You win...');
        reset();
    }
};

// Desenhando na tela
var render = function () {
    if (bgReady) {
    ctx.drawImage(bgImage, 0, 0);
    }

    if (heroReady) {
        ctx.drawImage(heroImage, hero.x, hero.y);
    }

    if (monsterReady) {
        ctx.drawImage(monsterImage, monster.x, monster.y);
    }

//    // Placar
//    ctx.fillStyle = "rgb(250, 0, 0)";
//    ctx.font = "22px Verdana";
//    ctx.textAlign = "left";
//    ctx.textBaseline = "top";
//    ctx.fillText("Monstros Capturados: " + monstersCaught, 32, 32);

};

// loop do jogo
var main = function () {
    var now = Date.now();
    var delta = now - then;

    update(delta / 1000);
    render();

    then = now;
};

// Inicia o jogo
reset();
var then = Date.now();
setInterval(main, 1); 