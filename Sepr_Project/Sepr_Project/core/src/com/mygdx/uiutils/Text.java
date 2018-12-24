package com.mygdx.uiutils;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text {

	private float height;
	private float width;
	private String text = "";
	private BitmapFont font = null;
	
	private String fontName;
	private String fontPath;
	private FontController fontController;
	
	private boolean isShow=true;
	
	private float r, g, b, a;
	private float x;
	private float y;

	private Batch batch = new SpriteBatch();

	public Text(float x, float y, float charWidthScale, float charHeightScale, FontController fontController, String fontName, String fontPath,
                String text) {
        this.x = x;
        this.y = y;
		this.fontName = fontName;
		this.fontPath = fontPath;
		this.fontController = fontController;
		if(fontController.getFont(fontName)== null)
		this.font = fontController.addFont(fontName, fontPath);
		else
		this.font = fontController.getFont(fontName);

		this.width = charWidthScale;
		this.height = charHeightScale;
		this.font.getData().setScale(charWidthScale,charHeightScale);
		this.text = text;
		r = g = b = a = 1;
		
	}
	
//	public Text(float x, float y, float width, float height, int zIndex, String fontFileName) {
//		super(x, y, width, height, zIndex);
//		font = new BitmapFont(Gdx.files.internal(fontFileName),false);
//
//	}

	public float getX(){
		return this.x;
	}

	public float getY(){
		return this.y;
	}

	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}

	
	public void setColor(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	

	public void renderText() {
		if(this.isShow){
			batch.begin();
			fontController.getFont(this.fontName).setColor(r, g, b, a);
			fontController.getFont(this.fontName).draw(batch, text, x, y);
			batch.end();
		}

	}


	public BitmapFont getFont() {
		return font;
	}
	public void setActive(boolean b){
		this.isShow = b;
	}
	
	public boolean isActive(){
		return isShow;
	}
}
