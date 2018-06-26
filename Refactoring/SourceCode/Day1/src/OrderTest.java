import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class OrderTest {

	@Test
	public void test() {
		Order order = new Order();
		OrderLine orderLine = new OrderLine(10.0);
		order.add(orderLine);
		OrderLine orderLine2 = new OrderLine(15.0);
		order.add(orderLine2);
		List<OrderLine> orderLines = order.getOrderLines();
		for (int i = 0; i < orderLines.size(); i++) {
			if(i == 0){
				Assert.assertEquals(10.0,orderLines.get(i).getTotal());
			}else if(i == 1){
				Assert.assertEquals(15.0,orderLines.get(i).getTotal());
			}
		}
		
	}

}
