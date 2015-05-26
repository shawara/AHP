package AHP_OR;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class AHP implements ActionListener {

public double CR(double arr[][],int n){
	double ci=0,ymax=0,cr=0;
for(int x=0; x<n; x++){
	ymax+=arr[n][x]*arr[x][n+1];
	System.out.println(arr[n][x]+" "+arr[x][n+1]);
}

if(n>2){
ci=(ymax-n)/(double)(n-1);
cr=ci/RI[n];
}
System.out.println("ymax="+ymax);
System.out.println("CI="+ci);
System.out.println("CR="+cr);
	return cr;
	
}
	
///check if input is fraction ex 1/4
public double checkH(String h){
	String a,b;
	for(int x=0; x<h.length(); x++){
		if(h.charAt(x)=='/'){
			a=h.substring(0, x);
			b=h.substring(x+1, h.length());
			System.out.println("a="+a+" b="+b);
			return (Double.parseDouble(a)/Double.parseDouble(b));
		}
	}
	return Double.parseDouble(h);
}

///get data from table and process it
public double[][] solveit(JTable t,int n){
	//declare array to contain data and geometic , normaliztion and sum of columns
	double t1[][]=new double[n+1][n+2];
	for(int x=0; x<n; x++){
		for(int z=0; z<n; z++){
		  Object o=t.getValueAt(x, z+1);
		if(o!=null){
			String h=o.toString();
			double i=checkH(h);    ///check if input is fraction ex 1/4
			 t1[x][z]=i; }
		else t1[x][z]=.0;	
		}}
	//complate table criteria and calculate Geometric
	for(int x=0; x<n; x++){ double prod=1;
		for(int z=0; z<n; z++){
			if(x==z)t1[x][z]=1;
			else if(t1[x][z]==.0) t1[x][z]=1/t1[z][x];
			prod*=t1[x][z];
	
		}t1[x][n]=Math.pow(prod, (1/(double)n)); 
		
	}
	
	//calculate sum of column
	for(int x=0; x<=n; x++){ double sum=0;
	for(int z=0; z<n; z++){
	sum+=t1[z][x];
	}
	t1[n][x]=sum;}
	
	///calculate normalization column
	for(int x=0; x<n; x++)t1[x][n+1]=t1[x][n]/t1[n][n]; 
	
///printing table data in consol	
	for(int x=0; x<=n; x++){
       for(int z=0; z<=n+1; z++){
	     System.out.print(t1[x][z]+" ");}
System.out.println();
}
    	   
	return t1;
}

private double RI[]={0.0 , 0.0 , 0.0 , .58 , .90 , 1.12 , 1.24 , 1.32 , 1.41 , 1.45 , 1.49 , 1.51 , 1.48 , 1.56 , 1.57 , 1.59 }; 
private JTable table[];
private int nc,na;
private double t1[][],t2[][][];
private String al[],c[];
private JLabel l1;
private JButton b;
//constructor
public AHP(int nc,int na ,String c[],String al[]){
	this.nc=nc;
	this.na=na;
	this.al=al;
	this.c=c;
	JFrame frame = new JFrame();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 

	
	
	///array of gui tables with (n criateria +1) criateria table and alternative tables
  	 table= new JTable[nc+1];
    JPanel p=new JPanel();
  	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
  	
  	///creating tables
	table[0]=new JTable(nc+1,nc+3);
  	
  	for(int x=1; x<=nc; x++)
	table[x]=new JTable(na+1,na+3);


	 
	for(int x=0; x<=nc; x++)
	 table[x].setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	 
	//adding tables to panel
	for(int x=0; x<=nc; x++){
		p.add(table[x].getTableHeader(),BorderLayout.AFTER_LINE_ENDS);
		 p.add(table[x],BorderLayout.AFTER_LINE_ENDS);
	}
	
	 JScrollPane js=new JScrollPane(p);
	
//putting names on tables.	 
	 for(int x=0; x<=nc+2; x++){
		 
		 JTableHeader th = table[0].getTableHeader();
		  TableColumnModel tcm = th.getColumnModel();
			 TableColumn tc = tcm.getColumn(x);
			 
	       tc.setHeaderValue(c[x]);
	       if(x<nc)table[0].setValueAt(c[x+1] ,x , 0);   
	   }
	 table[0].setValueAt("sum",nc , 0);
	 
	 for(int z=1; z<=nc; z++){
	 al[0]=c[z];
	 for(int x=0; x<=na+2; x++){
		 JTableHeader th = table[z].getTableHeader();
		  TableColumnModel tcm = th.getColumnModel();
			 TableColumn tc = tcm.getColumn(x);
	       tc.setHeaderValue(al[x]);
	       if(x<na)table[z].setValueAt(al[x+1] ,x , 0);      
	       if(x==na) table[z].setValueAt("sum",x , 0);
	 }
	 
	 }
table[0].setBackground(Color.yellow);
	 
//////////////////////////////////
	  t1=new double[nc+1][nc+2];	
	  t2=new double[nc+1][na+1][na+2];
	
	///adding components to frame   
	 l1=new JLabel();
	 b=new JButton("goooo");
	frame.setSize(800, 400);
    frame.add(js);
    p.add(l1,BorderLayout.AFTER_LINE_ENDS);
    frame.add(b,BorderLayout.SOUTH);
	frame.setVisible(true);

	b.addActionListener(this);
}
@Override
public void actionPerformed(ActionEvent e) {

t1=solveit(table[0], nc);
     
for(int x=0; x<nc; x++){
	t2[x]=solveit(table[x+1], na); //function get data from tables and solve it
	System.out.println();	
}
	
//print solution in tables
for(int x=0; x<=nc; x++)
	for(int z=0; z<=nc+1; z++)
		//if to avoid printting sum of Eigenvector in criteria
		if(!(z==nc+1 && x==na+1))table[0].setValueAt(t1[x][z] ,x , z+1); 

for(int i=1; i<=nc; i++)
for(int x=0; x<=na; x++)
	for(int z=0; z<=na+1; z++)
		//if to avoid printting sum of Eigenvector other tables
	if(!(z==na+1 && x==na)) table[i].setValueAt(t2[i-1][x][z] ,x , z+1); 

//calculate CR value
String hhh="tables with CR >.1 ";
boolean ch=false;

if(CR(t1,nc)<=.1) {ch=true; hhh+=", "+c[0];}

for(int i=0; i<nc; i++)if(CR(t2[i],na)>.1) {ch=true; hhh+=", "+c[i+1];}


//calculate goal priorities
double pri[]=new double[na+1];
for(int z=0; z<na; z++){
double sum=0;
for(int x=0; x<nc; x++){sum+=t1[x][nc+1]*t2[x][z][na+1]; 
}
pri[z]=sum;
System.out.println("sum="+sum);
}

//find max prioroty
int max_index=0;
for(int i=1; i<na; i++)
	if(pri[i]>pri[max_index]) max_index=i;

l1.setText("Your goal is "+al[max_index+1] +" and it's priority is "+pri[max_index]);
///if(ch)JOptionPane.showMessageDialog(null, hhh);

}


}
