
public class RegularPrice extends Price{

	@Override
	int getPriceCode() {
		return Movie.REGULAR;
	}
	/**
	 * 重构switch方法块(迁移)
	 * @param daysRented 租贸长度
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
