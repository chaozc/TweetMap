/*-------------------------------------------------------
  connect.js
  Connect the client to the server via websocket
  Listen on the real-time Twitter crawled by the server
---------------------------------------------------------*/

//Define websocket wsUri
var loc = window.location, wsUri;

if (loc.protocol === "https:") {
    wsUri = "wss:";
} 
else {
    wsUri = "ws:";
}
wsUri += "//" + loc.host;
wsUri += loc.pathname + "echo";

//Initalize websocket, and define functions related to websocket events
function init() {
    
    websocket = new WebSocket(wsUri);
    websocket.onopen = function(evt) {
        onOpen(evt)
    };
    websocket.onmessage = function(evt) {
        onMessage(evt)
    };
    websocket.onerror = function(evt) {
        onError(evt)
    };
}
function send_message() {
}
function onOpen(evt) {
}
//When get coordinates returned from server, update coordiations on map
function onMessage(evt) {
	if (evt.data.indexOf(',') > 0){
		var coordinate = evt.data.split(",");
		addGeo(coordinate);	
	}
}
function onError(evt) {
}
function doSend(message) {
    websocket.send(message);
}
window.addEventListener("load", init, false);




