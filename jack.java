import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;
class jack1 extends JPanel implements Runnable
 {
    static cardList_mod gur=new cardList_mod();
	public boolean st=true,comp_card=false,player1=false,game_start=false,logic=false,db=false,split=false;;//st is for deal,comp_card for computer chance,player1 for hit
	//game_start from where deal starts
	Thread t;
	Font ff;
	public int cash_value=0,width,height,bank=500;
	public int temp=0,temp1=0,cnt=0,dis=0,ht=1,sum1=0,sumc=0;
	public int[][] xy=new int[22][2];
	public int[] card_value_1=new int[11];
	public int[] card_value_c=new int[11];
	BufferedImage img;
    public static JButton[] Deals=new JButton[6];
	public String name1;
	class action implements ActionListener
	{
	public void actionPerformed(ActionEvent e)
	{
	//Deal button action
	if(((((JButton)e.getSource()).getText()).compareTo("DEAL"))==0)
	{
		if(st)
		{
		while((cash_value<10)||(cash_value>1000)||(cash_value==0))
		{
		try{
		cash_value=Integer.parseInt(JOptionPane.showInputDialog("Please input a the bid value b/w $10 to $1000"));
		}
		catch(NullPointerException e2){cash_value=10;}
		catch(NumberFormatException e1){cash_value=10;}
		}
		bank-=cash_value;
		st=false;
		game_start=true;
		sum1=0;
		sumc=0;
		calc();
		repaint();
		}
			else if(!st)
			{
			ht=1;
			logic=true;
			comp_card=true;
			sumc=0;
			sumc=gur.card_no(2);card_value_c[0]=gur.card_no(2);
			if((sumc==11)||(sumc==12)||(sumc==13))
			{sumc=10;card_value_c[0]=10;}
			else if(sumc==1){sumc=11;card_value_c[0]=11;}
			repaint();
			}
	}
	//Hit button action
	if((((((JButton)e.getSource()).getText()).compareTo("HIT"))==0)&&(!st))
	{
	//JOptionPane.showConfirmDialog(null,"choose one", "choose one", JOptionPane.YES_NO_OPTION);
	if(dis<=9)
	{++dis;}
	ht=1;
	player1=true;repaint();
	}
	//Double button action
	if((((((JButton)e.getSource()).getText()).compareTo("DOUBLE"))==0)&&(!st)&&(!player1))
		{
		//JOptionPane.showConfirmDialog(null,"choose one", "choose one", JOptionPane.YES_NO_OPTION);
		++dis;
		bank-=cash_value;
		cash_value*=2;
		ht=1;
		db=true;
		player1=true;
		logic=true;
			comp_card=true;
			sumc=0;
			sumc=gur.card_no(2);card_value_c[0]=gur.card_no(2);
			if((sumc==11)||(sumc==12)||(sumc==13))
			{sumc=10;card_value_c[0]=10;}
			else if(sumc==1){sumc=11;card_value_c[0]=11;}
			repaint();
		}
		//Insurance button action
	if((((((JButton)e.getSource()).getText()).compareTo("INSURANCE"))==0)&&(!st))
	{
	if(((card_value_c[0]==11)||(card_value_c[0]==10))&&(dis==0))
	{
	bank+=(((int)(cash_value/4))+1);
	cash_value*=3;cash_value=((int)(cash_value/4));
	repaint();
	//JOptionPane.showConfirmDialog(null,"choose one", "choose one", JOptionPane.YES_NO_OPTION);}
	}
	}
			//Split Button action
		if((((((JButton)e.getSource()).getText()).compareTo("SPLIT"))==0)&&(!st))
		{
		if((card_value_1[0]==card_value_1[1])&&(dis==0))
		{
	    split=true;game_start=false;repaint();
		}
		}
	}}
	protected void paintComponent(Graphics g)
{
		   super.paintComponent(g);
		   cnt=0;
		   g.setColor(new Color(17,189,21));
		   g.drawString("DEALER",((width/2)-70),((int)((height*2)/3)));
		   g.setColor(new Color(142,159,145));
		   g.drawLine(0,100,width,100);
		   g.drawLine(0,((int)((height*2)/3))-55,width,((int)((height*2)/3))-55);
		   if(split)
		   {g.drawLine((width/2),100,(width)/2,((int)((height*2)/3))-55);}
		   //g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		  //player cards
if((!game_start)&&(!split))
{
		try{
				img = ImageIO.read(new File("joker1.jpg"));
				}
				catch(IOException e){}
				for(int i=0;i<=15;i++)
				{
				g.drawImage(img, xy[i][0],xy[i][1],(xy[i][0]+115),(xy[i][1]+176),0,0,232,352,null);    
				g.drawRect(xy[i][0],xy[i][1],115,176); 	
				}			
}				
if(game_start)
{
		   while(cnt<=2)
		    {
				   temp=gur.card_suit(cnt+1);
				   temp1=gur.card_no(cnt+1);
				   string_names ob=new string_names((((temp-1)*13)+temp1));
				   StringBuffer q=ob.ret();
				   String a=q.toString();
				   try{
				   img = ImageIO.read(new File(a));
				   }
				   catch(IOException e){}
				   g.drawImage(img, xy[cnt][0],xy[cnt][1],(xy[cnt][0]+115),(xy[cnt][1]+176),0,0,232,355,null);    
				   g.drawRect(xy[cnt][0],xy[cnt][1],115,176);
				   ++cnt;
			}
				if(!comp_card)
				{
				try{
				img = ImageIO.read(new File("joker.jpg"));
				}
				catch(IOException e){}
				g.drawImage(img, xy[cnt][0],xy[5][1],(xy[cnt][0]+115),(xy[cnt][1]+176),0,0,232,355,null);    
				g.drawRect(xy[cnt][0],xy[cnt][1],115,176); 
				}++cnt;
				//for hit new card
					while((ht<=dis)){
						if(player1)
						{
						temp=gur.card_suit(ht+3);
						temp1=gur.card_no(ht+3);
						string_names ob=new string_names((((temp-1)*13)+temp1));
						StringBuffer q=ob.ret();
						String a=q.toString();
							try{
							img = ImageIO.read(new File(a));
							}
							catch(IOException e){}
							if(((ht==dis)&&(!logic))||(db))
								{
								check1(ht+2);
								}
							g.drawImage(img, xy[cnt][0],xy[cnt][1],(xy[cnt][0]+115),(xy[cnt][1]+176),0,0,232,355,null);    
							g.drawRect(xy[cnt][0],xy[cnt][1],115,176); 
						cnt+=2;++ht;
						}
					}
					g.setColor(new Color(17,189,21));
					g.drawString("COUNT:"+sum1,50,150);
					g.setColor(new Color(142,159,145));
					cnt-=(dis*2);cnt+=dis;
					if(logic)
					{int x_comp=5;
					while(sumc<17)
					{
							temp=gur.card_suit(cnt);
							temp1=gur.card_no(cnt);
							string_names ob=new string_names((((temp-1)*13)+temp1));
							StringBuffer q=ob.ret();
							String a=q.toString();
								if(temp1==1){sumc+=11;card_value_c[x_comp-4]=11;}
								else if((temp1==11)||(temp1==12)||(temp1==13)){sumc+=10;card_value_c[x_comp-4]=10;}
								else {sumc+=temp1;card_value_c[x_comp-4]=temp1;}
								try{
								img = ImageIO.read(new File(a));
								}
								catch(IOException e){}
								g.drawImage(img, xy[(2*x_comp)-7][0],xy[(2*x_comp)-7][1],(xy[(2*x_comp)-7][0]+115),(xy[(2*x_comp)-7][1]+176),0,0,232,355,null);    
								g.drawRect(xy[(2*x_comp)-7][0],xy[(2*x_comp)-7][1],115,176); 
							if(sumc>=22){counter_checkc(x_comp-4);}
					cnt++;x_comp++;
					}g.drawString(""+sumc+","+x_comp,900,700);
					g.setFont(new Font("Jokerman", Font.BOLD, 36));
					g.setColor(new Color(252,29,63));
					g.drawString(check_comp(),(width/2)-((check_comp().length())*13),(92));
}			
}
if(split)
{
}
g.setFont(ff);
g.setColor(new Color(17,189,21));
g.drawString(name1+":$"+bank,((int)(width/2))-(((name1+":$"+bank).length())*10),145);
}
public String check_comp()
{
String jojo;
if(sum1==21)
{jojo="BLACKJACK, Great Move!!!";bank+=(int)(2.5*cash_value);}
else if(sum1>=22)
{jojo="LOST You Have, Try Again!";}
else if(sumc>=22)
{jojo="WON You Have,Got Lucky This Time!";bank+=2*cash_value;}
else if(sum1>sumc)
{jojo="WON You Have, Nice Move!";bank+=2*cash_value;}
else if(sum1<sumc)
{jojo="LOST You Have, Bad Luck!";}
else 
{jojo="DRAW, This Was Tight ";bank+=cash_value;}
//JOptionPane.showConfirmDialog(null,jojo+" you haved $500", "StartBale", JOptionPane.YES_NO_OPTION);
game_start=false;sum1=0;dis=0;ht=1;sumc=0;comp_card=false;player1=false;logic=false;st=true;cash_value=0;db=false;split=false;
for(int i=0;i<11;++i)
{
card_value_1[i]=0;card_value_c[i]=0;
}
gur.card_gen();
return jojo;
}
public void check1(int gt)
{
			if((gur.card_no(gt+1))==1)
			{sum1+=11;card_value_1[gt]=11;}
			else if((gur.card_no(gt+1)==11)||(gur.card_no(gt+1)==12)||(gur.card_no(gt+1)==13))
			{sum1+=10;card_value_1[gt]=10;}
			else
			{sum1+=gur.card_no(gt+1);card_value_1[gt]=gur.card_no(gt+1);}
if(sum1>=22)
{
counter_check1(gt);
}
if(sum1>=22)
{
logic=true;comp_card=true;
}
}
public void counter_check1(int gt)
	{
	for(int i=0;i<=gt;++i)
	{
	if(card_value_1[i]==11)
	{
	sum1-=10;card_value_1[i]=0;
	}
	if(sum1<0){sum1+=10;card_value_1[i]=11;}  
	if(sum1<=21){break;}
	}
	}
		public void counter_checkc(int yo)
		{
		for(int i=0;i<=yo;++i)
		{
		if(card_value_c[i]==11)
		{
		sumc-=10;card_value_c[i]=0;
		}
		if(sumc<0){sumc+=10;card_value_c[i]=11;} 
		if(sumc<21){break;}
		}
		}
		
	public void calc()
	{
			for(int i=0;i<=2;i+=2)
			{
			if((gur.card_no(i+1))==1)
			{sum1+=11;card_value_1[i/2]=11;}
			else if((gur.card_no(i+1)==11)||(gur.card_no(i+1)==12)||(gur.card_no(i+1)==13))
			{sum1+=10;card_value_1[i/2]=10;}
			else
			{sum1+=gur.card_no(i+1);card_value_1[i/2]=gur.card_no(i+1);}
			}
			sumc=gur.card_no(2);card_value_c[0]=gur.card_no(2);card_value_1[2]=0;
			if((sumc==11)||(sumc==12)||(sumc==13))
			{sumc=10;card_value_c[0]=10;}
			else if(sumc==1){sumc=11;card_value_c[0]=11;}
	}
	public void run()
	{
	add(Deals[0]);
	add(Deals[1]);
	add(Deals[2]);
	add(Deals[3]);
	add(Deals[4]);
	add(Deals[5]);
	action al=new action();
	Deals[0].addActionListener(al);
	Deals[1].addActionListener(al);
	Deals[2].addActionListener(al);
	Deals[3].addActionListener(al);
	Deals[4].addActionListener(al);
	Deals[5].addActionListener(al);
	SwingUtilities.invokeLater(new Runnable()//setting event handler used when a event occurs bringing something new on frame
		{
		public void run()
		{
		int qaz=((int)((width-850)/7));
		Deals[1].setBounds(qaz,8,145,52);
		Deals[0].setBounds(((qaz*2)+145),8,140,30);
		Deals[2].setBounds(((qaz*3)+140+145),8,140,30);
		Deals[3].setBounds(((qaz*4)+140+140+145),8,140,30);
		Deals[4].setBounds(((qaz*5)+140+140+140+145),8,140,30);
		Deals[5].setBounds(((qaz*6)+140+280+285),8,145,50);
		}});
	}
	public jack1() 
	{
		   ff=new Font("ALGERIAN", Font.BOLD, 36);
		   setFont(ff);
		   gur.card_gen();
		   setBackground(new Color(185,238,238));
		   //JButton display panel
		   ImageIcon hit=new ImageIcon("ico/hit.ico");
		   ImageIcon deal=new ImageIcon("ico/deal.ico");
		   ImageIcon fold=new ImageIcon("ico/fold.ico");
		   ImageIcon insurance=new ImageIcon("ico/insurance.ico");
		   ImageIcon split=new ImageIcon("ico/split.ico");
		   ImageIcon double1=new ImageIcon("ico/double1.ico");
		   Deals[0]=new JButton("DEAL",deal);
		   Deals[1]=new JButton("HIT",hit);
		   Deals[2]=new JButton("DOUBLE",double1);
		   Deals[3]=new JButton("SPLIT",split);
		   Deals[4]=new JButton("INSURANCE",insurance);
		   Deals[5]=new JButton("HIGHSCORE",fold);
		   //screen resolution
		   try{
		   Object[] possibleValues2 = { "1600*860", "1366*728(Laptop)","1024*728"};
		   Object selectedValue2 = JOptionPane.showInputDialog(null,"Select screen resolution", "BlackJack v1.0",JOptionPane.INFORMATION_MESSAGE, null,possibleValues2, possibleValues2[0]);
		   width=Integer.parseInt((selectedValue2.toString()).substring(0,4));
		   height=Integer.parseInt((selectedValue2.toString()).substring(5,8));
		   }
		   catch(NullPointerException e)
		   {
		   width=1024;height=728;}
		   //cards_index display width and height adjustment
		   xy[0][0]=((int)((width-920)/9));//player1
		   xy[1][0]=((int)((width-920)/9));;//comp
		   for(int i=2;i<21;i+=2)
		   {
		   xy[i][0]=xy[i-2][0]+(xy[0][0])+115;
		   xy[i+1][0]=xy[i-2][0]+xy[0][0]+115;
		   }
		   int tt=((((height*2)/3)-276)/2)+100;
		   for(int i=0;i<21;i+=2)
		   {
		   xy[i][1]=tt;
		   }
		   tt=((height*2)/3)+(((height/3)-176)/2);
		   for(int i=1;i<=21;i+=2)
		   {
		   xy[i][1]=tt;
		   }
		   //player name and money assigned
		   try{
		   name1=(JOptionPane.showInputDialog("ENTER PLAYER_1 NAME")).toString();
		   }
		   catch(NullPointerException e) {name1="PLAYER";}
		   if(name1.compareTo("")==0)
		   {name1="PLAYER";}
		   //int x=JOptionPane.showConfirmDialog(null,name1+", you have been assigned $500", "Starting Bank Balance", JOptionPane.CANCEL_OPTION);
		   //name1=name1+x;  choosing options
		   //JOptionPane.showConfirmDialog(null,name1+", you have been assigned $500", "Starting Bank Balance", JOptionPane.CANCEL_OPTION);
		   //adding buttons to frame
		   t=new Thread(this);
		   t.setPriority(5);
		   t.start();
	}
	public int hgt(){return height;}public int wdt(){return width;}
}
   public class jack
	{
	jack()
	{
	JFrame f = new JFrame("Blackjack Testing v1.0");
    f.setResizable(false);
        f.addWindowListener(new WindowAdapter()
		{
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
        });
jack1 as =new jack1();   
f.add(as);
f.setSize(as.wdt(),as.hgt());
f.setVisible(true);
}
	public static void main(String[] args) 
	{   
SwingUtilities.invokeLater(new Runnable()
{
public void run()
{
new jack();
}});	 
    }
}

