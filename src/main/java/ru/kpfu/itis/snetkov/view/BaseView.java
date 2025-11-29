package ru.kpfu.itis.snetkov.view;

import javafx.scene.Parent;
import ru.kpfu.itis.snetkov.ChatApplication;

public abstract class BaseView {

    private static ChatApplication chatApplication;

    public static ChatApplication getChatApplication() {
        if (chatApplication == null) {
            throw new IllegalStateException("ru.kpfu.itis.snetkov.http.ChatApplication has not been initialized");
        }
        return chatApplication;
    }

    public static void setChatApplication(ChatApplication chatApplication) {
        BaseView.chatApplication = chatApplication;
    }

    public abstract Parent getView();
}
