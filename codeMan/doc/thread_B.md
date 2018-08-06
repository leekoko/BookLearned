# 我是一个线程（分析）   

D：[原文](thread_A.md)。这篇文章写得很精彩，算是刘欣大神的代表作了。    

M：这里的就绪车间，运行车间是什么呢？

```
“去哪儿处理？”

“跟着指示走，先到就绪车间。”

果然，地上有指示箭头，跟着它来到了一间明亮的大屋子，这里已经有不少线程了，大家都很紧张，好像时刻准备着往前冲。

我刚一进来，就听见广播说：“0x3704，进入车间。”

我赶紧往前走，身后有很多人议论。

“他太幸运了，刚进入就绪状态就能运行。”

“是不是有关系？”

“不是，你看人家的优先级多高啊，唉！”

```

Z：这里描述的是线程的状态，线程总共有五种状态，一个时间点上有且只能存在一种状态。

- 新建New：创建未启动
- 运行Runable：运行包括  Ready等候执行  和  Running执行中，文中的就绪车间应该就是指Ready（等待CPU分配执行时间），运行自然就是Running。
- 等待Waiting：这种等待不同于Ready，CPU不会主动分配执行时间给他，而是要被其他线程唤醒。当然也可能永远不会被唤醒。
- 有期等待Timed Waiting：CPU也不会为它分配时间，但是系统会在一段时间后唤醒它。
- 阻塞Blocked：当等待进入同步区域时，线程就会进入阻塞状态，这个时候就等待另一个线程放弃排他锁，本线程就会获得排它锁。

M：这几个步骤对应实际的什么情况？

```
第一步：从包裹中取出参数。

打开包裹，里边有个HttpRequest对象，可以取到userName、 password两个参数。

第二步：执行登录操作。

奥，原来是有人要登录啊，我把userName、password交给数据库服务员，他拿着数据，慢腾腾地走了。

他怎么这么慢？不过我是不是正好可以在车间里多待一会儿？反正也没法执行第三步。

就在这时，车间里的广播响了：“0x3704，我是CPU，记住你正在执行的步骤，然后马上带着包裹离开！”

我慢腾腾地开始收拾。

“快点，别的线程马上就要进来了。”
```

Z：







https://www.ibm.com/developerworks/cn/education/java/j-threads/j-threads.html


