import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Heroes
{
	private int x, y;
	private Robot robot;

	Heroes(int aktualny)
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
	
	void rekrutuj()
	{
		robot.mouseMove(x, y);
		robot.keyPress(KeyEvent.VK_Z);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.keyRelease(KeyEvent.VK_Z);

	}

}
