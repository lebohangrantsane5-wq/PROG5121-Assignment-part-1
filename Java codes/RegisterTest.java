import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterTest {

    private Register register;

    @BeforeEach
    void setUp() {
        register = new Register();
    }

    // --- Username Tests ---
    @Test
    void testValidUsername() {
        assertTrue(register.checkUserName("ab_cd")); // valid: underscore + <= 5 chars
    }

    @Test
    void testInvalidUsernameNoUnderscore() {
        assertFalse(register.checkUserName("abcde")); // no underscore
    }

    @Test
    void testInvalidUsernameTooLong() {
        assertFalse(register.checkUserName("abcd_e")); // too long (> 5 chars)
    }

    // --- Password Tests ---
    @Test
    void testValidPassword() {
        assertTrue(register.checkPasswordComplexity("Strong@1")); // valid password
    }

    @Test
    void testPasswordTooShort() {
        assertFalse(register.checkPasswordComplexity("Ab@1")); // too short
    }

    @Test
    void testPasswordMissingCapital() {
        assertFalse(register.checkPasswordComplexity("weak@123")); // no capital letter
    }

    @Test
    void testPasswordMissingNumber() {
        assertFalse(register.checkPasswordComplexity("Weak@word")); // no number
    }

    @Test
    void testPasswordMissingSpecialChar() {
        assertFalse(register.checkPasswordComplexity("Weak1234")); // no special char
    }

    // --- Cellphone Tests ---
    @Test
    void testValidCellPhoneNumber() {
        assertTrue(register.checkCellPhoneNumber("+27721234567")); // valid SA number
    }

    @Test
    void testInvalidCellPhoneNumberNoCode() {
        assertFalse(register.checkCellPhoneNumber("0721234567")); // missing +
    }

    @Test
    void testInvalidCellPhoneNumberTooShort() {
        assertFalse(register.checkCellPhoneNumber("+27123")); // too short
    }

    // --- Registration Tests ---
    @Test
    void testSuccessfulRegistration() {
        String result = register.registerUser("ab_cd", "Strong@1", "+27721234567", "John", "Doe");
        assertEquals("User successfully registered!", result);
    }

    @Test
    void testFailedRegistrationInvalidUsername() {
        String result = register.registerUser("abcdef", "Strong@1", "+27721234567", "John", "Doe");
        assertTrue(result.contains("Username is not correctly formatted"));
    }

    @Test
    void testFailedRegistrationInvalidPassword() {
        String result = register.registerUser("ab_cd", "weakpass", "+27721234567", "John", "Doe");
        assertTrue(result.contains("Password is not correctly formatted"));
    }

    @Test
    void testFailedRegistrationInvalidCell() {
        String result = register.registerUser("ab_cd", "Strong@1", "0721234567", "John", "Doe");
        assertTrue(result.contains("Cell phone number incorrectly formatted"));
    }

    // --- Login & Status Tests ---
    @Test
    void testLoginSuccess() {
        register.registerUser("ab_cd", "Strong@1", "+27721234567", "John", "Doe");
        assertTrue(register.loginUser("ab_cd", "Strong@1"));
    }

    @Test
    void testLoginFailureWrongPassword() {
        register.registerUser("ab_cd", "Strong@1", "+27721234567", "John", "Doe");
        assertFalse(register.loginUser("ab_cd", "WrongPass"));
    }

    @Test
    void testReturnLoginStatusSuccess() {
        register.registerUser("ab_cd", "Strong@1", "+27721234567", "John", "Doe");
        String status = register.returnLoginStatus(register.loginUser("ab_cd", "Strong@1"));
        assertTrue(status.contains("Welcome John Doe"));
    }

    @Test
    void testReturnLoginStatusFailure() {
        register.registerUser("ab_cd", "Strong@1", "+27721234567", "John", "Doe");
        String status = register.returnLoginStatus(register.loginUser("wrong", "WrongPass"));
        assertEquals("Username or password incorrect, please try again.", status);
    }
}
