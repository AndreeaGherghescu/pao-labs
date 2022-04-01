package com.company.service;

import com.company.library.Library;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Sort implements Comparator<Map.Entry<Integer, Library>> {

    public int compare(Map.Entry<Integer, Library> p, Map.Entry<Integer, Library> q) {
        Library l1 = p.getValue();
        Library l2 = q.getValue();

        if (l1.getRating() > l2.getRating()){
            return -1;
        } else {
            return 1;
        }
    }
}
