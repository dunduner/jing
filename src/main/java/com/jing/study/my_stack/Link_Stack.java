package com.jing.study.my_stack;

/**
 * @author zhangning
 * @date 2020/11/2
 */
public class Link_Stack<E> implements Stack<E> {

    private Node<E> head;
    private int size;


    private static class Node<E> {
        E data;
        Node<E> next;

        public Node(E e) {
            data = e;
        }
    }


    @Override
    public E push(E e) {
        Node<E> node = new Node<>(e);
        if (head == null) {
            head = node;
        } else {
            Node<E> n = head;
            head = node;
            head.next = n;
        }
        size++;
        return e;
    }

    @Override
    public E pop() {
        if (head == null) {
            return null;
        }
        E data = head.data;
        head = head.next;
        size--;
        return data;
    }

    @Override
    public E peek() {
        return head == null ? null : head.data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void print() {
        //TODO
        Node<E> pri = head;
        while (pri!=null){
            System.out.println(pri.data);
            pri = pri.next;
        }
    }



    public static void main(String[] args) {
        Link_Stack<String> linkStack = new Link_Stack<>();

        linkStack.push("a1");
        linkStack.push("a2");
        linkStack.push("a3");
        linkStack.push("a4");
        linkStack.push("a5");
        linkStack.push("a6");

//        linkStack.print();
        String pop = linkStack.pop();
        System.out.println("弹出元素："+pop);

        System.out.println("弹出后打印");
        linkStack.print();
        System.out.println("查看栈顶");
        System.out.println(linkStack.peek());
    }
}