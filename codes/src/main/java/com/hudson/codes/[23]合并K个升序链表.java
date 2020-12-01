package com.hudson.codes;//给你一个链表数组，每个链表都已经按升序排列。
//
// 请你将所有链表合并到一个升序链表中，返回合并后的链表。 
//
// 
//
// 示例 1： 
//
// 输入：lists = [[1,4,5],[1,3,4],[2,6]]
//输出：[1,1,2,3,4,4,5,6]
//解释：链表数组如下：
//[
//  1->4->5,
//  1->3->4,
//  2->6
//]
//将它们合并到一个有序链表中得到。
//1->1->2->3->4->4->5->6
// 
//
// 示例 2： 
//
// 输入：lists = []
//输出：[]
// 
//
// 示例 3： 
//
// 输入：lists = [[]]
//输出：[]
// 
//
// 
//
// 提示： 
//
// 
// k == lists.length 
// 0 <= k <= 10^4 
// 0 <= lists[i].length <= 500 
// -10^4 <= lists[i][j] <= 10^4 
// lists[i] 按 升序 排列 
// lists[i].length 的总和不超过 10^4 
// 
// Related Topics 堆 链表 分治算法 
// 👍 1026 👎 0


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
class Solution23 {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }


    // 类似题【更简单的题】见21题 即，每次都找合并链表的下一个节点
    // 思路与上题类似，通过，时间超过6.8%的用户，空间超过 46.52%的用户
    public static ListNode mergeKLists(ListNode[] lists) {
        if(lists == null || lists.length == 0) return null;
        // 最终的结束条件是有所有链表都是null的情况下，就说明已经到了合并的尾结点了 【这个判断返回null很关键,可以确定尾部的位置】
        boolean isAllNull = true;
        for (ListNode list : lists) {
            if(list != null){
                isAllNull = false;
                break;
            }
        }
        if(isAllNull) return null;
        ListNode currentNode = null;
        ListNode oldMinNode = null;
        int oldMinIndex = 0;
        for (int i = 0; i < lists.length; i++) {
            ListNode item = lists[i];
            if(item == null) continue; // skip empty link
            if(currentNode == null){
                currentNode = item;
                // 错误1：认为currentNode为null（未初始化）一定是由第一个元素list[0]初始化，错误，如果第一个链表为null呢
                lists[i] = item.next;
                oldMinNode = item;
                oldMinIndex = i;
            }else{
                if(currentNode.val > item.val){
                    currentNode = item;
                    // 当前位置的链表需要往后移动一位，以便后续比较
                    lists[i] = item.next;
                    // 恢复之前的
                    lists[oldMinIndex] = oldMinNode;
                    oldMinNode = item;
                    oldMinIndex = i;
                }
            }
        }
        currentNode.next = mergeKLists(lists);
        return currentNode;
    }


    // 多说一句，在leetCode上，有种解法是两两合并，而且居然效率比上面的高，这。。。思路差不多啊

}
//leetcode submit region end(Prohibit modification and deletion)
