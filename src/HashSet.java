public class HashSet {
    private String[] table;
    private int capacity;
    private int size;
    private static final double LOAD_FACTOR = 0.9;

    public HashSet(int cap) {
        capacity = cap;
        table = new String[capacity];
        size = 0;
    }

    private int hashIndex(String key) {
        int hash = key.hashCode();
        return (hash & 0x7FFFFFFF) % capacity;
    }

    public boolean contains(String key) {
        int index = hashIndex(key);
        int start = index;

        while (table[index] != null) {
            if (table[index].equals(key)) return true;
            index = (index + 1) % capacity;
            if (index == start) break; // full cycle, key not found
        }

        return false;
    }

    public void add(String key) {
        if (contains(key)) return;

        if (size >= capacity * LOAD_FACTOR) {
            resize();
        }

        int index = hashIndex(key);
        while (table[index] != null) {
            index = (index + 1) % capacity;
        }

        table[index] = key;
        size++;
    }

    private void resize() {
        int newCapacity = capacity * 2;
        String[] oldTable = table;

        table = new String[newCapacity];
        capacity = newCapacity;
        size = 0;

        for (String key : oldTable) {
            if (key != null) {
                add(key);  // rehash keys into new table
            }
        }
    }
}
