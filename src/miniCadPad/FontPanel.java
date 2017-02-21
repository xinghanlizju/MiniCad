package miniCadPad;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * 字体面板
 * @author suntfen@163.com
 *
 */
public class FontPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JCheckBox isbold,isitalic;	// 是否加粗,斜体的CheckBox
	private JComboBox<String> styles;	// 字体选项的下拉菜单
	private JComboBox<String> fontsizes;	// 字号的下拉菜单
	private String styleNames[]={"微软雅黑", "宋体", "楷体", "Times New Roman" , "Serif" ,
			"Monospaced" ,"SonsSerif" , "Garamond"};	// 字体的备选内容
    private String []fontNum = new String[500];	// 字号的备选内容
	
	public FontPanel(){
		for (int i = 0;i<fontNum.length;i++)
	    	fontNum[i] = Integer.toString(i+1);
		init();
	}
	private void init(){
		this.setPreferredSize(new Dimension(600,38));
		this.setLayout(null);
	    
	    // 设置加粗的CheckBox
		isbold=new JCheckBox("加粗");	// CheckBox的名称
		isbold.setFont(new Font("微软雅黑",1,15));	// CheckBox的显示字体
		isbold.setBackground(null);	// CheckBox的背景
		isbold.setBounds(450, 0, 70, 38);
		this.add(isbold);
	    
	    // 设置斜体的CheckBox
		isitalic=new JCheckBox("斜体");	// CheckBox的名称
		isitalic.setFont(new Font("微软雅黑",3,15));	// CheckBox的显示字体
		isitalic.setBackground(null);	// CheckBox的背景
		isitalic.setBounds(540, 0, 70, 38);
	    this.add(isitalic);
	    
	    // 字体框占位
	    JTextField jf2 = new JTextField("字体：");
	    jf2.setFont(new Font("微软雅黑",1,15));
	    jf2.setBounds(20, 0, 55, 38);
	    jf2.setBackground(null);
	    jf2.setEditable(false);
	    jf2.setBorder(new LineBorder(new Color(0,0,0,0)));
	    this.add(jf2);
	    
	    // 设置字体下拉菜单
	    styles=new JComboBox<String>(styleNames);
	    styles.setFont(new Font("微软雅黑",1,15));	// 下拉菜单显示字体
	    styles.setSelectedIndex(0);	// 下拉菜单当前选项
	    styles.setMaximumRowCount(8);	// 下拉菜单最多显示行数
	    styles.setBackground(null);	// 下拉菜单背景颜色
	    styles.setBounds(75, 7, 175, 26);
	    this.add(styles);
	    
	    // 字号框占位
	    JTextField jf3 = new JTextField("字号：");
	    jf3.setFont(new Font("微软雅黑",1,15));
	    jf3.setBounds(270, 0, 55, 38);
	    jf3.setBackground(null);
	    jf3.setEditable(false);
	    jf3.setBorder(new LineBorder(new Color(0,0,0,0)));
	    this.add(jf3);
	    
	    // 设置字体下拉菜单
	    fontsizes=new JComboBox<String>(fontNum);
	    fontsizes.setFont(new Font("微软雅黑",1,15));	// 下拉菜单显示字体
	    fontsizes.setSelectedIndex(59);	// 下拉菜单当前选项
	    fontsizes.setMaximumRowCount(8);	// 下拉菜单最多显示行数
	    fontsizes.setBackground(null);	// 下拉菜单背景颜色
	    fontsizes.setBounds(325, 7, 75, 26);
	    this.add(fontsizes);
	}
	
	/**
	 * 获取加粗属性
	 * @return bold
	 */
	public int getBold(){
		int bold;
		if(isbold.isSelected())
			bold = Font.BOLD;
		else
			bold = Font.PLAIN;
		return bold;
	}
	
	/**
	 * 获取斜体属性
	 * @return italic
	 */
	public int getItalic(){
		int italic;
		if(isitalic.isSelected())
			italic = Font.ITALIC;
		else
			italic = Font.PLAIN;
		return italic;
	}
	
	/**
	 * 获取字体
	 * @return font
	 */
	public String getFontVal(){
		return styleNames[styles.getSelectedIndex()];
	}
	 
	/**
	 * 获取字号
	 * @return fontsize
	 */
	public int getFontSize(){
		return Integer.parseInt(fontNum[fontsizes.getSelectedIndex()]);
	}
	
	/**
	 * 设置加粗CheckBox的显示
	 * @param b
	 */
	public void setBoldBox(int b){
		if(b==Font.BOLD)
			isbold.setSelected(true);
		else
			isbold.setSelected(false);
	}
	
	/**
	 * 设置斜体CheckBox的显示
	 * @param b
	 */
	public void setItalicBox(int b){
		if(b==Font.ITALIC)
			isitalic.setSelected(true);
		else
			isitalic.setSelected(false);
	}
	
	/**
	 * 设置字体下拉菜单的显示
	 * @param s
	 */
	public void setStylesBox(String s){
		int i;
		for(i = 0;i<styleNames.length;i++)
			if(s.equals(styleNames[i]))
				break;
		styles.setSelectedIndex(i);
	}
	
	/**
	 * 设置字号下拉菜单的显示
	 * @param size
	 */
	public void setSizeBox(int size){
		int i;
		for(i = 0;i<fontNum.length;i++)
			if(size==Integer.parseInt(fontNum[i]))
				break;
		fontsizes.setSelectedIndex(i);
	}
	
	public JCheckBox getisBold(){
		return isbold;
	}
	
	public JCheckBox getisItalic(){
		return isitalic;
	}
	
	public JComboBox<String> getFontSizeBox(){
		return fontsizes;
	}
	
	public JComboBox<String> getStylesBox(){
		return styles;
	}
}
