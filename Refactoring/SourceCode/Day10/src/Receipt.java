import java.util.List;

public class Receipt{
	private List<Double> Discounts;
	private List<Double> ItemTotals;

    public List<Double> getDiscounts() {
		return Discounts;
	}
	public void setDiscounts(List<Double> discounts) {
		Discounts = discounts;
	}

	public List<Double> getItemTotals() {
		return ItemTotals;
	}
	public void setItemTotals(List<Double> itemTotals) {
		ItemTotals = itemTotals;
	}

	public Double CalculateGrandTotal(){
        Double subTotal = CalculateSubTotal();
        subTotal = CalculateDiscounts(subTotal);
        subTotal = CalculateTax(subTotal);
        return subTotal;
    }
	
	private Double CalculateTax(Double subTotal) {
		Double tax = subTotal * 0.065;
        subTotal += tax;
		return subTotal;
	}
	private Double CalculateDiscounts(Double subTotal) {
		if (Discounts.size() > 0){
        	for (Double discount : Discounts) {
        		subTotal -= discount;
			}
        }
		return subTotal;
	}
	private Double CalculateSubTotal() {
		Double subTotal = 0.0;
        for (Double itemTotal : ItemTotals) {
        	subTotal += itemTotal;
		}
		return subTotal;
	}
}
