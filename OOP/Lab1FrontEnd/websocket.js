var socket = new WebSocket("ws://localhost:9090/hedgehog");

var message = {
    direction: 'TOP'
};

class GameField {
    constructor(M, N, field) {
        this.M = M;
        this.N = N;
        this.field = new Array(M);
        for(var i = 0; i < this.field.length; ++i) {
            this.field[i] = new Array(N);
        }
        //console.log("M=" + M + ", N=" + N + " fs=" + field.length);


        for(var i = 0; i < field.length; ++i) {
            //console.log(Math.floor(i/N) + " " + i%N);
            this.field[Math.floor(i/N)][i%N] = field[i];
        }
    }
}

document.onkeydown = function(e) {
    e = e || window.event;
    send(e);
};

//socket functions

socket.onopen = function(evt) {
    openEvent(evt);
};

socket.onmessage = function(evt) {
    onMessage(evt);
};

socket.onclose = function(evt) {

};

socket.onerror = function(evt) {
    onError(evt);
};

function openEvent(evt) {
    console.log("Connection established");
}


function onMessage(evt) {
    var jsonData = evt.data;
    var jsonObj = JSON.parse(jsonData);
    var game = new GameField(jsonObj.yDim, jsonObj.xDim, jsonObj.field);
    createBoard(game);
}

function send(e) {

    switch (e.keyCode) {
        case 37:
            message.direction = "LEFT";
            socket.send(JSON.stringify(message));
            break;
        case 38:
            message.direction = "TOP";
            socket.send(JSON.stringify(message));
            break;
        case 39:
            message.direction = "RIGHT";
            socket.send(JSON.stringify(message));
            break;
        case 40:
            message.direction = "BOTTOM";
            socket.send(JSON.stringify(message));
            break;
        default:
            break;
    }

    
}