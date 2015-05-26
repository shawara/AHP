package AHP_OR;

import javax.swing.JOptionPane;

public class Main {
public static void main(String [] args){
	String a,b,c[],d[];
	int nc,na;
	a=JOptionPane.showInputDialog(null, "number of criateria");
	b=JOptionPane.showInputDialog(null, "number of alternatives");
	nc=Integer.parseInt(a);
	na=Integer.parseInt(b);
	c=new String[nc+3];
	d=new String[na+3];
	c[0]="Criateria";

	for(int x=1; x<=nc; x++)
		c[x]=JOptionPane.showInputDialog(null, "name of criateria "+x);
	
		c[nc+1]="GEOMETRIC";
		c[nc+2]="Eigenvector";
		
	for(int x=1; x<=na; x++)
		d[x]=JOptionPane.showInputDialog(null, "name of alternatives "+x);
	d[na+1]="GEOMETRIC";
	d[na+2]="Eigenvector";
	
	AHP f=new AHP(nc, na, c, d);
	
}
}
