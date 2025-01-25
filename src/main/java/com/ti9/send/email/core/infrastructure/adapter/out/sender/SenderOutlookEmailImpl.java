package com.ti9.send.email.core.infrastructure.adapter.out.sender;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.models.*;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;

import com.ti9.send.email.core.domain.dto.message.information.OAuthEmailMessageInformationDTO;

import java.util.Collections;

public class SenderOutlookEmailImpl implements Sender<OAuthEmailMessageInformationDTO> {

    String clientId = "SEU_CLIENT_ID";
    String clientSecret = "SEU_CLIENT_SECRET";
    String tenantId = "SEU_TENANT_ID";

    @Override
    public void send(OAuthEmailMessageInformationDTO oAuthEmailMessageInformationDTO) {
        GraphServiceClient<?> graphClient = getGraphClient();
        Message message = new Message();

        message.toRecipients =
                oAuthEmailMessageInformationDTO.getToList().stream().map(email -> {
                    Recipient recipient = new Recipient();
                    EmailAddress emailAddress = new EmailAddress();
                    emailAddress.address = email;
                    recipient.emailAddress = emailAddress;
                    return recipient;
                }).toList();

        message.ccRecipients =
                oAuthEmailMessageInformationDTO.getCarbonCopy().stream().map(email -> {
                    Recipient recipient = new Recipient();
                    EmailAddress emailAddress = new EmailAddress();
                    emailAddress.address = email;
                    recipient.emailAddress = emailAddress;
                    return recipient;
                }).toList();

        message.bccRecipients =
                oAuthEmailMessageInformationDTO.getBlindCarbonCopy().stream().map(email -> {
                    Recipient recipient = new Recipient();
                    EmailAddress emailAddress = new EmailAddress();
                    emailAddress.address = email;
                    recipient.emailAddress = emailAddress;
                    return recipient;
                }).toList();


        message.subject = oAuthEmailMessageInformationDTO.getSubject();

        ItemBody itemBody = new ItemBody();
        itemBody.content = oAuthEmailMessageInformationDTO.getBody();
        itemBody.contentType = BodyType.TEXT;
        message.body = itemBody;

        message.from = new Recipient();
        message.from.emailAddress = new com.microsoft.graph.models.EmailAddress();
        message.from.emailAddress.address = oAuthEmailMessageInformationDTO.getFrom();

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
