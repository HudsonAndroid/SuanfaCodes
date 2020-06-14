package com.hudson.codes.removeNonSortRepeatNode;

import java.util.HashSet;

/**
 * Created by Hudson on 2020/6/14.
 */
public class RemoveRepeatNode {

    public static void main(String[] args){
        LinkNode head = new LinkNode(1);
        LinkNode second = new LinkNode(2);
        head.mNext = second;
        LinkNode third = new LinkNode(1);
        second.mNext = third;
        LinkNode fourth = new LinkNode(2);
        third.mNext = fourth;
        fourth.mNext = null;
        printNode(head);
        System.out.println("");
        removeRepeatNode2(head);
        printNode(head);
    }

    //从头到尾部遍历链表，每次遍历到一个节点，和后面所有的节点比较，
    //如果两个节点相等，那么把被比较的节点删除
    private static void removeRepeatNode2(LinkNode head){
        while(head != null){
            //遍历后续所有节点
            LinkNode cur = head.mNext;
            LinkNode pre = head;
            while(cur != null){
                if(cur.mValue == head.mValue){// pre不变
                    pre.mNext = cur.mNext;
                    cur = cur.mNext;
                }else{//pre需要跟着变
                   pre = cur;
                   cur = cur.mNext;
                }
            }
            //继续下一个节点
            head = head.mNext;
        }
    }

    private static void printNode(LinkNode head){
        if(head != null){
            System.out.print(head.mValue + " ");
            printNode(head.mNext);
        }
    }

    private static void removeRepeatNode(LinkNode head){
        if(head != null){
            HashSet<LinkNode> hashSet = new HashSet<>();
            LinkNode pre = head;
            LinkNode cur = head.mNext;
            //由于当前节点从头节点下一个开始，因此记录第一个节点的值
            hashSet.add(pre);
            while(cur != null){
                    if(hashSet.contains(cur)){
                        // 需要移除
                        cur = cur.mNext;
                        pre.mNext = cur;
                    }else{
                        pre = cur;
                        // 如果没有这个节点，那么需要添加该节点
                        hashSet.add(cur);
                        cur = cur.mNext;
                    }
            }
        }
    }

    static class LinkNode{
        int mValue;
        LinkNode mNext;

        public LinkNode(int value) {
            mValue = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof LinkNode)) return false;

            LinkNode linkNode = (LinkNode) o;

            return mValue == linkNode.mValue;
        }

        @Override
        public int hashCode() {
            return mValue;
        }
    }
}
