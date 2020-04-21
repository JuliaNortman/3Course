function createBoard(gameField)
{
	rowNum = gameField.M;
	colNum = gameField.N;
	var elem = document.getElementsByTagName('table');
	if(elem[0] != null)
	{
		elem[0].parentNode.removeChild(elem[0]);
	}

	var table = document.createElement('table');
	for(var i = 0; i < gameField.M; ++i)  
	{
		var newRow = table.insertRow(i);
		for(var j = 0; j < gameField.N; ++j)
		{
			var newCell = newRow.insertCell(j);
			var img = document.createElement('img');
			if(gameField.field[i][j] == "HEDGEHOG") {
				img.src = "images/hedgehog120.png";
				img.className = "image";
				//newCell.appendChild(img);
			}
			else if(gameField.field[i][j] == "APPLE"){
				img.src = "images/apple120.png";
				img.className = "image";
				//newCell.appendChild(img);
			}
			else {
				img.src = "images/grass120.png";
				img.className = "image";
				//newCell.appendChild(img);
			}
			newCell.appendChild(img);
		}
	}
	document.body.appendChild(table);

}

function refresh() {
	location.reload();
	return false;
}