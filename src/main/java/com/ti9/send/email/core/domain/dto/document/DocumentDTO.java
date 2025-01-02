package com.ti9.send.email.core.domain.dto.document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record DocumentDTO(
        UUID creId,
        String docType,
        String name,
        String billingContact,
        String document,
        LocalDate issueDate,
        LocalDate dueDate,
        BigDecimal documentAmount,
        BigDecimal outstandingAmount,
        Short calendarDayDifferenceIssueDate,
        Short calendarDayDifferenceDueDate,
        String billingEmail
){
}
