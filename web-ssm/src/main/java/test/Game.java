package test;

/**
 * @version 1.0
 * @类名: Game.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-6-14
 */
class Game {
    Game(int i) {
        System.out.println("Game constructor" + i);
    }

    Game() {

    }
}

class BoardGame extends Game {
    BoardGame(int i) {
        super(i);
        System.out.println("BoardGame constructor" + i);
    }

    BoardGame() {

    }
}

class Chess extends BoardGame {
    Chess() {
        super(11);
        System.out.println("Chess constructor");
    }

    public static void main(String[] args) {
        Chess x = new Chess();
    }
}
