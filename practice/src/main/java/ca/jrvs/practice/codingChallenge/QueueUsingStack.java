package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

public class QueueUsingStack {
  Stack<Integer> stack;
  Stack<Integer> reverseStack;

  public QueueUsingStack() {
    stack = new Stack<>();
    reverseStack = new Stack<>();
  }

  public void push(int x) {
    while (!reverseStack.empty()) {
      stack.push(reverseStack.pop());
    }
    stack.push(x);
  }

  public int pop() {
      while (!stack.empty()) {
        reverseStack.push(stack.pop());
      }
    return reverseStack.pop();
  }

  public int peek() {
    while (!stack.empty()) {
      reverseStack.push(stack.pop());
    }
    return reverseStack.peek();
  }

  public boolean empty() {
    return reverseStack.empty() && stack.empty();
  }
}
