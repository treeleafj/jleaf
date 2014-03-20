// 获得coolie 的值
function getCookie(name) {
	var cookieArray = document.cookie.split("; "); // 得到分割的cookie名值对
	for (var i = 0; i < cookieArray.length; i++) {
		var arr = cookieArray[i].split("="); // 将名和值分开
		if (arr[0] == name)
			return unescape(arr[1]); // 如果是指定的cookie，则返回它的值
	}
	return null;
}

// 删除cookie
function delCookie(name) {
	document.cookie = name + "=;expires=" + (new Date(0)).toGMTString();
}

 // 添加cookie
function setCookie(objName, objValue, expires) {
	var str = objName + "=" + escape(objValue);
	if (expires) { // 为时不设定过期时间，浏览器关闭时cookie自动消失
		str += "; expires=" + expires.toGMTString();
	}
	document.cookie = str;
}