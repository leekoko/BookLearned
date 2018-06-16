# Basic BeanFactory   

M：spring构造开始，首先要创建什么文件呢？

Z：我们采用的是maven项目，pom文件里面一开始只需要有junit就可以了

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>cn.leekoko</groupId>
  <artifactId>litespring</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  
  <dependencies>
	<!-- 单元测试 -->
	<dependency>
		<groupId>junit</groupId>
		<artifactId>junit</artifactId>
		<version>4.12</version>
		<scope>test</scope>
	</dependency>
      
  </dependencies>
  
  
</project>
```

D：现在通过TDD要实现一个功能：从xml配置文件中获取Bean的定义&Bean的实例

Z：简单来说就是Spring具有以下功能：用户只需配置好xml文件，就能通过Spring容器将Bean对象获取出来，而我们这里就是要实现这种搬运的效果。

M：那要做从xml配置文件中获取指定Bean对象，首先需要有xml文件吧？

Z：xml文件内容如下，由于是测试的xml文件，放在test/resources目录下

petstore-v1.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
		http://www.springframework.org/schema/beans/spring-beans.xsd">
	
	<bean id="petStore" 
	class="org.litespring.service.v1.PetStoreService">
	</bean>

</beans>
```

D：有了素材之后，第一步是编写预想中的测试案例

M：要怎么测试一个 通过xml中实例化一个类出来的方法呢？

Z：如下代码

```java
	@Test
	public void testGetBean() {
		//读取配置文件
		BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
		BeanDefinition bd = factory.getBeanDefinition("petStore");
		//校验配置文件属性
		assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());
		//实例化对象
		PetStoreService petStore = (PetStoreService)factory.getBean("petStore");
		//判断对象成功实例化否
		assertNotNull(petStore);
	}
```

M：为什么用不存在的``BeanFactory``来获取``petstore-v1.xml``配置文件呢？

Z：这是一种构造读取配置文件的思路，首先读取，然后实例化。只不过读取分成了两步，先获取配置文件，再获取bean对象

```java
		//读取配置文件
		BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
		BeanDefinition bd = factory.getBeanDefinition("petStore");
```

M：那为什么``BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");``中``BeanFactory``为什么要使用接口呢？

Z：这是面向接口编程。在系统分析和架构中，分清层次和依赖关系，每个层次不是直接向其上层提供服务（即不是直接实例化在上层中），而是通过定义一组接口，仅向上层暴露其接口功能，上层对于下层仅仅是接口依赖，而不依赖具体类。   

M：所以``BeanFactory``接口暴露的就是:

- 获取BeanFactory接口对象的构造方法``new DefaultBeanFactory("petstore-v1.xml");``
- 获取配置文件信息接口对象``factory.getBeanDefinition`` ，从而获取配置文件内容``bd.getBeanClassName()``
- 获取Bean对象``(PetStoreService)factory.getBean("petStore")``    

这里获取Bean对象就不使用面向接口了么。

D：第一步写完测试案例，并且把相关的类生成出来，TDD编程的第二步就是检验是否会报错，直接运行一次，看看有没正常报错。   

D：第三步就是开始填充相关代码，保证java代码顺利通过测试。   







---

有一个Sun来的人一直Debug，最后组里没人比他认识更多。流程我也可以学下来。





