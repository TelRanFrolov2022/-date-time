package time.application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

public class PrintCalendar {

	private static final String LANGUAGE_TAG = "en";
	private static final int YEAR_OFFSET = 10;
	private static final int WIDTH_FIELD = 4;
	private static Locale locale = Locale.forLanguageTag(LANGUAGE_TAG);

	public static void main(String[] args)  {
		try {
			int monthYear[] = getMonthYear(args);
			printCalendar(monthYear[0], monthYear[1], monthYear[2]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void printCalendar(int month, int year, int firstDay) {
		printTitle(month, year);
		DayOfWeek[] sortedDays = printWeekDays(firstDay);
		printDates(month, year, firstDay, sortedDays);
		
	}

	private static void printDates(int month, int year, int firstDayOfWeek, DayOfWeek[] sortedDays) {		
		int counter=0;
		DayOfWeek firstDay = getFirstDay(month, year);
		while (sortedDays[counter++]!=firstDay);		
		int offset = (counter-1)* WIDTH_FIELD;
		int dayNumber = YearMonth.of(year, month).lengthOfMonth(); 
		System.out.printf("%s", " ".repeat(offset));
		for(int date = 1; date <= dayNumber ; date++) {
			System.out.printf("%4d",date);
			if (++counter > 7) {
				System.out.println();
				counter = 1;
			}
		}		
	}


	private static DayOfWeek getFirstDay(int month, int year) {
		return LocalDate.of(year, month, 1).getDayOfWeek();
	}

	private static DayOfWeek[] printWeekDays(int firstDayOfWeek) {
		System.out.print("  ");
		DayOfWeek[] daysArr = DayOfWeek.values();
		if (firstDayOfWeek > 1) daysArr = sortArrayOfDays(firstDayOfWeek, daysArr);
		Arrays.stream(daysArr).forEach(dw -> System.out.printf("%s ", dw.getDisplayName(TextStyle.SHORT, locale)));
		System.out.println();
		return daysArr;

	}

	private static DayOfWeek[] sortArrayOfDays(int firstDayOfWeek, DayOfWeek[] daysOfWeek) {
		DayOfWeek[] daysArrSorted = new DayOfWeek[daysOfWeek.length];
		System.arraycopy(daysOfWeek, firstDayOfWeek - 1, daysArrSorted, 0, daysOfWeek.length - firstDayOfWeek + 1);
		System.arraycopy(daysOfWeek, 0, daysArrSorted, daysOfWeek.length - firstDayOfWeek + 1, firstDayOfWeek - 1);
		return daysArrSorted;
	}

	private static void printTitle(int month, int year) {
		System.out.printf("%s%d, %s\n", " ".repeat(YEAR_OFFSET), year,Month.of(month)
				.getDisplayName(TextStyle.FULL, locale));
	}
	
 static int getFirstDayOfWeek(String[] args) throws Exception {
		int res = 1;
		if (args.length > 2) {
			try {
				res = DayOfWeek.valueOf(args[2].toUpperCase(locale)).getValue();
			} catch (Exception e) {
				throw new Exception("incorrect firstday");
			}
		}
		return res;
	}

	private static int getYearArgs(String[] args) throws Exception {
		int res = LocalDate.now().getYear();
		if (args.length > 1) {
			try {
				res = Integer.parseInt(args[1]);
				if (res < 0) {
					throw new Exception("year must be a positive number");
				}
			} catch (NumberFormatException e) {
				throw new Exception("year must be a number");
			}
		}
		return res;
	}

	private static int getMonthArgs(String[] args) throws Exception {
		try {
			int res = Integer.parseInt(args[0]);
			if (res < 1 || res > 12) {
				throw new Exception("Month should be a number in the range [1-12]");
			}
			return res;
		} catch (NumberFormatException e) {
			throw new Exception("Month should be a number");
		}
	}

	private static int[] getCurrentMonthYear() {
		LocalDate current = LocalDate.now();
		return new int[] { current.getMonth().getValue(), current.getYear(), 1 };
	}
	private static int[] getMonthYear(String[] args) throws Exception {

		return args.length == 0 ? getCurrentMonthYear() : getMonthYearArgs(args);
	}

	private static int[] getMonthYearArgs(String[] args) throws Exception {

		return new int[] { getMonthArgs(args), getYearArgs(args), getFirstDayOfWeek(args) };
	}
}