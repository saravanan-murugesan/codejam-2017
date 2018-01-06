package sarav;

import java.util.Queue;

class LinkedList {

    /* head node of link list */
    static Stall head;

    /* Link list Node */
    class Stall
    {
        public final int stallId;
        Stall next, prev;

        Stall(int d)
        {
            stallId = d;
            next = prev = null;
        }
    }

    /* A Binary Tree Node */
    class TNode
    {
        int stallId;
        boolean isOccupied = false;
        TNode left, right;

        TNode(int stallId)
        {
            this.stallId = stallId;
            left = right = null;
        }
    }

    /* This function counts the number of nodes in Linked List
       and then calls sortedListToBSTRecur() to construct BST */
    TNode sortedListToBST()
    {
        /*Count the number of nodes in Linked List */
        int n = countNodes(head);

        /* Construct BST */
        return sortedListToBSTRecur(n);
    }

    /* The main function that constructs balanced BST and
       returns root of it.
       n  --> No. of nodes in the Doubly Linked List */
    TNode sortedListToBSTRecur(int n)
    {
        System.out.println(n);
//        /* Base Case */
//        if (n == 0)
//            return null;

        if(n == 2) {
            TNode right = new TNode(head.stallId);
            /* Change head pointer of Linked List for parent
           recursive calls */
            head = head.next;
            TNode root =  new TNode(head.stallId);
            root.right = right;
            head = head.next;
            return root;
        }
        /* Recursively construct the left subtree */
        TNode left = sortedListToBSTRecur(n / 2);

        /* head_ref now refers to middle node,
           make middle node as root of BST*/
        TNode root = new TNode(head.stallId);

        // Set pointer to left subtree
        root.left = left;

        /* Change head pointer of Linked List for parent
           recursive calls */
        head = head.next;

        /* Recursively construct the right subtree and link it
           with root. The number of nodes in right subtree  is
           total nodes - nodes in left subtree - 1 (for root) */
        root.right = sortedListToBSTRecur(n - n / 2 - 1);

        return root;
    }

    /* UTILITY FUNCTIONS */
    /* A utility function that returns count of nodes in a
       given Linked List */
    int countNodes(Stall head)
    {
        int count = 0;
        Stall temp = head;
        while (temp != null)
        {
            temp = temp.next;
            count++;
        }
        return count;
    }

    /* Function to insert a node at the beginging of
       the Doubly Linked List */
    void push(int new_data)
    {
        /* allocate node */
        Stall new_node = new Stall(new_data);

        /* since we are adding at the begining,
           prev is always NULL */
        new_node.prev = null;

        /* link the old list off the new node */
        new_node.next = head;

        /* change prev of head node to new node */
        if (head != null)
            head.prev = new_node;

        /* move the head to point to the new node */
        head = new_node;
    }

    /* Function to print nodes in a given linked list */
    void printList(Stall node)
    {
        while (node != null)
        {
            System.out.print(node.stallId + " ");
            node = node.next;
        }
    }

    /* A utility function to print preorder traversal of BST */
    void preOrder(TNode node)
    {
        if (node == null)
            return;
        System.out.print(node.stallId + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    void levelOrder(TNode node)
    {
        if (node == null)
            return;
        System.out.print(node.stallId + " ");
        if (node.left != null) {
            System.out.print(node.left.stallId+ " ");
        }
        if (node.right != null) {
            System.out.print(node.right.stallId+ " ");
        }
        preOrder(node.left);
        preOrder(node.right);
    }

    public void levelOrderTraverse(TNode node)
    {
        if(node == null)
            System.out.println("Empty tree");
        else
        {
            Queue<TNode> q= new java.util.LinkedList<>();
            q.add(node);
            while(q.peek() != null)
            {
                TNode temp = q.remove();
                System.out.println(temp.stallId);
                if(temp.left != null)
                    q.add(temp.left);
                if(temp.right != null)
                    q.add(temp.right);
            }
        }
    }

//    TNode occupyNextPreferredStall(TNode root) {
//        if(!root.isOccupied) {
//            root.isOccupied = true;
//            return root;
//        } else {
//        }
//    }
//
//    void occupyAndUpdateParent(TNode node) {
//        node.isOccupied = true;
//        node.pa
//    }

    /* Drier program to test above functions */
    public static void main(String[] args) {
        LinkedList llist = new LinkedList();

        /* Let us create a sorted linked list to test the functions
           Created linked list will be 7->6->5->4->3->2->1 */
        llist.push(11);
        llist.push(10);
        llist.push(9);
        llist.push(8);
        llist.push(7);
        llist.push(6);
        llist.push(5);
        llist.push(4);
        llist.push(3);
        llist.push(2);
        llist.push(1);

        System.out.println("Given Linked List ");
        llist.printList(head);

        /* Convert List to BST */
        TNode root = llist.sortedListToBST();
        System.out.println("");
        System.out.println("Pre-Order Traversal of constructed BST ");
        llist.preOrder(root);
        llist.levelOrderTraverse(root);
    }
}
