package com.hudson.codes;//给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。 
//
// 示例： 
//
// 给定一个链表: 1->2->3->4->5, 和 n = 2.
//
//当删除了倒数第二个节点后，链表变为 1->2->3->5.
// 
//
// 说明： 
//
// 给定的 n 保证是有效的。 
//
// 进阶： 
//
// 你能尝试使用一趟扫描实现吗？ 
// Related Topics 链表 双指针 
// 👍 1134 👎 0


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
class Solution19 {

    public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // A -> B -> C -> D -> E -> F
    // 假设删除倒数第2个，即E节点，则我们需要找到D节点和F节点（其实我们只需要知道删除节点的上一个节点即可）
    // 我们希望的是，在一次遍历中，有两个指针刚好分别指向D和F节点
    // 我们让一个指针先走n步，例如这里n=2
    // 第一个节点到达C；然后第二指针开始与第一个同时走，第二个达到
    // 尾结点的时候，刚好达到D和F节点
    // 特殊情况： 1) N超出链表长度
    // 2)只有两个节点或1个节点的情况下，不满足上述情况【错误1】【可以合并到3、4中】
    // 3)删除的是尾节点 【错误2】【解决办法，判断一起走的停止条件到第一个指针为null】
    // 4)删除的是头节点  【解决办法：为了避免记录头节点，我们可以在原有链表头部增加一个临时节点，最后返回临时头节点的下一个节点】
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if(head == null) return null;
        ListNode tmpHead = new ListNode(0, head);
        ListNode first = head;
        ListNode second = tmpHead;// 当第一个节点为null的时候（尾结点之后），第二个节点刚好，因为second起点多了一个节点
        for (int i = 0; i < n; i++) {
            first = first.next;
            // 并非如此，这时可能刚好在尾结点之后，因此去除判断
//            if(first == null){
//                return null;// N超出链表长度
//            }
        }
        while(first != null){
            first = first.next;
            second = second.next;
        }
        // 删除节点
        second.next = second.next.next;
        return tmpHead.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
