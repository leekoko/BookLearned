
public class RegularPrice extends Price{

	@Override
	int getPriceCode() {
		return Movie.REGULAR;
	}
	/**
	 * �ع�switch������(Ǩ��)
	 * @param daysRented ��ó����
	 * @return
	 */
	public double getCharge(int daysRented) {  
		double result = 2;
		if(daysRented > 2){
			result += (daysRented - 2) * 1.5;
		}
		return result;
	}

}
