import java.util.Iterator;
import java.util.LinkedList;


public class OpenHashSet extends SimpleHashSet {

    private CollectionFacadeSet[] hashTable;

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet(){
        super();
        this.initialize();
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor the upper load factor before rehashing.
     * @param lowerLoadFactor the lower load factor before rehashing
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor){
        super(upperLoadFactor, lowerLoadFactor);
        this.initialize();
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     * @param data the elements we want to add.
     */
    public OpenHashSet(java.lang.String[] data){
        super();
        this.initialize();
        for (String dataToAdd: data){
            this.add(dataToAdd);
        }
    }

    /**
     * Initialize the hashTable as an array of empty linked lists.
     */
    private void initialize(){
        this.hashTable = new CollectionFacadeSet[this.capacity];
        for (int i=0; i < this.capacity(); i++){
            this.hashTable[i] = new CollectionFacadeSet(new LinkedList<String>());
        }
    }

    /**
     * Add a specified element to the set, assuming this element does not appear in the set and there is no
     * need to resize the table.
     * @param newValue New value to add to the set
     */
    protected void regularAdd(java.lang.String newValue){
        int index = this.getIndex(newValue);
        this.hashTable[index].add(newValue);
    }


    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for.
     * @return True if searchVal is found in the set.
     */
    public boolean contains(java.lang.String searchVal){
        CollectionFacadeSet curLinkedList = this.hashTable[this.getIndex(searchVal)];
        return curLinkedList.contains(searchVal);
    }

    /**
     * Delete a specified element to the set, assuming this element appears in the set and there is no
     * need to resize the table.
     * @param toDelete Value to delete from the set.
     */
    protected void regularDelete(java.lang.String toDelete){
        int index = this.getIndex(toDelete);
        this.hashTable[index].delete(toDelete);
    }

    /**
     * @param value the value to find an index to add it to.
     * @return the index in the hash table this string should be in.
     */
    protected int getIndex(java.lang.String value){
        int hashCode = value.hashCode();
        return clamp(hashCode);
    }

    /**
     * Resize the table to be in the given newCapacity.
     * @param newCapacity the new size to change the table to.
     */
    protected void resize(int newCapacity){
        this.capacity = newCapacity;
        CollectionFacadeSet[] oldHashTable = this.hashTable;
        this.initialize();
        this.reHash(oldHashTable);
    }

    /**
     * reHash the table, takes all the element from the given old table and place them in the current
     * hashTable.
     * @param oldHashTable contains the element to add to the hashTable.
     */
    private void reHash(CollectionFacadeSet[] oldHashTable){
        for (CollectionFacadeSet curLinkedList: oldHashTable){
            int length = curLinkedList.size();
            Iterator<String> linkedListIterator = curLinkedList.iterator();
            for (int i = 0; i < length; i++) this.regularAdd(linkedListIterator.next());
        }
    }

}
