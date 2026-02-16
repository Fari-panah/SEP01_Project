package controller;

import dao.TaskDao;
import model.Task;
import model.User;
import model.CurrentUser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.testfx.framework.junit5.ApplicationExtension;
import utils.LanguageManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith({MockitoExtension.class, ApplicationExtension.class})
class AddTaskControllerTest {

    @Mock
    private TaskDao taskDao;


    @Mock
    private LanguageManager languageManager;

    @InjectMocks
    private AddTaskController addTaskController;

    private TextField titleField;
    private TextArea descField;
    private DatePicker dueDatePicker;
    private ChoiceBox<String> statusChoice;

    @BeforeAll
    static void setupSpec() {
        try {
            Field instanceField = LanguageManager.class.getDeclaredField("instance");
            instanceField.setAccessible(true);
            instanceField.set(null, mock(LanguageManager.class));
        } catch (Exception e) { // This is an empty method.
        }
    }


    @BeforeEach
    void setUp() throws Exception {
        mockStatic(LanguageManager.class);
        when(LanguageManager.getTranslation(anyString())).thenReturn("test string");


        User dummyUser = new User("testuser", "password");
        dummyUser.setUserID(1);
        CurrentUser.set(dummyUser);

        titleField = new TextField();
        setPrivateField(addTaskController, "titleField", titleField);

        descField = new TextArea();
        setPrivateField(addTaskController, "descField", descField);

        dueDatePicker = new DatePicker();
        setPrivateField(addTaskController, "dueDatePicker", dueDatePicker);

        statusChoice = new ChoiceBox<>(FXCollections.observableArrayList("TODO", "IN_PROGRESS", "DONE"));
        setPrivateField(addTaskController, "statusChoice", statusChoice);
    }

    @Test
    void testHandleSaveTask_Success() throws InterruptedException {
        titleField.setText("New Test Task");
        descField.setText("A description for the test task.");
        dueDatePicker.setValue(LocalDate.now().plusDays(5));
        statusChoice.setValue("TODO");


        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                invokePrivateMethod(addTaskController, "handleSaveTask", new ActionEvent());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        });

        latch.await();

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskDao).persist(taskCaptor.capture());

        Task savedTask = taskCaptor.getValue();
        assertEquals("New Test Task", savedTask.getTitle());
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private void invokePrivateMethod(Object target, String methodName, Object... args) throws Exception {
        Method method = target.getClass().getDeclaredMethod(methodName, ActionEvent.class);
        method.setAccessible(true);
        method.invoke(target, args);
    }
}