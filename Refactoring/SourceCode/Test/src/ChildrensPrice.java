
public class ChildrensPrice extends Price{

	@Override
	int getPriceCode() {
		return Movie.CHILDRENS;
	}
	/**
	 * 重构switch方法块(迁移)
	 * @param daysRented 租贸长度
	 * @return
	 */
	public double getCharge(int daysRented) {  
		double result = 1.5;
		if(daysRented > 3){
			result += (daysRented - 3) * 1.5;
		}
		return result;
	}
}
