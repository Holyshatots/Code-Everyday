package com.mcaffee.ocdsim.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;

/**
 * Created by Mitch on 4/9/2016.
 */
public class Die extends Rectangle{
    // How fast the die will change faces when rolling
    private final static long SHUFFLING_FACE_CHANGE_TIMEOUT = 9 * 10000000;
    // Minimum number of face changes before the next number can be reached
    private final static long MIN_FACE_CHANGES = 10;

    // The current numerical value of the face of the die
    private int currentFace;
    // The last time that the face was changed
    private long lastFaceChange;
    // The number of face changes
    private int faceChanges;
    // The next die number to be rolled
    private int nextFace;
    // The random variable
    private Random rand;

    public Die() {
        rand = new Random(TimeUtils.nanoTime());
        currentFace = 1;
        faceChanges = 0;
        this.width = 64;
        this.height = 64;
        generateNextFace();
    }

    public int getCurrentFace() {
        return currentFace;
    }

    /**
     * Set the die to hover above the given player
     * @param player
     */
    public void setToPlayer(Player player) {
        this.x = player.x;
        this.y = player.y - 10;
    }

    public void setLastFaceChange(long currentTime) {
        lastFaceChange = currentTime;
    }

    /**
     * Returns true if the die face reached it's next face
     * @param currentTime
     * @return
     */
    public boolean checkFaceChange(long currentTime) {
        if(currentTime - lastFaceChange > SHUFFLING_FACE_CHANGE_TIMEOUT) {
            setLastFaceChange(currentTime);
            // Set the correct next face value for a roll
            if(currentFace == 6) {
                currentFace = 1;
            } else {
                currentFace++;
            }


             if(faceChanges > MIN_FACE_CHANGES && currentFace == nextFace) {
                 // This is the number that we want to display
                generateNextFace();
                faceChanges = 0;
                return true;
            } else {
                faceChanges++;
            }
        }
        return false;
    }

    /**
     * Randomly generate the next face value of the die
     */
    private void generateNextFace() {
        nextFace = rand.nextInt(5) + 1;
    }
}
