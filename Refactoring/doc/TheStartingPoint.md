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

D：例如以下重构，把switch方法块提取出来 (用**Extract Method**可以快速实现)

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

而这个方法用到Rental类的数据，所以应该将其迁移到Rental类中（**Move Method**），并对参数进行处理：

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

Z：回到Customer.java的``statement()``方法中，我们要尽量减少临时，像``thisAmount``就可以用``amountFor(each)``进行代替(**Replace Temp with Query**)。**临时变量往往会引发问题，导致跟丢的情况**。   

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

Z：观察Customer.java的statement()方法，你觉得还有什么需要改进的？

M：临时变量有点多，但是这也没什么办法啊。

Z：可以用**Replace Temp with Query**，将变量提取成查询函数出来

```java
	public String statement(){
		Enumeration rentals = _rentals.elements();
		String result = "Rental Record for" + getName() + "\n";
		while(rentals.hasMoreElements()){
			Rental each = (Rental) rentals.nextElement();
			result += "\t" +each.getMovie().getTitle() +"\t" + String.valueOf(each.getCharge()) + "\n"; //重构临时变量
		}
		result += "Amount owed is" + String.valueOf(getTotalCharge()) + "\n";   //重构总费用计算
		result += "You earned" + String.valueOf(getTotalIFrequentRenterPoints()) + "frequent renter points";  //重构积分计算
		return result;
		
	}
	
	/**
	 * 重构积分计算
	 * @return
	 */
	private int getTotalIFrequentRenterPoints(){
		int result = 0;
		Enumeration rentals = _rentals.elements();
		while(rentals.hasMoreElements()){
			Rental each = (Rental) rentals.nextElement();
			result += each.getFrequentRenterPoints();
		}
		return result;
	}
	
	/**
	 * 重构总费用计算
	 * @return
	 */
	private double getTotalCharge(){
		double result = 0;
		Enumeration rentals = _rentals.elements();
		while(rentals.hasMoreElements()){
			Rental each = (Rental) rentals.nextElement();
			result += each.getCharge();
		}
		return result;
	}
```

Z：就是把临时变量用方法替换出来，但是该方法比较复杂，带有循环，所以还需要把循环抽取出来。这样做有利于新的statement方法复用原来的计算。

M：之前讲到，Customer类中的``getCharge()``方法(带switch)用到Rental类的数据，所以应该将其迁移到Rental类中（**Move Method**），并对参数进行处理。但是考虑一下，它用到的参数除了Rental的getDaysRented()，其实还用到了Movie类中的方法``getMovie().getPriceCode()``。为什么``getCharge()``方法选择的是Rental类？

```java
	public class Rental {
        ...
	/**
	 * 重构switch方法块(迁移)
	 * @param each
	 * @return
	 */
	public double getCharge() {
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

Z：那就要考虑系统的业务了，像上方是处于Rental类，将Movie对象传入``getCharge()``方法中。而以下代码是在Movie中，将``getDaysRented()``也就是租贸长度传入。

```java
public class Movie {
    ...	
	/**
	 * 重构switch方法块(迁移)
	 * @param daysRented 租贸长度
	 * @return
	 */
	public double getCharge(int daysRented) {   //传入租贸长度
		double result = 0;
		switch(getPriceCode()){    //重构调用自身方法
			case Movie.REGULAR:
				result += 2;
				if(daysRented > 2){
					result += (daysRented - 2) * 1.5;
				}
				break;
			case Movie.NEW_RELEASE:
				result += daysRented * 3;
				break;
			case Movie.CHILDRENS:
				result += 1.5;
				if(daysRented > 3){
					result += (daysRented - 3) * 1.5;
				}
				break;
		}
		return result;
	}
```

Movie影片对象和daysRented租贸长度两个参数，选择daysRented作为参数传入，因为相对于Movie，Movie的变化具有不稳定倾向，放在该类中可以控制它造成的影响。再者daysRented的传入也更利于复用。  

M：相似的还有Rental中的``getFrequentRenterPoints()``方法，这要怎么迁移呢（**Move Method**）？  

```java
public class Rental {
    ...
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

Z：迁移到Movie类中，由于points是固定的，所以用常量值返回:

```java
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
```

M：因为不同的影片类型有不同的计费方式，而计费方式又存在多次改变的情况。这段代码可以进行优化吗？

```java
public class Movie {
    ...	
	/**
	 * 重构switch方法块(迁移)
	 * @param daysRented 租贸长度
	 * @return
	 */
	public double getCharge(int daysRented) {   //传入租贸长度
		double result = 0;
		switch(getPriceCode()){    //重构调用自身方法
			case Movie.REGULAR:
				result += 2;
				if(daysRented > 2){
					result += (daysRented - 2) * 1.5;
				}
				break;
			case Movie.NEW_RELEASE:
				result += daysRented * 3;
				break;
			case Movie.CHILDRENS:
				result += 1.5;
				if(daysRented > 3){
					result += (daysRented - 3) * 1.5;
				}
				break;
		}
		return result;
	}
```

Z：可以构造Price类下的三个子类，每个子类有自己的计费方式。来替换switch，叫做State模式

Price父类

```java
abstract class Price {
	abstract int getPriceCode();
	/**
	 * 重构switch方法块(迁移)
	 * @param daysRented 租贸长度
	 * @return
	 */
	abstract double getCharge(int daysRented);
}
```

ChildrensPrice子类

```java
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
```

NewReleasePrice子类

```java
public class NewReleasePrice extends Price{
	@Override
	int getPriceCode() {
		return Movie.NEW_RELEASE;
	}
	public double getCharge(int daysRented) {  
		return daysRented * 3;
	}		
}
```

RegularPrice子类

```java
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
```

M：我们本来是通过``		switch(getPriceCode()){    //重构调用自身方法``来找到PriceCode对应的计费方式。但是现在拆分成类继承的方式了，怎么对应？

Z：可以在初始化Movie对象的时候就进行初始化对应的价格类型对象，并且修改getPriceCode方式

```java
public class Movie {
    ...
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
			_price = new RegularPrice();   //初始化对应的价格类型对象
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
```





















































