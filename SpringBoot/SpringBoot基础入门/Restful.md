# REST 和 RESTful

>  Representational State Transfer （缩写：REST）直译就是表现层状态转换，它是一种便于不同软件/程序在网络中互相传递信息 的架构风格。符合这种架构风格的网络服务 可被称为 RESTful 风格。 

**RESTFUL特点包括：**

- 1、资源：每一个 URI 都代表一种资源；
- 2、方法：客户端使用GET、POST、PUT、DELETE4个表示操作方式的动词对服务端资源进行操作：GET用来获取资源，POST用来新建资源（也可以用于更新资源），PUT用来更新资源，DELETE用来删除资源；
- 3、方法操作资源：通过不同方法来操作资源，导致了资源不同的表现形式。
- 4、操作结果表现：资源的表现形式可以是JSON，XML或者HTML等；
- 5、无状态：客户端与服务端之间的交互在请求之间是无状态的，从客户端到服务端的每个请求都必须包含理解请求所必需的信息。

**总结就是：** 上面的提到的特点，可以总结为“ `用明确的方法 操作 语义清晰的资源，来呈现不同的资源表现形式`”。

- 明确的方法是指HTTP的get,post方法；
- 清晰的资源指一个语义表达清晰的网址；
- 不同的资源表现形式是指导致了资源的状态变化。

**即：** 看到 URI 就知道要资源什么     （是什么） 看到 HTTP 方法 就知道干什么   （怎么做） 看到 HTTP 响应，就知道结果如何 （结果如何）

# 示例

用示例表示。

**比如：**

列举所有商品 GET [http://www.store.com/products](https://links.jianshu.com/go?to=http%3A%2F%2Fwww.store.com%2Fproducts) *备注：这里表达了，将获得这个网站下的所有商品。*

呈现某一件商品 GET [http://www.store.com/products/12345](https://links.jianshu.com/go?to=http%3A%2F%2Fwww.store.com%2Fproducts%2F12345) *备注：这里表达了，将获得第 12345 号 商品。*

下单购买 POST [http://www.store.com/orders](https://links.jianshu.com/go?to=http%3A%2F%2Fwww.store.com%2Forders) <purchase-order> <item> ... </item> </purchase-order> *备注：这里表达了，使用POST方法发送订单信息的内容*

# 参考

[https://baike.baidu.com/item/RESTful/4406165?fr=aladdin](https://links.jianshu.com/go?to=https%3A%2F%2Fbaike.baidu.com%2Fitem%2FRESTful%2F4406165%3Ffr%3Daladdin)

[https://zh.wikipedia.org/wiki/%E8%A1%A8%E7%8E%B0%E5%B1%82%E7%8A%B6%E6%80%81%E8%BD%AC%E6%8D%A2](https://links.jianshu.com/go?to=https%3A%2F%2Fzh.wikipedia.org%2Fwiki%2F%E8%A1%A8%E7%8E%B0%E5%B1%82%E7%8A%B6%E6%80%81%E8%BD%AC%E6%8D%A2)

[https://www.zhihu.com/question/28557115](https://links.jianshu.com/go?to=https%3A%2F%2Fwww.zhihu.com%2Fquestion%2F28557115)