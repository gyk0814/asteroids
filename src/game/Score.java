package game;


/**
 * This class implements the Score that contains basic methods
 * for getting the score and increasing the score.
 */
public class Score {
    
    private int score;
    
    public Score() {
        score = 0;
    }
    
    //Get score method
    public int getScore() {
        return score;
    }
    
    //Increase score method
    public void increaseScore(int increase) {
        score = score + increase;
    }
    

}