/*
 *  Copyright (C) 2017 https://www.tfswx.com.cn - 同方赛威讯
 *
 * <p>
 *     通过模块化定义的js代码,可以方便管理js代码之间的依赖和日后维护工作,模块定义将实现代码细节
 *     封装,对外部只需要暴露接口即可,避免了全局污染
 * </p>
 */
/**
 *
 * <h3> 模块化 </h3>
 *
 * @author 符黄辰君
 * @since 2017年6月21日
 * @since ES6,jQuery
 */
(function (swx, $) {

    "use strict";//严格模式,消除js语法不严谨之处,减少怪异行为


    let isSynchronizeParentModule = true; //是否同步父页面模块
    let isExtendSubject = false;           //定义的模块是否继承主题类
    let isExtendParameter = false;         //定义的模块是否继承参数类
    let isCover = true;                   //重复定义的模块是否覆盖之前的

    let _toString_ = Object.prototype.toString;

    /**
     * 主题类
     */
    class Subject {
        /**
         * 构造器
         */
        constructor() {
            this.observers = Object.create(null);
        }

        /**
         * 添加观察者
         *
         * @param subject
         * @param observable
         */
        addObservable(subject, observable) {
            if (!!observable) {
                if (!this.observers[subject]) {
                    this.observers[subject] = [];
                }
                let observersQueue = this.observers[subject];
                if (_toString_.call(observersQueue) === '[object Array]') {
                    if (observable instanceof Observable) {
                        observersQueue.push(observable);
                    } else {
                        console.warn("添加的不是observable对象！");
                    }

                }
            }
        }

        /**
         * 删除观察者
         *
         * @param subject
         * @param observable
         */
        removeObservable(subject, observable) {
            if (!!observable) {
                if (!!this.observers[subject]) {
                    let observersQueue = this.observers[subject];
                    if (_toString_.call(observersQueue) === '[object Array]') {
                        let index = null;
                        for (let i = 0; i < observersQueue.length; i++) {
                            if (observersQueue[i] === observable) {
                                index = i;
                                break;
                            }
                        }
                        if (index >= 0) {
                            removeArrayItem(index, observersQueue);
                        } else {
                            console.error("删除错误，未找到观察者！");
                        }

                    }

                }
            }
        }

        /**
         * 通知
         * @param subject
         * @param param
         */
        notify(subject, param) {
            let observers = this.observers[subject];
            if (!!observers) {
                let observersLength = observers.length;
                if (observersLength > 0) {
                    for (let i = 0; i < observersLength; i++) {
                        if (!!observers[i].update) {
                            observers[i].update(param);// 所有观察者必须实现update()接口
                        } else {
                            console.warn("观察者未实现Observable！");
                        }
                    }
                }
            }
        };
    }
    /**
     *
     * 观察者接口
     *
     */
    class Observable {
        /**
         * 构造器
         * @param func
         */
        constructor(func = function () {
        }) {
            this.func = func
        }

        update(param) {
            if (!!this.func && typeof this.func === "function") {
                (this.func)(param);
            }
        }

        destroy() {
            if (!!this.func && typeof this.func === "function") {
                this.func = null;
            }
        }
    }

    let subscribeObjects = Object.create(null);    // 观察者对象列表
    let moduleObjects = Object.create(null);       // 模块对象列表,所有模块定义都存到这里
    let moduleDefinedStub = Object.create(null);   // 模块定义存根

    /**
     * 模块定义入口(不支持双向依赖)
     *
     * @param name      模块名称
     * @param requires  依赖列表  ['userModule','roleModule']
     * @param func      模块函数  function(){}
     */
    swx.module = function (name, requires, func) {

        // 参数合法性检察
        checkArgumentsLegal(requires, func);

        //  1-- 继承
        if (isExtendSubject) {
            func.prototype = new Subject();
            /**
             * 订阅主题
             *
             * @param subjectName 主题名 "模块名.事件名"
             * @param fn          回调
             */
            func.prototype.subscribe = function (subjectName, fn) {

                let observable = new Observable(fn);//创建观察者对象

                let subjectNameArray = subjectName.split(".");
                let moduleName = subjectNameArray[0];//得到模块名
                let eventName = subjectNameArray[1];//得到事件名

                let moduleObject = getModuleObject(moduleName);//获取模块对象

                //如果模块存在则直接为其添加观察者,不存在则先缓存观察者对象
                if (!!moduleObject) {
                    moduleObject.addObservable(eventName, observable);
                } else {

                    if (!subscribeObjects[moduleName]) {
                        subscribeObjects[moduleName] = {};
                        if (!subscribeObjects[moduleName][eventName]) {
                            subscribeObjects[moduleName][eventName] = [];
                        }
                    }
                    //添加对应数组
                    subscribeObjects[moduleName][eventName].push(observable);
                }

            }


        }


        //  2-- 继承
        if (isExtendParameter) {
            func.prototype.__map__ = {}
            func.prototype.setParameterAll = function (params) {
                if (!!params) {
                    for (let key in params) {
                        this.setParameter(key, params[key]);
                    }
                }
            }
            func.prototype.setParameter = function (key, value) {
                this.__map__[key] = value;
            }
            func.prototype.getParameter = function (key) {
                return this.__map__[key];
            }
            func.prototype.removeParameter = function (key) {
                delete this.__map__[key];
            }
        }


        // 创建模块对象,等同于 new 操作
        let obj = {};
        obj.__proto__ = func.prototype;

        addModuleObjectToList(name, obj);// 添加到列表

        //解决A模块订阅了B模块,B模块还未被定义的情况
        if (isExtendSubject) {
            addObservableForTheModuleObject(name, obj);
        }

        let requireObjects = [];
        if (requires.length > 0) {
            requireObjects = searchDependency(requires);// 寻找依赖
        }

        $(function () {

            //异步操作,避免报错后后续代码不执行
            setTimeout(function () {
                func.apply(obj, requireObjects);// 初始化模块
            }, 0);


        });

        // 存储模块定义存根
        moduleDefinedStub[name] = {
            name: name,
            requires: requires,
            func: func
        };
    }

    /**
     * 重载模块
     *
     * @description 重载模块相当于 重新调用swx.module(name,requires,func)
     *
     * @param name   模块名称
     */
    swx.reloadModule = function (name) {
        var stub = moduleDefinedStub[name];
        if (!!stub) {
            this.module(stub.name, stub.requires, stub.func);
        }
    }
    /**
     * 获取模块对象列表
     *
     * @returns {{}}
     */
    swx.getModuleObjects = function () {
        return moduleObjects;
    }

    /**
     * 获取模块对象
     * @param name 模块名称
     * @returns {{}}
     */
    swx.getModuleObject = function (name) {
        return getModuleObject(name);
    }


    /**
     * 为 模块对象 添加观察者
     *
     * @description 为模块对象添加观察者,这些观察者存储在subscribeObjects列表中
     *
     * @param name  模块名称
     * @param obj   模块对象
     */
    function addObservableForTheModuleObject(name, obj) {
        let moduleSubscribeObjects = subscribeObjects[name];
        for (let eventName in moduleSubscribeObjects) {
            let observers = moduleSubscribeObjects[eventName];//获取观察者列表
            for (let i = 0; i < observers.length; i++) {
                let observer = observers[i];
                obj.addObservable(eventName, observer);
            }

        }
    }

    /**
     * 获取模块对象
     *
     * @param name  模块名称
     *
     */
    function getModuleObject(name) {
        return moduleObjects[name];
    }

    /**
     * 添加模块对象到列表
     *
     * @param name 模块名称
     * @param obj  模块对象
     *
     */
    function addModuleObjectToList(name, obj) {

        if (!getModuleObject(name)) {
            moduleObjects[name] = obj;
        } else {
            if (isCover) {
                moduleObjects[name] = obj;
                //console.warn("----模块名'" + name + "'已经被定义,将会被覆盖!")
            }

            //throw new Error("----模块名'" + name + "'已经被定义!");
        }

    }

    /**
     * 参数是否合法
     *
     * @param requires  依赖列表  ['userModule','roleModule']
     * @param func      模块函数  function(){}
     */
    function checkArgumentsLegal(requires, func) {

        if (requires.constructor !== Array) {
            throw new Error("----依赖参数不合法,requires应为数组!");
        }

        if (typeof func !== "function") {
            throw new Error("----依赖参数不合法,func必须是function对象");
        }
    }

    /**
     * 寻找依赖
     *
     * @param requires  依赖列表
     */
    function searchDependency(requires) {

        let requireObjects = [];
        for (let i = 0; i < requires.length; i++) {

            let name = requires[i];
            let require = getModuleObject(name);// 通过'名称'获取所依赖的对象
            // 依赖不存在
            if (!require) {
                throw new Error("----模块'" + name + "'未定义!");
            }

            requireObjects.push(require);
        }

        return requireObjects;
    }

    /**
     * 同步父页面模块,此方法将(父页面)的模块放到(子页面)的模块定义列表中
     *
     * @param parentWindow  父窗口对象
     */
    function synchronizeParentModuleObjects(parentWindow) {

        let _parentWindow = parentWindow || window.top;

        if (!!_parentWindow && !!_parentWindow.swx) {

            let parentModuleObjects = _parentWindow.swx.getModuleObjects();
            if (!$.isEmptyObject(parentModuleObjects)) {
                for (let name in parentModuleObjects) {
                    addModuleObjectToList(name, parentModuleObjects[name]);
                }
            }

        }
    }

    if (isSynchronizeParentModule) {
        synchronizeParentModuleObjects();
    }

})(window.swx, jQuery);
