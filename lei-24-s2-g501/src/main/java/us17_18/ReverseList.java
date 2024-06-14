package us17_18;

import java.util.*;
import java.io.*;

public class ReverseList {
    // Method to reverse the list
    public static <T> void reverse(List<T> list) {
        int size = list.size();
        for (int i = 0; i < size / 2; i++) {
            T temp = list.get(i);
            list.set(i, list.get(size - 1 - i));
            list.set(size - 1 - i, temp);
        }
    }

}
