//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Class          : vendingMachine.Model
//
// Author         : Richard E. Pattis
//                  Computer Science Department
//                  Carnegie Mellon University
//                  5000 Forbes Avenue
//                  Pittsburgh, PA 15213-3891
//                  e-mail: pattis@cs.cmu.edu
//
// Maintainer     : Author
//
//
// Description:
//
//   The Model for the VendingMachine package implements the guts of the
// vending machine: it responds to presses of buttons created by the
// Conroller (deposit, cancel, buy), and tells the View when it needs
// to update its display (calling the update in view, which calls the
// accessor methods in this classes)
// 
//   Note that "no access modifier" means that the method is package
// friendly: this means the member is public to all other classes in
// the calculator package, but private elsewhere.
//
// Future Plans   : More Comments
//                  Increase price as stock goes down
//                  Decrease price if being outsold by competition
//                  Allow option to purchase even if full change cannot 
//                    be returned (purchaser pays a premium to quench thirst)
//                  Allow user to enter 2 x money and gamble: 1/2 time
//                    all money returned with product; 1/2 time no money and
//                    no product returned
//
// Program History:
//   9/20/01: R. Pattis - Operational for 15-100
//   2/10/02: R. Pattis - Fixed Bug in change-making method
//
//
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////


package vendingMachine;


import java.lang.Math;


public class Model {
	//Define fields (all instance variables)

	private View view;         // Model must tell View when to update itself
	
	private int    cokeLeft;
	private int    pepsiLeft;
	private final int cokePrice;
	private final int pepsiPrice;
	private int cokeBought;
	private int pepsiBought;
	private int    quartersLeft, dimesLeft, nickelsLeft;
	private int baseQuarters, baseDimes, baseNickels;
	private int deposited;
	private String message;

	//I defined about 10 more fields
	
	//Define constructor
	public Model(int quarters, int dimes, int nickels, int cokeLeft, int pepsiLeft, int cokePrice, int pepsiPrice){
		this.cokeLeft = cokeLeft;
		this.pepsiLeft = pepsiLeft;
		this.cokePrice = cokePrice;
		this.pepsiPrice = pepsiPrice;
		this.quartersLeft = quarters;
		this.baseQuarters = quarters;
		this.dimesLeft = dimes;
		this.baseDimes = dimes;
		this.nickelsLeft = nickels;
		this.baseNickels = nickels;
		cokeBought = 0;
		pepsiBought = 0;
		message = "Hi!";
	}
	//Refer to the view (used to call update after each button press)
	public void addView(View v)
	{view = v;}
	public void cancel(){
		deposited += cokeBought * cokePrice + pepsiBought * pepsiPrice;
		cokeLeft += cokeBought;
		cokeBought = 0;
		pepsiLeft += pepsiBought;
		pepsiBought = 0;
		nickelsLeft = baseNickels;
		dimesLeft = baseDimes;
		quartersLeft = baseQuarters;
		deposited = quartersLeft * 25 + dimesLeft * 10 + nickelsLeft * 5;
		message = "Your pending orders have been cancelled and your money has been returned.";
		view.update();
		message = "Hi!";
	}
	public void deposit(int amount){
		if (amount == 5){
			nickelsLeft += 1;
			baseNickels++;
		}
		else if (amount == 10){
			dimesLeft += 1;
			baseDimes++;
		}
		else {
			if (amount == 25){
				quartersLeft += 1;
				baseQuarters++;
			}
		}
		deposited = quartersLeft * 25 + dimesLeft * 10 + nickelsLeft * 5;
		view.update();
	}
	public void buy(String product) {
		if (product.charAt(0) == 'C') {
			if (cokeLeft >= 1 && deposited >= cokePrice) {
				cokeLeft--;
				int change = deposited - cokePrice;
				deposited -= cokePrice;
				cokeBought++;
				quartersLeft = 0;
				dimesLeft = 0;
				nickelsLeft = 0;
				while (change >= 25){
					quartersLeft++;
					change -= 25;
				}
				while (change >= 10){
					dimesLeft++;
					change -= 10;
				}
				while (change >= 5){
					nickelsLeft++;
					change -= 5;
				}
				message = "You bought a coke!";
			} else message = "You do not have enough money deposited!";
			view.update();
		} else if (product.charAt(0) == 'P') {
			if (pepsiLeft >= 1 && deposited >= pepsiPrice) {
				pepsiLeft--;
				int change = deposited - cokePrice;
				deposited -= pepsiPrice;
				pepsiBought++;
				quartersLeft = 0;
				dimesLeft = 0;
				nickelsLeft = 0;
				while (change >= 25){
					quartersLeft++;
					change -= 25;
				}
				while (change >= 10){
					dimesLeft++;
					change -= 10;
				}
				while (change >= 5){
					nickelsLeft++;
					change -= 5;
				}
				message = "You bought a pepsi!";
			} else message = "You do not have enough money deposited!";
			view.update();
		}
	}

	//Define required methods: mutators (setters) and accessors (getters)
	public String getDeposited(){
		return deposited + " cents";
	}
	public String getMessage(){
		return message;
	}
	public int getCokeLeft(){
		return cokeLeft;
	}
	public int getPepsiLeft(){
		return pepsiLeft;
	}
	public String getCokePrice(){
		return " " + cokePrice + "c";
	}
	public String getPepsiPrice(){
		return " " + pepsiPrice + "c";
	}

	//Represent "interesting" state of vending machine
	public String toString()
	{
		return "Vending Machine State: \n" +
			"  Coke     Left      = " + cokeLeft     + "\n" +
			"  Pepsi    Left      = " + pepsiLeft    + "\n" +
			"  Coke     Bought    = " + cokeBought  + "\n" +
			"  Pepsi    Bought   = " + pepsiBought  + "\n" +
			"  Quarters Left      = " + quartersLeft + "\n" +
			"  Dimes    Left      = " + dimesLeft    + "\n" +
			"  Nickels  Left      = " + nickelsLeft  + "\n" +
			"  Base Quarters      = " + baseQuarters  + "\n" +
			"  Base Dimes         = " + baseDimes  + "\n" +
			"  Base Nickels       = " + baseNickels  + "\n" +
			"  Coke Price         = " + cokePrice  + "\n" +
			"  Pepsi Price        = " + pepsiPrice  + "\n" +
			"  Total Deposited (cents)      = " + deposited  + "\n";
		//Display any other instance variables that you declare too

	}
	
	//Define helper methods

}
