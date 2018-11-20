import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.event.ActionEvent;
import javax.swing.*;



public class SemesterConversionQ extends JFrame
{
	private JLabel title,quarterLabel,semesterLabel;
	private JTextField qElective, sElective;
	private JPanel panel;
	private String[] course= {"109/L","114/L","130","204/L","205/L","207/L","209/L","220/L","256","302","304","306","306L","307","309/L","310/L","315","320/L","325/L","330","341/L",
							  "405/L","425/L", "426/L","431/L or 433/L","464","480"}; //list of quarter courses
	private String[] sCourse = {"1101","1310","2101/L","2200/L","2300/L","2310","3101","3101L","3200/L","3250","3300/L","3301/L","3310",
								"3709/L","3715","3810/L","4064","4300","4303/L","4310","4318","4705/L"};//list of semester courses
	private String[] ceCourses = {"1101","1310","2101/L","2200/L","2300/L","2310","3101","3300/L","3301/L","3310","3715","4064","4300","4310"};//required courses for CE
	private String[] eeCourses = {"1101","1310","2101/L","2200/L","2300/L","3101","3101L","3200/L","3250","3301/L","3709/L","3715","3810/L","4064","4705/L"};//required courses for EE
	public boolean[] eC = {true,true,true,true,true,true, true, true, true, true, true, true, true, true, true};//EE boolean case
	public boolean[] cC = {true,true,true,true,true,true,true, true, true, true, true, true,true, true};//CE boolean case
	
	//add quarter system requirements
	public boolean[] cQC= {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true};//21
    public boolean[] eQC= {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true};//20
    private String[] ceqCourses= {"109/L","114/L","130","204/L","205/L","207/L","209/L","220/L","256","302","304","306","306L","309/L","315","325/L","341/L","425/L","426/L","431/L or 433/L","464","480"};
    private String[] eeqCourses= {"109/L","114/L","204/L","205/L","207/L","209/L","220/L","256","302","306","306L","307","309/L","310/L","315","320/L","330","341/L","405/L","464"};
	private double[] qC = {0,0,0,0,0,0,0,0,0,4,4,4,1,3,5,5,4,4,4,3,4,5,4,4,4,1,4};//quarter unit value of classes
	private double[] sC = {0,0,0,0,0,0,3,1,4,3,4,4,3,4,3,4,1,3,4,3,3,4};//semester unit value of classes
	
	public boolean run = false;
	private JCheckBox[] boxes, boxes2;//array of checkboxes to handle Quarter and Semester separately
	private JButton eeButton, ceButton;//buttons to run 2 cases
	private Color myBlue = new Color(29,57,125);//BroncoDirect Color
	private Color myGreen = new Color(222,235,181);//BroncoDirect Color
	public boolean major = true;// CE true, EE false
	public double units, units2; //default based on units we are not counting
	
	public SemesterConversionQ()
	{
		//define panel
		super("Semester Conversion");
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.CENTER);
		//set title
		title =new JLabel("ECE Semester Conversion Progress Report");
		title.setForeground(myBlue);
		title.setLocation(10,0);
		title.setSize(500,25);
		panel.add(title);
		//set checkboxes
		int h1 = 0,h2 = 0;
		boxes = new JCheckBox[course.length];//set length same as # of courses
		for(int i = 0;i< boxes.length;i++)//define each checkbox based on quarter courses
		{
			boxes[i] = new JCheckBox(course[i]);
			boxes[i].setForeground(myBlue);
			boxes[i].setBackground(Color.WHITE);
			boxes[i].setLocation(10,h1+20);
			boxes[i].setSize(125,25);
			panel.add(boxes[i]);
			h1 = h1+20;	
		}
		boxes2 = new JCheckBox[sCourse.length];
		for(int i = 0;i< boxes2.length;i++)//repeat for semester courses
		{
			boxes2[i] = new JCheckBox(sCourse[i]);
			boxes2[i].setForeground(myBlue);
			boxes2[i].setBackground(Color.WHITE);
			boxes2[i].setLocation(150,h2+20);
			boxes2[i].setSize(100,25);
			panel.add(boxes2[i]);
			h2 = h2	+20;	
		}
		//set text fields
		h2 = h2+40;
		quarterLabel =new JLabel("Quarter Electives: ");
		quarterLabel.setForeground(myBlue);
		quarterLabel.setLocation(150,h2);
		quarterLabel.setSize(500,25);
		panel.add(quarterLabel);
		
