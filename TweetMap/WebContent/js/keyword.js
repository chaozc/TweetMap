/*--------------------------------------------------
  Keyword.js
  When the user changes the keyword,
  Send request via websocket to server
  So that the server changes TwitterStream filter
----------------------------------------------------*/

var data_all = [];
for (var i = 0; i < data_water.length; i++){
	data_all.push(data_water[i]);
}
for (var i = 0; i < data_run.length; i++){
	data_all.push(data_run[i]);
}
for (var i = 0; i < data_dance.length; i++){
	data_all.push(data_dance[i]);
}
for (var i = 0; i < data_ball.length; i++){
	data_all.push(data_ball[i]);
}
var data_db = data_all;

var keyword = 'all';

function set_keyword(w) {
	keyword = w;

	switch (w){
	case 'water':
		data_db = data_water;
		break;
	case 'run':
		data_db = data_run;
		break;
	case 'dance':
		data_db = data_dance;
		break;
	case 'ball':
		data_db = data_ball;
		break;
	case 'all':
		data_db = data_all;
		break;
	case 'any':
		data_db = [];
		break;
	}
	load_db();
	doSend(w);
}