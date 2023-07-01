package tests;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.Assert.*;

import org.junit.Test;

import searchTree.EmptyTree;
import searchTree.SearchTreeMap;
import searchTree.Tree;



public class StudentTests {

	
	@Test
	public void testEmptySearchTreeMap() {
		SearchTreeMap<String, Integer> s = new SearchTreeMap<String, Integer>();
		s.put("a", 1);
		s.put("b", 2);
		s.put("c", 3);
		s.put("d", 4);
		s.put("e", 5);
	}
	
	@Test
	public void testBasicSearchTreeMapStuff() {
		SearchTreeMap<Integer,String> s = new SearchTreeMap<Integer,String>();
		assertEquals(0, s.size());
		s.put(2, "Two");
		s.put(1, "One");
		s.put(3, "Three");
		s.put(1, "OneSecondTime");
		assertEquals(3, s.size());
		assertEquals("OneSecondTime", s.get(1));
		assertEquals("Two", s.get(2));
		assertEquals("Three", s.get(3));
		assertEquals(null, s.get(8));
	}
	
	@Test
	public void testBasicSearchTreeMapStuff2() {
		SearchTreeMap<Integer,String> s = new SearchTreeMap<Integer,String>();
		SearchTreeMap<Integer,String> col;
		s.put(31, "a");
		s.put(22, "b");
		s.put(13, "c");
		s.put(999, "d");
		s.put(17, "e");
		col= s.subMap(31, 999);
		List <Integer> list = col.keyList();
		for(Integer x : list) {
			System.out.println(x);
		}
		/*
		s.remove(31);
		s.remove(998);
		s.remove(31);
		s.remove(998);
		s.remove(31);
		s.remove(998);
		s.remove(31);
		s.remove(998);
		s.remove(31);
		s.remove(998);
		s.put(312, "fdsafafagi");
		s.remove(17);
		s.remove(22);
		s.remove(999);
		s.remove(13);
		s.remove(22);
		s.remove(999);
		s.remove(13);
		s.put(312, "hgjfftsfkkagivs");
		System.out.println(s.size());
		System.out.println(s.get(32));
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		List <Integer> list2 = s.keyList();
		for(Integer x : list2) {
			System.out.println(x);
		}
		*/
	}
}

