/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.spellcheckpro;

import javax.swing.DefaultListModel;

/**
 *
 * @author ahmed
 */
public class AhmedMuazAtikObadaMasriBinaryTree<T> {

    //Defining global variables
    DefaultListModel def_model = new DefaultListModel();

    AhmedMuazAtikObadaMasriNode<T> root;

    //Inserting the key into the binary tree using recursiveInsert method
    void insert(String key) {
        root = recursiveInsert(root, key);
    }

    //Inserting the key into the binary tree recursively
    AhmedMuazAtikObadaMasriNode recursiveInsert(AhmedMuazAtikObadaMasriNode<T> newTemp, String key) {

        //If node is null
        if (newTemp == null) {
            newTemp = new AhmedMuazAtikObadaMasriNode(key); //Creating a new node with the key parameter
            return newTemp;
        }

        //If the key is smaller than node's word
        if (key.compareTo(newTemp.word) < 0) {
            newTemp.leftChild = recursiveInsert(newTemp.leftChild, key); //Moving to the left child
        } else if (key.compareTo(newTemp.word) > 0) {
            newTemp.rightChild = recursiveInsert(newTemp.rightChild, key); //Moving to the right child
        }

        return newTemp;
    }

    //LevenshteinDistance algorithm method
    public int LevenshteinDistance(String first, String second) {

        int first_len = first.length(); //Getting the length of the first string
        int second_len = second.length(); //Getting the length of the second string

        int[][] matrix = new int[first_len + 1][second_len + 1]; //Creating a matrix with the variables above
        
        for (int i = 0; i <= first_len; i++) {
            matrix[i][0] = i; //Equalizing the rows to the i value
        }
        for (int j = 0; j <= second_len; j++) {
            matrix[0][j] = j; //Equalizing the columns to the j value
        }

        for (int i = 1; i <= first_len; i++) {
            for (int j = 1; j <= second_len; j++) {
                if (first.charAt(i - 1) == second.charAt(j - 1)) { //If the characters at the current position are the same, the distance remains the same as the diagonal value
                    matrix[i][j] = matrix[i - 1][j - 1];
                } else {  //If they are different, the minimum value between the three closest cells plus one is selected as the new distance
                    matrix[i][j] = 1 + Math.min(matrix[i - 1][j - 1], Math.min(matrix[i][j - 1], matrix[i - 1][j]));
                }
            }
        }

        return matrix[first_len][second_len];
    }

    //Method for getting the closest match according to the parameter
    public String getClosestMatch(String word) {

        AhmedMuazAtikObadaMasriNode<T> vary = root; //Creating a temp node to hold the root

        int distance = Integer.MAX_VALUE;

        String closest = "";

        while (vary != null) {

            int d = LevenshteinDistance(vary.word, word); //Finding the levenshtein distance between the parameters

            if (d < distance) {
                distance = d;
                closest = vary.word;
            }

            if (d == 0) {
                break;
            } else if (word.compareToIgnoreCase(vary.word) < 0) {
                vary = vary.leftChild; //Moving to the left child
            } else {
                vary = vary.rightChild; //Moving to the right child
            }
        }

        return closest;
    }

    //Method for getting the closest matches according to the parameter
    public AhmedMuazAtikObadaMasriBinaryTree getClosestMatches(AhmedMuazAtikObadaMasriBinaryTree<T> bt, String word, int key) {

        AhmedMuazAtikObadaMasriBinaryTree<T> suggested_bt = new AhmedMuazAtikObadaMasriBinaryTree<>(); //Empty binary tree for suggested words

        AhmedMuazAtikObadaMasriBinaryTree<T> temp_bt = bt; //1000 words binary tree

        AhmedMuazAtikObadaMasriNode<T> suggested_root = suggested_bt.root; //Empty binary tree's root

        AhmedMuazAtikObadaMasriNode<T> vary = temp_bt.root; //Temporary binary tree's root

        int distance = Integer.MAX_VALUE;

        String closest = "";

        //Creating a for loop for the number of suggestions
        for (int i = 0; i < key; i++) {

            vary = temp_bt.root;

            distance = Integer.MAX_VALUE;

            closest = "";

            while (vary != null) {

                int d = LevenshteinDistance(vary.word, word);

                if (d < distance) {
                    distance = d;
                    closest = vary.word;
                }

                if (d == 0) {
                    break;
                } else if (word.compareToIgnoreCase(vary.word) < 0) {
                    vary = vary.leftChild; //Moving to the left child
                } else {
                    vary = vary.rightChild; //Moving to the right child
                }
            }

            //Deleting the closest word from the temp binary tree
            temp_bt.deleteKey(closest);

            //Inserting the closest word to the suggested binary tree
            suggested_bt.insert(closest);

        }

        return suggested_bt;
    }

    //Adding suggestions into the default list model from the binary tree
    DefaultListModel addIntoListModel(AhmedMuazAtikObadaMasriNode node) {

        if (node == null) {
            return null;
        }

        addIntoListModel(node.leftChild);
        def_model.addElement(node.word);
        addIntoListModel(node.rightChild);

        return def_model;
    }

    //Deleting the word from binary tree using delete_Recursive method
    void deleteKey(String word) {
        root = delete_Recursive(root, word);
    }

    //Deleting the word from binary tree recursively
    AhmedMuazAtikObadaMasriNode delete_Recursive(AhmedMuazAtikObadaMasriNode root, String word) {
        
        if (root == null) {
            return root;
        }
        
        //If word is smaller than root's word
        if (word.compareTo(root.word) < 0) 
        {
            root.leftChild = delete_Recursive(root.leftChild, word); //Deleting the left child
        } else if (word.compareTo(root.word) > 0)  //If word is bigger than root's word
        {
            root.rightChild = delete_Recursive(root.rightChild, word); //Deleting the right child
        } else {
            
            if (root.leftChild == null) {
                return root.rightChild;
            } else if (root.rightChild == null) {
                return root.leftChild;
            }

            root.word = minValue(root.rightChild); //Finding the min value of the right child

            root.rightChild = delete_Recursive(root.rightChild, root.word); //Deleting the right child from the binary tree
        }
        return root;
    }

    //Calculating the minimum value of the node
    String minValue(AhmedMuazAtikObadaMasriNode root) {
        
        String minval = root.word;
        
        //Looking for the bottom left child for the min value
        while (root.leftChild != null) {
            minval = root.leftChild.word;
            root = root.leftChild;
        }
        return minval;
    }
}
