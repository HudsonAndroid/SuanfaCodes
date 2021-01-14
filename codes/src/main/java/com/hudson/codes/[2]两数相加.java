package com.hudson.codes;//给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
//
// 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。 
//
// 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。 
//
// 示例： 
//
// 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
//输出：7 -> 0 -> 8
//原因：342 + 465 = 807
// 
// Related Topics 链表 数学 
// 👍 5294 👎 0


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
class Solution2 {

    public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // 耗时: 2ms（99.93%），内存38.7MB（86.36%）
    // 错误3：在给l1或l2后移时，没有判断是否为null
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode target = new ListNode();
        ListNode head = target;
        int sum, carry = 0;
        int first, second;
        while(l1 != null || l2 != null){
            // sum two node
            if(l1 == null){
                first = 0;
            }else{
                first = l1.val;
                // not null, we can forward
                l1 = l1.next;
            }
            if(l2 == null){
                second = 0;
            }else{
                second = l2.val;
                l2 = l2.next;
            }
            // 错误1： 没有考虑前面进位问题（没有加carry）
            sum = first + second + carry;
            carry = sum / 10;
            target.val = sum % 10;
            if(l1 != null || l2 != null){
                target.next = new ListNode();
                target = target.next;
            }
        }
        // 错误2:如果最后还有进位，那么需要增加一个节点
        if(carry != 0){
            target.next = new ListNode(carry);
        }
        return head;
    }




















    // 复习
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        if(l1 == null) return l2;
        if(l2 == null) return l1;
        int over = 0;
        ListNode head = null;
        ListNode cur = null;
        while(l1 != null || l2 != null){
            int first = 0;
            if(l1 != null){
                first = l1.val;
                l1 = l1.next;
            }
            int second = 0;
            if(l2 != null){
                second = l2.val;
                l2 = l2.next;
            }
            int sum = first + second + over;
            // 【错误1】应该是>=10
            if(sum >= 10){
                sum %= 10;
                over = 1;
            }else{
                // 【错误2】另外over需要及时重置，避免对后面影响
                over = 0;
            }
            ListNode node = new ListNode(sum);
            if(head == null){
                head = node;
            }else{
                cur.next = node;
            }
            cur = node;
        }
        // 判断是否还有溢出
        if(over == 1){
            cur.next = new ListNode(1);
        }
        return head;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
