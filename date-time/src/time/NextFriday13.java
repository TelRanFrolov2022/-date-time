package time;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class NextFriday13 implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		int currDay = temporal.get(ChronoField.DAY_OF_MONTH);
		if (currDay < 13) {
			temporal = temporal.plus(13 - currDay, ChronoUnit.DAYS);
		} else if (currDay > 13) {
			temporal = temporal.minus(currDay - 13, ChronoUnit.DAYS);
		}
		while (temporal.get(ChronoField.DAY_OF_WEEK) != DayOfWeek.FRIDAY.getValue()) {
			temporal = temporal.plus(1, ChronoUnit.MONTHS);
		}
		return temporal;
	}
}