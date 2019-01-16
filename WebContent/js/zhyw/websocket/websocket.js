//定义websocket
var webSocket = null;

// 建立websocket通信
webSocket = new WebSocket("ws://" + location.host + rootPath + "/daMessage");
//console.info(location.host);

webSocket.onopen = function(event) {
	
	var intervalVal = setInterval(function() {
		if (currentUserInfo) {
			sendMsgNoticeArgs(currentUserInfo.dwbm, currentUserInfo.gh);
			clearInterval(intervalVal);
		}
	}, 500);

	setInterval(function() {
		if (currentUserInfo) {
			sendMsgNoticeArgs(currentUserInfo.dwbm, currentUserInfo.gh);
		}
	}, 3000000);

	if (event.data === undefined) {
		// alert("undefined data");
		// return;
	}

	webSocket.onmessage = function(event) {
		// alert("成功了" + event.data);
		// "1,2017-5-6到2017-6-8的个人档案审批通过.<br/>2,2017-5-5到2017-6-8的个人绩效<br/>2017-5-7到2017-6-8的个人档案";
		slide(event.data);
	};

	webSocket.onerror = function(event) {
		console.log(event);
	};

	webSocket.onclose = function(event) {
//		console.log(event);
//		alert("连接关闭");
	};

	// 给WebSocket发送必要参数
}

// 给WebSocket后端传递数据
function sendMsgNoticeArgs(dwbm, gh) {

	// 获取当前登录人的dwbm、gh、消息状态值
	var messageNotice = {
		dwbm : dwbm,
		gh : gh,
		status : '1',
		nowPage : 1,
		pageSize : 4
	};
	
//	console.info(currentUserInfo + "-----" + messageNotice);// JSON.stringify(message)
	
	var message = JSON.stringify(messageNotice);//""+dwbm+","+gh+","+status+","+nowPage+","+pageSize;
	webSocket.send(message);//"张明的2017-5-6到2017-6-8的个人档案陈更明已经审批通过！"
};

// 调用Jquery EasyUI的消息提示框
function slide(msg) {
	$.messager.show({
		title : '您有新的消息',
		height : '310px',
		width : '300px',
		msg : msg,
		timeout : 10000,
		showType : 'slide'
	});
}
