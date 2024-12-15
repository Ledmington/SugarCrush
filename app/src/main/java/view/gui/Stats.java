package view.gui;

import static controller.files.FileTypes.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.*;

import static controller.Controller.playerName;

import controller.Controller;
import controller.files.*;
import view.View;

/**
 * A {@link GUI} that shows the stats of the current player
 * 
 * @author Emanuele Lamagna
 *
 */
public final class Stats extends GUI {

	private static final long serialVersionUID = -6538372787351542506L;
	private Map<String, Object> player = new HashMap<>();
	
	protected Stats(final Controller controller, final View view) {
		super(controller, view);
		//initialize the map
		controller.getPlayers(STATS)
					.stream()
					.filter(map -> map.get(playerName).toString().equals(("\"" + controller.getCurrentPlayer() + "\"")))
					.forEach(map -> this.player = map);
		this.setLayout(new BorderLayout());
		final JPanel stats = new JPanel();
		
		//set the stats panel
		stats.setLayout(new GridLayout(player.size()+1, 2));
		stats.add(new JLabel("STATS"));
		stats.add(new JLabel("VALUES"));
		stats.add(new JLabel("Player"));
		stats.add(new JLabel(controller.getCurrentPlayer()));
		Stream.of(StatsTypes.values()).forEach(s -> {
			stats.add(new JLabel(s.getDescription()));
			stats.add(new JLabel(player.get(s.name()).toString()));
		});
		Stream.of(stats.getComponents()).forEach(c -> ((JLabel)c).setHorizontalAlignment(JLabel.CENTER));
		
		this.getContentPane().add(stats, BorderLayout.CENTER);;
		
		//set the back button
		final JButton back = new JButton("BACK");
		back.addActionListener(e -> {
			this.load(new MainMenu(controller, view));
			controller.getSound().playSound("button_press");
		});
		this.add(back, BorderLayout.SOUTH);
		
		//set the graphics
		stats.setBackground(Color.pink);
		stats.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

	}
	
}
