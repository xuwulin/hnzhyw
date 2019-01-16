/*
 * Copyright (C) 2017 https://www.tfswx.com.cn - 同方赛威讯
 * 前端控件,封装常用的控件,如表格,等等
 */
/**
 * 表格控件
 *
 * <p>通过如下定义：
 * <code>
 *
 *     <table　id="tableTest">
 *        <thead>
 *          <tr>
 *              <th field="id" field-hidden="true">主键</th>
 *              <th field="action" formatter="Formatter.action">入库操作</th>
 *              <th field="lrr">录入人</th>
 *              <th field="opt" btn='[{"text":"上传","iconClass":"xz_img","click":"qtqk.fjsc"}'>操作</th>
 *          </tr>
 *       </thead>
 *       <tbody>
 *             ...
 *       </tbody>
 *    </table>
 *
 * </code>
 *
 * <p>列定义属性说明:
 *
 *      1.field        　　------列字段名称
 *      2.field-hidden 　　------列字段是否隐藏
 *      3.formatter    　　------列格式化器
 *      4.default-value　　------该列为空时的默认值,不写默认为""
 *      5.btn          　　------按钮定义
 *
 * <p>然后在js 如下调用：
 * <code>
 *
 *     var dataSet = [{...}];
 *     //定义表格
 *     var table = new SwxTable($("#tableTest"));
 *     //加载数据
 *     table.loadData(dataSet);
 *     // 添加行事件监听
 *     table.addRowListener("onClick",function(row,index){
 *         console.log(row);
 *         console.log(index);
 *     })
 *
 * </code>
 *
 *
 * @author 符黄辰君
 * @date 2017年9月27日
 * @since ES6,jQuery
 */
