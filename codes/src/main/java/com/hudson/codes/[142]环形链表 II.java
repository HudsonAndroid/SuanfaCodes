package com.hudson.codes;//给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。 
//
// 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。注意，po
//s 仅仅是用于标识环的情况，并不会作为参数传递到函数中。 
//
// 说明：不允许修改给定的链表。 
//
// 进阶： 
//
// 
// 你是否可以使用 O(1) 空间解决此题？ 
// 
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入：head = [3,2,0,-4], pos = 1
//输出：返回索引为 1 的链表节点
//解释：链表中有一个环，其尾部连接到第二个节点。
// 
//
// 示例 2： 
//
// 
//
// 
//输入：head = [1,2], pos = 0
//输出：返回索引为 0 的链表节点
//解释：链表中有一个环，其尾部连接到第一个节点。
// 
//
// 示例 3： 
//
// 
//
// 
//输入：head = [1], pos = -1
//输出：返回 null
//解释：链表中没有环。
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点的数目范围在范围 [0, 104] 内 
// -105 <= Node.val <= 105 
// pos 的值为 -1 或者链表中的一个有效索引 
// 
// Related Topics 链表 双指针 
// 👍 806 👎 0


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
class Solution142 {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    //39.98%  87.77%
    // 找出环的起点
    // 很简单，我们只需要找出环的大小，然后再按照找倒数第k个节点的方式处理
    // 问题在于倒数第k个节点必须要以尾节点作为判断依据。
    // 实际上，如果有环，那么尾节点必然在环的最后，因为这个链表只有一个next
    // 如果形成环，必然是尾部与头部连接起来的
    // 但是该如何找出尾节点？
    // 其实，我们并不一定要找出尾节点
    // 观察示例，按照找倒数第k个节点的方式，
    // 我们让快慢指针在起点，快指针先走k步（之前那个是k-1步，这里因为有环，不会出现null）
    // 然后两个指针同时走，慢指针先走（虽然最先走应该是快指针，但这里前面快指针已经走了）
    // 判断是否相等，不相等再走慢指针。（注意：走慢指针不用判断，因为是快追慢）
    public static ListNode detectCycle(ListNode head) {
        if(head == null) return  null;
        // 找出环的大小
        ListNode slow = head;
        ListNode fast = head;
        boolean hasLoop = false;
        while(fast != null){
            // 后移，什么时候快的追上了慢的就说明有环
            // 快指针先走，因为它走两步，如果它再后面走，必然会与慢的重叠，而我们判断的是快是否追上了慢
            fast = fast.next;
            if(fast == slow){
                // 找到环
                hasLoop = true;
                break;
            }
            // 【错误】这时fast可能是null
            if(fast == null) return null;
            fast = fast.next;
            if(fast == slow){
                // 找到环
                hasLoop = true;
                break;
            }
            // slow再走
            slow = slow.next;
        }
        int loopLen = 0;
        //有环，那么再找出环节点数
        if(hasLoop){
            // 因为当前fast == slow，我们停止fast，让slow一步一步走，直接再次重合
            slow = slow.next;
            loopLen++;
            while(slow != fast){
                loopLen++;
                slow = slow.next;
            }
        }
        if(loopLen == 0){
            return null;
        }
        // 开始找环的入口
        fast = head;
        slow = head;
        // 让fast先走k步
        while(loopLen > 0){
            fast = fast.next;
            loopLen--;
        }
        // 然后开始一起走
        while(fast != slow){
            slow = slow.next;
            fast = fast.next;
        }
        return fast;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
