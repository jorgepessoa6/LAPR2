package lapr2.framework.company.affectation;

import lapr2.framework.algorithm.SchedulingAlgorithm;
import lapr2.framework.config.FileConfiguration;
import lapr2.framework.homeservices.HomeServices;
import lombok.Getter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

/**
 * Takes care of the affectation of service providers to services.
 *
 * @author flow
 */
public class AffectationAssignment {

	/**
	 * The scheduling algorithm of the company.
	 */
	@Getter
	private SchedulingAlgorithm schedulingAlgorithm;

	/**
	 * The waiting time to the first affectation in ms.
	 */
	private long delay;

	/**
	 * The period of stop between affectations in ms.
	 */
	private long period;

	/**
	 * The timer responsible to run the task.
	 */
	private Timer timer;

	/**
	 * The task that runs at a given time.
	 */
	private TimerTask task;

	/**
	 * Starts the affectation of service providers.
	 *
	 * @return <code>true</code> if it started successfully, <code>false</code> otherwise
	 */
	public boolean start() {
		if (!setup()) return false;

		timer.schedule(task, delay, period);
		return true;
	}

	/**
	 * Sets all the required info up.
	 *
	 * @return <code>true</code> if all the info was successfully loaded from the properties file, <code>false</code> otherwise
	 */
	private boolean setup() {
		this.task = new AffectProvidersTask();
		this.timer = new Timer();

		FileConfiguration fileConfiguration = HomeServices.getInstance().getFileConfiguration();

		if (fileConfiguration == null) return false;

		try {
			this.delay = Long.parseLong(fileConfiguration.getProperty("delay"));
			this.period = Long.parseLong(fileConfiguration.getProperty("period"));

			String algorithm = fileConfiguration.getProperty("schedulingAlgorithm");
			String className = fileConfiguration.getProperty(algorithm + ".path");

			Class algorithmClass = Class.forName(className);
			Constructor constructor = algorithmClass.getConstructor();

			this.schedulingAlgorithm = (SchedulingAlgorithm) constructor.newInstance();

			return true;
		} catch (NullPointerException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
			HomeServices.getInstance().getLoggerManager().getLogger().log(Level.SEVERE, "Couldn't start the providers affectation", e);
			return false;
		}
	}
}
