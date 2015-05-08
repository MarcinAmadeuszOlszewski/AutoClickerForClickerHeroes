import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.KeyEvent;


public class Farma
{
	private int x, y;
	private Robot robot;
	private int czerwony =0;
	private Color czerwonyKolor = new Color(255,0,0);
	private boolean flaga = false;

	Farma()
	{
		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}
	
	void aktywuj(){
		robot.keyPress(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_A);
	}
	
	void sprawdz(){
		for(int i=x-20; i<x+20; i++)
		{
			if(robot.getPixelColor(i, y).equals(czerwonyKolor)){
				czerwony++;
			}
			if(czerwony==4){
				if(flaga)
				{
					aktywuj();
//					System.out.println("A A A");
					flaga = false;
				}
				else
				{
					flaga = true;
				}
				
				czerwony=0;
				break;
			}
		
		}

		
	}
}
