package com.mygdx.game;

import static com.badlogic.gdx.scenes.scene2d.Touchable.enabled;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.HashMap;

public class Bird extends Actor {
    TextureRegion birdImg;
    public Bird(String name, float x, float y){
        HashMap<String, TextureRegion> images = Sprites.getImages();
        birdImg = images.get(name);
        setWidth(birdImg.getRegionWidth());
        setHeight(birdImg.getRegionHeight());
        setX(x);
        setY(y);
        setTouchable(enabled);
        setBounds(getX(),getY(),getWidth(),getHeight());

        addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                addAction(Actions.removeActor());
                return true;
            }
        });
    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(birdImg, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
