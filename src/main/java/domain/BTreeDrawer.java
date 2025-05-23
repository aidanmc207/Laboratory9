package domain;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Random;

public class BTreeDrawer{
private static final double NODE_RADIUS = 20;
private static final double LEVEL_GAP = 70;
private static final double HORIZONTAL_GAP = 40;

private Random random = new Random();

public void draw(GraphicsContext gc, BTreeNode root) {
    gc.clearRect(0, 0, 800, 600);
    drawNode(gc,root, 400, 50, 200);
}

private void drawNode(GraphicsContext gc, BTreeNode node, double x, double y, double offset) {
    if (node == null) return;
    //    Color fillColor = Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
    Color color=Color.LIGHTBLUE;
    gc.setFill(color);
    gc.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
    gc.setStroke(Color.BLACK);
    gc.strokeOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
    gc.setFill(Color.BLACK);
    gc.setFont(new Font(12));
    gc.fillText(node.data.toString(), x - NODE_RADIUS / 2, y + 5);

    //Recursividad
    if (node.left != null) {//Lado izquierdo
        double childX = x - offset;
        double childY = y + LEVEL_GAP;
        gc.strokeLine(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
        drawNode(gc, node.left, childX, childY, offset / 2);
    }
    if (node.right != null) {//Lado derecho
        double childX = x + offset;
        double childY = y + LEVEL_GAP;
        gc.strokeLine(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
        drawNode(gc, node.right, childX, childY, offset / 2);
    }
}
    public void drawLevels(GraphicsContext gc, BTree tree, int initialLevel, double canvasWidth) throws TreeException {
        int treeHeight = tree.height(); // Calcula la altura
        gc.setStroke(Color.RED);
        gc.setLineWidth(1.5);

        for (int level = 0; level <= treeHeight; level++) {
            double y = 50 + level * LEVEL_GAP;
            gc.strokeLine(0, y, canvasWidth, y);
        }
    }
}