package com.udacity.jwdnd.course1.cloudstorage.Models;

import lombok.Data;

@Data
public class CredentialModel {

    private Integer credentialid;
    private String url;
    private String userName;
    private String key;
    private String password;
    private Integer userid;
    // Constuctor for all Variables
    public CredentialModel(Integer credentialid, String url, String userName, String key, String password, Integer userid) {
        this.credentialid = credentialid;
        this.url = url;
        this.userName = userName;
        this.key = key;
        this.password = password;
        this.userid = userid;
    }

// Constuctor for URL , Username , Password only
    public CredentialModel(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }
    // Generate CredentialModel Setter and Getter methods
    public Integer getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(Integer credentialid) {
        this.credentialid = credentialid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }



}