(function (window, $) {


    const SELECTED_CLASS = "selected-row";

    let eventDefineMap = Object.freeze({
        onClick: "click",
        onMouseout: "mouseout",
        onMouseover: "mouseover",
        onDbclick: "dblclick"

    });


    /**
     * 定义
     */
    class SwxTable {
        //版本
        static get version() {
            return "1.0";
        }

        /**
         * 构造器
         *
         * @param table
         * @param url  服务器地址
         * @constructor
         *
         */
        constructor(table, url) {//
            if (table instanceof HTMLElement || table instanceof String) {
                this.table = $(table);
            } else if (table instanceof jQuery) {
                this.table = table;
            }
            //初始化表头定义
            this.heads = this.table.find("thead").find("tr").children();
            this.body = this.table.find("tbody");
            this.url = url;

            this.selected = [];//选中的行
            this.events = Object.create(null);
        }

        /**
         * 加载数据
         *
         * @param recordSet 数据集
         */
        loadData(recordSet = [], type) {//
            this.reset();//复位数据
            if (recordSet.length === 0) {
                //TODO:从服务器获取


            } else {
                for (let i = 0; i < recordSet.length; i++) {
                    //为表格添加行
                    this.appendRow(i, recordSet[i]);
                }
                if (type == 'grxx') {

                }
            }
            //遍历<th>信息
            this.heads.each(function () {
                if (obj2bool($(this).attr("field-hidden"))) {
                    $(this).hide();
                }

            });
        }

        /**
         * 添加行
         *
         * @param index 行索引
         * @param record 数据项
         * 数据项内容 <code>
         *              {
         *                  ...
         *              }
         *           </code>
         */
        appendRow(index, record) {//
            let self = this;

            let $tr = $("<tr index='" + index + "'></tr>");

            $tr.bind("click.rowIntercept", {tableObj: this}, makeRowIntercept());
            //遍历<th>信息(未进入此遍历)
            this.heads.each(function () {
                if (obj2bool($(this).attr("field-hidden"))) {
                    $(this).hide();
                }
                let formatter = $(this).attr("formatter");//获取格式化器（一个函数句柄）
                let field = $(this).attr("field");

                let value = record[field] || "";//从服务器数据里取得对应的数据
                let text = value;//默认text就是value值
                if (!!formatter) {//遍历案件名称时这里为true，此if条件能进入，但是执行完就直接退出debugger
                    text = (eval(formatter))(value, record);//格式化数据；高检：是text = <a href = 'view/archives/case_details.html?bmsah=b4f1be39-d447-4e30-9c12-9d504fca43a1' target='_blank'>万淑芬</a>
                }

                if (!text) {
                    text = $(this).attr("default-value") || "";
                }
                //添加列
                self.appendCol($tr, $(this), field, value, text);

            });

            if (index !== 0) {
                findPrevTr.call(this, index).after($tr);
            } else {
                this.body.append($tr);//加入到<tbody></tbody>下
            }

            for (let jQEvent in this.events) {
                let fn = this.events[jQEvent];
                bind($tr, jQEvent + ".custom", fn);
            }
        }

        /**
         * 添加行监听
         *
         * @param event
         * @param fn
         */
        addRowListener(event, fn) {
            if (!fn || !(typeof fn === "function")) {
                return;
            }
            let jQEvent = eventDefineMap[event];
            this.events[jQEvent] = fn;

            bind(this.body.find("tr"), jQEvent + ".custom", fn);
        }

        /**
         * 增量更新行数据
         *
         * @param index 行索引
         * @param row 行数据
         */
        updateRowIncrement(index, row) {
            for (let field in row) {
                let $td = findTd.apply(this, [index, field]);
                if (!!$td) {
                    $td.text(row[field]);
                }
            }

        }

        /**
         * 添加列
         *
         * @param $tr   行对象
         * @param $head 表头<td>对象
         * @param field 字段
         * @param value 值
         * @param text 　文本
         */
        appendCol($tr, $head, field, value, text) {

            let hidden = $head.attr("field-hidden");

            let $td = null;

            if (!obj2bool(hidden)) {
                //如果<th field='opt'>则代表是操作，应当添加按钮
                if (field === "opt") {
                    $td = $(makeTd().field(field).text(""));

                    let btnInfo = $head.attr("btn");//获取按钮定义信息
                    if (!!btnInfo) {
                        //添加按钮
                        appendButton($tr, $td, JSON.parse(btnInfo));
                    }

                } else {
                    $td = makeTd().field(field).data(value).text(text);
                }

            } else {// 如果<th field-hidden='true'>定义为隐藏
                $td = makeTd().field(field).data(value).hidden(true).text(text);
            }


            if (!!$td) {
                $tr.append($td);
            }

        }

        /**
         * 获取选中行的数据
         *
         * @returns {Array}
         */
        getSelected() {
            let rows = [];
            let selected = this.selected;

            for (let i = 0; i < selected.length; i++) {
                let tr = selected[i];
                rows.push(getRowData($(tr)));
            }
            return rows;
        };

        /**
         * 复位表数据
         */
        reset() {
            this.body.empty();
        };

    }

    /*********************************私有方法区***************************************/


    function bind($tr, evtName, fn) {
        $tr.bind(evtName, {fn: fn}, function (e) {
            (e.data.fn)(getRowData($(this)), getRowIndex($tr));
        });
    }

    function makeRowIntercept() {
        //　
        return function (e) {

            if (!(this instanceof HTMLElement)) {
                return;
            }

            //所有选中行
            let selected = e.data.tableObj.selected;
            $(this).toggleClass(SELECTED_CLASS);

            if ($(this).hasClass(SELECTED_CLASS)) {
                selected.push(this);
            } else {
                removeSelected(selected, this);
            }
        };
    }

    //产生td
    function makeTd() {
        return Object.freeze({
            __td__s: ["<td "],
            field: function (field) {
                this.__td__s.push(" field='" + field + "' ");
                return this;
            },
            hidden: function (is) {
                if (is) {
                    this.__td__s.push(" style='display: none'");
                }
                return this;
            },
            data: function (data) {
                this.__td__s.push(" data='" + data + "' ");
                return this;
            },
            text: function (text) {
                this.__td__s.push(">" + text + "</td>")
                return this.__td__s.join("");
            }
        });
    }

    //为行添加按钮
    function appendButton($tr, $td, buttons) {
        for (let j = 0; j < buttons.length; j++) {
            let button = buttons[j];
            let $btn = $('<button evt-handler="' + button["click"] + '" class="table_bj">' +
                '<span class="' + button["iconClass"] + '"></span>' +
                '<span>' + button["text"] + '</span>' +
                '</button>').appendTo($td);
            $btn.click(function () {
                let $self = $(this);
                let $parentTd = $self.closest("td");

                let $tds = $self.closest("tr").children();
                let row = {};
                $tds.each(function () {
                    if ($parentTd[0] !== $(this)[0]) {
                        row[$(this).attr("field")] = $(this).attr("data");
                    }
                });

                //找到事件处理器并调用事件
                let evtHandler = $self.attr("evt-handler");
                if (!!evtHandler) {
                    //1、整行数据
                    //2、行索引
                    (eval(evtHandler))(row, getRowIndex($tr));//传入参数
                }
            });
        }

    }

    //获取行数据
    function getRowData($tr) {
        let $tds = $tr.children();
        let row = {};
        $tds.each(function () {
            //排除opt行
            if ($(this).attr("field") !== "opt") {
                row[$(this).attr("field")] = $(this).attr("data");
            }

        });
        return row;
    }

    //获取行索引
    function getRowIndex($tr) {
        return $tr.attr("index");
    }

    //通过索引找到前一行
    function findPrevTr(index) {
        let $prev = null;

        if (!!index) {//行索引存在
            //索引自减
            $prev = this.body.find("tr[index='" + --index + "']");
        } else {
            let $trList = this.body.find("tr");
            $prev = $trList.get($trList.length - 1);//找到行末尾
        }
        return $prev;
    }

    // 通过行索引和字段名找到<td></td>
    function findTd(index, field) {
        let $td = null;
        let $tr = this.body.find("tr[index='" + index + "']").children();
        $tr.each(function () {
            if ($(this).attr("field") === field) {
                $td = $(this);
            }
        });
        return $td;
    }


    function removeSelected(arr, item) {
        if (arr.length) {
            let index = arr.indexOf(item);
            if (index > -1) {
                return arr.splice(index, 1);
            }
        }
    }


    //----
    let _stringPro_ = String.prototype;
    let _arrayPro_ = Array.prototype;

    _stringPro_.format = function (obj) {
        let str = this;
        for (let key in obj) {
            str = str.replace(new RegExp("\\{\\{" + key + "\\}\\}", "g"), obj[key]);
        }

        return str;
    };

    window.SwxTable = SwxTable;
})(window, jQuery);

