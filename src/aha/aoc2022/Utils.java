package aha.aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Utils {

	public static List<String> readLines(final String string) {
		final List<String> lines = new LinkedList<>();
		
		BufferedReader br = null;
		try {
			final InputStream is = Utils.class.getClassLoader()
					.getResourceAsStream(string);
			br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null)
				lines.add(line);
			br.close();
		} catch (final IOException e) {
			e.printStackTrace();
		} finally { try { br.close(); } catch (final Exception ex) {} }

		System.out.println(" ... read " + lines.size() + " lines from " + string);
		
		return lines;
	}
	
}
