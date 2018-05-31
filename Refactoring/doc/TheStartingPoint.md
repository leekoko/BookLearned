# TheStartingPoint/起点   

M：第一个案例是什么呢?

Z：是一个video store的简单程序   

D：这是一段影片租贸店的程序

Moive.java

```java
/**
 * 影片
 * @author liyb
 */
public class Movie {
	
	public static final int CHILDRENS = 2; //儿童
	public static final int REGULAR = 0;  //普通片
	public static final int NEW_RELEASE = 1;  //新片
	
	private String _title;
	private int _priceCode;
	
	public Movie(String title, int priceCode){
		_title = title;
		_priceCode = priceCode;
	}
	
	public int getPriceCode(){
		return _priceCode;
	}
	
	public void setPriceCode(int arg){
		_priceCode = arg;
	}
	
	public String getTitle(){
		return _title;
	}
	
}
```

Rental.java

```java
/**
 * 租贸
 * @author liyb
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
	
}
```

Customer.java

```java
import java.util.Enumeration;
import java.util.Vector;

/**
 * 顾客
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
		double totalAmount = 0;
		int frequentRenterPoints = 0;
		Enumeration rentals = _rentals.elements();
		String result = "Rental Record for" + getName() + "\n";
		while(rentals.hasMoreElements()){
			double thisAmount = 0;
			Rental each = (Rental) rentals.nextElement();
			
			switch(each.getMovie().getPriceCode()){
				case Movie.REGULAR:
					thisAmount += 2;
					if(each.getDaysRented() > 2){
						thisAmount += (each.getDaysRented() - 2) * 1.5;
					}
					break;
				case Movie.NEW_RELEASE:
					thisAmount += each.getDaysRented() * 3;
					break;
				case Movie.CHILDRENS:
					thisAmount += 1.5;
					if(each.getDaysRented() > 3){
						thisAmount += (each.getDaysRented() - 3) * 1.5;
					}
					break;
			}
			frequentRenterPoints ++;
			if((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) && 
					each.getDaysRented() > 1){
				frequentRenterPoints ++;
			}
			result += "\t" +each.getMovie().getTitle() +"\t" + String.valueOf(thisAmount) + "\n";
			totalAmount += thisAmount;
			
		}
		result += "Amount owed is" + String.valueOf(totalAmount) + "\n";
		result += "You earned" + String.valueOf(frequentRenterPoints) + "frequent renter points";
		return result;
		
	}
}
```

M：这段代码有什么缺陷么？

Z：不符合面向对象的过程，``statement``方法做的事情太多了。如果要新增一个功能，这个方法根本就不能复用，那怎么办？

M：就只能重写一个类似的方法了，例如``htmlStatement``。   

Z：这样代码之间就存在大量重复，就是如果需求出现变化，还得多处修改以保持一致。所以这段代码最好进行重构。

M：重构要做什么呢？

Z：步骤如下

1. 首先要建立测试机制，测试代码还需要能进行自动校对（正确就输出OK）。

2. 寻找局部变量&参数，例如例子中的 each ，thisAmount

   each 不会变。不变可以当作参数传入。

   thisAmount 会变。只有一个变量，可以作为返回值。

D：例如以下重构，把switch方法块提取出来 (用Eclipse的Extract Method可以快速实现)

Customer.java

```java
/**
 * 顾客
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
		double totalAmount = 0;
		int frequentRenterPoints = 0;
		Enumeration rentals = _rentals.elements();
		String result = "Rental Record for" + getName() + "\n";
		while(rentals.hasMoreElements()){
			double thisAmount = 0;
			Rental each = (Rental) rentals.nextElement();
			
			thisAmount = amountFor(each);    //重构
			
			frequentRenterPoints ++;
			if((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) && 
					each.getDaysRented() > 1){
				frequentRenterPoints ++;
			}
			result += "\t" +each.getMovie().getTitle() +"\t" + String.valueOf(thisAmount) + "\n";
			totalAmount += thisAmount;
			
		}
		result += "Amount owed is" + String.valueOf(totalAmount) + "\n";
		result += "You earned" + String.valueOf(frequentRenterPoints) + "frequent renter points";
		return result;
		
	}
	/**
	 * 重构switch方法块
	 * @param each
	 * @return
	 */
	private double amountFor(Rental each) {
		double thisAmount = 0;
		switch(each.getMovie().getPriceCode()){
			case Movie.REGULAR:
				thisAmount += 2;
				if(each.getDaysRented() > 2){
					thisAmount += (each.getDaysRented() - 2) * 1.5;
				}
				break;
			case Movie.NEW_RELEASE:
				thisAmount += each.getDaysRented() * 3;
				break;
			case Movie.CHILDRENS:
				thisAmount += 1.5;
				if(each.getDaysRented() > 3){
					thisAmount += (each.getDaysRented() - 3) * 1.5;
				}
				break;
		}
		return thisAmount;
	}
}
```

