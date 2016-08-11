import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.imageio.ImageIO;
import javax.swing.JCheckBox;

public class Cele
{
	private Color[] wzorZolty = new Color[] { new Color(254, 255, 0), new Color(169, 140, 45), new Color(194, 161, 52), new Color(194, 161, 52),
			new Color(213, 177, 57), new Color(45, 40, 25), new Color(68, 61, 37), new Color(213, 177, 57), new Color(213, 177, 57), new Color(213, 177, 57),
			new Color(75, 62, 20), new Color(93, 90, 68) };
	private int[] wzorSzZolty = new int[wzorZolty.length];
	private Color[] wzorBialy = new Color[] { new Color(255, 255, 255), new Color(169, 140, 45), new Color(194, 161, 52), new Color(194, 161, 52),
			new Color(213, 177, 57), new Color(45, 40, 25), new Color(68, 61, 37), new Color(213, 177, 57), new Color(213, 177, 57), new Color(213, 177, 57),
			new Color(75, 62, 20), new Color(93, 90, 68) };
	private int[] wzorSzBialy = new int[wzorZolty.length];

	private Robot robot;
	private JCheckBox wylacznik;
	private Grafika gg;

//	private int xPowroc, yPowroc;
	private int xp, yp;

	// x 600 - 1200 y 400 - 600
	public Cele(JCheckBox wyl, Grafika ggt)
	{
		wylacznik = wyl;
		gg = ggt;
		for (int i = 0; i < wzorZolty.length; i++)
		{
			wzorSzZolty[i] = wzorZolty[0].getRGB();
			wzorSzBialy[i] = wzorBialy[0].getRGB();
		}

		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}

		testuj1();

	}

	private void testuj1()
	{
		
		new Thread()
		{
			Color tmp;
			boolean jestZolty = false;

			@Override
			public void run()
			{
				super.run();

				while (wylacznik.isSelected())
				{

				
					BufferedImage bi = robot.createScreenCapture(obszarSzukania());

					
					int width = bi.getWidth();
					int height = bi.getHeight();
					int[][] result = new int[height][width];

					for (int row = 0; row < height; row++)
					{
						for (int col = 0; col < width; col++)
						{

							result[row][col] = bi.getRGB(col, row);
						}
					}
					int[][] tab = result;
					
					jestZolty = false;
					DOROBOTYZOLTY:
					for (int row = 0; row < tab.length; row++)
					{
						for (int col = 0; col < tab[row].length; col++)
						{
							if (tab[row][col] == wzorSzZolty[0])
							{
								for (int xx = col + 1; xx < col + wzorSzZolty.length - 3; xx++)
								{
									if (tab[row][xx] == wzorSzZolty[xx - col])
									{

										if (xx == col + wzorZolty.length - 4)
										{
											robot.mouseMove(xp + col, yp + row);
//											robot.mousePress(InputEvent.BUTTON1_MASK);
//											robot.mouseRelease(InputEvent.BUTTON1_MASK);
//											System.out.println("row" + row + " col" + col + " xx" + xx + "CLICK");
//											gg.powroc();
											jestZolty = true;
											break DOROBOTYZOLTY;
													
										}
									}
									else
									{

										break;
									}
								}

							}

						}

					} // for loop
					
					if(!jestZolty)
					{
						DOROBOTYBIALY:
							for (int row = 0; row < tab.length; row++)
							{
								for (int col = 0; col < tab[row].length; col++)
								{
									if (tab[row][col] == wzorSzBialy[0])
									{
										for (int xx = col + 1; xx < col + wzorSzBialy.length - 3; xx++)
										{
											if (tab[row][xx] == wzorSzBialy[xx - col])
											{
	
												if (xx == col + wzorBialy.length - 4)
												{
													robot.mouseMove(xp + col, yp + row);
	//												robot.mousePress(InputEvent.BUTTON1_MASK);
	//												robot.mouseRelease(InputEvent.BUTTON1_MASK);
	//												System.out.println("row" + row + " col" + col + " xx" + xx + "CLICK");
	//												gg.powroc();
													break DOROBOTYBIALY;
												}
											}
											else
											{
												break;
											}
										}
	
									}
	
								}
	
							}
					}
	
						try
						{
							if(jestZolty)
								Thread.sleep(1_000);
							else
								Thread.sleep(500);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
						
					}// while loop
				
			}

		}.start();

	}

	private Rectangle obszarSzukania(){
		xp = gg.getXp();
		int xwym = gg.getXk()-xp;
		yp = gg.getYp();
		int ywym = gg.getYk()-yp;
		System.out.println("600:"+xp +" 400:"+yp +" 600:"+xwym +" 200:"+ywym);
		
		return new Rectangle(xp, yp, xwym, ywym);
	}
}