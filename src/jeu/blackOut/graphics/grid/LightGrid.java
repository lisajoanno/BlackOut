/*
 * Projet DevInt 2014-2015 - BlackOut
 */
package jeu.blackOut.graphics.grid;

import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JPanel;

import jeu.blackOut.graphics.BlackOut;
import jeu.blackOut.utils.Utils;

/**
 * The Class BlackOut which is the game itself, with lights to turn off.
 */
public class LightGrid extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The number of row. */
	private int nbRow;

	/** The number of column. */
	private int nbColumn;

	/** The difficulty. */
	private int difficulty;

	/** The frame. */
	private BlackOut frame;

	/**
	 * Instantiates a new black out.
	 * 
	 * @param frame
	 *            the frame
	 * @param row
	 *            the row
	 * @param column
	 *            the column
	 * @param difficulty
	 *            the difficulty
	 */
	public LightGrid(BlackOut frame, int row, int column, int difficulty)
	{
		super();

		this.nbRow = row;
		this.nbColumn = column;
		this.difficulty = difficulty;
		this.frame = frame;

		// Setting the grid
		this.setGrid();

		// Apply the difficulty
		this.applyDifficulty();

		// Actualize the grid
		Utils.refresh(this);

		this.setVisible(true);
	}

	/**
	 * Apply difficulty. Simulate randomly one or several clicks.
	 */
	private void applyDifficulty()
	{
		int rdRom;
		int rdColumn;
		
		
		Light lightToClick;

		for (int i = 0; i < this.difficulty; i++)
		{

//			ArrayList<Light> lightDone = new ArrayList<>();

			Random rand = new Random();

			// Getting x axis
			rdRom = rand.nextInt(this.nbRow);

			// Getting y axis
			rdColumn = rand.nextInt(this.nbColumn);

			lightToClick = this.getLightAt(rdRom, rdColumn);

			// Simulate click
			lightToClick.onClick();
		}

		if (isFinished())
			this.applyDifficulty();
			
		
		// // Odd number of click
		// if ((dif % 2) == 0)
		// this.getLightAt(1, 1).onClick();

	}

	/**
	 * Sets the grid.
	 */
	private void setGrid()
	{
		this.setLayout(new GridLayout(nbRow, nbColumn));

		// Adding all Light
		for (int i = 0; i < nbRow; i++)
		{
			for (int j = 0; j < nbColumn; j++)
			{
				Light lightToAdd = new Light(i, j, this, this.frame);
				this.add(lightToAdd);
			}
		}
	}

	/**
	 * Gets the light at.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the light at
	 */
	public Light getLightAt(int x, int y)
	{
		if (x >= this.nbRow || y >= this.nbColumn || x < 0 || y < 0)
		{
			return null;
		}
		else
		{
			try
			{
				return (Light) this.getComponent(this.nbRow * x + y);
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				return null;
			}
		}
	}
	
	public Light getLightAtOrigin()
	{
		try
		{
			return (Light) this.getComponent(0);
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return null;
		}
	}

	/**
	 * Checks if the grid is finished.
	 * 
	 * @return true, if is finished
	 */
	public boolean isFinished()
	{

		for (int i = 0; i < this.nbRow; i++)
			for (int j = 0; j < this.nbColumn; j++)
				if (this.getLightAt(i, j).isLightOn())
					return false;

		return true;
	}

	/**
	 * Gets the number of row.
	 * 
	 * @return the nbRow
	 */
	public int getNbRow()
	{
		return nbRow;
	}

	/**
	 * Gets the number of column.
	 * 
	 * @return the nbColumn
	 */
	public int getNbColumn()
	{
		return nbColumn;
	}

	// public void setLightAt(int index, Light light) {
	// this.remove(index);
	// this.add(light);
	// }
	
	public int getNumberOfElement() {
		return (this.nbColumn * this.nbRow);
	}

}
