package com.ti9.send.email.core.domain.dto;

import com.ti9.send.email.core.domain.dto.document.DocumentDTO;
import com.ti9.send.email.core.domain.model.message.MessageRule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnstructuredMessage {
    private MessageRule messageRule;
    private DocumentDTO document;
    private String body;
    private List<File> attachmentList;
}
