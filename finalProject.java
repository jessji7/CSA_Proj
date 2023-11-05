import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import sun.audio.*;
import java.io.File;

public class finalProject
{
	public static void main (String...args)
	{
		JFrame j = new JFrame();
		MyPanel m = new MyPanel();
		j.setSize(m.getSize());
		j.add(m); //adds the panel to the frame so that the picture will be drawn
			      //use setContentPane() sometimes works better then just add b/c of greater efficiency.
		j.addKeyListener(m); 
	    j.addMouseListener(m);
	    j.setVisible(true); //allows the frame to be shown.
		playAudio("birdSong.wav"); //plays the sound
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //makes the dialog box exit when you click the "x" button.
	}
		
	public static void playAudio(String filename)
	{
		InputStream in = null;
		AudioStream as = null;
		try
		{
			//create audio data source
			in = new FileInputStream(filename);
		}
		catch(FileNotFoundException fnfe){System.out.println("The audio file was not found");}
		try
		{
		//create audio stream from file stream
		as = new AudioStream(in);
		}
		catch(IOException ie){System.out.println("Audio stream could not be created");}

		AudioPlayer.player.start(as);
		
	}
}

class MyPanel extends JPanel implements ActionListener, KeyListener, MouseListener 
{
	private Timer time;
	private int moveImage;
	private int x,y; //coordinates for the image
	private int colorBackground;
	private int x1,y1,x2,y2,x3,y3,x4,y4; //coordinates for the objects
	private int add1,add2,add3,add4; //variables to change the positions of the objects or attributes
	private int move1,flash,move3,move4; //variables for different aspects of the objects
	
	MyPanel()
	{
		time = new Timer(15, this); //sets delay to 15 millis and calls the actionPerformed of this class.
		setSize(1600, 1000);
		setVisible(true); //it's like calling the repaint method.
		time.start();
		moveImage = 0;
		x = 850;
		y = 600;
		colorBackground = 0;
		add1 = 3;
		add2 = 1;
		add3 = 5;
		add4 = 4;
		x1 = 0;
		y1 = 970;
		x2 = 1498;
		y2 = 101;
		x3 = 500;
		y3 = 90;
		x4 = 550;
		y4 = 830;
		move1 = 4; //determines the coordinates of wing flap
		flash = 1; //flashing of the sun's rays
		move3 = 10; //timing of cloud's movement
		move4 = 15; //timing of the grass movement
	}


	public void paintComponent(Graphics g)
	{
		switch (colorBackground)
		{
			case 0: 	g.setColor(new Color(0, 150, 150)); break;
			case 1: 	g.setColor(new Color(150, 150, 0)); break;
			case 2: 	g.setColor(new Color(100, 153, 255)); break;
		}
		g.fillRect(0,0,1600,1000); //draws a filled-in rectangle as the background
		
		switch (moveImage)
		{
			case 1: 	if (y>=100) y -= 100; break; //moves image up
			case 2: 	if (y<=550) y += 100; break; //moves image down
			case 3: 	if (x>=100) x -= 100; break; //moves image left
			case 4:     if (x<=800) x += 100; break; //moves image right
		}
		
		try
		{
			Image chirp = ImageIO.read(new File("birds-singing.jpg")); //image is 700 x 329 pixels
			g.drawImage(chirp, x, y, null); //draws the imported image
		}
		catch(Exception e)	{}
		
		drawSun(g,x2,y2); //draws sun
		drawCloud(g,x3,y3); //draws cloud
		drawGrass(g,x4,y4); //draws grass patch #1
		drawGrass(g,x4-100,y4); //draws grass patch #2
		drawGrass(g,x4-250,y4); //draws grass patch #3
		drawGrass(g,x4-450,y4); //draws grass patch #4
		drawBird(g,x1,y1); //draws bird
		
		//displays text
		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font ("Arial", Font.BOLD, 25));
        g.drawString("Use the arrow keys to move the image in all four directions", 70,730);
        g.drawString("Click to change background color", 70,760);
	}
	
	public void drawBird(Graphics g, int x1, int y1)
	{
		g.setColor(Color.BLACK);
		g.drawLine(x1+35,y1-20,x1+45,y1-45);//leg
		g.drawLine(x1+55,y1-45,x1+65,y1-20);//leg
		g.setColor(Color.PINK);
		g.fillOval(x1+50,y1-100,20,20); //head
		g.fillOval(x1+70,y1-92,12,6);//beak
		g.fillOval(x1+30,y1-83,40,45);//body
		g.fillOval(x1,y1-75+move1,35,20);//wing
		g.fillOval(x1+65,y1-75+move1,35,20);//wing
		g.setColor(Color.BLACK);
		g.fillOval(x1+62,y1-95,4,4);//eye
		move1 *= -1;
	}
	
	public void drawSun(Graphics g, int x2, int y2)
	{
		g.setColor(Color.YELLOW);
		if (flash == 0)
		{
			//rays
			g.drawLine(x2-60,y2-60,x2+60,y2+60);
			g.drawLine(x2+60,y2-60,x2-60,y2+60);
			g.drawLine(x2,y2-85,x2,y2+85);
			g.drawLine(x2-85,y2,x2+85,y2);
			flash = 6;	
		}
		g.fillOval(x2-50,y2-50,100,100); //center of the sun
		g.setColor(Color.BLACK);
		g.fillOval(x2-20,y2-20,10,10); //left eye
		g.fillOval(x2+15,y2-20,10,10); //right eye
		g.drawArc(x2-20,y2,45,30,180,180); //mouth
		flash--;
	}
	
	public void drawCloud(Graphics g, int x3, int y3)
	{
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval(x3+add3,y3,152,40);
		g.fillOval(x3+30+add3,y3-30,50,50);
		g.fillOval(x3+75+add3,y3-25,50,50);
	}
	
	public void drawGrass(Graphics g, int x4, int y4)
	{
		g.setColor(Color.GREEN);
		g.fillArc(x4-5+add4,y4+add4/2,30,140,320,170);
		g.fillArc(x4+25+add4,y4-40+add4/2,30,180,70,170);
		g.fillArc(x4+40+add4,y4-20+add4/2,30,160,60,180);
	}

	public void actionPerformed(ActionEvent e)
	{
		repaint(); //calls paintComponent method
		//for bird
		x1+=add1;
		y1-=add1;
		if(x1+100>=1599 || y1-100<=0 || x1<=0 || y1>=970)
			add1*=-1;
		//for sun	
		x2+=add2;
		if(x2+100>=1599 || x2-100<=0)
				add2*=-1;
		//for cloud		
		if (move3==0)
		{
			add3*=-1;
			move3=10;
		}
		else
		{
			move3--;
		}
		//for grass
		if (move4==0)
		{
			add4*=-1;
			move4=15;
		}
		else
		{
			move4--;
		}
			
		moveImage = 0; //sets to 0 so that the image does not keep moving every 15 millis
	}
	
	public void mouseClicked(MouseEvent e)
	{
		colorBackground = (colorBackground + 1) % 3; //changes the colorBackground variable every time the mouse is clicked
	}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	
	public void keyPressed(KeyEvent e)
	{
		  int code = e.getKeyCode();

		  switch (code)
		  {
		  	case 38: moveImage = 1; break; //up
		  	case 40: moveImage = 2; break; //down
		  	case 37: moveImage = 3; break; //left
		  	case 39: moveImage = 4; break; //right
		  }

	}

	public void keyReleased(KeyEvent e)	{}
	public void keyTyped(KeyEvent e){}
}
