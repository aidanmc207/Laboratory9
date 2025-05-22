package domain;

import org.junit.jupiter.api.Test;

import javax.management.MBeanTrustPermission;

import static org.junit.jupiter.api.Assertions.*;
class BTreeTest {

    @Test
    void test() {
        BTree bTree = new BTree();
        for (int i = 0; i < 20; i++) {
            bTree.add(util.Utility.random(50));
        }
        System.out.println(bTree);
        try {
            System.out.println("BTree size: "+bTree.size());
            for (int i = 0; i < 20; i++){
                int value = util.Utility.random(50);
                System.out.println(
                        bTree.contains(value)?
                                "The value ["+value+"] exists in the binary tree":
                                "The value ["+value+"] does not exist in the binary tree"
                );
                if (bTree.contains(value)){
                    bTree.remove(value);
                    System.out.println("The value [" +value+"] has been removed");;
                }
            }
            System.out.println(bTree + "\n" + bTree.size());
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testHeight(){
        BTree bTree = new BTree();
        bTree.add(20); bTree.add(30); bTree.add(18); bTree.add(4); bTree.add(5); bTree.add(50);
        System.out.println(bTree);
        try {
            System.out.println("Binary Tree - height (20): " + bTree.height(20));
            System.out.println("Binary Tree - height (30): " + bTree.height(30));
            System.out.println("Binary Tree - height (18): " + bTree.height(18));
            System.out.println("Binary Tree - height (4): " + bTree.height(4));
            System.out.println("Binary Tree - height (5): " + bTree.height(5));
            System.out.println("Binary Tree - height (50): " + bTree.height(50));
            System.out.println("\nBinary Tree - height(): " + bTree.height());
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }
}