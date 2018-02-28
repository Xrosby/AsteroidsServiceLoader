/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author MadsNorby
 */
public class CollisionPart implements EntityPart {
    
    private float x;
    private float y;
    private float radius;
    
    public CollisionPart(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
       PositionPart positionPart = entity.getPart(PositionPart.class);
       
       float newX = positionPart.getX();
       float newY = positionPart.getY();
       
       this.x = newX;
       this.y = newY;
        
    }
    
}
