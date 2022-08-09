package com.example.springPracticeProject.services.security;

public interface SecurityService {
    boolean isAuthenticated();
    Long autoLogin(String username, String password);
}