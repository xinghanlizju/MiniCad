package shapes;

import java.awt.*;
import java.io.Serializable;
import java.util.HashSet;

/**
 * Shape类
 * @author suntfen@163.com
 *
 */
public class Shape implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int x1,y1,x2,y2;	// (x1, y1)与(x2, y2)两点可以确定一个Shape的位置
	private String type;	// shape的类型: line, oval, circle, rect, word
	private Color color;	// shape的颜色
	private float stroke;	// shape的粗细
	private HashSet <Point> points = new HashSet<Point>();	// 储存shape上所有的点
	
	public Shape(int x1, int y1, int x2, int y2, Color color, float stroke)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
		this.stroke = stroke;
	}
	
	public void draw(Graphics2D g){
	};
	
	/**
	 * 获取两点的横、纵坐标
	 * @return  x1,y1,x2,y2
	 */
	public int getX1(){
		return x1;
	}
	public int getX2(){
		return x2;
	}
	public int getY1(){
		return y1;
	}
	public int getY2(){
		return y2;
	}
	
	/**
	 * 获得Shape的类型
	 * @return type
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * 获得Shape的类型
	 * @param t
	 */
	public void setType(String t){
		this.type = t;
	}
	
	/**
	 * 获得Shape的粗细
	 * @return stroke
	 */
	public float getStroke(){
		return stroke;
	}
	
	/**
	 * 获得Shape的颜色
	 * @return color
	 */
	public Color getColor(){
		return color;
	}
	
	/*
	 * 加入点
	 */
	public void addPoint(Point p)
	{
		points.add(p);
	}
	
	public boolean isContainPoint(int x,int y){
		return points.contains(new Point(x,y));
	}
}