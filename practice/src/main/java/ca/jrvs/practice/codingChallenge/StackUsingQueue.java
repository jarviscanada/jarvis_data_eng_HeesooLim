package ca.jrvs.practice.codingChallenge;

import java.util.Deque;
import java.util.LinkedList;

public class StackUsingQueue {
  Deque<Integer> queue;
  public StackUsingQueue() {
    queue = new LinkedList<>();
  }

  // only push to back, peek/pop from front, size and is empty operations are valid.
  public void push(int x) {
    queue.add(x);
  }

  public int pop() {
    int len = queue.size();
    int[] items = new int[len];
    for (int i = 0; i < len - 1; i++) {
      items[i] = queue.poll();
    }
    int popItem = queue.poll();
    for (int i = 0; i < len - 1; i++) {
      queue.add(items[i]);
    }
    return popItem;
  }

  public int top() {
    int len = queue.size();
    int[] items = new int[len];
    for (int i = 0; i < len; i++) {
      items[i] = queue.poll();
    }
    for (int i = 0; i < len; i++) {
      queue.add(items[i]);
    }
    return items[len - 1];
  }

  public boolean empty() {
    return queue.size() == 0;
  }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
