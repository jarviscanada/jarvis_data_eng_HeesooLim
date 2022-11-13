package ca.jrvs.practice.codingChallenge;

import ca.jrvs.practice.dataStructure.map.JMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Ticket URL: https://www.notion.so/jarvisdev/Fibonacci-Number-Climbing-Stairs-0ed8fea2579a45e88f714bf947d32e42
 */
public class FibonacciAndClimbingStairs {
    public int fibonacciRecursion(int n) {
        if (n <= 1)
            return n;
        return fibonacciRecursion(n - 1) + fibonacciRecursion(n - 2);
    }

    public int fibonacciDp(int n) {
        if (n <= 1)
            return n;

        int[] fibonacci = new int[n + 1];
        fibonacci[0] = 0;
        fibonacci[1] = 1;

        for (int i = 2; i <= n; i++) {
            fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
        }

        return fibonacci[n];
    }

    public int climbingStairsRecursion(int n) {
        if (n <= 1)
            return 1;
        return climbingStairsRecursion(n - 1) + climbingStairsRecursion(n - 2);
    }

    public int climbStairsDp(int n) {
        if (n <= 1)
            return 1;

        int[] stairs = new int[n + 1];
        stairs[0] = 1;
        stairs[1] = 1;

        for (int i = 2; i <= n; i++) {
            stairs[i] = stairs[i - 1] + stairs[i - 2];
        }

        return stairs[n];
    }
}
