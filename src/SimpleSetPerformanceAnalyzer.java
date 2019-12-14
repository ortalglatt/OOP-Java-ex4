import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Arrays;

public class SimpleSetPerformanceAnalyzer {

    private final static int TO_MILI_SECONDS = 1000000;
    private final static int ITERATIONS = 70000;
    private final static int LINKED_LIST_ITERATIONS = 7000;
    private final static String LINKED_LIST = "LinkedList";

    // to change the number of tests to run - you need to remove them from the testNum HashSet.
    // to change the sets kind you want to run the tests on - you need to turn them to empty string ("") in
    // the setsType Array.
    private String[] setsType =
            new String[]{"OpenHashSet", "ClosedHashSet", "TreeSet", "LinkedList", "HashSet"};
    private HashSet<Integer> testsNum = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6));

    private SimpleSet[] sets;
    private String[] data1 = Ex4Utils.file2array("data1.txt");
    private String[] data2 = Ex4Utils.file2array("data2.txt");


    /**
     * Initialize all the sets we need to check.
     */
    private void initialize() {
        SimpleSet openHashSet = new OpenHashSet();
        SimpleSet closedHashSet = new ClosedHashSet();
        SimpleSet treeSet = new CollectionFacadeSet(new TreeSet<>());
        SimpleSet linkedList = new CollectionFacadeSet(new LinkedList<>());
        SimpleSet hashSet = new CollectionFacadeSet(new HashSet<>());
        this.sets = new SimpleSet[]{openHashSet, closedHashSet, treeSet, linkedList, hashSet};
    }

    /**
     * Adds one by one the element from the given array to every set we want to check, and print the time it
     * took (in nano-seconds).
     * @param toAdd the array with the elements to add.
     */
    private void addOneByOneCheck(String[] toAdd) {
        for (int i = 0; i < this.sets.length; i++) {
            if (!setsType[i].equals("")) {
                long timeBefore = System.nanoTime();
                for (String value : toAdd) this.sets[i].add(value);
                long difference = System.nanoTime() - timeBefore;
                System.out.println(setsType[i] + ": " + (difference / TO_MILI_SECONDS) + " ms");
            }
        }
    }

    /**
     * Adds one by one the element from the given array to every set we want to check.
     * @param toAdd the array with the elements to add.
     */
    private void addOneByOne(String[] toAdd) {
        for (int i = 0; i < this.sets.length; i++) {
            if (!setsType[i].equals("")) {
                for (String value : toAdd) this.sets[i].add(value);
            }
        }
    }

    /**
     * Check the "contains" function with the given value on every set we want to check, and print the time it
     * took (in mili-seconds).
     * @param value the value we want to check the "contains" function on.
     */
    private void containsCheck(String value) {
        for (int i = 0; i < this.sets.length; i++) {
            if (!setsType[i].equals("")) {
                long difference;
                if (!this.setsType[i].equals(LINKED_LIST)) {
                    this.containsIterations(value, sets[i], ITERATIONS);
                    long timeBefore = System.nanoTime();
                    this.containsIterations(value, sets[i], ITERATIONS);
                    difference = (System.nanoTime() - timeBefore) / ITERATIONS;
                } else {
                    long timeBefore = System.nanoTime();
                    this.containsIterations(value, sets[i], LINKED_LIST_ITERATIONS);
                    difference = (System.nanoTime() - timeBefore) / LINKED_LIST_ITERATIONS;
                }
                System.out.println(setsType[i] + ": " + difference + " ns");
            }
        }
    }

    /**
     * Runs the "contains" function on the given set and value, it will run in the given iterations times.
     * @param value the value we want to check the "contains" function on.
     * @param set the set we want to check the "contains" function on.
     * @param iterations the number of times we want to run the "contains".
     */
    private void containsIterations(String value, SimpleSet set, int iterations) {
        for (int i = 0; i < iterations; i++) set.contains(value);
    }

    /**
     * Checks which tests to run, and run all of them.
     */
    private void runTests() {
        boolean contains1 = false;
        boolean contains2 = false;
        this.initialize();
        if (this.testsNum.contains(1)) {
            contains1 = true;
            System.out.println("1. Adding one by one results on data1:");
            this.addOneByOneCheck(this.data1);
            System.out.println();
        }
        if (this.testsNum.contains(3)) {
            if (!contains1) this.addOneByOne(this.data1);
            System.out.println("3. contains('hi') results on data1:");
            this.containsCheck("hi");
            System.out.println();
        }
        if (this.testsNum.contains(4)) {
            if (!contains1) this.addOneByOne(this.data1);
            System.out.println("4. contains('-13170890158') results on data1:");
            this.containsCheck("-13170890158");
            System.out.println();
        }
        this.initialize();
        if (this.testsNum.contains(2)) {
            contains2 = true;
            System.out.println("2. Adding one by one results on data2:");
            this.addOneByOneCheck(this.data2);
            System.out.println();
        }
        if (this.testsNum.contains(5)) {
            if (!contains2) this.addOneByOne(this.data2);
            System.out.println("5. contains('23') results on data2:");
            this.containsCheck("23");
            System.out.println();
        }
        if (this.testsNum.contains(6)) {
            if (!contains2) this.addOneByOne(this.data2);
            System.out.println("6. contains('hi') results on data2: ");
            this.containsCheck("hi");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SimpleSetPerformanceAnalyzer analyzer = new SimpleSetPerformanceAnalyzer();
        analyzer.runTests();
    }

}
