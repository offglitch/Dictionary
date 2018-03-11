import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;
/** CompactPrefixTree class, implements Dictionary ADT and
 *  several additional methods. Can be used as a spell checker.
 *  Fill in code in the methods of this class. You may add additional methods. */


public class CompactPrefixTree implements Dictionary {

    private Node root; // the root of the tree

    /** Default constructor.
     * Creates an empty "dictionary" (compact prefix tree).
     * */
    public CompactPrefixTree(){

        root = new Node();
    }

    /**
     * Creates a dictionary ("compact prefix tree")
     * using words from the given file.
     * @param filename the name of the file with words
     */
    public CompactPrefixTree(String filename) {

        this();
        try {
            Scanner sc = new Scanner(new File(filename));
            while(sc.hasNextLine()) {
                add(sc.nextLine().trim());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not read " + filename);
        }
        // FILL IN CODE:
        // Read each word from the file, add it to the tree


    }

    /** Adds a given word to the dictionary.
     * @param word the word to add to the dictionary
     */
    public void add(String word) {
        add(word.toLowerCase(), root); // Calling private add method
    }

    /**
     * Checks if a given word is in the dictionary
     * @param word the word to check
     * @return true if the word is in the dictionary, false otherwise
     */
    public boolean check(String word) {
        return check(word.toLowerCase(), root); // Calling private check method
    }

    /**
     * Checks if a given prefix is a prefix of any word stored in the dictionary
     * @param prefix The prefix of a word
     * @return true if the prefix is a prefix of any word in the dictionary, false otherwise
     */
    public boolean checkPrefix(String prefix) {
        return checkPrefix(prefix.toLowerCase(), root); // Calling private checkPrefix method
    }


    /**
     * Prints all the words in the dictionary, in alphabetical order,
     * one word per line.
     */
    public void print() {
        print("", root); // Calling private print method
    }

    /**
     * Print out the nodes of the compact prefix tree, in a pre-order fashion.
     * First, print out the root at the current indentation level
     * (followed by * if the node's valid bit is set to true),
     * then print out the children of the node at a higher indentation level.
     */
    public void printTree() {
        printTree(root, System.out);


    }

    /**
     * Print out the nodes of the tree to a file, using indentations to specify the level
     * of the node.
     * @param filename the name of the file where to output the tree
     */
    public void printTree(String filename) {
        try {
            PrintStream out = new PrintStream(new File(filename));
            printTree(root, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return an array of the entries in the dictionary that are as close as possible to
     * the parameter word.  If the word passed in is in the dictionary, then
     * return an array of length 1 that contains only that word.  If the word is
     * not in the dictionary, then return an array of numSuggestions different words
     * that are in the dictionary, that are as close as possible to the target word.
     * Implementation details are up to you, but you are required to make it efficient
     * and make good use ot the compact prefix tree.
     *
     * @param word The word to check
     * @param numSuggestions The length of the array to return.  Note that if the word is
     * in the dictionary, this parameter will be ignored, and the array will contain a
     * single world.
     * @return An array of the closest entries in the dictionary to the target word
     */

    public String[] suggest(String word, int numSuggestions) {
        // FILL IN CODE
        // Note: you need to create a private suggest method in this class
        // (like we did for methods add, check, checkPrefix)


        return null; // don't forget to change it
    }

    // ---------- Private helper methods ---------------
    private int getCommonLength(String s1, String s2) {

        int j = Math.min(s2.length(), s1.length());
        int i = 0;
        for ( ; i < j ; i++) {
            if(s1.charAt(i) != s2.charAt(i)) {
                break;
            }
        }

        return i;
    }
    private void printTree(Node node, PrintStream out, int level) {
        for (int i = 0 ; i < level ; i++) {
            out.print(" ");
        }
        out.print(node.prefix);
        if(node.isWord) {
            out.print("*");
        }
        out.println("");

        for (Node child : node.children) {
            if(child != null) {
                printTree(child, out, level+1);
            }
        }
    }
    private void printTree(Node node, PrintStream out) {
        printTree(node, out, 0);
    }

    /**
     *  A private add method that adds a given string to the tree
     * @param s the string to add
     * @param node the root of a tree where we want to add a new string

     * @return a reference to the root of the tree that contains s
     */
    private Node add(String s, Node node) {
        if(node.prefix.equals("")) {
            Node child = node.getChildFor(s);
            if(child == null) {
                child = new Node(s);
                child.isWord = true;
                node.insertChild(child);
                child.parent = node;
                return child;
            }
            else {
                return add(s, child);
            }
        }
        if(node.prefix.equals(s) || s.equals("")) {
            node.isWord = true;
            return node;
        }


        int i = getCommonLength(node.prefix, s);
        String suffix = node.prefix.substring(i);
        String suffixWord = s.substring(i);
        String lcp = s.substring(0, i);

        if( !lcp.equals("")) {
            if(!suffix.equals("")) {
                Node newNode = new Node(lcp); // 1
                newNode.isWord = false;
                node.prefix = suffix;
                newNode.insertChild(node); // 2

                node.parent.insertChild(newNode);
                newNode.parent = node.parent;
                node.parent = newNode;
                newNode.parent.insertChild(newNode);
                if(newNode.prefix.length() == 1 &&
                        suffixWord.length() > 0 &&
                        newNode.prefix.charAt(0) == suffixWord.charAt(0)) {
                    //System.out.println("\t" + suffixWord + " " + newNode.prefix);
                    return add(s, newNode);
                }
                else {
                    return add(suffixWord, newNode);
                }
            }
            else {
                Node child = node.getChildFor(suffixWord);
                if(child == null) {
                    child = new Node(suffixWord);
                    child.isWord = true;
                    node.insertChild(child);
                    child.parent = node;
                    return child;
                }
                else {

                    return add(suffixWord, child);
                }

            }
        }
        else {
            Node child = node.getChildFor(suffixWord);
            if(child == null) {
                child = new Node(s);
                child.isWord = true;
                node.insertChild(child);
                child.parent = node;
                return child;
            }
            else {
                return add(suffixWord, child);
            }
        }
    }


    /** A private method to check whether a given string is stored in the tree.
     *
     * @param s the string to check
     * @param node the root of a tree
     * @return true if the prefix is in the dictionary, false otherwise
     */
    private boolean check(String s, Node node) {
        // FILL IN CODE
        if(s.length() != 0) {
            if(node.prefix.equals("")) {
                Node child = node.getChildFor(s);
                if(child != null) {
                    return check(s, child);
                }
            }
            if(s.equals(node.prefix)) {

                return node.isWord;
            }
            int i = getCommonLength(s, node.prefix);
            if(i <= node.prefix.length()) {
                Node child = node.getChildFor(s.substring(i));
                if(child != null) {
                    return check(s.substring(i), child);
                }
            }
        }
        return false;
    }

    /**
     * A private recursive method to check whether a given prefix is in the tree
     *
     * @param prefix the prefix
     * @param node the root of the tree
     * @return true if the prefix is in the dictionary, false otherwise
     */
    private boolean checkPrefix(String prefix, Node node) {
        // FILL IN CODE
        if(prefix.length() != 0) {
            if(node.prefix.equals("")) {
                Node child = node.getChildFor(prefix);
                if(child != null) {
                    return checkPrefix(prefix, child);
                }
            }
            if(prefix.equals(node.prefix)) {

                return true;
            }
            int i = getCommonLength(prefix, node.prefix);
            if(i <= node.prefix.length()) {
                Node child = node.getChildFor(prefix.substring(i));
                if(child != null) {
                    return checkPrefix(prefix.substring(i), child);
                }
            }
        }
        return true;
    }

    /**
     * Outputs all the words stored in the dictionary
     * to the console, in alphabetical order, one word per line.
     * @param s the string obtained by concatenating prefixes on the way to this node
     * @param node the root of the tree
     */
    private void print(String s, Node node){
        for(Node child: node.children) {
            if(child != null) {
                String newS = s + child.prefix;

                if(child.isWord) {
                    System.out.println(newS);
                }
                print(newS, child);
            }
        }
    }

    // private void suggest(String s, Node node){

    //}
    // FILL IN CODE: add a private suggest method. Decide which parameters
    // it should have

    // --------- Private class Node ------------
    // Represents a node in a compact prefix tree
    private class Node {
        String prefix; // prefix stored in the node
        Node children[]; // array of children (26 children)
        boolean isWord; // true if by concatenating all prefixes on the path from the root to this node, we get a valid word
        private Node parent;

        Node() {
            isWord = false;
            prefix = "";
            children = new Node[26]; // initialize the array of children
        }

        Node(String s) {
            prefix = s;
            children = new Node[26];
        }

        public Node getChildFor(String s) {
            if(s.equals("")) {
                return null;
            }
            return children[s.charAt(0) - 'a'];
        }

        public void insertChild(Node child) {
            if(child.prefix.length() > 0) {
                int indx = child.prefix.charAt(0) - 'a';
                children[indx] = child;
            }
        }

        public String toString() {
            return String.format("%s <- %s ", prefix,  (parent == null) ? "" : parent.prefix);
        }
    }

}