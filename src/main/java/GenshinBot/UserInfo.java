package GenshinBot;

import java.util.ArrayList;
import java.util.List;


public class UserInfo {
	
	public Banner currentBanner;
	public Banner eventBanner;
	public Banner weaponBanner;
	public Banner standardBanner;
	
	public int pityFive;
	public int pityFour;
	public boolean promoFive;
	public boolean promoFour;	
	
	//State of the bot by message id
	public long currentMessage = -1;
	public State state;
	
	
	//States
	public static  enum State{
		CHOOSE_BANNER_STATE,
		PITY_FIVE_STATE,
		PITY_FOUR_STATE,
		PROMO_FIVE_STATE,
		PROMO_FOUR_STATE,
		WISH_STATE,
		WAIT_STATE
	}
	
	public UserInfo() {
		makeBanners();
	}
	
	
	public void resetBanners() {
		setEventBanner(1, 1, false, false);
		setWeaponBanner(1, 1, false, false);
		setStandardBanner(1, 1);
	}

	public boolean isValidPityFive(String command) {
		try {
			int pity = Integer.parseInt(command);
			if(pity <= this.currentBanner.maxPityFive() && pity>= 1) {
				return true;
			}
		}
		catch (NumberFormatException e) {
			return false;
		}
		return false;
	}
	public boolean isValidPityFour(String command) {
		try {
			int pity = Integer.parseInt(command);
			if(pity <= this.currentBanner.maxPityFour() && pity>= 1) {
				return true;
			}
		}
		catch (NumberFormatException e) {
			return false;
		}
		return false;
	}
	
	private void makeBanners() {
		makeWeaponBanner();
		makeEventBanner();
		makeStandardBanner();
	}
	public void setEventBanner(int pityFive, int pityFour, boolean promoFive, boolean promoFour) {
		this.eventBanner.setPityFiveStar(pityFive);
		this.eventBanner.setPityFourStar(pityFour);
		this.eventBanner.setPromoFiveStar(promoFive);
		this.eventBanner.setPromoFourStar(promoFour);
	}
	public void setStandardBanner(int pityFive, int pityFour) {
		this.standardBanner.setPityFiveStar(pityFive);
		this.standardBanner.setPityFourStar(pityFour);
	}
	public void setWeaponBanner(int pityFive, int pityFour, boolean promoFive, boolean promoFour) {
		this.weaponBanner.setPityFiveStar(pityFive);
		this.weaponBanner.setPityFourStar(pityFour);
		this.weaponBanner.setPromoFiveStar(promoFive);
		this.weaponBanner.setPromoFourStar(promoFour);
	}
	
	public void setBannerValues(int pityFive, int pityFour, boolean promoFive, boolean promoFour) {
		if(this.currentBanner instanceof EventBanner) {
			this.setEventBanner(pityFive, pityFour, promoFive, promoFour);
		}
		else if(this.currentBanner instanceof WeaponBanner) {
			this.setWeaponBanner(pityFive, pityFour, promoFive, promoFour);
		}
		else if(this.currentBanner instanceof StandardBanner) {
			this.setStandardBanner(pityFive, pityFour);
		}
	}
	
