package office;

import java.util.HashMap;

/**
 * Created by achowdhu on 8/30/2016.
 * least recently used entry would be pushed to the end of the linked list where instance variable "head" represents the head of the linked list.
 * HashMap is used to make searching of the item a O(1) operation
 * the doubly linked list makes the removal of entries a O(1) operation (removal is need when you want to remove it from its current location-
 * -and put it to the front.
 */
public class LRUCache {
    private int maxSize;
    private CacheEntry head;
    private CacheEntry tail;
    private HashMap<String, CacheEntry> map = new HashMap<>();

    public LRUCache(int maxSize){
        this.maxSize = maxSize;
        map = new HashMap<>();

        head = new CacheEntry("HEAD", null);
        tail = new CacheEntry("TAIL", null);
        head.setNext(tail);
        tail.setPrevious(head);
    }

    public void add(String key, String value){
        CacheEntry entry = map.get(key);
        if(entry == null) {
            entry = new CacheEntry(key, value);
            if(map.size() == maxSize){
                System.out.println("Remove.");
                //remove the oldest entry
                CacheEntry deleteEntry = tail.getPrevious();
                map.remove(tail.getPrevious().getKey());
                removeEntry(deleteEntry);
            }
            map.put(key, entry);
        } else {
            entry.setValue(value);
        }
        addEntryToFront(entry);
    }

    public String get(String key){
        CacheEntry entry = map.get(key);
        if(entry != null) {
            addEntryToFront(entry);
            return entry.getValue();
        }
        return null;
    }

    private void addEntryToFront(CacheEntry entry) {
        removeEntry(entry);
        CacheEntry next = head.getNext();

        head.setNext(entry);
        next.setPrevious(entry);

        entry.setNext(next);
        entry.setPrevious(head);
    }

    private void removeEntry(CacheEntry deleteEntry){
        CacheEntry prev = deleteEntry.getPrevious();
        CacheEntry next = deleteEntry.getNext();

        //note: the below if checks are needed because this method can be called from addEntryToFront
        //and addEntryToFront() can be called in the usecase where the cacheEntry is a new entry
        if(prev != null){
            prev.setNext(next);
        }
        if(next !=null){
            next.setPrevious(prev);
        }
    }

    public void print(){
        CacheEntry curr = head.getNext();
        while(!curr.getKey().equals("TAIL")){
            System.out.println(curr.getKey() + "  " +curr.getValue());
            curr = curr.getNext();
        }
    }

    class CacheEntry {
        private CacheEntry previous;
        private CacheEntry next;
        private String key;
        private String value;

        public CacheEntry (String key, String value){
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public CacheEntry getPrevious() {
            return previous;
        }

        public void setPrevious(CacheEntry previous) {
            this.previous = previous;
        }

        public CacheEntry getNext() {
            return next;
        }

        public void setNext(CacheEntry next) {
            this.next = next;
        }
    }
}
