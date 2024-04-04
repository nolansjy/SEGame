package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Shop implements Screen {
    final SEMain game;
    OrthographicCamera camera;
    Stage stage;
    private Skin skin;
    private AssetManager assetManager;

    public Shop(final SEMain game){ //create object instances
        this.game = game;
        camera = new OrthographicCamera();
        assetManager = game.getAssetManager();
        skin = assetManager.get("earthskin-ui/earthskin.json",Skin.class);
        stage = new Stage(new FitViewport(450,894,camera));
        Gdx.input.setInputProcessor(stage);
    }


    public void show() { //edit screen here
        Color grayscale = new Color(0.2f,0.2f,0.2f,1f);
        Color colorDefault = new Color(1f, 1f, 1f, 1f);

        //TODO: Fix the alignment of the tables to match those in SEGameScreen.
        //headUI initiate
        Table headUI = new Table();
        headUI.align(Align.topLeft).padLeft(15);
        headUI.setFillParent(true);
        stage.addActor(headUI);

        //headUI elements
        Image img = new Image(skin, "quill");
        headUI.add(img).padRight(15);
        Label wallet = new Label(String.valueOf(User.getQuills()), skin, "button");
        headUI.add(wallet).padRight(45);
        Label info = new Label("You don't have enough quills.", skin, "button");
        info.setVisible(false);
        headUI.add(info);


        //menu initiate
        Table menu = new Table();
        //menu.align(Align.center);
        menu.setFillParent(true);
        stage.addActor(menu);


        //decoration Table
        // TODO: Replace it so the data is stored in the userfile for items that have been purchased
        Object[][] decoList = {{"quill", 50, 0}, {"quill", 80, 0}};
        // decoList is: {"item/image", cost, own}

        Label decoLabel = new Label("Decorations", skin, "button");
        menu.add(decoLabel).padBottom(15).row();
        Table decoTable = new Table();

        Image firImg = new Image(skin, "quill");
        Image secImg = new Image(skin, "quill");
        if (User.getQuills() < 50){firImg.setColor(grayscale);}
        if (User.getQuills() < 80){secImg.setColor(grayscale);}
        decoTable.add(firImg).size(150,150).padRight(15);
        decoTable.add(secImg).size(150,150);
        decoTable.row();

        TextButton decoOne = new TextButton("50 quills", skin);
        TextButton decoTwo = new TextButton("80 quills", skin);
        decoTable.add(decoOne).width(150).padRight(15);
        decoTable.add(decoTwo).width(150);
        menu.add(decoTable).row();
        decoTable.padBottom(105);

        decoOne.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(User.getQuills() >= 50 && (int) decoList[0][2] != 1){
                    User.addQuills(-50);
                    decoOne.setText("Purchased!");
                    decoList[0][2] = 1;
                }
                else if((int) decoList[0][2] == 1){
                    info.setText("Item had been purchased.");
                    info.setVisible(true);
                    info.addAction(Actions.sequence(
                            Actions.delay(2f),
                            Actions.run(() -> info.setVisible(false)),
                            Actions.run(() -> info.setText("You don't have enough quills."))
                    ));
                }
                else{
                    info.setVisible(true);
                    info.addAction(Actions.sequence(
                            Actions.delay(2f),
                            Actions.run(() -> info.setVisible(false)),
                            Actions.run(() -> info.setText("You don't have enough quills."))));
                }
                wallet.setText(String.valueOf(User.getQuills()));
                stage.act();
                stage.draw();
                return true;
            }
        });

        decoTwo.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(User.getQuills() >= 80 && (int) decoList[1][2] != 1){
                    User.addQuills(-80);
                    decoTwo.setText("Purchased!");
                    decoList[1][2] = 1;
                }
                else if((int) decoList[1][2] == 1){
                    info.setText("Item had been purchased.");
                    info.setVisible(true);
                    info.addAction(Actions.sequence(
                            Actions.delay(2f),
                            Actions.run(() -> info.setVisible(false)),
                            Actions.run(() -> info.setText("You don't have enough quills."))
                    ));
                }
                else{
                    info.setVisible(true);
                    info.addAction(Actions.sequence(
                            Actions.delay(2f),
                            Actions.run(() -> info.setVisible(false)),
                            Actions.run(() -> info.setText("You don't have enough quills."))));
                }
                wallet.setText(String.valueOf(User.getQuills()));
                stage.act();
                stage.draw();
                return true;
            }
        });


        //TODO: Replace the foodList, and store the information in the userfile. Link to inventory
        //bird-feeder Table
        Label foodLabel = new Label("Bird feeds", skin, "button");
        menu.add(foodLabel).row();

        //Runs through each of the food items in the foodList and creates the buttons dynamically
        Table foodTable = new Table();
        Object[][] foodList = {{"Fruit", 20}, {"Seeds", 30}, {"Sugar", 10}, {"Worms", 40}};
        // foodList is: {"item type/image", cost}

        for(int i = 0; i < foodList.length; i++){
            //Creates the placement of the buttons onto the screen
            TextButton foodButton = new TextButton(foodList[i][0] + "\n" + foodList[i][1] + " quills", skin);
            foodTable.add(foodButton).width(150).pad(0,0,15,15);
            if((i+1)%2 == 0){
                foodTable.row();
            }

            //Creates an inputListener to each of the buttons, based on the foodList
            int foodPrice = (int) foodList[i][1];
            foodButton.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(User.getQuills() >= foodPrice) {
                        User.addQuills(-foodPrice);
                        if (User.getQuills() < 50 && (int) decoList[0][2] != 1){firImg.setColor(grayscale);}
                        if (User.getQuills() < 80 && (int) decoList[1][2] != 1){secImg.setColor(grayscale);}
                    }
                    else {
                        info.setVisible(true);
                        info.addAction(Actions.sequence(
                                Actions.delay(2f),
                                Actions.run(() -> info.setVisible(false))
                        ));
                    }
                    wallet.setText(String.valueOf(User.getQuills()));
                    stage.act();
                    stage.draw();
                    return true;
                }
            });
        }
        menu.add(foodTable).row();
        foodTable.padBottom(45);

        //returnButton
        TextButton returnBtn = new TextButton("Return", skin);
        stage.addActor(returnBtn);
        returnBtn.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                game.setScreen(new SEGameScreen(game));
            }
        });


        //TODO: Remove later. This table won't appear in the final game. Used only to test the shop
        Table temp = new Table();
        TextButton addQ = new TextButton("+10", skin);
        TextButton subQ = new TextButton("-10", skin);
        temp.add(addQ).padRight(15);
        temp.add(subQ);
        menu.add(temp).row();

        addQ.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                User.addQuills(10);
                wallet.setText(String.valueOf(User.getQuills()));

                if (User.getQuills() >= 50){firImg.setColor(colorDefault);}
                if (User.getQuills() >= 80){secImg.setColor(colorDefault);}
                stage.act();
                stage.draw();
                return true;
            }
        });

        subQ.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(User.getQuills() != 0) {
                    User.addQuills(-10);
                }
                wallet.setText(String.valueOf(User.getQuills()));

                if (User.getQuills() < 50 && (int) decoList[0][2] != 1){firImg.setColor(grayscale);}
                if (User.getQuills() < 80 && (int) decoList[1][2] != 1){secImg.setColor(grayscale);}
                stage.act();
                stage.draw();
                return true;
            }
        });
    }

    @Override
    public void render (float delta) { //render screen here
        camera.update();
        Gdx.gl.glClearColor(156/255f, 175/255f, 170/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    //maintain below
    @Override
    public void resize(int width, int height) {stage.getViewport().update(width,height,false);}

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose () {
        skin.dispose();
        stage.dispose();
    }

}