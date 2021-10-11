package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	private String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void test_loginAndSignUp() throws InterruptedException {
		String username = "aaa";
		String password = "123";

		driver.get(baseURL + "/signup");

		Signup signup = new Signup(driver);
		signup.signup("Lily", "Rose", username, password);

		driver.get(baseURL + "/login");

		Login login = new Login(driver);
		login.login(username, password);
	}

	@Test
	public void test_note() throws InterruptedException {
		this.test_loginAndSignUp();
		Note note = new Note(driver);
		Result result = new Result(driver);

		String noteTitle="Note test";
		String noteDescription = "This is a note test!";

		note.addNote(noteTitle, noteDescription, driver);

		String changeNoteTitle = "Update note";
		String changeNoteDescription = "This is an updated note!";

		result.clickSuccessResult();
		note.editNote(changeNoteTitle, changeNoteDescription, driver);

		result.clickSuccessResult();
		note.deleteNote(driver);
	}

	@Test
	public void test_credential() throws InterruptedException {
		this.test_loginAndSignUp();
		Credentials credentialsPage = new Credentials(driver);
		Result result = new Result(driver);

		String url = "google.com";
		String username = "aaa";
		String password = "123";

		credentialsPage.addCredential(url, username, password, driver);

		String changeUrl = "youtube.com";
		String changeUsername = "abc";
		String changePassword = "456";
		result.clickSuccessResult();
		credentialsPage.editCredential(changeUrl, changeUsername, changePassword, driver);

		result.clickSuccessResult();
		credentialsPage.deleteCredential(driver);
	}

	@Test
	public void test_logoutAndRedirect() throws InterruptedException {
		this.test_loginAndSignUp();
		Logout logout = new Logout(driver);
		logout.logout(driver);
		driver.get(baseURL + "/home");
		Thread.sleep(5000); // Redirect to login page.
	}

}
