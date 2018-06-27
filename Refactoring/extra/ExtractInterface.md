# 提取接口    

D：原代码如下（原文的源代码我觉得有点问题，进行了修改）

```java
class ClassRegistration{
    public void CreateA(){
    	// create registration code
    }
    public void CreateB(){
    	// create registration code
    }

    private Double Total;

	public Double getTotal() {
		return Total;
	}

	public void setTotal(Double total) {
		Total = total;
	}
    
}

public class RegistrationProcessor{
    public Double ProcessRegistrationA(ClassRegistration registration)
    {
        registration.CreateA();
        return registration.getTotal();
    }
    public Double ProcessRegistrationB(ClassRegistration registration)
    {
    	registration.CreateA();
    	return registration.getTotal();
    }

}
```

M：这段代码有什么问题呢？

Z：CreateA和CreateB方法的代码是相似的。而且当我们要添加CreateS的时候，就要分别在ClassRegistration和RegistrationProcessor类中添加相应的代码。   

M：那需要怎么做呢？

Z：把相似的方法接口提取成接口，不同的方法用不同的类去实现

```java
interface IClassRegistration{
	public void Create();
	public Double getTotal();
}

class ClassRegistrationA implements IClassRegistration{

	@Override
	public void Create() {
    	// create registration code   A
	}
    
    private Double Total;

	public Double getTotal() {
		return Total;
	}

	public void setTotal(Double total) {
		Total = total;
	}

}
class ClassRegistrationB implements IClassRegistration{

	@Override
	public void Create() {
    	// create registration code   B
	}
    
    private Double Total;

	public Double getTotal() {
		return Total;
	}

	public void setTotal(Double total) {
		Total = total;
	}

}

public class RegistrationProcessor{
    public Double ProcessRegistrationA(IClassRegistration registration)
    {
        registration.Create();
        return registration.getTotal();
    }
}
```

这样的话，添加相似的代码，只要添加相应的类即可。   

