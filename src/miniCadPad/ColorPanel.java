package miniCadPad;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * 调色盘类
 * @author suntfen@163.com
 *
 */
public class ColorPanel extends JPanel { 
	private static final long serialVersionUID = 1L;
	
	private JPanel panelNow;	// 显示当前颜色的面板
	
    public ColorPanel() {
    	init();
    }
    
    private void init(){
    	this.setLayout(null);
    	this.setSize(new Dimension(385,65));
    	
		panelNow = new JPanel();
		panelNow.setBackground(Color.BLACK);	// 设置面板颜色,即当前调色板颜色
		panelNow.setToolTipText("当前颜色");
		panelNow.setBounds(0, 15, 40, 40);
        this.add(panelNow);  
    
        // 设置鼠标按键监听ma,若按下Button,则返回Button上的颜色,并设置为当前面板颜色
        MouseAdapter ma = new MouseAdapter(){  
            public void mousePressed(MouseEvent e){   
                JPanel btn = (JPanel)e.getSource();  
                if(e.getButton() == 1)
                	setColor(btn.getBackground());
            }  
        };  
        
        JPanel panel1 = new JPanel();	// 调色板可选颜色区域
        panel1.setBounds(50, 2, 240, 62);
        panel1.setToolTipText("可选颜色");
        panel1.setLayout(new GridLayout(2, 7, 2, 2));	// 可选颜色区域以网格布局  
        Color[] array = {Color.BLACK,Color.BLUE,Color.CYAN,Color.DARK_GRAY,  
                Color.GRAY,Color.GREEN,Color.LIGHT_GRAY,Color.MAGENTA,Color.ORANGE,  
                Color.PINK,Color.RED,Color.YELLOW,Color.WHITE,new Color(150,200,130)};	// 设置可选颜色  
        // 为每个颜色建立一个Button
        for (int i = 0; i < array.length; i++) 
        {   
            JPanel jbn = new JPanel();  
            jbn.setPreferredSize(new Dimension(30, 30));	//设置Button大小  
            jbn.setBackground(array[i]);	// 设置Button背景颜色,即可选的颜色
            jbn.setToolTipText("R:"+array[i].getRed()+" G:"+array[i].getGreen()+" B:"+array[i].getBlue());
            jbn.addMouseListener(ma);   // 为Button添加监听ma
            panel1.add(jbn);  
        }  
        this.add(panel1);
        
        // 更多颜色面板
        JButton colorbutton = new JButton();
        colorbutton.setBounds(320, 0, 65, 65);
        colorbutton.setToolTipText("更多颜色");	// 添加说明
        colorbutton.setBorderPainted(false);	// 关闭Button边框显示
        // 自适应图标
        ImageIcon image = new ImageIcon("src/icon/tool/palette.png");
        Image temp = image.getImage().getScaledInstance(colorbutton.getWidth(), 
        		colorbutton.getHeight(), Image.SCALE_SMOOTH);
        image = new ImageIcon(temp);
        colorbutton.setIcon(image);
        colorbutton.setContentAreaFilled(false);
        // 添加监听
        colorbutton.addMouseListener(new MouseAdapter() {
        	public void mousePressed(MouseEvent e){ 
        		Color c = JColorChooser.showDialog(colorbutton,"Choose a color",panelNow.getBackground());
        		if(c!=null)
        			setColor(c);
        	}
		});
        this.add(colorbutton);
        
        this.setBackground(null); 
    }
    
    /**
     * 获取当前颜色 color
     * @return color
     */
    public Color getColor(){
    	return panelNow.getBackground();
    }
    
    /**
     * 设置当前颜色面板上的颜色
     * @param c
     */
    public void setColor(Color c){
    	panelNow.setBackground(c);  
    }
    
    public JPanel getPanelNow(){
    	return panelNow;
    }
}
