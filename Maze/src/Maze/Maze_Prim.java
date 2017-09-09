package Maze;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import Maze.Prim.Point;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Maze_Prim {

	private JFrame frmMaze;
	
	public static String toBoardString(char[][] board) { 
	        String boardString = "";
	         
	        for (int i = 0; i < board.length; i++) {
	            char[] line = board[i];
	 
	            for (int j = 0; j < line.length; j++) {
	                boardString += line[j];
	            }
	 
	            boardString += "\n";
	        }
	         
	        return boardString;
	    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Maze_Prim window = new Maze_Prim();
					window.frmMaze.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Maze_Prim() {
		initialize();
		createEvents();
	}

	private void createEvents() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		

		// dimensions of generated maze
    	int r=20, c=40;

    	// build maze and initialize with only walls
        StringBuilder s = new StringBuilder(c);
        for(int x=0;x<c;x++)
        	s.append('|');
        char[][] maz = new char[r][c];
        for(int x=0;x<r;x++) maz[x] = s.toString().toCharArray();

        // select random point and open as start node
        Point st = new Point((int)(Math.random()*r),(int)(Math.random()*c),null);
        maz[st.r][st.c] = 'S';

        // iterate through direct neighbors of node
        ArrayList<Point> frontier = new ArrayList<Point>();
        for(int x=-1;x<=1;x++)
        	for(int y=-1;y<=1;y++){
        		if(x==0&&y==0||x!=0&&y!=0)
        			continue;
        		try{
        			if(maz[st.r+x][st.c+y]=='*') continue;
        		}catch(Exception e){ // ignore ArrayIndexOutOfBounds
        			continue;
        		}
        		// add eligible points to frontier
        		frontier.add(new Point(st.r+x,st.c+y,st));
        	}

        Point last=null;
        while(!frontier.isEmpty()){

        	// pick current node at random
        	Point cu = frontier.remove((int)(Math.random()*frontier.size()));
        	Point op = cu.opposite();
        	try{
        		// if both node and its opposite are walls
        		if(maz[cu.r][cu.c]=='|'){
        			if(maz[op.r][op.c]=='|'){

        				// open path between the nodes
        				maz[cu.r][cu.c]='*';
        				maz[op.r][op.c]='*';

        				// store last node in order to mark it later
        				last = op;

        				// iterate through direct neighbors of node, same as earlier
        				for(int x=-1;x<=1;x++)
				        	for(int y=-1;y<=1;y++){
				        		if(x==0&&y==0||x!=0&&y!=0)
				        			continue;
				        		try{
				        			if(maz[op.r+x][op.c+y]=='*') continue;
				        		}catch(Exception e){
				        			continue;
				        		}
				        		frontier.add(new Point(op.r+x,op.c+y,op));
				        	}
        			}
        		}
        	}catch(Exception e){ // ignore NullPointer and ArrayIndexOutOfBounds
        	}

        	// if algorithm has resolved, mark end node
        	if(frontier.isEmpty())
        		maz[last.r][last.c]='E';
        }

		// print final maze
		for(int i=0;i<r;i++){
			for(int j=0;j<c;j++) {
			//System.out.print(maz[i][j]+"");
			}
		//	System.out.println();
		}
		
		
		frmMaze = new JFrame();
		frmMaze.setTitle("MAZE");
		frmMaze.setBounds(100, 100, 487, 451);
		frmMaze.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblMazeBy = new JLabel("Maze    ----    By   Anil    Kumar   Yadav");
		lblMazeBy.setFont(new Font("Cambria Math", Font.BOLD, 20));

		JTextArea textArea = new JTextArea(toBoardString(maz));
		textArea.setFont(new Font("Lucida Console", Font.BOLD, 15));
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		textArea.setBackground(new Color(204, 204, 153));
		
		GroupLayout groupLayout = new GroupLayout(frmMaze.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 434, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMazeBy))
					.addContainerGap(55, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMazeBy)
					.addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(12))
		);
		frmMaze.getContentPane().setLayout(groupLayout);

		//==================================================================================================//

    }

    static class Point{
    	Integer r;
    	Integer c;
    	Point parent;
    	public Point(int x, int y, Point p){
    		r=x;c=y;parent=p;
    	}
    	// compute opposite node given that it is in the other direction from the parent
    	public Point opposite(){
    		if(this.r.compareTo(parent.r)!=0)
    			return new Point(this.r+this.r.compareTo(parent.r),this.c,this);
    		if(this.c.compareTo(parent.c)!=0)
    			return new Point(this.r,this.c+this.c.compareTo(parent.c),this);
    		return null;
    	}
    }
}
