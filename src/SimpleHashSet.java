public abstract class SimpleHashSet implements SimpleSet{

    protected static final float DEFAULT_HIGHER_CAPACITY = 0.75f;
    protected static final float DEFAULT_LOWER_CAPACITY = 0.25f;
    protected static final int INITIAL_CAPACITY = 16;
    protected int capacity = SimpleHashSet.INITIAL_CAPACITY;
    protected int size = 0;
    private float higherLoadFactor;
    private float lowerLoadFactor;

    /**
     * Constructs a new hash set with the default capacities.
     */
    protected SimpleHashSet(){
        this.higherLoadFactor = SimpleHashSet.DEFAULT_HIGHER_CAPACITY;
        this.lowerLoadFactor = SimpleHashSet.DEFAULT_LOWER_CAPACITY;
    }

    /**
     * Constructs a new hash set.
     * @param upperLoadFactor the upper load factor before rehashing.
     * @param lowerLoadFactor the lower load factor before rehashing
     */
    protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor){
        this.higherLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;
    }

    /**
     * @return The current capacity (number of cells) of the table.
     */
    public int capacity(){return this.capacity;}

    /**
     * Clamps hashing indices to fit within the current table capacity.
     * @param index the index before clamping.
     * @return an index properly clamped.
     */
    protected int clamp(int index) {
        return index & (this.capacity() - 1);
    }

    /**
     * @param value the value to find an index to add it to.
     * @return the index in the hash table this string should be in.
     */
    protected abstract int getIndex(java.lang.String value);

    /**
     * @return The lower load factor of the table.
     */
    protected float getLowerLoadFactor(){
        return this.lowerLoadFactor;
    }

    /**
     * @return The higher load factor of the table.
     */
    protected float getUpperLoadFactor(){
        return this.higherLoadFactor;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return false if newValue already exists in the set.
     */
    public boolean add(java.lang.String newValue){
        if (this.contains(newValue)) return false;
        double upperFactor = (double) (this.size() + 1)/this.capacity();
        if (upperFactor > this.getUpperLoadFactor()){
            this.resize(this.capacity *= 2);
        }
        this.size++;
        this.regularAdd(newValue);
        return true;
    }

    /**
     * Add a specified element to the set, assuming this element does not appear in the set and there is no
     * need to resize the table.
     * @param newValue New value to add to the set
     */
    protected abstract void regularAdd(java.lang.String newValue);

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete.
     * @return True if toDelete is found and deleted.
     */
    public boolean delete(java.lang.String toDelete){
        if (!this.contains(toDelete)) return false;
        this.regularDelete(toDelete);
        this.size--;
        double lowerFactor = (double) this.size()/this.capacity();
        if (lowerFactor < this.getLowerLoadFactor()) {
            if (this.capacity / 2 >= 1) this.resize(this.capacity /= 2);
        }
        return true;
    }

    /**
     * Delete a specified element to the set, assuming this element appears in the set and there is no
     * need to resize the table.
     * @param toDelete Value to delete from the set.
     */
    protected abstract void regularDelete(java.lang.String toDelete);

    /**
     * Resize the table to be in the given newCapacity.
     * @param newCapacity the new size to change the table to.
     */
    protected abstract void resize(int newCapacity);

    /**
     * @return The number of elements currently in the set.
     */
    public int size(){return this.size;}

}
