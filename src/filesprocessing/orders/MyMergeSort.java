package filesprocessing.orders;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * singleton generic class for sorting arrays, in "mergeSort" method.
 */
public class MyMergeSort {

    /**
     * create instance of MyMergeSort
     */
    private static final MyMergeSort mergeSortInstance = new MyMergeSort();

    /**
     * private constructor
     */
    private MyMergeSort(){}

    /**
     * @return class's instance.
     */
    public static MyMergeSort getInstance() {return mergeSortInstance; }

    /**
     * sort an generic array by merge sort.
     * @param array given array of generic object.
     * @param comperator given comparator of generic object
     * @param <T> type of generic object.
     */
    public <T> void sort(T[] array, Comparator<T> comperator){
        mergeSort(array, comperator, 0, array.length - 1);

    }

    /**
     * main func of mergeSort sorting algorithm.
     * @param array array given array of generic object.
     * @param comperator given comparator of the same generic object
     * @param start first index of subarray
     * @param end last index of subarray
     * @param <T> type of generic object.
     */
    private <T> void mergeSort(T[] array, Comparator<T> comperator, int start, int end) {
        if (end > start){
            int middle = start + (end - start) / 2;
            mergeSort(array, comperator, start, middle);
            mergeSort(array, comperator, middle + 1, end);
            merge(array, comperator, start, middle, end);
        }
    }

    /**
     * merge two subarrays of the original array.
     * @param array  array array given array of generic object.
     * @param comperator given comparator of the same generic object
     * @param start first index of subarray
     * @param middle middle index of subarray
     * @param end last index of subarray
     * @param <T> type of generic object
     */
    private <T> void merge(T[] array, Comparator<T> comperator, int start, int middle, int end) {
        List<T> leftSubArr = new ArrayList<T>();
        List<T> rightSubArr = new ArrayList<T>();
        int dist1 = middle - start + 1;
        int dist2 = end - middle;
        int left = 0, right = 0, counter = start;

        for (int i = 0; i < dist1; i++){
            leftSubArr.add(array[start + i]);
        }
        for (int j = 0; j < dist2; j++){
            rightSubArr.add(array[middle + 1 + j]);
        }
        while (left < dist1 || right < dist2){
            if (left == dist1){
                array[counter] = rightSubArr.get(right);
                right++;
            }
            else if (right == dist2 || comperator.compare(leftSubArr.get(left), rightSubArr.get(right)) < 0){
                array[counter] = leftSubArr.get(left);
                left++;
            }
            else {
                array[counter] = rightSubArr.get(right);
                right++;
            }
            counter++;
        }
    }
}
