package lapr2.framework.io.bootstrap;

import lapr2.framework.company.affectation.Affectation;
import lapr2.framework.company.affectation.AffectationManager;
import lapr2.framework.company.candidature.Candidature;
import lapr2.framework.company.candidature.CandidatureManager;
import lapr2.framework.company.candidature.qualification.AcademicQualification;
import lapr2.framework.company.candidature.qualification.ProfessionalQualification;
import lapr2.framework.company.category.Category;
import lapr2.framework.company.category.CategoryManager;
import lapr2.framework.company.location.geographicalarea.GeographicalArea;
import lapr2.framework.company.location.geographicalarea.GeographicalAreaManager;
import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.location.postcode.PostCodeManager;
import lapr2.framework.company.manager.DataManager;
import lapr2.framework.company.service.*;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrder;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrderManager;
import lapr2.framework.company.servicerequest.Schedule;
import lapr2.framework.company.servicerequest.ServiceDescription;
import lapr2.framework.company.servicerequest.ServiceRequest;
import lapr2.framework.company.servicerequest.ServiceRequestManager;
import lapr2.framework.company.user.client.Client;
import lapr2.framework.company.user.client.ClientManager;
import lapr2.framework.company.user.serviceprovider.ServiceProvider;
import lapr2.framework.company.user.serviceprovider.ServiceProviderManager;
import lapr2.framework.company.user.serviceprovider.dailyavailability.DailyAvailability;
import lapr2.framework.company.user.serviceprovider.rating.Rating;
import lapr2.framework.company.user.user.User;
import lapr2.framework.company.user.user.UserManager;
import lapr2.framework.homeservices.HomeServices;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;

/**
 * Loads all the info from an excel path.
 * It is specifically implemented to load from the excel given by the teachers.
 *
 * @author flow
 */
public class CustomBootstrap {

	/*
	Name of the sheet of a given field
	 */
	private static final String CLIENTS = "Clients";
	private static final String SP_APPLICATIONS = "Applications";
	private static final String CATEGORIES = "Categories";
	private static final String SERVICES = "Services";
	private static final String GEOGRAPHICAL_AREAS = "Areas";
	private static final String REQUESTS = "Requests";
	private static final String PROVIDERS = "Providers";
	private static final String AVAILABILITIES = "Availabilities";
	private static final String ISSUES = "Issues";
	private static final String RATINGS = "Ratings";
	private static final String ORDERS = "Orders";

	/**
	 * The workbook.
	 */
	private final Workbook workbook;

	/**
	 * Constructs a new bootstrap.
	 *
	 * @param excelPath the path to the excel file
	 * @throws IOException if path is incorrect
	 */
	public CustomBootstrap(String excelPath) throws IOException {
		this.workbook = WorkbookFactory.create(new File(excelPath));
	}

	/**
	 * Runs the custom bootstrap fulfilling all the fields.
	 */
	public void run() {
		DataManager dataManager = HomeServices.getInstance().getCompany().getDataManager();

		dataManager.get(PostCodeManager.class).load();

		Set<User> users = new HashSet<>();
		List<Affectation> affectations = new ArrayList<>();

		for (GeographicalArea area : loadGeographicalAreas())
			dataManager.get(GeographicalAreaManager.class).add(area);

		for (Client client : loadClients(users))
			dataManager.get(ClientManager.class).add(client);

		for (Category category : loadCategories())
			dataManager.get(CategoryManager.class).add(category);

		for (ServiceProvider provider : loadProviders(users))
			dataManager.get(ServiceProviderManager.class).add(provider);

		for (Service service : loadServices())
			dataManager.get(ServiceManager.class).add(service);

		for (Candidature candidature : loadCandidatures())
			dataManager.get(CandidatureManager.class).add(candidature);

		for (ServiceRequest serviceRequest : loadRequests())
			dataManager.get(ServiceRequestManager.class).add(serviceRequest);

		for (ServiceExecutionOrder order : loadOrders(affectations))
			dataManager.get(ServiceExecutionOrderManager.class).add(order);

		for (User user : users)
			dataManager.get(UserManager.class).add(user);

		for (Affectation affectation : affectations)
			dataManager.get(AffectationManager.class).add(affectation);

		loadDailyAvailabilities();
		loadIssues();
		loadRatings();

		//dataManager.saveAll();
	}

