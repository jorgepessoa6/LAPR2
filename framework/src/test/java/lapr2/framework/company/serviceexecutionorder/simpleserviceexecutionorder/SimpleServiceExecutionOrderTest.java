package lapr2.framework.company.serviceexecutionorder.simpleserviceexecutionorder;

import lapr2.framework.company.category.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SimpleServiceExecutionOrderTest {

	@Test
	void equals1() {
		SimpleServiceExecutionOrder simpleServiceExecutionOrder = new SimpleServiceExecutionOrder("client", "category", "2019-01-01_10-10", "type", "address", 0);
		Category category = new Category("", "");
		SimpleServiceExecutionOrder simpleServiceExecutionOrder1 = new SimpleServiceExecutionOrder("", "category", "2019-01-01_10-10", "type", "address", 0);
		SimpleServiceExecutionOrder simpleServiceExecutionOrder2 = new SimpleServiceExecutionOrder("client", "", "2019-01-01_10-10", "type", "address", 0);
		SimpleServiceExecutionOrder simpleServiceExecutionOrder3 = new SimpleServiceExecutionOrder("client", "category", "2019-01-01_10-20", "type", "address", 0);
		SimpleServiceExecutionOrder simpleServiceExecutionOrder4 = new SimpleServiceExecutionOrder("client", "category", "2019-01-01_10-10", "", "address", 0);
		SimpleServiceExecutionOrder simpleServiceExecutionOrder5 = new SimpleServiceExecutionOrder("client", "category", "2019-01-01_10-10", "type", "", 0);
		SimpleServiceExecutionOrder simpleServiceExecutionOrder6 = new SimpleServiceExecutionOrder("client", "category", "2019-01-01_10-10", "type", "address", 0);

		assertEquals(simpleServiceExecutionOrder, simpleServiceExecutionOrder);
		assertNotEquals(simpleServiceExecutionOrder, category);
		assertNotEquals(simpleServiceExecutionOrder, simpleServiceExecutionOrder1);
		assertNotEquals(simpleServiceExecutionOrder, simpleServiceExecutionOrder2);
		assertNotEquals(simpleServiceExecutionOrder, simpleServiceExecutionOrder3);
		assertNotEquals(simpleServiceExecutionOrder, simpleServiceExecutionOrder4);
		assertNotEquals(simpleServiceExecutionOrder, simpleServiceExecutionOrder5);
		assertEquals(simpleServiceExecutionOrder, simpleServiceExecutionOrder6);
	}

	@Test
	void hashCode1() {
		SimpleServiceExecutionOrder simpleServiceExecutionOrder = new SimpleServiceExecutionOrder("client", "category", "2019-01-01_10-10", "type", "address", 0);
		SimpleServiceExecutionOrder simpleServiceExecutionOrder1 = new SimpleServiceExecutionOrder("", "category", "2019-01-01_10-10", "type", "address", 0);
		SimpleServiceExecutionOrder simpleServiceExecutionOrder2 = new SimpleServiceExecutionOrder("client", "", "2019-01-01_10-10", "type", "address", 0);
		SimpleServiceExecutionOrder simpleServiceExecutionOrder3 = new SimpleServiceExecutionOrder("client", "category", "2019-01-01_10-20", "type", "address", 0);
		SimpleServiceExecutionOrder simpleServiceExecutionOrder4 = new SimpleServiceExecutionOrder("client", "category", "2019-01-01_10-10", "", "address", 0);
		SimpleServiceExecutionOrder simpleServiceExecutionOrder5 = new SimpleServiceExecutionOrder("client", "category", "2019-01-01_10-10", "type", "", 0);
		SimpleServiceExecutionOrder simpleServiceExecutionOrder6 = new SimpleServiceExecutionOrder("client", "category", "2019-01-01_10-10", "type", "address", 0);


		assertEquals(simpleServiceExecutionOrder.hashCode(), simpleServiceExecutionOrder.hashCode());
		assertNotEquals(simpleServiceExecutionOrder.hashCode(), simpleServiceExecutionOrder1.hashCode());
		assertNotEquals(simpleServiceExecutionOrder.hashCode(), simpleServiceExecutionOrder2.hashCode());
		assertNotEquals(simpleServiceExecutionOrder.hashCode(), simpleServiceExecutionOrder3.hashCode());
		assertNotEquals(simpleServiceExecutionOrder.hashCode(), simpleServiceExecutionOrder4.hashCode());
		assertNotEquals(simpleServiceExecutionOrder.hashCode(), simpleServiceExecutionOrder5.hashCode());
		assertEquals(simpleServiceExecutionOrder.hashCode(), simpleServiceExecutionOrder6.hashCode());
	}
}