/**
 * 下一步向导控件
 *
 * <p>通过如下定义：
 * <code>
 *
 *       <div id="div_gwxx_guide" style="width: 1000px;margin-bottom: 12px;"></div>
 *       <div id="div_gwxx_guide_content" style="height: 300px;width: 1500px;">
 *           <div class="step_panel" style="" bind-step-index="0">
 *               <!--你的内容-->
 *           </div>
 *           <div class="step_panel" style="display: none" bind-step-index="1">
 *               <!--你的内容-->
 *           </div>
 *       </div>
 *       <div id="div_gwxx_guide_btn">
 *           <button  class="nav_prev btn_step">上一步</button>
 *           <button  class="nav_next btn_step">下一步</button>
 *           <button  class="nav_final btn_step_complete" style="width:80px">保存</button>
 *       </div>
 *
 * </code>
 *
 * <p>然后在js 如下调用：
 * <code>
 *      var stepNavigator = new StepNavigator($("#div_gwxx_guide"),["编辑数据", "上传附件"]);
 *
 *      stepNavigator.setContent($("#div_gwxx_guide_content"));
 *      stepNavigator.setButton($("#div_gwxx_guide_btn"));
 *
 * </code>
 *
 *
 * @author 符黄辰君
 * @date 2017年11月16日
 * @since ES5,jQuery
 */
(function (window, $) {

    /**
     *
     * @param $navCon
     * @param title
     * @constructor
     */
    function StepNavigator($navCon, title) {
        //总步数
        this.stepNum = title.length;
        //最后一步的位置
        this.finalStepIndex = this.stepNum - 1;
        this.$navCon = $navCon;
        this.$navCon.step({
            index: 0,
            time: 200,
            title: title
        });
    }

    /**
     * 设置按钮Div容器
     * @param $btnCon
     */
    StepNavigator.prototype.setButton = function ($btnCon) {
        var self = this;
        this.$btnCon = $btnCon;
        var finalStepIndex = this.finalStepIndex;
        //
        var $prev = this.$btnCon.find(".nav_prev");
        var $next = this.$btnCon.find(".nav_next");
        var $final = this.$btnCon.find(".nav_final");
        $final.hide();
        if (getIndex.call(this) === 0) {
            $prev.hide();
        }
        if (getIndex.call(this) === finalStepIndex) {
            $final.show();
        }
        //
        $prev.click(function () {
            prev.call(self);
            var index = getIndex.call(self);
            if (index === 0) {
                $prev.hide();
            } else {
                $prev.show();
            }
            if (index === finalStepIndex) {
                $next.hide();
                $final.show();
            } else {
                $next.show();
                $final.hide();
            }
            trigger.call(self, index);
        });
        //
        $next.click(function () {
            next.call(self);
            var index = getIndex.call(self);
            if (index === 0) {
                $prev.hide();
            } else {
                $prev.show();
            }
            if (index === finalStepIndex) {
                $next.hide();
                $final.show();
            } else {
                $final.hide();
            }
            trigger.call(self, index);
        });
        //
        $final.click(function () {
            //直接跳到最后一步
            goto.call(self, finalStepIndex);
            var index = getIndex.call(self);
            if (index === 0) {
                $prev.hide();
            } else {
                $prev.show();
            }
            if (index === finalStepIndex) {
                $next.hide();
                $final.show();
            } else {
                $next.show();
                $final.hide();
            }
            trigger.call(self, index);
        });
    };
    /**
     * 设置内容Div容器
     *
     * @param $contentCon
     */
    StepNavigator.prototype.setContent = function ($contentCon) {
        this.$contentCon = $contentCon;
        this.$stepPanelList = this.$contentCon.find(".step_panel");
    };

    /**
     * 设置按钮点击监听
     *
     * @param hook
     */
    StepNavigator.prototype.setListener = function (hook) {
        this.hook = hook;
    };

    /**
     *
     * @param index
     */
    function trigger(index) {
        if (!(this instanceof StepNavigator))return;
        //显示面板
        showStepPanel.call(this, index);
        //触发回调
        if (!!this.hook) {
            (this.hook)(index);
        }
    }

    /**
     * 上一步
     */
    function prev() {
        if (!(this instanceof StepNavigator))return;
        this.$navCon.prevStep();
    }

    /**
     * 下一步
     */
    function next() {
        if (!(this instanceof StepNavigator))return;
        this.$navCon.nextStep();
    }

    /**
     * 直接跳到某步
     * @param index 索引
     */
    function goto(index) {
        if (!(this instanceof StepNavigator))return;
        this.$navCon.toStep(index);
    }

    /**
     * 获取索引
     */
    function getIndex() {
        if (!(this instanceof StepNavigator))return;
        return parseInt(this.$navCon.getIndex());
    }

    /**
     * 显示面板
     *
     * @param index 索引
     *
     */
    function showStepPanel(index) {
        if (!this instanceof StepNavigator)return;

        this.$stepPanelList.hide();
        this.$stepPanelList.each(function () {
            var sIndex = $(this).attr("bind-step-index");
            if (sIndex == index) {
                $(this).show();
            }
        })
    }

    window.StepNavigator = StepNavigator;
})(window, jQuery);

