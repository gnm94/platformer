/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.platformer.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.platformer.game.PlatformerGame;
import com.platformer.game.bullets.Bullet;
import com.platformer.game.bullets.MyBullet;
import com.platformer.game.character.Enemy;
import com.platformer.game.character.PlayableCharacter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yuumu
 */
public class DemoScreen implements Screen, InputProcessor {
    
    Game _game;
    
    SpriteBatch batch;
    OrthographicCamera camera;
    
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    
    PlayableCharacter character;
    Enemy enemy;
    
    Sprite characterSprite;
    
    Rectangle lastRectangle;
    
    List<Bullet> liveBulletInstances;
    
    Texture bulletTexture;
    
    TiledMapTileLayer floorLayer;
    
    float deltaTime;
    
    int[] backgroundLayers = {0,2};
    int[] objectLayers = { 1 };
    
    ShapeRenderer shapeRenderer;
    
    int dx = 0;
    int dy = -1;

    public DemoScreen(Game game){
        _game = game;
        
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        camera.update();
        
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        
        map = new TmxMapLoader().load("Stage.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        
        floorLayer = (TiledMapTileLayer) map.getLayers().get("Floor-Layer");
        
        character = new PlayableCharacter();
        
        Texture zombieTexture = new Texture(Gdx.files.internal("Zombie.png"));
        bulletTexture = new Texture(Gdx.files.internal("Bullet.png"));
        
        liveBulletInstances = new ArrayList<Bullet>();
        
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        
        enemy = new Enemy(zombieTexture);
        enemy.setX(640);
        enemy.setY(64);
        System.out.println("Enemy : " + enemy.getBoundingRectangle().getX() + "," + enemy.getBoundingRectangle().getY());
        
        
    }

    @Override
    public void show() {
        
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float f) {
        deltaTime+= Gdx.graphics.getDeltaTime();
       
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        renderer.setView(camera);
      
        renderer.render(backgroundLayers);
        
        updateCharacter();
        batch.begin();
        batch.draw(character.getKeyFrame(deltaTime), character.getCharX(), character.getCharY());
        enemy.draw(batch);
        
        for (Bullet bullet : liveBulletInstances){
            bullet.updateBullet();
            bullet.draw(batch);
            if ( bullet.getBoundingRectangle().overlaps(enemy.getBoundingRectangle()) ){
                System.out.println("Bullet - Enemy collision confirmed");
                // Remove from list, dispose the bullet.
                
            }
            
        }
        batch.end();
        
        
        renderer.render(objectLayers);

    }
    
    public void updateCharacter(){
        if ( !isCollideBottom(character.getCharY()+dy) ){
            character.setCharY(character.getCharY() + dy);
        } 
        character.setCharX(character.getCharX() + dx);        
        
    }
    
    private boolean isCollideBottom(int newY){
        int gridLocY = newY/32;
        int gridLocX = character.getCharX()/32;
        if (floorLayer.getCell(gridLocX, gridLocY) != null){
            return true;
        }
        return false;
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

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.T){
           PlatformerGame myGame = (PlatformerGame) _game;
           myGame.ChangeScreen();
        }
        if (keycode == Input.Keys.RIGHT )
        {
            dx=2;
            character.setCurrentState(PlayableCharacter.CharacterState.WALKING);
            character.setDirection(PlayableCharacter.CharacterDirection.RIGHT);
        }
        else if ( keycode == Input.Keys.LEFT ){
            dx=-2;
            character.setCurrentState(PlayableCharacter.CharacterState.WALKING);
            character.setDirection(PlayableCharacter.CharacterDirection.LEFT);
        }
        else if ( keycode == Input.Keys.Z ){
            character.setCurrentState(PlayableCharacter.CharacterState.ATTACKING);
            
            lastRectangle = character.getHitbox();
            if ( character.getHitbox().overlaps(enemy.getBoundingRectangle()) ){
                System.out.println("Collision confirmed");
            } else {
                System.out.println(character.getHitbox().getX() + "," + character.getHitbox().getY());
            }
        } else if (keycode == Input.Keys.X ){
            MyBullet newInstance = new MyBullet(bulletTexture);
            newInstance.setX(character.getCharX());
            newInstance.setY(character.getCharY());
            newInstance.setDirection(Bullet.BulletDirection.RIGHT);
            liveBulletInstances.add(newInstance);
        }
        return true;
    }

    @Override
    public boolean keyUp(int i) {
        character.setCurrentState(PlayableCharacter.CharacterState.STANDBY);
        dx=0;
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
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
