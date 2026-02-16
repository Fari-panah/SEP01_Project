package controller;

import dao.TaskDao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.CurrentUser;
import model.Task;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationExtension;
import utils.LanguageManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, ApplicationExtension.class})
class AddTaskControllerTest {

    @Mock
    private TaskDao taskDao;

    @InjectMocks
    private AddTaskController addTaskController;

    private MockedStatic<LanguageManager> languageMock;

    private TextField titleField;
    private TextArea descField;
    private DatePicker dueDatePicker;
    private ChoiceBox<String> statusChoice;

    @BeforeEach
    void setUp() throws Exception {

        // ---- mock LanguageManager static calls
        languageMock = mockStatic(LanguageManager.class);
        languageMock.when(() -> LanguageManager.getTranslation(anyString()))
                .thenReturn("test");
        languageMock.when(LanguageManager::getCurrentLocale)
                .thenReturn(Locale.ENGLISH);

        // ---- logged in user
        User dummyUser = new User("testuser", "password");
        dummyUser.setUserID(1);
        CurrentUser.set(dummyUser);

        // ---- inject JavaFX controls
        titleField = new TextField();
        setPrivateField(addTaskController, "titleField", titleField);

        descField = new TextArea();
        setPrivateField(addTaskController, "descField", descField);

        dueDatePicker = new DatePicker();
        setPrivateField(addTaskController, "dueDatePicker", dueDatePicker);

        statusChoice = new ChoiceBox<>(
                FXCollections.observableArrayList("TODO", "IN_PROGRESS", "DONE"));
        setPrivateField(addTaskController, "statusChoice", statusChoice);

        // ---- VERY IMPORTANT: replace real DAO with mock
        setPrivateField(addTaskController, "taskDao", taskDao);
    }

    @AfterEach
    void tearDown() {
        languageMock.close();
        CurrentUser.set(null);
    }

    @Test
    void testHandleSaveTask_Success() throws Exception {

        titleField.setText("New Test Task");
        descField.setText("A description for the test task.");
        dueDatePicker.setValue(LocalDate.now().plusDays(5));
        statusChoice.setValue("TODO");

        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                invokePrivateMethod(addTaskController,
                        "handleSaveTask",
                        new ActionEvent());
            } catch (Exception e) {
                // ignore navigation / UI problems
            } finally {
                latch.countDown();
            }
        });

        latch.await();

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskDao, times(1)).persist(taskCaptor.capture());

        Task savedTask = taskCaptor.getValue();
        assertEquals("New Test Task", savedTask.getTitle());
    }

    private void setPrivateField(Object target,
                                 String fieldName,
                                 Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private void invokePrivateMethod(Object target,
                                     String methodName,
                                     Object... args) throws Exception {
        Method method = target.getClass()
                .getDeclaredMethod(methodName, ActionEvent.class);
        method.setAccessible(true);
        method.invoke(target, args);
    }
}