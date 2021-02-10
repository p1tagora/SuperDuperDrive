package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseURL;
	private CredentialService credentialService;
	private EncryptionService encryptionService;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		this.baseURL = "http://localhost:" + this.port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testGetLoginPage() {
		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testGetSignupPage() {
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void testUnauthorizedUserAccess() {
		//unauthorized user should be able to access the signup page
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		//unauthorized user should be able to access the login page
		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		//unauthorized user should not be able to access the home page, therefore he should remain on login
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testUserSignupLoginAndLogout() {
		String username = "testuser";
		String password = "testpassword";

		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Urs", "Meyer", username, password);

		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		HomePage homePage = new HomePage(driver);
		//check that we can now access /home
		Assertions.assertEquals("Home", driver.getTitle());

		homePage.logout();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(driver -> { System.out.println(driver.getCurrentUrl()); return driver.findElement(By.id("submit-login"));});

		//check that we can no longer access /home, after logout
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testNotes() {
		String username = "testuser";
		String password = "testpassword";
		String noteTitle;
		String noteDescription;
		Note testNote;

		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Urs", "Meyer", username, password);

		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		HomePage homePage = new HomePage(driver);

		//Test adding a note
		noteTitle = "Test Add Note Title";
		noteDescription = "Test Add Note Description";
		homePage.addNote(noteTitle, noteDescription);
		testNote = homePage.readNote();
		Assertions.assertEquals(noteTitle, testNote.getNotetitle());
		Assertions.assertEquals(noteDescription, testNote.getNotedescription());

		//Test editing a note
		noteTitle = "Test Edit Note Title";
		noteDescription = "Test Edit Note Description";
		homePage.editNote(noteTitle, noteDescription);
		testNote = homePage.readNote();
		Assertions.assertEquals(noteTitle, testNote.getNotetitle());
		Assertions.assertEquals(noteDescription, testNote.getNotedescription());

		//Test deleting a note
		Assertions.assertEquals(homePage.noteExists(), true);
		homePage.deleteNote();
		Assertions.assertEquals(homePage.noteExists(), false);

		homePage.logout();
	}

	@Test
	public void testCredentials() {
		String username = "testuser";
		String password = "testpassword";
		String credentialUrl;
		String credentialUsername;
		String credentialPassword;
		Credential testCredential;

		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Urs", "Meyer", username, password);

		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		HomePage homePage = new HomePage(driver);

		//Test adding a credential
		credentialUrl = "http://localhost:8080/";
		credentialUsername = "testUser";
		credentialPassword = "testPassword";
		homePage.addCredential(credentialUrl, credentialUsername, credentialPassword);

		//check that the password is not the same as initial one (it was encrypted)
		testCredential = homePage.readCredential();
		Assertions.assertEquals(credentialUrl, testCredential.getUrl());
		Assertions.assertEquals(credentialUsername, testCredential.getUsername());
		Assertions.assertNotEquals(credentialPassword, testCredential.getPassword());

		//check that the password is the same as the initial one (decrypted)
		testCredential = homePage.readCredentialFromEditMode();
		Assertions.assertEquals(credentialUrl, testCredential.getUrl());
		Assertions.assertEquals(credentialUsername, testCredential.getUsername());
		Assertions.assertEquals(credentialPassword, testCredential.getPassword());

		//Test editing a credential
		credentialUrl = "http://localhost:8080/edited/";
		credentialUsername = "testEditedUser";
		credentialPassword = "testEditedPassword";
		homePage.editCredential(credentialUrl, credentialUsername, credentialPassword);
		testCredential = homePage.readCredentialFromEditMode();
		Assertions.assertEquals(credentialUrl, testCredential.getUrl());
		Assertions.assertEquals(credentialUsername, testCredential.getUsername());
		Assertions.assertEquals(credentialPassword, testCredential.getPassword());

		//Test deleting a credential
		Assertions.assertEquals(homePage.credentialExists(), true);
		homePage.deleteCredential();
		Assertions.assertEquals(homePage.credentialExists(), false);

		homePage.logout();
	}
}
