package com.udacity.jwdnd.course1.cloudstorage.Forms;


import lombok.Data;

@Data
public class CredentialForm {
    private String credentialId;
    private String userName;
    private String url;
    private String password;
// Generate CredentialForm constuctor
    public CredentialForm(String credentialId, String userName, String url, String password) {
        this.credentialId = credentialId;
        this.userName = userName;
        this.url = url;
        this.password = password;
    }
    // Generate CredentialForm Setter and Getter methods
    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
