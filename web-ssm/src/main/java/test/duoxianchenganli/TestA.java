package test.duoxianchenganli;

/**
 * @version 1.0
 * @类名: TestA.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-6-5
 */
public class TestA {
    public static void main(String[] args) {
//        List<Integer> a = new ArrayList<Integer>();
//        List<Integer> b = new ArrayList<>();
//        a.add(21);
//        a.add(22);
//        System.arraycopy(a, 0, a, 1, 1);

        int[] fun = {0, 1, 2, 3, 4, 5, 6};
        System.arraycopy(fun, 0, fun, 3, 3);
        for (int a : fun) {
            System.out.print(a);
        }

    }
}
