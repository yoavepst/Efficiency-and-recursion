
/**
 * This class contains the answers for Ex14.
 *
 * @author (Yoav Epstein)
 * @version (11/1/21)
 */
public class EffNRec
{
    private static boolean isEmpty (int [] y)
    {
        return y == null || y.length == 0;
    }

    /**
     * Finding the single integer number, who breaks the rule, in an array of integer numbers which appears twice in a row. <br>
     * <br> Complexity: 
     * <br> Time complexity: n/(2^x) = 3 --> x = log2(n/3) --> T(n) = log2(n/3) + 3 = O(log n) + O(1) = O(log n)
     * <br> Memort complexity: T(n) = 1 + 1 + 1 + 1 = O(1) <br> 
     * @param a The array in which it finds singular number
     * @return The singular number
     */
    public static int findSingle (int [] a)
    {
        int high = a.length-1;
        int low = 0;
        int mid = (high + low)/2;
        int single;
        while (high - low > 2){// Stopping when there are three suspiciouss indexes
            if (mid % 2 == 0){// The mid index value will be the pivot for which case should be checked 
                if (a[mid] == a[mid+1]){// Then the single's index must be between the mid and the high 
                    low = mid;
                    mid = (high + low)/2;
                }
                else{// Then the single's index must be between the low and the mid
                    high = mid + 1;
                    mid = (high + low)/2;                    
                }
            }
            else{
                if (a[mid] == a[mid+1]){// Then the single's index must be between the low and the mid 
                    high = mid + 1;
                    mid = (high + low)/2;
                }
                else{// Then the single's index must be between the mid and the high 
                    low = mid;
                    mid = (high + low)/2;                    
                }    

            }
        }
        //Cheaking the relations between the three suspiciouss indexes and finding the single
        if (a[low] == a[mid])
            single = high;
        else if (a[mid] == a[high])
            single = low;
        else
            single = mid;

        return a[single];
    }

    /**
     * Finding the minimal (set power related) sub array, that its summery equals more then a given integer number.
     * <br> Will be valid under the following: All the numbers in the array are zero or positive.
     * <br> Complexity:
     * <br> Time complexity: T(n) = n + n = 2n = O(n)
     * <br> Memort complexity: T(n) = 1 + 1 + 1 + 1 = O(1) 
     * @param arr The array in which it finds the minimal sub array
     * @param x The integer number to be equaled to
     * @return The smallest sub array
     */
    public static int smallestSubSum(int arr[], int x)
    {
        int temp = -1;
        if (!isEmpty(arr)){//Taking care the empty array case            
            int right = 0;
            int left = 0;
            int sub = arr[left];
            while (right <= arr.length-1 && left <= arr.length-1 && temp != 1)
            {                    
                if (right != arr.length-1 && sub <= x){//Extaned the "right" index of the subarray, until the "edge" 
                    right++;
                    sub += arr[right];

                    if (sub > x){
                        if(temp != -1)
                            temp = Math.min (temp, right - left + 1);
                        else
                            temp = right - left + 1;

                        sub -= arr[left];
                        left++;//Next iteration will check if the subarray is cable as solution                        
                    }
                }

                else{//The corrent sub is less or equal to x or "right" is on the "edge" of the array
                    if (sub > x){//"right" is on the "edge" of the array case 
                        if(temp != -1)
                            temp = Math.min (temp, right - left + 1);
                        else
                            temp = right - left + 1;
                    }

                    sub -= arr[left];
                    left++;//Next iteration will check if the subarray is cable as solution                        
                }
            }
        }
        return temp;
    }

    /**
     * Finding and printing all the possible solution of a summery of 3 given numbers (between 1-10) and a given integer number.  
     * @param num The integer number to be summerize to
     * @return The number of possible solutions
     */
    public static int solutions(int num)
    {
        if (num < 3 || num > 30)
            return 0;
        return solutions (num, 0, "");
    }

    private static int solutions(int num, int count, String str)
    {
        if (num == 0 && count == 3){
            System.out.println(str.substring(0,str.length()-1));
            return 1;
        }
        if (num < 0 || count > 3)
            return 0;

        return solutions (num-1, count+1, str + 1 + "+") + 
        solutions (num-2, count+1, str + 2 + "+") +
        solutions (num-3, count+1, str + 3 + "+") +
        solutions (num-4, count+1, str + 4 + "+") +
        solutions (num-5, count+1, str + 5 + "+") +
        solutions (num-6, count+1, str + 6 + "+") +
        solutions (num-7, count+1, str + 7 + "+") +
        solutions (num-8, count+1, str + 8 + "+") +
        solutions (num-9, count+1, str + 9 + "+") +
        solutions (num-10, count+1, str + 10 + "+") ;
    }

    /**
     * Finding how many true zones are in a given matrix.
     * <br> Will be valid under the following:
     * <br>The matrix is a square matrix.
     * <br>True zone is vaild when true cells are located either above/under or left/right to each other.
     * @param mat The matrix in which it finds true zones
     * @return The number of true zones
     */
    public static int cntTrueReg (boolean[][]mat)
    {
        if (isEmpty(mat))
            return 0;
        return cntTrueReg (mat, 0, 0, 0);
    }

    private static int cntTrueReg (boolean[][]mat, int row, int col, int count)
    {
        if (!isLegal(mat, row, col)) 
            return 0;

        if (mat[row][col]){
            makeFalse(mat, row, col);
            count++;
        }

        if (lastCell(mat, row, col))//All Matrix was searched
            return count;

        if (col == mat.length-1)
            return cntTrueReg (mat, row+1, 0, count);
        return cntTrueReg (mat, row, col+1, count);

    }

    private static boolean isEmpty (boolean [][] y)
    {
        return y == null || y[0].length == 0;
    }

    private static boolean isLegal (boolean[][]mat, int row, int col)//Valid in a square matrix only
    {
        return row < mat.length && col < mat.length && row >=0 && col >= 0;
    }

    private static boolean lastCell (boolean[][]mat, int row, int col)//Valid in a square matrix only
    {
        return row == mat.length-1 && col == mat.length-1;
    }

    private static void makeFalse (boolean[][]mat, int row, int col)//Valid in a square matrix only
    {
        if (!isLegal(mat, row, col))
            return;
        if (!mat[row][col])
            return;
        mat[row][col] = false;
        makeFalse (mat, row, col+1);
        makeFalse(mat, row, col-1);
        makeFalse(mat, row+1, col);
        makeFalse (mat, row-1, col);

    }
}
