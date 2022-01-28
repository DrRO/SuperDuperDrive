package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.Models.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.Models.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.Elements.SignupAndLogin;

import com.udacity.jwdnd.course1.cloudstorage.TabServices.CredentialServices;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public
class CloudStorageApplicationTests {



	@Autowired
	private CredentialServices credentialServices;

	@Autowired
	private EncryptionService encryptionService;


	protected WebDriver driver;

	@LocalServerPort
	protected int port;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}



	@AfterEach
	public void afterEach() {


		if (this.driver != null) {
			driver.quit();


		}
	}



    // Test  pages accessibility:
    // Test LoginPage Access
    @Test
    public void LoginPageِAccess() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    // Test SignUpPage Access
    @Test
    public void SignupPageِAccess() {
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }
/////////////////////////////

// The main method to Signup and Login which used in all Test methods
	protected SignupAndLogin signUpAndLogin() {
		driver.get("http://localhost:" + this.port + "/signup");

// set the signup page's text fields with placeholder data
		driver.findElement(By.id("inputFirstName")).sendKeys("Rasha");
		driver.findElement(By.id("inputLastName")).sendKeys("Omran");
		driver.findElement(By.id("inputUsername")).sendKeys("Rasha");
		driver.findElement(By.id("inputPassword")).sendKeys("123");

		driver.findElement(By.id("submitbtn")).click();
		driver.get("http://localhost:" + this.port + "/login");

// set the login page's text fields with registered data
		driver.findElement(By.id("inputUsername")).sendKeys("Rasha");
		driver.findElement(By.id("inputPassword")).sendKeys("123");
		driver.findElement(By.id("submitbtn")).click();

        Assertions.assertEquals("Home", driver.getTitle());
		return new SignupAndLogin(driver);
	}




	//Test Note CRUD methods

// Test adding note
	@Test
	public void testNote() {


		SignupAndLogin signupAndLogin = signUpAndLogin();
        // test opening Note tab
        signupAndLogin.navNotesTab();

        // test add Note button
        signupAndLogin.addNote();


        //test setting of Note Title and Description
        signupAndLogin.setNoteTitle("Note Title");
        signupAndLogin.setNoteDescription("note description test");

        // test Note saveing button
        signupAndLogin.saveNote();

        // test result page
        SuccessClass success = new SuccessClass(driver);

        Assertions.assertEquals("Result", driver.getTitle());
        success.goTo();


        Assertions.assertEquals("Home", driver.getTitle());
        signupAndLogin.navNotesTab();

        // test if this Note is in the note list at Home page
        NoteModel note = signupAndLogin.getNotedemo();
        Assertions.assertEquals("Note Title", note.getNoteTitle());
        Assertions.assertEquals("note description test", note.getNoteDescription());

	}
// Test Update Note
	@Test
	public void updateNoteTest() throws InterruptedException {
     // test adding Note
		SignupAndLogin signupAndLogin = signUpAndLogin();
		// test opening Note tab
		signupAndLogin.navNotesTab();

		// test add Note button
		signupAndLogin.addNote();


		//test setting of Note Title and Description
		signupAndLogin.setNoteTitle("Note Title");
		signupAndLogin.setNoteDescription("note description test");

		// test Note saveing button
		signupAndLogin.saveNote();

		// test result page
		SuccessClass success = new SuccessClass(driver);

		Assertions.assertEquals("Result", driver.getTitle());

		success.goTo();
		signupAndLogin.navNotesTab();

		// test if this Note is in the note list at Home page
		NoteModel note = signupAndLogin.getNotedemo();
		Assertions.assertEquals("Note Title", note.getNoteTitle());
		Assertions.assertEquals("note description test", note.getNoteDescription());



		// test edit note button
		signupAndLogin.editNote();

      // test updating Note details with other placeholder texts
		signupAndLogin.updateNoteTitle("Update Title");
		signupAndLogin.updateNoteDescription("Update Description");

		// test save note button
		signupAndLogin.saveNote();

        // redirecting to Result page
        SuccessClass successClass = new SuccessClass(driver);
        Assertions.assertEquals("Result", driver.getTitle());

        successClass.goTo();
		Thread.sleep(2000);

        // test if the updated Note is in the note list at Home page
		signupAndLogin.navNotesTab();

		// View updated note
		NoteModel updatwdnote = signupAndLogin.getNotedemo();
		Assertions.assertEquals("Update Title", updatwdnote.getNoteTitle());
		Assertions.assertEquals("Update Description", updatwdnote.getNoteDescription());
	}

// Test Delete note
	@Test
	public void testDeleteNote() {
     // test adding Note
		testNote();

        // test The possibility of deleting of this Note
		SignupAndLogin signupAndLogin = new SignupAndLogin(driver);
		signupAndLogin.navNotesTab();
		Assertions.assertFalse(noteNotExist(driver));

		// test Delete note button
		signupAndLogin.deleteNote();

        // redirecting to Result page
        SuccessClass successClass = new SuccessClass(driver);
        Assertions.assertEquals("Result", driver.getTitle());

        successClass.goTo();


        // check if note is already deleted
		Assertions.assertTrue(noteNotExist(driver));
	}



	// Check if Note is exist or not
    public boolean noteNotExist(WebDriver driver) {
        try {
            driver.findElement(By.id("tableNoteTitle"));

            return false;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return true;
        }    }