	/**
	 * Loads all the clients, saving the users as well.
	 * Has to be implemented after the load of postcodes.
	 *
	 * @param users the set with the users
	 * @return the list with the clients
	 */
	private List<Client> loadClients(Set<User> users) {
		List<Client> clients = new ArrayList<>();
		Iterator rowIterator = getRowIterator(CLIENTS);

		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();

			loadSingleClient(clients, row, users);
		}

		return clients;
	}

	/**
	 * Loads all the candidatures to service provider.
	 * Has to be implemented after the load of postcodes.
	 *
	 * @return the list with all the candidatures
	 */
	private List<Candidature> loadCandidatures() {
		List<Candidature> candidatures = new ArrayList<>();
		Iterator rowIterator = getRowIterator(SP_APPLICATIONS);

		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();

			loadSingleCandidature(row, candidatures);
		}

		return candidatures;
	}

	/**
	 * Loads all the categories.
	 *
	 * @return the list with the categories
	 */
	private List<Category> loadCategories() {
		List<Category> categories = new ArrayList<>();
		Iterator rowIterator = getRowIterator(CATEGORIES);

		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();

			loadSingleCategory(row, categories);
		}

		return categories;
	}

	/**
	 * Loads all the services.
	 * Has to be implemented after the load of categories.
	 *
	 * @return the list with all the services
	 */
	private List<Service> loadServices() {
		List<Service> services = new ArrayList<>();
		Iterator rowIterator = getRowIterator(SERVICES);

		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();

			loadSingleService(row, services);
		}
		return services;
	}

	/**
	 * Loads all the geographical areas.
	 * Has to be implemented after the load of postcodes.
	 *
	 * @return the list with the geographical areas
	 */
	private List<GeographicalArea> loadGeographicalAreas() {
		List<GeographicalArea> geographicalAreas = new ArrayList<>();
		Iterator rowIterator = getRowIterator(GEOGRAPHICAL_AREAS);

		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();

			loadSingleArea(row, geographicalAreas);
		}

		return geographicalAreas;
	}

	/**
	 * Loads all the requests.
	 * Has to be implemented after the load of clients, postcodes and services.
	 *
	 * @return the list with all the requests loaded
	 */
	private List<ServiceRequest> loadRequests() {
		List<ServiceRequest> serviceRequests = new ArrayList<>();
		Iterator rowIterator = getRowIterator(REQUESTS);

		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();

			loadSingleRequest(row, serviceRequests);
		}

		return serviceRequests;
	}

	/**
	 * Loads all the service providers, loading the users as well.
	 * Has to be implemented after the load of postcodes, the load of categories and the load of geographical areas.
	 *
	 * @param users the set of users
	 * @return a list with all the providers loaded
	 */
	private List<ServiceProvider> loadProviders(Set<User> users) {
		List<ServiceProvider> serviceProviders = new ArrayList<>();
		Iterator rowIterator = getRowIterator(PROVIDERS);

		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();

			loadSingleProvider(row, serviceProviders, users);
		}

		return serviceProviders;
	}

	/**
	 * Loads all the daily availabilities.
	 * Has to be implemented after the load of service providers.
	 */
	private void loadDailyAvailabilities() {
		Iterator rowIterator = getRowIterator(AVAILABILITIES);

		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();

			loadSingleAvailability(row);
		}
	}

	/**
	 * Loads all the issues.
	 * Has to be implemented after the service execution orders.
	 */
	private void loadIssues() {
		Iterator rowIterator = getRowIterator(ISSUES);

		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();

			loadSingleIssue(row);
		}
	}

	/**
	 * Loads all the ratings.
	 * Has to be implemented after the service execution orders.
	 */
	private void loadRatings() {
		Iterator rowIterator = getRowIterator(RATINGS);

		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();

			loadSingleRating(row);
		}
	}

	/**
	 * Loads all the service execution orders, loading the affectations as well
	 * Has to be implemented after the clients, postcodes and services.
	 *
	 * @param affectations the list of affectations
	 * @return the list with all the orders
	 */
	private List<ServiceExecutionOrder> loadOrders(List<Affectation> affectations) {
		List<ServiceExecutionOrder> orders = new ArrayList<>();
		Iterator rowIterator = getRowIterator(ORDERS);

		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();

			loadSingleOrder(row, orders, affectations);
		}

		return orders;
	}

	/**
	 * Loads a single service execution order to a given list, loading the affectation as well.
	 *
	 * @param row    the row of the service execution order
	 * @param orders the given list
	 */
	private void loadSingleOrder(Row row, List<ServiceExecutionOrder> orders, List<Affectation> affectations) {
		DataManager dataManager = HomeServices.getInstance().getCompany().getDataManager();

		String clientTaxId = new BigDecimal(row.getCell(2).getNumericCellValue()).toPlainString();
		Client client = dataManager.get(ClientManager.class).getClientTIN(clientTaxId);

		String address = row.getCell(8).getStringCellValue();
		String postCodeDesignation = row.getCell(10).getStringCellValue();
		PostCode postCode = dataManager.get(PostCodeManager.class).getPostCode(postCodeDesignation);
		PostalAddress postalAddress = new PostalAddress(address, postCode);

		String serviceId = new BigDecimal(row.getCell(11).getNumericCellValue()).toPlainString();
		Service service = dataManager.get(ServiceManager.class).getService(serviceId);
		String description = row.getCell(12).getStringCellValue();
		ServiceDescription serviceDescription = new ServiceDescription(description, service, 0.5f);

		LocalDate localDate = row.getCell(6).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		double hours = row.getCell(7).getNumericCellValue() * 24;
		int hour = (int) hours;
		int minute = (int) ((hours - hour) * 60);
		Schedule schedule = new Schedule(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth(), hour, minute);

		ServiceRequest serviceRequest = new ServiceRequest(client, postalAddress);
		serviceRequest.getServiceDescriptionList().addServiceDescription(serviceDescription);
		serviceRequest.getScheduleList().addSchedulePreference(schedule);

		String providerNumber = new BigDecimal(row.getCell(1).getNumericCellValue()).toPlainString();
		ServiceProvider serviceProvider = dataManager.get(ServiceProviderManager.class).getServiceProviderMN(providerNumber);

		Affectation affectation = new Affectation(serviceRequest, serviceDescription, serviceProvider, schedule);
		affectation.setState(Affectation.State.CONFIRMED);

		serviceRequest.setCost(serviceRequest.getTotalCost());

		orders.add(new ServiceExecutionOrder(affectation));
		if (!affectations.contains(affectation)) affectations.add(affectation);
	}

	/**
	 * Loads a single rating to a service provider.
	 *
	 * @param row the row of the rating
	 */
	private void loadSingleRating(Row row) {
		int index = (int) row.getCell(0).getNumericCellValue() - 1;
		ServiceExecutionOrder order = HomeServices.getInstance().getCompany().getDataManager().get(ServiceExecutionOrderManager.class).getElements().get(index);
		ServiceProvider provider = order.getAffectation().getServiceProvider();

		Rating rating = provider.getRating();
		int ratingNumber = (int) row.getCell(1).getNumericCellValue();
		rating.add(ratingNumber);
		//provider.getRating().add((int) row.getCell(1).getNumericCellValue());
	}

	/**
	 * Loads a single issue.
	 *
	 * @param row the row of the issue
	 */
	private void loadSingleIssue(Row row) {
		int index = (int) row.getCell(0).getNumericCellValue() - 1;
		ServiceExecutionOrder order = HomeServices.getInstance().getCompany().getDataManager().get(ServiceExecutionOrderManager.class).getElements().get(index);

		String evaluation = row.getCell(4).getStringCellValue();

		if (evaluation.equals("Not as expected")) {
			String description = row.getCell(1).getStringCellValue();
			String troubleshooting = row.getCell(2).getStringCellValue();

			order.createIssue(description, troubleshooting);
		}

		order.setState(ServiceExecutionOrder.State.EVALUATED);
	}

	/**
	 * Loads a single daily availability into a provider.
	 *
	 * @param row the row of the daily availability
	 */
	private void loadSingleAvailability(Row row) {
		String number = new BigDecimal(row.getCell(0).getNumericCellValue()).toPlainString();
		ServiceProvider serviceProvider = HomeServices.getInstance().getCompany().getDataManager().get(ServiceProviderManager.class).getServiceProviderMN(number);

		for (int i = 2; i < 11; i += 4) {
			LocalDateTime begin = getAvailabilityLocalDateTime(row, i);
			if (begin == null) break;
			LocalDateTime finish = getAvailabilityLocalDateTime(row, i + 2);

			serviceProvider.getDailyAvailabilityList().add(new DailyAvailability(begin, finish));
		}
	}

	/**
	 * Returns the LocalDateTime for a daily availability.
	 * Returns null if such date does not exist.
	 *
	 * @param row    of of the availability
	 * @param column column of the date
	 * @return the LocalDateTime
	 */
	private LocalDateTime getAvailabilityLocalDateTime(Row row, int column) {
		Date date = row.getCell(column).getDateCellValue();

		if (date == null || date.equals(new Date(0))) return null;

		LocalDate localDate = row.getCell(column).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		double hourDouble = row.getCell(column + 1).getNumericCellValue() * 24;
		int hour = (int) hourDouble;
		int minute = (int) ((hourDouble - hour) * 60);
		LocalTime time = LocalTime.of(hour, minute);

		return LocalDateTime.of(localDate, time);
	}

	/**
	 * Loads a single service provider to a given list, loading to the set of users as well.
	 *
	 * @param row              the row of the provider
	 * @param users            the set of users
	 * @param serviceProviders the given list
	 */
	private void loadSingleProvider(Row row, List<ServiceProvider> serviceProviders, Set<User> users) {
		String number = new BigDecimal(row.getCell(0).getNumericCellValue()).toPlainString();
		String completeName = row.getCell(2).getStringCellValue();
		String abbreviatedName = row.getCell(3).getStringCellValue();
		String email = row.getCell(4).getStringCellValue();
		PostCode postCode = HomeServices.getInstance().getCompany().getDataManager().get(PostCodeManager.class).getPostCode(row.getCell(5).getStringCellValue());
		String password = "bootstrapsp";

		User user = new User(email, password);
		user.addRole(User.Role.SERVICE_PROVIDER);
		users.add(user);

		ServiceProvider serviceProvider = new ServiceProvider(number, completeName, abbreviatedName, email, postCode);

		for (int column = 6; column < 9; column++) addCategoryProvider(row, column, serviceProvider);
		for (int column = 9; column < 11; column++) addGeographicalAreaProvider(row, column, serviceProvider);

		serviceProviders.add(serviceProvider);
	}

	/**
	 * Adds a geographical area to a service provider.
	 *
	 * @param row             the row of the provider
	 * @param column          the column of the category
	 * @param serviceProvider the service provider
	 */
	private void addGeographicalAreaProvider(Row row, int column, ServiceProvider serviceProvider) {
		String geographicalArea = row.getCell(column).getStringCellValue();

		GeographicalAreaManager geographicalAreaManager = HomeServices.getInstance().getCompany().getDataManager().get(GeographicalAreaManager.class);
		if (!geographicalArea.isEmpty())
			serviceProvider.addGeographicalArea(geographicalAreaManager.getGeographicalArea(geographicalArea));
	}

	/**
	 * Adds a category to a service provider.
	 *
	 * @param row             the row of the provider
	 * @param column          the column of the category
	 * @param serviceProvider the service provider
	 */
	private void addCategoryProvider(Row row, int column, ServiceProvider serviceProvider) {
		String categoryId = new BigDecimal(row.getCell(column).getNumericCellValue()).toPlainString();

		CategoryManager categoryManager = HomeServices.getInstance().getCompany().getDataManager().get(CategoryManager.class);
		if (!categoryId.isEmpty())
			serviceProvider.addCategory(categoryManager.getCategory(categoryId));
	}

	/**
	 * Loads a single service request into a given list.
	 *
	 * @param row             the row of the service request
	 * @param serviceRequests the given list
	 */
	private void loadSingleRequest(Row row, List<ServiceRequest> serviceRequests) {
		String taxId = new BigDecimal(row.getCell(1).getNumericCellValue()).toPlainString();
		String address = row.getCell(2).getStringCellValue();
		String postCodeDesignation = row.getCell(4).getStringCellValue();

		DataManager dataManager = HomeServices.getInstance().getCompany().getDataManager();
		Client client = dataManager.get(ClientManager.class).getClientTIN(taxId);
		PostCode postCode = dataManager.get(PostCodeManager.class).getPostCode(postCodeDesignation);
		PostalAddress postalAddress = new PostalAddress(address, postCode);

		ServiceRequest request = new ServiceRequest(client, postalAddress);

		String description = row.getCell(7).getStringCellValue();
		String serviceId = new BigDecimal(row.getCell(6).getNumericCellValue()).toPlainString();
		float duration;

		Service service = dataManager.get(ServiceManager.class).getService(serviceId);

		if (service instanceof FixedService) duration = (float) ((FixedService) service).getPredefinedDuration();
		else duration = (float) (row.getCell(8).getNumericCellValue() / 60);

		ServiceDescription serviceDescription = new ServiceDescription(description, service, duration);
		request.getServiceDescriptionList().addServiceDescription(serviceDescription);

		addRequestSchedulePreference(row, 9, request);
		addRequestSchedulePreference(row, 11, request);

		int order = (int) row.getCell(0).getNumericCellValue();
		if (serviceRequests.size() > order) serviceRequests.set(order, request);
		else serviceRequests.add(request);
	}

	/**
	 * Gets a schedule of a request.
	 *
	 * @param row    the row of the request
	 * @param column the column where the date is
	 */
	private void addRequestSchedulePreference(Row row, int column, ServiceRequest request) {
		Date date = row.getCell(column).getDateCellValue();

		if (date == null || date.equals(new Date(0))) return;

		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		double timeDouble = row.getCell(column + 1).getNumericCellValue() * 24;
		int hours = (int) timeDouble;
		int minutes = (int) ((timeDouble - hours) * 60);

		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();
		request.getScheduleList().addSchedulePreference(new Schedule(year, month, day, hours, minutes));
	}

	/**
	 * Loads a single geographical area.
	 *
	 * @param row               the row in the sheet
	 * @param geographicalAreas the list to load to
	 */
	private void loadSingleArea(Row row, List<GeographicalArea> geographicalAreas) {
		String designation = row.getCell(0).getStringCellValue();
		PostCode postCode = HomeServices.getInstance().getCompany().getDataManager().get(PostCodeManager.class).getPostCode(row.getCell(1).getStringCellValue());
		double radius = row.getCell(2).getNumericCellValue() * 1000;
		double travelCost = row.getCell(3).getNumericCellValue();
		HashMap<PostCode, Double> acting = HomeServices.getInstance().getCompany().getExternalGeographicalService().getActing(postCode, radius);

		geographicalAreas.add(new GeographicalArea(designation, postCode, radius, travelCost, acting));
	}

	/**
	 * Loads a single service to a list.
	 *
	 * @param row      the row of the service
	 * @param services the list
	 */
	private void loadSingleService(Row row, List<Service> services) {
		String type = row.getCell(1).getStringCellValue();

		String id = new BigDecimal(row.getCell(0).getNumericCellValue()).toPlainString();
		String briefDescription = row.getCell(2).getStringCellValue();
		String completeDescription = row.getCell(3).getStringCellValue();
		double cost = row.getCell(4).getNumericCellValue();
		String categoryStr = new BigDecimal(row.getCell(5).getNumericCellValue()).toPlainString();

		Category category = HomeServices.getInstance().getCompany().getDataManager().get(CategoryManager.class).getCategory(categoryStr);

		Service service;
		switch (type) {
			case "FixedService":
				service = new FixedService(id, briefDescription, completeDescription, cost, category);
				break;
			case "LimitedService":
				service = new LimitedService(id, briefDescription, completeDescription, cost, category);
				break;
			case "ExpandedService":
				service = new ExpandableService(id, briefDescription, completeDescription, cost, category);
				break;
			default:
				HomeServices.getInstance().getLoggerManager().getLogger().log(Level.SEVERE, () -> "Couldn't load service " + id);
				return;
		}

		if (service instanceof FixedService) {
			double duration = row.getCell(6).getNumericCellValue();
			((FixedService) service).setPredefinedDuration(duration / 60);
		}

		services.add(service);
	}

	/**
	 * Loads a single category into a given list.
	 *
	 * @param row        the row of the category
	 * @param categories the given list
	 */
	private void loadSingleCategory(Row row, List<Category> categories) {
		String id = new BigDecimal(row.getCell(0).getNumericCellValue()).toPlainString();
		String description = row.getCell(1).getStringCellValue();

		categories.add(new Category(id, description));
	}

	/**
	 * Loads a single candidature.
	 *
	 * @param row          the row of the candidature
	 * @param candidatures the list of candidatures
	 */
	private void loadSingleCandidature(Row row, List<Candidature> candidatures) {
		String taxId = new BigDecimal(row.getCell(0).getNumericCellValue()).toPlainString();
		String name = row.getCell(1).getStringCellValue();
		String phoneNumber = new BigDecimal(row.getCell(2).getNumericCellValue()).toPlainString();
		String email = row.getCell(3).getStringCellValue();

		String address = row.getCell(4).getStringCellValue();
		String postCodeStr = row.getCell(6).getStringCellValue();
		PostCodeManager postCodeManager = HomeServices.getInstance().getCompany().getDataManager().get(PostCodeManager.class);
		PostalAddress postalAddress = new PostalAddress(address, postCodeManager.getPostCode(postCodeStr));

		Candidature candidature = new Candidature(name, taxId, phoneNumber, email, postalAddress);

		for (int column = 7; column < 10; column++) addAcademicQualification(row, column, candidature);
		for (int column = 10; column < 13; column++) addProfessionalQualification(row, column, candidature);
		for (int column = 13; column < 16; column++) addCategoryCandidature(row, column, candidature);

		candidatures.add(candidature);
	}

	/**
	 * Adds a single academic qualification to a candidature.
	 *
	 * @param row         the row of the candidature
	 * @param column      the column of the qualification
	 * @param candidature the candidature
	 */
	private void addAcademicQualification(Row row, int column, Candidature candidature) {
		String academicDegree = row.getCell(column).getStringCellValue();

		if (!academicDegree.isEmpty())
			candidature.addAcademicQualification(new AcademicQualification(academicDegree, "", ""));
	}

	/**
	 * Adds a single professional qualification to a candidature.
	 *
	 * @param row         the row of the candidature
	 * @param column      the column of the qualification
	 * @param candidature the candidature
	 */
	private void addProfessionalQualification(Row row, int column, Candidature candidature) {
		String professionalDegree = row.getCell(column).getStringCellValue();

		if (!professionalDegree.isEmpty())
			candidature.addProfessionalQualification(new ProfessionalQualification(professionalDegree));
	}

	/**
	 * Adds a single category to a candidature.
	 *
	 * @param row         the row of the candidature
	 * @param column      the column of the category
	 * @param candidature the candidature
	 */
	private void addCategoryCandidature(Row row, int column, Candidature candidature) {
		String categoryName = row.getCell(column).getStringCellValue();

		CategoryManager categoryManager = HomeServices.getInstance().getCompany().getDataManager().get(CategoryManager.class);
		if (!categoryName.isEmpty())
			candidature.addCategory(categoryManager.getCategoryByDescription(categoryName));
	}

	/**
	 * Loads a single client in a row to a given list. Saving as well the user.
	 *
	 * @param clients the set
	 * @param row     the row
	 * @param users   the set of the users
	 */
	private void loadSingleClient(List<Client> clients, Row row, Set<User> users) {
		String taxId = new BigDecimal(row.getCell(0).getNumericCellValue()).toPlainString();
		String name = row.getCell(1).getStringCellValue();
		String phoneNumber = new BigDecimal(row.getCell(2).getNumericCellValue()).toPlainString();
		String password = row.getCell(4).getStringCellValue();
		String email = row.getCell(3).getStringCellValue();

		User user = new User(email, password);
		user.addRole(User.Role.CLIENT);

		users.add(user);

		String address = row.getCell(5).getStringCellValue();
		String postCodeStr = row.getCell(7).getStringCellValue();
		PostCodeManager postCodeManager = HomeServices.getInstance().getCompany().getDataManager().get(PostCodeManager.class);
		PostalAddress postalAddress = new PostalAddress(address, postCodeManager.getPostCode(postCodeStr));

		clients.add(new Client(name, taxId, phoneNumber, email, postalAddress));
	}

	/**
	 * Gets the row iterator for a specific sheet.
	 * The iterator is already past the header.
	 *
	 * @param sheetName the name of the sheet
	 * @return the row iterator
	 */
	private Iterator getRowIterator(String sheetName) {
		Sheet sheet = workbook.getSheet(sheetName);

		Iterator rowIterator = sheet.rowIterator();

		if (rowIterator.hasNext()) rowIterator.next(); //jumps the header

		return rowIterator;
	}
}
