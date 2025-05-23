package domain;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import util.Utility;


public class BTree implements  Tree {


    public BTreeNode getRoot() {
        return root;
    }

    private BTreeNode root; //se refiere a la raiz del arbol

    @Override
    public int size() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return size(root);
    }

    private int size(BTreeNode node){
        if(node==null) return 0;
        else return 1 + size(node.left) + size(node.right);
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean isEmpty() {
        return root==null;
    }

    @Override
    public boolean contains(Object element) throws TreeException {
        if (isEmpty())
            throw new TreeException("Binary Tree is empty");
        return binarySearch(root, element);
    }

    private boolean binarySearch(BTreeNode node, Object element){
        if (node==null) return false;
        else if (util.Utility.compare(node.data, element) == 0) return true;
        else return binarySearch(node.left, element) || binarySearch(node.right, element);
    }

    @Override
    public void add(Object element) {
        //this.root = add(root, element);
        this.root = add(root, element, "root");
    }

    private BTreeNode add(BTreeNode node, Object element){
        if(node==null)
            node = new BTreeNode(element);
        else{
            int value = util.Utility.random(100);
            if(value%2==0)
                node.left = add(node.left, element);
            else node.right = add(node.right, element);
        }
        return node;
    }

    private BTreeNode add(BTreeNode node, Object element, String path){
        if(node==null)
            node = new BTreeNode(element, path);
        else{
            int value = util.Utility.random(100);
            if(value%2==0)
                node.left = add(node.left, element, path+"/left");
            else node.right = add(node.right, element, path+"/right");
        }
        return node;
    }

    @Override
    public void remove(Object element) throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        //Contribución de Jefferson Varela para que no borre repetidos. Excelente!!!
        //para que el boolean funcione bien en el llamado recursivo, debe ir en un array
        root = remove(root,element, new boolean[]{false});
        //root = remove(root, element);
    }

    private BTreeNode remove(BTreeNode node, Object element, boolean[] deleted) throws TreeException{
        if(node!=null){
            if(util.Utility.compare(node.data, element)==0){
                deleted[0] = true; // cambia a true porque lo va a eliminar

                //caso 1. es un nodo si hijos, es una hoja
                if(node.left==null && node.right==null) return null;
                    //caso 2-a. el nodo solo tien un hijo, el hijo izq
                else if (node.left!=null&&node.right==null) {
                    node.left = newPath(node.left, node.path);
                    return node.left;
                } //caso 2-b. el nodo solo tien un hijo, el hijo der
                else if (node.left==null&&node.right!=null) {
                    node.right = newPath(node.right, node.path);
                    return node.right;
                }
                //caso 3. el nodo tiene dos hijos
                else{
                    //else if (node.left!=null&&node.right!=null) {
                    /* *
                     * El algoritmo de supresión dice que cuando el nodo a suprimir
                     * tiene 2 hijos, entonces busque una hoja del subarbol derecho
                     * y sustituya la data del nodo a suprimir por la data de esa hoja,
                     * luego elimine esa hojo
                     * */
                    Object value = getLeaf(node.right);
                    node.data = value;
                    node.right = removeLeaf(node.right, value, new boolean[]{false});
                }
            }
            if(!deleted[0]) node.left = remove(node.left, element, deleted); //llamado recursivo por la izq
            if(!deleted[0]) node.right = remove(node.right, element, deleted); //llamado recursivo por la der
        }
        return node; //retorna el nodo modificado o no
    }

    /* *
     * Funciona cuando se invoca al metodo remove
     * Sirve para actualizar los labels del nodo removido y sus
     * descendientes (cuando aplica)
     * */
    private BTreeNode newPath(BTreeNode node,String label){
        if(node!=null){
            node.path = label;
            node.left = newPath(node.left,label+"/left");
            node.right = newPath(node.right,label+"/right");
        }
        return node;
    }

    private Object getLeaf(BTreeNode node){
        Object aux;
        if(node==null) return null;
        else if(node.left==null&&node.right==null) return node.data; //es una hoja
        else{
            aux = getLeaf(node.left); //siga bajando por el subarbol izq
            if(aux==null) aux = getLeaf(node.right);
        }
        return aux;
    }

    private BTreeNode removeLeaf(BTreeNode node, Object value, boolean[] deleted){
        if(node==null) return null;
            //si es una hoja y esa hoja es la que andamos buscando, la eliminamos
        else if(node.left==null&&node.right==null&&util.Utility.compare(node.data, value)==0) {
            deleted[0] = true; //el elemento fue eliminado
            return null; //es una hoja y la elimina
        }else{
            node.left = removeLeaf(node.left, value, deleted);
            if(!deleted[0]) node.right = removeLeaf(node.right, value, deleted);
        }
        return node; //retorna el subarbol derecho con la hoja eliminada
    }

    @Override
    public int height(Object element) throws TreeException {
        if (isEmpty())
            throw new TreeException("Binary Tree is Empty");
        return height(root, element, 0);
    }

    private int height(BTreeNode node, Object element, int level) {
        if (node == null) return 0;
        else if (Utility.compare(node.data, element)==0) return level;
        else return Math.max(height(node.left, element, ++level),
                    height(node.right, element, level));
    }

    @Override
    public int height() throws TreeException {
        if (isEmpty())
            throw new TreeException("Binary Tree is Empty");
        return height(root, 0);
    }

    private int height(BTreeNode node, int level) {
        if (node==null) return level-1;
        else return Math.max(height(node.left, ++level),
                height(node.right, level));
    }

    private int height(BTreeNode node) {
        if (node==null) return -1;
        else return Math.max(height(node.left),
                height(node.right)) + 1;
    }

    @Override
    public Object min() throws TreeException {
        return null;
    }

    @Override
    public Object max() throws TreeException {
        return null;
    }

    @Override
    public String preOrder() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return preOrder(root);
    }

    //recorre el árbol de la forma: nodo-hijo izq-hijo der
    private String preOrder(BTreeNode node){
        String result="";
        if(node!=null){
            //result = node.data+" ";
            result  = node.data+"("+node.path+")"+" ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return  result;
    }

    @Override
    public String inOrder() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return inOrder(root);
    }

    //recorre el árbol de la forma: hijo izq-nodo-hijo der
    private String inOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result  = inOrder(node.left);
            result += node.data+"("+node.path+")"+" ";
            result += inOrder(node.right);
        }
        return  result;
    }

    //para mostrar todos los elementos existentes
    @Override
    public String postOrder() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return postOrder(root);
    }

    //recorre el árbol de la forma: hijo izq-hijo der-nodo,
    private String postOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result  = postOrder(node.left);
            result += postOrder(node.right);
            result += node.data+"("+node.path+")"+" ";
        }
        return result;
    }

    @Override
    public String toString() {
        String result="Binary Tree Content:";
        try {
            result = "PreOrder: "+preOrder();
            result+= "\nInOrder: "+inOrder();
            result+= "\nPostOrder: "+postOrder();

        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public String printLeaves() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return printLeaves(root);
    }
    private String printLeaves(BTreeNode node){
        if (node == null) {
            return "";
        }
        //cuando es una hoja
        if (node.left == null && node.right == null) {
            return node.data + " ";
        }

        String leftLeaves = printLeaves(node.left);
        String rightLeaves = printLeaves(node.right);

        return leftLeaves + rightLeaves;
    }

    public String printNodes1Child() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return printNodes1Child(root);
    }
    private String printNodes1Child(BTreeNode node) {
        String result = "";
        if (node == null)
            return result;
        else {
            if (node.left != null && node.right == null) {
                result += node.data + " " + printNodes1Child(node.left);
            } else if (node.left == null && node.right != null) {
                result += node.data + " " + printNodes1Child(node.right);
            } else if (node.left != null && node.right != null) {
                result += printNodes1Child(node.left) + printNodes1Child(node.right);
            }
        }
        return result;
    }

    public String printNodes2Children() throws TreeException {
        if (isEmpty())
            throw new TreeException("Binary Tree is empty");
        return printNodes2Children(root).trim();
    }

    private String printNodes2Children(BTreeNode node) {
        if (node == null)
            return "";

        StringBuilder result = new StringBuilder();

        if (node.left != null && node.right != null) {
            result.append("Node: ").append(node.data).append(" (").append(node.path).append(")\n");
            result.append("Left Children: ").append(node.left.data).append(" (").append(node.left.path).append(")\n");
            result.append("Right children:  ").append(node.right.data).append(" (").append(node.right.path).append(")\n\n");
        }
        //Recursividad: explorar el hijo izquierdo y derecho a partir de la raiz, en busca de mas nodos con 2 hijos
        result.append(printNodes2Children(node.left));
        result.append(printNodes2Children(node.right));

        return result.toString();
    }


    public int totalLeaves() throws TreeException{
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return totalLeaves(root);
    }

    private int totalLeaves(BTreeNode node){
        int counter = 0;
        if(node==null)
            return 0;
        else{
            counter += totalLeaves(node.left);
            counter += totalLeaves(node.right);
            if(node.left==null && node.right==null)
                counter++;
        }
        return counter;
    }
    public void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, 800, 600);
        drawNode(gc, root, 400, 50, 200);
    }

    private void drawNode(GraphicsContext gc, BTreeNode node, double x, double y, double offset) {
        final double NODE_RADIUS = 20;
        final double LEVEL_GAP = 70;
        if (node == null) return;//Caso base
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
    public void drawLevels(GraphicsContext gc,double width) throws TreeException {
        drawLevels(gc,this,0,width);
    }
    private void drawLevels(GraphicsContext gc, BTree tree, int initialLevel, double canvasWidth) throws TreeException {
        int treeHeight = tree.height(); // altura del árbol}
        final double LEVEL_GAP=70;
        gc.setStroke(Color.RED);
        gc.setFill(Color.RED);
        gc.setLineWidth(1.5);
        gc.setFont(new Font("Arial", 12));

        for (int level = 0; level <= treeHeight; level++) {
            double y = 50 + level * LEVEL_GAP;
            gc.strokeLine(0, y, canvasWidth, y); // línea horizontal
            gc.fillText(""+(level+1), 5, y - 5); // etiqueta al inicio
        }
    }

    public String printNodesWithChildren() throws TreeException {
        if (isEmpty())
            throw new TreeException("Binary tree is empty");
        return printNodesWithChildren(root);
    }

    private String printNodesWithChildren(BTreeNode node) {
        if (node == null)
            return "";

        StringBuilder result = new StringBuilder();

        // Si el nodo tiene al menos un hijo
        if (node.left != null || node.right != null) {
            result.append("Node: ").append(node.data).append(" (").append(node.path).append(")\n");

            if (node.left != null)
                result.append("Left Child: ").append(node.left.data).append(" (").append(node.left.path).append(")\n");
            else
                result.append("Left child: null\n");

            if (node.right != null)
                result.append("Right Child: ").append(node.right.data)
                        .append(" (").append(node.right.path).append(")\n");
            else
                result.append("Right Child: null\n");

            result.append("\n");
        }

        result.append(printNodesWithChildren(node.left));
        result.append(printNodesWithChildren(node.right));

        return result.toString();
    }
}
