package detectfaceclass;

/**
 * Created by Wheee on 2016/11/5.
 */

import java.util.List;

public class Images {
    private List<Faces> faces;

    private String image;

    public Images() {

    }

    public void setFaces(List<Faces> faces) {
        this.faces = faces;
    }

    public List<Faces> getFaces() {
        return this.faces;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }

}