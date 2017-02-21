package shapes;

import java.awt.*;

/**
 * 直线类
 * @author suntfen@163.com
 *
 */
public class Line extends Shape {
	private static final long serialVersionUID = 1L;

	public Line(int x1, int y1, int x2, int y2, Color color, float stroke) {
		super(x1, y1, x2, y2, color, stroke);
		setType("line");
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setPaint(getColor());
		g.setStroke(new BasicStroke(getStroke(),
                BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
		g.drawLine(getX1(),getY1(),getX2(),getY2());
		initPoint();
	}
	
	/**
	 * 确定圆上所有的点,利用直线斜率来求,注意k为无穷的时候要特殊考虑
	 */
	private void initPoint(){
		float k = (getY2()-getY1())/(float)(getX2()-getX1());
		float k2 = (getX2()-getX1())/(float)(getY2()-getY1());
		int s = getStroke()>5?(int)(getStroke()/2):2;
		s = s<20?s:20;
		for (int j = -s;j<=s;j++)
		{
			if (k<=1&&k>=-1)
			{
				for (int i = Math.min(getX1(), getX2())+j;
						i<=Math.max(getX1(), getX2())+j;i++)
				{
					int x = i;
					int y = (int)(k*(x-(getX1()+j))+getY1());
					addPoint(new Point(x,y));
				}
				for (int i = Math.min(getX1(), getX2());
						i<=Math.max(getX1(), getX2());i++)
				{
					int x = i;
					int y = (int)(k*(x-getX1())+getY1()+j);
					addPoint(new Point(x,y));
				}
			}
			else if(k2<=1&&k2>=-1)
			{
				for (int i = Math.min(getY1(), getY2())+j;
						i<=Math.max(getY1(), getY2())+j;i++)
				{
					int y = i;
					int x = (int)(k2*(y-(getY1()+j))+getX1());
					addPoint(new Point(x,y));
				}
				for (int i = Math.min(getY1(), getY2());
						i<=Math.max(getY1(), getY2());i++)
				{
					int y = i;
					int x = (int)(k2*(y-getY1())+getX1()+j);
					addPoint(new Point(x,y));
				}
			}
			else 
			{
				for(int i = -s;i<=s;i++)
					addPoint(new Point(getX1()+i,getY1()+j));
			}
		}
	}
}
