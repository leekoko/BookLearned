
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