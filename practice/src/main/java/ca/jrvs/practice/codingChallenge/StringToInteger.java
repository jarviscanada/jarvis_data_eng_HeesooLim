package ca.jrvs.practice.codingChallenge;

public class StringToInteger {
    public static int myAtoi(String s) {
      boolean isNegative = false;
      boolean isSymbolOver = false;
      boolean isNumberStarted = false;
      long result = 0;
      char[] arr = s.toCharArray();

      for (char c : arr) {
        // if it's not a number
        if (c < 48 || c > 57) {
          if ((c == '-' || c == '+') && !isNumberStarted){

            if (isSymbolOver){
              break;
            }
            if (c == '-'){
              isNegative = true;
            }
            isSymbolOver = true;
            continue;
          }
          else if (c == ' ' && !isNumberStarted && !isSymbolOver) {
            continue;
          }
          break;
        }
        else {
          isNumberStarted = true;
          long newResult = (result * 10) + (c - 48);
          if (newResult < result){
            break;
          }
          result = newResult;
        }
      }
      if (isNegative && result > 0){
        result = result * -1;
      }
      result = Math.max(Integer.MIN_VALUE, Math.min(Integer.MAX_VALUE, result));

      return (int)result;
    }
}
