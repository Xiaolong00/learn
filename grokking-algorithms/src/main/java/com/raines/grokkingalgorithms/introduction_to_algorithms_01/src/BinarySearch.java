package com.raines.grokkingalgorithms.introduction_to_algorithms_01.src;

/**
 * 二分法
 */
public class BinarySearch {

    // has to return boxed integer in order to comfort to interface defined in the book
    private static Integer binarySearch(int[] list, int item) {
        int low = 0;
        int high = list.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int guess = list[mid];
            if (guess == item) {
                return mid;
            }
            if (guess > item) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return null;
    }

    public static void main(String[] args) {
        int[] myList = {1, 3, 5, 7, 9};

        System.out.println(binarySearch(myList, 3)); // 1
        System.out.println(binarySearch(myList, -1)); // null
    }
}
