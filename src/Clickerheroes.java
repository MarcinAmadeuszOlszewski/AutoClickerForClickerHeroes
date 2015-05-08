import java.awt.EventQueue;

public class Clickerheroes
{

	/**
	 * @param args
	 */
	public static void main(final String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{

			@Override
			public void run()
			{
				new Grafika(args);

			}
		});
	}

}
