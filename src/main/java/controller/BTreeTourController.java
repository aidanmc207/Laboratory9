package controller;

import domain.BTree;
import domain.BTreeDrawer;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;

public class BTreeTourController {
    private BTreeDrawer bTreeDrawer;
    private BTree bTree;

    @javafx.fxml.FXML
    private Canvas treeCanvas;

    @javafx.fxml.FXML
    public void initialize() {
        bTreeDrawer = new BTreeDrawer();
        bTree = new BTree();
    }

    @javafx.fxml.FXML
    public void postOrderOnAction(ActionEvent actionEvent) {
        bTreeDrawer.drawPostOrder(treeCanvas.getGraphicsContext2D(), bTree.getRoot());
    }

    @javafx.fxml.FXML
    public void inOrderOnAction(ActionEvent actionEvent) {
        bTreeDrawer.drawInOrder(treeCanvas.getGraphicsContext2D(), bTree.getRoot());
    }

    @javafx.fxml.FXML
    public void preOrderOnAction(ActionEvent actionEvent) {
        bTreeDrawer.drawPreOrder(treeCanvas.getGraphicsContext2D(), bTree.getRoot());
    }

    @javafx.fxml.FXML
    public void randomizeOnAction(ActionEvent actionEvent) {
        int numNodes = 5 + util.Utility.random(20);
        bTree.clear();

        for (int i = 0; i < numNodes; i++) {
            Integer randomValue = util.Utility.random(100) + 1;
            bTree.add(randomValue);
        }
        bTreeDrawer.drawPreOrder(treeCanvas.getGraphicsContext2D(), bTree.getRoot());
    }
}