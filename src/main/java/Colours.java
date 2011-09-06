import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;


public class Colours {

	/**
  <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
  	<property name="driverClassName" value="org.postgresql.Driver"/>
  	<property name="username" value="rokta"/>
  	<property name="password" value="r0kt@"/>
  	<property name="url" value="jdbc:postgresql://hurst:5432/rokta"/>
  </bean>  
	 */
	public static void main(String[] args) throws Exception {
		Map<String, Collection<String>> coloursByGroup = Maps.newLinkedHashMap();
		Multimap<String, String> coloursByGroupMultimap = Multimaps.newListMultimap(
			coloursByGroup, 
			new Supplier<List<String>>() { public List<String> get() { return Lists.newArrayList(); }});
		Pattern colourPattern = Pattern.compile("([a-zA-Z]+)(?:\\s+[0-9A-F][0-9A-F]){3}(?:\\s+[0-9]+){3}");
		BufferedReader reader = new BufferedReader(new InputStreamReader(Colours.class.getResourceAsStream("colours.txt")));
		String line;
		String colourGroup = null;
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if (!line.isEmpty()) {
				Matcher matcher = colourPattern.matcher(line);
				if (matcher.matches()) {
					coloursByGroupMultimap.put(colourGroup, matcher.group(1).toLowerCase());
				}
				else {
					colourGroup = line;
				}
			}
		}
		Class.forName("org.postgresql.Driver");
		Connection connection = DriverManager.getConnection("jdbc:postgresql://hurst:5432/rokta", "rokta", "r0kt@");
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("select * from colour");
		while (rs.next()) {
			final String htmlName = rs.getString("htmlname").toLowerCase();
			Predicate<Entry<String, Collection<String>>> predicate = new Predicate<Entry<String, Collection<String>>>() {
				@Override
				public boolean apply(Entry<String, Collection<String>> entry) {
					return entry.getValue().contains(htmlName);
				}
			};
			Entry<String, Collection<String>> foundColourGroup = Iterables.find(coloursByGroup.entrySet(), predicate, null);
			if (foundColourGroup == null) {
				System.out.println(htmlName + " not found!");
			}
			else {
				int idx = Iterables.indexOf(foundColourGroup.getValue(), Predicates.equalTo(htmlName));
				System.out.println(htmlName + ": " + foundColourGroup.getKey() + " " + idx);
			}
		}
		rs.close();
		stmt.close();
		connection.close();
	}

}
