package miniCadPad;

import java.awt.*;    
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import shapes.Shape; 


/**
 * 菜单类
 * @author suntfen@163.com
 *
 */
public class CadMenuBar extends JMenuBar{
	private static final long serialVersionUID = 1L;
	
	private ObjectInputStream  input;
	private ObjectOutputStream output;
	private DrawPanel dpl;
	private boolean updateflag = false;
	private boolean saveflag = false;
	private File fileName;
	
	public CadMenuBar(DrawPanel dpl){
		this.dpl = dpl;
		init();
	}
	
	private void init(){
		/**
		  * 文件菜单
		  */
		 JMenu fileMenu = new JMenu(" 文件 ");
		 fileMenu.setFont(new Font("微软雅黑",1,15));
		 // 新建
		 JMenuItem newItem = new JMenuItem("  新建",
				 new ImageIcon("src/icon/menu/new.png"));
		 newItem.setFont(new Font("微软雅黑",1,15));
		 newItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newfile();
			}
		});
		 fileMenu.add(newItem);
		 // 保存
		 JMenuItem saveItem = new JMenuItem("  保存",
				 new ImageIcon("src/icon/menu/save.png"));
		 saveItem.setFont(new Font("微软雅黑",1,15));
		 saveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				savefile();
			}
		});
		 fileMenu.add(saveItem);
		// 另存为
		 JMenuItem saveasItem = new JMenuItem("  另存为",
				 new ImageIcon("src/icon/menu/save.png"));
		 saveasItem.setFont(new Font("微软雅黑",1,15));
		 saveasItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveasfile();
			}
		});
		 fileMenu.add(saveasItem);
		 // 打开
		 JMenuItem loadItem = new JMenuItem("  打开",
				 new ImageIcon("src/icon/menu/load.png"));
		 loadItem.setFont(new Font("微软雅黑",1,15));
		 loadItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadfile();
			}
		});
		 fileMenu.add(loadItem);
		 fileMenu.addSeparator();
		 
		 // 退出
		 JMenuItem exitItem = new JMenuItem("  退出",
				 new ImageIcon("src/icon/menu/exit.png"));
		 exitItem.setFont(new Font("微软雅黑",1,15));
		 exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i = JOptionPane.YES_OPTION;
				if (dpl.getShapeNum()!=0&&updateflag)
					i = JOptionPane.showConfirmDialog(null,"当前图片尚未保存，是否确认退出？","",JOptionPane.YES_NO_OPTION);
				if(i == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		 fileMenu.add(exitItem);
		 this.add(fileMenu);
		 
		 /**
		  * 编辑菜单
		  */
		 JMenu editMenu = new JMenu(" 编辑 ");
		 editMenu.setFont(new Font("微软雅黑",1,15));
		 // 画布大小
		 JMenuItem canvasItem = new JMenuItem("  画布大小",
				 new ImageIcon("src/icon/menu/canvas.png"));
		 canvasItem.setFont(new Font("微软雅黑",1,15));
		 canvasItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CanvasSizeOption csp = new CanvasSizeOption(dpl);
				dpl.setSize(csp.getWidth(), csp.getHeight());
			}
		});
		 editMenu.add(canvasItem);
		 this.add(editMenu);
		 
		 /**
		  * 帮助菜单
		  */
		 JMenu helpMenu = new JMenu(" 帮助 ");
		 helpMenu.setFont(new Font("微软雅黑",1,15));
		 // 关于
		 JMenuItem aboutItem = new JMenuItem("  关于",
				 new ImageIcon("src/icon/menu/about.png"));
		 aboutItem.setFont(new Font("微软雅黑",1,15));
		 aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,
                      "这是一个简单的画图软件！\n"
                      + "Java应用基础：项目实战篇",
                       "关于", JOptionPane.INFORMATION_MESSAGE );
			}
		});
		 helpMenu.add(aboutItem);
		 this.add(helpMenu);
	}
	
	/**
	 * 另存为文件
	 */
	public void saveasfile(){
		JFileChooser fileChooser=new JFileChooser();
	    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    int result =fileChooser.showSaveDialog(this);
	    if(result==JFileChooser.CANCEL_OPTION)
	             return ;
	    fileName=fileChooser.getSelectedFile();
	    fileName.canWrite();

	    if (fileName==null||fileName.getName().equals(""))
	    JOptionPane.showMessageDialog(fileChooser,"Invalid File Name",
	            "Invalid File Name", JOptionPane.ERROR_MESSAGE);
	    else{
	      try {
	       fileName.delete();
	       FileOutputStream fos=new FileOutputStream(fileName);

	       output=new ObjectOutputStream(fos);
	       
	       output.writeInt( dpl.getWidth() );
	       output.writeInt( dpl.getHeight() );
	       output.writeInt( dpl.getShapeNum() );
	       
	       for(int i=0;i< dpl.getShapeNum() ;i++)
	       {
	            Shape s = dpl.getShape(i);
		        output.writeObject(s);
		        output.flush();
		        }
	       output.close();
	       fos.close();
	       updateflag = false;
	       saveflag = true;
	       }catch(IOException ioe){
	    	   ioe.printStackTrace();
	    	   }
	      }
	    }
	
	/**
	 * 保存文件
	 */
	public void savefile(){
		if(saveflag)
		{
			try {
				fileName.delete();
				FileOutputStream fos=new FileOutputStream(fileName);
				output=new ObjectOutputStream(fos);
				
				output.writeInt( dpl.getWidth() );
				output.writeInt( dpl.getHeight() );
				output.writeInt( dpl.getShapeNum() );
				
				for(int i=0;i< dpl.getShapeNum() ;i++)
				{
					Shape s = dpl.getShape(i);
					output.writeObject(s);
					output.flush();
					}
				output.close();
				fos.close();
				updateflag = false;
				}catch(IOException ioe){
					ioe.printStackTrace();
					}
		}
		else
			saveasfile();
	}
	
	
	/**
	 * 打开文件
	 */
	public void loadfile(){
		int flag =0;
		if (dpl.getShapeNum()!=0&&updateflag)
			flag = JOptionPane.showConfirmDialog(null,"当前图片尚未保存，是否确认打开其他图片？","",JOptionPane.YES_NO_OPTION);
		if(flag==0)
		{
			JFileChooser fileChooser=new JFileChooser();
		    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    int result =fileChooser.showOpenDialog(this);
		    if(result==JFileChooser.CANCEL_OPTION)
		          return ;
		    fileName=fileChooser.getSelectedFile();
		    fileName.canRead();
		    if (fileName==null||fileName.getName().equals(""))
		       JOptionPane.showMessageDialog(fileChooser,"Invalid File Name",
		            "Invalid File Name", JOptionPane.ERROR_MESSAGE);
		    else {
		    	try {
		    		FileInputStream fis=new FileInputStream(fileName);
		    		input=new ObjectInputStream(fis);
		    		dpl.clearShape();
		    		Shape inputRecord;
		    		int twidth = input.readInt();
		    		int theight = input.readInt();
		    		dpl.setSize(twidth,theight);
		    		int countNumber=input.readInt();
		    		for(int i=0 ;i<countNumber ;i++)
		    		{
		    			inputRecord=(Shape)input.readObject();
		    			dpl.add(inputRecord);
		    			}
		    		input.close();
		    		dpl.repaint();
		    		saveflag = true;
			    	updateflag = false;
		    		}catch(EOFException endofFileException){
		    			JOptionPane.showMessageDialog(this,"no more record in file",
		    					"class not found",JOptionPane.ERROR_MESSAGE );
		    			}catch(ClassNotFoundException classNotFoundException){
		    				JOptionPane.showMessageDialog(this,"Unable to Create Object",
		    						"end of file",JOptionPane.ERROR_MESSAGE );
		    				}catch (IOException ioException){
		    					JOptionPane.showMessageDialog(this,"error during read from file",
		    							"read Error",JOptionPane.ERROR_MESSAGE );
		    					}
		    	}
		    }
		}
	
	/**
	 * 新建文件
	 */
	public void newfile(){
		int i =0;
		if (dpl.getShapeNum()!=0&&updateflag)
			i = JOptionPane.showConfirmDialog(null,"当前图片尚未保存，是否确认新建？","",JOptionPane.YES_NO_OPTION);
		if(i==0)
		{
			dpl.clearShape();
    		dpl.repaint();
    		updateflag = false;
    		saveflag = false;
		}
	}
	
	public void setUpdateflag(boolean f){
		updateflag = f;
	}
	
	public boolean getUpdateflag(){
		return updateflag;
	}
}
