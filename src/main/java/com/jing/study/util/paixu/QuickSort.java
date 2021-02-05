package com.jing.study.util.paixu;

/**
 * @author zhangning
 * @date 2020/10/14
 * 快速排序
 */
public class QuickSort {
    public static void quickSort(int[] arr, int begin, int end) {
        int i, j,
                temp, //temp就是基准位
                save_jiaohuan;
        if (begin > end) {
            return;
        }
        i = begin;
        j = end;

        temp = arr[begin];
        while (i < j) {
            //先看右边，依次往左递减,找到小于基数的
            while (arr[j] >= temp && i < j) {
                j--;
            }
            //再看左边，依次往右递增，找到大于基数的
            while (arr[i] <= temp && i < j) {
                i++;
            }
            //如果满足条件则交换
            if (i < j) {
                save_jiaohuan = arr[j];
                arr[j] = arr[i];
                arr[i] = save_jiaohuan;
            }
        }
        //最后将基准为与i和j相等位置的数字交换
        arr[begin] = arr[i];
        arr[i] = temp;
        //递归调用左半数组
        quickSort(arr, begin, j - 1);
        //递归调用右半数组
        quickSort(arr, j + 1, end);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{22, 7, 88, 9, 99, 1, 7, 2, 3, 6};
        quickSort(arr, 0, arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
