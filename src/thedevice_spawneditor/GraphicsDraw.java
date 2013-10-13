/////Simple graphics drawing by Masana Pawlan
package thedevice_spawneditor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.Rectangle;

final class CenteredText{
    String text;
    Vector2 position;
    public CenteredText(String text,Vector2 center){
	this.text = text;
	setCenter(center);
    }
    
    public void setCenter(Vector2 center){
	FontRenderContext frc = new FontRenderContext(null,true,true);
	TextLayout layout = new TextLayout(text,GraphicsDraw.currentFont,frc);
	Rectangle r = layout.getPixelBounds(null,(int)center.x,(int)center.y);
	position = new Vector2(center.x-r.width/2,center.y+r.height/2);
    }
}

public class GraphicsDraw{
    public static Graphics2D screen;
    static Font currentFont;
    static Font defaultfont,boldfont,bigfont,biggishfont,smallboldfont;
    
    public static void initialize(Font def){
	defaultfont = def;
	String fontname = defaultfont.getFontName();
	boldfont = new Font(fontname,Font.BOLD,defaultfont.getSize());
	bigfont = new Font(fontname,Font.BOLD,32);
	biggishfont = new Font(fontname,Font.BOLD,24);
	smallboldfont = new Font(fontname,Font.BOLD,10);
    }

    public static void initializeDraw(Graphics2D _screen){
	screen = _screen;
    }

    public static void setColor(Color color){
	screen.setColor(color);
    }

    public static void line(Vector2 v1, Vector2 v2){
	screen.drawLine((int)v1.x,(int)v1.y,(int)v2.x,(int)v2.y);
    }

    public static void circle(Vector2 center, float radius){
	screen.drawOval((int)(center.x-radius),(int)(center.y-radius),(int)(radius*2),(int)(radius*2));
    }

    public static void rectangle(Vector2 center, Vector2 size){
	screen.drawRect((int)(center.x-size.x/2),(int)(center.y-size.y/2),(int)size.x,(int)size.y);
    }

    public static void fillCircle(Vector2 center, float radius){
	screen.fillOval((int)(center.x-radius),(int)(center.y-radius),(int)(radius*2),(int)(radius*2));
    }

    public static void fillRectangle(Vector2 center, Vector2 size){
	screen.fillRect((int)(center.x-size.x/2),(int)(center.y-size.y/2),(int)size.x,(int)size.y);
    }

    /////Text
    public static void text(String text,Vector2 pos){
	screen.drawString(text,pos.x,pos.y);
    }
    public static void centerText(String text,Vector2 pos){
	FontRenderContext frc = new FontRenderContext(null,true,true);
	TextLayout layout = new TextLayout(text,currentFont,frc);
	Rectangle r = layout.getPixelBounds(null,(int)pos.x,(int)pos.y);
	screen.drawString(text,pos.x-r.width/2,pos.y+r.height/2);
    }
    public static void centerText(CenteredText text){//More efficient, if the text and position are not changed often
	text(text.text,text.position);
    }

    /////Different font settings
    static void setFont(Font font){
	currentFont = font;
	screen.setFont(currentFont);
    }
    
    public static void defaultFont(){
	setFont(defaultfont);
    }
    public static void boldFont(){
	setFont(boldfont);
    }
    public static void bigFont(){
	setFont(bigfont);
    }
    public static void biggishFont(){
	setFont(biggishfont);
    }
    public static void smallBoldFont(){
	setFont(smallboldfont);
    }
}
