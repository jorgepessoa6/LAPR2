package lapr2.serviceproviderapplication;

import lapr2.framework.company.serviceexecutionorder.simpleserviceexecutionorder.SimpleServiceExecutionOrderManager;
import lombok.Getter;

public class ServiceProviderApplication {

	@Getter
	private static ServiceProviderApplication instance = new ServiceProviderApplication();

	@Getter
	private SimpleServiceExecutionOrderManager simpleServiceExecutionOrderManager;

	private ServiceProviderApplication() {
		this.simpleServiceExecutionOrderManager = new SimpleServiceExecutionOrderManager();
	}
}
