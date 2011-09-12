package uk.co.unclealex.rokta.shared.model;

import java.util.Arrays;
import java.util.List;

public enum Colour {
	INDIAN_RED(Group.REDS, "IndianRed", "#CD5C5C", true, "indian", "red"),
	LIGHT_CORAL(Group.REDS, "LightCoral", "#F08080", false, "light", "coral"),
	SALMON(Group.REDS, "Salmon", "#FA8072", false, "salmon"),
	DARK_SALMON(Group.REDS, "DarkSalmon", "#E9967A", false, "dark", "salmon"),
	RED(Group.REDS, "Red", "#FF0000", true, "red"),
	CRIMSON(Group.REDS, "Crimson", "#DC143C", true, "crimson"),
	FIRE_BRICK(Group.REDS, "FireBrick", "#B22222", true, "fire", "brick"),
	DARK_RED(Group.REDS, "DarkRed", "#8B0000", true, "dark", "red"),
	PINK(Group.PINKS, "Pink", "#FFC0CB", false, "pink"),
	LIGHT_PINK(Group.PINKS, "LightPink", "#FFB6C1", false, "light", "pink"),
	HOT_PINK(Group.PINKS, "HotPink", "#FF69B4", false, "hot", "pink"),
	DEEP_PINK(Group.PINKS, "DeepPink", "#FF1493", false, "deep", "pink"),
	MEDIUM_VIOLET_RED(Group.PINKS, "MediumVioletRed", "#C71585", true, "medium", "violet", "red"),
	PALE_VIOLET_RED(Group.PINKS, "PaleVioletRed", "#DB7093", false, "pale", "violet", "red"),
	LIGHT_SALMON(Group.ORANGES, "LightSalmon", "#FFA07A", false, "light", "salmon"),
	CORAL(Group.ORANGES, "Coral", "#FF7F50", false, "coral"),
	TOMATO(Group.ORANGES, "Tomato", "#FF6347", false, "tomato"),
	ORANGE_RED(Group.ORANGES, "OrangeRed", "#FF4500", false, "orange", "red"),
	DARK_ORANGE(Group.ORANGES, "DarkOrange", "#FF8C00", false, "dark", "orange"),
	ORANGE(Group.ORANGES, "Orange", "#FFA500", false, "orange"),
	GOLD(Group.YELLOWS, "Gold", "#FFD700", false, "gold"),
	YELLOW(Group.YELLOWS, "Yellow", "#FFFF00", false, "yellow"),
	LIGHT_YELLOW(Group.YELLOWS, "LightYellow", "#FFFFE0", false, "light", "yellow"),
	LEMON_CHIFFON(Group.YELLOWS, "LemonChiffon", "#FFFACD", false, "lemon", "chiffon"),
	LIGHT_GOLDENROD_YELLOW(Group.YELLOWS, "LightGoldenrodYellow", "#FAFAD2", false, "light", "goldenrod", "yellow"),
	PAPAYA_WHIP(Group.YELLOWS, "PapayaWhip", "#FFEFD5", false, "papaya", "whip"),
	MOCCASIN(Group.YELLOWS, "Moccasin", "#FFE4B5", false, "moccasin"),
	PEACH_PUFF(Group.YELLOWS, "PeachPuff", "#FFDAB9", false, "peach", "puff"),
	PALE_GOLDENROD(Group.YELLOWS, "PaleGoldenrod", "#EEE8AA", false, "pale", "goldenrod"),
	KHAKI(Group.YELLOWS, "Khaki", "#F0E68C", false, "khaki"),
	DARK_KHAKI(Group.YELLOWS, "DarkKhaki", "#BDB76B", false, "dark", "khaki"),
	LAVENDER(Group.PURPLES, "Lavender", "#E6E6FA", false, "lavender"),
	THISTLE(Group.PURPLES, "Thistle", "#D8BFD8", false, "thistle"),
	PLUM(Group.PURPLES, "Plum", "#DDA0DD", false, "plum"),
	VIOLET(Group.PURPLES, "Violet", "#EE82EE", false, "violet"),
	ORCHID(Group.PURPLES, "Orchid", "#DA70D6", false, "orchid"),
	FUCHSIA(Group.PURPLES, "Fuchsia", "#FF00FF", false, "fuchsia"),
	MAGENTA(Group.PURPLES, "Magenta", "#FF00FF", false, "magenta"),
	MEDIUM_ORCHID(Group.PURPLES, "MediumOrchid", "#BA55D3", true, "medium", "orchid"),
	MEDIUM_PURPLE(Group.PURPLES, "MediumPurple", "#9370DB", false, "medium", "purple"),
	BLUE_VIOLET(Group.PURPLES, "BlueViolet", "#8A2BE2", true, "blue", "violet"),
	DARK_VIOLET(Group.PURPLES, "DarkViolet", "#9400D3", true, "dark", "violet"),
	DARK_ORCHID(Group.PURPLES, "DarkOrchid", "#9932CC", true, "dark", "orchid"),
	DARK_MAGENTA(Group.PURPLES, "DarkMagenta", "#8B008B", true, "dark", "magenta"),
	PURPLE(Group.PURPLES, "Purple", "#800080", true, "purple"),
	INDIGO(Group.PURPLES, "Indigo", "#4B0082", true, "indigo"),
	DARK_SLATE_BLUE(Group.PURPLES, "DarkSlateBlue", "#483D8B", true, "dark", "slate", "blue"),
	SLATE_BLUE(Group.PURPLES, "SlateBlue", "#6A5ACD", true, "slate", "blue"),
	MEDIUM_SLATE_BLUE(Group.PURPLES, "MediumSlateBlue", "#7B68EE", true, "medium", "slate", "blue"),
	GREEN_YELLOW(Group.GREENS, "GreenYellow", "#ADFF2F", false, "green", "yellow"),
	CHARTREUSE(Group.GREENS, "Chartreuse", "#7FFF00", false, "chartreuse"),
	LAWN_GREEN(Group.GREENS, "LawnGreen", "#7CFC00", false, "lawn", "green"),
	LIME(Group.GREENS, "Lime", "#00FF00", false, "lime"),
	LIME_GREEN(Group.GREENS, "LimeGreen", "#32CD32", false, "lime", "green"),
	PALE_GREEN(Group.GREENS, "PaleGreen", "#98FB98", false, "pale", "green"),
	LIGHT_GREEN(Group.GREENS, "LightGreen", "#90EE90", false, "light", "green"),
	MEDIUM_SPRING_GREEN(Group.GREENS, "MediumSpringGreen", "#00FA9A", false, "medium", "spring", "green"),
	SPRING_GREEN(Group.GREENS, "SpringGreen", "#00FF7F", false, "spring", "green"),
	MEDIUM_SEA_GREEN(Group.GREENS, "MediumSeaGreen", "#3CB371", false, "medium", "sea", "green"),
	SEA_GREEN(Group.GREENS, "SeaGreen", "#2E8B57", true, "sea", "green"),
	FOREST_GREEN(Group.GREENS, "ForestGreen", "#228B22", true, "forest", "green"),
	GREEN(Group.GREENS, "Green", "#008000", true, "green"),
	DARK_GREEN(Group.GREENS, "DarkGreen", "#006400", true, "dark", "green"),
	YELLOW_GREEN(Group.GREENS, "YellowGreen", "#9ACD32", false, "yellow", "green"),
	OLIVE_DRAB(Group.GREENS, "OliveDrab", "#6B8E23", true, "olive", "drab"),
	OLIVE(Group.GREENS, "Olive", "#808000", true, "olive"),
	DARK_OLIVE_GREEN(Group.GREENS, "DarkOliveGreen", "#556B2F", true, "dark", "olive", "green"),
	MEDIUM_AQUAMARINE(Group.GREENS, "MediumAquamarine", "#66CDAA", false, "medium", "aquamarine"),
	DARK_SEA_GREEN(Group.GREENS, "DarkSeaGreen", "#8FBC8F", false, "dark", "sea", "green"),
	LIGHT_SEA_GREEN(Group.GREENS, "LightSeaGreen", "#20B2AA", false, "light", "sea", "green"),
	DARK_CYAN(Group.GREENS, "DarkCyan", "#008B8B", true, "dark", "cyan"),
	TEAL(Group.GREENS, "Teal", "#008080", true, "teal"),
	AQUA(Group.BLUES, "Aqua", "#00FFFF", false, "aqua"),
	CYAN(Group.BLUES, "Cyan", "#00FFFF", false, "cyan"),
	LIGHT_CYAN(Group.BLUES, "LightCyan", "#E0FFFF", false, "light", "cyan"),
	PALE_TURQUOISE(Group.BLUES, "PaleTurquoise", "#AFEEEE", false, "pale", "turquoise"),
	AQUAMARINE(Group.BLUES, "Aquamarine", "#7FFFD4", false, "aquamarine"),
	TURQUOISE(Group.BLUES, "Turquoise", "#40E0D0", false, "turquoise"),
	MEDIUM_TURQUOISE(Group.BLUES, "MediumTurquoise", "#48D1CC", false, "medium", "turquoise"),
	DARK_TURQUOISE(Group.BLUES, "DarkTurquoise", "#00CED1", false, "dark", "turquoise"),
	CADET_BLUE(Group.BLUES, "CadetBlue", "#5F9EA0", false, "cadet", "blue"),
	STEEL_BLUE(Group.BLUES, "SteelBlue", "#4682B4", true, "steel", "blue"),
	LIGHT_STEEL_BLUE(Group.BLUES, "LightSteelBlue", "#B0C4DE", false, "light", "steel", "blue"),
	POWDER_BLUE(Group.BLUES, "PowderBlue", "#B0E0E6", false, "powder", "blue"),
	LIGHT_BLUE(Group.BLUES, "LightBlue", "#ADD8E6", false, "light", "blue"),
	SKY_BLUE(Group.BLUES, "SkyBlue", "#87CEEB", false, "sky", "blue"),
	LIGHT_SKY_BLUE(Group.BLUES, "LightSkyBlue", "#87CEFA", false, "light", "sky", "blue"),
	DEEP_SKY_BLUE(Group.BLUES, "DeepSkyBlue", "#00BFFF", false, "deep", "sky", "blue"),
	DODGER_BLUE(Group.BLUES, "DodgerBlue", "#1E90FF", false, "dodger", "blue"),
	CORNFLOWER_BLUE(Group.BLUES, "CornflowerBlue", "#6495ED", false, "cornflower", "blue"),
	ROYAL_BLUE(Group.BLUES, "RoyalBlue", "#4169E1", true, "royal", "blue"),
	BLUE(Group.BLUES, "Blue", "#0000FF", true, "blue"),
	MEDIUM_BLUE(Group.BLUES, "MediumBlue", "#0000CD", true, "medium", "blue"),
	DARK_BLUE(Group.BLUES, "DarkBlue", "#00008B", true, "dark", "blue"),
	NAVY(Group.BLUES, "Navy", "#000080", true, "navy"),
	MIDNIGHT_BLUE(Group.BLUES, "MidnightBlue", "#191970", true, "midnight", "blue"),
	CORNSILK(Group.BROWNS, "Cornsilk", "#FFF8DC", false, "cornsilk"),
	BLANCHED_ALMOND(Group.BROWNS, "BlanchedAlmond", "#FFEBCD", false, "blanched", "almond"),
	BISQUE(Group.BROWNS, "Bisque", "#FFE4C4", false, "bisque"),
	NAVAJO_WHITE(Group.BROWNS, "NavajoWhite", "#FFDEAD", false, "navajo", "white"),
	WHEAT(Group.BROWNS, "Wheat", "#F5DEB3", false, "wheat"),
	BURLY_WOOD(Group.BROWNS, "BurlyWood", "#DEB887", false, "burly", "wood"),
	TAN(Group.BROWNS, "Tan", "#D2B48C", false, "tan"),
	ROSY_BROWN(Group.BROWNS, "RosyBrown", "#BC8F8F", false, "rosy", "brown"),
	SANDY_BROWN(Group.BROWNS, "SandyBrown", "#F4A460", false, "sandy", "brown"),
	GOLDENROD(Group.BROWNS, "Goldenrod", "#DAA520", false, "goldenrod"),
	DARK_GOLDENROD(Group.BROWNS, "DarkGoldenrod", "#B8860B", false, "dark", "goldenrod"),
	PERU(Group.BROWNS, "Peru", "#CD853F", false, "peru"),
	CHOCOLATE(Group.BROWNS, "Chocolate", "#D2691E", false, "chocolate"),
	SADDLE_BROWN(Group.BROWNS, "SaddleBrown", "#8B4513", true, "saddle", "brown"),
	SIENNA(Group.BROWNS, "Sienna", "#A0522D", true, "sienna"),
	BROWN(Group.BROWNS, "Brown", "#A52A2A", true, "brown"),
	MAROON(Group.BROWNS, "Maroon", "#800000", true, "maroon"),
	WHITE(Group.WHITES, "White", "#FFFFFF", false, "white"),
	SNOW(Group.WHITES, "Snow", "#FFFAFA", false, "snow"),
	HONEYDEW(Group.WHITES, "Honeydew", "#F0FFF0", false, "honeydew"),
	MINT_CREAM(Group.WHITES, "MintCream", "#F5FFFA", false, "mint", "cream"),
	AZURE(Group.WHITES, "Azure", "#F0FFFF", false, "azure"),
	ALICE_BLUE(Group.WHITES, "AliceBlue", "#F0F8FF", false, "alice", "blue"),
	GHOST_WHITE(Group.WHITES, "GhostWhite", "#F8F8FF", false, "ghost", "white"),
	WHITE_SMOKE(Group.WHITES, "WhiteSmoke", "#F5F5F5", false, "white", "smoke"),
	SEASHELL(Group.WHITES, "Seashell", "#FFF5EE", false, "seashell"),
	BEIGE(Group.WHITES, "Beige", "#F5F5DC", false, "beige"),
	OLD_LACE(Group.WHITES, "OldLace", "#FDF5E6", false, "old", "lace"),
	FLORAL_WHITE(Group.WHITES, "FloralWhite", "#FFFAF0", false, "floral", "white"),
	IVORY(Group.WHITES, "Ivory", "#FFFFF0", false, "ivory"),
	ANTIQUE_WHITE(Group.WHITES, "AntiqueWhite", "#FAEBD7", false, "antique", "white"),
	LINEN(Group.WHITES, "Linen", "#FAF0E6", false, "linen"),
	LAVENDER_BLUSH(Group.WHITES, "LavenderBlush", "#FFF0F5", false, "lavender", "blush"),
	MISTY_ROSE(Group.WHITES, "MistyRose", "#FFE4E1", false, "misty", "rose"),
	GAINSBORO(Group.GREYS, "Gainsboro", "#DCDCDC", false, "gainsboro"),
	LIGHT_GREY(Group.GREYS, "LightGrey", "#D3D3D3", false, "light", "grey"),
	SILVER(Group.GREYS, "Silver", "#C0C0C0", false, "silver"),
	DARK_GRAY(Group.GREYS, "DarkGray", "#A9A9A9", false, "dark", "gray"),
	GRAY(Group.GREYS, "Gray", "#808080", true, "gray"),
	DIM_GRAY(Group.GREYS, "DimGray", "#696969", true, "dim", "gray"),
	LIGHT_SLATE_GRAY(Group.GREYS, "LightSlateGray", "#778899", false, "light", "slate", "gray"),
	SLATE_GRAY(Group.GREYS, "SlateGray", "#708090", true, "slate", "gray"),
	DARK_SLATE_GRAY(Group.GREYS, "DarkSlateGray", "#2F4F4F", true, "dark", "slate", "gray"),
	BLACK(Group.GREYS, "Black", "#000000", true, "black");

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
	private final String i_rgb;
	private final boolean i_dark;
	private final List<String> i_descriptiveWords;
	
	private Colour(Group group, String htmlName, String rgb, boolean dark,
			String... descriptiveWords) {
		i_group = group;
		i_htmlName = htmlName;
		i_rgb = rgb;
		i_dark = dark;
		i_descriptiveWords = Arrays.asList(descriptiveWords);
	}

	public Group getGroup() {
		return i_group;
	}

	public String getHtmlName() {
		return i_htmlName;
	}

	public boolean isDark() {
		return i_dark;
	}

	public String getRgb() {
		return i_rgb;
	}
	
	public List<String> getDescriptiveWords() {
		return i_descriptiveWords;
	}
	
	
}
