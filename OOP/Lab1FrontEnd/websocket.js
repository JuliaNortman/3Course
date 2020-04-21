var socket = new WebSocket("ws://localhost:9090/hedgehog");

var message = {
	direction: 'TOP'
};

class GameField {
	constructor(height, width, field) {
		this.height = height;
		this.width = width;
		this.field = new Array(height);
		for(var i = 0; i < this.field.length; ++i) {
			this.field[i] = new Array(width);
		}
        //console.log("M=" + M + ", N=" + N + " fs=" + field.length);
        for(var i = 0; i < field.length; ++i) {
            //console.log(Math.floor(i/N) + " " + i%N);
            this.field[Math.floor(i/width)][i%width] = field[i];
        }
    }

    createBoard()
    {
    	var elem = document.getElementsByTagName('table');
    	if(elem[0] != null)
    	{
    		elem[0].parentNode.removeChild(elem[0]);
    	}

    	var table = document.createElement('table');
    	for(var i = 0; i < this.height; ++i)  
    	{
    		var newRow = table.insertRow(i);
    		for(var j = 0; j < this.width; ++j)
    		{
    			var newCell = newRow.insertCell(j);
    			var img = document.createElement('img');
    			if(this.field[i][j] == "HEDGEHOG") {
    				img.src = "images/hedgehog120.png";
    				img.className = "image";
			}
			else if(this.field[i][j] == "APPLE"){
				img.src = "images/apple120.png";
				img.className = "image";
			}
			else {
				img.src = "images/grass120.png";
				img.className = "image";
			}
			newCell.appendChild(img);
		}
	}
	document.body.appendChild(table);

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

function gameEnd(gameOver) {
	if(gameOver) {
		var elem = document.getElementById('gameOver');
		elem.innerHTML = "Congratulations";
	}
}

function newGame() {
	var elem = document.getElementById('gameOver');
	elem.innerHTML = "";
	refresh();
}

function onMessage(evt) {
	var jsonObj = JSON.parse(evt.data);
	var game = new GameField(jsonObj.yDim, jsonObj.xDim, jsonObj.field);
	game.createBoard();
	gameEnd(jsonObj.gameOver);
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