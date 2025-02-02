package com.ti9.send.email.core.domain.model.inbox;


import com.ti9.send.email.core.domain.model.BaseAudit;
import com.ti9.send.email.core.domain.model.account.Account;
import com.ti9.send.email.core.domain.model.enums.BodyFormatEnum;
import com.ti9.send.email.core.domain.model.enums.PriorityEnum;
import com.ti9.send.email.core.domain.model.inbox.enums.InboxStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ecob_inbox")
public class Inbox extends BaseAudit {
    @Id
    @Column
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "reply_to")
    private String replyTo;

    @Column(name = "recipients_to")
    private String recipientsTO;

    @Column(name = "recipients_cc")
    private String recipientsCC;

    @Column(name = "recipients_bcc")
    private String recipientsBCC;

    @Column
    @Enumerated(EnumType.STRING)
    private PriorityEnum priority;

    @Column
    private String subject;

    @Column(name = "body_format")
    @Enumerated(EnumType.STRING)
    private BodyFormatEnum bodyFormat;

    @Column(name = "body_text")
    private String bodyText;

    @Column
    private LocalDate scheduled;

    @Column(name = "processed_at")
    private LocalDate processedAt;

    @Column
    @Enumerated(EnumType.STRING)
    private InboxStatusEnum status;

    @Column(name = "error_message")
    private String errorMessage;

    @OneToMany(mappedBy = "inbox", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<InboxLink> inboxLinkList;

}
