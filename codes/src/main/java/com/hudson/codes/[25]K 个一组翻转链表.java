package com.hudson.codes;//给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
//
// k 是一个正整数，它的值小于或等于链表的长度。 
//
// 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。 
//
// 
//
// 示例： 
//
// 给你这个链表：1->2->3->4->5 
//
// 当 k = 2 时，应当返回: 2->1->4->3->5 
//
// 当 k = 3 时，应当返回: 3->2->1->4->5 
//
// 
//
// 说明： 
//
// 
// 你的算法只能使用常数的额外空间。 
// 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。 
// 
// Related Topics 链表 
// 👍 807 👎 0


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
class Solution25 {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // 首先，分析如何把链表翻转
    // 最快的办法就是定义三个指针，不断地后移并不断翻转
    // 接下来，需要一个数记录当前已经翻转的数量，并保证到达最多的n组k个节点
    // 问题在于，我们一旦翻转了链表，就不能因为末尾没有k个节点又恢复回去，
    // 我们事先也并不知道链表有多少个节点，因此为了避免这样的问题发生，
    // 我们需要先遍历一遍链表，以计算链表节点的个数

    // 另一种思路是，不事先计算节点个数，而是以k为单位，把链表拆成
    // 多个子链表，如果在链表拆解过程中，最后一个链表还没到k个就为null了，就排除这个链表
    // 接下来拆解的长度为k的链表做链表翻转操作，最后链接起来，并添加最后多余的链表
    // 整体上效率基本与上面的差不多，因此拆解过程一次遍历，各个子链表翻转也有一次遍历。

    // 我们这里使用的是第一种方案，通过，时间超过100%用户，空间超过47.95%的用户
    public static ListNode reverseKGroup(ListNode head, int k) {
        if(head == null) return null;
        // 计算链表长度
        int nodeCount = 0;
        ListNode node = head;
        while(node != null){
            nodeCount ++;
            node = node.next;
        }
        int endNodeIndex =  nodeCount / k * k - 1;// 计算出翻转的最后一个节点下标
        // 开始翻转链表， 翻转链表需要操作三个节点，分别是pre, cur, next
        return reverse(head, k, 0, endNodeIndex);
    }

    private static ListNode reverse(ListNode head, int k, int curIndex, int endIndex){
        if(curIndex > endIndex){
            return head;// 说明已经到了超过k整数倍的位置，直接返回这部分的头结点
        }
        ListNode pre = null;
        ListNode cur = head;

        while((curIndex + 1) % k != 0){
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
            curIndex ++;
        }
        // 完成最后一个的翻转
        ListNode nextKHead = cur.next;
        cur.next = pre;
        // 链接起后面翻转的结果【注意：这时候head其实是翻转链表的尾结点】
        head.next = reverse(nextKHead, k, curIndex + 1, endIndex);
        return cur;// 返回翻转后的头结点
    }
}
//leetcode submit region end(Prohibit modification and deletion)
