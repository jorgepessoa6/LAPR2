package lapr2.framework.io.serviceexecutionorder;

import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrder;
import lapr2.framework.company.serviceexecutionorder.simpleserviceexecutionorder.SimpleServiceExecutionOrder;

import java.io.IOException;
import java.util.List;

/**
 * Operates the input/output on service execution orders, this is
 * their importing and exporting, respectively.
 *
 * @author flow
 */
public interface ServiceExecutionOrderIO {

	/**
	 * Exports the given service execution orders.
	 *
	 * @param serviceExecutionOrders the service execution orders to export
	 * @return <code>true</code> if the orders were successfully exported, <code>false</code> otherwise
	 */
	boolean exportServiceExecutionOrders(List<ServiceExecutionOrder> serviceExecutionOrders);

	/**
	 * Imports the service execution orders of a file.
	 *
	 * @param filePath the path to the file
	 * @return the list with all the simple service execution orders
	 */
	List<SimpleServiceExecutionOrder> importServiceExecutionOrders(String filePath) throws IOException;
}
