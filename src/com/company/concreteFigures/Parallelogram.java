package com.company.concreteFigures;

import com.company.Figure;
import com.company.common.Coordinate;
import com.company.common.HeightCountable;

public class Parallelogram extends Figure implements HeightCountable {
    protected final Coordinate pointC, pointD;
    protected final double sideA, sideB;

    public Parallelogram(Coordinate pointA, Coordinate pointB,
                         Coordinate pointC, Coordinate pointD) {
        super(pointA, pointB);
        this.pointC = pointC;
        this.pointD = pointD;

        sideA = countSide(pointA, pointB);
        sideB = countSide(pointB, pointC);
    }

    @Override
    public double countSquare() {
        return sideB * countHeight();
    }

    @Override
    public double countPerimeter() {
        return 2 * (sideA + sideB);
    }

    @Override
    public double countHeight() {
        var angle = Math.acos(Math.pow(sideA, 2) + Math.pow(sideB, 2)
                - Math.pow(countSide(pointB, pointD), 2)/(2 * sideA * sideB));
        return sideA * Math.sin(angle);
    }
}
