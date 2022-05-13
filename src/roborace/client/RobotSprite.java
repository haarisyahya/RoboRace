package roborace.client;

import COSC3P91.graphics.Animation;
import COSC3P91.util.Pair;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import roborace.common.Direction;

import static roborace.client.AnimatedBoard.LOC_SIZE;
import static roborace.client.AnimatedBoard.ROBOT_OFFSET;

public class RobotSprite {
    
    private enum State {Idle, Turning, Moving, Explode, Dead};
    
    private static final float SPEED = 0.2f;
	
    private final String name;
    private Direction direction;
    private float x;
    private float y;
    private State state;
    private Animation currentAnimation;
    private final Animation[] animations;
    private final List<Point> wayPoints;
	
    public RobotSprite(String name, int x, int y, Direction direction, boolean alive) {
        this.name = name;
        this.x = LOC_SIZE*x+ROBOT_OFFSET;
        this.y = LOC_SIZE*y+ROBOT_OFFSET;
        this.direction = direction;
        state = State.Idle;
        animations = ImageAndAnimationFactory.getInstance().createRobotAnimations(name);
        currentAnimation = getIdleAnimation(direction);
        currentAnimation.start();
        wayPoints = new ArrayList<Point>();
    }

    public String getName() {
        return name;
    }
    
    public Direction getDirection() {
        return direction;
    }
    
    public boolean isAlive() {
        return state != State.Dead;
    }

    public void revitalize(Pair<Point,Direction> startConfig) {
        this.x = LOC_SIZE*startConfig.getFirst().x+ROBOT_OFFSET;
        this.y = LOC_SIZE*startConfig.getFirst().y+ROBOT_OFFSET;
        this.direction = startConfig.getSecond();
        currentAnimation.stop();
        currentAnimation = getIdleAnimation(direction);
        currentAnimation.start();
        state = State.Idle;
    }
    
    public void destroyed() {
        currentAnimation.stop();
        currentAnimation = getExplodeAnimation(direction);
        currentAnimation.start();
        RoboRaceSoundManager.getInstance().playDestroy();
        state = State.Explode;
    }
    
    public void turn90(boolean clockwise) {
        currentAnimation.stop();
        currentAnimation = getTurn90Animation(direction,clockwise);
        currentAnimation.start();
        RoboRaceSoundManager.getInstance().playDrivingShort();
        direction = direction.rotate90(clockwise);
        state = State.Turning;
    }

    public void turn180() {
        currentAnimation.stop();
        currentAnimation = getTurn180Animation(direction);
        currentAnimation.start();
        RoboRaceSoundManager.getInstance().playDrivingLong();
        direction = direction.rotate180();
        state = State.Turning;
    }

    public void step(Direction direction) {
        currentAnimation.stop();
        currentAnimation = getMoveAnimation(this.direction);
        currentAnimation.start();
        RoboRaceSoundManager.getInstance().playDrivingShort();
        wayPoints.add(getMoveWayPoint(direction));
        state = State.Moving;
    }
    
    public void bump(Direction direction) {
        currentAnimation.stop();
        currentAnimation = getMoveAnimation(this.direction);
        currentAnimation.start();
        RoboRaceSoundManager.getInstance().playBump();
        wayPoints.addAll(getBumpWayPoints(direction));
        state = State.Moving;
    }

    public void update(long elapsedTime) {
        currentAnimation.update(elapsedTime);
        switch (state) {
            case Explode:
                if (!currentAnimation.isRunning()) {
                    currentAnimation.reset();
                    currentAnimation = getDeadAnimation(direction);
                    currentAnimation.start();
                    state = State.Dead;
                    synchronized(this) {
                        notify();
                    }
                };
                break;
            case Turning:
                if (!currentAnimation.isRunning()) {
                    currentAnimation.reset();
                    currentAnimation = getIdleAnimation(direction);
                    currentAnimation.start();
                    state = State.Idle;
                    synchronized(this) {
                        notify();
                    }
                };
                break;
            case Moving:
                Point goal = wayPoints.get(0);
                float rx = goal.x-x;
                float ry = goal.y-y;
                float dist = (float) Math.sqrt(rx*rx+ry*ry);
                float move = SPEED*elapsedTime;
                if (dist <= move) {
                    x = goal.x;
                    y = goal.y;
                    wayPoints.remove(0);
                    if (wayPoints.isEmpty()) {
                        currentAnimation.stop();
                        currentAnimation = getIdleAnimation(direction);
                        currentAnimation.start();
                        state = State.Idle;
                        synchronized(this) {
                            notify();
                        }
                    }
                } else {
                    x += move*(rx/dist);
                    y += move*(ry/dist);
                };
                break;
        }
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(currentAnimation.getImage(),(int) x,(int) y,null);
    }

