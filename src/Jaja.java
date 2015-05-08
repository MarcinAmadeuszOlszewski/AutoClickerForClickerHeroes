import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.imageio.ImageIO;
import javax.swing.JCheckBox;

public class Jaja
{
	private Color[] wzor = new Color[] { new Color(153, 126, 39), new Color(169, 140, 45), new Color(194, 161, 52), new Color(194, 161, 52),
			new Color(213, 177, 57), new Color(45, 40, 25), new Color(68, 61, 37), new Color(213, 177, 57), new Color(213, 177, 57), new Color(213, 177, 57),
			new Color(75, 62, 20), new Color(93, 90, 68) };
	private int[] wzorI = new int[wzor.length];

	private Robot robot;
	private JCheckBox wylacznik;
	private Grafika gg;

//	private int xPowroc, yPowroc;
	private int xp, yp;

	// x 600 - 1200 y 400 - 600
	public Jaja(JCheckBox wyl, Grafika ggt)
	{
		wylacznik = wyl;
		gg = ggt;
		for (int i = 0; i < wzor.length; i++)
		{
			wzorI[i] = wzor[i].getRGB();
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

					DOROBOTY:
					for (int row = 0; row < tab.length; row++)
					{
						for (int col = 0; col < tab[row].length; col++)
						{
							if (tab[row][col] == wzorI[0])
							{
								for (int xx = col + 1; xx < col + wzorI.length - 3; xx++)
								{
									if (tab[row][xx] == wzorI[xx - col])
									{

										if (xx == col + wzor.length - 4)
										{
											robot.mouseMove(xp + col, yp + row);
											robot.mousePress(InputEvent.BUTTON1_MASK);
											robot.mouseRelease(InputEvent.BUTTON1_MASK);
											System.out.println("row" + row + " col" + col + " xx" + xx + "CLICK");
											gg.powroc();
											break DOROBOTY;
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

					try
					{
						Thread.sleep(30_000);
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
		xp = (gg.getXp())/4*3;
		int xwym = gg.getXk()-xp;
		yp = (gg.getYp()/2)*3;
		int ywym = gg.getYk()-yp;
		System.out.println("600:"+xp +" 400:"+yp +" 600:"+xwym +" 200:"+ywym);
		
		return new Rectangle(xp, yp, xwym, ywym);
	}
	
//	void ustawObszar(int xpt, int xkt, int ypt)
//	{
//		xPowroc = xpt + ((xkt - xpt) / 2);
//		yPowroc = ypt + 10;
//		System.out.println(xPowroc + " " + yPowroc);
//	}

}
