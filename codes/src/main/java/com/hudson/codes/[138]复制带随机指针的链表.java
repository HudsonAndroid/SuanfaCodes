package com.hudson.codes;//给定一个链表，每个节点包含一个额外增加的随机指针，该指针可以指向链表中的任何节点或空节点。
//
// 要求返回这个链表的 深拷贝。 
//
// 我们用一个由 n 个节点组成的链表来表示输入/输出中的链表。每个节点用一个 [val, random_index] 表示： 
//
// 
// val：一个表示 Node.val 的整数。 
// random_index：随机指针指向的节点索引（范围从 0 到 n-1）；如果不指向任何节点，则为 null 。 
// 
//
// 
//
// 示例 1： 
//
// 
//
// 输入：head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
//输出：[[7,null],[13,0],[11,4],[10,2],[1,0]]
// 
//
// 示例 2： 
//
// 
//
// 输入：head = [[1,1],[2,1]]
//输出：[[1,1],[2,1]]
// 
//
// 示例 3： 
//
// 
//
// 输入：head = [[3,null],[3,0],[3,null]]
//输出：[[3,null],[3,0],[3,null]]
// 
//
// 示例 4： 
//
// 输入：head = []
//输出：[]
//解释：给定的链表为空（空指针），因此返回 null。
// 
//
// 
//
// 提示： 
//
// 
// -10000 <= Node.val <= 10000 
// Node.random 为空（null）或指向链表中的节点。 
// 节点数目不超过 1000 。 
// 
// Related Topics 哈希表 链表 
// 👍 458 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

class Solution138 {

//    public static void main(String[] args){
//        Node head = new Node(7);
//        Node second = new Node(13);
//        head.next = second;
//        Node third = new Node(11);
//        head.next.next = third;
//        Node forth = new Node(10);
//        head.next.next.next = forth;
//        Node fifth = new Node(1);
//        head.next.next.next.next = fifth;
//        head.random = null;
//        second.random = head;
//        forth.random = third;
//        fifth.random = head;
//        Node copy = copyRandomList(head);
//        printNode(copy);
//        System.out.println("原始");
//        printNode(head);
//    }

//    private static void printNode(Node node){
//        if(node == null) return ;
//        while(node != null){
//            System.out.print(" cur: "+node.val);
//            if(node.random != null){
//                System.out.print( ", random:"+node.random.val);
//            }
//            System.out.println();
//            node = node.next;
//        }
//    }

    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
    
    
    // 100%  78.96%
    // 复制包含随机指针的链表
    // 由于我们需要保证链表中元素的指向，
    // 而其中又包含了随机指针，因为需要一个结构帮助我们
    // 记住节点的位置。而这个位置，我们可以依赖原有链表
    // 所以，我们在原有链表每个节点的后面增加一个临时节点
    // 当做是前一个节点的复制后的节点，这样把前一个节点的情况
    // 复制到后一个节点上。
    // 最后断成两个链表即可
    public static Node copyRandomList(Node head) {
        if(head == null) return null;
        Node cur = head;
        Node copy = null;
        // 先在每个链表节点的后面增加一个临时节点
        // 因为随机指向的位置不确定，因此需要先确定整个链表后决定
        while(cur != null){
            copy = new Node(cur.val);
            Node next = cur.next;
            cur.next = copy;
            // 把复制指向原有的下一个位置
            copy.next = next;
            cur = next;
        }
        // 复制完链表后，再遍历一次，确定随机指向的位置
        // 同时断开节点  【错误，不能这个时候断开，因为有可能后面的节点指向前面的节点】
        cur = head;
        while(cur != null){
            copy = cur.next;
            // 复制节点应该指向被复制节点random的下一个
            // 【错误1】random节点可能是null
            if(cur.random != null){
                copy.random = cur.random.next;
            }
            cur = copy.next;
        }
        cur = head;
        Node copyHead = null;
        Node preOld = null;
        Node preNew = null;
        // 【注意,容易出错】单独成一步
        while(cur != null){
            copy = cur.next;
            if(copyHead == null){
                copyHead = copy;
            }else{
                preNew.next = copy;
            }
            if(preOld != null){
                preOld.next = cur;
            }
            preOld = cur;
            preNew = copy;
            cur = copy.next;
        }
        // 【错误2】如果题目要求不能把原来的链表变掉的话，我们需要确保原始链表的尾结点是指向null的
        preOld.next = null;
        return copyHead;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
