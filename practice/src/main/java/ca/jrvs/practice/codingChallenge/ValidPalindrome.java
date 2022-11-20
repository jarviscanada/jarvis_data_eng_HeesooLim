package ca.jrvs.practice.codingChallenge;

public class ValidPalindrome {
  public boolean isPalindrome(String s) {
    int startIndex = 0;
    int endIndex = s.length() - 1;

    while (startIndex <= endIndex) {
      char start = s.charAt(startIndex);
      char end = s.charAt(endIndex);
      if (!Character.isLetterOrDigit(start)) {
        startIndex++;
        continue;
      }
      if (!Character.isLetterOrDigit(end)) {
        endIndex--;
        continue;
      }
      if (Character.toLowerCase(start) != Character.toLowerCase(end))
        return false;

      startIndex++;
      endIndex--;
    }
    return true;
  }
}
