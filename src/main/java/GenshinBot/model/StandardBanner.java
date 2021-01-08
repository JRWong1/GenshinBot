package GenshinBot.model;

import java.util.List;
import java.util.Random;

public class StandardBanner extends Banner{
	public StandardBanner(List<Item> threeStars, List<Item> fourStars, List<Item> fiveStars) {
		super(threeStars, fourStars, fiveStars);
	}
	@Override
	public Item rollOne() {
		Item result = null;
		Random rand = new Random();
		double random = this.randomNumber();
		//Got a 5 star
		if(random <= this.calcPercentChance(this.pityFiveStar)) {
			int index = rand.nextInt(this.fiveStars.size());
			result = fiveStars.get(index);
			//Reset pity and stuff
			this.pityFiveStar = 0;
			this.pityFourStar = 0;
			this.promoFiveStar = true;
		}
		//Got a 4 star
		else if(random <= Banner.CHAR_BANNER_FOUR_STAR_PERCENT || this.pityFourStar == 10) {
			int index = rand.nextInt(this.fourStars.size());
			result = fourStars.get(index);
			this.pityFourStar = 0;
			this.promoFourStar = true;
		}
		else {
			//Got a 3 star sad
			int index = rand.nextInt(this.threeStars.size());
			result = threeStars.get(index);
		}
		
		//After rolling, increment pity, change promo pity if needed
		this.pityFiveStar+=1;
		this.pityFourStar+=1;
		
		
		return result;
	}

}