//-----------

class rnd
{
public int[][] deck=new int[2][53];
public rnd()
{
for(int i=0;i<=51;++i)
{
deck[0][i+1]=(i+1)%13;
deck[1][i+1]=((int)((i+1)/13))+1;
if(((i+1)%13)==0)
{
deck[0][i+1]=13;
deck[1][i+1]-=1;
}
}
deck[0][0]=0;deck[1][0]=0;
}
public void rnd_card_gen()
{
Random r1=new Random();
Random r2=new Random();
int chng1=(r1.nextInt(52))+1,chng2=(r2.nextInt(52))+1,temp1=0,temp2=0;
for(int i=1;i<=208;++i)
{
temp1=deck[0][chng1];temp2=deck[1][chng1];
deck[0][chng1]=deck[0][chng2];deck[1][chng1]=deck[1][chng2];
deck[0][chng2]=temp1;deck[1][chng2]=temp2;
chng1=(r1.nextInt(52))+1;chng2=(r2.nextInt(52))+1;
}
Random r3=new Random();
for(int i=1;i<=52;++i)
{
chng1=(r3.nextInt(52))+1;
temp1=deck[0][i];temp2=deck[1][i];
deck[0][i]=deck[0][chng1];deck[1][i]=deck[1][chng1];
deck[0][chng1]=temp1;deck[1][chng1]=temp2;
}
int[][] deck1=new int[2][53];
for(int i=1;i<=26;++i)
{
deck1[0][((i*2)-1)]=deck[0][i];deck1[1][((i*2)-1)]=deck[1][i];
deck1[0][(i*2)]=deck[0][(26+i)];deck1[1][(i*2)]=deck[1][(i+26)];
}
for(int i=1;i<=52;++i)
{
deck[0][i]=deck1[0][i];deck[1][i]=deck1[1][i];
}
}
}
class cardList_mod extends rnd
{
public cardList_mod()
{
super();
}
public void card_gen()
{
this.rnd_card_gen();
}
public int card_suit(int i)
{
return deck[1][i];
}
public int card_no(int i)
{
return deck[0][i];
}
}


//--------


class string_names
{
public StringBuffer b=new StringBuffer(20);
int count=1;
int ch;
boolean state=true;

public string_names()
{}
public string_names(int search)
{
try
{
File a=new File(System.getProperty("user.dir")+"/card_names.txt");
if(a.exists())
{
FileReader fw=new FileReader(a);
b.delete(0,20);
while(state)
{
ch=fw.read();
while((ch!=32)&&(count==search))
{
b.append(((char)ch));
ch=fw.read();
}
if(ch==32)
{++count;}
if(b.length()!=0)
{
break;
}
if(ch=='*')
{state=false;}
}
fw.close();
}}
catch(Exception e)
{
System.out.println(e);
}
}
public StringBuffer ret()
{
return(b);
}
}