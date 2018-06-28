# 策略代替Switch    

D：原代码如下

```java
public class ClientCode{
    public Double CalculateShipping(){
        ShippingInfo shippinginfo = new ShippingInfo();
        return shippinginfo.CalclateShippingAmount(State.Alaska);
    }
}

enum State{
    Alaska,
    NewYork,
    Florida
}

class ShippingInfo{

    public Double CalclateShippingAmount(State shipToState){
    	
        switch (shipToState){
            case Alaska:
                return GetAlaskaShippingAmount();
            case NewYork:
                return GetNewYorkShippingAmount();
            case Florida:
                return GetFloridaShippingAmount();
            default:
                return 0.0;
        }
    }

    private Double GetAlaskaShippingAmount(){
        return 15.0;
    }

    private Double GetNewYorkShippingAmount(){
        return 10.0;
    }

    private Double GetFloridaShippingAmount(){
        return 3.0;
    }
}
```

Z：这段代码使用到了switch，这样如果要修改这个方法，耦合度太高。

M：那要怎么做呢？

Z：可以使用策略的设计模式，把每一种情况都封装进类中。

```java
public class ClientCode{
    public Double CalculateShipping(){
        ShippingInfo shippinginfo = new ShippingInfo();
        return shippinginfo.CalculateShippingAmount(State.Alaska);
    }
}

enum State{
    Alaska,
    NewYork,
    Florida
}

class ShippingInfo{
	private HashMap<State, IShippingCalculation> shippingCalculations = null;
    public HashMap<State, IShippingCalculation> getShippingCalculations() {
		return shippingCalculations;
	}
	public void setShippingCalculations(HashMap<State, IShippingCalculation> shippingCalculations) {
		this.shippingCalculations = shippingCalculations;
	}

	public ShippingInfo(){
    	shippingCalculations = new HashMap<State, IShippingCalculation>();
    	shippingCalculations.put(State.Alaska, new AlaskShippingCaculation());
    	shippingCalculations.put(State.NewYork, new NewYorkShippingCaculation());
    	shippingCalculations.put(State.Florida, new FloridaShippingCaculation());
    }
	
    public double CalculateShippingAmount(State shipToState){
        return shippingCalculations.get(shipToState).Caculate();
    }

}

interface IShippingCalculation{
    double Caculate();
}

class AlaskShippingCaculation implements IShippingCalculation{
	public State State;
	
	public State getState() {
		return State.Alaska;
	}
	public double Caculate() {
        return 15.0;
	}
}

class NewYorkShippingCaculation implements IShippingCalculation{
	public State State;
	
	public State getState() {
		return State.NewYork;
	}
	public double Caculate() {
		return 10.0;
	}
}

class FloridaShippingCaculation implements IShippingCalculation{
	public State State;
	
	public State getState() {
		return State.Florida;
	}
	public double Caculate() {
		return 3.0;
	}
}
```

M：将方法提取成类出来，然后就可以将switch匹配改为map匹配

```java
	public ShippingInfo(){
    	shippingCalculations = new HashMap<State, IShippingCalculation>();
    	shippingCalculations.put(State.Alaska, new AlaskShippingCaculation());
    	shippingCalculations.put(State.NewYork, new NewYorkShippingCaculation());
    	shippingCalculations.put(State.Florida, new FloridaShippingCaculation());
    }
	
    public double CalculateShippingAmount(State shipToState){
        return shippingCalculations.get(shipToState).Caculate();
    }
```

由于类之间有共通之处，所以再将其接口提取出来

```java
interface IShippingCalculation{
    double Caculate();
}

class AlaskShippingCaculation implements IShippingCalculation{
	public State State;
	
	public State getState() {
		return State.Alaska;
	}
	public double Caculate() {
        return 15.0;
	}
}
```