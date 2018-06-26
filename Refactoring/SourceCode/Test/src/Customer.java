import java.util.Enumeration;
import java.util.Vector;

/**
 * �˿�
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
			result += "\t" +each.getMovie().getTitle() +"\t" + String.valueOf(each.getCharge()) + "\n"; //�ع���ʱ����
		}
		result += "Amount owed is" + String.valueOf(getTotalCharge()) + "\n";   //�ع��ܷ��ü���
		result += "You earned" + String.valueOf(getTotalIFrequentRenterPoints()) + "frequent renter points";  //�ع����ּ���
		return result;
		
	}
	
	/**
	 * �ع����ּ���
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
	 * �ع��ܷ��ü���
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
