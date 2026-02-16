package controller;

import dao.UserDao;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.User;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class SignUpControllerTest {

    private UserDao mockDao;
    private SignUpController controller;


    @Start
    public void start(Stage stage) throws Exception {
        mockDao = Mockito.mock(UserDao.class);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
        Parent root = loader.load();

        controller = loader.getController();

        Field daoField = SignUpController.class.getDeclaredField("userDao");
        daoField.setAccessible(true);
        daoField.set(controller, mockDao);

        stage.setScene(new Scene(root));
        stage.show();
        stage.toFront();
    }


    @Test
    void testCreateAccountButtonExists(FxRobot robot) {
        // correct fx:id in your FXML is btnCreateAccount
        Button btn = robot.lookup("#btnCreateAccount").queryAs(Button.class);
        assertNotNull(btn, "btnCreateAccount should be present in FXML");
    }

    @Test
    void testEmptyFieldsShowAlert(FxRobot robot) {
        // click the create account button with empty fields
        robot.clickOn("#btnCreateAccount");

        assertTrue(robot.lookup(".dialog-pane").tryQuery().isPresent());
        robot.clickOn(".button");
    }

    @Test
    void testUsernameAlreadyExistsShowsAlert(FxRobot robot) {
        // simulate existing user: login(...) returns a User
        User existing = new User("existingUser", "pass", "pass");
        doReturn(existing).when(mockDao).login("existingUser", "pass");

        robot.clickOn("#usernameField").write("existingUser");
        robot.clickOn("#passwordField").write("pass");
        robot.clickOn("#confirmPasswordField").write("pass");
        robot.clickOn("#btnCreateAccount");

        // expect alert
        assertTrue(robot.lookup(".dialog-pane").tryQuery().isPresent());
        robot.clickOn(".button");

        // register should NOT be called
        verify(mockDao, never()).register(any());
    }

    @Test
    void testSuccessfulSignUpCallsRegister(FxRobot robot) {
        // user does not exist
        doReturn(null).when(mockDao).login("newuser", "newpass");
        doNothing().when(mockDao).register(any());

        robot.clickOn("#usernameField").write("newuser");
        robot.clickOn("#passwordField").write("newpass");
        robot.clickOn("#confirmPasswordField").write("newpass");
        robot.clickOn("#btnCreateAccount");

        // success alert shown
        assertTrue(robot.lookup(".dialog-pane").tryQuery().isPresent());
        robot.clickOn(".button"); // close the alert

        // verify register called with a User that has expected values
        verify(mockDao, times(1)).register(argThat(u ->
                u != null
                        && "newuser".equals(u.getUsername())
                        && "newpass".equals(u.getPassword())
                        && "newpass".equals(u.getConfirmPassword())
        ));
    }
}
