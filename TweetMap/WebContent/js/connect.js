/*WebSocket.js*/
var loc = window.location, wsUri;

if (loc.protocol === "https:") {
    wsUri = "wss:";
} 
else {
    wsUri = "ws:";
}
wsUri += "//" + loc.host;
wsUri += loc.pathname + "echo";
var output = document.getElementById("output");
function init() {
    
    //writeToScreen(wsUri);
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
    //writeToScreen("Connected to Endpoint!");
}
function onMessage(evt) {
	if (evt.data.indexOf(',') > 0){
		//writeToScreen(evt.data);
		var coordinate = evt.data.split(",");
		addGeo(coordinate);	
	}
	
    //writeToScreen("Message Received: " + evt.data);
}
function onError(evt) {
    //writeToScreen('ERROR: ' + evt.data);
}
function doSend(message) {
    //writeToScreen("Keyword Sent: " + message);
    websocket.send(message);
}
function writeToScreen(message) {
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
     
    output.appendChild(pre);
}
window.addEventListener("load", init, false);
//window.addEventListener("load", load_db, false);




