# kotLinDemo
初学kotlin

这个项目只是我初学的一个Demo
里面用了kotlin+retrofit2+rxjava2
代码结构模式是MVP

第二版(app2)：

2018/08/07  第二个版本是在1.2版本的kotlin基础上编写，主要是再次封装retrofit网络请求，
添加接口在ApiService
添加接口实现在ApiImp
请求调用代码结构如下

                  loginByNamea是APiImp里实现的接口方法
									这里用到的是RxLife的插件用来绑定接口与Activity或Fragment的生命周期
									ApiSubscriber是请求结果的处理封装类，注意ApiSubscriber<T> T是更接口和接口实现方法的返回值一致
ApiImp.instance.loginByName(loginRequest)
                .bindUntilEvent(lifecycleProvider, ActivityEvent.DESTROY)
                .subscribe(object : ApiSubscriber<BaseApiResultData<UserInfo>>() {
                    override fun onSuccess(data: BaseApiResultData<UserInfo>) {
                        返回数据
                        myView.getUserInfo(data.body)
                        Constans.appToken = data.body.token
                        getSchoolInfo()
                    }

                    override fun onCompleted() {
                    //请求所有处理完毕
                    }

                    override fun onFailure(error: ErrorResponse) {
                    //请求错误
                        myView.showLoadingDialog(false)
                    }

                    override fun TokenInvalid() {
                    //Token失效
                        myView.TokenInvalid()
                    }
                })


主要技能点：
1.recyclerview的adpata 和item 的点击事件
2.retrofit2的请求和处理封装
3.Activty和Fragment的base父类简单的处理方法
=。= 差不多就这些，其他的看看吧,项目可以直接跑起来，个人觉得的难点是空处理，感觉比java还难用~~~
