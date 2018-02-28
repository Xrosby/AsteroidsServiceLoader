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
public class ShapePart implements EntityPart{
    
    private float[] shapex;
    private float[] shapey;
    private float radius;
    private EntitySize size;
    
    public ShapePart(float[] shapex, float[] shapey, float radius, EntitySize size) {
        this.shapex = shapex;
        this.shapey = shapey;
        this.radius = radius;
        this.size = size;
    }
    public ShapePart(float[] shapex, float[] shapey, float radius) {
        this.shapex = shapex;
        this.shapey = shapey;
        this.radius = radius;
    }

    public EntitySize getSize() {
        return size;
    }

    public void setSize(EntitySize size) {
        this.size = size;
    }

    public float getRadius() {
        return radius;
    }

    public float[] getShapex() {
        return shapex;
    }

    public float[] getShapey() {
        return shapey;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setShapex(float[] shapex) {
        this.shapex = shapex;
    }

    public void setShapey(float[] shapey) {
        this.shapey = shapey;
    }
    
}
