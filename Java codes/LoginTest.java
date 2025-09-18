import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testSuccessfulLogin() {
        Login login = new Login("user123", "pass123");
        assertTrue(login.authenticate("user123", "pass123"), "Login should succeed with correct credentials");
    }

    @Test
    public void testFailedLoginWrongPassword() {
        Login login = new Login("user123", "pass123");
        assertFalse(login.authenticate("user123", "wrongpass"), "Login should fail with wrong password");
    }

    @Test
    public void testThreeFailedAttemptsLockout() {
        Login login = new Login("user123", "pass123");

        // Simulate 3 wrong attempts
        login.authenticate("user123", "wrong1");
        login.authenticate("user123", "wrong2");
        login.authenticate("user123", "wrong3");

        // Now even if password is correct, login should fail
        assertFalse(login.authenticate("user123", "pass123"), 
            "Login should be locked out after 3 failed attempts");
    }
}
