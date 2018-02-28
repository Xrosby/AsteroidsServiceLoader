/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.collisiondetection;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.EventType;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import javafx.scene.control.Alert;

/**
 *
 * @author MadsNorby
 */
public class CollisionProcessor implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {

                PositionPart entity1HitBox = entity.getPart(PositionPart.class);
                PositionPart entity2HitBox = entity2.getPart(PositionPart.class);

                float x1 = entity1HitBox.getX();
                float y1 = entity1HitBox.getY();
                float radius1 = entity.getRadius();
                float x2 = entity2HitBox.getX();
                float y2 = entity2HitBox.getY();
                float radius2 = entity2.getRadius();

                if (entity != entity2) {
                    if (checkCollision(x1, y1, radius1, x2, y2, radius2)) {
                        gameData.addEvent(new Event(entity, EventType.COLLISION));
                        handleCollision(entity, entity2, world, gameData);
                    }
                }
            }
        }
    }

    private boolean checkCollision(float x1, float y1, float radius1, float x2, float y2, float radius2) {

        double distance = Math.hypot(x1 - x2, y1 - y2);

        if ((radius1 + radius2) > distance) {
            return true;
        }
        return false;

    }

    private void handleCollision(Entity e1, Entity e2, World world, GameData gd) {

        if (e1.getType() == EntityType.BULLET && e2.getType() == EntityType.ASTEROID) {
            gd.addEvent(new Event(e2, EventType.SPLITASTEROID));
            world.removeEntity(e2);
            world.removeEntity(e1);

        } else if (e1.getType() == EntityType.PLAYER && e2.getType() == EntityType.ASTEROID) {

            gd.addEvent(new Event(e1, EventType.GAMEOVER));
            world.removeEntity(e1);

        }

    }

}
