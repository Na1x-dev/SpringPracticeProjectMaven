package com.example.springPracticeProject.controllers.message;

public class MessageEndpoints {

    static final String messageEndpoint = "/messages";
    static final String messageIdEndpoint = "/messages/byId/{id}";
    static final String messageMailIdEndpoint = "/messages/byMailId/{id}";
    static final String messagePageEnd = "/messagePage/index/id={id}";
    static final String draftMessagesPage = "/draftMessages/index";
    static final String favoriteMessagesPage = "/favoriteMessages/index";
    static final String receivedMessagesPage = "/receivedMessages/index";
    static final String sendMessagesPage = "/sendMessages/index";
    static final String logInEndpoint = "/signUpPage/index";
    static final String defaultEndpoint = "/";
    static final String newMessagePageEnd = "newMessagePage/index";
}
