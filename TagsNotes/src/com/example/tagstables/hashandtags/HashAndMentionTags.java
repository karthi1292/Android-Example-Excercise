package com.example.tagstables.hashandtags;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HashAndMentionTags {
	
	private Pattern HASH_PATTERN;
	private Pattern MENTION_PATTERN;
	private Matcher hash_mat;
	private Matcher mention_mat;
	private List<String> hash_string=new ArrayList<String>();
	private List<String> mention_string=new ArrayList<String>();
	
	public HashAndMentionTags(final String str) {

		HASH_PATTERN = Pattern.compile("#(\\w+|\\W+)");
		MENTION_PATTERN =Pattern.compile("@(\\w+|\\W+)");

		hash_mat = HASH_PATTERN.matcher(str);
		mention_mat=MENTION_PATTERN.matcher(str);
		
		while(mention_mat.find()) { 
		  mention_string.add("@"+mention_mat.group(1));
		}


		while (hash_mat.find()) {
			hash_string.add("#"+hash_mat.group(1));
		}

	}
	
	public List<String> getHashTags() {
		return hash_string;
	}
	
	public List<String> getMentionTags() {
		return mention_string;
	}

  }

 


