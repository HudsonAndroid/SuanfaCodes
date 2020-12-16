package com.hudson.codes;//给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
//
// 示例 1: 
//
// 输入: 1->2->3->4->5->NULL, k = 2
//输出: 4->5->1->2->3->NULL
//解释:
//向右旋转 1 步: 5->1->2->3->4->NULL
//向右旋转 2 步: 4->5->1->2->3->NULL
// 
//
// 示例 2: 
//
// 输入: 0->1->2->NULL, k = 4
//输出: 2->0->1->NULL
//解释:
//向右旋转 1 步: 2->0->1->NULL
//向右旋转 2 步: 1->2->0->NULL
//向右旋转 3 步: 0->1->2->NULL
//向右旋转 4 步: 2->0->1->NULL 
// Related Topics 链表 双指针 
// 👍 380 👎 0


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
class Solution61 {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // 通过，时间超过70.27%，空间超过76.50%
    // 思路很简单，就是把链表变成环，在某个位置切开即可。
    // 因此我们必须先确定链表长度，然后才能找到切开位置
    // 1->2->3->4->5  k = 2
    // 那么先确定长度是len = 5，因此切开的位置实际上是len - k - 1和 len - k中间的位置
    // 值得注意的是有可能k会大于链表长度，因此这种情况下，我们只要对链表长度取余即可
    // 例如 k=8，那么其实相当于k=3
    public static ListNode rotateRight(ListNode head, int k) {
        if(head == null) return null;
        // 先统计出链表的长度
        int len = 0;
        ListNode node = head;
        ListNode tail = null;
        while(node != null){
            len ++;
            if(node.next == null){
                tail = node;// 找到尾节点，以便后面首尾相接
            }
            node = node.next;
        }
        k = k % len;
        // 首尾链接起来形成环
        tail.next = head;
        // 开始找需要切开的位置，我们从最初的头节点往后走 len - k - 1步
        int step = 0;
        while(true){
            if(step >= len - k - 1){
                break;
            }
            head = head.next;
            step++;
        }
        // 找到后，断开，并返回新的头结点
        ListNode newHead = head.next;
        head.next = null;
        return newHead;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
