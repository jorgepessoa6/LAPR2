package lapr2.framework.io.serviceexecutionorder;

import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrder;
import lapr2.framework.company.serviceexecutionorder.simpleserviceexecutionorder.SimpleServiceExecutionOrder;
import lapr2.framework.company.servicerequest.Schedule;
import lapr2.framework.homeservices.HomeServices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

/**
 * Operates the input/output of service execution orders in a *.csv file.
 *
 * @author flow
 */
public class CSVOrders implements ServiceExecutionOrderIO {

	/**
	 * The fields of a service execution order to be saved.
	 */
	private static final String[] FIELDS = new String[]{"client", "service_category", "schedule", "service_type", "postal_address", "distance"};

	/**
	 * The path and name of the .csv file.
	 */
	private static final String FILE_NAME = HomeServices.FOLDER_PATH + "orders/orders";

	/**
	 * The piece that separates different fields of the same service execution order.
	 */
	private final String fieldSeparator;

	/**
	 * Constructs a new in/out .csv operator.
	 */
	public CSVOrders() {
		this.fieldSeparator = ";";
	}

	/**
	 * Exports a list of service execution orders to the .csv file.
	 *
	 * @param serviceExecutionOrders the service execution orders list
	 * @return <code>true</code> if the exportation was successful, <code>false</code> otherwise
	 */
	@Override
	public boolean exportServiceExecutionOrders(List<ServiceExecutionOrder> serviceExecutionOrders) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Schedule.TIME_FORMAT);
		try (Formatter formatter = new Formatter(new File(FILE_NAME + "_" + dateTimeFormatter.format(LocalDateTime.now()) + ".csv"))) {

			writeHeader(formatter);
			writeFields(serviceExecutionOrders, formatter);

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Imports the service execution orders from the .csv file.
	 *
	 * @return the list with all the simple service execution orders
	 */
	@Override
	public List<SimpleServiceExecutionOrder> importServiceExecutionOrders(String filePath) throws FileNotFoundException {
		List<SimpleServiceExecutionOrder> orders = new ArrayList<>();

		try (Scanner scanner = new Scanner(new File(filePath))) {
			scanner.nextLine(); //jumps the header

			while (scanner.hasNextLine()) {
				String[] fields = scanner.nextLine().split(fieldSeparator);
				String client = fields[0];
				String category = fields[1];
				String schedule = fields[2];
				String serviceType = fields[3];
				String postalAddress = fields[4];
				double distance = Double.parseDouble(fields[5]);
				SimpleServiceExecutionOrder order = new SimpleServiceExecutionOrder(client, category, schedule, serviceType, postalAddress, distance);
				orders.add(order);
			}
		}
		return orders;
	}

	/**
	 * Writes the header of the .csv file.
	 *
	 * @param formatter the formatter
	 */
	private void writeHeader(Formatter formatter) {
		for (String field : FIELDS)
			formatter.format("%s%s", field, fieldSeparator);

		formatter.format("%n");
	}

	/**
	 * Writes all the fields to the .csv file.
	 *
	 * @param serviceExecutionOrders the list of orders to write
	 * @param formatter              the formatter
	 */
	private void writeFields(List<ServiceExecutionOrder> serviceExecutionOrders, Formatter formatter) {
		for (ServiceExecutionOrder serviceExecutionOrder : serviceExecutionOrders) {
			formatter.format("%s%s", serviceExecutionOrder.getAffectation().getServiceRequest().getClient().getName(), fieldSeparator);
			formatter.format("%s%s", serviceExecutionOrder.getAffectation().getServiceDescription().getService().getCategory().toString(), fieldSeparator);
			formatter.format("%s%s", serviceExecutionOrder.getAffectation().getSchedule().toTimeFormattedString(), fieldSeparator);
			formatter.format("%s%s", serviceExecutionOrder.getAffectation().getServiceDescription().getService().getClass().getSimpleName(), fieldSeparator);
			formatter.format("%s%s", serviceExecutionOrder.getAffectation().getServiceRequest().getPostalAddress().getPostCode().toString(), fieldSeparator);
			formatter.format("%s%s", PostCode.getDistance(serviceExecutionOrder.getAffectation().getServiceRequest().getPostalAddress().getPostCode(), serviceExecutionOrder.getAffectation().getServiceProvider().getPostCode()), fieldSeparator);
			formatter.format("%n");
		}
	}

}
