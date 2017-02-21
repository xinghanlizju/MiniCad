package shapes;

import java.awt.*;

/**
 * 圆类
 * @author suntfen@163.com
 *
 */
public class Circle extends Shape {
	private static final long serialVersionUID = 1L;

	public Circle(int x1, int y1, int x2, int y2, Color color, float stroke) {
		super(x1, y1, x2, y2, color, stroke);
		setType("circle");
	}

	@Override
	public void draw(Graphics2D g) {
		g.setPaint(getColor());
		g.setStroke(new BasicStroke(getStroke(),
                BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
		g.drawOval(Math.min(getX1(),getX2()),Math.min(getY1(),getY2()),
	               Math.max(Math.abs(getX1()-getX2()),Math.abs(getY1()-getY2())),
	               Math.max(Math.abs(getX1()-getX2()),Math.abs(getY1()-getY2())));
		initPoint();
	}
	
	/**
	 * 确定圆上所有的点,与椭圆类似,但a=b
	 */
	private void initPoint(){
		for (int i =0;i<360;i++)
		{
			int s = getStroke()>5?(int)(getStroke()/2):2;
			s = s<20?s:20;
			for(int j =-s;j<=s;j++)
			{
				for(int k =-s;k<=s;k++)
				{
					double a = (double)Math.max(Math.abs(getX1()-getX2()),Math.abs(getY1()-getY2()))/2;
					double b = (double)Math.max(Math.abs(getX1()-getX2()),Math.abs(getY1()-getY2()))/2;
					int x = (int)((double)Math.min(getX1(),getX2())+a+j+a*Math.cos((double)i/360*Math.PI*2));
					int y = (int)((double)Math.min(getY1(),getY2())+b+k+b*Math.sin((double)i/360*Math.PI*2));
					addPoint(new Point(x,y));
				}
			}
		}
	}
}
