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
				img.src = "images/hedgehog.png";
				img.className = "grass";
				newCell.appendChild(img);
			}
			else if(gameField.field[i][j] == "APPLE"){
				img.src = "images/apple.png";
				img.className = "grass";
				newCell.appendChild(img);
			}
		}
	}
	document.body.appendChild(table);

}

function refresh() {
	location.reload();
	return false;
}