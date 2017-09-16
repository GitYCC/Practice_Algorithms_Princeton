package com.ycchen.algorithms.strings;

public class LongestRepeatedSubstring {
	
	private LongestRepeatedSubstring () {}
	
	public static String lrs(String text) {
		int n = text.length();
		SuffixArray sa = new SuffixArray(text);
		String lrs = "";
		for (int i=1; i<n; i++) {
			int len = sa.lcp(i);
			if (len > lrs.length()) {
				lrs = text.substring(sa.index(i), sa.index(i)+len);
			}
		}
		return lrs;
	}
	
	public static void main(String[] args) {
		String text = "Call me Ishmael. Some years ago- never mind how long precisely-\nhaving little or no money in my purse, and nothing particular to\ninterest me on shore, I thought I would sail about a little and see\nthe watery part of the world. It is a way I have of driving off the\nspleen and regulating the circulation. Whenever I find myself growing\ngrim about the mouth; whenever it is a damp, drizzly November in my\nsoul; whenever I find myself involuntarily pausing before coffin\nwarehouses, and bringing up the rear of every funeral I meet; and\nespecially whenever my hypos get such an upper hand of me, that it\nrequires a strong moral principle to prevent me from deliberately\nstepping into the street, and methodically knocking people's hats\noff- then, I account it high time to get to sea as soon as I\ncan. This is my substitute for pistol and ball. With a philosophical\nflourish Cato throws himself upon his sword; I quietly take to the\nship. There is nothing surprising in this. If they but knew it,\nalmost all men in their degree, some time or other, cherish very\nnearly the same feelings towards the ocean with me.\n\nThere now is your insular city of the Manhattoes, belted round by\nwharves as Indian isles by coral reefs- commerce surrounds it with\nher surf. Right and left, the streets take you waterward. Its extreme\ndowntown is the battery, where that noble mole is washed by waves,\nand cooled by breezes, which a few hours previous were out of sight\nof land.  Look at the crowds of water-gazers there.\n\nCircumambulate the city of a dreamy Sabbath afternoon. Go from\nCorlears Hook to Coenties Slip, and from thence, by Whitehall,\nnorthward. What do you see?- Posted like silent sentinels all around\nthe town, stand thousands upon thousands of mortal men fixed in ocean\nreveries. Some leaning against the spiles; some seated upon the\npier-heads; some looking over the bulwarks of ships from China; some\nhigh aloft in the rigging, as if striving to get a still better\nseaward peep. But these are all landsmen; of week days pent up in\nlath and plaster- tied to counters, nailed to benches, clinched to\ndesks. How then is this? Are the green fields gone? What do they\nhere?\n\nBut look! here come more crowds, pacing straight for the water, and\nseemingly bound for a dive. ";
		String lrs = LongestRepeatedSubstring.lrs(text);
		System.out.println(lrs);
	}

}