		qElective = new JTextField();
		qElective.setLocation(280,h2);
		qElective.setSize(50,25);
		panel.add(qElective);
		
		h2 = h2+40;
		semesterLabel =new JLabel("Semester Electives: ");
		semesterLabel.setForeground(myBlue);
		semesterLabel.setLocation(150,h2);
		semesterLabel.setSize(500,25);
		panel.add(semesterLabel);
		
		sElective = new JTextField();
		sElective.setLocation(280,h2);
		sElective.setSize(50,25);
		panel.add(sElective);
		//set buttons
		ceButton = new JButton("Computer Engineering");
		ceButton.setSize (200,30);
		ceButton.setLocation(10,600);
		ceButton.setBackground(myGreen);
		panel.add(ceButton);
		eeButton = new JButton("Electrical Engineering");
		eeButton.setSize (200,30);
		eeButton.setLocation(320,600);
		eeButton.setBackground(myGreen);
		panel.add(eeButton);	
		//set a button handler for when the buttons are pressed
		ButtonHandler bhandler = new ButtonHandler();
		ceButton.addActionListener(bhandler);
		eeButton.addActionListener(bhandler);
	}
	public void paint (Graphics g)//design the output section of the panel
	{
		super.paint(g);
		Font titleFont = new Font ("Arial", Font.BOLD, 15);
		Font textFont = new Font ("Arial", Font.PLAIN, 15);
		Color darkBlue = new Color(115,130,181);//BroncoDirect Color
		Color lightBlue = new Color(218,226,238);//BroncoDirect Color
		//draw output rectangle
		g.setColor(lightBlue);
		g.fillRect(350, 55, 360, 560);
		g.setColor(darkBlue);
		g.fillRect(350, 30, 360, 25);
		g.setColor(Color.WHITE);
		g.setFont(titleFont);
		g.drawString("Results", 355, 50);
		int h1 = 50;
		int h2=70;//default height
		g.setFont(textFont);
		g.setColor(Color.BLACK);
		//display output text
		if(run) 
		{//does not display until the program has been ran at least once
			if(major)//computer engineering
			{	
				g.drawString("Remaining CE Core Courses Needed To Take: ",355,h1+20);
				h1=h1+20;
				g.drawString("Semeter:", 355, h1+20);
				g.drawString("Quarter:", 550, h2+20);
				h2=h2+20;
				h1=h1+20;//makes space between remaining core courses and course list
				for(int j=0;j<ceCourses.length; j++)
				{
					if(cC[j]) {
						g.drawString(ceCourses[j], 355, h1+20);
						h1 = h1+20;
					}
				}
				for(int i=0; i<ceqCourses.length; i++)
				{
					if(cQC[i])
					{
						g.drawString(ceqCourses[i], 550, h2+20);
						h2=h2+20;
					}
				}

				g.setColor(Color.RED);
				if(h1<h2)
					h1=h2;
				g.drawString("Elective Units Required: 13/11", 355, h1+20);
				h1=h1+20;
			}
			else//electrical engineering
			{
				g.drawString("Remaining EE Core Courses Needed to Take: ", 355, h1+20);
				h1 = h1+20;
				g.drawString("Semeter:", 355, h1+20);
				g.drawString("Quarter:", 550, h2+20);
				h2=h2+20;
				h1=h1+20;
				for(int j=0;j<eeCourses.length; j++)
				{
					if(eC[j]) {
						g.drawString(eeCourses[j], 355, h1+20);
						h1 = h1+20;
					}
				}
				for(int i=0; i<eeqCourses.length; i++)
				{
					if(eQC[i])
					{
						g.drawString(eeqCourses[i], 550, h2+20);
						h2=h2+20;
					}
				}
				g.setColor(Color.RED);
				if(h1<h2)
					h1=h2;
				g.drawString("Elective Units Required: 10/19", 355, h1+20);
				h1=h1+20;
			}

			g.setColor(Color.BLACK);
			g.drawString("Elective Units Taken: "+units+"/"+units2, 355, h1+20);
			h1=h1+20;
			g.drawString("*NOTE: need minimum # of 3000/4000 electives each", 355, h1+20);
			g.drawString("talk to your advisor about these", 355, h1+40);
		}
		run = true;//always run after the first time
	}
	private class ButtonHandler implements ActionListener{//handle if button is pressed
		public void actionPerformed (ActionEvent e)
		{
			units=0;
			units2=0;
			major=true;//set major to CE
			if(e.getActionCommand()=="Computer Engineering")//if CE button is pressed
			{
				for(int i =0;i<cC.length;i++)
				{
					cC[i] = true;//reset boolean values
				}
				
				
				for(int j=0;j<cQC.length; j++)
				{
					cQC[j]=true;
				}
				
				readText();//look at text file
				
				String qe = qElective.getText();
				if(qe.matches("[0-9]+"))//verify input is a number
				{
					double temp1 = Double.parseDouble(qe);
					units = units+ temp1*2/3;
					units2=units2+temp1;
				}
				String se = sElective.getText();
				if(se.matches("[0-9]+"))
				{
					double temp2 = Double.parseDouble(se);
					units = temp2+units;
					units2=units2+temp2*3/2;
				}
				
				units = Math.round(units*100.0)/100.0;
				units2 = Math.round(units2*100.0)/100.0;
				repaint();//redraw the output
			}
			
			else//if EE button is pressed
			{
				for(int i =0;i<eC.length;i++)
				{
					eC[i] = true;//reset boolean values
					
				}
				
				for(int j=0;j<eQC.length; j++)
				{
					eQC[j]=true;
				}
				
				major=false;//set major to EE
				readText();//look at text file
				
				String qe = qElective.getText();
				if(qe.matches("[0-9]+"))//verify input is a number
				{
					double temp1 = Double.parseDouble(qe);
					units = units+ temp1*2/3;
					units2 = units2+temp1;
				}
				String se = sElective.getText();
				if(se.matches("[0-9]+"))
				{
					double temp2 = Double.parseDouble(se);
					units = temp2+units;
					units2 = units2+temp2*3/2;
				}

				units = Math.round(units*100.0)/100.0;
				units2 = Math.round(units2*100.0)/100.0;
				repaint();//redraw the output
			}
		}
		public int locateCourse(String x, int y)
		{
			int output = 0;//default output
			switch(y) {
			case 0: // quarter class location 
			{
				for(int i=0; i<course.length;i++)
				{
					if(course[i].equals(x))//store location of semester course
					{
						output = i;
					}
				}
			}
			case 1: // semester class location 
			{
				for(int i=0; i<sCourse.length;i++)
				{
					if(sCourse[i].equals(x))//store location of semester course
					{
						output = i;
					}
				}
			}
			}
			
			
			return output;
		}
		public void compare(String[] in, Boolean b)
		{
			int d = 4318, e= 4318, e2= 4318; //return case
			//=====================================CE=================================
			if(major)//If dealing with CE
			{
				for(int i=0; i<ceCourses.length;i++)
				{
					if(ceCourses[i].equals(sCourse[locateCourse(in[0],1)]))
					{
						d = i;//look for the equivalent location of the course being looked at
					}
				}
				
				if(boxes2[locateCourse(in[0],1)].isSelected())//if dealing with semester
				{
					if(d!=4318)//if it is actually located in the list
						cC[d] = false;
					else
						units = units+ sC[locateCourse(in[0],1)];//add units anyways
				}
				if(b)//special case happens classes combined in quarter
				{
					if(boxes[locateCourse(in[1],0)].isSelected()&&boxes[locateCourse(in[2],0)].isSelected())
					cC[d] = false;	
					
					//special case for 3101 for CE since only required to take 306, not 307
					if(in[0].equals("3101"))
					{
						if(boxes[locateCourse(in[1],0)].isSelected())
							cC[d] = false;
					}
				}
				else//no special cases 
				{
					if(boxes[locateCourse(in[1],0)].isSelected())//no special case quarter class
					{
						if(d!=4318)					
							cC[d] = false;
						else
							units = units+ qC[locateCourse(in[1],0)]*2/3;
					}
				}
				
				
				for(int j=0;j<ceqCourses.length;j++)//set up array locations
				{
					if(ceqCourses[j].equals(course[locateCourse(in[1],0)]))
					{
						e=j;
					}
					if(b)
					{
						if(ceqCourses[j].equals(course[locateCourse(in[2],0)]))
							e2=j;
					}
				}
				if(boxes[locateCourse(in[1],0)].isSelected())//no special case quarter class
				{
					if(e!=4318)					
						cQC[e] = false;
					else
						units2 = units2+ qC[locateCourse(in[1],0)];//add units anyways
				}
				
				if(b)//special case happens classes combined in quarter
				{

					if(boxes[locateCourse(in[2],0)].isSelected())
					{
						if(e2!=4318)
							cQC[e2]=false;
						else
							units2 = units2+ qC[locateCourse(in[2],0)];//add units anyways
					}
					if(boxes2[locateCourse(in[0],1)].isSelected())//if semester with 2 quarter courses is selected
					{
						if(e!=4318)
						cQC[e] = false;
						if(e2!=4318)
						cQC[e2] = false;	
					}
				}
				else//no special cases  SEMESTER
				{
					if(boxes2[locateCourse(in[0],1)].isSelected())//if dealing with semester
					{
						if(e!=4318)//if it is actually located in the list
							cQC[e] = false;
						else
								units2 = units2+ sC[locateCourse(in[0],1)]*3/2;//add units anyways
					}
				}
			}
			//===============================EE============================================
			else 
			{//if dealing with EE
				for(int i=0; i<eeCourses.length;i++)//
				{
					if(eeCourses[i].equals(sCourse[locateCourse(in[0],1)]))
					{
						d = i;
					}
				}
				
				if(boxes2[locateCourse(in[0],1)].isSelected())//semester courses
				{
					if(d!=4318)
						eC[d] = false;
					else
						units = units+ sC[locateCourse(in[0],1)];
				}
				if(b)//special case happens
				{
					if(boxes[locateCourse(in[1],0)].isSelected()&&boxes[locateCourse(in[2],0)].isSelected())
					{
						eC[d] = false;	
					}
				}
				else//no special cases 
				{
					if(boxes[locateCourse(in[1],0)].isSelected())//no special case
					{
						if(d!=4318)
							eC[d] = false;
						else
							units = units+ qC[locateCourse(in[1],0)]*2/3;
					}
					
				}
				for(int j=0;j<eeqCourses.length;j++)//set up array locations
				{
					if(eeqCourses[j].equals(course[locateCourse(in[1],0)]))
					{
						e=j;
					}
					if(b)
					{
						if(eeqCourses[j].equals(course[locateCourse(in[2],0)]))
							e2=j;
					}
				}
				if(boxes[locateCourse(in[1],0)].isSelected())//no special case quarter class
				{
					if(e!=4318)					
						eQC[e] = false;
					else
						units2 = units2+ qC[locateCourse(in[1],0)];//add units anyways
				}
				
				if(b)//special case happens classes combined in quarter
				{
					if(boxes[locateCourse(in[2],0)].isSelected())
					{
						if(e2!=4318)
							eQC[e2]=false;
						else
							units2 = units2+ qC[locateCourse(in[2],0)];//add units anyways
					}
					if(boxes2[locateCourse(in[0],1)].isSelected())//if semester with 2 quarter courses is selected
					{
						if(e!=4318)
							eQC[e] = false;
						if(e2!=4318)
							eQC[e2] = false;
					}
				}
				else//no special cases  SEMESTER
				{
					if(boxes2[locateCourse(in[0],1)].isSelected())//if dealing with semester
					{
						if(e!=4318)//if it is actually located in the list
							eQC[e] = false;
						else
							units2 = units2+ sC[locateCourse(in[0],1)]*3/2;//add units anyways
					}
				}
			}
		}
		public void readText()
		{
			try(BufferedReader br = new BufferedReader(new FileReader("MASTER.txt")))
			{
					
				for(int i = 0; i<24;i++)
				{
					String reader = br.readLine();
					String[] pair = reader.split(";");
					if(pair.length>2)//special case
					{
						compare(pair, true);//there is a special case
					}
					else
					{
						compare(pair, false);//there is no special case
					}
				}
				
			}
			catch (IOException e)
			{System.out.println("Master Class Converter File is Missing!");}
			
		}
	}
}