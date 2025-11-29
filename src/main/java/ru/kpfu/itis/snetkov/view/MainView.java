package ru.kpfu.itis.snetkov.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;

public class MainView extends BaseView{
    private AnchorPane root;
    private Button start;


    @Override
    public Parent getView() {
        if (root == null) {
            createView();
        }
        return root;
    }

    public void createView() {
        root = new AnchorPane();

        start = new Button("Start");

        start.setOnAction(event -> {
            if (start == event.getSource()) {
                getChatApplication().setView(getChatApplication().getChatView());
            }
        });

        root.getChildren().addAll(start);
    }
}