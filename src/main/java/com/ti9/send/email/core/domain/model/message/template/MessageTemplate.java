package com.ti9.send.email.core.domain.model.message.template;

import com.ti9.send.email.core.domain.model.UpdatableBaseAudit;
import com.ti9.send.email.core.domain.model.account.Account;
import com.ti9.send.email.core.domain.model.message.MessageRule;
import com.ti9.send.email.core.domain.model.message.template.enums.ActionEnum;
import com.ti9.send.email.core.domain.model.message.template.enums.RecipientTypeEnum;
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
@Entity(name = "ecob_msg_model")
public class MessageTemplate extends UpdatableBaseAudit {
    @Id
    @Column
    private UUID id;

    @Column
    private ActionEnum action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "recipiente_type")
    private RecipientTypeEnum recipientType;

    @Column(name = "reply_to")
    private String replyTo;

    @Column
    private String cc;

    @Column
    private String bcc;

    @Column
    private String subject;

    @Column
    private String Body;

    @OneToMany(mappedBy = "messageTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MessageRule> messageRuleList;

}
