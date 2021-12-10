package lapr2.framework.algorithm;

import lapr2.framework.company.servicerequest.ServiceRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomScheduling extends SchedulingAlgorithm {

	@Override
	protected List<ServiceRequest> orderRequests(List<ServiceRequest> serviceRequests) {
		List<ServiceRequest> res = new ArrayList<>(serviceRequests);

		Collections.shuffle(res);

		return res;
	}
}
