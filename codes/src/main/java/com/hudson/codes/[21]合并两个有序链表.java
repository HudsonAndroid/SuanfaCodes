package com.hudson.codes;//将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
//
// 
//
// 示例： 
//
// 输入：1->2->4, 1->3->4
//输出：1->1->2->3->4->4
// 
// Related Topics 链表 
// 👍 1412 👎 0


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
class Solution21 {

    public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // oh,yeah, 时间复杂度超过100%用户
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 这个判断返回null很关键,可以确定尾部的位置
        if(l1 == null && l2 == null) return null;
        if(l1 == null) return l2;
        if(l2 == null) return l1;
        ListNode node;// 表示当前节点
        // 确定当前的节点，并返回给上一个节点的next
        if(l1.val < l2.val){
            node = l1;
            node.next = mergeTwoLists(l1.next, l2);
        }else{
            node = l2;
            node.next = mergeTwoLists(l1, l2.next);
        }
        return node;
    }

}
//leetcode submit region end(Prohibit modification and deletion)
