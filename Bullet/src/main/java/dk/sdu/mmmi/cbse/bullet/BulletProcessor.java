/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShapePart;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.EventType;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.List;

/**
 *
 * @author MadsNorby
 */
public class BulletProcessor implements IEntityProcessingService {

    @Override
    public void process(GameData gd, World world) {

        List<Event> events = gd.getEvents();

        for (Event event : events) {
            if (event.getEventType() == EventType.BULLET) {
                Entity bullet = createBullet(event);
                gd.removeEvent(event);
                world.addEntity(bullet);
                
            }
            if(event.getEventType() == EventType.ENEMYSHOT) {
                Entity bullet = createBullet(event);
                gd.removeEvent(event);
                 //world.addEntity(bullet);
            }
        }

        for (Entity bullet : world.getEntities(Bullet.class)) {
            LifePart lifePart = bullet.getPart(LifePart.class);

            lifePart.reduceExpiration(1);
            if (lifePart.getExpiration() <= 0) {
                world.removeEntity(bullet);
            }

            MovingPart movingPart = bullet.getPart(MovingPart.class);
            movingPart.setUp(true);
            movingPart.process(gd, bullet);

            PositionPart positionpart = bullet.getPart(PositionPart.class);
            positionpart.process(gd, bullet);

            updateShape(bullet);
        }

    }

    public void updateShape(Entity entity) {

        PositionPart positionPart = entity.getPart(PositionPart.class);
        ShapePart shapePart = entity.getPart(ShapePart.class);

        float[] shapeX = shapePart.getShapex();
        float[] shapeY = shapePart.getShapey();

        float x = positionPart.getX();
        float y = positionPart.getY();

        shapeX[0] = (float) (x - 2);
        shapeY[0] = (float) (y + 2);

        shapeX[1] = (float) (x + 2);
        shapeY[1] = (float) (y + 2);

        shapeX[2] = (float) (x + 2);
        shapeY[2] = (float) (y - 2);

        shapeX[3] = (float) (x - 2);
        shapeY[3] = (float) (y - 2);

        shapePart.setShapex(shapeX);
        shapePart.setShapey(shapeY);


    }

    public Entity createBullet(Event event) {

        PositionPart sourcePosition = event.getSource().getPart(PositionPart.class);;

        float deacceleration = 10;
        float acceleration = 5000;
        float maxSpeed = 500;
        float rotationSpeed = 5;
        float x = sourcePosition.getX();
        float y = sourcePosition.getY();
        float radians = sourcePosition.getRadians();
        
        
        float[] shapeX = new float[4];
        float[] shapeY = new float[4];
        
        //Set bullet shape
        shapeX[0] = (float) (x - 2);
        shapeY[0] = (float) (y + 2);

        shapeX[1] = (float) (x + 2);
        shapeY[1] = (float) (y + 2);

        shapeX[2] = (float) (x + 2);
        shapeY[2] = (float) (y - 2);

        shapeX[3] = (float) (x - 2);
        shapeY[3] = (float) (y - 2);
        

        //Set bullet hitboxsize
        float radius = 1;

        System.out.println("radius: " + radius);

        float expiration = 80;
        int life = 1;

        Entity bullet = new Bullet();
        bullet.setRadius(radius);
        bullet.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        bullet.add(new PositionPart(x, y, radians));
        bullet.add(new LifePart(life, expiration));
        bullet.add(new CollisionPart(x, y, radius));
        bullet.add(new ShapePart(shapeX, shapeY, radius));
        
        bullet.setType(EntityType.BULLET);

        return bullet;
    }

}
