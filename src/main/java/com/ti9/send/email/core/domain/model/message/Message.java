package com.ti9.send.email.core.domain.model.message;

import com.ti9.send.email.core.domain.dto.message.MessageRequest;
import com.ti9.send.email.core.domain.model.UpdatableBaseAudit;
import com.ti9.send.email.core.domain.model.enums.StatusEnum;
import com.ti9.send.email.core.domain.model.message.enums.BaseDateEnum;
import com.ti9.send.email.core.domain.model.message.enums.DateRuleEnum;
import com.ti9.send.email.core.domain.model.message.template.MessageTemplate;
import com.ti9.send.email.core.domain.model.enums.PaymentStatusEnum;
import com.ti9.send.email.core.infrastructure.adapter.converter.PaymentStatusEnumArrayConverter;
import com.ti9.send.email.core.infrastructure.adapter.converter.StringArrayConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ecob_msg")
public class Message extends UpdatableBaseAudit {

    @Id
    @Column
    private UUID id;

    @Column
    private String name;

    @Column(name = "date_rule")
    private DateRuleEnum dateRule;

    @Column(name = "date_index")
    private BaseDateEnum dateIndex;

    @Column(name = "selected_times")
    @Convert(converter = StringArrayConverter.class)
    private List<String> selectedTime;

    @Column(name = "selected_days")
    private List<Short> selectedDay;

    @Column(name = "doc_types")
    @Convert(converter = StringArrayConverter.class)
    private List<String> docType;

    @Column(name = "doc_status")
    @Convert(converter = PaymentStatusEnumArrayConverter.class)
    private List<PaymentStatusEnum> docStatus;

    @Column
    private StatusEnum status;

    @Column(name = "include_attachments")
    private Boolean includeAttachment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private MessageTemplate messageTemplate;

    public Message(
            UUID id,
            String name,
            StatusEnum status
    ) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public void update(MessageRequest messageRequest) {
        this.name = messageRequest.name();
        this.dateRule = messageRequest.dateRule();
        this.dateIndex = messageRequest.dateBase();
        this.selectedTime = messageRequest.sendingTimeList();
        this.selectedDay = messageRequest.sendingDayList();
        this.docType = messageRequest.docTypeList();
        this.docStatus = messageRequest.paymentStatusList();
        this.status = messageRequest.status();
        this.includeAttachment = messageRequest.sendAttachments();
    }
}
