package com.ti9.send.email.core.domain.model.message.template;

import com.ti9.send.email.core.domain.model.UpdatableBaseAudit;
import com.ti9.send.email.core.domain.model.account.Account;
import com.ti9.send.email.core.domain.model.message.MessageRule;
import com.ti9.send.email.core.domain.model.message.template.enums.ActionEnum;
import com.ti9.send.email.core.domain.model.message.template.enums.RecipientTypeEnum;
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
@Entity(name = "ecob_msg_model")
public class MessageTemplate extends UpdatableBaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;

    @Column
    @Enumerated(EnumType.STRING)
    private ActionEnum action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "recipient_type")
    @Enumerated(EnumType.STRING)
    private RecipientTypeEnum recipientType;

    @Column(name = "reply_to")
    private String replyTo;

    @Column
    @Convert(converter = StringArrayConverter.class)
    private List<String> cc;

    @Column
    @Convert(converter = StringArrayConverter.class)
    private List<String> bcc;

    @Column
    private String subject;

    @Column(name = "body_text")
    private String Body;

    @OneToMany(mappedBy = "messageTemplate", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<MessageRule> messageRuleList;

}
