package com.oshovskii.algorithms.treemap;

import java.util.NoSuchElementException;

public class CustomTreeMap<Key extends Comparable<Key>, Value> {
    private Node root;

    private class Node {
        Key key;
        Value value;
        Node left;
        Node right;
        int size;
        int depth;

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            size = 1;
            this.depth = 1;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        return node.size;
    }

    public int depth() {
        return depth(root);
    }

    private int depth(Node node) {
        if (node == null) {
            return 0;
        }
        return node.depth;
    }

    public boolean isEmpty() {
        return root == null;
    }

    private void checkKeyNotNull(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Key не должен быть null");
        }
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node node, Key key) {
        checkKeyNotNull(key);
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node.value;
        } else if (cmp < 0) {
            return get(node.left, key);
        } else {
            return get(node.right, key);
        }
    }

    public void put(Key key, Value value) {
        checkKeyNotNull(key);
        if (value == null) {
            //remove(key)
            return;
        }
        root = put(root, key, value);
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) {
            return new Node(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            node.value = value;
        } else if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else {
            node.right = put(node.right, key, value);
        }
        node.size = size(node.left) + size(node.right) + 1;
        putDepth(node);
        return node;
    }

    private void putDepth(Node node) {
        if (node.left == null && node.right == null) {
            node.depth = 0;
        } else if (node.left != null && node.right == null) {
            node.depth = node.left.depth + 1;
        } else if (node.left == null) {
            node.depth = node.right.depth + 1;
        } else if (node.left.depth > node.right.depth) {
            node.depth = node.left.depth + 1;
        } else if (node.left.depth < node.right.depth) {
            node.depth = node.right.depth + 1;
        } else {
            node.depth = node.left.depth + 1;
        }
    }

    public boolean isBalanced() {
        if (root == null || root.size == 1) {
            return true;
        } else {

            int leftHeight;
            int rightHeight;

            if (root.left == null) {
                leftHeight = 0;
            } else {
                leftHeight = root.left.depth;
            }

            if (root.right == null) {
                rightHeight = 0;
            } else {
                rightHeight = root.right.depth;
            }

            return Math.abs(leftHeight - rightHeight) <= 1;
        }
    }

    public Key minKey() {
        return min(root).key;
    }

    private Node min(Node node) {
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }

    public Key maxKey() {
        return max(root).key;
    }

    private Node max(Node node) {
        if (node.right == null) {
            return node;
        }
        return max(node.right);
    }

    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        root = deleteMin(root);
    }

    private Node deleteMin(Node node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMin(node.left);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void delele(Key key) {
        checkKeyNotNull(key);
        root = delete(root, key);
    }

    private Node delete(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = delete(node.left, key);
        } else if (cmp > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            Node temp = node;
            node = min(node.right);
            node.right = deleteMin(temp.right);
            node.left = temp.left;
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    public String toString() {
        return toString(root);
    }

    private String toString(Node node) {
        if (node == null) {
            return "";
        }
        return toString(node.left) + " " +
                node.key + " = " + node.value + " " +
                toString(node.right);
    }
}

