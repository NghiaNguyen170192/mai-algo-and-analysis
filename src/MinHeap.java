public class MinHeap {
    Node[] heap;
    int size;

    public MinHeap(int capacity) {
        heap = new Node[capacity];
        size = 0;
    }

    public void insert(Node node) {
        if (size == heap.length) resize();
        heap[size] = node;
        int pos = size++;
        while (pos > 0 && heap[pos].priority < heap[(pos - 1) / 2].priority) {
            swap(pos, (pos - 1) / 2);
            pos = (pos - 1) / 2;
        }
    }

    private void resize() {
        Node[] newHeap = new Node[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, heap.length);
        heap = newHeap;
    }

    public Node remove() {
        Node top = heap[0];
        heap[0] = heap[--size];
        heap[size] = null; // Nullify the old last element to free memory
        heapify(0);
        return top;
    }

    private void heapify(int index) {
        int position = index, left = 2 * index + 1, right = 2 * index + 2;

        if (left < size && heap[left].priority < heap[position].priority) position = left;
        if (right < size && heap[right].priority < heap[position].priority) position = right;

        if (position != index) {
            swap(index, position);
            heapify(position);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void swap(int i, int j) {
        Node temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}