package com.hudson.codes;//反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
//
// 说明: 
//1 ≤ m ≤ n ≤ 链表长度。 
//
// 示例: 
//
// 输入: 1->2->3->4->5->NULL, m = 2, n = 4
//输出: 1->4->3->2->5->NULL 
// Related Topics 链表 
// 👍 605 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution92 {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // 时间100%,空间：87.54%
    public static ListNode reverseBetween(ListNode head, int m, int n) {
        if(head == null) return null;
        if(m == n) return head;
        int curIndex = 1;
        ListNode pre = null;
        ListNode combinePre = null;
        ListNode combineNext = null;
        ListNode combineHead = null;
        ListNode combineTail = null;
        ListNode newHead = head;
        ListNode node = head;
        while(node != null){
            if(curIndex == m){
                combinePre = pre;
                combineTail = node;// 会变成翻转部分的尾部
            }
            if(curIndex == n){
                combineNext = node.next;// 这里与*处配合，会把尾节点的next置为null
                combineHead = node;// 会变成翻转部分的头部
            }
            if(curIndex > n){
                break;
//                // 需要重新拼接
//                if(combinePre == null){
//                    // 是从第一个开始的
//                    newHead = combineHead;
//                }else{
//                    combinePre.next = combineHead;
//                }
//                combineTail.next = combineNext;// *
            }

            if(curIndex > m && curIndex <= n){
                // 需要翻转
                ListNode next = node.next;
                node.next = pre;
                pre = node;
                node = next;
                curIndex ++;
                continue;
            }
            pre = node;
            node = node.next;
            curIndex ++;
        }
        // 如果curIndex并没有超过n的情况下，即翻转到了尾部的情况，那么需要我们跳出循环后拼接
        // 为了避免重复操作，我们在上面代码中curIndex > n时，跳出链表遍历
        if(combinePre == null){
            // 是从第一个开始的
            newHead = combineHead;
        }else{
            combinePre.next = combineHead;
        }
        // m和n都在链表长度范围内，因此不会出现空指针
        combineTail.next = combineNext;// *
        return newHead;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
