import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private double[] data;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        data=new double[trials];
        for(int i=0;i<trials;i++){
            Percolation p=new Percolation(n);
            while(!p.percolates()){
                p.open((int)(Math.random()*n)+1,(int)(Math.random()*n)+1);
            }
        //    System.out.println(p.numberOfOpenSites());
        //    System.out.println(((double)p.numberOfOpenSites())/(n*n));
            data[i]=((double)p.numberOfOpenSites())/(n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        double sum=0;
        for(int i=0;i<data.length;i++){
            sum+=data[i];
        }
        return sum/data.length;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        double sum=0;
        for(int i=0;i<data.length;i++){
            sum+=Math.pow(data[i]-mean(),2);
        }
        return Math.sqrt(sum/(data.length-1));
    }


    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean()-1.96*stddev()/Math.sqrt(data.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean()+1.96*stddev()/Math.sqrt(data.length);
    }

    // test client (see below)
    public static void main(String[] args){
        PercolationStats wzc=new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        System.out.println("mean="+wzc.mean());
        System.out.println("stddev="+wzc.stddev());
        System.out.println("95% confidence interval="+"["+wzc.confidenceHi()+","+wzc.confidenceHi()+"]");
    }


}