	private void makeWeaponBanner() {
		List<Item> threeStars = new ArrayList<Item>();
		List<Item> fourStars = new ArrayList<Item>();
		List<Item> fiveStars = new ArrayList<Item>();
		
		List<Item> fourStarPromos = new ArrayList<Item>();
		
		fourStarPromos.add(new Item(false, 4, "The Stringless"));
		fourStarPromos.add(new Item(false, 4, "Favonius Lance"));
		fourStarPromos.add(new Item(false, 4, "Favonius Sword"));
		fourStarPromos.add(new Item(false, 4, "Favonius Greatsword"));
		fourStarPromos.add(new Item(false, 4, "Sacrificial Fragments"));
		
		
		Item promo1 = new Item(false, 5, "Skyward Atlas");
		Item promo2 = new Item(false, 5, "Summit Shaper");
		
		//5 stars
		fiveStars.add(new Item(false, 5, "Wolf's Gravestone"));
		fiveStars.add(new Item(false, 5, "Skyward Blade"));
		fiveStars.add(new Item(false, 5, "Skyward Harp"));
		fiveStars.add(new Item(false, 5, "Skyward Spine"));
		fiveStars.add(new Item(false, 5, "Skyward Pride"));
		fiveStars.add(new Item(false, 5, "Aquila Favonia"));
		fiveStars.add(new Item(false, 5, "Amos' Bow"));
		fiveStars.add(new Item(false, 5, "Lost Prayer to the Sacred Winds"));
		fiveStars.add(new Item(false, 5, "Primordial Jade Winged-Spear"));
		
		
		//4 stars
		//4 star characters
		fourStars.add(new Item(true, 4, "Sucrose"));
		fourStars.add(new Item(true, 4, "Fischl"));
		fourStars.add(new Item(true, 4, "Bennett"));
		fourStars.add(new Item(true, 4, "Xinyan"));
		fourStars.add(new Item(true, 4, "Diona"));
		fourStars.add(new Item(true, 4, "Chongyun"));
		fourStars.add(new Item(true, 4, "Noelle"));
		fourStars.add(new Item(true, 4, "Ningguang"));
		fourStars.add(new Item(true, 4, "Xingqui"));
		fourStars.add(new Item(true, 4, "Beidou"));
		fourStars.add(new Item(true, 4, "Xiangling"));
		fourStars.add(new Item(true, 4, "Razor"));
		fourStars.add(new Item(true, 4, "Barbara"));
		
		//4 star weapons
		fourStars.add(new Item(false, 4, "Sacrificial Bow"));
		fourStars.add(new Item(false, 4, "Favonius Warbow"));
		fourStars.add(new Item(false, 4, "Eye of Perception"));
		fourStars.add(new Item(false, 4, "The Widsith"));
		fourStars.add(new Item(false, 4, "Favonius Codex"));
		fourStars.add(new Item(false, 4, "Dragon's Bane"));
		fourStars.add(new Item(false, 4, "Rainslasher"));
		fourStars.add(new Item(false, 4, "The Bell"));
		fourStars.add(new Item(false, 4, "Lion's Roar"));
		fourStars.add(new Item(false, 4, "Sacrificial Sword"));
		fourStars.add(new Item(false, 4, "The Flute"));
		fourStars.add(new Item(false, 4, "Rust"));
		fourStars.add(new Item(false, 4, "Sacrificial Greatsword"));
		
		//3 stars
		threeStars.add(new Item(false, 3, "Slingshot"));
		threeStars.add(new Item(false, 3, "Sharpshooter's Oath"));
		threeStars.add(new Item(false, 3, "Raven Bow"));
		threeStars.add(new Item(false, 3, "Emerald Orb"));
		threeStars.add(new Item(false, 3, "Thrilling Tales of Dragon Slayers"));
		threeStars.add(new Item(false, 3, "Magic Guide"));
		threeStars.add(new Item(false, 3, "Black Tassel"));
		threeStars.add(new Item(false, 3, "Debate Club"));
		threeStars.add(new Item(false, 3, "Bloodtainted Greatsword"));
		threeStars.add(new Item(false, 3, "Ferrous Shadow"));
		threeStars.add(new Item(false, 3, "Skyrider Sword"));
		threeStars.add(new Item(false, 3, "Harbinger of Dawn"));
		threeStars.add(new Item(false, 3, "Cool Steel"));
		
		this.weaponBanner = new WeaponBanner(threeStars, fourStars, fiveStars);
		this.weaponBanner.setPromoFourStars(fourStarPromos);
		this.weaponBanner.setPromoWeapon1(promo1);
		this.weaponBanner.setPromoWeapon2(promo2);
	}
	private void makeEventBanner() {
		List<Item> threeStars = new ArrayList<Item>();
		List<Item> fourStars = new ArrayList<Item>();
		List<Item> fiveStars = new ArrayList<Item>();
		List<Item> fourStarPromos = new ArrayList<Item>();
		
		//Promo 5 stars
		Item promoChar = new Item(true, 5, "Albedo");
		
		//Promo 4 stars
		fourStarPromos.add(new Item(true, 4, "Sucrose"));
		fourStarPromos.add(new Item(true, 4, "Fischl"));
		fourStarPromos.add(new Item(true, 4, "Bennett"));
		
		
		//Add 5 star characters
		fiveStars.add(new Item(true, 5, "Mona"));
		fiveStars.add(new Item(true, 5, "Keqing"));
		fiveStars.add(new Item(true, 5, "Qiqi"));
		fiveStars.add(new Item(true, 5, "Jean"));
		fiveStars.add(new Item(true, 5, "Diluc"));
		
		//Add 4 star items
		//4 star chars
		fourStars.add(new Item(true, 4, "Xinyan"));
		fourStars.add(new Item(true, 4, "Diona"));
		fourStars.add(new Item(true, 4, "Chongyun"));
		fourStars.add(new Item(true, 4, "Noelle"));
		fourStars.add(new Item(true, 4, "Ningguang"));
		fourStars.add(new Item(true, 4, "Xingqui"));
		fourStars.add(new Item(true, 4, "Beidou"));
		fourStars.add(new Item(true, 4, "Xiangling"));
		fourStars.add(new Item(true, 4, "Razor"));
		fourStars.add(new Item(true, 4, "Barbara"));
		
		
		//4 star weapons
		fourStars.add(new Item(false, 4, "Sacrificial Bow"));
		fourStars.add(new Item(false, 4, "The Stringless"));
		fourStars.add(new Item(false, 4, "Favonius Warbow"));
		fourStars.add(new Item(false, 4, "Eye of Perception"));
		fourStars.add(new Item(false, 4, "Sacrificial Fragments"));
		fourStars.add(new Item(false, 4, "The Widsith"));
		fourStars.add(new Item(false, 4, "Favonius Codex"));
		fourStars.add(new Item(false, 4, "Favonius Lance"));
		fourStars.add(new Item(false, 4, "Dragon's Bane"));
		fourStars.add(new Item(false, 4, "Rainslasher"));
		fourStars.add(new Item(false, 4, "Sacrificial Greatsword"));
		fourStars.add(new Item(false, 4, "The Bell"));
		fourStars.add(new Item(false, 4, "Favonius Greatsword"));
		fourStars.add(new Item(false, 4, "Lion's Roar"));
		fourStars.add(new Item(false, 4, "Sacrificial Sword"));
		fourStars.add(new Item(false, 4, "The Flute"));
		fourStars.add(new Item(false, 4, "Favonius Sword"));
		
		
		
		//Add 3 star weapons
		threeStars.add(new Item(false, 3, "Slingshot"));
		threeStars.add(new Item(false, 3, "Sharpshooter's Oath"));
		threeStars.add(new Item(false, 3, "Raven Bow"));
		threeStars.add(new Item(false, 3, "Emerald Orb"));
		threeStars.add(new Item(false, 3, "Thrilling Tales of Dragon Slayers"));
		threeStars.add(new Item(false, 3, "Magic Guide"));
		threeStars.add(new Item(false, 3, "Black Tassel"));
		threeStars.add(new Item(false, 3, "Debate Club"));
		threeStars.add(new Item(false, 3, "Bloodtainted Greatsword"));
		threeStars.add(new Item(false, 3, "Ferrous Shadow"));
		threeStars.add(new Item(false, 3, "Skyrider Sword"));
		threeStars.add(new Item(false, 3, "Harbinger of Dawn"));
		threeStars.add(new Item(false, 3, "Cool Steel"));
		
		this.eventBanner = new EventBanner(threeStars, fourStars, fiveStars);
		this.eventBanner.setPromoChar(promoChar);
		this.eventBanner.setPromoFourStars(fourStarPromos);
		
	}
	private void makeStandardBanner() {
		List<Item> threeStars = new ArrayList<Item>();
		List<Item> fourStars = new ArrayList<Item>();
		List<Item> fiveStars = new ArrayList<Item>();
		
		
		//Five stars
		//Add 5 star characters
		fiveStars.add(new Item(true, 5, "Mona"));
		fiveStars.add(new Item(true, 5, "Keqing"));
		fiveStars.add(new Item(true, 5, "Qiqi"));
		fiveStars.add(new Item(true, 5, "Jean"));
		fiveStars.add(new Item(true, 5, "Diluc"));
		//5 star weapons
		fiveStars.add(new Item(false, 5, "Skyward Harp"));
		fiveStars.add(new Item(false, 5, "Skyward Atlas"));
		fiveStars.add(new Item(false, 5, "Skyward Spine"));
		fiveStars.add(new Item(false, 5, "Skyward Pride"));
		fiveStars.add(new Item(false, 5, "Aquila Favonia"));
		fiveStars.add(new Item(false, 5, "Amos' Bow"));
		fiveStars.add(new Item(false, 5, "Lost Prayer to the Sacred Winds"));
		fiveStars.add(new Item(false, 5, "Primordial Jade Winged-Spear"));
		fiveStars.add(new Item(false, 5, "Wolf's Gravestone"));
		fiveStars.add(new Item(false, 5, "Skyward Blade"));
		
		
		
		//4 stars
		//4 star characters
		fourStars.add(new Item(true, 4, "Sucrose"));
		fourStars.add(new Item(true, 4, "Fischl"));
		fourStars.add(new Item(true, 4, "Bennett"));
		fourStars.add(new Item(true, 4, "Xinyan"));
		fourStars.add(new Item(true, 4, "Diona"));
		fourStars.add(new Item(true, 4, "Chongyun"));
		fourStars.add(new Item(true, 4, "Noelle"));
		fourStars.add(new Item(true, 4, "Ningguang"));
		fourStars.add(new Item(true, 4, "Xingqui"));
		fourStars.add(new Item(true, 4, "Beidou"));
		fourStars.add(new Item(true, 4, "Xiangling"));
		fourStars.add(new Item(true, 4, "Razor"));
		fourStars.add(new Item(true, 4, "Barbara"));
		fourStars.add(new Item(true, 4, "Amber"));
		fourStars.add(new Item(true, 4, "Kaeya"));
		fourStars.add(new Item(true, 4, "Lisa"));
		
		
		
		//4 star weapons
		fourStars.add(new Item(false, 4, "Sacrificial Bow"));
		fourStars.add(new Item(false, 4, "The Stringless"));
		fourStars.add(new Item(false, 4, "Favonius Warbow"));
		fourStars.add(new Item(false, 4, "Eye of Perception"));
		fourStars.add(new Item(false, 4, "Sacrificial Fragments"));
		fourStars.add(new Item(false, 4, "The Widsith"));
		fourStars.add(new Item(false, 4, "Favonius Codex"));
		fourStars.add(new Item(false, 4, "Favonius Lance"));
		fourStars.add(new Item(false, 4, "Dragon's Bane"));
		fourStars.add(new Item(false, 4, "Rainslasher"));
		fourStars.add(new Item(false, 4, "Sacrificial Greatsword"));
		fourStars.add(new Item(false, 4, "The Bell"));
		fourStars.add(new Item(false, 4, "Favonius Greatsword"));
		fourStars.add(new Item(false, 4, "Lion's Roar"));
		fourStars.add(new Item(false, 4, "Sacrificial Sword"));
		fourStars.add(new Item(false, 4, "The Flute"));
		fourStars.add(new Item(false, 4, "Favonius Sword"));
		fourStars.add(new Item(false, 4, "Rust"));
		
		
		//Add 3 star weapons
		threeStars.add(new Item(false, 3, "Slingshot"));
		threeStars.add(new Item(false, 3, "Sharpshooter's Oath"));
		threeStars.add(new Item(false, 3, "Raven Bow"));
		threeStars.add(new Item(false, 3, "Emerald Orb"));
		threeStars.add(new Item(false, 3, "Thrilling Tales of Dragon Slayers"));
		threeStars.add(new Item(false, 3, "Magic Guide"));
		threeStars.add(new Item(false, 3, "Black Tassel"));
		threeStars.add(new Item(false, 3, "Debate Club"));
		threeStars.add(new Item(false, 3, "Bloodtainted Greatsword"));
		threeStars.add(new Item(false, 3, "Ferrous Shadow"));
		threeStars.add(new Item(false, 3, "Skyrider Sword"));
		threeStars.add(new Item(false, 3, "Harbinger of Dawn"));
		threeStars.add(new Item(false, 3, "Cool Steel"));
		
		this.standardBanner = new StandardBanner(threeStars, fourStars, fiveStars);
		
		
	}
}
