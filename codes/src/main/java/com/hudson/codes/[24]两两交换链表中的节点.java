package com.hudson.codes;//给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
//
// 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。 
//
// 
//
// 示例 1： 
//
// 
//输入：head = [1,2,3,4]
//输出：[2,1,4,3]
// 
//
// 示例 2： 
//
// 
//输入：head = []
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：head = [1]
//输出：[1]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点的数目在范围 [0, 100] 内 
// 0 <= Node.val <= 100 
// 
// Related Topics 链表 
// 👍 751 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution24 {

    public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // 下面代码时间超过100%的用户，空间超过72.53%的用户
    // 很明显使用递归解决
    // 但是需要注意的是：
    // 1.每次递归返回值是什么 (返回当前两个节点调整后的结果)
    // 2.递归过程要做什么  （调整两个节点）
    // 3.终止的条件是什么  (没有两个节点了)
    public static ListNode swapPairs(ListNode head) {
        if(head == null) return null;
        ListNode second = head.next;
        if(second == null) return head;
        // 交换两个节点
        ListNode next = second.next;
        second.next = head;
        head.next = swapPairs(next);
        return second;// 返回的是调整后的头结点
    }
}
//leetcode submit region end(Prohibit modification and deletion)
