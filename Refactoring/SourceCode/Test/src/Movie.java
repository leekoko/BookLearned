/**
 * ӰƬ
 * @author liyb
 *
 */
public class Movie {
	
	public static final int CHILDRENS = 2; //��ͯ
	public static final int REGULAR = 0;  //��ͨƬ
	public static final int NEW_RELEASE = 1;  //��Ƭ
	
	private String _title;
	private int _priceCode;
	
	public Movie(String title, int priceCode){
		_title = title;
		setPriceCode(priceCode);    //ʹ��set������ֵ
	}
	
	public int getPriceCode(){
		return _price.getPriceCode();   //�޸Ļ�ȡ��ʽ
	}
	//�ع�setPriceCode�����ҳ�ʼ����Ӧ����
	private Price _price;
	public void setPriceCode(int arg){
		switch (arg) {
		case REGULAR:
			_price = new RegularPrice();
			break;
		case CHILDRENS:
			_price = new ChildrensPrice();
			break;
		case NEW_RELEASE:
			_price = new NewReleasePrice();
			break;
		default:
			throw new IllegalArgumentException("Incorrect Price Code");
		}
	}
	
	public String getTitle(){
		return _title;
	}
	
	/**
	 * �ع�switch������(Ǩ��)
	 * @param daysRented ��ó����
	 * @return
	 */
	public double getCharge(int daysRented) {  
		return _price.getCharge(daysRented);
	}
	/**
	 * �ع����ּ��㷽����Ǩ�ƣ�
	 * @return
	 */
	public int getFrequentRenterPoints(int daysRented) {
		if((getPriceCode() == Movie.NEW_RELEASE) && daysRented > 1){
			return 2;
		}else{
			return 1;
		}
	}
	
}
