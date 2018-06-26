/**
 * 影片
 * @author liyb
 *
 */
public class Movie {
	
	public static final int CHILDRENS = 2; //儿童
	public static final int REGULAR = 0;  //普通片
	public static final int NEW_RELEASE = 1;  //新片
	
	private String _title;
	private int _priceCode;
	
	public Movie(String title, int priceCode){
		_title = title;
		setPriceCode(priceCode);    //使用set方法赋值
	}
	
	public int getPriceCode(){
		return _price.getPriceCode();   //修改获取方式
	}
	//重构setPriceCode，并且初始化对应对象
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
	 * 重构switch方法块(迁移)
	 * @param daysRented 租贸长度
	 * @return
	 */
	public double getCharge(int daysRented) {  
		return _price.getCharge(daysRented);
	}
	/**
	 * 重构积分计算方法（迁移）
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
