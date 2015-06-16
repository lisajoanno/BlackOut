package jeu.blackOut.graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jeu.blackOut.graphics.grid.LightGrid;


public class MyActionListener implements ActionListener {
	
	private LightGrid lightGrid;

	public MyActionListener(LightGrid lightGrid) {
		this.lightGrid = lightGrid;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		
	}

}
