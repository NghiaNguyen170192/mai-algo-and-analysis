package models;

class MinPriorityQueue {
    private int[] heap;
    private int size;
    private int capacity;

    public MinPriorityQueue(int capacity) {
        this.capacity = capacity;
        this.heap = new int[capacity];
        this.size = 0;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    public void insert(int value) {
        if (size >= capacity) throw new IllegalStateException("Queue is full");

        heap[size] = value;
        int index = size;
        size++;

        // Heapify Up
        while (index > 0 && heap[index] < heap[parent(index)]) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    public int removeMin() {
        if (size == 0) throw new IllegalStateException("Queue is empty");

        int min = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = 0;
        size--;

        // Heapify Down
        int index = 0;
        while (true) {
            int left = leftChild(index);
            int right = rightChild(index);
            int smallest = index;

            if (left < size && heap[left] < heap[smallest]) smallest = left;
            if (right < size && heap[right] < heap[smallest]) smallest = right;
            if (smallest == index) break;

            swap(index, smallest);
            index = smallest;
        }
        return min;
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public void printQueue() {
        System.out.print("Priority Queue: ");
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MinPriorityQueue pq = new MinPriorityQueue(10);
        pq.insert(10);
        pq.printQueue();
        pq.insert(5);
        pq.printQueue();
        pq.insert(20);
        pq.printQueue();
        pq.insert(3);
        pq.printQueue();
        pq.insert(1);
        pq.printQueue();
        pq.insert(6);
        pq.printQueue();
        System.out.println("Removed Min: " + pq.removeMin());
        pq.printQueue();
        System.out.println("Removed Min: " + pq.removeMin());
        pq.printQueue();
        System.out.println("Removed Min: " + pq.removeMin());
        pq.printQueue();
    }
}
