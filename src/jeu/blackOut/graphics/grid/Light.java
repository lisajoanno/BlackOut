/*
 * Projet DevInt 2014-2015 - BlackOut
 */
package jeu.blackOut.graphics.grid;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import jeu.blackOut.graphics.BlackOut;
import jeu.blackOut.utils.Constants;
import jeu.blackOut.utils.Utils;
import jeu.blackOut.utils.settings.SettingsSave;

/**
 * The Class Light. This is a button you click on to change its statement and its neighbor
 * statement.
 */
public class Light extends JButton implements ActionListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The x. */
	private int x;

	/** The y. */
	private int y;

	/** The light on. */
	private boolean lightOn;

	/** The grid. */
	private LightGrid grid;

	/** The frame. */
	private BlackOut frame;

	/**
	 * Instantiates a new light.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param grid
	 *            the grid
	 * @param frame
	 *            the frame
	 */
	public Light(int x, int y, LightGrid grid, BlackOut frame)
	{
		super("");

		this.x = x;
		this.y = y;
		this.lightOn = false;
		this.grid = grid;
		this.frame = frame;

		// Setting the button
		this.setBackground(Constants.UNLIGHT_COLOR);
		this.addActionListener(this);

		Utils.refresh(this);

	}

	/**
	 * Instantiates a new light.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param grid
	 *            the grid
	 * @param frame
	 *            the frame
	 * @param lightOn
	 *            the light on
	 */
	public Light(int x, int y, LightGrid grid, BlackOut frame, boolean lightOn)
	{
		super("");

		this.x = x;
		this.y = y;
		this.lightOn = lightOn;
		this.grid = grid;
		this.frame = frame;

		// Setting the button
		if (lightOn)
			this.setBackground(Constants.LIGHT_COLOR);
		else
			this.setBackground(Constants.UNLIGHT_COLOR);

		this.addActionListener(this);

		Utils.refresh(this);
	}

	/**
	 * Checks if is light on.
	 * 
	 * @return the lightOn
	 */
	public boolean isLightOn()
	{
		return lightOn;
	}

	/**
	 * Sets the light on.
	 * 
	 * @param lightOn
	 *            the lightOn to set
	 */
	public void setLightOn(boolean lightOn)
	{
		this.lightOn = lightOn;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// Action
		this.onPlayerClick();
	}

	public void onPlayerClick()
	{
		this.frame.increaseClickNumber();
		this.onClick();
	}

	/**
	 * Action on click.
	 */
	public void onClick()
	{
		// Case clicked
		this.switchLigthStatement();

		// East case
		Light lightToChange = this.grid.getLightAt(this.x + 1, this.y);
		try
		{
			lightToChange.switchLigthStatement();
		}
		catch (NullPointerException e)
		{}

		// West case
		lightToChange = this.grid.getLightAt(this.x - 1, this.y);
		try
		{
			lightToChange.switchLigthStatement();
		}
		catch (NullPointerException e)
		{}

		// North case
		lightToChange = this.grid.getLightAt(this.x, this.y + 1);
		try
		{
			lightToChange.switchLigthStatement();
		}
		catch (NullPointerException e)
		{}

		// South case
		lightToChange = this.grid.getLightAt(this.x, this.y - 1);
		try
		{
			lightToChange.switchLigthStatement();
		}
		catch (NullPointerException e)
		{}

		this.frame.requestFocus();

		// System.out.println("Finished ? : " + this.grid.isFinished());

		// If grid is finished after the click :
		// Launch a new grid with n+1 rows/columns
		if (this.grid.isFinished())
		{

			this.frame.resetClickNumber();

			this.frame.increaseLevel();

			if (!SettingsSave.musicDisabled)
			    this.frame.playVictorySound();

			int rows = this.grid.getNbRow();
			int columns = this.grid.getNbColumn();

			if (this.frame.getIndice() == this.grid.getNbRow())
			{
				this.frame.setIndice(0);
				rows++;
				columns++;
			}

			this.frame.setNewGrid(rows, columns, (rows * columns) / 2 - 1);

			Utils.refresh(this.frame);

		}

	}

	/**
	 * Switch the light statement.
	 */
	public void switchLigthStatement()
	{
		if (this != null)
		{
			this.setLightOn(!this.isLightOn());
			this.actualiseColor();
		}
	}

	/**
	 * Actualize color.
	 */
	public void actualiseColor()
	{
		if (this != null)
		{
			if (this.isLightOn())
			{
				this.setBackground(Constants.LIGHT_COLOR);
			}
			else
			{
				this.setBackground(Constants.UNLIGHT_COLOR);
			}

			Utils.refresh(this);
		}
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Light other = (Light) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
