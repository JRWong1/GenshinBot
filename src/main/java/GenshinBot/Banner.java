package GenshinBot;

import java.util.*;

public abstract class Banner {
	
	public static final double CHAR_BANNER_FOUR_STAR_PERCENT = 5.1;
	public static final double WEAPON_BANNER_FOUR_STAR_PERCENT = 6;
	
	protected List<Item> threeStars;
	protected List<Item> fourStars;
	protected List<Item> fiveStars;
	protected Item promoChar;
	protected List<Item> promoFourStars;
	protected Item promoWeapon1;
	protected Item promoWeapon2;
	protected int pityFiveStar = 0;
	protected int pityFourStar = 0;
	
	
	protected boolean promoFiveStar;
	protected boolean promoFourStar;
	
	public Banner() {
		
	}
	
	public Banner(List<Item> threeStars, List<Item> fourStars, List<Item> fiveStars) {
		this.threeStars = threeStars;
		this.fourStars = fourStars;
		this.fiveStars = fiveStars;
	}
	
	public abstract Item rollOne();
	
	public List<Item> rollTen() {
		List<Item> result = new ArrayList<Item>();
		for(int i = 1; i <= 10; i++) {
			result.add(rollOne());
		}
		return result;
	}
	
	
	public int maxPityFive() {
		if(this instanceof WeaponBanner) {
			System.out.println("we got a weapon banner instance");
			return 80;
		}
		System.out.println("we got a non weapon banner instance");
		return 90;
	}
	public int maxPityFour() {
		return 10;
	}
	
	//Make random number from 0 to 100
	public double randomNumber() {
		double random = Math.random();
		random = 100*random;
		return random;
	}
		
	public void setPromoWeapon1(Item weapon) {
		this.promoWeapon1 = weapon;
	}
	public void setPromoWeapon2(Item weapon) {
		this.promoWeapon2 = weapon;
	}
	public void setPromoFourStars(List<Item> promos) {
		this.promoFourStars = promos;
	}
	
	public void setPromoFiveStar(boolean promo) {
		this.promoFiveStar = promo;
	}
	public void setPromoFourStar(boolean promo) {
		this.promoFourStar = promo;
	}
	public void setPromoChar(Item promoChar) {
		this.promoChar = promoChar;
	}
	public void setPityFiveStar(int pity) {
		this.pityFiveStar = pity;
	}
	public void setPityFourStar(int pity) {
		this.pityFourStar = pity;
	}
	
	
	//5 star calculation
	public double calcPercentChance(int pity) {
		//Soft pity hard coded
		if(pity == 76) {
			return 20.627;
		}
		if(pity == 77) {
			return 13.946;
		}
		if(pity == 78) {
			return 9.429;
		}
		if(pity == 79) {
			return 6.375;
		}
		if(pity == 80) {
			return 4.306;
		}
		if(pity == 81) {
			return 2.914;
		}
		if(pity == 82) {
			return 1.97;
		}
		if(pity == 83) {
			return 1.332;
		}
		if(pity == 84) {
			return 0.901;
		}
		if(pity == 85) {
			return 0.608;
		}
		if(pity == 86) {
			return 0.411;
		}
		if(pity == 87) {
			return 0.278;
		}
		if(pity == 88) {
			return 0.187;
		}
		if(pity == 89) {
			return 0.126;
		}
		if(pity == 90) {
			return 100;
		}
		double result = 0.6;
		int difference = pity - 1;
		double slope = 0.0029054;
		result -= (double) difference * (double) slope;
		
		return result;
	}
}
