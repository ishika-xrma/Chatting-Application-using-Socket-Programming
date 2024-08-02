package chatting.application;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.net.*;
import java.io.*;

public class Server implements ActionListener{
	JTextField t1;
	JPanel a1;
	static Box vertical =Box.createVerticalBox();
	static JFrame f = new JFrame();
	static DataOutputStream dout;
	
	
	Server(){
		f.setSize(400,600);
		f.setLayout(null);
		JPanel p1 = new JPanel();
		p1.setBackground(new Color(106,60,247));
		p1.setBounds(0,0,400,70);
		p1.setLayout(null);
		f.add(p1);
		
		JPanel lside = new JPanel();
		lside.setBackground(new Color(106,60,247));
		lside.setBounds(0,70,10,600);
		f.add(lside);
		
		JPanel rside = new JPanel();
		rside.setBackground(new Color(106,60,247));
		rside.setBounds(390,70,10,600);
		f.add(rside);
		
		JPanel bottom = new JPanel();
		bottom.setBackground(new Color(106,60,247));
		bottom.setBounds(0,590,400,10);
		f.add(bottom);
		
		ImageIcon i1 = new ImageIcon(this.getClass().getResource("/3.png"));
		Image i2=i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i3=new ImageIcon(i2);
		
		JLabel back = new JLabel(i3);
		back.setBounds(10,20,25,25);
		p1.add(back);
		back.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent ae) {
				System.exit(0);
			}
		});
		
		ImageIcon i4 = new ImageIcon(this.getClass().getResource("/1.png"));
		Image i5=i4.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon i6=new ImageIcon(i5);
		JLabel profile = new JLabel(i6);
		profile.setBounds(40,10,50,50);
		p1.add(profile);
		
		ImageIcon i7 = new ImageIcon(this.getClass().getResource("/phone.png"));
		Image i8=i7.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		ImageIcon i9=new ImageIcon(i8);
		JLabel phone = new JLabel(i9);
		phone.setBounds(230,10,50,50);
		p1.add(phone);
		
		ImageIcon i10 = new ImageIcon(this.getClass().getResource("/video.png"));
		Image i11=i10.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
		ImageIcon i12=new ImageIcon(i11);
		JLabel vid = new JLabel(i12);
		vid.setBounds(285,10,50,50);
		p1.add(vid);
		
		ImageIcon i13 = new ImageIcon(this.getClass().getResource("/3dot.png"));
		Image i14=i13.getImage().getScaledInstance(10,25, Image.SCALE_SMOOTH);
		ImageIcon i15=new ImageIcon(i14);
		JLabel more = new JLabel(i15);
		more.setBounds(330,10,50,50);
		p1.add(more);
		
		JLabel name = new JLabel("User Name 1");
		name.setBounds(100,12,300,30);
		name.setFont(new Font("Franklin Gothic Medium Cond",Font.BOLD,22));
		name.setForeground(Color.WHITE);
		p1.add(name);
		
		JLabel status = new JLabel("Active Now...");
		status.setBounds(100,35,300,30);
		status.setFont(new Font("Calibri",Font.BOLD,12));
		status.setForeground(Color.WHITE);
		p1.add(status);
		
		a1 = new JPanel();
		a1.setBounds(10,80,380,465);
		f.add(a1);
		
		t1=new JTextField();
		t1.setBounds(17,550,280,35);
		t1.setFont(new Font("Calibri",Font.PLAIN,16));
		t1.setBackground(new Color(178,204,253));
		t1.setBorder(BorderFactory.createLineBorder(new Color(39,132,251), 2));
		f.add(t1);
		
		
		JButton send=new JButton("Send");
		send.setBounds(305,550,80,35);
		send.setBackground(new Color(39,132,251));
		send.setForeground(Color.WHITE);
		send.setFont(new Font("Franklin Gothic Demi Cond",Font.PLAIN,18));
		f.add(send);
		send.addActionListener(this);
		
		f.setLocation(150,30);
		f.getContentPane().setBackground(Color.WHITE);
		f.setUndecorated(true);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       
		
		
	}
	public void actionPerformed(ActionEvent ae) {
		try{
			String out=t1.getText();
		
			JPanel p2=formatlabel(out);
			
			a1.setLayout(new BorderLayout());
			
			JPanel right=new JPanel(new BorderLayout());
			right.add(p2,BorderLayout.LINE_END);
			vertical.add(right);
			vertical.add(Box.createVerticalStrut(15));
			
			a1.add(vertical,BorderLayout.PAGE_START);
			
			dout.writeUTF(out);
			
			
			t1.setText("");
			
			f.repaint();
			f.invalidate();
			f.validate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static JPanel formatlabel(String out) {
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		JLabel output=new JLabel("<html><p style=\"width: 140px\">" + out + "</p></html>");
		output.setFont(new Font("Lucida Sans Unicode",Font.BOLD, 14));
		output.setBackground(new Color(187,166,253));
		output.setForeground(Color.WHITE);
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(8,25,8,40));
		
		panel.add(output);
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf= new SimpleDateFormat("hh:mm");
		
		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		
		panel.add(time);
		
		return panel;
	}
	public static void main(String[] args) {
		new Server();
		
		try {
			ServerSocket skt = new ServerSocket(6001);
			while(true) {
				Socket s = skt.accept();
				DataInputStream din = new DataInputStream(s.getInputStream());
				dout = new DataOutputStream(s.getOutputStream());
				while(true) {
					String msg = din.readUTF();
					JPanel panel= formatlabel(msg);
					
					JPanel left = new JPanel(new BorderLayout());
					left.add(panel, BorderLayout.LINE_START);
					vertical.add(left);
					f.validate();
					
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
