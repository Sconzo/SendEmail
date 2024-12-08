package com.ti9.send.email.core.domain.model.message;

import com.ti9.send.email.core.domain.dto.message.rule.MessageRuleRequest;
import com.ti9.send.email.core.domain.model.UpdatableBaseAudit;
import com.ti9.send.email.core.domain.model.enums.StatusEnum;
import com.ti9.send.email.core.domain.model.message.enums.BaseDateEnum;
import com.ti9.send.email.core.domain.model.message.enums.DateRuleEnum;
import com.ti9.send.email.core.domain.model.message.template.MessageTemplate;
import com.ti9.send.email.core.domain.model.enums.PaymentStatusEnum;
import com.ti9.send.email.core.infrastructure.adapter.converter.PaymentStatusEnumArrayConverter;
import com.ti9.send.email.core.infrastructure.adapter.converter.StringArrayConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ecob_msg")
public class MessageRule extends UpdatableBaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;

    @Column
    private String name;

    @Column(name = "date_rule")
    @Enumerated(EnumType.STRING)
    private DateRuleEnum dateRule;

    @Column(name = "date_index")
    @Enumerated(EnumType.STRING)
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
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "include_attachments")
    private Boolean includeAttachment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private MessageTemplate messageTemplate;

    public MessageRule(
            UUID id,
            String name,
            StatusEnum status
    ) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public void update(MessageRuleRequest messageRuleRequest) {
        this.name = messageRuleRequest.name();
        this.dateRule = messageRuleRequest.dateRule();
        this.dateIndex = messageRuleRequest.dateBase();
        this.selectedTime = messageRuleRequest.sendingTimeList();
        this.selectedDay = messageRuleRequest.sendingDayList();
        this.docType = messageRuleRequest.docTypeList();
        this.docStatus = messageRuleRequest.paymentStatusList();
        this.status = messageRuleRequest.status();
        this.includeAttachment = messageRuleRequest.sendAttachments();
    }
}
