package lapr2.framework.company.serviceexecutionorder.simpleserviceexecutionorder;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleServiceExecutionOrderManagerTest {

	@Test
	void add() {
		SimpleServiceExecutionOrderManager manager = new SimpleServiceExecutionOrderManager();

		SimpleServiceExecutionOrder order = new SimpleServiceExecutionOrder("a", "a", "2019-10-10_10-10", "a", "a", 0);

		manager.add(order);

		assertTrue(manager.getOrders().contains(order));
		assertFalse(manager.add(order));
	}

	@Test
	void getOrdersTimeline() {
		SimpleServiceExecutionOrder order2000 = new SimpleServiceExecutionOrder("a", "a", "2000-10-10_10-10", "a", "a", 0);
		SimpleServiceExecutionOrder order2001 = new SimpleServiceExecutionOrder("a", "a", "2001-10-10_10-10", "a", "a", 0);
		SimpleServiceExecutionOrder order2002 = new SimpleServiceExecutionOrder("a", "a", "2002-10-10_10-10", "a", "a", 0);
		SimpleServiceExecutionOrder order2003 = new SimpleServiceExecutionOrder("a", "a", "2003-10-10_10-10", "a", "a", 0);

		SimpleServiceExecutionOrderManager manager = new SimpleServiceExecutionOrderManager();

		manager.add(order2003);
		manager.add(order2001);
		manager.add(order2000);
		manager.add(order2002);

		List<SimpleServiceExecutionOrder> expected = new ArrayList<>();
		expected.add(order2000);
		expected.add(order2001);
		expected.add(order2002);
		expected.add(order2003);

		assertEquals(expected, manager.getOrdersTimeline());
		assertNotEquals(manager.getOrders(), manager.getOrdersTimeline());
	}
}