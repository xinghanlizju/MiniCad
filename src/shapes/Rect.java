package shapes;

import java.awt.*;

/**
 * 矩形类
 * @author suntfen@163.com
 *
 */
public class Rect extends Shape {
	private static final long serialVersionUID = 1L;

	public Rect(int x1, int y1, int x2, int y2, Color color, float stroke) {
		super(x1, y1, x2, y2, color, stroke);
		setType("rect");
	}

	@Override
	public void draw(Graphics2D g) {
		g.setPaint(getColor());
		g.setStroke(new BasicStroke(getStroke(),
                BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
		g.drawRect(Math.min(getX1(),getX2()),Math.min(getY1(),getY2()),
	              Math.abs(getX1()-getX2()),Math.abs(getY1()-getY2()));

		initPoint();
	}
	
	/**
	 * 确定矩形上所有的点,分别求矩形上的四条边
	 */
	private void initPoint(){
		int s = getStroke()>5?(int)(getStroke()/2):2;
		s = s<20?s:20;
		for (int k = -s;k<=s;k++)
		{
			for(int l = -s;l<=s;l++)
			{
				for (int i =Math.min(getX1(),getX2())+k;
						i<=Math.max(getX1(),getX2())+k;i++)
				{
					int x = i;
					int y11 = getY1()+l;
					int y22 = getY2()+l;
					addPoint(new Point(x,y11));
					addPoint(new Point(x,y22));
				}
				for (int i =Math.min(getY1(),getY2())+l;
						i<=Math.max(getY1(),getY2())+l;i++)
				{
					int y = i;
					int x11 = getX1()+k;
					int x22 = getX2()+k;
					addPoint(new Point(x11,y));
					addPoint(new Point(x22,y));
				}
			}
		}
	}
}
