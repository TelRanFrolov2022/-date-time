package time;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Set;

public class WorkingDays implements TemporalAdjuster {
	private int daysNum;
	private Set<DayOfWeek> daysOff;

	public WorkingDays(int amountOfDays, Set<DayOfWeek> dayOffs) {
		this.daysNum = amountOfDays;
		this.daysOff = dayOffs;
	}

	@Override
	public Temporal adjustInto(Temporal temporal) {
		Temporal res = temporal;
		int i = 0;
		while (i != daysNum) {
			DayOfWeek currDayOfWeek = DayOfWeek.of(res.get(ChronoField.DAY_OF_WEEK));
			if (!daysOff.contains(currDayOfWeek)) {
				i++;
			}
			res = res.plus(1, ChronoUnit.DAYS);
		}
		return res;
	}

}