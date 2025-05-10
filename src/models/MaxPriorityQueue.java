package models;

class MaxPriorityQueue {
    private int[] queue;
    private int size;
    private int capacity;

    public MaxPriorityQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new int[capacity];
        this.size = 0;
    }

    public void enqueue(int value) {
        if (size >= capacity) throw new IllegalStateException("Queue is full");

        // Insert while keeping array sorted in descending order
        int i = size - 1;
        while (i >= 0 && queue[i] < value) { // Change comparison to prioritize larger values
            queue[i + 1] = queue[i];
            i--;
        }
        queue[i + 1] = value;
        size++;
    }

    public int dequeue() {
        if (size == 0) throw new IllegalStateException("Queue is empty");

        int maxValue = queue[0]; // Max element is always at index 0
        for (int i = 1; i < size; i++) {
            queue[i - 1] = queue[i]; // Shift elements left after removal
        }
        queue[size - 1] = 0;
        size--;
        return maxValue;
    }

    public int peekFront() {
        if (size == 0) throw new IllegalStateException("Queue is empty");
        return queue[0]; // Highest priority element (largest value)
    }

    public void printQueue() {
        System.out.print("Max-Priority Queue: ");
        for (int i = 0; i < size; i++) {
            System.out.print(queue[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MaxPriorityQueue pq = new MaxPriorityQueue(10);
        pq.enqueue(10);
        pq.printQueue();
        pq.enqueue(5);
        pq.printQueue();
        pq.enqueue(20);
        pq.printQueue();
        pq.enqueue(3);
        pq.printQueue();

        System.out.println("Peek Front: " + pq.peekFront());
        System.out.println("Dequeued: " + pq.dequeue());
        pq.printQueue();
    }
}