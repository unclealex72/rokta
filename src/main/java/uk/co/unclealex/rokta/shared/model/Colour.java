package uk.co.unclealex.rokta.shared.model;

import java.util.Arrays;
import java.util.List;

public enum Colour {
	INDIAN_RED(Group.REDS, "IndianRed", 205, 92, 92, true, "indian", "red"),
	LIGHT_CORAL(Group.REDS, "LightCoral", 240, 128, 128, false, "light", "coral"),
	SALMON(Group.REDS, "Salmon", 250, 128, 114, false, "salmon"),
	DARK_SALMON(Group.REDS, "DarkSalmon", 233, 150, 122, false, "dark", "salmon"),
	RED(Group.REDS, "Red", 255, 0, 0, true, "red"),
	CRIMSON(Group.REDS, "Crimson", 220, 20, 60, true, "crimson"),
	FIRE_BRICK(Group.REDS, "FireBrick", 178, 34, 34, true, "fire", "brick"),
	DARK_RED(Group.REDS, "DarkRed", 139, 0, 0, true, "dark", "red"),
	PINK(Group.PINKS, "Pink", 255, 192, 203, false, "pink"),
	LIGHT_PINK(Group.PINKS, "LightPink", 255, 182, 193, false, "light", "pink"),
	HOT_PINK(Group.PINKS, "HotPink", 255, 105, 180, false, "hot", "pink"),
	DEEP_PINK(Group.PINKS, "DeepPink", 255, 20, 147, false, "deep", "pink"),
	MEDIUM_VIOLET_RED(Group.PINKS, "MediumVioletRed", 199, 21, 133, true, "medium", "violet", "red"),
	PALE_VIOLET_RED(Group.PINKS, "PaleVioletRed", 219, 112, 147, false, "pale", "violet", "red"),
	LIGHT_SALMON(Group.ORANGES, "LightSalmon", 255, 160, 122, false, "light", "salmon"),
	CORAL(Group.ORANGES, "Coral", 255, 127, 80, false, "coral"),
	TOMATO(Group.ORANGES, "Tomato", 255, 99, 71, false, "tomato"),
	ORANGE_RED(Group.ORANGES, "OrangeRed", 255, 69, 0, false, "orange", "red"),
	DARK_ORANGE(Group.ORANGES, "DarkOrange", 255, 140, 0, false, "dark", "orange"),
	ORANGE(Group.ORANGES, "Orange", 255, 165, 0, false, "orange"),
	GOLD(Group.YELLOWS, "Gold", 255, 215, 0, false, "gold"),
	YELLOW(Group.YELLOWS, "Yellow", 255, 255, 0, false, "yellow"),
	LIGHT_YELLOW(Group.YELLOWS, "LightYellow", 255, 255, 224, false, "light", "yellow"),
	LEMON_CHIFFON(Group.YELLOWS, "LemonChiffon", 255, 250, 205, false, "lemon", "chiffon"),
	LIGHT_GOLDENROD_YELLOW(Group.YELLOWS, "LightGoldenrodYellow", 250, 250, 210, false, "light", "goldenrod", "yellow"),
	PAPAYA_WHIP(Group.YELLOWS, "PapayaWhip", 255, 239, 213, false, "papaya", "whip"),
	MOCCASIN(Group.YELLOWS, "Moccasin", 255, 228, 181, false, "moccasin"),
	PEACH_PUFF(Group.YELLOWS, "PeachPuff", 255, 218, 185, false, "peach", "puff"),
	PALE_GOLDENROD(Group.YELLOWS, "PaleGoldenrod", 238, 232, 170, false, "pale", "goldenrod"),
	KHAKI(Group.YELLOWS, "Khaki", 240, 230, 140, false, "khaki"),
	DARK_KHAKI(Group.YELLOWS, "DarkKhaki", 189, 183, 107, false, "dark", "khaki"),
	LAVENDER(Group.PURPLES, "Lavender", 230, 230, 250, false, "lavender"),
	THISTLE(Group.PURPLES, "Thistle", 216, 191, 216, false, "thistle"),
	PLUM(Group.PURPLES, "Plum", 221, 160, 221, false, "plum"),
	VIOLET(Group.PURPLES, "Violet", 238, 130, 238, false, "violet"),
	ORCHID(Group.PURPLES, "Orchid", 218, 112, 214, false, "orchid"),
	FUCHSIA(Group.PURPLES, "Fuchsia", 255, 0, 255, false, "fuchsia"),
	MAGENTA(Group.PURPLES, "Magenta", 255, 0, 255, false, "magenta"),
	MEDIUM_ORCHID(Group.PURPLES, "MediumOrchid", 186, 85, 211, true, "medium", "orchid"),
	MEDIUM_PURPLE(Group.PURPLES, "MediumPurple", 147, 112, 219, false, "medium", "purple"),
	BLUE_VIOLET(Group.PURPLES, "BlueViolet", 138, 43, 226, true, "blue", "violet"),
	DARK_VIOLET(Group.PURPLES, "DarkViolet", 148, 0, 211, true, "dark", "violet"),
	DARK_ORCHID(Group.PURPLES, "DarkOrchid", 153, 50, 204, true, "dark", "orchid"),
	DARK_MAGENTA(Group.PURPLES, "DarkMagenta", 139, 0, 139, true, "dark", "magenta"),
	PURPLE(Group.PURPLES, "Purple", 128, 0, 128, true, "purple"),
	INDIGO(Group.PURPLES, "Indigo", 75, 0, 130, true, "indigo"),
	DARK_SLATE_BLUE(Group.PURPLES, "DarkSlateBlue", 72, 61, 139, true, "dark", "slate", "blue"),
	SLATE_BLUE(Group.PURPLES, "SlateBlue", 106, 90, 205, true, "slate", "blue"),
	MEDIUM_SLATE_BLUE(Group.PURPLES, "MediumSlateBlue", 123, 104, 238, true, "medium", "slate", "blue"),
	GREEN_YELLOW(Group.GREENS, "GreenYellow", 173, 255, 47, false, "green", "yellow"),
	CHARTREUSE(Group.GREENS, "Chartreuse", 127, 255, 0, false, "chartreuse"),
	LAWN_GREEN(Group.GREENS, "LawnGreen", 124, 252, 0, false, "lawn", "green"),
	LIME(Group.GREENS, "Lime", 0, 255, 0, false, "lime"),
	LIME_GREEN(Group.GREENS, "LimeGreen", 50, 205, 50, false, "lime", "green"),
	PALE_GREEN(Group.GREENS, "PaleGreen", 152, 251, 152, false, "pale", "green"),
	LIGHT_GREEN(Group.GREENS, "LightGreen", 144, 238, 144, false, "light", "green"),
	MEDIUM_SPRING_GREEN(Group.GREENS, "MediumSpringGreen", 0, 250, 154, false, "medium", "spring", "green"),
	SPRING_GREEN(Group.GREENS, "SpringGreen", 0, 255, 127, false, "spring", "green"),
	MEDIUM_SEA_GREEN(Group.GREENS, "MediumSeaGreen", 60, 179, 113, false, "medium", "sea", "green"),
	SEA_GREEN(Group.GREENS, "SeaGreen", 46, 139, 87, true, "sea", "green"),
	FOREST_GREEN(Group.GREENS, "ForestGreen", 34, 139, 34, true, "forest", "green"),
	GREEN(Group.GREENS, "Green", 0, 128, 0, true, "green"),
	DARK_GREEN(Group.GREENS, "DarkGreen", 0, 100, 0, true, "dark", "green"),
	YELLOW_GREEN(Group.GREENS, "YellowGreen", 154, 205, 50, false, "yellow", "green"),
	OLIVE_DRAB(Group.GREENS, "OliveDrab", 107, 142, 35, true, "olive", "drab"),
	OLIVE(Group.GREENS, "Olive", 128, 128, 0, true, "olive"),
	DARK_OLIVE_GREEN(Group.GREENS, "DarkOliveGreen", 85, 107, 47, true, "dark", "olive", "green"),
	MEDIUM_AQUAMARINE(Group.GREENS, "MediumAquamarine", 102, 205, 170, false, "medium", "aquamarine"),
	DARK_SEA_GREEN(Group.GREENS, "DarkSeaGreen", 143, 188, 143, false, "dark", "sea", "green"),
	LIGHT_SEA_GREEN(Group.GREENS, "LightSeaGreen", 32, 178, 170, false, "light", "sea", "green"),
	DARK_CYAN(Group.GREENS, "DarkCyan", 0, 139, 139, true, "dark", "cyan"),
	TEAL(Group.GREENS, "Teal", 0, 128, 128, true, "teal"),
	AQUA(Group.BLUES, "Aqua", 0, 255, 255, false, "aqua"),
	CYAN(Group.BLUES, "Cyan", 0, 255, 255, false, "cyan"),
	LIGHT_CYAN(Group.BLUES, "LightCyan", 224, 255, 255, false, "light", "cyan"),
	PALE_TURQUOISE(Group.BLUES, "PaleTurquoise", 175, 238, 238, false, "pale", "turquoise"),
	AQUAMARINE(Group.BLUES, "Aquamarine", 127, 255, 212, false, "aquamarine"),
	TURQUOISE(Group.BLUES, "Turquoise", 64, 224, 208, false, "turquoise"),
	MEDIUM_TURQUOISE(Group.BLUES, "MediumTurquoise", 72, 209, 204, false, "medium", "turquoise"),
	DARK_TURQUOISE(Group.BLUES, "DarkTurquoise", 0, 206, 209, false, "dark", "turquoise"),
	CADET_BLUE(Group.BLUES, "CadetBlue", 95, 158, 160, false, "cadet", "blue"),
	STEEL_BLUE(Group.BLUES, "SteelBlue", 70, 130, 180, true, "steel", "blue"),
	LIGHT_STEEL_BLUE(Group.BLUES, "LightSteelBlue", 176, 196, 222, false, "light", "steel", "blue"),
	POWDER_BLUE(Group.BLUES, "PowderBlue", 176, 224, 230, false, "powder", "blue"),
	LIGHT_BLUE(Group.BLUES, "LightBlue", 173, 216, 230, false, "light", "blue"),
	SKY_BLUE(Group.BLUES, "SkyBlue", 135, 206, 235, false, "sky", "blue"),
	LIGHT_SKY_BLUE(Group.BLUES, "LightSkyBlue", 135, 206, 250, false, "light", "sky", "blue"),
	DEEP_SKY_BLUE(Group.BLUES, "DeepSkyBlue", 0, 191, 255, false, "deep", "sky", "blue"),
	DODGER_BLUE(Group.BLUES, "DodgerBlue", 30, 144, 255, false, "dodger", "blue"),
	CORNFLOWER_BLUE(Group.BLUES, "CornflowerBlue", 100, 149, 237, false, "cornflower", "blue"),
	ROYAL_BLUE(Group.BLUES, "RoyalBlue", 65, 105, 225, true, "royal", "blue"),
	BLUE(Group.BLUES, "Blue", 0, 0, 255, true, "blue"),
	MEDIUM_BLUE(Group.BLUES, "MediumBlue", 0, 0, 205, true, "medium", "blue"),
	DARK_BLUE(Group.BLUES, "DarkBlue", 0, 0, 139, true, "dark", "blue"),
	NAVY(Group.BLUES, "Navy", 0, 0, 128, true, "navy"),
	MIDNIGHT_BLUE(Group.BLUES, "MidnightBlue", 25, 25, 112, true, "midnight", "blue"),
	CORNSILK(Group.BROWNS, "Cornsilk", 255, 248, 220, false, "cornsilk"),
	BLANCHED_ALMOND(Group.BROWNS, "BlanchedAlmond", 255, 235, 205, false, "blanched", "almond"),
	BISQUE(Group.BROWNS, "Bisque", 255, 228, 196, false, "bisque"),
	NAVAJO_WHITE(Group.BROWNS, "NavajoWhite", 255, 222, 173, false, "navajo", "white"),
	WHEAT(Group.BROWNS, "Wheat", 245, 222, 179, false, "wheat"),
	BURLY_WOOD(Group.BROWNS, "BurlyWood", 222, 184, 135, false, "burly", "wood"),
	TAN(Group.BROWNS, "Tan", 210, 180, 140, false, "tan"),
	ROSY_BROWN(Group.BROWNS, "RosyBrown", 188, 143, 143, false, "rosy", "brown"),
	SANDY_BROWN(Group.BROWNS, "SandyBrown", 244, 164, 96, false, "sandy", "brown"),
	GOLDENROD(Group.BROWNS, "Goldenrod", 218, 165, 32, false, "goldenrod"),
	DARK_GOLDENROD(Group.BROWNS, "DarkGoldenrod", 184, 134, 11, false, "dark", "goldenrod"),
	PERU(Group.BROWNS, "Peru", 205, 133, 63, false, "peru"),
	CHOCOLATE(Group.BROWNS, "Chocolate", 210, 105, 30, false, "chocolate"),
	SADDLE_BROWN(Group.BROWNS, "SaddleBrown", 139, 69, 19, true, "saddle", "brown"),
	SIENNA(Group.BROWNS, "Sienna", 160, 82, 45, true, "sienna"),
	BROWN(Group.BROWNS, "Brown", 165, 42, 42, true, "brown"),
	MAROON(Group.BROWNS, "Maroon", 128, 0, 0, true, "maroon"),
	WHITE(Group.WHITES, "White", 255, 255, 255, false, "white"),
	SNOW(Group.WHITES, "Snow", 255, 250, 250, false, "snow"),
	HONEYDEW(Group.WHITES, "Honeydew", 240, 255, 240, false, "honeydew"),
	MINT_CREAM(Group.WHITES, "MintCream", 245, 255, 250, false, "mint", "cream"),
	AZURE(Group.WHITES, "Azure", 240, 255, 255, false, "azure"),
	ALICE_BLUE(Group.WHITES, "AliceBlue", 240, 248, 255, false, "alice", "blue"),
	GHOST_WHITE(Group.WHITES, "GhostWhite", 248, 248, 255, false, "ghost", "white"),
	WHITE_SMOKE(Group.WHITES, "WhiteSmoke", 245, 245, 245, false, "white", "smoke"),
	SEASHELL(Group.WHITES, "Seashell", 255, 245, 238, false, "seashell"),
	BEIGE(Group.WHITES, "Beige", 245, 245, 220, false, "beige"),
	OLD_LACE(Group.WHITES, "OldLace", 253, 245, 230, false, "old", "lace"),
	FLORAL_WHITE(Group.WHITES, "FloralWhite", 255, 250, 240, false, "floral", "white"),
	IVORY(Group.WHITES, "Ivory", 255, 255, 240, false, "ivory"),
	ANTIQUE_WHITE(Group.WHITES, "AntiqueWhite", 250, 235, 215, false, "antique", "white"),
	LINEN(Group.WHITES, "Linen", 250, 240, 230, false, "linen"),
	LAVENDER_BLUSH(Group.WHITES, "LavenderBlush", 255, 240, 245, false, "lavender", "blush"),
	MISTY_ROSE(Group.WHITES, "MistyRose", 255, 228, 225, false, "misty", "rose"),
	GAINSBORO(Group.GREYS, "Gainsboro", 220, 220, 220, false, "gainsboro"),
	LIGHT_GREY(Group.GREYS, "LightGrey", 211, 211, 211, false, "light", "grey"),
	SILVER(Group.GREYS, "Silver", 192, 192, 192, false, "silver"),
	DARK_GRAY(Group.GREYS, "DarkGray", 169, 169, 169, false, "dark", "gray"),
	GRAY(Group.GREYS, "Gray", 128, 128, 128, true, "gray"),
	DIM_GRAY(Group.GREYS, "DimGray", 105, 105, 105, true, "dim", "gray"),
	LIGHT_SLATE_GRAY(Group.GREYS, "LightSlateGray", 119, 136, 153, false, "light", "slate", "gray"),
	SLATE_GRAY(Group.GREYS, "SlateGray", 112, 128, 144, true, "slate", "gray"),
	DARK_SLATE_GRAY(Group.GREYS, "DarkSlateGray", 47, 79, 79, true, "dark", "slate", "gray"),
	BLACK(Group.GREYS, "Black", 0, 0, 0, true, "black");

