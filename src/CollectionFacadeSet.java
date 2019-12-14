import java.util.Iterator;

public class CollectionFacadeSet implements SimpleSet {

    protected java.util.Collection<java.lang.String> collection;

    /**
     * Creates a new facade wrapping the specified collection.
     * @param collection The Collection to wrap.
     */
    public CollectionFacadeSet(java.util.Collection<java.lang.String> collection){
        this.collection = collection;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set.
     * @return false if newValue already exists in the set.
     */
    public boolean add(java.lang.String newValue){
        if (collection.contains(newValue)) return false;
        return this.collection.add(newValue);
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for.
     * @return true if searchVal is found in the set.
     */
    public boolean contains(java.lang.String searchVal){
        return this.collection.contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete.
     * @return true if toDelete is found and deleted.
     */
    public boolean delete(java.lang.String toDelete){
        return this.collection.remove(toDelete);
    }

    /**
     * @return The number of elements currently in the set
     */
    public int size(){
        return this.collection.size();
    }

    /**
     * @return The iterator of the collection.
     */
    public Iterator<String> iterator(){
        return this.collection.iterator();
    }
}
