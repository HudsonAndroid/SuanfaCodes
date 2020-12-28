package com.hudson.codes;//给定一个 完美二叉树 ，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下： 
//
// 
//struct Node {
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
// 
//输入：root = [1,2,3,4,5,6,7]
//输出：[1,#,2,3,#,4,5,6,7,#]
//解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个右侧节点，如图 B 所示。序列化的输出按层序遍历排列，同一层节点由 
//next 指针连接，'#' 标志着每一层的结束。
// 
//
// 
//
// 提示： 
//
// 
// 树中节点的数量少于 4096 
// -1000 <= node.val <= 1000 
// 
// Related Topics 树 深度优先搜索 广度优先搜索 
// 👍 363 👎 0


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

import java.util.LinkedList;
import java.util.Queue;

class Solution116 {


//    public static void main(String[] args){
//        Node node = new Node(1);
//        node.left = new Node(2);
//        node.right = new Node(3);
//        node.left.left = new Node(4);
//        node.left.right = new Node(5);
//        node.right.left = new Node(6);
//        node.right.right = new Node(7);
//        Node connect = connect2(node);
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

    // 42.16%  71.13%
    // 看过题意之后，我们大概思路也就清晰了
    // 就是按层遍历，并把每层遍历到的节点链接起来即可
    // 但实际上，题目的进阶要求仅使用常数级额外空间，下面使用了队列，不符合要求
    public static Node connect(Node root) {
        if(root == null) return null;
        int levelCount = 1;
        int nextLevelCount = 0;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        Node last = null;
        while(!queue.isEmpty()){
            Node node = queue.poll();
            // 加入其左右节点
            if(node.left != null){
                queue.offer(node.left);
                nextLevelCount++;
            }
            if(node.right != null){
                queue.offer(node.right);
                nextLevelCount++;
            }
//            if(last == null){
//                last = node;
//            }else{
//                // 首尾链接起来
//                last.next = node;
//                last = node;
//            }
            if(last != null){
                last.next = node;
            }
            last = node;
            levelCount--;
            if(levelCount == 0){
                // 如果当前层没有元素了
                // 把当前的node的next指向null
                node.next = null;
                levelCount = nextLevelCount;
                nextLevelCount = 0;
                last = null;//一层完成后，需要重新开始
            }
        }
        return root;
    }

    // 法2
    // 100%  91.67%
    // 实际上，我们发现，比如第三层的连接，除了第二层的两个根节点
    // 内部可以自己完成链接之外，我们还需要链接第二层根节点的右子节点与第二层第二个根节点的左子节点
    // 而我们如果完成了第二层的链接，第三层也就简单了
    // 实际上，我们可以把第二层当成一个单链表，我们只需要记录单链表的头节点即可
    // 这样下一层开始时，遍历头节点即可
    public static Node connect2(Node root) {
        if(root == null) return null;
        Node head = root;
        // 第一层的root不需要处理（next=null不需要考虑）
        while(head.left != null){ // 因为是完全二叉树，因此只判断left
            Node node = head;// 当前单链表的头节点
            Node nextLevelHead = head.left;// 下一层单链表的头节点
            Node nextLevelNode = null;
            // 遍历head这个单链表
            while(node != null){
                node.left.next = node.right;
                if(nextLevelNode == null){// 还没有头节点，那么当前的right就是当前的尾部，后续要接在这个上面
                    nextLevelNode = node.right;
                }else{// 把当前部分的头部接到前面的尾部
                    nextLevelNode.next = node.left;
                    // 【错误1】未更新尾节点
                    nextLevelNode = node.right;
                }
                node = node.next;
            }
            head = nextLevelHead;
        }
        return root;
    }


}
//leetcode submit region end(Prohibit modification and deletion)
