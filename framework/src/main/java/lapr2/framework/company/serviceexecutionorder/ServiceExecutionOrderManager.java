package lapr2.framework.company.serviceexecutionorder;


import lapr2.framework.company.affectation.Affectation;
import lapr2.framework.company.manager.Manager;
import lapr2.framework.company.servicerequest.ServiceRequest;
import lapr2.framework.company.user.client.Client;
import lapr2.framework.company.user.serviceprovider.ServiceProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Stores and retrieves all the {@link ServiceExecutionOrder}s of the Company. Also allows their
 * creation and validation.
 *
 * @author flow
 */
public class ServiceExecutionOrderManager extends Manager<ServiceExecutionOrder> {

	/**
	 * Constructs a new service execution order manager associating the path to its binary file.
	 *
	 * @param filePath the path to the binary file
	 */
	public ServiceExecutionOrderManager(String filePath) {
		super(filePath);
	}

	/**
	 * Creates a new {@link ServiceExecutionOrder} returning it.
	 *
	 * @param affectation the affectation
	 * @return the created service execution order
	 */
	public ServiceExecutionOrder createServiceExecutionOrder(Affectation affectation) {
		return new ServiceExecutionOrder(affectation);
	}

	/**
	 * Returns a list of service execution orders waiting evaluation of a specific service provider.
	 *
	 * @param serviceProvider the service provider
	 * @return the list of service execution orders waiting evaluation of the service provider
	 */
	public List<ServiceExecutionOrder> getServicesWaitingEvaluation(ServiceProvider serviceProvider) {
		List<ServiceExecutionOrder> servicesWaitingEvaluationList = new ArrayList<>();
		for (ServiceExecutionOrder serviceExecutionOrder : elements) {
			if (LocalDateTime.now().isAfter(serviceExecutionOrder.getAffectation().getSchedule().getStartTime()) &&
                    serviceExecutionOrder.getAffectation().getServiceProvider().getEmail().equals(serviceProvider.getEmail()) &&
					serviceExecutionOrder.getState().equals(ServiceExecutionOrder.State.TO_DO)) {
				servicesWaitingEvaluationList.add(serviceExecutionOrder);
			}
		}
		return servicesWaitingEvaluationList;
	}

	/**
	 * Returns all the service execution orders of a given client.
	 *
	 * @param client the client
	 * @return a list with the service execution orders
	 */
	public List<ServiceExecutionOrder> getOrdersOfClient(Client client) {
		List<ServiceExecutionOrder> orders = new ArrayList<>();

		for (ServiceExecutionOrder order : elements)
			if (order.getAffectation().getServiceRequest().getClient().equals(client))
				orders.add(order);

		return orders;
	}

	/**
	 * Returns all the completed service execution orders of a given client.
	 *
	 * @param client the client
	 * @return a list with the completed service execution orders
	 */
	public List<ServiceExecutionOrder> getCompletedServiceExecutionOrdersOfClient(Client client) {
		List<ServiceExecutionOrder> clientOrders = getOrdersOfClient(client);
		List<ServiceExecutionOrder> completedClientOrders = new ArrayList<>();

		for (ServiceExecutionOrder serviceExecutionOrder : clientOrders) {
			if (serviceExecutionOrder.getState() == ServiceExecutionOrder.State.EVALUATED) {
				completedClientOrders.add(serviceExecutionOrder);
			}
		}

		return completedClientOrders;
	}

	/**
	 * Returns all the completed and yet unrated service execution orders of a given client.
	 *
	 * @param client the client
	 * @return a list with the completed and unrated service execution orders
	 */
	public List<ServiceExecutionOrder> getCompletedUnratedServiceExecutionOrdersOfClient(Client client) {
		List<ServiceExecutionOrder> completedClientOrders = getCompletedServiceExecutionOrdersOfClient(client);
		List<ServiceExecutionOrder> completedUnratedClientOrder = new ArrayList<>();

		for (ServiceExecutionOrder serviceExecutionOrder : completedClientOrders) {
			if (serviceExecutionOrder.getRateStatus() == ServiceExecutionOrder.RateStatus.TO_DO) {
				completedUnratedClientOrder.add(serviceExecutionOrder);
			}
		}

		return completedUnratedClientOrder;
	}

	/**
	 * Returns all the completed and yet unrated service execution orders of a service request.
	 *
	 * @param serviceRequest the service request
	 * @return a list with the completed and unrated service execution orders of the given service request.
	 */
	public List<ServiceExecutionOrder> getCompletedUnratedServiceExecutionOrdersOfServiceRequest(ServiceRequest serviceRequest) {
		List<ServiceExecutionOrder> requests = new ArrayList<>();

		for (ServiceExecutionOrder serviceExecutionOrder : elements) {
			if (serviceExecutionOrder.getAffectation().getServiceRequest().equals(serviceRequest) &&
					serviceExecutionOrder.getState() == ServiceExecutionOrder.State.EVALUATED &&
					serviceExecutionOrder.getRateStatus() == ServiceExecutionOrder.RateStatus.TO_DO) {
				requests.add(serviceExecutionOrder);
			}
		}

		return requests;
	}

	/**
	 * Checks if a service execution order is secure, verifying its integrity.
	 *
	 * @param serviceExecutionOrder the service execution order
	 * @return <code>true</code> if the service execution order is valid locally and globally, <code>false</code> otherwise
	 */
	@Override
	public boolean isSecure(ServiceExecutionOrder serviceExecutionOrder) {
		return isValid(serviceExecutionOrder);
	}

	/**
	 * Checks if a service execution order is already in the elements of the manager.
	 *
	 * @param serviceExecutionOrder the service execution order
	 * @return <code>true</code> if the service execution order is not in elements of the manager, <code>false</code> otherwise
	 */
	@Override
	public boolean isValid(ServiceExecutionOrder serviceExecutionOrder) {
		return !elements.contains(serviceExecutionOrder);
	}

	/**
	 * Returns all the service execution orders of a given service provider.
	 *
	 * @param serviceProvider the service provider
	 * @return a list with the service execution orders
	 */
	public List<ServiceExecutionOrder> getServiceExecutionOrdersOfServiceProvider(ServiceProvider serviceProvider) {
		List<ServiceExecutionOrder> orders = new ArrayList<>();

		for (ServiceExecutionOrder order : elements) {
            if (order.getAffectation().getServiceProvider().getEmail().equals(serviceProvider.getEmail())) {
				orders.add(order);
			}
		}
		return orders;
	}
}
