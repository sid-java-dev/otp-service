package test;

public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9, 11, 13, 15};
        int element = 11;
        int result = binarySearch(arr, element);
        if (result != -1) {
            System.out.println("Element present at index: " + result);
        } else {
            System.out.println("Element not found");
        }
    }

    private static int binarySearch(int[] arr, int element) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2; // Calculate mid within the loop

            if (arr[mid] == element) {
                return mid; // Element found
            } else if (arr[mid] < element) {
                left = mid + 1; // Search in the right half
            } else if (arr[mid] > element) {
                right = mid - 1; // Search in the left half
            }
        }
        return -1; // Element not found
    }
}
