package core.domain.model.account;

import core.domain.model.enums.StatusEnum;
import core.domain.model.UpdatableBaseAudit;
import core.domain.model.account.enums.ProviderEnum;
import core.domain.model.inbox.Inbox;
import core.domain.model.message.template.MessageTemplate;
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
@Entity(name = "ecob_account")
public class Account extends UpdatableBaseAudit {
    @Id
    @Column
    private UUID id;

    @Column
    private String name;

    @Column
    private ProviderEnum provider;

    @Column
    private StatusEnum status;

    @Column
    private String settings;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inbox> inboxList;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MessageTemplate> messageTemplateList;

}
