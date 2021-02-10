package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    @FindBy(id="logout-button")
    private WebElement logoutButton;

    @FindBy(id="nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "button-add-note")
    private WebElement buttonAddNote;

    @FindBy(id = "button-edit-note")
    private WebElement buttonEditNote;

    @FindBy(id = "button-delete-note")
    private WebElement buttonDeleteNote;

    @FindBy(id = "button-note-save-changes")
    private WebElement buttonNoteSaveChanges;

    @FindBy(id = "table-note-title")
    private WebElement tableNoteTitle;

    @FindBy(id = "table-note-description")
    private WebElement tableNoteDescription;

    @FindBy(id = "note-title")
    private WebElement noteTitleInputField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInputField;

    @FindBy(id="nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "button-add-credential")
    private WebElement buttonAddCredential;

    @FindBy(id = "button-edit-credential")
    private WebElement buttonEditCredential;

    @FindBy(id = "button-delete-credential")
    private WebElement buttonDeleteCredential;

    @FindBy(id = "button-credential-save-changes")
    private WebElement buttonCredentialSaveChanges;

    @FindBy(id = "table-credential-url")
    private WebElement tableCredentialUrl;

    @FindBy(id = "table-credential-username")
    private WebElement tableCredentialUsername;

    @FindBy(id = "table-credential-password")
    private WebElement tableCredentialPassword;

    @FindBy(id = "hidden-input-credential-id")
    private WebElement credentialId;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlInputField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameInputField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordInputField;

    private final WebDriver driver;
    private final JavascriptExecutor js;
    private final WebDriverWait wait;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.driver = webDriver;
        this.js = (JavascriptExecutor) webDriver;
        this.wait = new WebDriverWait(webDriver, 10);
    }

    public void addNote(String noteTitle, String noteDescription) {
        js.executeScript("arguments[0].click()", navNotesTab);
        wait.withTimeout(Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(buttonAddNote)).click();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitleInputField)).sendKeys(noteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(noteDescriptionInputField)).sendKeys(noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(buttonNoteSaveChanges)).click();
    }

    public void editNote(String noteTitle, String noteDescription) {
        js.executeScript("arguments[0].click()", navNotesTab);
        wait.withTimeout(Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(buttonEditNote)).click();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitleInputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitleInputField)).sendKeys(noteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(noteDescriptionInputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(noteDescriptionInputField)).sendKeys(noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(buttonNoteSaveChanges)).click();
    }

    public void deleteNote() {
        js.executeScript("arguments[0].click()", navNotesTab);
        wait.withTimeout(Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(buttonDeleteNote)).click();
    }

    public Note readNote() {
        js.executeScript("arguments[0].click()", navNotesTab);
        wait.withTimeout(Duration.ofSeconds(10));
        String title = wait.until(ExpectedConditions.elementToBeClickable(tableNoteTitle)).getText();
        String description = wait.until(ExpectedConditions.elementToBeClickable(tableNoteDescription)).getText();
        return new Note(null, title, description, null);
    }

    public boolean noteExists() {
        js.executeScript("arguments[0].click()", navNotesTab);
        wait.withTimeout(Duration.ofSeconds(10));
        try {
            return tableNoteTitle.isEnabled() && tableNoteDescription.isEnabled();
        } catch (org.openqa.selenium.NoSuchElementException e){
            return false;
        }
    }

    public void addCredential(String url, String username, String password) {
        js.executeScript("arguments[0].click()", navCredentialsTab);
        wait.withTimeout(Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(buttonAddCredential)).click();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUrlInputField)).sendKeys(url);
        wait.until(ExpectedConditions.elementToBeClickable(credentialUsernameInputField)).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(credentialPasswordInputField)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(buttonCredentialSaveChanges)).click();
    }

    public void editCredential(String url, String username, String password) {
        js.executeScript("arguments[0].click()", navCredentialsTab);
        wait.withTimeout(Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(buttonEditCredential)).click();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUrlInputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUrlInputField)).sendKeys(url);
        wait.until(ExpectedConditions.elementToBeClickable(credentialUsernameInputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUsernameInputField)).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(credentialPasswordInputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(credentialPasswordInputField)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(buttonCredentialSaveChanges)).click();
    }

    public void deleteCredential() {
        js.executeScript("arguments[0].click()", navCredentialsTab);
        wait.withTimeout(Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(buttonDeleteCredential)).click();
    }

    public Credential readCredential() {
        js.executeScript("arguments[0].click()", navCredentialsTab);
        wait.withTimeout(Duration.ofSeconds(10));
        String url = wait.until(ExpectedConditions.elementToBeClickable(tableCredentialUrl)).getText();
        String username = wait.until(ExpectedConditions.elementToBeClickable(tableCredentialUsername)).getText();
        String password = wait.until(ExpectedConditions.elementToBeClickable(tableCredentialPassword)).getText();
        return new Credential(null, url, username, null, password, null);
    }

    public Credential readCredentialFromEditMode() {
        js.executeScript("arguments[0].click()", navCredentialsTab);
        wait.withTimeout(Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(buttonEditCredential)).click();
        String url = wait.until(ExpectedConditions.elementToBeClickable(credentialUrlInputField)).getAttribute("value");
        String username = wait.until(ExpectedConditions.elementToBeClickable(credentialUsernameInputField)).getAttribute("value");
        String password = wait.until(ExpectedConditions.elementToBeClickable(credentialPasswordInputField)).getAttribute("value");
        wait.until(ExpectedConditions.elementToBeClickable(buttonCredentialSaveChanges)).click();
        return new Credential(null, url, username, null, password, null);
    }

    public boolean credentialExists() {
        js.executeScript("arguments[0].click()", navCredentialsTab);
        wait.withTimeout(Duration.ofSeconds(10));
        try {
            return tableCredentialUrl.isEnabled() && this.tableCredentialUsername.isEnabled() && this.tableCredentialPassword.isEnabled();
        } catch (org.openqa.selenium.NoSuchElementException e){
            return false;
        }
    }

    public void logout() {
        this.logoutButton.submit();
    }
}
