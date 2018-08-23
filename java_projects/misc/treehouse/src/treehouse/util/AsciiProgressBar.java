package treehouse.util;

public class AsciiProgressBar 
{
    public AsciiProgressBar(String label, int total) 
    {
        this.label = label;
        this.total = total;
    }

    public void show(int step) 
    {
        double d = ((double) step) / ((double) total);
        int perc = (int) (d * 50);
        perc = Math.max(perc, 0);
        perc = Math.min(perc, 50);

        if (perc > lastPerc)
        {
            System.out.print("\r" + label + ": [");
            for (int i = 0; i < 50; i++)
                System.out.print(i <= perc ? "=" : " ");
            System.out.print("] " + (perc<<1) + "%");
            lastPerc = perc;
        }
    }

    private final String label;
    private final int total;
    int lastPerc = 0;
}
