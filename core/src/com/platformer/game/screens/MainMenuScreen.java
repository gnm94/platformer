/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.platformer.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 *
 * @author Yuumu
 */
public class MainMenuScreen implements Screen, InputProcessor {
    
    Game _game;
    
    OrthographicCamera camera;
    Texture background;
    SpriteBatch batch;
    
    Texture demoTexture;
    Texture networkTexture;
    Texture exitTexture;
    
    Sprite demoButtonSprite;
    Sprite networkButtonSprite;
    Sprite exitButtonSprite;
    
    
    public MainMenuScreen(Game game){
        _game = game;
        
        background = new Texture("Background.png");
        
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        
        demoTexture = new Texture(Gdx.files.internal("Start.png"));
        exitTexture = new Texture(Gdx.files.internal("Exit.png"));
        
        demoButtonSprite = new Sprite(demoTexture);
        exitButtonSprite = new Sprite(exitTexture);
        
        
        demoButtonSprite.setPosition(275f, 350f);
        
        
        exitButtonSprite.setPosition(275f, 150f);
        
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        camera.update();
        
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float f) {
     
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        batch.draw(background, 0, 0);
        demoButtonSprite.draw(batch);
        exitButtonSprite.draw(batch);
        batch.end();
        
    }

    @Override
    public void resize(int i, int i1) {
        
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void dispose() {
        
    }

    // The part below deal with inputs on this screen
    
    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        int x = i;
        int y = Gdx.graphics.getHeight() - i1;
        
        System.out.println(x + "," + y);
        if ( demoButtonSprite.getBoundingRectangle().contains(x, y) ){
            _game.setScreen(new DemoScreen(_game));
        }
       
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
    
}
