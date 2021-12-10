package lapr2.framework.company.affectation;

import lapr2.framework.company.manager.Manager;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrder;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrderManager;
import lapr2.framework.company.servicerequest.Schedule;
import lapr2.framework.company.servicerequest.ServiceDescription;
import lapr2.framework.company.servicerequest.ServiceRequest;
import lapr2.framework.company.user.serviceprovider.ServiceProvider;
import lapr2.framework.homeservices.HomeServices;

import java.time.LocalDateTime;

/**
 * Stores and retrieves all the {@link Affectation}, providing access to its elements.
 *
 * @author flow
 */
public class AffectationManager extends Manager<Affectation> {

	/**
	 * Constructs a new affectation manager associating the path to its binary file.
	 *
	 * @param filePath the path to the binary file
	 */
	public AffectationManager(String filePath) {
		super(filePath);
	}

	/**
	 * Verifies if an affectation is valid.
	 *
	 * @param affectation the affectation
	 * @return <code>true</code> if the affectation is valid, <code>false</code> otherwise
	 */
	@Override
	public boolean isSecure(Affectation affectation) {
		return affectation != null && isValid(affectation);
	}

	/**
	 * Creates an affectation adding it.
	 *
	 * @param serviceRequest     the service request of the affectation
	 * @param serviceDescription the service description of the affectation
	 * @param serviceProvider    the service provider of the affectation
	 * @param schedule           the schedule of the affectation
	 * @return <code>true</code> if it was successfully affected, <code>false</code> otherwise
	 */
	public boolean affect(ServiceRequest serviceRequest, ServiceDescription serviceDescription, ServiceProvider serviceProvider, Schedule schedule) {
		Affectation affectation = new Affectation(serviceRequest, serviceDescription, serviceProvider, schedule);
		return add(affectation);
	}

	/**
	 * Verifies if a service description is already affected.
	 *
	 * @return <code>true</code> if the service description is affected, <code>false</code> otherwise
	 */
	public boolean isServiceDescriptionAffected(ServiceDescription serviceDescription) {
		/*for (Affectation affectation : elements) {
			if (affectation.getState() != Affectation.State.REJECTED && affectation.getServiceDescription().equals(serviceDescription))
				return true;
		}
		return false;*/

		for (ServiceExecutionOrder order : HomeServices.getInstance().getCompany().getDataManager().get(ServiceExecutionOrderManager.class).getElements())
			if (order.getAffectation().getServiceDescription().equals(serviceDescription))
				return true;

		return false;
	}

	/**
	 * Verifies if an affectation is valid globally.
	 *
	 * @param affectation the affectation
	 * @return <code>true</code> if the affectation is valid globally, <code>false</code> otherwise
	 */
	@Override
	public boolean isValid(Affectation affectation) {
		return !isProviderBusy(affectation);
	}

	/**
	 * Verifies if the affectation provider already has a service for the given schedule.
	 *
	 * @param affectation affectation of the provider
	 * @return <code>true</code> if the provider already had a service, <code>false</code> otherwise
	 */
	private boolean isProviderBusy(Affectation affectation) {
		LocalDateTime affectationStart = affectation.getSchedule().getStartTime();
		LocalDateTime affectationStop = affectationStart.plusMinutes((long) (affectation.getServiceDescription().getDuration() * 60));

		LocalDateTime listedAffectationStart;
		LocalDateTime listedAffectationStop;

		for (Affectation listedAffectation : elements) {

			if (listedAffectation.getServiceProvider().equals(affectation.getServiceProvider())) {
				listedAffectationStart = listedAffectation.getSchedule().getStartTime();
				listedAffectationStop = listedAffectationStart.plusMinutes((long) (listedAffectation.getServiceDescription().getDuration() * 60));

				if (affectationStart.isBefore(listedAffectationStop) && affectationStop.isAfter(listedAffectationStart)) {
					return true;
				}
			}
		}

		return false;
	}
}
