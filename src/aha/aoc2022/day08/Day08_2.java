package aha.aoc2022.day08;

import java.util.List;

import aha.aoc2022.Utils;

public class Day08_2 {

	public static void main(final String[] args) {
		
		final long start0 = System.currentTimeMillis();
		
		final List<String> lines = Utils.readLines("aha/aoc2022/day08/input");
		
		final long start1 = System.currentTimeMillis();
		
		final int maxX = lines.size(),
				maxY = lines.get(0).length();

		final int[][] forest = new int[maxX][maxY];
		{
			int x = 0;
			for (final String line : lines) {
				for (int y = 0; y < line.length(); y++)
					forest[x][y] = Integer.parseInt("" + line.charAt(y));
				x++;
			}
		}

		int maxScenicScore = -1;

		final long start2 = System.currentTimeMillis();

		for (int x = 0; x < maxX; x++)
			for (int y = 0; y < maxY; y++)
				maxScenicScore = Math.max(maxScenicScore, ss(forest, x, y, maxX, maxY));

		final long end = System.currentTimeMillis();

		System.out.println(
				maxScenicScore
				+ " (in " + (end - start2) + "ms"
				+ ", parsing in " + (start1 - start0)
				+ "+" + (start2 - start1) + "ms)"
		);
	}

	private static int ss(final int[][] forest, final int x, final int y, final int maxX, final int maxY) {
		final int h = forest[x][y];
		int ss = 1;

		{ // xx < x
			int ps = 0;
			for (int xx = x - 1; xx >= 0; xx--) {
				ps++;
				if (forest[xx][y] >= h)
					break;
			}
			ss *= ps;
		}
		{ // xx > x
			int ps = 0;
			for (int xx = x + 1; xx < maxX; xx++) {
				ps++;
				if (forest[xx][y] >= h)
					break;
			}
			ss *= ps;
		}
		{ // yy < y
			int ps = 0;
			for (int yy = y - 1; yy >= 0; yy--) {
				ps++;
				if (forest[x][yy] >= h)
					break;
			}
			ss *= ps;
		}
		{ // yy > y
			int ps = 0;
			for (int yy = y + 1; yy < maxY; yy++) {
				ps++;
				if (forest[x][yy] >= h)
					break;
			}
			ss *= ps;
		}

		return ss;
	}

}
