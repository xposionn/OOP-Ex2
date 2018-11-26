package GIS;

import java.util.*;

public class GIS_layer_obj extends ArrayList<GIS_element> implements GIS_layer {

    Meta_data layerMeta;

    @Override
    public Meta_data get_Meta_data() {
        return this.layerMeta;
    }

    public void setMeta(Meta_data meta) {
        this.layerMeta = meta;
    }

    public void toKml(){
        System.out.println(this.toString());
    }

}
