package lapr2.agpsdapplication;

import javafx.stage.Stage;
import lapr2.agpsdapplication.scene.SceneManager;
import lapr2.framework.company.Company;
import lapr2.framework.company.affectation.AffectationManager;
import lapr2.framework.company.candidature.CandidatureManager;
import lapr2.framework.company.category.CategoryManager;
import lapr2.framework.company.location.geographicalarea.GeographicalAreaManager;
import lapr2.framework.company.location.postcode.PostCodeManager;
import lapr2.framework.company.service.ServiceManager;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrderManager;
import lapr2.framework.company.servicerequest.ServiceRequestManager;
import lapr2.framework.company.servicetype.ServiceTypeManager;
import lapr2.framework.company.user.administrator.Administrator;
import lapr2.framework.company.user.administrator.AdministratorManager;
import lapr2.framework.company.user.client.ClientManager;
import lapr2.framework.company.user.humanresourcesofficer.HumanResourcesOfficer;
import lapr2.framework.company.user.humanresourcesofficer.HumanResourcesOfficerManager;
import lapr2.framework.company.user.serviceprovider.ServiceProviderManager;
import lapr2.framework.company.user.user.User;
import lapr2.framework.company.user.user.UserManager;
import lapr2.framework.config.FileConfiguration;
import lapr2.framework.homeservices.HomeServices;
import lapr2.framework.io.bootstrap.CustomBootstrap;
import lapr2.framework.session.CurrentSession;
import lombok.Getter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

public class Application extends javafx.application.Application {

	@Getter
	private static SceneManager sceneManager;

	public static void main(String[] args) {
		bootstrap(args);
		bootstrapDefaultUsers();
		launch(args);
	}

	private static void bootstrapDefaultUsers() {
		UserManager userManager = HomeServices.getInstance().getCompany().getDataManager().get(UserManager.class);
		AdministratorManager administratorManager = HomeServices.getInstance().getCompany().getDataManager().get(AdministratorManager.class);
		HumanResourcesOfficerManager humanResourcesOfficerManager = HomeServices.getInstance().getCompany().getDataManager().get(HumanResourcesOfficerManager.class);

		User admin = userManager.createUser("admin@lapr2.pt", "lapr2");
		User hro = userManager.createUser("hro@lapr2.pt", "lapr2");

		admin.addRole(User.Role.ADMINISTRATOR);
		hro.addRole(User.Role.HUMAN_RESOURCES_OFFICER);

		userManager.add(admin);
		userManager.add(hro);

		administratorManager.add(new Administrator("Admin LAPR2", "admin@lapr2.pt"));
		humanResourcesOfficerManager.add(new HumanResourcesOfficer("Human Resources Officer", "hro@lapr2.pt"));
	}

	private static void bootstrap(String[] args) {
		HomeServices homeServices = HomeServices.getInstance();

		FileConfiguration fileConfiguration = new FileConfiguration();

		Company company = new Company(fileConfiguration.getProperty("company.name"), fileConfiguration.getProperty("company.taxIdentificationNumber"));

		company.getDataManager().set(new PostCodeManager(HomeServices.FOLDER_PATH + "bin/postcodes.dat"));
		company.getDataManager().set(new UserManager(HomeServices.FOLDER_PATH + "bin/users.dat"));
		company.getDataManager().set(new GeographicalAreaManager(HomeServices.FOLDER_PATH + "bin/geographicalareas.dat"));
		company.getDataManager().set(new ClientManager(HomeServices.FOLDER_PATH + "bin/clients.dat"));
		company.getDataManager().set(new ServiceExecutionOrderManager(HomeServices.FOLDER_PATH + "bin/serviceexecutionorders.dat"));
		company.getDataManager().set(new ServiceProviderManager(HomeServices.FOLDER_PATH + "bin/serviceproviders.dat"));
		company.getDataManager().set(new HumanResourcesOfficerManager());
		company.getDataManager().set(new AdministratorManager());
		company.getDataManager().set(new CategoryManager(HomeServices.FOLDER_PATH + "bin/categories.dat"));
		company.getDataManager().set(new ServiceTypeManager());
		company.getDataManager().set(new ServiceManager(HomeServices.FOLDER_PATH + "bin/services.dat"));
		company.getDataManager().set(new CandidatureManager(HomeServices.FOLDER_PATH + "bin/candidatures.dat"));
		company.getDataManager().set(new ServiceRequestManager(HomeServices.FOLDER_PATH + "bin/servicerequests.dat"));
		company.getDataManager().set(new ServiceExecutionOrderManager(HomeServices.FOLDER_PATH + "bin/serviceexecutionorders.dat"));
		company.getDataManager().set(new AffectationManager(HomeServices.FOLDER_PATH + "bin/affectations.dat"));
		//Set managers above this.

		homeServices.setFileConfiguration(fileConfiguration);
		homeServices.setSession(new CurrentSession());
		homeServices.setCompany(company);

		if (args.length == 2 && args[0].equals("-test")) {
			try {
				if (fileConfiguration.getProperty("firstLoad").equalsIgnoreCase("true")) {
					CustomBootstrap bootstrap = new CustomBootstrap(args[1]);
					bootstrap.run();

					fileConfiguration.getProperties().setProperty("firstLoad", "false");
					fileConfiguration.save();
				}
			} catch (IOException e) {
				HomeServices.getInstance().getLoggerManager().getLogger().log(Level.SEVERE, String.format("%s is not a valid path. Loading the defaults", args[1]), FileNotFoundException.class);
			}
		}

		company.getDataManager().loadAll();

		if (company.getAffectationAssignment().start()) {
			HomeServices.getInstance().getLoggerManager().getLogger().log(Level.INFO, "The affectation assignment is now working");
		} else {
			HomeServices.getInstance().getLoggerManager().getLogger().log(Level.SEVERE, "Could not start the affectation assignment task");
		}

	}

	@Override
	public void start(Stage primaryStage) {
		Application.sceneManager = new SceneManager(primaryStage);

		Application.sceneManager.loadLoginPage();
	}

}
