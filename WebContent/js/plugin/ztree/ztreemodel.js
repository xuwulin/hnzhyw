
var isShowMenu = false;
var ztreedata = {
	dwbm : top.currentUserInfo.dwbm,
	name : top.currentUserInfo.dwmc
}
	var setting = {
		data : {
			key : {
				name : "text"
			}
		},
		view : {
			showIcon : false,
			dblClickExpand : false,
			selectedMulti : false
		},
		callback : {
			beforeClick : beforeClick,
			onClick : onClick
		}
	};

	function beforeClick(treeId, treeNode) {
		var check = (treeNode && treeNode.valueSelect);
		if (check)
			hideMenu();
		return check;
	}

	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree.getSelectedNodes();
		v = "";
		var name = nodes[0].text;
		var dwbm = nodes[0].id;
		ztreedata.name = name;
		ztreedata.dwbm = dwbm;
		v = name;
		var cityObj = $("#citySel");
		cityObj.attr("value", v);
	}

	function showMenu() {
		if (isShowMenu == false) {
			isShowMenu = true;
			var cityObj = $("#citySel");
			var cityOffset = $("#citySel").offset();
			$("#menuContent").slideDown("fast");
			$("body").bind("mousedown", onBodyDown);
		} else
			hideMenu();
	}
	
	function hideMenu() {
		isShowMenu = false;
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
				event.target).parents("#menuContent").length > 0)) {
			hideMenu();
		}
	}