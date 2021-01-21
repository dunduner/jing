package com.jing.study.node_sort;

/**
 * @author ning11.zhang
 * @Description:
 * @date 2021/1/4
 */
//利用3个指针来进行node逆序
public class Node_Reverse {

    public static void main(String[] args) {

        Node2 head = new Node2(0);
        Node2 node1 = new Node2(1);
        Node2 node2 = new Node2(2);
        Node2 node3 = new Node2(3);
        Node2 node4 = new Node2(44);


        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        System.out.println("逆序前的node顺序");
        Node2 cur = head;
        while (null != cur) {
            System.out.print(cur.data + "   ");
            cur = cur.next;
        }

        System.out.println("===================");
        System.out.println("逆序后的node顺序");
        cur = reverse2(head);
        while (null != cur) {
            System.out.print(cur.data + "   ");
            cur = cur.next;
        }

    }

    public static Node2 reverse2(Node2 head) {
        if (head == null) {   //
            return null;
        }
        if (head.next == null) {
            return head;
        }
        Node2 prev = null;
        Node2 cur = head;
        Node2 newhead = null;
        while (cur != null) {
            Node2 next = cur.next;
            if (next == null) {
                newhead = cur;
            }
            cur.next = prev;
            prev = cur;
            cur = next;

        }
        return newhead;
    }


}

class Node2 {
    Node2 next;
    int data;

    public Node2() {
    }

    public Node2(Node2 next, int data) {
        this.next = next;
        this.data = data;
    }

    public Node2(int data) {
        this.data = data;
    }
}


