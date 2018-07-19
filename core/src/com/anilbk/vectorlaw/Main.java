package com.anilbk.vectorlaw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Sprite Hero; 
	
	Texture cloud;
	
	ShapeRenderer shapeRenderer;
	BitmapFont font;
	FreeTypeFontGenerator generator;
	FreeTypeFontParameter parameter;
	//Positions of the clouds stored as array.
	float w_locations_x[];int w_locations_y[];
	
	float WindVelocity=65;
	float HeroVelocity=65;
	double Resultant=0.0f;
	double Resultant_Direction=0.0f;
	float Angle=45f;
	
	int W,H;

	Button IncreaseWindVel,IncreaseHeroVel;
	Button DecreaseWindVel,DecreaseHeroVel;
	Button IncreaseDir,DecreaseDir;
	Button show;
	
	boolean showing=false;
	
	@Override
	public void create () {
		
		
		w_locations_x=new float[7];
		w_locations_y=new int[7];
		
		//Generate random places of clouds.
		for(int j=0;j<w_locations_x.length;j++) {
			w_locations_x[j]=(float) (Gdx.graphics.getWidth()+45+Math.random()*445);
			w_locations_y[j]=(int) (Math.random()*Gdx.graphics.getHeight());
		}
		
		batch = new SpriteBatch();
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("calibri.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 25; 
		font = generator.generateFont(parameter);
		
		
		Texture hero=new Texture("superman.png");
		cloud=new Texture("cloud.png");
		
		
		Hero=new Sprite(hero);
		shapeRenderer=new ShapeRenderer();
		Hero.setPosition(150,50);
		Hero.setSize(390,250);
		Hero.setOrigin(150,100);
		
		W=Gdx.graphics.getWidth();
		H=Gdx.graphics.getHeight();
		
		Texture bg=new Texture("s_bg.png");

		IncreaseWindVel=new Button(230,H-40,35,30,bg,"+");
		IncreaseHeroVel=new Button(230,H-80,35,30,bg,"+");

		DecreaseWindVel=new Button(270,H-40,35,30,bg,"-");
		DecreaseHeroVel=new Button(270,H-80,35,30,bg,"-");
		
		IncreaseDir=new Button(20,H-240,35,30,bg,"+");
		DecreaseDir=new Button(60,H-240,35,30,bg,"-");
		show=new Button(10,45,35,30,bg,"X");
		
	}
	
	private void HUD() {
		
		
		Resultant=magnitude(HeroVelocity,WindVelocity,Angle);
		Resultant_Direction=direction(HeroVelocity,WindVelocity,Angle);
				
		font.setColor(Color.PINK);
		font.draw(batch, "Wind Velocity :"+WindVelocity,10,H-20);
		
		font.setColor(Color.RED);
		font.draw(batch, "Actual Velocity :"+HeroVelocity,10,H-60);
		
		font.setColor(Color.YELLOW);
		font.draw(batch, "Resultant Velocity :"+Math.round(Resultant * 100.0) / 100.0,10,H-100);
		
		font.setColor(Color.YELLOW);
		font.draw(batch, "Resultant Direction :"+Math.round(Resultant_Direction * 100.0) / 100.0,10,H-140);
		
		font.setColor(Color.RED);
		font.draw(batch, "Hero's Direction:"+Angle,10,H-180);
		
		font.setColor(Color.RED);
		font.draw(batch, "Show with Resultant Velocity",10,40);
				
		IncreaseWindVel.Draw(batch,font,1.0f,1.0f);
		IncreaseHeroVel.Draw(batch,font,1.0f,1.0f);
		DecreaseWindVel.Draw(batch,font,1.0f,1.0f);
		DecreaseHeroVel.Draw(batch,font,1.0f,1.0f);
		IncreaseDir.Draw(batch,font,1.0f,1.0f);
		DecreaseDir.Draw(batch,font,1.0f,1.0f);
		show.Draw(batch,font,1.0f,1.0f);
		
		if(Gdx.input.justTouched()) {
    		int x=Gdx.input.getX();
    		int y=Gdx.input.getY();
    		
    		if(IncreaseWindVel.check(x, y)) {WindVelocity+=35;}
    		if(DecreaseWindVel.check(x, y)) {if(WindVelocity>=35) {WindVelocity-=35;}}
    		if(IncreaseHeroVel.check(x, y)) {HeroVelocity+=35;}
    		if(DecreaseHeroVel.check(x, y)) {if(HeroVelocity>=25) {HeroVelocity-=25;}}
    		if(IncreaseDir.check(x, y)) {Angle+=10;}
    		if(DecreaseDir.check(x, y)) {Angle-=10;}
    		if(show.check(x, y)) {
    			//The button on the lower which toggles ON and OFF.
    			if(!showing) {
    				showing=true;
    				show.TITLE="X";
    			}
    			else {
    				showing=false;
    				show.TITLE=" ";
    			}
    		}
    	
		}	
    	

		
	}
	
	private double magnitude(float P,float Q,double theta) {
		theta=Math.toRadians(theta);		
		return Math.sqrt((Q*Q)+(P*P)+(2*P*Q*Math.cos(theta)));
	}
	
	private double direction(float P,float Q,double theta) {
		theta=Math.toRadians(theta);
		double a=Math.sin(theta);
		double b=P+(Q*Math.cos(theta));
		return Math.toDegrees(Math.atan(Q*(a/b)));
	}
	
	public void Wind_Update() {
		
		HUD();
		Hero.setPosition((int)(150+Math.random()*5),(int)(50+Math.random()*10));
		
		if(Angle>360) {Angle=Angle-360;}
		
	}
	
	public Vector2 rotate(Vector2 p,Vector2 o,float degrees) {
		
		/*
		 * Theory:
		 * If you rotate point (px, py) around point (ox, oy) by angle theta 

		p'x = cos(theta) * (px-ox) - sin(theta) * (py-oy) + ox
		
		p'y = sin(theta) * (px-ox) + cos(theta) * (py-oy) + oy
		
		: p'x += ox
		
		 */
		float theta=(float) Math.toRadians(degrees);
	
		Vector2 X=new Vector2();
		X.x=(float) (Math.cos(theta)*(p.x-o.x)-Math.sin(theta)*(p.y-o.y)+o.x);
		X.y=(float) (Math.sin(theta)*(p.x-o.x)+Math.cos(theta)*(p.y-o.y)+o.y);
		
		 
		return X;
		
	}

	public void line(float x1,float y1,float x2,float y2,float width,Color c) {
		
		 shapeRenderer.begin(ShapeType.Line);
		 shapeRenderer.setColor(c);
		 Gdx.gl.glLineWidth(width);
		 shapeRenderer.line(x1, y1, x2, y2);
		 shapeRenderer.end();
	}
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		//Clouds scroll only around the x-axis
		float value=Gdx.graphics.getDeltaTime()*WindVelocity*2;
		for(int j=0;j<w_locations_x.length;j++) {
			w_locations_x[j]-=value;
			float W=Gdx.graphics.getWidth();
			if(w_locations_x[j]<-150) {
				w_locations_x[j]=(float) (W+75+Math.random()*445);
			}
			batch.draw(cloud,w_locations_x[j], w_locations_y[j],125,125);
		}
	

		float x=Hero.getX()+Hero.getWidth()/2,y=Hero.getY()+Hero.getHeight()/2;
		Hero.setOrigin(Hero.getWidth()/2,Hero.getHeight()/2);
		Hero.setRotation(Angle);
		Hero.draw(batch);
		if(showing) {
		//now with the resultant angle.
		Hero.setRotation((float) Resultant_Direction);
		//the displaced hero.
		Hero.draw(batch, 0.5f);
		}
		
		Wind_Update();
		
		batch.end();
		
		Vector2 f=rotate(new Vector2(x+150,y),new Vector2(x,y),Angle);
		
		//the line rotating with the Hero.
		line(x,y,f.x,f.y,5,Color.RED);
		
		//the line along x-axis
		line(x,y,x+200,y,5,Color.MAGENTA);
		
		line(f.x,f.y,f.x+200,f.y,5,Color.BLUE); //-THis moves with the tail of the first diagonal
		
		line(x+200,y,f.x+200,f.y,5,Color.BLACK);
		
		//the diagonal.
		line(x,y,f.x+200,f.y,5,Color.YELLOW);
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		generator.dispose();
		
	}
}
