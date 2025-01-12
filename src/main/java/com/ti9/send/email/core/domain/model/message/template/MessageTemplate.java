package com.ti9.send.email.core.domain.model.message.template;

import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateRequest;
import com.ti9.send.email.core.domain.model.UpdatableBaseAudit;
import com.ti9.send.email.core.domain.model.account.Account;
import com.ti9.send.email.core.domain.model.message.MessageRule;
import com.ti9.send.email.core.domain.model.message.template.enums.ActionEnum;
import com.ti9.send.email.core.domain.model.message.template.enums.RecipientTypeEnum;
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
@Table(name = "ecob_msg_model")
public class MessageTemplate extends UpdatableBaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;

    @Column
    @Enumerated(EnumType.STRING)
    private ActionEnum action;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "recipient_type")
    @Enumerated(EnumType.STRING)
    private RecipientTypeEnum recipientType;

    @Column(name = "reply_to")
    private String replyTo;

    @Column
    private List<String> cc;

    @Column
    private List<String> bcc;

    @Column
    private String subject;

    @Column(name = "body_text")
    private String body;

    @OneToMany(mappedBy = "messageTemplate", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MessageRule> messageRuleList;

    public void update(MessageTemplateRequest request) {
        this.action = request.action();
        this.account = new Account(request.senderId());
        this.recipientType = request.recipientType();
        this.replyTo = request.replyTo();
        this.cc = request.cc();
        this.bcc = request.cco();
        this.subject = request.subject();
        this.body = request.body();
    }
}
