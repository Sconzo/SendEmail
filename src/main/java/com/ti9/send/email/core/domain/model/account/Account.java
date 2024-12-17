package com.ti9.send.email.core.domain.model.account;

import com.ti9.send.email.core.domain.model.UpdatableBaseAudit;
import com.ti9.send.email.core.domain.model.enums.StatusEnum;
import com.ti9.send.email.core.domain.model.account.enums.ProviderEnum;
import com.ti9.send.email.core.domain.model.inbox.Inbox;
import com.ti9.send.email.core.domain.model.message.template.MessageTemplate;
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
@Table(name = "ecob_account")
public class Account extends UpdatableBaseAudit {
    @Id
    @Column
    private UUID id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private ProviderEnum provider;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column
    private String settings;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Inbox> inboxList;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MessageTemplate> messageTemplateList;

    public Account(
            UUID id,
            String name,
            ProviderEnum provider,
            StatusEnum status
    ) {
        this.name = name;
        this.id = id;
        this.provider = provider;
        this.status = status;
    }

    public Account(
            UUID id
    ) {
        this.id = id;
    }
}
