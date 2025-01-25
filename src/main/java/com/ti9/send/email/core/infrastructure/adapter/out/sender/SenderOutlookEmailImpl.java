package com.ti9.send.email.core.infrastructure.adapter.out.sender;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.*;
import com.microsoft.graph.requests.GraphServiceClient;
import com.ti9.send.email.core.domain.dto.account.AccountSettings;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class SenderOutlookEmailImpl implements Sender {

    String clientId = "SEU_CLIENT_ID";
    String clientSecret = "SEU_CLIENT_SECRET";
    String tenantId = "SEU_TENANT_ID";
    @Value("${from}")
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
        itemBody.contentType = BodyType.TEXT;
        message.body = itemBody;

        message.from = new Recipient();
        message.from.emailAddress = new com.microsoft.graph.models.EmailAddress();
        message.from.emailAddress.address = from;

        graphClient.me().sendMail(UserSendMailParameterSet.newBuilder()
                        .withMessage(message)
                        .withSaveToSentItems(false)
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
                Collections.singletonList("https://graph.microsoft.com/.default"), clientSecretCredential);

        return GraphServiceClient.builder()
                .authenticationProvider(authProvider)
                .buildClient();
    }

}
