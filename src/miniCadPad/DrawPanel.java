package miniCadPad;

import java.awt.*;    
import java.util.ArrayList;

import javax.swing.*;

import shapes.Shape; 

/**
 * 画图板类
 * @author suntfen@163.com
 *
 */
public class DrawPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Shape> listShape = new ArrayList<Shape>();	// 画图板上的所有Shape
	
	public DrawPanel(){
		this.setBackground(Color.WHITE);  // 设置画图板上的背景颜色
		this.setSize(new Dimension(800,600));	// 设置画图板的大小
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D)g;
		for ( Shape s : listShape ){
			s.draw(g2d);
		}			
	}
	
	/**
	 * 为画图板添加Shape
	 * @param s
	 */
	public void add(Shape s){
		listShape.add(s);
	}
	
	/**
	 * 通过画图板上的某个坐标(x,y)获取画图板上的某个Shape的索引
	 * @param x
	 * @param y
	 * @return ShapeIndx
	 */
	public int getShapeIndx(int x, int y){
		boolean flag = false;
		int i;
		for(i = 0;i<listShape.size();i++){
			Shape shape = listShape.get(i);
			if(shape.isContainPoint(x, y)){
				flag = true;
				break;
			}
		}
		if (flag)
			return i;
		else
			return -1;
	}
	
	/**
	 * 获取画图板上某个索引i对应的Shape
	 * @param i
	 * @return Shape
	 */
	public Shape getShape(int i){
		return listShape.get(i);
	}
	
	/**
	 * 设置画图板上的某个索引i对应的Shape
	 * @param i
	 * @param shape
	 */
	public void setShape(int i, Shape shape){
		listShape.set(i, shape);
	}
	
	/**
	 * 移除画图板上的某个索引i对应的Shape
	 * @param i
	 */
	public void removeShape(int i){
		listShape.remove(i);
	}
	
	/**
	 * 获取画图板上所有Shape的个数
	 * @return ShapeNum
	 */
	public int getShapeNum(){
		return listShape.size();
	}
	
	/**
	 * 删除所有Shape
	 */
	public void clearShape(){
		listShape.clear();
	}
}
