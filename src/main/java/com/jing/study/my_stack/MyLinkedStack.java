package com.jing.study.my_stack;

/**
 * @author zhangning
 * @date 2020/11/2
 */
public class MyLinkedStack<E> implements Stack<E> {
    Node<E> head;
    int size;

    private static class Node<E> {
        E data;
        Node<E> next;

        public Node(E e) {
            data = e;
        }
    }

    @Override
    public E push(E e) {
        Node<E> new_node = new Node<>(e);
        if (head == null) {
            head = new_node;
        } else {
            //把老node存起来
            Node<E> old_head = head;
            head = new_node;
            head.next = old_head;
        }
        size++;
        return e;
    }

    //    弹出来
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

    //查栈顶
    @Override
    public E peek() {

        return head==null ?null :head.data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void print() {

    }

    public static void main(String[] args) {
        List_Stack<String> stringList_stack = new List_Stack<>();

        stringList_stack.push("a1");
        stringList_stack.push("a2");
        stringList_stack.push("a3");
        stringList_stack.push("a4");
        stringList_stack.push("a5");
        stringList_stack.push("a6");

//        stringList_stack.print();
        String pop = stringList_stack.pop();
        System.out.println("弹出元素："+pop);
        System.out.println("弹出元素："+stringList_stack.pop());
//        System.out.println("弹出后打印");
//        stringList_stack.print();
        System.out.println("查看栈顶");
        System.out.println(stringList_stack.peek());

    }
}
