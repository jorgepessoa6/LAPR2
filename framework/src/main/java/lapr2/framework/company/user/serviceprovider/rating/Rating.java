package lapr2.framework.company.user.serviceprovider.rating;

import lapr2.framework.company.user.serviceprovider.ServiceProviderManager;
import lapr2.framework.homeservices.HomeServices;
import lapr2.framework.utils.WordUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Represents the {@link Rating} of a {@link lapr2.framework.company.user.serviceprovider.ServiceProvider}, providing access to its reviews,
 * label, suggested label, mean and standard deviation.
 */
public class Rating implements Serializable, Comparable<Rating> {

	private static final long serialVersionUID = -6442090920944927498L;

	public static final int MIN_RATING = 0;

	public static final int MAX_RATING = 5;

	/**
	 * The reviews number.
	 */
	private final int[] reviews = new int[MAX_RATING - MIN_RATING + 1];

	/**
	 * The label.
	 */
	@Getter
	@Setter
	private Label label = Label.NONE;

	/**
	 * Computes the probability of getting a rating in the total reviews scenario.
	 *
	 * @param reviews the reviews
	 * @return the reviews probability array
	 */
	public static float[] getReviewsProbability(int[] reviews) {
		int reviewCount = getReviewCount(reviews);

		float[] reviewsProbability = new float[reviews.length];

		if (reviewCount == 0) {
			return reviewsProbability;
		}

		for (int index = 0; index < reviews.length; index++) {
			reviewsProbability[index] = reviews[index] / (float) reviewCount;
		}

		return reviewsProbability;
	}

	/**
	 * Computes the total review count.
	 *
	 * @param reviews the reviews
	 * @return the review count
	 */
	private static int getReviewCount(int[] reviews) {
		int count = 0;

		for (int review : reviews) {
			count += review;
		}

		return count;
	}

	/**
	 * Adds a review to the rating reviews given a specific rating value.
	 *
	 * @param rating the rating value
	 * @return <code>true</code> if the rating is between 0 and 5, <code>false</code> otherwise
	 */
	public boolean add(int rating) {
		if (rating >= MIN_RATING && rating <= MAX_RATING) {
			reviews[rating]++;
			return true;
		}

		return false;
	}

	/**
	 * Returns the number of reviews of a specific rating value. The rating value must be between 0 and 5.
	 * If the rating value is not valid, 0 is returned as a default value.
	 *
	 * @param rating the rating value
	 * @return the review number
	 */
	public int getReviewNumber(int rating) {
		if (rating >= MIN_RATING && rating <= MAX_RATING) {
			return reviews[rating];
		}

		return 0;
	}

	/**
	 * Computes the mean of the reviews. Since the rating is a discrete distribution the algorithm used is based
	 * on the mean formula of discrete distributions.
	 *
	 * @return the mean
	 */
	public float getMean() {
		if (getReviewCount(reviews) == 0) {
			return 3.0f;
		}

		float[] reviewsProbability = getReviewsProbability(reviews);

		float mean = 0;

		for (int index = 0; index < reviewsProbability.length; index++) {
			mean += index * reviewsProbability[index];
		}

		return mean;
	}

	/**
	 * Computes the standard deviation of the reviews. Since the rating is a discrete distribution the algorithm used
	 * is based on the standard deviation formula of discrete distributions.
	 *
	 * @return the standard deviation
	 */
	public double getStandardDeviation() {
		if (getReviewCount(reviews) == 0) {
			return 0d;
		}

		float[] reviewsProbability = getReviewsProbability(reviews);

		float standardDeviation = 0;

		for (int index = 0; index < reviewsProbability.length; index++) {
			standardDeviation += Math.pow(index, 2) * reviewsProbability[index];
		}

		return standardDeviation - Math.pow(getMean(), 2);
	}

	/**
	 * Returns a suggested label based on the population mean and standard deviation and the current mean.
	 * <p>
	 * The rating of a service provider can be classified as 'Worst Providers', 'Regular Providers' and
	 * 'Outstanding Providers'.
	 * <p>
	 * The rating is labelled as 'Worst Providers' if the rating mean of the service provider is less than the
	 * population mean subtracted by the population standard deviation.
	 * The rating is labelled as 'Outstanding Providers' if the rating mean of the service provider is greater than
	 * the population mean added by the population standard deviation.
	 * The rating is labelled as 'Regular Providers' in remaining cases.
	 *
	 * @return the suggested label
	 */
	public Label getSuggestedLabel() {
		ServiceProviderManager serviceProviderManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceProviderManager.class);

		float mean = getMean();
		float populationMean = serviceProviderManager.getPopulationMean();
		double populationStandardDeviation = serviceProviderManager.getPopulationStandardDeviation();

		if (mean < populationMean - populationStandardDeviation) {
			return Label.WORST_PROVIDERS;
		} else if (mean > populationMean + populationStandardDeviation) {
			return Label.OUTSTANDING_PROVIDERS;
		} else {
			return Label.REGULAR_PROVIDER;
		}
	}

	/**
	 * Compares this rating with a given one.
	 *
	 * @param rating the given rating
	 * @return a positive number if this rating is higher, null if they are the same, and a negative if the given rating is higher
	 */
	@Override
	public int compareTo(Rating rating) {
		double difference = this.getMean() - rating.getMean();
		if (difference == 0) return 0;
		return (difference > 0) ? 1 : -1;
	}

	/**
	 * Verifies if this rating is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equal, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Rating)) return false;
		Rating rating = (Rating) o;
		return Arrays.equals(reviews, rating.reviews);
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Arrays.hashCode(reviews);
	}

	/**
	 * Represents the label of a rating.
	 */
	public enum Label {
		NONE,
		WORST_PROVIDERS,
		REGULAR_PROVIDER,
		OUTSTANDING_PROVIDERS;

		@Override
		public String toString() {
			return WordUtils.capitalize(this.name());
		}
	}
}
