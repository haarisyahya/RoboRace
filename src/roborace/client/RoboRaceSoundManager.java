package roborace.client;

import COSC3P91.sound.Sound;
import COSC3P91.sound.SoundManager;
import javax.sound.sampled.AudioFormat;


public class RoboRaceSoundManager extends SoundManager {
    private static RoboRaceSoundManager thisInstance;
    public Sound bump, destroy, victory, pusher, crusher, drivingShort, drivingLong;
    public long endOfBump, endOfDestroy, endOfVictory, endOfPusher, endOfCrusher, endOfDrivingShort, endOfDrivingLong;
            
    private RoboRaceSoundManager() {
        super(new AudioFormat(8000,8,1,false,false),4);
        super.setSoundPath("./Sounds&Midi/");
        bump = getSound("bump.wav");
        destroy = getSound("explosion.wav");
        victory = getSound("fanfare.wav");
        pusher = getSound("pusher.wav");
        crusher = getSound("crusher.wav");
        drivingShort = getSound("drivingShort.wav");
        drivingLong = getSound("drivingLong.wav");
        endOfBump = System.currentTimeMillis();
        endOfDestroy = System.currentTimeMillis();
        endOfVictory = System.currentTimeMillis();
        endOfDrivingLong = System.currentTimeMillis();
        endOfDrivingShort = System.currentTimeMillis();
        endOfPusher = System.currentTimeMillis();
        endOfCrusher = System.currentTimeMillis();
        
    }
    
    public static RoboRaceSoundManager getInstance(){
        if(thisInstance == null){
            thisInstance = new RoboRaceSoundManager();
            return thisInstance;
        }
        return thisInstance;
    }
    
    public void playBump(){
       long currentPlayed = System.currentTimeMillis();
       if(currentPlayed-endOfBump > 400){
           thisInstance.play(bump);
           endOfBump = System.currentTimeMillis();
       }
    }
    public void playDestroy(){
        long currentPlayed = System.currentTimeMillis();
        if(currentPlayed - endOfDestroy > 400){
            thisInstance.play(destroy);
            endOfDestroy = System.currentTimeMillis();
        }
    }
    
    public void playVictory(){
        long currentPlayed = System.currentTimeMillis();
        if(currentPlayed  - endOfVictory> 400){
            thisInstance.play(victory);
            endOfVictory = System.currentTimeMillis();
        }
    }
    
    public void playPusher(){
        long currentPlayed = System.currentTimeMillis();
        if(currentPlayed - endOfPusher> 400){
            thisInstance.play(pusher);
            endOfPusher = System.currentTimeMillis();
        }
    }
    
    public void playCrusher(){
        long currentPlayed = System.currentTimeMillis();
        if(currentPlayed - endOfCrusher > 400){
            thisInstance.play(crusher);
            endOfCrusher = System.currentTimeMillis();
        }
    }
    
    public void playDrivingShort(){
         long currentPlayed = System.currentTimeMillis();
         if(currentPlayed - endOfDrivingShort> 400){
               thisInstance.play(drivingShort);
               endOfDrivingShort = System.currentTimeMillis();
           }
    }
    
    public void playDrivingLong(){
         long currentPlayed = System.currentTimeMillis();
         if(currentPlayed- endOfDrivingLong > 400){
             thisInstance.play(drivingLong);
             endOfDrivingLong = System.currentTimeMillis();
         }
    }
}