package Window;
import java.awt.TextArea;
import java.io.*;
import javax.swing.*;

//重载TextArea,将控制台信息传入可视化界面中
public class ConsoleText extends TextArea{
	private static final long serialVersionUID = 1L;
	public ConsoleText(){
		LoopedStreams ls = null;
		try {
			ls = new LoopedStreams();
		} 
		catch (IOException e) { 
			Throwable blocke = null;
			blocke.printStackTrace();
		}
		PrintStream ps = new PrintStream(ls.getOutputStream(),true);
		System.setOut(ps);
		System.setErr(ps);
		startConsoleReaderThread(ls.getInputStream());
	} 
	private void startConsoleReaderThread(InputStream inStream){
		final BufferedReader br =new BufferedReader(new InputStreamReader(inStream));
		new Thread(new Runnable() {
			public void run() {
				StringBuffer sb = new StringBuffer();
				try {
					String s;
					while( (s=br.readLine()) != null) {
						sb.setLength(0);
						append(sb.append(s).append("\n").toString());
						setCaretPosition(getText().length());
					}
				}
				catch(Exception e) {
					e.printStackTrace();
				} 
			}
		}).start();
	}
}

