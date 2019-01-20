package com.mygdx.game;

import java.util.concurrent.atomic.AtomicInteger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Item extends Sprite{

    // so that item id is not duplicated
    private static final AtomicInteger count = new AtomicInteger(0);
    private String type;
    private Sprite image;
    private int id;

    private int item_WIDTH = 30;
    private int item_HEIGHT = 30;
 


    //positions the item in the passed coordinates.
    public Item(Sprite sprite, String type, int x, int y) {
        super(sprite);
        this.image = sprite;
        this.type = type;
        this.id = count.incrementAndGet();
        this.type = type;
        this.setSize(item_WIDTH,item_HEIGHT);
        this.setPosition( x, y);
        this.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    }
    //positions the item in the default coordinates.
    public Item(Sprite sprite, String type) {
        super(sprite);
        this.image = sprite;
        this.type = type;
        this.id = count.incrementAndGet();
        this.type = type;
        this.setSize(item_WIDTH,item_HEIGHT);
        this.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
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

 

    public Sprite getImage() {
        return image;
    }

    public void setImage(Sprite image) {
        this.image = image;
    }
}
