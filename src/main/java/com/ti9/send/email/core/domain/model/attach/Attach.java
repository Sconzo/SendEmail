package com.ti9.send.email.core.domain.model.attach;

import com.ti9.send.email.core.domain.model.UpdatableBaseAudit;
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
@Table(name = "attach")
public class Attach extends UpdatableBaseAudit {
    @Id
    @Column
    private UUID id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "mimetype")
    private String mimetype;

    @Column(name = "foldername")
    private String folderName;

    @Column
    private Boolean compressed;

    @Column
    private Boolean thumbnailable;

    @Column(name = "filesize")
    private Integer fileSize;

    @Column
    private UUID author;

    @Column(name = "att_byte")
    private Boolean attByte;

    @OneToMany(mappedBy = "attach", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttachLink> attachLinkList;

}
