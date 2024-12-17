package com.ti9.send.email.core.domain.model.attach;

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
@Entity
@Table(name = "account_link")
public class AttachLink {
    @Id
    @Column
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "attach_id")
    private Attach attach;

    @Column(name = "ref_id")
    private UUID refId;
}
