package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;

public class SEMain extends Game{
    SkinLoader.SkinParameter skinParameter;
    public AssetManager assetManager;

    public void create(){
        assetManager = new AssetManager();
        skinParameter = new SkinLoader.SkinParameter("earthskin-ui/earthskin.atlas");
        assetManager.load("sprites.atlas", TextureAtlas.class);
        assetManager.load("earthskin-ui/earthskin.json",Skin.class,skinParameter);
        assetManager.load("rain.mp3", Music.class);
        assetManager.finishLoading();

        FileHandle userfile = User.getUserfile();
        if (!userfile.exists()) User.init();
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
