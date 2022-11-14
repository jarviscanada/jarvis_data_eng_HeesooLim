package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

/**
 * Ticket URL: https://www.notion.so/jarvisdev/Two-Sum-3109a002351545f3b693812ac2274d5e
 */
public class TwoSum {
  public int[] twoSumTwoLoop(int[] nums, int target) {
    int[] result = new int[2];
    for (int i = 0; i < nums.length; i++) {
      for (int j = i+1; j < nums.length; j++) {
        if (nums[i] + nums[j] == target) {
          result[0] = i;
          result[1] = j;
        }
      }
    }
    return result;
  }

  public int[] twoSumLinear(int[] nums, int target) {
    int[] result = new int[2];
    Map<Integer, Integer> map = new HashMap<>();

    for (int i = 0; i < nums.length; i++) {
      int restNumber = target - nums[i];
      if (map.containsKey(nums[i])) {
        result[0] = map.get(nums[i]);
        result[1] = i;
        break;
      }
      map.put(restNumber, i);
    }
    return result;
  }


}
