package lapr2.framework.company.affectation;

import lapr2.framework.algorithm.SchedulingAlgorithm;
import lapr2.framework.company.Company;
import lapr2.framework.company.servicerequest.ServiceRequest;
import lapr2.framework.company.servicerequest.ServiceRequestManager;
import lapr2.framework.homeservices.HomeServices;

import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;

/**
 * Runs the affectation of service providers at a given time.
 *
 * @author flow
 */
public class AffectProvidersTask extends TimerTask {

	/**
	 * Runs the affectation of service providers.
	 */
	@Override
	public void run() {
		HomeServices.getInstance().getLoggerManager().getLogger().log(Level.WARNING, "The system is affecting service providers...");
		affectProviders();
		HomeServices.getInstance().getLoggerManager().getLogger().log(Level.WARNING, "Service providers affected");
	}

	/**
	 * Affects {@link lapr2.framework.company.user.serviceprovider.ServiceProvider} to {@link lapr2.framework.company.servicerequest.ServiceDescription}
	 * using {@link lapr2.framework.company.affectation.Affectation}.
	 */
	private void affectProviders() {
		Company company = HomeServices.getInstance().getCompany();
		ServiceRequestManager serviceRequestManager = company.getDataManager().get(ServiceRequestManager.class);
		List<ServiceRequest> notAffectedRequests = serviceRequestManager.getNotFullyAffectedServiceRequests();
		SchedulingAlgorithm schedulingAlgorithm = company.getAffectationAssignment().getSchedulingAlgorithm();
		schedulingAlgorithm.affectProviders(notAffectedRequests);
	}
}
