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
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;

/**
 *
 * @author MadsNorby
 */
public class AsteroidPlugin implements IGamePluginService {

    private Entity asteroid;
    private Random random = new Random();

    @Override
    public void start(GameData gameData, World world) {
        asteroid = createAsteroid(gameData);
        createTenAsteroids(gameData, world);

    }

    private void createTenAsteroids(GameData gameData, World world) {
        for (int i = 0; i < 10; i++) {
            Entity asteroid = createAsteroid(gameData);
            world.addEntity(asteroid);
        }
    }

    private Entity createAsteroid(GameData gameData) {

        //Create movingpart of the asteroid
        float deacceleration = 0;
        float acceleration = 10000;
        float maxSpeed = 60;
        float rotationSpeed = 10;

        float[] shapex = new float[8];
        float[] shapey = new float[8];
        float radius = 15;

        //Create random start position
        int randomX = random.nextInt(gameData.getDisplayWidth());
        int randomY = random.nextInt(gameData.getDisplayHeight());
        float randomRadians = 3.1415f * random.nextFloat();

        //Set asteroid start position
        float x = randomX;  //gameData.getDisplayWidth() / 3;
        float y = randomY;  //gameData.getDisplayHeight() / 3;
        float radians = randomRadians;//3.1415f / 2;

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
        asteroid.add(new ShapePart(shapex, shapey, radius, EntitySize.LARGE));
        asteroid.setType(EntityType.ASTEROID);

        return asteroid;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(asteroid);
    }

}
