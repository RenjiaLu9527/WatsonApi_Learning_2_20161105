package detectfaceclass;

/**
 * Created by Wheee on 2016/11/5.
 */
public class Faces {
    private Age age;

    private Face_location face_location;

    private Gender gender;
    public Faces(){
        
    }
    public void setAge(Age age){
        this.age = age;
    }
    public Age getAge(){
        return this.age;
    }
    public void setFace_location(Face_location face_location){
        this.face_location = face_location;
    }
    public Face_location getFace_location(){
        return this.face_location;
    }
    public void setGender(Gender gender){
        this.gender = gender;
    }
    public Gender getGender(){
        return this.gender;
    }

}