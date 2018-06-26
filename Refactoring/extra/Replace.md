# 使用委托代替继承  

D：原代码如下

```java
public class Sanitation{
    public String WashHands(){
        return "Cleaned!";
    }

}

class Child extends Sanitation{

}
```

Z：Sanitation是公共设施类，而Child是孩子类，两者没有直接的逻辑关系。但是为了使用``WashHands()``方法，让孩子类继承公共设施类是不适合的。

M：那要怎么操作呢?

Z：可以通过构造函数注入的方式将Sanitation对象传递给Child，就可以调用该类方法了。

```java
public class Sanitation{
    public String WashHands(){
        return "Cleaned!";
    }

}

class Child{
	private Sanitation sanitation;

	public Sanitation getSanitation() {
		return sanitation;
	}

	public void setSanitation(Sanitation sanitation) {
		this.sanitation = sanitation;
	}
	
	public Child(){
		sanitation = new Sanitation();
	}

    public String WashHands(){
        return sanitation.WashHands();
    }
}
```

M：总结一下，没有逻辑关系的类不用继承，用对象注入。   