var socket = new WebSocket("ws://localhost:9090/hedgehog");

var message = {
    direction: 'TOP'
};

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

    console.log(JSON.parse(jsonData).field);
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