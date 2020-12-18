package com.hudson.codes;//给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
//
// 示例 1: 
//
// 输入: 1->1->2
//输出: 1->2
// 
//
// 示例 2: 
//
// 输入: 1->1->2->3->3
//输出: 1->2->3 
// Related Topics 链表 
// 👍 439 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution83 {
    // 时间 55.16%，空间69.17%
    // 首先，目标是排序列表
    // 因此按顺序遍历
    public static Main.ListNode deleteDuplicates(Main.ListNode head) {
        if(head == null) return null;
        Main.ListNode validNode = head;
        Main.ListNode node = head.next;
        while(node != null){
//            if(node.val == validNode.val){
//                // 当前这个位置可以去掉
//            }else{
//                // 当前这个位置可以续上
//                validNode.next = node;
//            }
            if(node.val != validNode.val){
                validNode.next = node;
                validNode = node;
            }
            node = node.next;
        }
        validNode.next = null;// 最后尾部要清空
        return head;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
