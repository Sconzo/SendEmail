package core.domain.model.message;

import core.domain.model.UpdatableBaseAudit;
import core.domain.model.enums.StatusEnum;
import core.domain.model.message.enums.BaseDateEnum;
import core.domain.model.message.enums.DateRuleEnum;
import core.domain.model.enums.PaymentStatusEnum;
import core.domain.model.message.template.MessageTemplate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String selectedTime;

    @Column(name = "selected_days")
    private Integer selectedDay;

    @Column(name = "doc_types")
    private String docType;

    @Column(name = "doc_status")
    private PaymentStatusEnum docStatus;

    @Column
    private StatusEnum status;

    @Column(name = "include_attachments")
    private Boolean includeAttachment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private MessageTemplate messageTemplate;

}
