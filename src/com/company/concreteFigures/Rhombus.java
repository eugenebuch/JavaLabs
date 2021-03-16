package com.company.concreteFigures;

import com.company.Figure;
import com.company.common.Coordinate;
import com.company.common.HeightCountable;

public class Rhombus extends Figure implements HeightCountable {
    private final Coordinate pointC, pointD;
    private final double sideA;

    public Rhombus(Coordinate pointA, Coordinate pointB,
                   Coordinate pointC, Coordinate pointD) {
        super(pointA, pointB);
        this.pointC = pointC;
        this.pointD = pointD;

        sideA = countSide(pointA, pointB);
    }


    @Override
    public double countSquare() {
        return sideA * countHeight();
    }

    @Override
    public double countPerimeter() {
        return 4 * sideA;
    }

    @Override
    public double countHeight() {
        var angle = Math.acos(Math.pow(sideA, 2) *
                (2 - countSide(pointB, pointD) / 2));
        return sideA * Math.sin(angle);
    }
}
