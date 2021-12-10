package lapr2.framework.company;

import lapr2.framework.company.affectation.AffectationAssignment;
import lapr2.framework.company.location.geographicalservice.ExternalGeographicalService;
import lapr2.framework.company.location.geographicalservice.GeographicalService;
import lapr2.framework.company.manager.DataManager;
import lapr2.framework.homeservices.HomeServices;
import lombok.Getter;

import java.util.logging.Level;

/**
 * Represents a company, providing access to its designation, tax identification number and respective managers.
 *
 * @author flow
 */
public class Company {

	/**
	 * The designation of the company.
	 */
	@Getter
	private final String designation;

	/**
	 * The tax ID of the company.
	 */
	@Getter
	private final String taxIdentificationNumber;

	/**
	 * The data manager that stores the managers.
	 */
	@Getter
	private final DataManager dataManager;

	/**
	 * The geographical service of the company.
	 */
	@Getter
	private final ExternalGeographicalService externalGeographicalService;

	/**
	 * The instance responsible to affect service providers.
	 */
	@Getter
	private final AffectationAssignment affectationAssignment;

	/**
	 * Constructs a new company with the key values of it.
	 *
	 * @param designation             the designation of the company
	 * @param taxIdentificationNumber the tax ID of the company
	 */
	public Company(String designation, String taxIdentificationNumber) {
		this.designation = designation;
		this.taxIdentificationNumber = taxIdentificationNumber;

		this.dataManager = new DataManager();
		this.externalGeographicalService = new GeographicalService();
		this.affectationAssignment = new AffectationAssignment();

		HomeServices.getInstance().getLoggerManager().getLogger().log(Level.INFO, "Company is now initialized");
	}
}