M：这是我为``amountFor``方法编写的测试案例：

```java
	@Test
	public void amountForTest() {
		//顾客对象  初始化名称
		Customer customer = new Customer("liyb");  
		//影片名，新片
		int type = Movie.CHILDRENS;
		Movie movie = new Movie("影片名字Test1",type);   
		//片子    天数
		Rental rental = new Rental(movie,5);  
		//添加影片对象
		customer.addRental(rental);
		
		double result = customer.amountFor(rental);
		double checkPrice = 4.5;
		if(result == checkPrice){
			System.out.println("TRUE");
		}else{
			System.out.println(result);
		}
	}
```

Z：``amountFor()``目前还不够优秀，代码可读性差，所以还需要修改其变量名(Eclipse 批量命名 Alt + Shift + r)：

```java
	/**
	 * 重构switch方法块
	 * @param each
	 * @return
	 */
	public double amountFor(Rental aRental) {
		double result = 0;
		switch(aRental.getMovie().getPriceCode()){
			case Movie.REGULAR:
				result += 2;
				if(aRental.getDaysRented() > 2){
					result += (aRental.getDaysRented() - 2) * 1.5;
				}
				break;
			case Movie.NEW_RELEASE:
				result += aRental.getDaysRented() * 3;
				break;
			case Movie.CHILDRENS:
				result += 1.5;
				if(aRental.getDaysRented() > 3){
					result += (aRental.getDaysRented() - 3) * 1.5;
				}
				break;
		}
		return result;
	}
```

M：这段代码应该就完成了吧？

Z：观察一下这个方法，它没有用到Customer类的任何信息。**方法用到哪些数据，就把它方法数据属的类中**。

而这个方法用到Rental类的数据，所以应该将其迁移到Rental类中，并对参数进行处理：

Rental.java   

```java
public class Rental {
    ...
	/**
	 * 重构switch方法块(迁移)
	 * @param each
	 * @return
	 */
	public double getCharge() {       //主体迁移
		double result = 0;
		switch(getMovie().getPriceCode()){
			case Movie.REGULAR:
				result += 2;
				if(getDaysRented() > 2){
					result += (getDaysRented() - 2) * 1.5;
				}
				break;
			case Movie.NEW_RELEASE:
				result += getDaysRented() * 3;
				break;
			case Movie.CHILDRENS:
				result += 1.5;
				if(getDaysRented() > 3){
					result += (getDaysRented() - 3) * 1.5;
				}
				break;
		}
		return result;
	}
```

对应的，调用方法也需要进行修改``thisAmount = each.getCharge();    //重构``    

Z：回到Customer.java的``statement()``方法中，我们要尽量减少临时，像``thisAmount``就可以用``amountFor(each)``进行代替。**临时变量往往会引发问题，导致跟丢的情况**。   

```java
	public String statement(){
		double totalAmount = 0;
		int frequentRenterPoints = 0;
		Enumeration rentals = _rentals.elements();
		String result = "Rental Record for" + getName() + "\n";
		while(rentals.hasMoreElements()){
			Rental each = (Rental) rentals.nextElement();
			
			frequentRenterPoints ++;
			if((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) && 
					each.getDaysRented() > 1){
				frequentRenterPoints ++;
			}
            
			result += "\t" +each.getMovie().getTitle() +"\t" + String.valueOf(each.getCharge()) + "\n"; //重构临时变量
			totalAmount += each.getCharge();    //重构临时变量
		}
		result += "Amount owed is" + String.valueOf(totalAmount) + "\n";
		result += "You earned" + String.valueOf(frequentRenterPoints) + "frequent renter points";
		return result;
		
	}
```

M：但是这不是会执行方法两次，消耗性能么？

Z：这可以在Rental类中进行优化，后序再研究该方法。   

M：对积分统计重构方式与上边相似，我进行了重构

Customer.java

```java
	public String statement(){
		double totalAmount = 0;
		int frequentRenterPoints = 0;
		Enumeration rentals = _rentals.elements();
		String result = "Rental Record for" + getName() + "\n";
		while(rentals.hasMoreElements()){
			Rental each = (Rental) rentals.nextElement();
			frequentRenterPoints += each.getFrequentRenterPoints();   //重构积分计算
			
			result += "\t" +each.getMovie().getTitle() +"\t" + String.valueOf(each.getCharge()) + "\n"; //重构临时变量
			totalAmount += each.getCharge();    //重构临时变量
			
		}
		result += "Amount owed is" + String.valueOf(totalAmount) + "\n";
		result += "You earned" + String.valueOf(frequentRenterPoints) + "frequent renter points";
		return result;
		
	}
```

Rental.java

```java
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
```









51页

































