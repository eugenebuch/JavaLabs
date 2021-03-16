package com.company.common;

import com.company.Figure;

public class Comparator implements Comparable {
    public Figure isLarger(Figure f1, Figure f2) {
        return (f1.countSquare()>f2.countSquare()) ? f1 : f2;
    }
}
