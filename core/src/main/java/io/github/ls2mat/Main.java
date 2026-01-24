package io.github.ls2mat;

import com.badlogic.gdx.Game;

import java.io.Console;
import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {


    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
