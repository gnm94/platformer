/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.platformer.game.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author SI
 */
public abstract class Bullet extends Sprite{

    int dx;
    
    public Bullet(Texture texture){
        super(texture);
        dx = 0;
    }
    
    public void updateBullet(){
        this.setX(this.getX()+dx);
    }
    
    public void setDirection(BulletDirection direction){
        if ( direction == BulletDirection.LEFT){
            dx = -2;
        } else {
            dx = 2;
        }
    }
    
    public enum BulletDirection{
    LEFT,
    RIGHT;
    }
}


