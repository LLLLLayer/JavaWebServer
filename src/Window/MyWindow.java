package Window;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Server.MyServer;
import javax.swing.JTextField;
import java.awt.TextArea;
import java.awt.Label;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;


public class MyWindow extends JFrame {//主界面显示

	private JPanel contentPane;
	private JTextField urlIn;
	private JTextField portIn;
	private String URL;
	private String PORT;
	StringBuffer strBuf;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyWindow frame = new MyWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MyWindow() {
		setTitle("MyWebServer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 561, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//TextArea textShow = new TextArea();
		TextArea textShow=new ConsoleText();
		textShow.setBounds(10, 146, 507, 297);
		contentPane.add(textShow);
		
		Label titileShow = new Label("MyWebServer1.0");
		titileShow.setAlignment(Label.CENTER);
		titileShow.setBounds(10, 10, 523, 34);
		contentPane.add(titileShow);
		
		Label urlLab = new Label("URL:");
		urlLab.setBounds(10, 50, 53, 25);
		contentPane.add(urlLab);
		
		Label portLab = new Label("PORT:");
		portLab.setBounds(10, 81, 53, 25);
		contentPane.add(portLab);
		
		urlIn = new JTextField();
		urlIn.setToolTipText("");
		urlIn.setBounds(86, 50, 292, 24);
		contentPane.add(urlIn);
		urlIn.setColumns(10);
		
		portIn = new JTextField();
		portIn.setBounds(86, 82, 292, 24);
		contentPane.add(portIn);
		portIn.setColumns(10);
		
		JButton urlBtn = new JButton("FileChoose");//选择目录
		urlBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    JFileChooser jfc=new JFileChooser();
			    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    jfc.showDialog(new JLabel(), "Choose");
			    File file=jfc.getSelectedFile();
			    System.out.println(file.getAbsolutePath());
			    urlIn.setText(file.getAbsolutePath());
			}
		});
		urlBtn.setBounds(404, 50, 113, 27);
		contentPane.add(urlBtn);
		
		JButton clearBtn = new JButton("Clear");//清除输入
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				urlIn.setText("");
				portIn.setText("");
			}
		});
		clearBtn.setBounds(277, 113, 240, 27);
		contentPane.add(clearBtn);
		
		JButton startBtn = new JButton("Start");//启动服务器
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URL=urlIn.getText();
				PORT=portIn.getText();
				strBuf=new StringBuffer();
				MyServer newServer=new MyServer(Integer.valueOf(PORT).intValue(),strBuf);
				newServer.fileHead=URL;
				new Thread(newServer).start();
			
				while(strBuf.length()==0) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				String str=strBuf.toString();
				textShow.append(str);
			}
		});
		startBtn.setBounds(20, 112, 240, 27);
		contentPane.add(startBtn);
		
		JButton defaultBtn = new JButton("Default");//填入默认参数 
		defaultBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				urlIn.setText("F:/Ecl/eclipse-workspace/WebServer/src/Res");
				portIn.setText("80");
			}
		});
		defaultBtn.setBounds(404, 81, 113, 27);
		contentPane.add(defaultBtn);
	}
	
	public void startServer() {
		MyServer newServer=new MyServer(Integer.valueOf(PORT).intValue(),strBuf);
		newServer.fileHead=URL;
		new Thread(newServer).start();
	}
}
