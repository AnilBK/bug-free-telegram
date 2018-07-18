package com.anilbk.vectorlaw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button extends ApplicationAdapter{

    Texture Look;
    int x,y,w,h;
    String TITLE;
    boolean flashing=false;
    float flash_time=0.0f;//since how many time is the button flashing..
    
    final int flashes_in_sec=25;//how many times does the text in a button flashes in a second
    
    public Button(int X, int Y, int WIDTH, int HEIGHT, Texture Texture){
        this.x=X;
        this.y=Y;
        this.w=WIDTH;
        this.h=HEIGHT;
        this.Look=Texture;
    }
    
    public void Draw(SpriteBatch batch){
        batch.draw(Look,x,y,w,h);
    }
    
    /////////BUTTON WITH TEXT//////////////////////
    public Button(int X, int Y, int WIDTH, int HEIGHT, Texture Texture,String title){
        this.x=X;
        this.y=Y;
        this.w=WIDTH;
        this.h=HEIGHT;
        this.Look=Texture;
        this.TITLE=title;
    }
    
    public void Draw(SpriteBatch batch,BitmapFont font,float ScaleX,float ScaleY){
        batch.draw(Look,x,y,w,h);

        if(flashing)flash_time+=Gdx.graphics.getDeltaTime();
		
		if(flash_time>1.1f)flash_time=0.0f;
		
		float flash_scale=(flash_time/flashes_in_sec);	
		
        font.getData().setScale(ScaleX+flash_scale,ScaleY+flash_scale*3);
		font.setColor(Color.BLACK);
		
		
		font.draw(batch, ""+TITLE,(float) (x+w/2.5-flash_scale*5),(float) (y+h/1.5-flash_scale));
    }
    
    
    
    public boolean Pressed() {
    	if(Gdx.input.isTouched()) {
    		int x=Gdx.input.getX();
    		int y=Gdx.graphics.getHeight()-1-Gdx.input.getY();
    		return (x>this.x && x<this.x+this.w && y>this.y && y<this.y+this.h)?true:false;
    	}
		return false;
	}
    
    public boolean justPressed() {
    	if(Gdx.input.justTouched()) {
    		int x=Gdx.input.getX();
    		int y=Gdx.graphics.getHeight()-1-Gdx.input.getY();
    		return (x>this.x && x<this.x+this.w && y>this.y && y<this.y+this.h)?true:false;
    	}
		return false;
	}
    
    public boolean check(float x,float Y) {
		int y=(int) (Gdx.graphics.getHeight()-1-Y);
		return (x>this.x && x<this.x+this.w && y>this.y && y<this.y+this.h)?true:false;
	
    }
    
	@Override
	public void dispose () {
		Look.dispose();
	}   


}
