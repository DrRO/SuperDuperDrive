package com.udacity.jwdnd.course1.cloudstorage.Elements;

import com.udacity.jwdnd.course1.cloudstorage.Models.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.Models.NoteModel;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupAndLogin {


    private JavascriptExecutor js;



    public SignupAndLogin(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 500);
    }

    // Find Note Elements
   // Add id to each element in home.html


    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;
    public void navNotesTab() {

        js.executeScript("arguments[0].click();", navNotesTab);
    }

    @FindBy(id = "AddNotebtn")
    private WebElement AddNotebtn;
    public void addNote() {

        js.executeScript("arguments[0].click();", AddNotebtn);
    }


    @FindBy(id = "btnEditNote")
    private WebElement btnEditNote;
    public void editNote() {

        js.executeScript("arguments[0].click();", btnEditNote);
    }

    @FindBy(id = "note-title")
    private WebElement txtNoteTitle;
    public void setNoteTitle(String noteTitle) {
        js.executeScript("arguments[0].value='" + noteTitle + "';", txtNoteTitle);
    }

    @FindBy(id = "note-description")
    private WebElement txtNoteDescription;
    public void setNoteDescription(String noteDescription) {
        js.executeScript("arguments[0].value='"+ noteDescription +"';", txtNoteDescription);
    }

    private final WebDriverWait wait;
    public void updateNoteTitle(String updateTitle) {
        wait.until(ExpectedConditions.elementToBeClickable(txtNoteTitle)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(txtNoteTitle)).sendKeys(updateTitle);
    }


    @FindBy(id = "note-description")
    private WebElement txtModifyNoteDescription;
    public void updateNoteDescription(String updateDescription) {
        wait.until(ExpectedConditions.elementToBeClickable(txtModifyNoteDescription)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(txtModifyNoteDescription)).sendKeys(updateDescription);
    }



    @FindBy(id = "savenote")
    private WebElement savenote;
    public void saveNote() {
        js.executeScript("arguments[0].click();", savenote);
    }

    @FindBy(id = "ancDeleteNote")
    private WebElement ancDeleteNote;
    public void deleteNote() {
        js.executeScript("arguments[0].click();", ancDeleteNote);
    }


    @FindBy(id = "tableNoteTitle")
    private WebElement tableNoteTitle;

    @FindBy(id = "tableNoteDescription")
    private WebElement tableNoteDescription;

    public NoteModel getNotedemo() {
        return new NoteModel(
                wait.until(ExpectedConditions.elementToBeClickable(tableNoteTitle)).getText()
                ,tableNoteDescription.getText());
    }


    ////////////////////////////////////////////////

    // Find Credential Elements
    // Add id to each element in home.html

    @FindBy(id = "btnEditCredential")
    private WebElement btnEditCredential;
    public void addCredential() {
        js.executeScript("arguments[0].click();", btnEditCredential);
    }

    @FindBy(id = "addCredential")
    private WebElement addCredential;
    public void createCredential() {

        js.executeScript("arguments[0].click();", addCredential);
    }

    @FindBy(id = "credential-url")
    private WebElement txtCredentialUrl;
    public void setCredentialUrl(String url) {
        js.executeScript("arguments[0].value='" + url + "';", txtCredentialUrl);
    }

    @FindBy(id = "credential-username")
    private WebElement txtCredentialUsername;
    public void setCredentialUsername(String username) {
        js.executeScript("arguments[0].value='" + username + "';", txtCredentialUsername);
    }

    @FindBy(id = "credential-password")
    private WebElement txtCredentialPassword;
    public void setCredentialPassword(String password) {
        js.executeScript("arguments[0].value='" + password + "';", txtCredentialPassword);
    }

    @FindBy(id = "crendentialSave")
    private WebElement crendentialSave;
    public void savecred() {
        js.executeScript("arguments[0].click();", crendentialSave);
    }



    @FindBy(id = "aDeleteCredential")
    private WebElement aDeleteCredential;
    public void deleteCredential() {
        js.executeScript("arguments[0].click();", aDeleteCredential);
    }

    @FindBy(id = "tblCredentialUrl")
    private WebElement tblCredentialUrl;

    @FindBy(id = "tblCredentialUsername")
    private WebElement tblCredentialUsername;

    @FindBy(id = "tblCredentialPassword")
    private WebElement tblCredentialPassword;


    public CredentialModel getCredential() {
        return new CredentialModel(wait.until(ExpectedConditions.elementToBeClickable(tblCredentialUrl)).getText()
                , tblCredentialUsername.getText()
                , tblCredentialPassword.getText());
    }


    /////////////////////////////////////








// Test Logout btn

    @FindBy(id = "logout")
    private WebElement logoutButton;
    public void logout() {
        js.executeScript("arguments[0].click();", logoutButton);
    }
}
