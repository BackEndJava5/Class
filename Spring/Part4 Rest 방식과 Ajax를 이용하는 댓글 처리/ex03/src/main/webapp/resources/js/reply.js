console.log("Reply Module...............");
var replyService = (function() {
	function add(reply, callback) {
		console.log("reply.............");
	}
	return {add:add};
})();