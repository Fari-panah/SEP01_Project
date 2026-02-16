package controller;

import dao.UserDao;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.CurrentUser;
import model.User;
import utils.LanguageManager;

/**
 * This is for login functionality.
 */
public class LoginController extends BaseController {
    @FXML private Label headerLogin;
    @FXML private Label lblUsername;
    @FXML private TextField usernameField;
    @FXML private Label lblPassword;
    @FXML private PasswordField passwordField;
    @FXML private Button btnLogin;
    @FXML private Button btnSignup;
    @FXML private MenuButton btnLanguage;
    @FXML private MenuItem itemPersian;
    @FXML private MenuItem itemChinese;
    @FXML private MenuItem itemEnglish;
    @FXML private AnchorPane rootAnchorPane;


    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private UserDao userDao = new UserDao();

    /**
     * This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        updateLanguage();
        languageTexts();
        applyTextDirection();
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(getTranslation("error.title"), getTranslation("error.loginMessage"));
            return;
        }

        User user = userDao.login(username, password);

        if (user != null && user.getPassword().equals(password)) {
            CurrentUser.set(user);
            showAlert(getTranslation("success.title"),
                    getTranslation("success.loginMessage") + user.getUsername());
            Parent firstViewRoot = FXMLLoader.load(getClass().getResource("/first_view.fxml"));
            Scene firstViewScene = new Scene(firstViewRoot);


            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(firstViewScene);
            window.show();
        } else {
            showAlert(LanguageManager.getTranslation("error.title"),
                    LanguageManager.getTranslation("invalid.message"));
        }
    }

    @FXML
    private void handleSignupRedirect(ActionEvent event) throws IOException {
        Parent signUpRoot = FXMLLoader.load(getClass().getResource("/SignUp.fxml"));
        Scene signUpScene = new Scene(signUpRoot);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(signUpScene);
        window.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Database localization texts.
     */
    @FXML
    private void languageTexts() {
        headerLogin.setText(LanguageManager.getTranslation("headerLogin"));
        lblUsername.setText(LanguageManager.getTranslation("lblUsername"));
        lblPassword.setText(LanguageManager.getTranslation("lblPassword"));
        btnLogin.setText(LanguageManager.getTranslation("btnLogin"));
        btnSignup.setText(LanguageManager.getTranslation("btnSignup"));
        btnLanguage.setText(LanguageManager.getTranslation("btnLanguage"));
        itemPersian.setText(LanguageManager.getTranslation("itemPersian"));
        itemChinese.setText(LanguageManager.getTranslation("itemChinese"));
        itemEnglish.setText(LanguageManager.getTranslation("itemEnglish"));
    }

    private void applyTextDirection() {
        String lang = LanguageManager.getCurrentLocale().getLanguage();
        boolean isRTL = lang.equals("fa");

        if (rootAnchorPane != null) {
            rootAnchorPane.setNodeOrientation(
                    isRTL ? NodeOrientation.RIGHT_TO_LEFT : NodeOrientation.LEFT_TO_RIGHT
            );
        }

        // Apply to text fields
        if (usernameField != null) {
            rootAnchorPane.setNodeOrientation(
                    isRTL ?  NodeOrientation.RIGHT_TO_LEFT : NodeOrientation.LEFT_TO_RIGHT
            );
        }
        if (passwordField != null) {
            rootAnchorPane.setNodeOrientation(
                    isRTL ? NodeOrientation.RIGHT_TO_LEFT : NodeOrientation.LEFT_TO_RIGHT
            );
        }
    }

    /**
     * Set language to English.
     */
    @FXML
    public void onEnglishClick(ActionEvent event)  {
        LanguageManager.setLanguage("en", "US");
        updateLanguage();
        languageTexts();
        applyTextDirection();
    }

    /**
     * Set language to Persian.
     */
    @FXML
    public void onPersianClick(ActionEvent event) {
        LanguageManager.setLanguage("fa", "IR");
        updateLanguage();
        languageTexts();
        applyTextDirection();
    }

    /**
     * Set language to Chinese.
     */
    @FXML
    public void onChineseClick(ActionEvent event)  {
        LanguageManager.setLanguage("zh", "CN");
        updateLanguage();
        languageTexts();
        applyTextDirection();
    }
}