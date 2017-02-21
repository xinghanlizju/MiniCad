package shapes;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * 文字类
 * @author suntfen@163.com
 *
 */
public class Word extends Shape{
	private static final long serialVersionUID = 1L;
	
	private String font,string;	// 字体，内容
	private double fontwidth,fontheight;	// 文字区域的长，高
	private int fontsize;	// 文字的字号
	
	public Word(int x1, int y1, int x2, int y2, Color color, float stroke, 
			String font, int fontsize, String s) {
		super(x1, y1, x2, y2, color, stroke);
		setType("word");
		this.font = font;
		this.fontsize = fontsize;
		this.string = s;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setPaint(getColor());
		Font f = new Font(font,getX2()+getY2(), fontsize);
        g.setFont(f);
        // 利用FontRenderContext获取文字的width和height
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D stringBounds = f.getStringBounds(string, context);
        fontheight = stringBounds.getHeight();
        fontwidth = stringBounds.getWidth();
        if (string!= null )
        	g.drawString(string,(int)(getX1()-fontwidth/2),
        			(int)(getY1()+fontheight/6));
        initPoint();
	}
	
	/**
	 * 将文字块的width和height所圈定的区域视为文字上的点
	 */
	private void initPoint(){
		for (int i = (int)(getX1()-fontwidth/2);
				i<=getX1()+fontwidth/2;i++)
			for (int j = (int) (getY1()-3.0/6*fontheight);
					j<=getY1()+2.0/6*fontheight;j++)
				addPoint(new Point(i,j));
	}
	
	/**
	 * 获取文字内容
	 * @return string
	 */
	public String getString(){
		return string;
	}
	
	/**
	 * 获取文字的字体
	 * @return font
	 */
	public String getFont(){
		return font;
	}
	
	/**
	 * 获取文字的字号
	 * @return fontsize
	 */
	public int getFontSize(){
		return fontsize;
	}
}

