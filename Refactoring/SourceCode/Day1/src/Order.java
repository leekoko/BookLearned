import java.util.ArrayList;
import java.util.List;

public class Order{
	private List<OrderLine> _orderLines = new ArrayList<OrderLine>();
	private double _orderTotal = 0;
	
	public List<OrderLine> getOrderLines(){
		return _orderLines;
	}
	
	public void add(OrderLine orderLine){
        _orderTotal += orderLine.getTotal();
        _orderLines.add(orderLine);
    }

	public void removeOrderLine(OrderLine orderLine){
        int site = _orderLines.indexOf(orderLine);
        if (site < 0){
        	return;
        }
        _orderTotal -= orderLine.getTotal();
        _orderLines.remove(site);
	}
	

}

class OrderLine{
    public double total = 0;
    
    public OrderLine(double total) {
    	this.total = total;
    }

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}
