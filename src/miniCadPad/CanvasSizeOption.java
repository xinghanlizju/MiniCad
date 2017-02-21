package miniCadPad;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class CanvasSizeOption extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private int width = 800;
	private int height = 600;
	private DrawPanel dpl;
 
    public CanvasSizeOption(DrawPanel dpl) {
    	this.dpl = dpl;
    	width = dpl.getWidth();
    	height = dpl.getHeight();
    	init();
    }
    
    private void init(){
    	super.setLayout(null);
    	
    	
    	// 顶部
        JPanel jpl1 = new JPanel();
        jpl1.setLayout(null);
        JLabel jbl = new JLabel("设置画布大小");
        jbl.setFont(new Font("微软雅黑",1,18));
        jbl.setBounds(80,5,120,40);
        jpl1.add(jbl);
        jpl1.setBounds(0,0,280,50);
        super.add(jpl1);
        
        //中部表单
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(null);
        JLabel a1 = new JLabel("水平大小:");
        a1.setFont(new Font("微软雅黑",1,15));
        a1.setBounds(60,5,80,20);
        JLabel a2 = new JLabel("垂直大小:");
        a2.setFont(new Font("微软雅黑",1,15));
        a2.setBounds(60,35,80,20);
        fieldPanel.add(a1);
        fieldPanel.add(a2);
        JTextField widthtext = new JTextField(Integer.toString(width));
        JTextField heighttext = new JTextField(Integer.toString(height));
        widthtext.setBounds(150,5,60,20);
        heighttext.setBounds(150,35,60,20);
        fieldPanel.add(widthtext);
        fieldPanel.add(heighttext);
        
        JLabel a3 = new JLabel("> 0");
        JLabel a4 = new JLabel("> 0");
        a3.setFont(new Font("微软雅黑",1,12));
        a4.setFont(new Font("微软雅黑",1,12));
        a3.setBounds(215,5,100,20);
        a4.setBounds(215,35,100,20);
        a3.setForeground(Color.RED);
        a4.setForeground(Color.RED);
        a3.setVisible(false);
        a4.setVisible(false);
        fieldPanel.add(a3);
        fieldPanel.add(a4);
        fieldPanel.setBounds(0,50,280,80);
        super.add(fieldPanel);
        
        widthtext.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				if (isNumeric(widthtext.getText()))
				{
					int size = Integer.parseInt(widthtext.getText());
					if(size>0)
						a3.setVisible(false);
					else
						a3.setVisible(true);
				}else
					a3.setVisible(true);
			}
		});
        heighttext.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				if (isNumeric(heighttext.getText()))
				{
					int size = Integer.parseInt(heighttext.getText());
					if(size>0)
						a4.setVisible(false);
					else
						a4.setVisible(true);
				}else
					a4.setVisible(true);
			}
		});
	    
        //底部按钮
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        JButton confirm = new JButton("确定");
        confirm.setFont(new Font("微软雅黑",1,15));
        JButton cancel = new JButton("取消");
        cancel.setFont(new Font("微软雅黑",1,15));
        confirm.setBounds(45,0,75,25);
        cancel.setBounds(160,0,75,25);
        buttonPanel.add(confirm);
        buttonPanel.add(cancel);
        buttonPanel.setBounds(0,130,280,50);
        super.add(buttonPanel);
        
        confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isNumeric(widthtext.getText())&&isNumeric(heighttext.getText()))
				{
					int wtemp = Integer.parseInt(widthtext.getText());
					int htemp = Integer.parseInt(heighttext.getText());
					if(htemp>0&&wtemp>0)
					{
						width = wtemp;
						height = htemp;
						dpl.setSize(new Dimension(width, height));
						dpl.repaint();
						a3.setVisible(false);
						a4.setVisible(false);
						dispose();
					}
					else
						JOptionPane.showMessageDialog(null, "输入有问题！", 
								null,JOptionPane.ERROR_MESSAGE);
				}else
					JOptionPane.showMessageDialog(null, "输入有问题！", 
							null,JOptionPane.ERROR_MESSAGE);
			}
		});
        
        cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
     
	    super.setTitle("画布大小");
	    super.setFont(new Font("微软雅黑",1,15));
	    super.setSize(280,205);
	    super.setResizable(false);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setLocationRelativeTo(null);
		super.setVisible(true);
    }
    
    /**
	 * 设置水平
	 * @param width
	 */
	public void setWidth(int width){
		this.width = width;
	}
	
	/**
	 * 设置高度
	 * @param height
	 */
	public void setHeight(int height){
		this.height = height;
	}
	
	/**
	 * 获取水平值
	 * @return width
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * 获取高度
	 * @return height
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * 判断字符串是否全为数字
	 * @param str
	 * @return true or false
	 */
	public boolean isNumeric(String str){
		if (str.length()==0)
			return false;
		else{
			for (int i = 0; i < str.length(); i++)
			{
				System.out.println(str.charAt(i));
				if (!Character.isDigit(str.charAt(i)))
					return false;
			}
			return true;
		}
	}
}