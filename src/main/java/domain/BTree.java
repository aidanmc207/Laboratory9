package domain;

import util.Utility;

public class BTree implements  Tree {

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
        if (isEmpty())
            throw new TreeException("Binary Tree is Empty");
        root = remove(root, element);
    }

    private BTreeNode remove(BTreeNode node, Object element){
        if (node != null){
            if (Utility.compare(node.data, element)==0){
                //caso 1
                if (node.left == null && node.right == null) return null;
                //caso 2.a: solo tiene hijo izquierdo
                else if (node.left != null && node.right == null) {
                    //node.left = newPath(node.left, node.path);
                    return node.left;
                }
                //caso 2.b: solo tiene hijo derecho (a este punto el hijo derecho existe con certeza)
                else if (node.left == null) {
                    //node.right = newPath(node.right, node.path);
                    return node.right;
                }
                //caso 3
                else if (node.left != null && node.right != null) {
                    /**
                     * El algoritmo de supresión dice que cuando el nodo a suprimir tiene 2 hijos
                     * entonces busque una hoja del subarbol derecho y sustituya la data del nodo
                     * a suprimir por la data de esa hoja. Luego elimina esa hoja
                     */

                    Object value = getLeaf(node.right);
                    node.data = value;
                    node.right = removeLeaf(node.right, element);
                }
            }
            node.left = remove(node.left, element);
            node.right = remove(node.right, element);
        }
        return node; //retorna nodo modificado o no<
    }

    private BTreeNode removeLeaf(BTreeNode node, Object element) {
        if (node == null) return null;
        else if (node.left == null && node.right == null && Utility.compare(node.data, element) == 0) {
            return null;
        }
        else {
            node.left = removeLeaf(node.left, element);
            node.right = removeLeaf(node.right, element);
        }
        return node;
    }

    private Object getLeaf(BTreeNode node) {
        Object aux;
        if (node == null) return null;
        else if (node.left == null && node.right == null) {
            return node.data;
        } else {
            aux = getLeaf(node.left); //sigue bajando por el subarbol izquierdo
            if (aux == null) aux = getLeaf(node.right);
        }
        return aux;
    }

    /*private BTreeNode newPath(BTreeNode node, String path) {

    }*/

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
            result += node.data+" ";
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
            result += node.data+" ";
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
        if(node==null) return "";
        else{

        }
        return ""; //corregir para el retorno correcto
    }

    public String printNodes1Child() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return printNodes1Child(root);
    }
    private String printNodes1Child(BTreeNode node) {
        if (node == null)
            return "";
        else {

        }
        return "";
    }

    public String printNodes2Children() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return printNodes2Children(root);
    }
    private String printNodes2Children(BTreeNode node) {
        if (node == null)
            return "";
        else {

        }
        return ""; //corregir para el retorno correcto
    }
}
