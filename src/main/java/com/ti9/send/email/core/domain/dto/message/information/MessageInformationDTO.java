package com.ti9.send.email.core.domain.dto.message.information;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageInformationDTO {
    private String from;
    private List<String> toList;
    private String body;
    private List<byte[]> attachment;
}
