/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntitySize;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShapePart;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.EventType;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Random;

/**
 *
 * @author MadsNorby
 */
public class AsteroidControlSystem implements IEntityProcessingService {

    Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {

        for (Entity asteroid : world.getEntities(Asteroid.class)) {

            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);

            movingPart.setUp(true);

            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);
            
            System.out.println(asteroid.toString() + " POSITION: \n"
                    + "X: " + positionPart.getX() + "\n" +
                    "Y: " + positionPart.getY());
            

            for (Event e : gameData.getEvents()) {
                if (e.getEventType() == EventType.SPLITASTEROID) {
                    splitAsteroid(e.getSource(), world, gameData);
                    gameData.removeEvent(e);
                    System.out.println("SPLIT ASTEROID");
                }
            }

            updateShape(asteroid);
        }

    }

    private void splitAsteroid(Entity asteroid, World world, GameData gameData) {

        ShapePart shapePart = asteroid.getPart(ShapePart.class);
        EntitySize size = shapePart.getSize();

        Entity firstNewAsteroid;
        Entity secondNewAsteroid;

        if (size == EntitySize.LARGE) {
            firstNewAsteroid = createAsteroid(asteroid, gameData, EntitySize.MEDIUM);
            secondNewAsteroid = createAsteroid(asteroid, gameData, EntitySize.MEDIUM);
            world.addEntity(firstNewAsteroid);
            world.addEntity(secondNewAsteroid);
            world.removeEntity(asteroid);
        } else if (size == EntitySize.MEDIUM) {
            firstNewAsteroid = createAsteroid(asteroid, gameData, EntitySize.SMALL);
            secondNewAsteroid = createAsteroid(asteroid, gameData, EntitySize.SMALL);
            world.addEntity(firstNewAsteroid);
            world.addEntity(secondNewAsteroid);
            world.removeEntity(asteroid);
        }
        world.removeEntity(asteroid);

    }

    private Entity createAsteroid(Entity parentAsteroid, GameData gameData, EntitySize size) {

        //Create movingpart of the asteroid
        float deacceleration = 0;
        float acceleration = 10000;
        float maxSpeed = 60;
        float rotationSpeed = 10;

        float[] shapex = new float[8];
        float[] shapey = new float[8];

        float radius = 15;

        if (size == EntitySize.MEDIUM) {
            radius = 8;
        } else if (size == EntitySize.SMALL) {
            radius = 4;
        }

        //Create random start position

        PositionPart parentPosition = parentAsteroid.getPart(PositionPart.class);

  
        //Set asteroid start position
        float x = parentPosition.getX();
        float y = parentPosition.getY();
        float radians = 3.1415f * random.nextFloat();

        //Set the shape of the asteroid
        shapex[0] = (float) (x - 20);
        shapey[0] = (float) (y + 3);

        shapex[1] = (float) (x - 15);
        shapey[1] = (float) (y + 8);

        shapex[2] = (float) (x - 5);
        shapey[2] = (float) (y + 20);

        shapex[3] = (float) (x + 15);
        shapey[3] = (float) (y + 8);

        shapex[4] = (float) (x + 20);
        shapey[4] = (float) (y - 3);

        shapex[5] = (float) (x + 15);
        shapey[5] = (float) (y - 8);

        shapex[6] = (float) (x + 5);
        shapey[6] = (float) (y - 20);

        shapex[7] = (float) (x - 15);
        shapey[7] = (float) (y - 8);

        Entity asteroid = new Asteroid();
        asteroid.setRadius(radius);
        asteroid.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        asteroid.add(new PositionPart(x, y, radians));
        asteroid.add(new ShapePart(shapex, shapey, radius, size));
        asteroid.setType(EntityType.ASTEROID);

        return asteroid;

    }

    private void updateShape(Entity entity) {

        ShapePart shapePart = entity.getPart(ShapePart.class);
        PositionPart positionPart = entity.getPart(PositionPart.class);

        float[] shapex = shapePart.getShapex();
        float[] shapey = shapePart.getShapey();

        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        EntitySize size = shapePart.getSize();

        if (size == EntitySize.LARGE) {

            shapex[0] = (float) (x - 20);
            shapey[0] = (float) (y + 3);

            shapex[1] = (float) (x - 15);
            shapey[1] = (float) (y + 8);

            shapex[2] = (float) (x - 5);
            shapey[2] = (float) (y + 20);

            shapex[3] = (float) (x + 15);
            shapey[3] = (float) (y + 8);

            shapex[4] = (float) (x + 20);
            shapey[4] = (float) (y - 3);

            shapex[5] = (float) (x + 15);
            shapey[5] = (float) (y - 8);

            shapex[6] = (float) (x + 5);
            shapey[6] = (float) (y - 20);

            shapex[7] = (float) (x - 15);
            shapey[7] = (float) (y - 8);

        } else if (size == EntitySize.MEDIUM) {

            shapex[0] = (float) (x - 20 / 2);
            shapey[0] = (float) (y + 3 / 2);

            shapex[1] = (float) (x - 15 / 2);
            shapey[1] = (float) (y + 8 / 2);

            shapex[2] = (float) (x - 5 / 2);
            shapey[2] = (float) (y + 20 / 2);

            shapex[3] = (float) (x + 15 / 2);
            shapey[3] = (float) (y + 8 / 2);

            shapex[4] = (float) (x + 20 / 2);
            shapey[4] = (float) (y - 3 / 2);

            shapex[5] = (float) (x + 15 / 2);
            shapey[5] = (float) (y - 8 / 2);

            shapex[6] = (float) (x + 5 / 2);
            shapey[6] = (float) (y - 20 / 2);

            shapex[7] = (float) (x - 15 / 2);
            shapey[7] = (float) (y - 8 / 2);

        } else {

            shapex[0] = (float) (x - (20 / 2) / 2);
            shapey[0] = (float) (y + (3 / 2) / 2);

            shapex[1] = (float) (x - (15 / 2) / 2);
            shapey[1] = (float) (y + (8 / 2) / 2);

            shapex[2] = (float) (x - (5 / 2) / 2);
            shapey[2] = (float) (y + (20 / 2) / 2);

            shapex[3] = (float) (x + (15 / 2) / 2);
            shapey[3] = (float) (y + (8 / 2) / 2);

            shapex[4] = (float) (x + (20 / 2) / 2);
            shapey[4] = (float) (y - (3 / 2) / 2);

            shapex[5] = (float) (x + (15 / 2) / 2);
            shapey[5] = (float) (y - (8 / 2) / 2);

            shapex[6] = (float) (x + (5 / 2) / 2);
            shapey[6] = (float) (y - (20 / 2) / 2);

            shapex[7] = (float) (x - (15 / 2) / 2);
            shapey[7] = (float) (y - (8 / 2) / 2);
        }

        shapePart.setShapex(shapex);
        shapePart.setShapey(shapey);

    }

}
