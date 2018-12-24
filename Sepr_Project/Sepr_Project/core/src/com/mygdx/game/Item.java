package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Item extends Sprite{


    private String type;
    private int id;
    private Sprite image;


    private int item_WIDTH = 100;
    private int item_HEIGHT = 100;



    public Item(Sprite sprite,int id, String type) {
        super(sprite);
        this.image = sprite;
        this.type = type;
        this.id = id;
        this.type = type;

        initialize();
    }

    /*
    set default value of item attributes
     */
    private void initialize() {

        this.setSize(item_WIDTH,item_HEIGHT);
        this.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
    }


    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }



    /*
    update item status and response to events
    react on collision
  
     */

    public void update(float delta)
    {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sprite getImage() {
        return image;
    }

    public void setImage(Sprite image) {
        this.image = image;
    }
}
