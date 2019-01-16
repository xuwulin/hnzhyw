/**
 *
 * <h3>用户服务</h3>
 *
 * 2018/12/21
 * @since ES5
 */
(function (window) {

    var UserService = {};

    /**
     * 通过部门映射的字符串返回对应的业务部门/业务类型
     *
     * @param bmysArray
     */
    UserService.getYwbm = function (bmysArray) {
        var ywlxStr = ""; //业务类型字符串
        var ywlxStrTemp = "";
        var bmysStrTemp = "";  //临时部门映射字符串
        var bmysStr = "";
        if (bmysArray) {
            for (var i = 0; i < bmysArray.length; i++) {
                bmysStrTemp += bmysArray[i] + ",";
            }
            bmysStr = bmysStrTemp.substring(0, bmysStrTemp.length - 1);
        }

        var url = rootPath + "/service/ywlxkh/getYwlxByBmys";
        var data = {
            bmysStr: bmysStr
        };

        $.ajax({
            type: "get",
            url: url,
            async: false,
            data: data,
            dataType: 'json',
            success: function (res) {
//			console.log(res);
                if (res) {
                    ywlxStrTemp = res;
                    ywlxStr = ywlxStrTemp.substring(0, ywlxStrTemp.length - 1);
                }
            }
        });
        return ywlxStr;
    };



    //
    window.UserService = UserService;

})(window);