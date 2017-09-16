package com.ycchen.algorithms.graphs;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.LinkedHashSet;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.io.BufferedInputStream;
import java.util.Locale;
import java.io.IOException;
import java.net.MalformedURLException;


import com.ycchen.algorithms.fundamentals.ArrayQueue;

public class WebCrawler {

	private static class URLReader {
		// assume Unicode UTF-8 encoding
	    private static final String CHARSET_NAME = "UTF-8";
	    
	    // assume language = English, country = US for consistency with System.out.
	    private static final Locale LOCALE = Locale.US;
	    
	    // the default token separator; we maintain the invariant that this value 
	    // is held by the scanner's delimiter between calls
	    private static final Pattern WHITESPACE_PATTERN
	        = Pattern.compile("\\p{javaWhitespace}+");

	    // makes whitespace characters significant 
	    private static final Pattern EMPTY_PATTERN
	        = Pattern.compile("");

	    // used to read the entire input. source:
	    // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
	    private static final Pattern EVERYTHING_PATTERN
	        = Pattern.compile("\\A");

		private Scanner scanner;
		
		public URLReader(String u) {       
	        if (u == null) throw new IllegalArgumentException("url argument is null");
	        
	        URL url = null;
	        try {
		        url = new URL(u);
		    } catch (MalformedURLException e) {
		        e.printStackTrace();
		    }
	        
	        try {
	            URLConnection site = url.openConnection();
	            InputStream is     = site.getInputStream();
	            scanner            = new Scanner(new BufferedInputStream(is), CHARSET_NAME);
	            scanner.useLocale(LOCALE);
	        }
	        catch (IOException ioe) {
	            throw new IllegalArgumentException("Could not open " + url, ioe);
	        }
		}
		
	    public String readAll() {
	        if (!scanner.hasNextLine())
	            return "";

	        String result = scanner.useDelimiter(EVERYTHING_PATTERN).next();
	        // not that important to reset delimeter, since now scanner is empty
	        scanner.useDelimiter(WHITESPACE_PATTERN); // but let's do it anyway
	        return result;
	    }
		
	}
	public static void main(String[] args) {
		System.setProperty("sun.net.client.defaultConnectTimeout", "500");
		System.setProperty("sun.net.client.defaultReadTimeout", "1000");
		
		String s = "https://www.ntu.edu.tw";
		
		ArrayQueue<String> queue = new ArrayQueue<String>();
		queue.enqueue(s);
		ArrayQueue<Integer> queue_depth = new ArrayQueue<Integer>();
		queue_depth.enqueue(0);
		
		LinkedHashSet<String> marked = new LinkedHashSet<String>();
		marked.add(s);
		
		while (!queue.isEmpty()) {
			String v = queue.dequeue();
			int depth = queue_depth.dequeue();
			System.out.println("["+depth+"] "+v);
			
			String input = null;
			
			try {
				URLReader in = new WebCrawler.URLReader(v);
				input = in.readAll();
			}catch(IllegalArgumentException e){
				System.out.println("[could not open "+v+" ]");
				continue;
			}
			
			String regexp = "http://(\\w+\\.)+(\\w+)";
			Pattern pattern = Pattern.compile(regexp);
			Matcher matcher = pattern.matcher(input);
			
			depth++;
			while (matcher.find()) {
				String w = matcher.group();
				if (!marked.contains(w)){
					queue.enqueue(w);
					queue_depth.enqueue(depth);
					marked.add(w);
				}
			}
			depth++;
		
		}
	}

}
