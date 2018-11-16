package test.duoxianchenganli;

import java.util.Arrays;
import java.util.List;

/**
 * @version 1.0
 * @类名: Shapes.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-6-8
 */
public class Shapes {
    public static void main(String[] args) {
        List<Shape> shapeList = Arrays.asList(
                new Circle(), new Square(), new Triangle()  // 向上转型为 Shape，此处会丢失原来的具体类型信息！！对于数组而言，它们只是Shape类对象！
        );
        for (Shape shape : shapeList) {
            shape.draw();   // 数组实际上将所有事物都当作Object持有，在取用时会自动将结果转型回声明类型即Shape。
        }
        //shapeList.get(0).drawCircle(); //这里会编译错误：在Shape类中找不到符号drawCircle()，证实了具体类型信息的丢失!!
    }
}
