package lapr2.framework.logger;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Formatter to the application's logger.
 */
public class LoggerFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {
		return record.getLevel() + ": " +
				formatMessage(record) +
				System.lineSeparator();
	}
}
