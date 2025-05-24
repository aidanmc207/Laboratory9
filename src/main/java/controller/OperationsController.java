package controller;

import domain.BTree;
import domain.TreeException;
import javafx.event.ActionEvent;
import domain.BTreeDrawer;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;

import javax.swing.*;

public class OperationsController {
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
    public void nodeHeightOnAction(ActionEvent actionEvent) {
        int value = treeInput("Ingrese un valor para buscar su tamaño", "Tamaño del nodo");
        try {
            int totalHeight = bTree.height(value);
            mostrarAlerta("La altura total del arbol es: " + totalHeight + "");
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void removeOnAction(ActionEvent actionEvent) {
        int removeValue = treeInput("Ingrese un valor para eliminar", "Valor a eliminar");
        try {
            boolean exists = bTree.contains(removeValue);

            if (exists) {
                bTree.remove(removeValue);
                bTreeDrawer.draw(treeCanvas.getGraphicsContext2D(),bTree.getRoot());
                mostrarAlerta("El valor ["+ removeValue +"] ha sido eliminado");
            } else
                mostrarAlerta("El valor ["+ removeValue +"] no se encuentra en el arbol");

        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        int newValue = treeInput("Ingrese un valor para añadir al árbol", "Nuevo valor");

            try {
                if (bTree.contains(newValue)) {
                    mostrarAlerta("El valor [" + newValue + "] ya existe en el árbol");
                } else {
                    bTree.add(newValue);
                    bTreeDrawer.draw(treeCanvas.getGraphicsContext2D(), bTree.getRoot());
                    mostrarAlerta("El valor [" + newValue + "] ha sido añadido correctamente");
                }
            } catch (TreeException e) {
                throw new RuntimeException(e);
            }

    }

    @javafx.fxml.FXML
    public void randomizeOnAction(ActionEvent actionEvent) {
        int numNodes = 5 + util.Utility.random(20);

        bTree.clear();

        //crear arbol con valores random
        for (int i = 0; i < numNodes; i++) {
            Integer randomValue = util.Utility.random(100) + 1;
            bTree.add(randomValue);
        }

        bTreeDrawer.draw(treeCanvas.getGraphicsContext2D(), bTree.getRoot());
    }

    @javafx.fxml.FXML
    public void containsOnAction(ActionEvent actionEvent) {
        int value = treeInput("Ingrese un valor para buscar", "Valor a buscar");

        try {
            boolean found = bTree.contains(value);

            if (found) {
                mostrarAlerta("El valor ["+ value +"] fue encontrado");
            } else {
                mostrarAlerta("El valor ["+ value +"] no fue encontrado");
            }

        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void treeHeightOnAction(ActionEvent actionEvent) {
        try {
            int totalHeight = bTree.height();
            mostrarAlerta("La altura total del arbol es: " + totalHeight + "");
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    public int treeInput(String message, String title) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE);

            if (input == null) {
                return 0;
            }

            if (input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Por favor, ingrese un número.",
                        "Campo vacío",
                        JOptionPane.WARNING_MESSAGE);
                continue;
            }

            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "'" + input + "' no es un número válido.\nPor favor, ingrese solo números enteros.",
                        "Error de formato",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Error de validación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}