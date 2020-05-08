package com.sczy.map;

import java.util.HashMap;

public class MenuMap extends HashMap {

    public MenuMap() {
        super(4);
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof MenuMap)){
            return false;
        }
        if(((MenuMap) o).get("path").equals(this.get("path"))){
            return true;
        }
        return false;
    }
}
