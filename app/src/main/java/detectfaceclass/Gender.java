package detectfaceclass;

/**
 * Created by Wheee on 2016/11/5.
 */
public class Gender {
    private String gender;

    private double score;
    public Gender(){
        
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public String getGender(){
        return this.gender;
    }
    public void setScore(double score){
        this.score = score;
    }
    public double getScore(){
        return this.score;
    }

}