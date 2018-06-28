import java.util.HashMap;

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


