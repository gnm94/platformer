package com.platformer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platformer.game.screens.DemoScreen;
import com.platformer.game.screens.MainMenuScreen;
import java.util.Properties;

public class PlatformerGame extends Game {
	
    public DemoScreen demoScreen;
    public MainMenuScreen mainMenuScreen;
    
    Game game;

    @Override
    public void create () {
        game = this;
        
        demoScreen = new DemoScreen(game);
        mainMenuScreen = new MainMenuScreen(game);

        game.setScreen(mainMenuScreen);
        
    }

    @Override
    public void render () {
       super.render();
    }

    @Override
    public void dispose () {
         
    }
    
    public void ChangeScreen(){
        this.setScreen(mainMenuScreen);
    }
}
