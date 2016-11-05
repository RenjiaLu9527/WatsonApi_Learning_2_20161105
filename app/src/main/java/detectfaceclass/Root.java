package detectfaceclass;

/**
 * Created by Wheee on 2016/11/5.
 */
import java.util.List;
public class Root {
    private List<Images> images ;

    private int images_processed;
    public Root(){
        
    }
    public void setImages(List<Images> images){
        this.images = images;
    }
    public List<Images> getImages(){
        return this.images;
    }
    public void setImages_processed(int images_processed){
        this.images_processed = images_processed;
    }
    public int getImages_processed(){
        return this.images_processed;
    }

}