	public enum Group {
		REDS("Reds"),
		PINKS("Pinks"),
		ORANGES("Oranges"),
		YELLOWS("Yellows"),
		PURPLES("Purples"),
		GREENS("Greens"),
		BLUES("Blues"),
		BROWNS("Browns"),
		WHITES("Whites"),
		GREYS("Greys");
		
		private final String i_description;

		private Group(String description) {
			i_description = description;
		}
		
		public String getDescription() {
			return i_description;
		}
	}
	
	private final Group i_group;
	private final String i_htmlName;
	private final int i_red;
	private final int i_green;
	private final int i_blue;
	private final boolean i_dark;
	private final List<String> i_descriptiveWords;
	
	private Colour(Group group, String htmlName, int red, int green, int blue, boolean dark,
			String... descriptiveWords) {
		i_group = group;
		i_htmlName = htmlName;
		i_red = red;
		i_green = green;
		i_blue = blue;
		i_dark = dark;
		i_descriptiveWords = Arrays.asList(descriptiveWords);
	}

	public Group getGroup() {
		return i_group;
	}

	public String getHtmlName() {
		return i_htmlName;
	}

	public int getRed() {
		return i_red;
	}

	public int getGreen() {
		return i_green;
	}

	public int getBlue() {
		return i_blue;
	}

	public boolean isDark() {
		return i_dark;
	}

	public List<String> getDescriptiveWords() {
		return i_descriptiveWords;
	}
	
	
}
