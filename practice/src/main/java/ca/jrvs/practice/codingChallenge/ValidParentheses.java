package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ValidParentheses {
  public boolean isValid(String s) {
    Map<Character, Character> map = new HashMap<Character, Character>();
    map.put('(', ')');
    map.put('{', '}');
    map.put('[', ']');
    Stack<Character> stack = new Stack<>();
    char[] chars = s.toCharArray();

    for (char c : chars) {
      if (map.containsKey(c)) {
        stack.push(c);
      }
      else if (!stack.empty()) {
        if (map.get(stack.peek()) == c)
          stack.pop();
        else {
          return false;
        }
      }
      else {
        return false;
      }

    }
    return stack.empty();
  }
}
