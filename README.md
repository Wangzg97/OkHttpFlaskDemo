# OkHttpFlaskDemo
flask与安卓okhttp交互demo

#### 说明

##### master：
- 基本功能：通过OkHttp3实现安卓客户端与flask后台的简单数据交互
- 注意事项：
  - 由于 Android P 限制了明文流量的网络请求，非加密的流量请求都会被系统禁止掉，如果当前应用的请求是 htttp 请求，而非 https ,
  这样就会导系统禁止当前应用进行该请求，如果 WebView 的 url 用 http 协议，同样会出现加载失败，https 不受影响。为此，OkHttp3 做了检查，
  所以如果使用了明文流量，默认情况下，在 Android P 版本 OkHttp3 就抛出异常: CLEARTEXT communication to ” + host + ” not permitted by network security policy。
    - 简单一点的方法，在AndroidMainfest.xml中<Application下添加android:usesCleartextTraffic="true"即可解决
  - 安卓端访问的服务器地址，windows下可进命令行用ipconfig查看。
  - *flask_app.py*: 
    - 如果用的是pycharm，那么用app.run(host='0.0.0.0', port=8090)的方式可能参数不会生效，设计的服务器地址仍为http://127.0.0.1:5000/
      - 解决方法：在pycharm中，Run->Edit Configuration中，设置Additional options。例如这里我用的是--host=0.0.0.0 --port=8090
      - 如果用命令行运行代码则不会出现此问题。
      
 ##### second：
 - 基本功能：通过OkHttp3实现安卓客户端与flask后台的图片传输
