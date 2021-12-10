package lapr2.framework.io.serviceexecutionorder;

import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrder;
import lapr2.framework.company.serviceexecutionorder.simpleserviceexecutionorder.SimpleServiceExecutionOrder;
import lapr2.framework.company.servicerequest.Schedule;
import lapr2.framework.homeservices.HomeServices;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Operates the input/output of service execution orders in a *.xls file.
 *
 * @author flow
 */
public class XLSOrdersAdapter implements ServiceExecutionOrderIO {

	/**
	 * The fields of a service execution order to be saved.
	 */
	private static final String[] FIELDS = new String[]{"client", "service_category", "schedule", "service_type", "postal_address", "distance"};

	/**
	 * The path and name of the .xls file.
	 */
	private static final String FILE_NAME = HomeServices.FOLDER_PATH + "orders/orders";

	/**
	 * Exports the given service execution orders to a a .xls file.
	 *
	 * @param serviceExecutionOrders the service execution orders to export
	 * @return if the orders wew successfully exported
	 */
	@Override
	public boolean exportServiceExecutionOrders(List<ServiceExecutionOrder> serviceExecutionOrders) {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Service_Providers");
		Font headerFont = setHeaderFont(workbook);
		CellStyle headerCell = setHeaderStyle(workbook, headerFont);

		writeHeader(sheet, headerCell);
		writeOrders(serviceExecutionOrders, sheet);
		autoSizeSheet(sheet);
		return saveFile(workbook);
	}

	/**
	 * Imports the service execution orders from a file.
	 *
	 * @param filePath the path to the file
	 * @return the list of service execution orders
	 * @throws IOException if the file can not be found
	 */
	@Override
	public List<SimpleServiceExecutionOrder> importServiceExecutionOrders(String filePath) throws IOException {
		try (Workbook workbook = WorkbookFactory.create(new File(filePath))) {
			Sheet sheet = workbook.getSheetAt(0);

			List<SimpleServiceExecutionOrder> orders = new ArrayList<>();
			Iterator rowIterator = sheet.rowIterator();

			if (rowIterator.hasNext()) rowIterator.next(); //jumps the header

			while (rowIterator.hasNext()) {
				Row row = (Row) rowIterator.next();

				String client = row.getCell(0).getStringCellValue();
				String serviceCategory = row.getCell(1).getStringCellValue();
				String schedule = row.getCell(2).getStringCellValue();
				String serviceType = row.getCell(3).getStringCellValue();
				String postalAddress = row.getCell(4).getStringCellValue();
				double distance = row.getCell(5).getNumericCellValue();

				orders.add(new SimpleServiceExecutionOrder(client, serviceCategory, schedule, serviceType, postalAddress, distance));
			}
			return orders;
		}
	}

	/**
	 * Sets the font for the header.
	 *
	 * @param workbook the workbook
	 * @return the font of the header
	 */
	private Font setHeaderFont(Workbook workbook) {
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.GREY_80_PERCENT.getIndex());
		return headerFont;
	}

	/**
	 * Sets the style of the header.
	 *
	 * @param workbook   the workbook
	 * @param headerFont the font of the header
	 * @return the style
	 */
	private CellStyle setHeaderStyle(Workbook workbook, Font headerFont) {
		CellStyle headerCell = workbook.createCellStyle();
		headerCell.setBottomBorderColor(IndexedColors.LIGHT_BLUE.getIndex());
		headerCell.setFont(headerFont);
		return headerCell;
	}

	/**
	 * Writes the header of the sheet in its first line.
	 *
	 * @param sheet       the sheet
	 * @param headerStyle the style of the header
	 */
	private void writeHeader(Sheet sheet, CellStyle headerStyle) {
		Row headerRow = sheet.createRow(0);
		for (int i = 0; i < FIELDS.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(FIELDS[i]);
			cell.setCellStyle(headerStyle);
		}
	}

	/**
	 * Writes all the service execution orders in the sheet.
	 *
	 * @param serviceExecutionOrders the service execution orders
	 * @param sheet                  the sheet
	 */
	private void writeOrders(List<ServiceExecutionOrder> serviceExecutionOrders, Sheet sheet) {
		int rowNum = 1;
		for (ServiceExecutionOrder order : serviceExecutionOrders) {
			Row row = sheet.createRow(rowNum);

			row.createCell(0).setCellValue(order.getAffectation().getServiceRequest().getClient().getName());
			row.createCell(1).setCellValue(order.getAffectation().getServiceDescription().getService().getCategory().toString());
			row.createCell(2).setCellValue(order.getAffectation().getSchedule().toTimeFormattedString());
			row.createCell(3).setCellValue(order.getAffectation().getServiceDescription().getService().getClass().getSimpleName());
			row.createCell(4).setCellValue(order.getAffectation().getServiceRequest().getPostalAddress().toString());
			row.createCell(5).setCellValue(PostCode.getDistance(order.getAffectation().getServiceRequest().getPostalAddress().getPostCode(), order.getAffectation().getServiceProvider().getPostCode()));

			rowNum++;
		}
	}

	/**
	 * Resizes the sheet to fit in with the elements.
	 *
	 * @param sheet the sheet
	 */
	private void autoSizeSheet(Sheet sheet) {
		for (int i = 0; i < FIELDS.length; i++)
			sheet.autoSizeColumn(i);
	}

	/**
	 * Saves the workbook.
	 *
	 * @param workbook the workbook
	 * @return if the workbook was successfully saved
	 */
	private boolean saveFile(Workbook workbook) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Schedule.TIME_FORMAT);
		try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME + "_" + dateTimeFormatter.format(LocalDateTime.now()) + ".xls")) {
			workbook.write(fileOutputStream);
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
