package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SEMain extends Game{
    SkinLoader.SkinParameter skinParameter;
    private AssetManager assetManager;

    public void create(){
        assetManager = new AssetManager();
        skinParameter = new SkinLoader.SkinParameter("earthskin-ui/earthskin.atlas");
        assetManager.load("sprites.atlas", TextureAtlas.class);
        assetManager.load("earthskin-ui/earthskin.json",Skin.class,skinParameter);
        assetManager.load("rain.mp3", Music.class);
        assetManager.finishLoading();
        this.setScreen(new SEMainMenu(this));
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void render(){
        super.render();
    }

    public void dispose(){
        assetManager.dispose();
    }
}
