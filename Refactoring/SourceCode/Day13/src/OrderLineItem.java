import java.util.List;

public class OrderLineItem{
    private Double Price;

	public Double getPrice() {
		return Price;
	}

	public void setPrice(Double price) {
		Price = price;
	}
       
}

class Order{
	private List<OrderLineItem> OrderLineItems;
	private List<Double> Discounts;
	private Double Tax;
	
    public List<OrderLineItem> getOrderLineItems() {
		return OrderLineItems;
	}
	public void setOrderLineItems(List<OrderLineItem> orderLineItems) {
		OrderLineItems = orderLineItems;
	}
	public List<Double> getDiscounts() {
		return Discounts;
	}
	public void setDiscounts(List<Double> discounts) {
		Discounts = discounts;
	}
	public Double getTax() {
		return Tax;
	}
	public void setTax(Double tax) {
		Tax = tax;
	}
	
	public Double Calculate(){
		Double subTotal = 0.0;
        //Total up line items
		for (OrderLineItem lineItem : OrderLineItems) {
			subTotal += lineItem.getPrice();
		}
		//substract Discounts
		for (Double discount : Discounts) {
		    subTotal -= discount;	
		}
        //Calculate Tax
        Double tax = subTotal*Tax;
        //calculate GrandTotal
        Double grandtotal = subTotal + tax;

        return grandtotal;
    }
}