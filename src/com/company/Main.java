package com.company;

import com.company.common.Comparator;
import com.company.common.Coordinate;
import com.company.concreteFigures.Circle;
import com.company.concreteFigures.Triangle;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        var circle = new Circle(new Coordinate(0, 0), new Coordinate(1, 1));
        var triangle = new Triangle(new Coordinate(1, 2),
                new Coordinate(3, 2),new Coordinate(0,0));

        var comp = new Comparator();

        var className = comp.isLarger(circle, triangle).getClass().getName().split("\\.");
        System.out.println( className[className.length - 1] + " is larger");
        writeToFile("Triangle square is: " + triangle.countSquare());

    }
    private static void writeToFile(String text) {
        try (var output = new FileOutputStream("output.txt"))
        {
            output.write(text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
