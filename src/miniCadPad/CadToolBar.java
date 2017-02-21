package miniCadPad;

import java.awt.*;    
import java.awt.event.*;  
import javax.swing.*; 

/**
 * 工具栏类
 * @author suntfen@163.com
 *
 */
public class CadToolBar extends JToolBar {  
	private static final long serialVersionUID = 1L;
	
	private String shape_command = "line";	// 工具栏所选择的工具命令 
  
    public CadToolBar() {  
        init();
    }  
  
    private void init() {    
        this.setOrientation(JToolBar.VERTICAL) ;	// 初始化工具栏为纵排列
        String[] array = { "src/icon/tool/line.png","src/icon/tool/oval.png", "src/icon/tool/circle.png",
        		"src/icon/tool/rect.png", "src/icon/tool/word.png",
        		"src/icon/tool/delete.png","src/icon/tool/move.png", "src/icon/tool/resize.png"};	// 工具图标
        String[] tips = { "直线", "椭圆","圆", "矩形", 
                "文字", "删除", "移动", "改变大小"};	// 工具说明
        JButton []jbn = new JButton[array.length];	// 为每一个工具建立Button
        // 设置每个Button
        for (int i = 0; i < array.length; i++)
        {  
            jbn[i] = new JButton();  
            jbn[i].setToolTipText(tips[i]);	// 添加说明
            jbn[i].setSize(new Dimension(72, 72));  // 设置Button大小
            jbn[i].setBackground(null); // 设置Button背景
            jbn[i].setBorderPainted(false);	// 关闭Button边框显示
            // Button自适应加载图标图片
            ImageIcon image = new ImageIcon(array[i]);
            Image temp = image.getImage().getScaledInstance(jbn[i].getWidth(), 
            		jbn[i].getHeight(), Image.SCALE_SMOOTH);
            image = new ImageIcon(temp);
            jbn[i].setIcon(image);
            String fileName = array[i].substring(array[i].lastIndexOf("/")+1, array[i].lastIndexOf(".png"));    
            jbn[i].setActionCommand(fileName);   // 添加Button添加动作命令名
            jbn[i].addActionListener(tool_listener);  // 为button添加监听
            this.add(jbn[i]);  
        }  
        this.setBackground(null);  
    }  
    
    /**
     * 工具监听,返回工具的动作名称
     */
    private ActionListener tool_listener = new ActionListener() {   
        public void actionPerformed(ActionEvent e) {  
            shape_command =e.getActionCommand();  
        }  
    };  
    
    /**
     * 获取工具命令 shape_command
     * @return shape_command
     */
    public String getCommand(){
    	return shape_command;
    }
    
}  
