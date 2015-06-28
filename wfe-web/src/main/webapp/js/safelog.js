if (!window.console) {
	window.console = {};
}

if (!window.console.log) {
	window.console.log = function(msg) {};
}

var _internalLog = function () {
	console.log.apply(this, arguments);
}

if (!window.console.info) {
	window.console.info = _internalLog;
}
if (!window.console.debug) {
	window.console.debug = _internalLog;
}
if (!window.console.error) {
	window.console.error = _internalLog;
}