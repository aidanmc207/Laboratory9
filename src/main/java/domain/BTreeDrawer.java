package domain;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Random;

public class BTreeDrawer {
    private static final double NODE_RADIUS = 20;
    private static final double LEVEL_GAP = 70;
    private static final double HORIZONTAL_GAP = 40;
    private int visitCounter;

    private Random random = new Random();

    // Métodos originales sin modificar
    public void draw(GraphicsContext gc, BTreeNode root) {
        gc.clearRect(0, 0, 800, 600);
        drawNode(gc, root, 400, 50, 200);
    }

    private void drawNode(GraphicsContext gc, BTreeNode node, double x, double y, double offset) {
        if (node == null) return;
        Color color = Color.LIGHTBLUE;
        gc.setFill(color);
        gc.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(12));
        gc.fillText(node.data.toString(), x - NODE_RADIUS / 2, y + 5);

        if (node.left != null) {
            double childX = x - offset;
            double childY = y + LEVEL_GAP;
            gc.strokeLine(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
            drawNode(gc, node.left, childX, childY, offset / 2);
        }
        if (node.right != null) {
            double childX = x + offset;
            double childY = y + LEVEL_GAP;
            gc.strokeLine(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
            drawNode(gc, node.right, childX, childY, offset / 2);
        }
    }

    public void drawLevels(GraphicsContext gc, BTree tree, int initialLevel, double canvasWidth) throws TreeException {
        int treeHeight = tree.height();
        gc.setStroke(Color.RED);
        gc.setLineWidth(1.5);

        for (int level = 0; level <= treeHeight; level++) {
            double y = 50 + level * LEVEL_GAP;
            gc.strokeLine(0, y, canvasWidth, y);
        }
    }

    // Nuevos métodos para el recorrido
    public void drawPreOrder(GraphicsContext gc, BTreeNode root) {
        gc.clearRect(0, 0, 800, 600);
        visitCounter = 1;
        drawPreOrderNode(gc, root, 400, 50, 200);
    }

    public void drawInOrder(GraphicsContext gc, BTreeNode root) {
        gc.clearRect(0, 0, 800, 600);
        visitCounter = 1;
        drawInOrderNode(gc, root, 400, 50, 200);
    }

    public void drawPostOrder(GraphicsContext gc, BTreeNode root) {
        gc.clearRect(0, 0, 800, 600);
        visitCounter = 1;
        drawPostOrderNode(gc, root, 400, 50, 200);
    }

    private void drawPreOrderNode(GraphicsContext gc, BTreeNode node, double x, double y, double offset) {
        if (node == null) return;

        drawNodeWithNumber(gc, node, x, y, visitCounter++);
        drawConnections(gc, node, x, y, offset);

        if (node.left != null) {
            drawPreOrderNode(gc, node.left, x - offset, y + LEVEL_GAP, offset / 2);
        }
        if (node.right != null) {
            drawPreOrderNode(gc, node.right, x + offset, y + LEVEL_GAP, offset / 2);
        }
    }

    private void drawInOrderNode(GraphicsContext gc, BTreeNode node, double x, double y, double offset) {
        if (node == null) return;

        drawConnections(gc, node, x, y, offset);

        if (node.left != null) {
            drawInOrderNode(gc, node.left, x - offset, y + LEVEL_GAP, offset / 2);
        }

        drawNodeWithNumber(gc, node, x, y, visitCounter++);

        if (node.right != null) {
            drawInOrderNode(gc, node.right, x + offset, y + LEVEL_GAP, offset / 2);
        }
    }

    private void drawPostOrderNode(GraphicsContext gc, BTreeNode node, double x, double y, double offset) {
        if (node == null) return;

        drawConnections(gc, node, x, y, offset);

        if (node.left != null) {
            drawPostOrderNode(gc, node.left, x - offset, y + LEVEL_GAP, offset / 2);
        }
        if (node.right != null) {
            drawPostOrderNode(gc, node.right, x + offset, y + LEVEL_GAP, offset / 2);
        }

        drawNodeWithNumber(gc, node, x, y, visitCounter++);
    }

    private void drawNodeWithNumber(GraphicsContext gc, BTreeNode node, double x, double y, int order) {
        Color color = Color.LIGHTBLUE;
        gc.setFill(color);
        gc.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(12));
        gc.fillText(node.data.toString(), x - NODE_RADIUS / 2, y + 5);
        gc.fillText(String.valueOf(order), x + NODE_RADIUS + 5, y + 5);
    }

    private void drawConnections(GraphicsContext gc, BTreeNode node, double x, double y, double offset) {
        if (node.left != null) {
            double childX = x - offset;
            double childY = y + LEVEL_GAP;
            gc.strokeLine(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
        }
        if (node.right != null) {
            double childX = x + offset;
            double childY = y + LEVEL_GAP;
            gc.strokeLine(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
        }
    }
}