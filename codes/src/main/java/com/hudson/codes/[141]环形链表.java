package com.hudson.codes;//给定一个链表，判断链表中是否有环。 
//
// 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的
//位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。 
//
// 如果链表中存在环，则返回 true 。 否则，返回 false 。 
//
// 
//
// 进阶： 
//
// 你能用 O(1)（即，常量）内存解决此问题吗？ 
//
// 
//
// 示例 1： 
//
// 
//
// 输入：head = [3,2,0,-4], pos = 1
//输出：true
//解释：链表中有一个环，其尾部连接到第二个节点。
// 
//
// 示例 2： 
//
// 
//
// 输入：head = [1,2], pos = 0
//输出：true
//解释：链表中有一个环，其尾部连接到第一个节点。
// 
//
// 示例 3： 
//
// 
//
// 输入：head = [1], pos = -1
//输出：false
//解释：链表中没有环。
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点的数目范围是 [0, 104] 
// -105 <= Node.val <= 105 
// pos 为 -1 或者链表中的一个 有效索引 。 
// 
// Related Topics 链表 双指针 
// 👍 889 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
class Solution141 {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    // 100%  86.13%
    // 使用两个快慢指针，一个走一步，一个走两步，
    // 如果有环，那么必然快指针最后会与慢指针在一处（那为什么快的走三步呢？）
    // 那是因为如果走三步的话，相遇难度更大。
    // 如果没有环，那就是一直循环下去可以到达null
    // 如果要计算长度是多少呢？就是从上一次相遇开始到下一次相遇的走得步数
    // 因为快的走的是慢的两倍，当再次相遇时（相遇点永远同一个，思考一下）
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        // 有个疑问，如果自己指向自己可咋整
        // 其实也可以正常检查出来，因为如果有环，不管怎样都必然可以
        while(fast != null){
            slow = slow.next;
            fast = fast.next;
            if(fast == null){
                return false;
            }
            fast = fast.next;
            if(fast == slow){
                return true;
            }
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
