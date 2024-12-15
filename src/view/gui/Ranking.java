package view.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import view.View;

/**
 * 
 * @author Davide Degli Esposti
 *
 */
public final class Ranking extends GUI {
	
	private static final long serialVersionUID = 4592677871472067270L;
	private static final JLabel titleLabel = new JLabel("ScoreBoard");			//contains the title of the view
	private static final JLabel levelNumber = new JLabel("Level n°:");			//label that introduce the combobox
	private JButton back = new JButton("Back");									//button to go back to the main menu
	private JButton generalScoreRank = new JButton("GeneralRank");				//button to see the general ranking
	private JButton levelScoreRank = new JButton("levelScoreRank");				//button to see the ranking of a certain level
	private String[] lvls;														//contains the number of levels as a string
	private JComboBox<String> levelsList;										//combobox to indicate which rank of level shows 

	protected Ranking(final Controller controller, final View view) {
	    super(controller, view);
	    this.lvls = new String[controller.getNumLevels()];
	    JPanel scoreBoardPanel = new JPanel(new FlowLayout());
	    this.add(scoreBoardPanel);
	    for(int i=0; i<lvls.length; i++) {
	        lvls[i] = ""+(i+1);
	    }
	    levelsList = new JComboBox<>(lvls);

	    //add labels and buttons to the main panel
	    scoreBoardPanel.add(titleLabel,BorderLayout.NORTH); 
	    scoreBoardPanel.add(generalScoreRank);
	    scoreBoardPanel.add(levelNumber);
	    scoreBoardPanel.add(levelsList);
	    scoreBoardPanel.add(levelScoreRank);
	    scoreBoardPanel.add(back);
	    
	    //event on back button
	    back.addActionListener(e -> {
	        this.load(new MainMenu(controller, view));
	        controller.getSound().playSound("button_press");
	    });
	    
	    //event on generalScoreRank button
	    generalScoreRank.addActionListener(e->{
	        this.load(new ViewRank(controller, view, controller.getRankByGeneralScore(),"General"));
	        controller.getSound().playSound("button_press");
	    });


	    //event on levelScoreRank button
	    levelScoreRank.addActionListener(e-> {
	        this.load(new ViewRank(controller, view, controller.getRankByLevelScore(levelsList.getSelectedIndex()+1),("Level "+levelsList.getSelectedItem().toString())));
	        controller.getSound().playSound("button_press");
	    });


	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    this.pack();
	}

}
