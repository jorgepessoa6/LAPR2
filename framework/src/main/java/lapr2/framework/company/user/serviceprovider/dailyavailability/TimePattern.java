package lapr2.framework.company.user.serviceprovider.dailyavailability;

import lapr2.framework.utils.WordUtils;

public enum TimePattern {
	DAILY, WEEKLY, MONTHLY, QUARTERLY, SEMIANNUALLY, ANNUALLY;

	@Override
	public String toString() {
		return WordUtils.capitalize(this.name());
	}
}
