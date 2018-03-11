import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/** The Driver class for CompactPrefixTree */
public class Driver {
        public static void main(String[] args) throws FileNotFoundException {
                //full();
                //tx();

                t0();
                t3();
                t1();
                t2();

        }

        private static void tx() throws FileNotFoundException {
                CompactPrefixTree tree = new CompactPrefixTree();
                Scanner sc = new Scanner(new File("words_ospd.txt"));
                while(sc.hasNextLine()) {
                        String s = sc.nextLine();
                        tree.add(s);
                        if(s.equals("cab")) {
                                break;
                        }
                }
                tree.print();
        }
        private static void full() {
                CompactPrefixTree tree = new CompactPrefixTree("words_ospd.txt");
                tree.print();
        }
        private static void t0() {
                CompactPrefixTree tree = new CompactPrefixTree();
                // create the tree shown in the project description
                System.out.println();
                tree.add("hamster");
                tree.add("hamburger");

                tree.print();

        }
        private static void t2() {
                CompactPrefixTree tree = new CompactPrefixTree();
                // create the tree shown in the project description
                System.out.println();
                tree.add("cat");
                tree.add("ape");
                tree.add("apple");
                tree.add("cart");
                tree.add("cats");
                tree.add("cat");
                tree.add("demon");
                tree.add("dog");
                tree.add("demons");

                System.out.println("Suggestions for cat");
                for(String s: tree.suggest("cat", 2)) {
                        System.out.println("\t" + s);
                }

                System.out.println("Suggestions for cata");
                for(String s: tree.suggest("cata", 2)) {
                        System.out.println("\t" + s);
                }

                System.out.println("Found cat " + tree.check("cat"));
                tree.print();
        }

        private static void t3() {
                System.out.println();
                Dictionary dict = new CompactPrefixTree();
                dict.add("aa");
                dict.add("aah");
                dict.add("aahed");
                dict.add("aahing");
                dict.add("aahs");
                dict.add("aal");
                dict.add("aalii");
                dict.add("aaliis");

        }

        private static void t1() {
                System.out.println();
                Dictionary dict = new CompactPrefixTree();
                dict.add("cat");
                dict.add("cart");
                dict.add("carts");
                dict.add("case");
                dict.add("doge");
                dict.add("doghouse");
                dict.add("wrist");
                dict.add("wrath");
                dict.add("wristle");
                System.out.println("Found cat " + dict.check("cat"));
                dict.print();
                // Add other "tests"
        }
}