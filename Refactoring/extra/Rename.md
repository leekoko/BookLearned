# 重命名

D：以下是原代码

```java
public class Person{
	public String FN;
    public String getFN() {
		return FN;
	}
	public void setFN(String fN) {
		FN = fN;
	}

	public Double ClcHrlyPR()
    {
        // code to calculate hourly payrate
        return 0.0;
    }
}
```

M：这段代码有什么缺陷？

Z：看得懂它属性的意思吗，如果改成以下样子呢

```java
public class Person{
	public String FirstName ;
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}


	public Double CalculateHourlyPay()
    {
        // code to calculate hourly payrate
        return 0.0;
    }
}
```

M：Person类中有一个属性是职员名称，有一个方法是用来计算时薪的。一目了然。有意义的名字可以减少许多注释。













