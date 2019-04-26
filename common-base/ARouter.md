#common-base  公共基础模块
##遇到的一些坑
####ARouter,https://github.com/alibaba/ARouter/blob/master/README_CN.md
1.apt配置(用到ARouter的module必须加上)

    //java配置
    javaCompileOptions {
                 annotationProcessorOptions {
                     arguments = [AROUTER_MODULE_NAME: project.getName()]
                 }
             }
             
    //kotlin 配置    
    apply plugin: 'kotlin-kapt'
    kapt {
        arguments {
            arg("moduleName", project.getName())
        }
    }
3.注解写法

    @Route(path = "/xxx/activity")
    一般 xxx 会作为组使用，所以两个module 不要 xxx 内容相等,否则自己挖坑自己填去吧
3.依赖

    dependencies {
        compile 'com.alibaba:arouter-api:x.x.x'
        kapt 'com.alibaba:arouter-compiler:x.x.x'
        ...
    }
4.详细的API说明
    
    // 构建标准的路由请求
    ARouter.getInstance().build("/home/main").navigation();
    
    // 构建标准的路由请求，并指定分组
    ARouter.getInstance().build("/home/main", "ap").navigation();
    
    // 构建标准的路由请求，通过Uri直接解析
    Uri uri;
    ARouter.getInstance().build(uri).navigation();
    
    // 构建标准的路由请求，startActivityForResult
    // navigation的第一个参数必须是Activity，第二个参数则是RequestCode
    ARouter.getInstance().build("/home/main", "ap").navigation(this, 5);
    
    // 直接传递Bundle
    Bundle params = new Bundle();
    ARouter.getInstance()
        .build("/home/main")
        .with(params)
        .navigation();
    
    // 指定Flag
    ARouter.getInstance()
        .build("/home/main")
        .withFlags();
        .navigation();
    
    // 获取Fragment
    Fragment fragment = (Fragment) ARouter.getInstance().build("/test/fragment").navigation();
                        
    // 对象传递
    ARouter.getInstance()
        .withObject("key", new TestObj("Jack", "Rose"))
        .navigation();
    
    // 觉得接口不够多，可以直接拿出Bundle赋值
    ARouter.getInstance()
            .build("/home/main")
            .getExtra();
    
    // 转场动画(常规方式)
    ARouter.getInstance()
        .build("/test/activity2")
        .withTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
        .navigation(this);
    
    // 转场动画(API16+)
    ActivityOptionsCompat compat = ActivityOptionsCompat.
        makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
    
    // ps. makeSceneTransitionAnimation 使用共享元素的时候，需要在navigation方法中传入当前Activity
    
    ARouter.getInstance()
        .build("/test/activity2")
        .withOptionsCompat(compat)
        .navigation();
            
    // 使用绿色通道(跳过所有的拦截器)
    ARouter.getInstance().build("/home/main").greenChannel().navigation();
    
    // 使用自己的日志工具打印日志
    ARouter.setLogger();
    
    // 使用自己提供的线程池
    ARouter.setExecutor();