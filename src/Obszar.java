
public class Obszar
{
	private int xp, xk, yp, yk;
	private int xPowroc, yPowroc;
	
	Obszar(int xpt, int xkt, int ypt, int ykt)
	{
		xp = xpt;
		xk = xkt;
		yp = ypt;
		yk = ykt;
		xPowroc = xp + ((xk - xp) / 2);
		yPowroc =  yp + 10;
	}
}