// Test Creditial CRUD


	// Basic methods to add new Credential after signup and login and passing url , username and password parameters

	private void createCredTest(String url, String username, String password, SignupAndLogin signupAndLogin) {

        // go to Credential tab
        driver.findElement(By.id("nav-credentials-tab")).click();

        //  crendtial button test
        signupAndLogin.createCredential();

        //  adding Crendtial data
        signupAndLogin.setCredentialUrl(url);
        signupAndLogin.setCredentialUsername(username);
        signupAndLogin.setCredentialPassword(password);

        // test save credential button

        signupAndLogin.savecred();

        // redirecting to Result page
        ErrorClass errorClass = new ErrorClass(driver);
        errorClass.goToHome();
// Sure from redirecting Home page
        Assertions.assertEquals("Home", driver.getTitle());
     // clicl credential tab
        driver.findElement(By.id("nav-credentials-tab")).click();


		CredentialModel credentialModel = signupAndLogin.getCredential();


// Assertions of credential data
		Assertions.assertEquals(url, credentialModel.getUrl());
		Assertions.assertEquals(username, credentialModel.getUserName());
		/*Assertions.assertNotEquals(password, credentialModel.getPassword());*/


//to get id to recover the Credential that was created by the method getCredential
		CredentialModel credential = credentialServices.getCredential(1);
		Assertions.assertEquals(this.encryptionService.encryptValue("123", credential.getKey()), password);

	}







	// Tests for  add new Credential
@Test
public void newCredentialTest() {
	SignupAndLogin signupAndLogin = signUpAndLogin();

	// add an argument to createCredTest method
	createCredTest("https://www.google.com/", "Rasha", "123", signupAndLogin);

	ErrorClass errorClass = new ErrorClass(driver);
	errorClass.goToHome();

    // Sure from redirecting Home page
    Assertions.assertEquals("Home", driver.getTitle());
	signupAndLogin.logout();


}



// Update credentials test, test that the  password is unencrypted
	@Test
	public void updateCrential() {
		SignupAndLogin signupAndLogin = signUpAndLogin();

		// add a placeholder argument to createNewCredential method
        createCredTest("https://www.google.com/", "Rasha", "123", signupAndLogin);
		CredentialModel credentialModel = signupAndLogin.getCredential();

		// Test add cerd btn
		signupAndLogin.addCredential();

		// add a Updated argument url
		signupAndLogin.setCredentialUrl("https://www.wikipedia.com/");
        // add a Updated argument user
		signupAndLogin.setCredentialUsername("wuser");

        // add a Updated argument password
		signupAndLogin.setCredentialPassword("wpass");

		// Test Save button
		driver.findElement(By.id("crendentialSave")).click();
		SuccessClass successClass = new SuccessClass(driver);
		successClass.goTo();


        // Sure from redirecting Home page
        Assertions.assertEquals("Home", driver.getTitle());
		driver.findElement(By.id("nav-credentials-tab")).click();

		// Viewing Updating Credential
		CredentialModel credentialUpdated = signupAndLogin.getCredential();
		Assertions.assertEquals("https://www.wikipedia.com/", credentialUpdated.getUrl());
		Assertions.assertEquals("wuser", credentialUpdated.getUserName());

		Assertions.assertNotEquals("wpass", credentialUpdated.getPassword());
		Assertions.assertNotEquals(credentialModel.getPassword(), credentialUpdated.getPassword());



		//Logout
		driver.findElement(By.id("logout")).click();
	}


	@Test
	public void testDeleteCred() {
		SignupAndLogin signupAndLogin = signUpAndLogin();
        createCredTest("https://www.google.com/", "Rasha", "123", signupAndLogin);

// test The deleting of this Cred
		Assertions.assertTrue(credentialsNotExist(driver));
		signupAndLogin.deleteCredential();
		SuccessClass successClass = new SuccessClass(driver);
		successClass.goTo();

        // Sure from redirecting Home page
        Assertions.assertEquals("Home", driver.getTitle());

		driver.findElement(By.id("nav-credentials-tab")).click();
		Assertions.assertFalse(credentialsNotExist(driver));

	}








	// Check if credendial is exist
	public boolean credentialsNotExist(WebDriver driver) {
		try {
			driver.findElement(By.id("tblCredentialUsername"));

			return false;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return true;
		}    }





    // Success method
	public class SuccessClass {

		private JavascriptExecutor js;

		public SuccessClass(WebDriver driver) {
			PageFactory.initElements(driver, this);
			js = (JavascriptExecutor) driver;
		}

		@FindBy(id = "aResultSuccess")
		private WebElement aResultSuccess;

		public void goTo() {
			driver.findElement(By.id("aResultSuccess")).click();


		}
	}



	// Error method
	public class ErrorClass {


		private  JavascriptExecutor js;

		public ErrorClass(WebDriver driver) {
			PageFactory.initElements(driver, this);
			js = (JavascriptExecutor) driver;
		}

		@FindBy(id = "aResultError")
		private WebElement aResultError;

		public void goToHome() {
			driver.findElement(By.id("aResultError")).click();


		}
	}


}
