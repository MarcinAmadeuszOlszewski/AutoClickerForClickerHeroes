import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Grafika extends JFrame implements ChangeListener
{
	class RobotLisener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			start.setEnabled(false);
			ustawianie1.setEnabled(false);
			ustawianie2.setEnabled(false);
			koniec = false;
			int tmp;

			xp = Integer.parseInt(czysc(xPole1.getText()));
			yp = Integer.parseInt(czysc(yPole1.getText()));
			xk = Integer.parseInt(czysc(xPole2.getText()));
			yk = Integer.parseInt(czysc(yPole2.getText()));
//			jajo.setEnabled(true);

			if (xp > xk)
			{
				tmp = xk;
				xk = xp;
				xp = tmp;
			}
			if (yp > yk)
			{
				tmp = yk;
				yk = yp;
				yp = tmp;
			}

			powroc();

			new Thread()
			{

				@Override
				public void run()
				{
					int i = 0;

					while (true)
					{
						int tmpx = lokalizacjaKursora()[0];
						int tmpy = lokalizacjaKursora()[1];

						if (tmpx > xp && tmpx < xk && tmpy > yp && tmpy < yk)
						{
							robot.mousePress(InputEvent.BUTTON1_MASK);
							robot.mouseRelease(InputEvent.BUTTON1_MASK);
							robot.delay(OP);
							start.setText("" + i++);
						}

						if (koniec)
						{
							auto.setSelected(false);
							break;
						}
					}

					ustawianie1.setEnabled(true);
					ustawianie2.setEnabled(true);
					start.setText("start");
					start.setEnabled(true);
				}
			}.start();

		}

	}

	class UstawienieKursoraListener implements AWTEventListener
	{

		@Override
		public void eventDispatched(AWTEvent event)
		{
			if (ustawianie1.isSelected())
			{
				xPole1.setText(lokalizacjaKursora()[0] + "");
				yPole1.setText(lokalizacjaKursora()[1] + "");
				ustawianie1.setSelected(false);
			}

			if (ustawianie2.isSelected())
			{
				xPole2.setText(lokalizacjaKursora()[0] + "");
				yPole2.setText(lokalizacjaKursora()[1] + "");
				ustawianie2.setSelected(false);
			}
			
			if (farm.isSelected())
			{
				farma.setX(lokalizacjaKursora()[0]);
				farma.setY(lokalizacjaKursora()[1]);
				farm.setSelected(false);
			}

			if (hero1.isSelected())
			{
				tworzHero(0, hero1);
			}

			if (hero2.isSelected())
			{
				tworzHero(1, hero2);
			}

			if (hero3.isSelected())
			{
				tworzHero(2, hero3);
			}

			if (hero4.isSelected())
			{
				tworzHero(3, hero4);
			}

		}

		void tworzHero(int i, JCheckBox jBox)
		{
			heroLista[i] = new Heroes(i);
			heroLista[i].setX(lokalizacjaKursora()[0]);
			heroLista[i].setY(lokalizacjaKursora()[1]);
			jBox.setText("H" + (++i));
			jBox.setSelected(false);
		}
	}

	TimerTask timerTask = new TimerTask()
	{
		int licznikHero = 0;

		@Override
		public void run()
		{
			
			if (auto.isSelected())
			{

				if (heroLista[licznikHero] != null)
				{
					heroLista[licznikHero].rekrutuj();
					powroc();
				}
				licznikHero++;
				if (licznikHero >= 4)
				{
					licznikHero = 0;
					skille();
					farma.sprawdz();
//					System.out.println("TICK");
				}

			}

		}
	};

	private Robot robot;
	private Grafika gg;
	private Farma farma = new Farma();
	private Heroes[] heroLista = new Heroes[4];


	private int xp = 800, yp = 200, xk = 1200, yk = 750;
	public static boolean koniec = false;
	private JButton start, stop;

	private JCheckBox auto, ustawianie1, ustawianie2, hero1, hero2, hero3, hero4, farm, jajo;

	private JSlider suwakOP;
	private JLabel etykietaOP;

	private JTextField xPole1, yPole1, xPole2, yPole2;

	private int OP = 25;
	private Timer timer;

	public Grafika(String... arg)
	{
		gg =this;
		setLocation(1100, 10);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setAlwaysOnTop(true);
		setLayout(null);
		setResizable(false);
		Toolkit.getDefaultToolkit().addAWTEventListener(new UstawienieKursoraListener(), AWTEvent.FOCUS_EVENT_MASK);
		// Toolkit.getDefaultToolkit().addAWTEventListener(new
		// UstawienieKursoraListener(), AWTEvent.MOUSE_EVENT_MASK |
		// AWTEvent.FOCUS_EVENT_MASK);

		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}

		start = new JButton("start");
		start.setMnemonic('j');
		start.addActionListener(new RobotLisener());
		start.setBounds(0, 0, 160, 20);

		ustawianie1 = new JCheckBox("poczatek");
		ustawianie1.setBounds(0, 20, 80, 20);
		ustawianie1.setToolTipText("poczatek obszaru klikania");
		xPole1 = new JTextField("800", 4);
		xPole1.setHorizontalAlignment(SwingConstants.RIGHT);
		xPole1.setBounds(80, 20, 40, 20);
		xPole1.setToolTipText("wspolrzedna x");
		yPole1 = new JTextField("200", 4);
		yPole1.setHorizontalAlignment(SwingConstants.RIGHT);
		yPole1.setBounds(120, 20, 40, 20);
		yPole1.setToolTipText("wspolrzedna y");

		ustawianie2 = new JCheckBox("koniec");
		ustawianie2.setBounds(0, 40, 80, 20);
		ustawianie2.setToolTipText("koniec obszaru klikania");
		xPole2 = new JTextField("1200", 4);
		xPole2.setHorizontalAlignment(SwingConstants.RIGHT);
		xPole2.setBounds(80, 40, 40, 20);
		xPole2.setToolTipText("wspolrzedna x");
		yPole2 = new JTextField("750", 4);
		yPole2.setHorizontalAlignment(SwingConstants.RIGHT);
		yPole2.setBounds(120, 40, 40, 20);
		yPole2.setToolTipText("wspolrzedna y");

		stop = new JButton("stop");
		stop.setMnemonic('m');
		stop.setBounds(0, 120, 160, 20);
		stop.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				koniec = true;
			}
		});
		
		jajo = new JCheckBox("J");
