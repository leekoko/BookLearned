
abstract class Price {
	abstract int getPriceCode();
	
	/**
	 * �ع�switch������(Ǩ��)
	 * @param daysRented ��ó����
	 * @return
	 */
	abstract double getCharge(int daysRented);
	
	/**
	 * �ع����ּ��㷽����Ǩ�ƣ�
	 * @return
	 */
	public int getFrequentRenterPoints(int daysRented) {
		return 1;
	}
	
}
