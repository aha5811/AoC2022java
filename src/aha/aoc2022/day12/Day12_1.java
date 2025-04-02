package aha.aoc2022.day12;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import aha.aoc2022.Utils;

public class Day12_1 {

	static String input = "aha/aoc2022/day12/input";
	// static String input = "aha/aoc2022/day12/input.test";
	
	static int g = 26;
	
	public static void main(final String[] args) {

		final List<String> lines = Utils.readLines(input);
		final int maxX = lines.size(),
				maxY = lines.get(0).length();
		final int[][] map = new int[maxX][maxY];
		int gx = -1, gy = -1;
		{
			int x = 0;
			for (final String line : lines) {
				for (int y = 0; y < maxY; y++) {
					final char c = line.charAt(y);
					int n = -1;
					if (c == 'S')
						n = -1;
					else if (c == 'E') {
						gx = x;
						gy = y;
						n = g;
					} else
						n = c - 'a';
					map[x][y] = n;
				}
				x++;
			}
		}

		final double[][] distMap = new double[maxX][maxY];
		for (int x = 0; x < maxX; x++)
			for (int y = 0; y < maxY; y++)
				distMap[x][y] = Math.sqrt(Math.pow(gx - x, 2) + Math.pow(gy - y, 2));

		final Day12_1 d = new Day12_1(maxX, maxY, map, distMap);
		
		final List<Point> shortest = d.findShortest();
		System.out.println(shortest);
		System.out.println(shortest.size());

	}

	// List<Point> best = null;
	// int bestLength = Integer.MAX_VALUE;
	final int maxX, maxY;
	final int[][] map;
	final double[][] distMap;
	
	public Day12_1(final int maxX, final int maxY, final int[][] map, final double[][] distMap) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.map = map;
		this.distMap = distMap;
	}
	
	private List<Point> findShortest() {
		return findShortest(new LinkedList<>(), new Point(0, 0));
	}

	private int m(final Point p) {
		return this.map[p.x][p.y];
	}
	
	private double d(final Point p) {
		return this.distMap[p.x][p.y];
	}

	private List<Point> findShortest(final List<Point> path, final Point p) {
		final List<Point> newPath = new LinkedList<>(path);
		newPath.add(0, p);
		if (m(p) == g) {
			System.out.println(newPath.size());
			return newPath;
		}
		else {
			final List<Point> neighbours = getNeighbours(newPath);
			List<Point> ret = null;
			for (final Point n : neighbours) {
				final List<Point> bestPath = findShortest(newPath, n);
				if (bestPath != null && (ret == null || bestPath.size() < ret.size()))
					ret = bestPath;
			}
			return ret;
		}
	}

	private List<Point> getNeighbours(final List<Point> path) {
		final List<Point> ret = new LinkedList<>();
		final Point p = path.get(0);

		final int h = m(p), x = p.x, y = p.y;
		check(ret, h, path, x + 1, y);
		check(ret, h, path, x - 1, y);
		check(ret, h, path, x, y + 1);
		check(ret, h, path, x, y - 1);

		if (!ret.isEmpty())
			Collections.sort(ret, new Comparator<Point>() {
				@Override
				public int compare(final Point p1, final Point p2) {
					return Double.compare(d(p1), d(p2));
				}
			});

		return ret;
	}

	private void check(final List<Point> ret, final int h, final List<Point> path, final int x, final int y) {
		if (x >= 0 && x < this.maxX && y >= 0 && y < this.maxY) {
			final Point p = new Point(x, y);
			if (m(p) > 0 && m(p) <= h + 1 && !path.contains(p))
				ret.add(p);
		}
	}
	
	static class Point {
		int x;
		int y;

		public Point(final int x, final int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(final Object obj) {
			if (obj == null || !(obj instanceof Point))
				return false;
			if (obj == this)
				return true;
			return ((Point) obj).x == this.x && ((Point) obj).y == this.y;
		}

		@Override
		public String toString() {
			return "P(" + this.x + "," + this.y + ")";
		}
	}
	
	
}