/**
 * 简单的头像上传控件
 *
 * <p>通过如下定义：
 * <code>
 *
 *        <img id="grxx_xxzx_edit_photo" src="images/person.png" style="width: 90%;height: 97%">
 *        <button type="button" class="action_button" id="grxx_xxzx_edit_select" style="width: 85px;">选择照片</button>
 *
 * </code>
 *
 * <p>然后在js 如下调用：
 * <code>
 *      // 头像上传器
 *      var xxzxSimplePhotoUploader = new SimplePhotoUploader($("#grxx_xxzx_edit_photo"),"#grxx_xxzx_edit_select");
 *      xxzxSimplePhotoUploader.setParams(params);//上传参数
 *      xxzxSimplePhotoUploader.init();
 *
 * </code>
 *
 *
 * @author 符黄辰君
 * @date 2017年11月16日
 * @since ES5,jQuery
 */
(function (window, $) {


    /**
     *
     * @param $thumb
     * @constructor
     */
    function SimplePhotoUploader($thumb,picker) {
        this.thumb = $thumb;
        this.picker = picker;
        this.uploader = null;
    }

    /**
     * 设置上传时的参数
     *
     * @param params
     */
    SimplePhotoUploader.prototype.setParams = function (params) {
        this.params = params;
    };
    /**
     * 初始化
     */
    SimplePhotoUploader.prototype.init = function () {
        var self = this;
        self.destroy();
        //创建
        this.uploader = WebUploader.create({
            auto: true,
            swf: "plugin/webuploader/Uploader.swf",
            server: getRootPath() + '/service/attachment/uploadPersonImage',
            pick: self.picker,// 选择文件按钮
            duplicate: true,
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });

        this.uploader.on('fileQueued', function (file) {
            self.uploader.makeThumb(file, function (error, src, srcimg) {
                if (error) {
                    self.thumb.replaceWith('<span>不能预览</span>');
                    return;
                }
                self.thumb.attr('src', src);
            }, 100, 100);
        });

        this.uploader.on('uploadFinished', function () {
            self.destroy();
            self.init();
        });
        // 文件加入时
        this.uploader.on('uploadBeforeSend', function (obj, data, headers) {
            data.did = self.params.daid;
        });
    };
    /**
     * 销毁
     */
    SimplePhotoUploader.prototype.destroy = function () {
        if (!!this.uploader) {
            this.uploader.destroy();
        }
    };

    window.SimplePhotoUploader = SimplePhotoUploader;
})(window, jQuery);

/**
 * @author 李治鑫
 * @description 对easyUI的datebox控件进行优化，增加清除按钮
 * @date 2018/01/09
 */
(function(window,$){
    var buttons = $.extend([],$.fn.datebox.defaults.buttons);
    buttons.splice(1,0,{
        text:"清空",
        handler:function(target){
            $(target).combo("setValue","").combo("setText","");//设置空值
            $(target).combo("hidePanel");//点击清空后关闭选择面板
        }
    });

    $.fn.datebox.defaults.buttons = buttons;
})(window, jQuery);