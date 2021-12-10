package lapr2.agpsdapplication.utils;

import javafx.scene.control.Label;

import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class WarningUtilities {

	private static Timer timer = new Timer();

	private WarningUtilities() {
	}

	private static HashSet<Label> runningElements = new HashSet<>();

	public static void showLabelWarning(Label label, String message, int time, double fade) {
		// This line prevents from running multiple tasks in the same element.
		if (runningElements.contains(label)) return;

		runningElements.add(label);

		label.setOpacity(0);
		label.setText(message);

		timer.schedule(new TimerTask() {
			private int elapsed = 0;

			private double fadeTime = fade * time * 1000;

			private double endTime = time * 1000 - fadeTime;

			private double increment = 1 / fadeTime;

			@Override
			public void run() {
				elapsed++;

				if (elapsed <= fadeTime) {
					label.setOpacity(label.getOpacity() + increment);
				} else if (elapsed >= endTime) {
					label.setOpacity(label.getOpacity() - increment);
				}

				if (elapsed == time * 1000) {
					label.setOpacity(0);
					runningElements.remove(label);
					this.cancel();
				}
			}
		}, 0, 1);
	}
}
