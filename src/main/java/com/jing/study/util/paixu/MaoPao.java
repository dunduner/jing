package com.jing.study.util.paixu;

/**
 * @author zhangning
 * @date 2020/10/14
 */

public class MaoPao {

    public static void main(String[] args) {

        int[] nums = new int[]{10, 3, 1, 5};
//        int[] nums = new int[]{10,1,3,5,6,22,7,5,9,11,22};
        int temp = 0;
        int size = nums.length - 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - i; j++) {
                if (nums[j] > nums[j+1]) {
                    temp = nums[j];
                    nums[j]= nums[j+1];
                    nums[j+1] = temp;
                }
            }
        }

        System.out.println("从小到大排序后的结果是:");
        for (int i = 0; i < nums.length; i++) {
            System.out.print("  " + nums[i]);
        }
    }
}
