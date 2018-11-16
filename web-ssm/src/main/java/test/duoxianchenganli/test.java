package test.duoxianchenganli;

/**
 * @version 1.0
 * @类名: test.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-6-7
 */
public class test {
    class Homer {
        void doh(char c) {
            System.out.println("doh(char)");
        }

        void doh(float f) {
            System.out.println("doh(float)");
        }
    }

    class Milhouse {
    }

    class Bart extends Homer {
        @Override
        void doh(float f) {
            super.doh(f);
        }
    }
}
