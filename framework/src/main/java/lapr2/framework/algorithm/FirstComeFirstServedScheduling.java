package lapr2.framework.algorithm;

import lapr2.framework.company.servicerequest.ServiceRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the algorithm as first come firsts served.
 *
 * @author flow
 */
public class FirstComeFirstServedScheduling extends SchedulingAlgorithm {

	/**
	 * Orders the requests by first come first served.
	 *
	 * @param serviceRequests the collection with the requests
	 * @return the requests ordered in form of list
	 */
	@Override
	protected List<ServiceRequest> orderRequests(List<ServiceRequest> serviceRequests) {
		return new ArrayList<>(serviceRequests);
	}
}
