package ru.kpfu.itis.snetkov.view;

import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import ru.kpfu.itis.snetkov.model.ComandList;
import ru.kpfu.itis.snetkov.service.CommandsService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;


public class ChatView extends BaseView {

    private AnchorPane root;
    private TextArea conversation;
    private TextArea input;

    @Override
    public Parent getView() {
        if (root == null) {
            createView();
        }
        return root;
    }

    public void append(String message) {
        if (message != null) {
            conversation.appendText(message + System.lineSeparator());
        }
    }

    private void createView() {
        root = new AnchorPane();

        conversation = new TextArea();
        conversation.setEditable(false);
        conversation.setWrapText(true);

        AnchorPane.setLeftAnchor(conversation, 10.0);
        AnchorPane.setRightAnchor(conversation, 10.0);
        AnchorPane.setTopAnchor(conversation, 10.0);

        input = new TextArea();
        input.setMaxHeight(50);

        AnchorPane.setLeftAnchor(input, 10.0);
        AnchorPane.setRightAnchor(input, 10.0);
        AnchorPane.setBottomAnchor(input, 10.0);

        input.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String message = input.getText();
                conversation.appendText("you" + ": " + message + System.lineSeparator());
                try {
                    makeCommand(message);
                } catch (IllegalArgumentException e) {
                    conversation.appendText("Wrong command, write one of the existing comands, write list in every case to know what commands existing\n");
                }


                input.clear();
                event.consume();
            }
        });
        root.getChildren().addAll(conversation, input);
    }

    public void makeCommand(String message) {
        ComandList comand = ComandList.fromString(message.split(" ")[0]);
        System.out.println(comand);
        String ans = "...";
        try {
             ans = switch (comand) {
                case LIST -> CommandsService.list();
                case WEATHER -> CommandsService.weather(message.split(" ")[1]);
                case EXCHANGE -> CommandsService.exchange(message.split(" ")[1]);
                case QUIT -> quit();
            };
        } catch (IOException e) {
            conversation.appendText("Wrong parameter name or api is broken");
        }

        if (!ans.equals("quit")) {
            conversation.appendText(ans);
        }
    }

    public String quit() {
        getChatApplication().setView(getChatApplication().getMainView());
        return "quit";
    }
}