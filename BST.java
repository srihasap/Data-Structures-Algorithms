import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Your implementation of a BST.
 *
 * @author SRIHASA PENCHIKALA
 * @version 1.0
 * @userid spenchikala3
 * @GTID 903873663
 *
 * Collaborators: NONE
 *
 * Resources: NONE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("Cannot add null data");
            }
            add(element);
        }

    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        root = recursiveAdd(root, data);
    }

    /**
     * Recursive helper method for add.
     * @param curr current node
     * @param data data to add
     * @return the node to add
     */
    private BSTNode<T> recursiveAdd(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode<>(data);
        }

        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(recursiveAdd(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(recursiveAdd(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        BSTNode<T> dummy = new BSTNode<>(null);
        root = recursiveRemove(root, data, dummy);
        size--;
        return dummy.getData();
    }

    /**
     * Recursive helper method for remove.
     * @param curr current node
     * @param data data to remove
     * @param dummy dummy node to store data
     * @return the node to remove
     */
    private BSTNode<T> recursiveRemove(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Data not found");
        }

        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(recursiveRemove(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(recursiveRemove(curr.getRight(), data, dummy));
        } else {
            dummy.setData(curr.getData());

            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else {
                BSTNode<T> successor = new BSTNode<>(null);
                curr.setRight(removeSuccessor(curr.getRight(), successor));
                curr.setData(successor.getData());
            }
        }
        return curr;
    }

    /**
     * Recursive helper method for remove. Removes the successor from the tree.
     * @param curr current node
     * @param dummy dummy node to store data
     * @return the node to remove
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy));
            return curr;
        }
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        T getResult = getHelper(data, root);

        if (getResult == null) {
            throw new java.util.NoSuchElementException("Data not found in tree");
        }

        return getResult;
    }

    /**
     * Recursive helper method for get.
     * @param data data to get
     * @param curr current node
     * @return the data to get
     */
    private T getHelper(T data, BSTNode<T> curr) {
        if (curr == null || curr.getData() == null) {
            return null;
        }

        if (data.compareTo(curr.getData()) < 0) {
            return getHelper(data, curr.getLeft());
        } else if (data.compareTo(curr.getData()) > 0) {
            return getHelper(data, curr.getRight());
        } else {
            return curr.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        return containsHelper(data, root);
    }

    /**
     * Recursive helper method for contains. Checks if the data is in the tree.
     * @param data data to check
     * @param curr current node
     * @return true if the data is in the tree, false otherwise
     */
    private boolean containsHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            return false;
        }

        if (data.compareTo(curr.getData()) < 0) {
            return containsHelper(data, curr.getLeft());
        } else if (data.compareTo(curr.getData()) > 0) {
            return containsHelper(data, curr.getRight());
        } else {
            return true;
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> preorderList = new ArrayList<>();
        preorderHelper(root, preorderList);
        return preorderList;
    }

    /**
     * Recursive helper method for preorder. Generates the preorder traversal of the tree.
     * @param root current node
     * @param preorderList list to store the preorder traversal
     */
    private void preorderHelper(BSTNode<T> root, List<T> preorderList) {
        if (root != null) {
            preorderList.add(root.getData());
            preorderHelper(root.getLeft(), preorderList);
            preorderHelper(root.getRight(), preorderList);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> inorderList = new ArrayList<>();
        inorderHelper(root, inorderList);
        return inorderList;
    }

    /**
     * Recursive helper method for inorder. Generates the inorder traversal of the tree.
     * @param root  current node
     * @param inorderList list to store the inorder traversal
     */
    private void inorderHelper(BSTNode<T> root, List<T> inorderList) {
        if (root != null) {
            inorderHelper(root.getLeft(), inorderList);
            inorderList.add(root.getData());
            inorderHelper(root.getRight(), inorderList);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> postorderList = new ArrayList<>();
        postorderHelper(root, postorderList);
        return postorderList;
    }

    /**
     * Recursive helper method for postorder. Generates the postorder traversal of the tree.
     * @param root current node
     * @param postorderList list to store the postorder traversal
     */
    private void postorderHelper(BSTNode<T> root, List<T> postorderList) {
        if (root != null) {
            postorderHelper(root.getLeft(), postorderList);
            postorderHelper(root.getRight(), postorderList);
            postorderList.add(root.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        LinkedList<BSTNode<T>> q = new LinkedList<>();
        List<T> levelorderList = new ArrayList<>();

        if (root != null) {
            q.addLast(root);
            while (q.size() > 0) {
                BSTNode<T> curr = q.removeFirst();
                levelorderList.add(curr.getData());
                if (curr.getLeft() != null) {
                    q.addLast(curr.getLeft());
                }
                if (curr.getRight() != null) {
                    q.addLast(curr.getRight());
                }
            }
        }
        return levelorderList;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return heightHelper(root);
        }
    }

    /**
     * Recursive helper method for height. Finds the height of the tree.
     * @param root current node
     * @return the height of the tree
     */
    private int heightHelper(BSTNode<T> root) {
        if (root == null) {
            return -1;
        }
        return Math.max(heightHelper(root.getLeft()), heightHelper(root.getRight())) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * This must be done recursively.
     * 
     * A good way to start is by finding the deepest common ancestor (DCA) of both data
     * and add it to the list. You will most likely have to split off and
     * traverse the tree for each piece of data adding to the list in such a
     * way that it will return the path in the correct order without requiring any
     * list manipulation later. One way to accomplish this (after adding the DCA
     * to the list) is to then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. 
     *
     * Please note that there is no relationship between the data parameters 
     * in that they may not belong to the same branch. 
     * 
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use considering the Big-O efficiency of the list
     * operations.
     *
     * This method only needs to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     * 
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        if (!exists(root, data1) || !exists(root, data2)) {
            throw new java.util.NoSuchElementException("Data not found in tree");
        }

        BSTNode<T> dca = findDCA(root, data1, data2);

        List<T> pathToData1 = new ArrayList<>();
        findPathFromNode(dca, data1, pathToData1);

        List<T> pathToData2 = new ArrayList<>();
        findPathFromNode(dca, data2, pathToData2);

        List<T> result = new ArrayList<>();

        for (int i = pathToData1.size() - 1; i >= 0; i--) {
            result.add(pathToData1.get(i));
        }

        for (int i = 1; i < pathToData2.size(); i++) {
            result.add(pathToData2.get(i));
        }

        return result;
    }

    /**
     * Helper method to check if the data exists in the tree.
     * @param node current node
     * @param data data to check
     * @return true if the data is in the tree, false otherwise
     */
    private boolean exists(BSTNode<T> node, T data) {
        if (node == null) {
            return false;
        }

        if (node.getData().equals(data)) {
            return true;
        }

        if (data.compareTo(node.getData()) < 0) {
            return exists(node.getLeft(), data);
        } else {
            return exists(node.getRight(), data);
        }
    }

    /**
     * Finds the deepest common ancestor of two elements in the tree.
     * @param node current node
     * @param data1 data1
     * @param data2 data2
     * @return the deepest common ancestor of the two elements
     */
    private BSTNode<T> findDCA(BSTNode<T> node, T data1, T data2) {
        if (node == null) {
            return null;
        }

        if (data1.compareTo(node.getData()) < 0 && data2.compareTo(node.getData()) < 0) {
            return findDCA(node.getLeft(), data1, data2);
        } else if (data1.compareTo(node.getData()) > 0 && data2.compareTo(node.getData()) > 0) {
            return findDCA(node.getRight(), data1, data2);
        } else {
            return node;
        }
    }

    /**
     * Adds the path to the data in the tree.
     * @param node current node
     * @param data data to add
     * @param path list to store the path
     * @return true if the data is in the tree, false otherwise
     */
    private boolean findPathFromNode(BSTNode<T> node, T data, List<T> path) {
        if (node == null) {
            return false;
        }

        path.add(node.getData());

        if (node.getData().equals(data)) {
            return true;
        }

        if (data.compareTo(node.getData()) < 0) {
            if (findPathFromNode(node.getLeft(), data, path)) {
                return true;
            }
        } else {
            if (findPathFromNode(node.getRight(), data, path)) {
                return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
