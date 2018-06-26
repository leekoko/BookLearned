/**
 * 租贸
 * @author liyb
 *
 */
public class Rental {
	private Movie _movie;
	private int _daysRented;
	
	public Rental(Movie movie, int daysRented){
		_movie = movie;
		_daysRented = daysRented;
	}
	
	public int getDaysRented(){
		return _daysRented;
	}
	
	public Movie getMovie(){
		return _movie;
	}
	
	/**
	 * 重构switch方法块(迁移)
	 * @param each
	 * @return
	 */
	public double getCharge() {
		return _movie.getCharge(_daysRented);
	}
	/**
	 * 重构积分计算方法
	 * @return
	 */
	public int getFrequentRenterPoints() {
		int points = 0;
		points ++;
		if((getMovie().getPriceCode() == Movie.NEW_RELEASE) && getDaysRented() > 1){
			points ++;
		}
		return points;
	}
	
}
