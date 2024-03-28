package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

public class Sprites {
    public static HashMap<String, TextureRegion> images = new HashMap<>();

    public static void load(TextureAtlas textureAtlas) { // loads sprites from atlas
        Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();

        for (TextureAtlas.AtlasRegion region : regions) {
            TextureAtlas.AtlasRegion image = textureAtlas.findRegion(region.name);
            images.put(region.name,image);
        }
    }

    public static HashMap<String, TextureRegion> getImages(){
        return images;
    }

    public static void dispose(){
        images.clear();
    }
}
