/**
 * ��ó
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
	 * �ع�switch������(Ǩ��)
	 * @param each
	 * @return
	 */
	public double getCharge() {
		return _movie.getCharge(_daysRented);
	}
	/**
	 * �ع����ּ��㷽��
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
