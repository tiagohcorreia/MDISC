package us17_18;

import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomPriorityQueue {
    private List<Map.Entry<String, Double>> elements;

    public CustomPriorityQueue() {
        this.elements = new ArrayList<>();
    }

    public void add(Map.Entry<String, Double> entry) {
        elements.add(entry);
        elements.sort(Comparator.comparing(Map.Entry::getValue));
    }

    public Map.Entry<String, Double> poll() {
        return elements.remove(0);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }
}
