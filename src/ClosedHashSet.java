public class ClosedHashSet extends SimpleHashSet{

    private String[] hashTable = new String[this.capacity];
    private static final String DELETED = new String("deleted");

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
     * factor (0.75) and lower load factor (0.25).
     */
    public ClosedHashSet(){
        super();
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor){
        super(upperLoadFactor, lowerLoadFactor);
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75), and
     * lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public ClosedHashSet(java.lang.String[] data){
        super();
        for (String dataToAdd: data){
            this.add(dataToAdd);
        }
    }

    /**
     * Add a specified element to the set, assuming this element does not appear in the set and there is no
     * need to resize the table.
     * @param newValue New value to add to the set
     */
    protected void regularAdd(java.lang.String newValue){
        int index = this.getIndex(newValue);
        this.hashTable[index] = newValue;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for.
     * @return true if searchVal is found in the set.
     */
    public boolean contains(java.lang.String searchVal) {
        return (this.indexOfValue(searchVal) != -1);
    }

    /**
     * @param value value to search in the set.
     * @return the index of the value if the value appears in the set, -1 otherwise.
     */
    private int indexOfValue(java.lang.String value) {
        int hashCode = value.hashCode();
        for (int i = 0; i < this.capacity(); i++) {
            int index = clamp_i(hashCode, i);
            String curValue = hashTable[index];
            if (curValue != ClosedHashSet.DELETED) {
                if (curValue != null && curValue.equals(value)) return index;
                else if (curValue == null) return -1;
            }
        } return -1;
    }

    /**
     * Delete a specified element to the set, assuming this element appears in the set and there is no
     * need to resize the table.
     * @param toDelete Value to delete from the set.
     */
    protected void regularDelete(java.lang.String toDelete){
        int index = this.indexOfValue(toDelete);
        this.hashTable[index] = ClosedHashSet.DELETED;
    }


    /**
     * @param value the value to find an index to add it to.
     * @return the index in the hash table this string should be in.
     */
    protected int getIndex(java.lang.String value){
        int hashCode = value.hashCode();
        for (int i=0; i < this.capacity(); i++){
            int clampedIndex = this.clamp_i(hashCode, i);
            if (hashTable[clampedIndex] == null) return clampedIndex;
        }
        return -1;
    }

    /**
     * Clamps hashing indices to fit within the current table capacity (doesn't have to be empty in the set).
     * @param hashCode the hash code of the value to find it's index.
     * @param i the i to put in the calculation.
     * @return an index properly clamped.
     */
    private int clamp_i(int hashCode, int i){
        return clamp(hashCode + (i + i*i) / 2);
    }

    /**
     * Resize the table to be in the given newCapacity.
     * @param newCapacity the new size to change the table to.
     */
    protected void resize(int newCapacity){
        this.capacity = newCapacity;
        String[] oldHashTable = this.hashTable;
        this.hashTable = new String[newCapacity];
        this.reHash(oldHashTable);
    }

    /**
     * reHash the table, takes all the element from the given old table and place them in the current
     * hashTable.
     * @param oldHashTable contains the element to add to the hashTable.
     */
    private void reHash(String[] oldHashTable){
        for (String value: oldHashTable){
            if (value != ClosedHashSet.DELETED && value != null) this.regularAdd(value);
        }
    }

}
