
abstract class Price {
	abstract int getPriceCode();
	
	/**
	 * 重构switch方法块(迁移)
	 * @param daysRented 租贸长度
	 * @return
	 */
	abstract double getCharge(int daysRented);
	
	/**
	 * 重构积分计算方法（迁移）
	 * @return
	 */
	public int getFrequentRenterPoints(int daysRented) {
		return 1;
	}
	
}
