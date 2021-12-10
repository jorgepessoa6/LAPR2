package lapr2.framework.company.serviceexecutionorder.simpleserviceexecutionorder;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores all the simple service execution orders.
 *
 * @author flow
 */
public class SimpleServiceExecutionOrderManager {

	/**
	 * The list of simple service execution orders.
	 */
	@Getter
	private List<SimpleServiceExecutionOrder> orders = new ArrayList<>();

	/**
	 * Adds a new simple service execution order to the list.
	 *
	 * @param order the simple service execution order
	 * @return <code>true</code> if the order was successfully added, <code>false</code> otherwise
	 */
	public boolean add(SimpleServiceExecutionOrder order) {
		if (orders.contains(order)) return false;
		return orders.add(order);
	}

	/**
	 * Returns a list with the orders ordered by time, from the oldest to the newest.
	 *
	 * @return the list
	 */
	public List<SimpleServiceExecutionOrder> getOrdersTimeline() {
		List<SimpleServiceExecutionOrder> ordersSorted = new ArrayList<>(orders);

		ordersSorted.sort((order1, order2) -> {
			if (order1.getSchedule().isEqual(order2.getSchedule())) return 0;
			return (order1.getSchedule().isAfter(order2.getSchedule())) ? 1 : -1;
		});

		return ordersSorted;
	}
}
