package com.ti9.send.email.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatableBaseAudit extends BaseAudit {
    private LocalDate updated;

}