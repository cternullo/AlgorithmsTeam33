import java.util.Arrays;
import static java.lang.Math.*;

public class Sorter
{
    // Traditional Counting Sort
    public static int[] countingSort(int[] a)
    {
        // Trivial case
        if (a.length <= 1) return a;


        // Variables
        int n = a.length;
        int k = a[0];


        // Finding max element
        for (int i = 0; i < n; i++)
        {
            if (a[i] <= 0)
                throw new IllegalArgumentException("All elements must be positive");

            if (a[i] > k)
                k = a[i];
        }


        // Creating our auxiliary array c[]
        int[] c = new int[k + 1];


        // Counting the occurrences of each value
        for (int i = 0; i < n; i++)
        {
            c[ a[i] ]++;
        }


        // Cumulating the array c[]
        for (int i = 1; i <= k; i++)
        {
            c[i] += c[i - 1];
        }


        // Creating our output array b[]
        int[] b = new int[n];


        // Building b[]
        for (int i = 0; i < n; i++)
        {
            // The integer e is just the element of a[] we are concerned about
            int e = a[i];

            // The value stored at c[e-1] is the final position of e
            b[  c[e-1]  ] = e;

            // Incrementing the value in c[] for the next time we come across an e
            c[e-1]++;
        }


        // Our sorted array is stored in b[]
        return b;
    }



    // ARU Counting Sort
    public static void aruSort(int[] a)
    {
        // Trivial case
        if (a.length <= 1) return;


        // Variables
        int n = a.length;
        int k = a[0];



        // Finding max element
        for (int i = 0; i < n; i++)
        {
            if (a[i] <= 0)
                throw new IllegalArgumentException("All elements must be positive");

            if (a[i] > k)
                k = a[i];
        }


        // Defining m
        int m = (int) ceil(sqrt(k));


        // Auxiliary arrays
        int[] b = new int[n];
        int[] q = new int[m + 1];
        int[] r = new int[m + 1];


        // Building the quotient array, and the remainder array
        for (int i = 0; i < n; i++)
        {
            // The integer e is just the element of a[] we are concerned about
            int e = a[i];

            q[ e/m ]++;
            r[ e%m ]++;
        }


        // Cumulating the quotient array, and the remainder array
        for (int i = 1; i <= m; i++)
        {
            q[i] += q[i-1];
            r[i] += r[i-1];
        }


        // Modifying the remainder array
        for (int i = n - 1; i >= 0; i--)
        {
            // The integer e is just the element of a[] we are concerned about
            int e = a[i];

            // The value in r[] is decremented
            r[ e%m ]--;

            // We use the new value of r[] as an index for b[]
            b[ r[e%m] ] = e;
        }


        // Modifying the quotient array
        for (int i = n - 1; i >= 0; i--)
        {
            // The integer e is just the element of b[] we are concerned about
            int e = b[i];

            // The value in q[] is decremented
            q[ e/m ]--;

            // We use the new value of q[] as an index for a[]
            a[ q[e/m] ] = e;
        }
    }



    // Radix Sort
    public static int[] radixSort(int[] a)
    {
        // Trivial case
        if (a.length <= 1) return a;


        // Variables
        int n = a.length;
        int k = a[0];


        // Finding max element
        for (int i = 0; i < n; i++)
        {
            if (a[i] <= 0)
                throw new IllegalArgumentException("All elements must be positive");

            if (a[i] > k)
                k = a[i];
        }


        // Starting at 1, then 10, then 100, etc. until num of digits in k
        for (int i = 1; k / i != 0; i *= 10)
            a = radHelp(a, i);


        return a;
    }



    // Merge Sort
    public static void mergeSort(int[] a)
    {
        if (a.length <= 1) return;

        if (a.length == 2)
        {
            if (a[0] > a[1]) swap(a, 0, 1);
            return;
        }


        int[] firstHalf = Arrays.copyOfRange(a, 0, a.length / 2);
        int[] secondHalf = Arrays.copyOfRange(a, a.length / 2, a.length);

        mergeSort(firstHalf);
        mergeSort(secondHalf);

        int[] m = merge(firstHalf, secondHalf);
        System.arraycopy(m, 0, a, 0, m.length);
    }



    // Quick Sort
    public static void quickSort(int[] a)
    {
        if (a.length <= 1) return;

        qsHelper(a, 0, a.length - 1);
    }





    // Helper method for Quick Sort
    private static void qsHelper(int[] a, int lo, int hi)
    {
        if (lo >= hi) return;

        int pivot = a[hi];
        int i = lo - 1;

        for (int j = lo; j < hi; j++)
        {
            if (a[j] < pivot)
            {
                i++;
                swap(a, i, j);
            }
        }

        i++;
        swap(a, i, hi);

        qsHelper(a, lo, i - 1);
        qsHelper(a, i + 1, hi);
    }



    // Helper method for Radix Sort
    public static int[] radHelp(int[] a, int e)
    {
        // This array will be our 'bins' per digit
        int[] count = new int[10];
        int n = a.length;


        // increment the bin based on the digit in position e
        for (int i = 0; i < n; i++)
            count[ (a[i] / e)  %  10 ]++;


        // Cumulate the array
        for (int i = 1; i < 10; i++)
            count[i] += count[i - 1];

        // The array we will return as our result
        int[] b = new int[n];


        // Building the output array
        for (int i = n - 1; i >= 0; i--)
        {
            count[ (a[i] / e)  %  10 ]--;
            b[count[ (a[i] / e)  %  10 ]] = a[i];
        }

        return b;
    }



    // Helper method for Merge Sort
    public static int[] merge(int[] arr1, int[] arr2)
    {
        int[] merged = Arrays.copyOf(arr1, arr1.length + arr2.length);
        int i = 0, j = 0;

        for (int k = 0; k < merged.length; k++)
        {

            if (i == arr1.length)
            {
                merged[k] = arr2[j];
                j++;
            }
            else if (j == arr2.length)
            {
                merged[k] = arr1[i];
                i++;
            }
            else if (arr1[i]< arr2[j])
            {
                merged[k] = arr1[i];
                i++;
            }
            else
            {
                merged[k] = arr2[j];
                j++;
            }
        }

        return merged;
    }



    // Helper method for swapping
    public static void swap(int[] arr, int first, int second)
    {
        if(first != second)
        {
            int temp = arr[first];
            arr[first] = arr[second];
            arr[second] = temp;
        }
    }
}
