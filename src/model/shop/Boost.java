package model.shop;
import model.game.grid.candies.*;

/**
 * 
 * @author Davide Degli Esposti
 *
 */
public final class Boost {
	
	private String boostName;	//the name of the boost (wrapped,freckles,etc)
	private int price;			//the price of the boost in the shop
	private Candy candy;		//the type of boost
	
	/**
	 * the constructor of the object Boost
	 * @param price  the price of the boost
	 *@param candy  the candy that is represented by the object Boost
	 */
	public Boost(final String name, final int price, final Candy candy) {
		this.boostName = name;
		this.price = price;
		this.candy = candy;
	}
	
	/**
	 * 
	 * @return the name of the boost
	 */
	public final String getName() {
		return this.boostName;
	}
	/**
	 * 
	 * @return the price of the boost
	 */
	public final Integer getPrice() {
		return this.price;
	}
	
	/**
	 * 
	 * @return the type of the boost
	 */
	public final Candy getCandy() {
		return this.candy;
	}

}
