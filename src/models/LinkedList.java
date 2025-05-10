package models;

public class LinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void insertAtHead(T data) {
        Node<T> node = new Node<>(data);

        if (head == null) {
            head = node;
            tail = head;
        } else {
            node.next = head;
            head = node;
        }

        size++;
    }

    public void insertAtTail(T data) {
        Node<T> node = new Node<>(data);

        if (head == null) {
            head = node;
            tail = head;
            return;
        }

        tail.next = node;
        tail = node;
        size++;
    }

    public boolean insertAt(int index, T data) {
        if (index > size || size < 0) {
            return false;
        }

        if (isEmpty()) {
            insertAtHead(data);
            return true;
        }

        Node<T> current = head;
        Node<T> previous = null;
        while (index > 0) {
            previous = current;
            current = current.next;
            index--;
        }

        Node<T> node = new Node<>(data);
        node.next = current;
        previous.next = node;

        if (current == null) {
            tail = node;
        }

        size++;
        return true;
    }

    public boolean deleteFromHead() {
        if (isEmpty()) {
            return false;
        }

        head = head.next;
        size--;
        return true;
    }

    public boolean deleteFromTail() {
        if (isEmpty()) {
            return false;
        }

        return true;
    }

    public boolean deleteAtIndex(int index) {
        if (index >= size || isEmpty()) {
            return false;
        }

        if (index == 0) {
            deleteFromHead();
            return true;
        }

        if (index == size - 1) {
            deleteFromTail();
            return true;
        }

        Node<T> current = head;
        Node<T> previous = null;
        while (index > 0) {
            previous = current;
            current = current.next;
            index--;
        }

        previous.next = current.next;
        if (current.next == null) {
            tail = previous;

        }
        size--;
        return true;
    }

    public void reverse() {
        Node<T> previous = null;
        Node<T> current = head;
        Node<T> next = null;

        tail = head;

        while (current != null) {
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }

        head = previous;
    }

    public boolean exists(T data) {
        if (isEmpty()) {
            return false;
        }

        Node<T> current = head;

        while (current != null) {
            if (current.data == data) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    public Node<T> getMiddle(Node<T> startNode) {
        if (startNode == null) {
            return startNode;
        }

        Node<T> slow = startNode;
        Node<T> fast = startNode;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return this.size;
    }

    public void print() {
        Node<T> current = head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    public void main(String[] args) {

    }
}