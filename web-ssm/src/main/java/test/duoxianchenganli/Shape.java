package test.duoxianchenganli;

/**
 * @version 1.0
 * @类名: Shape.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-6-8
 */
abstract class Shape {
    void draw() {
        System.out.println(this + ".draw()");
    }

    abstract public String toString();  //要求子类需要实现 toString()
}

class Circle extends Shape {
    @Override
    public String toString() {
        return "Circle";
    }

    public void drawCircle() {
    }
}

class Square extends Shape {
    @Override
    public String toString() {
        return "Square";
    }
}

class Triangle extends Shape {
    @Override
    public String toString() {
        return "Triangle";
    }
}


