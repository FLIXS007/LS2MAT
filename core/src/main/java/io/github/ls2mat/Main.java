package io.github.ls2mat;

import com.badlogic.gdx.Game;

import java.io.Console;
import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    private int [] randomDance = new int[4];

    private void randomD(int [] randomDance){
      for (int i = 0 ; i < 4 ; i++ ){
          Random rand = new Random();
          System.out.println(randomDance.length);
          int a = rand.nextInt(0, 4);
          randomDance[i] = a;
      }
    }

    @Override
    public void create() {
        randomD(randomDance);
        setScreen(new DanseuseScreen(this, randomDance));
    }
}
