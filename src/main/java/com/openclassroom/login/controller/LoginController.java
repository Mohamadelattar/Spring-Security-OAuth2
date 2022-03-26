package com.openclassroom.login.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.Map;

@RestController
public class LoginController {

    private final OAuth2AuthorizedClientService authorizedClientService;

    public LoginController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }


    @RequestMapping("/admin")
    @RolesAllowed("ADMIN")
    public String admin() {
        return "Welcome , admin ! ";
    }

    @RequestMapping("/**")
    @RolesAllowed("USER")
    public String user() {
        return "Welcome , User ! ";
    }

    @RequestMapping("/*")
    public String getUserInfo(Principal user) {
        StringBuffer userInfo = new StringBuffer();
        if(user instanceof UsernamePasswordAuthenticationToken) {
            userInfo.append(getUsernamePasswordLoginInfo(user));
        } else if(user instanceof OAuth2AuthorizedClient) {
            userInfo.append(getOauth2LoginInfo(user));
        }
        System.out.println(userInfo.toString());
        return userInfo.toString();
    }

    private StringBuffer getUsernamePasswordLoginInfo(Principal user) {
        StringBuffer usernameInfo = new StringBuffer();
        UsernamePasswordAuthenticationToken token =
                (UsernamePasswordAuthenticationToken) user;
        if(token.isAuthenticated()) {
            User user1 = (User) token.getPrincipal();
            usernameInfo.append("Welecome, " + user1.getUsername());
        } else {
            usernameInfo.append("NA");
        }
        return usernameInfo;
    }

    private StringBuffer getOauth2LoginInfo(Principal user) {
        StringBuffer protectedInfo = new StringBuffer();
        OAuth2AuthenticationToken authToken =
                (OAuth2AuthenticationToken) user;
        OAuth2AuthorizedClient authClient =
                this.authorizedClientService.loadAuthorizedClient(
                        authToken.getAuthorizedClientRegistrationId(),
                        authToken.getName()
                );
        if(authToken.isAuthenticated()) {
            Map<String,Object> userAttributes = ((DefaultOAuth2User)
                    authToken.getPrincipal()).getAttributes();
            String userToken = authClient.getAccessToken().getTokenValue();
            protectedInfo.append("Welcome, " + userAttributes.get("name")+"<br><br>");
            protectedInfo.append("e-mail: " + userAttributes.get("email")+ "<br><br>");
            protectedInfo.append("Access Token: " + userToken + "<br><br>");
        } else {
            protectedInfo.append("NA");
        }
        return  protectedInfo;
    }
}
