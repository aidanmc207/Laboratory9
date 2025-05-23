package controller;

import domain.BTree;
import domain.TreeException;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import util.Utility;

public class GraphicBtreeController
{
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private Canvas canvas;
    private BTree btree;
    @javafx.fxml.FXML
    private Button tourBtn;
    @javafx.fxml.FXML
    private Button levelsBtn;

    @javafx.fxml.FXML
    public void initialize() {
        btree = new BTree();
    }

    @javafx.fxml.FXML
    public void levelsOnAction(ActionEvent actionEvent) {
        try {
            btree.drawLevels(canvas.getGraphicsContext2D(),canvas.getWidth()-150);
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void randomizeOnAction(ActionEvent actionEvent) {
        canvas.getGraphicsContext2D().clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        btree.clear();
        for (int i=0;i<30;i++){
            btree.add(Utility.random(50));
        }
        levelsBtn.setDisable(false);
        tourBtn.setDisable(false);
        btree.draw(canvas.getGraphicsContext2D());
    }

    @javafx.fxml.FXML
    public void tourOnAction(ActionEvent actionEvent) {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tour");
        alert.setHeaderText(null);
        TextArea textArea=new TextArea();
        textArea.setEditable(false);
        try {
            textArea.setText("Binary Tree Tour info: \n Tree Height: "+btree.height()+"\n"+btree.toString());
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
        alert.getDialogPane().setContent(textArea);
        alert.setResizable(true);
        alert.showAndWait();
    }
}