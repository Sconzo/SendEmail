package com.ti9.send.email.core.infrastructure.adapter.out.sender;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.*;
import com.microsoft.graph.requests.AttachmentCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;
import com.ti9.send.email.core.domain.dto.account.AccountSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class SenderOutlookEmailImpl implements Sender {

    @Value("${outlook.client.id}")
    private String clientId;
    @Value("${outlook.client.secret}")
    private String clientSecret;
    @Value("${outlook.tenant.id}")
    private String tenantId;
    @Value("${outlook.from}")
    private String from;

    @Override
    public void send(
            java.util.List<String> recipientList,
            java.util.List<String> ccRecipentList,
            java.util.List<String> bccRecipentList,
            String subject,
            String body,
            List<File> fileList,
            AccountSettings accountSettings
    ) {
        GraphServiceClient<?> graphClient = getGraphClient();
        Message message = new Message();

        message.toRecipients =
                recipientList.stream().map(email -> {
                    Recipient recipient = new Recipient();
                    EmailAddress emailAddress = new EmailAddress();
                    emailAddress.address = email;
                    recipient.emailAddress = emailAddress;
                    return recipient;
                }).toList();

        message.ccRecipients =
                ccRecipentList.stream().map(email -> {
                    Recipient recipient = new Recipient();
                    EmailAddress emailAddress = new EmailAddress();
                    emailAddress.address = email;
                    recipient.emailAddress = emailAddress;
                    return recipient;
                }).toList();

        message.bccRecipients =
                bccRecipentList.stream().map(email -> {
                    Recipient recipient = new Recipient();
                    EmailAddress emailAddress = new EmailAddress();
                    emailAddress.address = email;
                    recipient.emailAddress = emailAddress;
                    return recipient;
                }).toList();


        message.subject = subject;

        ItemBody itemBody = new ItemBody();
        itemBody.content = body;
        itemBody.contentType = BodyType.HTML;
        message.body = itemBody;

        List<Attachment> attachments = new ArrayList<>();
        try {
            for (File file : fileList) {
                byte[] fileData = Files.readAllBytes(file.toPath());

                FileAttachment attachment = new FileAttachment();
                attachment.oDataType = "#microsoft.graph.fileAttachment";
                attachment.name = file.getName();
                attachment.contentType = Files.probeContentType(file.toPath());
                attachment.contentBytes = fileData;

                attachments.add(attachment);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        message.attachments = new AttachmentCollectionPage(attachments, null);

        graphClient.users(from).sendMail(UserSendMailParameterSet.newBuilder()
                        .withMessage(message)
                        .withSaveToSentItems(true)
                        .build())
                .buildRequest()
                .post();
    }

    private GraphServiceClient<?> getGraphClient() {
        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();

        TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(
                Collections.singletonList("https://graph.microsoft.com/.default"),
                clientSecretCredential
        );

        return GraphServiceClient.builder()
                .authenticationProvider(authProvider)
                .buildClient();
    }

}
