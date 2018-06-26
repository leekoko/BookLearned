import java.util.Enumeration;
import java.util.Vector;

/**
 * 顾客
 * @author liyb
 */
public class Customer {
	private String _name;
	private Vector _rentals = new Vector();
	
	public Customer(String name){
		_name = name;
	}
	
	public void addRental(Rental arg){
		_rentals.addElement(arg);
	}
	
	public String getName(){
		return _name;
	}
	
	public String statement(){
		Enumeration rentals = _rentals.elements();
		String result = "Rental Record for" + getName() + "\n";
		while(rentals.hasMoreElements()){
			Rental each = (Rental) rentals.nextElement();
			result += "\t" +each.getMovie().getTitle() +"\t" + String.valueOf(each.getCharge()) + "\n"; //重构临时变量
		}
		result += "Amount owed is" + String.valueOf(getTotalCharge()) + "\n";   //重构总费用计算
		result += "You earned" + String.valueOf(getTotalIFrequentRenterPoints()) + "frequent renter points";  //重构积分计算
		return result;
		
	}
	
	/**
	 * 重构积分计算
	 * @return
	 */
	private int getTotalIFrequentRenterPoints(){
		int result = 0;
		Enumeration rentals = _rentals.elements();
		while(rentals.hasMoreElements()){
			Rental each = (Rental) rentals.nextElement();
			result += each.getFrequentRenterPoints();
		}
		return result;
	}
	
	/**
	 * 重构总费用计算
	 * @return
	 */
	private double getTotalCharge(){
		double result = 0;
		Enumeration rentals = _rentals.elements();
		while(rentals.hasMoreElements()){
			Rental each = (Rental) rentals.nextElement();
			result += each.getCharge();
		}
		return result;
	}


	
	
}
