package lapr2.framework.io.serviceexecutionorder;

import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrder;
import lapr2.framework.company.serviceexecutionorder.simpleserviceexecutionorder.SimpleServiceExecutionOrder;
import lapr2.framework.company.servicerequest.Schedule;
import lapr2.framework.homeservices.HomeServices;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Operates the input/output of service execution orders in a *.xml file.
 *
 * @author flow
 */
public class XMLOrdersAdapter implements ServiceExecutionOrderIO {

	/**
	 * The fields of a service execution order to be saved.
	 */
	private static final String[] FIELDS = new String[]{"client", "service_category", "schedule", "service_type", "postal_address", "distance"};

	/**
	 * The path and name of the .xml file.
	 */
	private static final String FILE_NAME = HomeServices.FOLDER_PATH + "orders/orders";

	/**
	 * Exports the given service execution orders to a .xml file.
	 *
	 * @param serviceExecutionOrders the service execution orders to export
	 * @return <code>true</code> if the orders were successfully exported, <code>false</code> otherwise
	 */
	@Override
	public boolean exportServiceExecutionOrders(List<ServiceExecutionOrder> serviceExecutionOrders) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("service_execution_orders");

		for (ServiceExecutionOrder serviceExecutionOrder : serviceExecutionOrders) {
			Element orderElement = root.addElement("order");
			orderElement.addElement(FIELDS[0]).addText(serviceExecutionOrder.getAffectation().getServiceRequest().getClient().getName());
			orderElement.addElement(FIELDS[1]).addText(serviceExecutionOrder.getAffectation().getServiceDescription().getService().getCategory().toString());
			orderElement.addElement(FIELDS[2]).addText(serviceExecutionOrder.getAffectation().getSchedule().toTimeFormattedString());
			orderElement.addElement(FIELDS[3]).addText(serviceExecutionOrder.getAffectation().getServiceDescription().getService().getClass().getSimpleName());
			orderElement.addElement(FIELDS[4]).addText(serviceExecutionOrder.getAffectation().getServiceRequest().getPostalAddress().toString());
			orderElement.addElement(FIELDS[5]).addText(String.valueOf(PostCode.getDistance(serviceExecutionOrder.getAffectation().getServiceRequest().getPostalAddress().getPostCode(), serviceExecutionOrder.getAffectation().getServiceProvider().getPostCode())));
		}

		return writeAllElements(document);

	}

	/**
	 * Imports the service execution orders of a file.
	 *
	 * @param filePath the path to the file
	 * @return the list with all the simple service execution orders
	 */
	@Override
	public List<SimpleServiceExecutionOrder> importServiceExecutionOrders(String filePath) throws IOException {

		File file = new File(filePath);
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(file);
		} catch (DocumentException e) {
			throw new IOException();
		}

		Element root = document.getRootElement();

		return readOrders(root);
	}

	/**
	 * Writes all elements in a {@link Document} to the file.
	 *
	 * @param document document to write the elements
	 * @return <code>true</code> if the elements were successfully written, <code>false</code> otherwise
	 */
	private boolean writeAllElements(Document document) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		try {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Schedule.TIME_FORMAT);
			XMLWriter writer = new XMLWriter(new FileWriter(new File(FILE_NAME + "_" + dateTimeFormatter.format(LocalDateTime.now()) + ".xml")), format);
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Reads all the orders of an element.
	 *
	 * @param root the element
	 * @return the list with all the orders read
	 */
	private List<SimpleServiceExecutionOrder> readOrders(Element root) {
		List<SimpleServiceExecutionOrder> orders = new ArrayList<>();

		for (Iterator<Element> iterator = root.elementIterator(); iterator.hasNext(); ) {
			Element order = iterator.next();

			orders.add(readOrder(order));
		}
		return orders;
	}

	/**
	 * Reads all the attributes of an order.
	 *
	 * @param order the element
	 * @return the simple order
	 */
	private SimpleServiceExecutionOrder readOrder(Element order) {
		SimpleServiceExecutionOrder simpleServiceExecutionOrder = null;
		for (Iterator<Element> orderIterator = order.elementIterator(); orderIterator.hasNext(); ) {
			String client = orderIterator.next().getStringValue();
			String category = orderIterator.next().getStringValue();
			String schedule = orderIterator.next().getStringValue();
			String serviceType = orderIterator.next().getStringValue();
			String postalAddress = orderIterator.next().getStringValue();
			double distance = Double.parseDouble(orderIterator.next().getStringValue());
			simpleServiceExecutionOrder = new SimpleServiceExecutionOrder(client, category, schedule, serviceType, postalAddress, distance);
		}
		return simpleServiceExecutionOrder;
	}

}
