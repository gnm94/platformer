/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.platformer.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.sun.javafx.scene.traversal.Direction;
import hitbox.HitboxRectangle;

/**
 *
 * @author Yuumu
 */
public class PlayableCharacter {
    
    Texture moveSheet;
    Texture attackSheet;
    SpriteBatch spriteBatch;
    
    CharacterState currentState;
    
    public Animation<TextureRegion> walkAnimation;
    public Animation<TextureRegion> walkAnimationFlip;
    public Animation<TextureRegion> standbyAnimation;
    public Animation<TextureRegion> attackAnimation;
    
    CharacterDirection direction;

    int charX = 100;
    int charY = 250;

    public int getCharX() {
        return charX;
    }

    public void setCharX(int charX) {
        this.charX = charX;
    }

    public int getCharY() {
        return charY;
    }

    public void setCharY(int charY) {
        this.charY = charY;
    }
    
    public CharacterState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(CharacterState currentState) {
        this.currentState = currentState;
    }
    
    public PlayableCharacter(){
        moveSheet = new Texture(Gdx.files.internal("S-Movement.png"));
        attackSheet = new Texture(Gdx.files.internal("S-Attack.png"));
        
        TextureRegion[][] textureRegion = TextureRegion.split(moveSheet, 64, 64);
        TextureRegion[][] attTextureRegion = TextureRegion.split(attackSheet, 128, 64);
        
        TextureRegion[] walkTextures = new TextureRegion[6];
        for ( int i = 0 ; i < 6 ; i++ ){
            walkTextures[i] = textureRegion[0][i];
        }
        standbyAnimation = new Animation<TextureRegion>(0.1f, walkTextures);
        standbyAnimation.setPlayMode(Animation.PlayMode.LOOP);
        
        
        TextureRegion[] walkAnimationTextures = new TextureRegion[24];
        for ( int i = 0 ; i < 8 ; i++ ){
            walkAnimationTextures[i] = textureRegion[1][i];
            walkAnimationTextures[i+8] = textureRegion[2][i];
            walkAnimationTextures[i+16] = textureRegion[3][i];
        }
        
        walkAnimation = new Animation<TextureRegion>(0.1f, walkAnimationTextures);
        walkAnimation.setPlayMode(Animation.PlayMode.LOOP);
        
        TextureRegion[] attAnimationTexture = new TextureRegion[4];
        for ( int i = 0 ; i < 4 ; i++ ){
            attAnimationTexture[i] = attTextureRegion[0][i];
        }
        attackAnimation = new Animation<TextureRegion>(0.3f, attAnimationTexture);
        
    }

    public CharacterDirection getDirection() {
        return direction;
    }

    public void setDirection(CharacterDirection direction) {
        this.direction = direction;
    }
    
    public TextureRegion getKeyFrame(float deltatime){
        TextureRegion currentFrame = standbyAnimation.getKeyFrame(deltatime);
        
        if ( currentState == CharacterState.WALKING){
            
            currentFrame = walkAnimation.getKeyFrame(deltatime);
            
            if ( currentFrame.isFlipX() && direction == CharacterDirection.RIGHT){
                currentFrame.flip(true, false);
            }
            if ( !currentFrame.isFlipX() && direction == CharacterDirection.LEFT){
                currentFrame.flip(true, false);
            }
            
        } else if ( currentState == CharacterState.ATTACKING){
            
            currentFrame = attackAnimation.getKeyFrame(deltatime);
            
            if ( currentFrame.isFlipX() && direction == CharacterDirection.RIGHT){
                currentFrame.flip(true, false);
            }
            if ( !currentFrame.isFlipX() && direction == CharacterDirection.LEFT){
                currentFrame.flip(true, false);
            }
        }
        return currentFrame;
    }
    
    public HitboxRectangle getHitbox(){
        if ( direction == CharacterDirection.RIGHT){
         return new HitboxRectangle(charX+50, charY, 100, 100);
        } else {
         return new HitboxRectangle(charX-50, charY, 100, 100);   
        }
    }
    
    public enum CharacterState{
        STANDBY,
        WALKING,
        ATTACKING,
        JUMPING_UP,
        JUMPING_DOWN;
    }
    
    public enum CharacterDirection{
        LEFT,
        RIGHT;
    }
    
}
