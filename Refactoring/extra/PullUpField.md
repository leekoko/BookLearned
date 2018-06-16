# 提升字段

Z：提升字段与提升方法相似，就是将子类中通用的属性提取到基类中来

原代码：

```java
public abstract class Account{

}

class CheckingAccount extends Account{
    private Double _minimumCheckingBalance = 5.0;
}

class SavingsAccount extends Account{
    private Double _minimumCheckingBalance = 5.0;
}
```

重构后代码：

```java
public abstract class Account{
	private Double _minimumCheckingBalance = 5.0;
}

class CheckingAccount extends Account{
	
}

class SavingsAccount extends Account{
	
}
```

# 降低字段   

Z：相反的，如果基类中的属性只是在某个子类中使用，直接在子类中声明即可

原代码：

```java
public abstract class Account{
	private Double _minimumCheckingBalance = 5.0;
}

class CheckingAccount extends Account{
	
}

class SavingsAccount extends Account{
	
}
```

重构后代码：

```java
public abstract class Account{
}

class CheckingAccount extends Account{
	private Double _minimumCheckingBalance = 5.0;
	
}

class SavingsAccount extends Account{
	
}
```