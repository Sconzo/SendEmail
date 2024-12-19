package com.ti9.send.email.core.infrastructure.adapter.utils;

import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DateUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatDateToString(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return date.format(FORMATTER);
    }

    public static List<LocalDate> getBrazilianHolidays() {
        HolidayManager manager = HolidayManager.getInstance(HolidayCalendar.BRAZIL);
        Set<Holiday> holidays = manager.getHolidays(LocalDate.now().getYear());
        holidays.addAll(manager.getHolidays(LocalDate.now().getYear() + 1));
        return holidays.stream()
                .map(Holiday::getDate)
                .toList();
    }

    public static int calculateWorkDaysDifference(
            LocalDate today,
            LocalDate finalDate,
            List<LocalDate> holidays
    ) {
        int workDays = 0;
        LocalDate currentDay = today;

        int step = finalDate.isAfter(today) ? 1 : -1;

        while (!currentDay.equals(finalDate)) {
            if (isWorkingDay(currentDay, holidays)) {
                workDays += step;
            }
            currentDay = currentDay.plusDays(step);
        }

        return workDays;
    }

    public static boolean isWorkingDay(
            LocalDate localDate,
            List<LocalDate> holidays
    ) {
        return localDate.getDayOfWeek() != DayOfWeek.SATURDAY &&
                localDate.getDayOfWeek() != DayOfWeek.SUNDAY &&
                !holidays.contains(localDate);
    }

}
