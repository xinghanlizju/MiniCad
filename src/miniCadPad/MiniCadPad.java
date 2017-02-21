package miniCadPad;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;

import javax.swing.*;

import shapes.*;
import shapes.Shape; 

/**
 * 主函数类
 * @author suntfen@163.com
 *
 */
public class MiniCadPad extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private CadMenuBar menu;	// 菜单
	private CadToolBar tool;	// 工具栏
	private DrawPanel dpl;	// 画布
	private JPanel drawmax;	// 画板
	private ColorPanel cpl;	// 调色板
	private FontPanel fpl;	// 字体栏
	private JLabel status;	// 状态栏
	
	private int canvas_x1,canvas_x2,canvas_y1,canvas_y2;	// 画布移动
	private Color color = Color.black;	// 颜色
	private float stroke = 10;	// 粗细
	private int x1,x2,y1,y2;	// 两点坐标
	private int move_x1,move_x2,move_y1,move_y2;	// 移动偏移
	private int cx,cy;	// Shape的中心点
	private float k, bk;	// Shape的斜率及斜率的倒数
	private String font;	// 字体
	private int bold = Font.PLAIN,italic = Font.PLAIN;	// 加粗、斜体
	private int fontsize;	// 字号
	private String string;	// 字内容
	private Shape currentshape;	// 当前Shape
	private int shape_idx=-1;	// 当前Shape索引
	private String shape_type;	// 当前Shape类型
	private int pressindx = 0;	// 鼠标按键
	private int mousewheel = 1;	// 鼠标滚轮方向
	
	public MiniCadPad(){
		
		super.setTitle("miniCAD");
		super.setSize(1100,900);
		
		// 工具栏
		tool = new CadToolBar();
		super.add(tool,BorderLayout.WEST);
		
		// 画板画布
		drawmax = new JPanel();
		drawmax.setLayout(null); 
		drawmax.setBackground(Color.gray);
		dpl = new DrawPanel(); 
		dpl.setBounds(5, 5, dpl.getWidth(), dpl.getHeight());
		drawmax.add(dpl);
		drawmax.setPreferredSize(new Dimension(dpl.getWidth()+10, dpl.getHeight()+10));
		JScrollPane jsp = new JScrollPane(drawmax);	//添加滚动条
		super.getContentPane().add(jsp);
		
		// 字体栏
		fpl = new FontPanel();
		super.add(fpl,BorderLayout.NORTH);
		
		// 调色板
		JPanel southpanel = new JPanel();
		southpanel.setPreferredSize(new Dimension(super.getWidth(),100));
		southpanel.setLayout(null);
		cpl = new ColorPanel();
		cpl.setBounds(20,8,super.getWidth()-20,72);
		southpanel.add(cpl);
		
		// 状态栏
		status = new JLabel();
		status.setBounds(20,80,super.getWidth()-20,15);
		southpanel.add(status);
		super.add(southpanel,BorderLayout.SOUTH);
		status.setText("welcome to miniCAD pad!");

		// 菜单
		menu = new CadMenuBar(dpl);
		super.setJMenuBar(menu);
		
		super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		super.setLocationRelativeTo(null);
		super.setVisible(true);
	}
	
	public void drawListener(){
		
		// 鼠标指针图片加载
    	Image imageCursorBrush = Toolkit.getDefaultToolkit().getImage("src/icon/cursor/brush.png");  
		Image imageCursorDelete = Toolkit.getDefaultToolkit().getImage("src/icon/cursor/cross.png"); 
		Image imageCursorResize = Toolkit.getDefaultToolkit().getImage("src/icon/cursor/resize.png"); 
		
		
		// 添加各类监听
		cpl.getPanelNow().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				color = cpl.getColor();
				if (shape_idx!=-1&&shape_idx<=dpl.getShapeNum())
					updateShape();
			}
		});
		
		fpl.getisBold().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				bold = fpl.getBold();
				if(shape_idx!=-1&&shape_idx<=dpl.getShapeNum())
					updateShape();
			}
		});
		
		fpl.getisItalic().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				italic = fpl.getItalic();
				if(shape_idx!=-1&&shape_idx<=dpl.getShapeNum())
					updateShape();
			}
		});
		
		fpl.getFontSizeBox().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fontsize = fpl.getFontSize();
				if(shape_idx!=-1&&shape_idx<=dpl.getShapeNum())
					updateShape();
			}
		});
		
		fpl.getStylesBox().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				font = fpl.getFontVal();
				if(shape_idx!=-1&&shape_idx<=dpl.getShapeNum())
					updateShape();
			}
		});
		
		dpl.addKeyListener(new KeyAdapter()  
	    {  
	        public void keyPressed(KeyEvent e)  
	        {  
	        	if (shape_idx!=-1&&shape_idx<=dpl.getShapeNum())
	        	{
	        		// 按上下调整当前选中的Shape大小
		        	if(e.getKeyCode()==37||e.getKeyCode()==39)
		        	{
		        		int i = 0;
		        		if(e.getKeyCode()==39)
		        			i = 1;
		        		else if(e.getKeyCode()==37)
		        			i = -1;
		        		stroke = stroke+i>0?stroke+i:1;
		        		if (currentshape.getType().equals("word"))
		        		{
		        			fontsize = fontsize+5*i>5?fontsize+5*i:5;
		        			fpl.setSizeBox(fontsize);
		        			}
		        		updateShape();
		        	}
		        	// 按左右调整当前选中的Shape粗细
		        	if(e.getKeyCode()==38||e.getKeyCode()==40)
		        	{
		        		move_x1 = move_y2 = 0;
		        		if(e.getKeyCode()==38)
		        			move_x2 = move_y1 = 1;
		        		else if(e.getKeyCode()==40)
		        			move_x2 = move_y1 = -1;
			        	resizeShape();
		        	}
		        	// 按Del或者backspace删除当前选中的Shape
		        	if(e.getKeyCode()==8||e.getKeyCode()==127)
		        	{
		        		dpl.removeShape(shape_idx);
		        		shape_idx = -1;
						repaint();
		        	}
		        }
	        }
	    }); 
		
		// 滚动鼠标滚轮可以调整当前选中的Shape的大小或粗细，通过按滚轮来切换功能
		dpl.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				status.setText("Mouse Wheel Rotating @:[" + e.getX() +
	                    ", " + e.getY() + "]      Tools: "+tool.getCommand());
				if (shape_idx!=-1&&shape_idx<=dpl.getShapeNum())
	        	{
					if(mousewheel==1)
					{
						stroke = stroke-e.getWheelRotation()>0?stroke-e.getWheelRotation():1;
						if (currentshape.getType().equals("word"))
						{
							fontsize = fontsize-e.getWheelRotation()>5?fontsize-e.getWheelRotation():5;
							fpl.setSizeBox(fontsize);
						}
						updateShape();
					}
					else
					{
						move_x1 = move_y2 = 0;
						move_x2 = move_y1 = -e.getWheelRotation();
						resizeShape();
					}
	        	}
			}
		});
		
		
		dpl.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e)
		    {
				pressindx = e.getButton();
				// 鼠标左键单击
				if(e.getButton()==1)
				{
					status.setText("Mouse LeftButton Pressed @:[" + e.getX() +
		                    ", " + e.getY() + "]      Tools: "+tool.getCommand());
					shape_idx = dpl.getShapeIndx(e.getX(), e.getY());
					if (shape_idx!=-1)
					{
						if(tool.getCommand().equals("move")||tool.getCommand().equals("resize")){
							// 移动或者缩放物体时，初始化偏移量
							getCurrentShape();
							move_x1 = move_x2 = e.getX();
							move_y1 = move_y2 = e.getY();
							}
						else if(tool.getCommand().equals("delete")){
							// 删除时 直接删除物体
							dpl.removeShape(shape_idx);
							shape_idx = -1;
							repaint();
							}
					}
					if(tool.getCommand().equals("line")||tool.getCommand().equals("rect")||
							tool.getCommand().equals("word")||tool.getCommand().equals("oval")||
							tool.getCommand().equals("circle")){
						x1 = x2 = e.getX();
						y1 = y2 = e.getY();
						// 画图形时 各参数初始化
						stroke = 5;
						font = fpl.getFontVal();
						bold = fpl.getBold();
						italic = fpl.getItalic();
						color = cpl.getColor();
						fontsize = fpl.getFontSize();
						string = null;
						//
						shape_type = tool.getCommand();
						
						if (shape_type.equals("word"))
							string=JOptionPane.showInputDialog(null,
									"请输入你想写的文本","请在这里输入");
						if (!(shape_type.equals("word")&&string==null))
						{
							createNewShape();
							setCurrentShapeHiddenPara();
							dpl.add(currentshape);
							shape_idx = dpl.getShapeNum()-1;
							repaint();
						}
					}
				}
				// 鼠标右键单击
				else if(e.getButton()==3){
					shape_idx = dpl.getShapeIndx(e.getX(), e.getY());
					if (shape_idx!=-1)
					{
						// 普通情况下选择Shape
						status.setText("Mouse RightButton Pressed @:[" + e.getX() +
			                    ", " + e.getY() + "]      Tools: choose");
						getCurrentShape();
						move_x1 = move_x2 = e.getX();
						move_y1 = move_y2 = e.getY();
					}
				}
				// 鼠标中键单击
				else if(e.getButton()==2)
				{
					// 切换中键功能
					mousewheel*=-1;
					if (mousewheel==1)
						status.setText("Mouse Wheel Pressed: Wheel Rotating can Change Stroke");
					else
						status.setText("Mouse Wheel Pressed: Wheel Rotating can Change Size");
				}
		    }
			
			public void mouseReleased(MouseEvent e)
		    {
				pressindx = 0;
				dpl.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
						imageCursorBrush,  new Point(8, 8), "cursor")); 
				if(e.getButton()==1)
					status.setText("Mouse LeftButton Released @:[" + e.getX() +
			                              ", " + e.getY() + "]      Tools: "+tool.getCommand());
				if(e.getButton()==3)
					status.setText("Mouse RightButton Released @:[" + e.getX() +
			                              ", " + e.getY() + "]      Tools: "+tool.getCommand());
				dpl.requestFocus();
			}
			public void mouseEntered(MouseEvent e)
			{
				status.setText("Mouse Entered @:[" + e.getX() +
		                              ", " + e.getY() + "]      Tools: "+tool.getCommand());
				dpl.requestFocus();
				}
			public void mouseExited(MouseEvent e)
			{
				status.setText("Mouse Exited @:[" + e.getX() +
		                              ", " + e.getY() + "]      Tools: "+tool.getCommand());
				dpl.requestFocus();
				}
			});
	    dpl.addMouseMotionListener(new MouseMotionListener() {
	    	@Override
			public void mouseMoved(MouseEvent e) {
				status.setText("Mouse Moved @:[" + e.getX() +
	                    ", " + e.getY() + "]      Tools: "+tool.getCommand());
				if (dpl.getShapeIndx(e.getX(), e.getY())!=-1)
				{
					if(tool.getCommand().equals("move"))
						dpl.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));

					else if(tool.getCommand().equals("resize"))
						dpl.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
								imageCursorResize,  new Point(16, 16), "cursor")); 	
					
					else if(tool.getCommand().equals("delete"))
						dpl.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
								imageCursorDelete,  new Point(16, 16), "cursor"));
					
					else
						dpl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
				else if(tool.getCommand().equals("word"))
					dpl.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
				else 
					dpl.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
							imageCursorBrush,  new Point(8, 8), "cursor")); 
				dpl.requestFocus();
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				// 按住鼠标左键拖拽
				if(pressindx==1)
				{
					status.setText("Mouse LeftButton Dragged @:[" + e.getX() +
		                    ", " + e.getY() + "]      Tools: "+tool.getCommand());
					if(tool.getCommand().equals("line")||tool.getCommand().equals("rect")||
							tool.getCommand().equals("oval")||tool.getCommand().equals("circle")){
						// 画图时，更新Shape参数画出Shape
						x2 = e.getX();
						y2 = e.getY();
						createNewShape();
						setCurrentShapeHiddenPara();
						dpl.setShape(dpl.getShapeNum()-1, currentshape);
						repaint();
						}
					else{
						// 移动或缩放时，更新偏移量，做出相应操作
						move_x2 = e.getX();
						move_y2 = e.getY();
						if(tool.getCommand().equals("move")&&shape_idx!=-1){
							dpl.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
							moveShape();
							}
						if(tool.getCommand().equals("resize")&&shape_idx!=-1){
							dpl.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
									imageCursorResize,  new Point(16, 16), "cursor")); 
							resizeShape();
							}
						}
					}
				// 按住鼠标 右键拖拽 移动物体
				else if(pressindx==3){
					status.setText("Mouse RightButton Dragged @:[" + e.getX() +
		                    ", " + e.getY() + "]      Tools: move");
					move_x2 = e.getX();
					move_y2 = e.getY();
					if(shape_idx!=-1){
						dpl.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
						moveShape();
					}
				}
				dpl.requestFocus();
				}
			});
	    
	    // 画布大小改变，整个画板大小要重新设置
	    dpl.addComponentListener(new ComponentAdapter() {
	    	public void componentResized(ComponentEvent e) {
				drawmax.setPreferredSize(new Dimension(dpl.getWidth()+10, dpl.getHeight()+10));
			}
		});
	    
	    // 拖拽画布边缘可以改变画布大小
	    drawmax.addMouseListener(new MouseAdapter() {
	    	public void mousePressed(MouseEvent e){
	    		pressindx=e.getButton();
	    		if(e.getButton()==1){
	    			if((e.getX()>=dpl.getWidth()&&e.getX()<=dpl.getWidth()+10&&e.getY()>=dpl.getHeight()&&e.getY()<=dpl.getHeight()+10)||
	    					(e.getX()>=dpl.getWidth()&&e.getX()<=dpl.getWidth()+10&&e.getY()<dpl.getHeight())||
	    					(e.getY()>=dpl.getHeight()&&e.getY()<=dpl.getHeight()+10&&e.getX()<dpl.getWidth()))
	    			{
	    				canvas_x1 = canvas_x2 = e.getX();
	    				canvas_y1 = canvas_y2 = e.getY();
	    			}
	    		}
	    	}
		});
	    drawmax.addMouseMotionListener(new MouseMotionListener() {	
			@Override
			public void mouseMoved(MouseEvent e) {
				if(e.getX()>=dpl.getWidth()&&e.getX()<=dpl.getWidth()+10&&e.getY()>=dpl.getHeight()&&e.getY()<=dpl.getHeight()+10)
					drawmax.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
				else if(e.getX()>=dpl.getWidth()&&e.getX()<=dpl.getWidth()+10&&e.getY()<dpl.getHeight())
					drawmax.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
				else if(e.getY()>=dpl.getHeight()&&e.getY()<=dpl.getHeight()+10&&e.getX()<dpl.getWidth())
					drawmax.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
				else
					drawmax.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				if(pressindx==1)
				{
		    		canvas_x2 = e.getX();
		    		canvas_y2 = e.getY();
		    		double rp = 1.4;
		    		if(e.getX()>=dpl.getWidth()&&e.getY()>=dpl.getHeight()){
		    			drawmax.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
						dpl.setSize(dpl.getWidth()+(int)(rp*(canvas_x2-canvas_x1)),dpl.getHeight()+(int)(rp*(canvas_y2-canvas_y1)));
		    		}
					else if(e.getX()>=dpl.getWidth()&&e.getY()<=dpl.getHeight()){
						drawmax.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
						dpl.setSize(dpl.getWidth()+(int)(rp*(canvas_x2-canvas_x1)),dpl.getHeight());
					}
					else if(e.getY()>=dpl.getHeight()&&e.getX()<=dpl.getWidth()){
						drawmax.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
						dpl.setSize(dpl.getWidth(),dpl.getHeight()+(int)(rp*(canvas_y2-canvas_y1)));
					}
		    		dpl.repaint();
		    		canvas_x1 = canvas_x2;
		    		canvas_y1 = canvas_y2;
		    	}
			}
		});
	    
	    // 关闭窗口
	    super.addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e){
	    		int i = JOptionPane.YES_OPTION;
				if (dpl.getShapeNum()!=0&&menu.getUpdateflag())
					i = JOptionPane.showConfirmDialog(null,"当前图片尚未保存，是否确认退出？","",JOptionPane.YES_NO_OPTION);
				if(i==JOptionPane.YES_OPTION)
					System.exit(0);
	    	}
		});
	}
	
	// 创建Shape
	private void createNewShape(){
		menu.setUpdateflag(true);
		if (shape_type.equals("line"))
			currentshape = new Line(x1,y1,x2,y2,color,stroke);
		else if(shape_type.equals("rect"))
			currentshape = new Rect(x1,y1,x2,y2,color,stroke);
		else if(shape_type.equals("oval"))
			currentshape = new Oval(x1,y1,x2,y2,color,stroke);
		else if(shape_type.equals("circle"))
			currentshape = new Circle(x1,y1,x2,y2,color,stroke);
		else if(shape_type.equals("word"))
			currentshape = new Word(x1,y1,bold,italic,color,stroke,
					font,fontsize,string);
	}
	
	// 得到当前Shape
	private void getCurrentShape(){
		currentshape = dpl.getShape(shape_idx);
	    shape_type = currentshape.getType();
	    stroke = currentshape.getStroke();
	    color = currentshape.getColor();
	    x1 = currentshape.getX1();
	    y1 = currentshape.getY1();
	    x2 = currentshape.getX2();
	    y2 = currentshape.getY2();
	    setCurrentShapeHiddenPara();
	    if (shape_type.equals("word"))
	    {
	    	Word w = (Word)currentshape;
	    	string = w.getString();
	    	fontsize = w.getFontSize();
	    	bold = currentshape.getX2();
	    	italic = currentshape.getY2();
	    	font = w.getFont();
		    fpl.setBoldBox(bold);
	    	fpl.setItalicBox(italic);
	    	fpl.setSizeBox(fontsize);
	    	fpl.setStylesBox(font);
	    }
	    cpl.setColor(color);
	}
	
	// 设置隐性特征，中点、斜率等
	private void setCurrentShapeHiddenPara(){
		double a = (double)Math.abs(x2-x1)/2;
		double b = (double)Math.abs(y2-y1)/2;
	    k = (float)(y2-y1)/(x2-x1);
	    bk = (float)(x2-x1)/(y2-y1);
	    if (shape_type.equals("circle"))
	    {
	    	a = b = (double)Math.max(Math.abs(x2-x1),Math.abs(y2-y1))/2;
	    	k = bk = 1;
	    }
		cx = (int)((double)Math.min(x1,x2)+a);
		cy = (int)((double)Math.min(y1,y2)+b);
	}
	
	// 更新Shape
	private void updateShape(){
		createNewShape();
		dpl.setShape(shape_idx, currentshape);
		repaint();
	}
	
	// 移动Shape
	private void moveShape(){
		x1 = currentshape.getX1()+(move_x2-move_x1);
		y1 = currentshape.getY1()+(move_y2-move_y1);
		x2 = currentshape.getX2()+(move_x2-move_x1);
		y2 = currentshape.getY2()+(move_y2-move_y1);
		updateShape();
		getCurrentShape();
		move_x1 = move_x2;
		move_y1 = move_y2;
	}
	
	// 改变shape大小
	private void resizeShape(){
		int i = move_x2-move_x1>0?5:-5;
		int j = move_y2-move_y1>0?5:-5;
		if(shape_type.equals("word"))
		{
			fontsize = (int)(fontsize+i)>5?(int)(fontsize+i):5;
			fpl.setSizeBox(fontsize);
			x1 = currentshape.getX1();
			y1 = currentshape.getY1();
		}
		else
		{
			if (k>=-1&&k<=1)
			{
				x1 = currentshape.getX1()-i;
				y1 = cy+(int)(((currentshape.getX1()-cx)-i)*k);
				x2 = currentshape.getX2()+i;
				y2 = cy+(int)(((currentshape.getX2()-cx)+i)*k);
			}
			else if(bk>=-1&&bk<=1)
			{
				x1 = cx+(int)(((currentshape.getY1()-cy)-j)*bk);
				y1 = currentshape.getY1()-j;
				x2 = cx+(int)(((currentshape.getY2()-cy)+j)*bk);
				y2 = currentshape.getY2()+j;
			}
			else
			{
				x1 = currentshape.getX1()-(move_x2-move_x1);
				y1 = currentshape.getY1()+(move_y2-move_y1);
				x2 = currentshape.getX2()+(move_x2-move_x1);
				y2 = currentshape.getY2()-(move_y2-move_y1);
			}
		}
		updateShape();
		move_x1 = move_x2;
		move_y1 = move_y2;
	}

	// 主函数类
	public static void main(String[] args) {
		MiniCadPad minicad = new MiniCadPad();
		minicad.drawListener();
	}

}
