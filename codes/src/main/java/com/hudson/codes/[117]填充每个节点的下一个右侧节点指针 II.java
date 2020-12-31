package com.hudson.codes;//给定一个二叉树
//
// struct Node {
//  int val;
//  Node *left;
//  Node *right;
//  Node *next;
//} 
//
// 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。 
//
// 初始状态下，所有 next 指针都被设置为 NULL。 
//
// 
//
// 进阶： 
//
// 
// 你只能使用常量级额外空间。 
// 使用递归解题也符合要求，本题中递归程序占用的栈空间不算做额外的空间复杂度。 
// 
//
// 
//
// 示例： 
//
// 
//
// 输入：root = [1,2,3,4,5,null,7]
//输出：[1,#,2,3,#,4,5,7,#]
//解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个右侧节点，如图 B 所示。 
//
// 
//
// 提示： 
//
// 
// 树中的节点数小于 6000 
// -100 <= node.val <= 100 
// 
//
// 
//
// 
// 
// Related Topics 树 深度优先搜索 
// 👍 337 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/

class Solution117 {

//    public static void main(String[] args){
//        Node node = new Node(1);
//        node.left = new Node(2);
//        node.right = new Node(3);
//        node.left.left = new Node(4);
//        node.left.right = new Node(5);
////        node.right.left = new Node(6);
//        node.right.right = new Node(7);
//        Node connect = connect(node);
//        printNode(connect);
//    }
//
//    private static void printNode(Node node){
//        if(node == null) return ;
//        Node cur = node;
//        while(node != null){
//            System.out.print(" "+node.val);
//            node = node.next;
//        }
//        System.out.println(" null");
//        printNode(cur.left);
//    }

    
    static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    // 100%  95.27
    // 本题与116题不同的地方就是这里不是一个完全二叉树
    // 本质思路没有任何变化，还是单链表的方式解决
    public static Node connect(Node root) {
        if(root == null) return null;
        Node head = root;
        // 找到每一个单链表的起点位置，然后往右链接
        // 考虑到可能存在左节点为null的情况，或者说某个节点下没有任何子节点，但它父节点的另一个节点有的情况
        // 因此我们需要考虑终止条件到底是什么
        // 我们可以记录下一个链表的头结点，如果下一个链表的头结点是null的，那么就终止
        while(head != null){
            Node nextHead = null;
            Node nextNode = null;
            // 遍历当前head这个单链表
            while(head != null){
                // 尝试把当前节点的左右链接起来
                if(head.left != null){
                    // 是否已经有头结点
                    if(nextHead == null){
                        nextHead = head.left;
                        nextNode = nextHead;
                    }else{
                        // 已有头结点
                        nextNode.next = head.left;
                        nextNode = head.left;
                    }
                }
                // 接着判断右节点是否是null，【注意】我们不能直接指向右节点，因为可能为null，这样如果后面还有有效节点就会有问题
                if(head.right != null){
                    // 有可能还没有头结点
                    if(nextHead == null){
                        nextHead = head.right;
                        nextNode = nextHead;
                    }else{
                        nextNode.next = head.right;
                        nextNode = head.right;
                    }
                }
                head = head.next;
            }
            // 当前层的单链表遍历完成，继续下一层
            head = nextHead;
//            // 重置nextHead和nextNode
//            nextHead = null;
//            nextNode = null;
        }
        return root;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