//		jajo.setEnabled(false);
		jajo.setBounds(40, 100, 40, 20);
		jajo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new Jaja(jajo, gg);
			}
		});

		auto = new JCheckBox("Auto");
		auto.setToolTipText("Aktywuje automatyczne zakupy i PowerSurge");
		auto.setMnemonic('i');
		auto.setBounds(80, 100, 80, 20);

		farm = new JCheckBox("F");
		farm.setBounds(00, 100, 40, 20);
		
		etykietaOP = new JLabel();
		etykietaOP.setToolTipText("Klik na sekunde");
		etykietaOP.setBounds(140, 60, 20, 20);

		add(start);
		add(ustawianie1);
		add(xPole1);
		add(yPole1);
		add(ustawianie2);
		add(xPole2);
		add(yPole2);
		add(tworzSuwak());
		add(etykietaOP);
		add(hero1 = tworzHero(hero1, 0));
		add(hero2 = tworzHero(hero2, 1));
		add(hero3 = tworzHero(hero3, 2));
		add(hero4 = tworzHero(hero4, 3));
		add(farm);
		add(auto);
		add(stop);
		add(jajo);

		timer = new Timer();
//		timer.schedule(timerTask, 20_000, 3_000);
		timer.schedule(timerTask, 15_000, 30_000);
		setMinimumSize(new Dimension(167, 170));
		pack();
		setVisible(true);
	}

	public String czysc(String ss)
	{
		String tmp = "";
		for (int i = 0, e = ss.length(); i < e; i++)
		{
			char xx = ss.charAt(i);
			if (xx == '1' || xx == '2' || xx == '3' || xx == '4' || xx == '5' || xx == '6' || xx == '7' || xx == '8' || xx == '9' || xx == '0')
			{
				tmp += xx;
			}
		}

		if (tmp.length() == 0)
			return "0";
		else
			return tmp;
	}

	int[] lokalizacjaKursora()
	{
		int[] wsp = new int[2];
		wsp[0] = (int) MouseInfo.getPointerInfo().getLocation().getX();
		wsp[1] = (int) MouseInfo.getPointerInfo().getLocation().getY();
		return wsp;
	}

	void powroc()
	{
		robot.mouseMove(xp + ((xk - xp) / 2), yp + 10);
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		JSlider zr = (JSlider) e.getSource();
		suwakOP.setToolTipText("Opuznienie " + zr.getValue());
		etykietaOP.setText("" + Math.round(1000 / zr.getValue()));
		OP = zr.getValue();

	}

	JCheckBox tworzHero(JCheckBox hh, int nr)
	{
		int wsp = nr * 40;
		hh = new JCheckBox("h" + (++nr));
		hh.setToolTipText(nr + " bohater do automatycznego zakupu");
		hh.setBounds(wsp, 80, 40, 20);

		return hh;
	}

	private JSlider tworzSuwak()
	{
		suwakOP = new JSlider(SwingConstants.HORIZONTAL, 10, 50, 25);
		suwakOP.addChangeListener(this);
		suwakOP.setBounds(0, 60, 140, 20);
		suwakOP.setMajorTickSpacing(2);
		return suwakOP;
	}
	
	private void skille(){
		char[] klawisze = {'2', '8', '3', '7', '4', '5', '9'};
		for(char xx : klawisze)
		{
			robot.keyPress(xx);
			robot.keyRelease(xx);
		}
					
					// max DMG 1-2-3-4-5-7-8-6-9(wait 15)8-9-1-2-3-4-5-7(wait
					// 15)(repeat)
	}
	
	
	int getXp()
	{
		return xp;
	}

	int getYp()
	{
		return yp;
	}

	int getXk()
	{
		return xk;
	}

	int getYk()
	{
		return yk;
	}
	
	
	
	
}
