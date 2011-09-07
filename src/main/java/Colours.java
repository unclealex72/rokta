import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;


public class Colours {

	public static void main(String[] args) throws Exception {
		Pattern colourPattern = Pattern.compile("([a-zA-Z]+)(?:\\s+[0-9A-F][0-9A-F]){3}(?:\\s+([0-9]+))(?:\\s+([0-9]+))(?:\\s+([0-9]+))");
		BufferedReader reader = new BufferedReader(new InputStreamReader(Colours.class.getResourceAsStream("colours.txt")));
		String line;
		String colourGroup = null;
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if (!line.isEmpty()) {
				Matcher matcher = colourPattern.matcher(line);
				if (matcher.matches()) {
					String htmlName = matcher.group(1);
					int red = Integer.parseInt(matcher.group(2));
					int green = Integer.parseInt(matcher.group(3));
					int blue = Integer.parseInt(matcher.group(4));
					List<String> htmlNameParts = splitByCapital(htmlName);
					String enumName = Joiner.on('_').join(htmlNameParts).toUpperCase();
			    int brightness =
			    		(int) Math.sqrt(.241 * (double) red * (double) red + .691 * (double) green * (double) green + .068 * (double) blue * (double) blue);
			    boolean dark = brightness < 130;
			    System.out.format(
			    		"%s(Group.%s, \"%s\", %d, %d, %d, %b, \"%s\"),%n", 
			    		enumName, colourGroup, htmlName, red, green, blue, dark, Joiner.on("\", \"").join(htmlNameParts).toLowerCase());
				}
				else {
					colourGroup = line.toUpperCase().replaceAll("[^A-Z]", "");
				}
			}
		}
	}

	public static List<String> splitByCapital(String htmlName) {
		List<String> words = Lists.newArrayList();
		StringBuilder builder = null;
		for (char c : htmlName.toCharArray()) {
			if (Character.isUpperCase(c)) {
				if (builder != null) {
					words.add(builder.toString());
				}
				builder = new StringBuilder();
			}
			builder.append(c);
		}
		String lastWord = builder.toString();
		if (!lastWord.isEmpty()) {
			words.add(lastWord);
		}
		return words;
	}

}