    public synchronized void waitOnRobot() {
        while (state != State.Idle && state != State.Dead) {
            try {
                wait();
            } catch (InterruptedException ex) {}
        }
    }
    
    private Animation getIdleAnimation(Direction direction) {
        Animation result = null;
        switch (direction) {
            case North:
                result = animations[12];
                break;
            case East:
                result = animations[13];
                break;
            case South:
                result = animations[14];
                break;
            case West:
                result = animations[15];
                break;
        }
        return result;
    }
    
    private Animation getTurn90Animation(Direction direction, Boolean clockwise) {
        Animation result = null;
        switch (direction) {
            case North:
                if (clockwise) {
                    result = animations[0];
                } else {
                    result = animations[4];
                };
                break;
            case East:
                if (clockwise) {
                    result = animations[1];
                } else {
                    result = animations[7];
                };
                break;
            case South:
                if (clockwise) {
                    result = animations[2];
                } else {
                    result = animations[6];
                };
                break;
            case West:
                if (clockwise) {
                    result = animations[3];
                } else {
                    result = animations[5];
                };
                break;
        }
        return result;
    }
    
    private Animation getTurn180Animation(Direction direction) {
        Animation result = null;
        switch (direction) {
            case North:
                result = animations[8];
                break;
            case East:
                result = animations[9];
                break;
            case South:
                result = animations[10];
                break;
            case West:
                result = animations[11];
                break;
        }
        return result;
    }
    
    private Animation getMoveAnimation(Direction direction) {
        Animation result = null;
        switch (direction) {
            case North:
                result = animations[16];
                break;
            case East:
                result = animations[17];
                break;
            case South:
                result = animations[18];
                break;
            case West:
                result = animations[19];
                break;
        }
        return result;
    }
    
    private Animation getExplodeAnimation(Direction direction) {
        Animation result = null;
        switch (direction) {
            case North:
                result = animations[20];
                break;
            case East:
                result = animations[21];
                break;
            case South:
                result = animations[22];
                break;
            case West:
                result = animations[23];
                break;
        }
        return result;
    }
    
    private Animation getDeadAnimation(Direction direction) {
        Animation result = null;
        switch (direction) {
            case North:
                result = animations[24];
                break;
            case East:
                result = animations[25];
                break;
            case South:
                result = animations[26];
                break;
            case West:
                result = animations[27];
                break;
        }
        return result;
    }
    
    private Point getMoveWayPoint(Direction direction) {
        Point p = null;
        switch (direction) {
            case North: 
                p = new Point((int) x,(int) y-LOC_SIZE);
                break;
            case East:
                p = new Point((int) x+LOC_SIZE,(int) y);
                break;
            case South:	
                p = new Point((int) x,(int) y+LOC_SIZE);
                break;
            case West:	
                p = new Point((int) x-LOC_SIZE,(int) y);
                break;
        }
        return p;
    }
    
    private List<Point> getBumpWayPoints(Direction direction) {
        List<Point> result = new ArrayList<Point>(5);
        switch (direction) {
            case North:
                result.add(new Point((int) x,(int) y-7));
                result.add(new Point((int) x,(int) y-2));
                result.add(new Point((int) x,(int) y-7));
                result.add(new Point((int) x,(int) y-2));
                result.add(new Point((int) x,(int) y-7));
                result.add(new Point((int) x,(int) y));
                break;
            case East:
                result.add(new Point((int) x+7,(int) y));
                result.add(new Point((int) x+2,(int) y));
                result.add(new Point((int) x+7,(int) y));
                result.add(new Point((int) x+2,(int) y));
                result.add(new Point((int) x+7,(int) y));
                result.add(new Point((int) x,(int) y));
                break;
            case South:
                result.add(new Point((int) x,(int) y+7));
                result.add(new Point((int) x,(int) y+2));
                result.add(new Point((int) x,(int) y+7));
                result.add(new Point((int) x,(int) y+2));
                result.add(new Point((int) x,(int) y+7));
                result.add(new Point((int) x,(int) y));
                break;
            case West:
                result.add(new Point((int) x-7,(int) y));
                result.add(new Point((int) x-2,(int) y));
                result.add(new Point((int) x-7,(int) y));
                result.add(new Point((int) x-2,(int) y));
                result.add(new Point((int) x-7,(int) y));
                result.add(new Point((int) x,(int) y));
                break;
        }
        return result;
    }
}
    
