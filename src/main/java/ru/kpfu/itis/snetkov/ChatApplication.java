package ru.kpfu.itis.snetkov;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.kpfu.itis.snetkov.view.BaseView;
import ru.kpfu.itis.snetkov.view.ChatView;
import ru.kpfu.itis.snetkov.view.MainView;

public class ChatApplication extends Application {
    private BorderPane root;
    private MainView mainView;
    private ChatView chatView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Chat");
        stage.setOnCloseRequest(event -> System.exit(0));
        BaseView.setChatApplication(this);

        root = new BorderPane();

        mainView = new MainView();
        chatView = new ChatView();

        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();

        setView(mainView);
    }

    public ChatView getChatView() {
        return chatView;
    }

    public MainView getMainView() {
        return mainView;
    }

    public void setView(BaseView view) {
        root.setCenter(view.getView());
    }
}
