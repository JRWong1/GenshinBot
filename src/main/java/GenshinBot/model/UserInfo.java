package GenshinBot.model;

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
	public long lastInteraction;
	
	
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
		
		//4 star promo weapons
		fourStarPromos.add(ItemTable.PROMO_4_WEAPON_1);
		fourStarPromos.add(ItemTable.PROMO_4_WEAPON_2);
		fourStarPromos.add(ItemTable.PROMO_4_WEAPON_3);
		fourStarPromos.add(ItemTable.PROMO_4_WEAPON_4);
		fourStarPromos.add(ItemTable.PROMO_4_WEAPON_5);
		
		Item promo1 = ItemTable.PROMO_5_WEAPON_1;
		Item promo2 = ItemTable.PROMO_5_WEAPON_2;
		
		//Item promo1 = new Item(false, 5, "Skyward Atlas");
		//Item promo2 = new Item(false, 5, "Summit Shaper");
		
		//5 stars
		fiveStars.add(ItemTable.SKYWARD_ATLAS);
		fiveStars.add(ItemTable.SUMMIT_SHAPER);
		fiveStars.add(ItemTable.WOLFS_GRAVESTONE);
		fiveStars.add(ItemTable.SKYWARD_BLADE);
		fiveStars.add(ItemTable.SKYWARD_HARP);
		fiveStars.add(ItemTable.SKYWARD_SPINE);
		fiveStars.add(ItemTable.SKYWARD_PRIDE);
		fiveStars.add(ItemTable.AQUILA_FAVONIA);
		fiveStars.add(ItemTable.AMOS_BOW);
		fiveStars.add(ItemTable.LOST_PRAYER_TO_THE_SACRED_WINDS);
		fiveStars.add(ItemTable.PRIMORDIAL_JADE_WINGED_SPEAR);
		
		//4 stars
		//4 star characters
		fourStars.add(ItemTable.SUCROSE);
		fourStars.add(ItemTable.FISCHL);
		fourStars.add(ItemTable.BENNETT);
		fourStars.add(ItemTable.XINYAN);
		fourStars.add(ItemTable.DIONA);
		fourStars.add(ItemTable.CHONGYUN);
		fourStars.add(ItemTable.NOELLE);
		fourStars.add(ItemTable.NINGGUANG);
		fourStars.add(ItemTable.XINGQUI);
		fourStars.add(ItemTable.BEIDOU);
		fourStars.add(ItemTable.XIANGLING);
		fourStars.add(ItemTable.RAZOR);
		fourStars.add(ItemTable.BARBARA);
		
		//4 star weapons
		fourStars.add(ItemTable.SACRIFICIAL_BOW);
		fourStars.add(ItemTable.THE_STRINGLESS);
		fourStars.add(ItemTable.FAVONIUS_WARBOW);
		fourStars.add(ItemTable.EYE_OF_PERCEPTION);
		fourStars.add(ItemTable.SACRIFICIAL_FRAGMENTS);
		fourStars.add(ItemTable.THE_WIDSITH);
		fourStars.add(ItemTable.FAVONIUS_CODEX);
		fourStars.add(ItemTable.FAVONIUS_LANCE);
		fourStars.add(ItemTable.DRAGONS_BANE);
		fourStars.add(ItemTable.RAINSLASHER);
		fourStars.add(ItemTable.SACRIFICIAL_GREATSWORD);
		fourStars.add(ItemTable.THE_BELL);
		fourStars.add(ItemTable.FAVONIUS_GREATSWORD);
		fourStars.add(ItemTable.LIONS_ROAR);
		fourStars.add(ItemTable.SACRIFICIAL_SWORD);
		fourStars.add(ItemTable.THE_FLUTE);
		fourStars.add(ItemTable.FAVONIUS_SWORD);
		fourStars.add(ItemTable.RUST);
		
		//3 stars
		threeStars.add(ItemTable.SLINGSHOT);
		threeStars.add(ItemTable.SHARPSHOOTERS_OATH);
		threeStars.add(ItemTable.RAVEN_BOW);
		threeStars.add(ItemTable.EMERALD_ORB);
		threeStars.add(ItemTable.THRILLING_TALES_OF_DRAGON_SLAYERS);
		threeStars.add(ItemTable.MAGIC_GUIDE);
		threeStars.add(ItemTable.BLACK_TASSEL);
		threeStars.add(ItemTable.DEBATE_CLUB);
		threeStars.add(ItemTable.BLOODTAINTED_GREATSWORD);
		threeStars.add(ItemTable.FERROUS_SHADOW);
		threeStars.add(ItemTable.SKYRIDER_SWORD);
		threeStars.add(ItemTable.HARBINGER_OF_DAWN);
		threeStars.add(ItemTable.COOL_STEEL);
		
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
		Item promoChar = ItemTable.PROMO_5_CHAR;
		
		//Promo 4 stars
		fourStarPromos.add(ItemTable.PROMO_4_CHAR_1);
		fourStarPromos.add(ItemTable.PROMO_4_CHAR_2);
		fourStarPromos.add(ItemTable.PROMO_4_CHAR_3);
		
		
		//Add 5 star characters
		fiveStars.add(ItemTable.MONA);
		fiveStars.add(ItemTable.KEQING);
		fiveStars.add(ItemTable.QIQI);
		fiveStars.add(ItemTable.JEAN);
		fiveStars.add(ItemTable.DILUC);
		
		//Add 4 star items
		//4 star chars
		fourStars.add(ItemTable.XINYAN);
		fourStars.add(ItemTable.DIONA);
		fourStars.add(ItemTable.CHONGYUN);
		fourStars.add(ItemTable.NOELLE);
		fourStars.add(ItemTable.NINGGUANG);
		fourStars.add(ItemTable.XINGQUI);
		fourStars.add(ItemTable.BEIDOU);
		fourStars.add(ItemTable.XIANGLING);
		fourStars.add(ItemTable.RAZOR);
		fourStars.add(ItemTable.BARBARA);
		
		//4 star weapons
		fourStars.add(ItemTable.SACRIFICIAL_BOW);
		fourStars.add(ItemTable.THE_STRINGLESS);
		fourStars.add(ItemTable.FAVONIUS_WARBOW);
		fourStars.add(ItemTable.EYE_OF_PERCEPTION);
		fourStars.add(ItemTable.SACRIFICIAL_FRAGMENTS);
		fourStars.add(ItemTable.THE_WIDSITH);
		fourStars.add(ItemTable.FAVONIUS_CODEX);
		fourStars.add(ItemTable.FAVONIUS_LANCE);
		fourStars.add(ItemTable.DRAGONS_BANE);
		fourStars.add(ItemTable.RAINSLASHER);
		fourStars.add(ItemTable.SACRIFICIAL_GREATSWORD);
		fourStars.add(ItemTable.THE_BELL);
		fourStars.add(ItemTable.FAVONIUS_GREATSWORD);
		fourStars.add(ItemTable.LIONS_ROAR);
		fourStars.add(ItemTable.SACRIFICIAL_SWORD);
		fourStars.add(ItemTable.THE_FLUTE);
		fourStars.add(ItemTable.FAVONIUS_SWORD);
		
		//Add 3 star weapons
		threeStars.add(ItemTable.SLINGSHOT);
		threeStars.add(ItemTable.SHARPSHOOTERS_OATH);
		threeStars.add(ItemTable.RAVEN_BOW);
		threeStars.add(ItemTable.EMERALD_ORB);
		threeStars.add(ItemTable.THRILLING_TALES_OF_DRAGON_SLAYERS);
		threeStars.add(ItemTable.MAGIC_GUIDE);
		threeStars.add(ItemTable.BLACK_TASSEL);
		threeStars.add(ItemTable.DEBATE_CLUB);
		threeStars.add(ItemTable.BLOODTAINTED_GREATSWORD);
		threeStars.add(ItemTable.FERROUS_SHADOW);
		threeStars.add(ItemTable.SKYRIDER_SWORD);
		threeStars.add(ItemTable.HARBINGER_OF_DAWN);
		threeStars.add(ItemTable.COOL_STEEL);
		
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
		fiveStars.add(ItemTable.MONA);
		fiveStars.add(ItemTable.KEQING);
		fiveStars.add(ItemTable.QIQI);
		fiveStars.add(ItemTable.JEAN);
		fiveStars.add(ItemTable.DILUC);
		
		//5 star weapons
		fiveStars.add(ItemTable.SKYWARD_ATLAS);
		fiveStars.add(ItemTable.WOLFS_GRAVESTONE);
		fiveStars.add(ItemTable.SKYWARD_BLADE);
		fiveStars.add(ItemTable.SKYWARD_HARP);
		fiveStars.add(ItemTable.SKYWARD_SPINE);
		fiveStars.add(ItemTable.SKYWARD_PRIDE);
		fiveStars.add(ItemTable.AQUILA_FAVONIA);
		fiveStars.add(ItemTable.AMOS_BOW);
		fiveStars.add(ItemTable.LOST_PRAYER_TO_THE_SACRED_WINDS);
		fiveStars.add(ItemTable.PRIMORDIAL_JADE_WINGED_SPEAR);
		
		//4 stars
		//4 star characters
		fourStars.add(ItemTable.SUCROSE);
		fourStars.add(ItemTable.FISCHL);
		fourStars.add(ItemTable.BENNETT);
		fourStars.add(ItemTable.XINYAN);
		fourStars.add(ItemTable.DIONA);
		fourStars.add(ItemTable.CHONGYUN);
		fourStars.add(ItemTable.NOELLE);
		fourStars.add(ItemTable.NINGGUANG);
		fourStars.add(ItemTable.XINGQUI);
		fourStars.add(ItemTable.BEIDOU);
		fourStars.add(ItemTable.XIANGLING);
		fourStars.add(ItemTable.RAZOR);
		fourStars.add(ItemTable.BARBARA);
		fourStars.add(ItemTable.AMBER);
		fourStars.add(ItemTable.KAEYA);
		fourStars.add(ItemTable.LISA);
		
		//4 star weapons
		fourStars.add(ItemTable.SACRIFICIAL_BOW);
		fourStars.add(ItemTable.THE_STRINGLESS);
		fourStars.add(ItemTable.FAVONIUS_WARBOW);
		fourStars.add(ItemTable.EYE_OF_PERCEPTION);
		fourStars.add(ItemTable.SACRIFICIAL_FRAGMENTS);
		fourStars.add(ItemTable.THE_WIDSITH);
		fourStars.add(ItemTable.FAVONIUS_CODEX);
		fourStars.add(ItemTable.FAVONIUS_LANCE);
		fourStars.add(ItemTable.DRAGONS_BANE);
		fourStars.add(ItemTable.RAINSLASHER);
		fourStars.add(ItemTable.SACRIFICIAL_GREATSWORD);
		fourStars.add(ItemTable.THE_BELL);
		fourStars.add(ItemTable.FAVONIUS_GREATSWORD);
		fourStars.add(ItemTable.LIONS_ROAR);
		fourStars.add(ItemTable.SACRIFICIAL_SWORD);
		fourStars.add(ItemTable.THE_FLUTE);
		fourStars.add(ItemTable.FAVONIUS_SWORD);
		fourStars.add(ItemTable.RUST);
		
		//Add 3 star weapons
		threeStars.add(ItemTable.SLINGSHOT);
		threeStars.add(ItemTable.SHARPSHOOTERS_OATH);
		threeStars.add(ItemTable.RAVEN_BOW);
		threeStars.add(ItemTable.EMERALD_ORB);
		threeStars.add(ItemTable.THRILLING_TALES_OF_DRAGON_SLAYERS);
		threeStars.add(ItemTable.MAGIC_GUIDE);
		threeStars.add(ItemTable.BLACK_TASSEL);
		threeStars.add(ItemTable.DEBATE_CLUB);
		threeStars.add(ItemTable.BLOODTAINTED_GREATSWORD);
		threeStars.add(ItemTable.FERROUS_SHADOW);
		threeStars.add(ItemTable.SKYRIDER_SWORD);
		threeStars.add(ItemTable.HARBINGER_OF_DAWN);
		threeStars.add(ItemTable.COOL_STEEL);
		
		this.standardBanner = new StandardBanner(threeStars, fourStars, fiveStars);
		
		
	}
}