package detectfaceclass;

/**
 * Created by Wheee on 2016/11/5.
 */ 
public class Age {
    private int max;

    private int min;

    private double score;

    public Age() {

    }
    public void setMax(int max){
        this.max = max;
    }
    public int getMax(){
        return this.max;
    }
    public void setMin(int min){
        this.min = min;
    }
    public int getMin(){
        return this.min;
    }
    public void setScore(double score){
        this.score = score;
    }
    public double getScore(){
        return this.score;
    }

}