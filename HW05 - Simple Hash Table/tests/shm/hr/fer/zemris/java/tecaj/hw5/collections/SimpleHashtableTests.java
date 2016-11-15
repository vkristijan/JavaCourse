package hr.fer.zemris.java.tecaj.hw5.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable.TableEntry;

@SuppressWarnings("javadoc")
public class SimpleHashtableTests {
	private SimpleHashtable<String, String> table;
	private SimpleHashtable<Integer, String> populatedTable;

	@Before
	public void setUp() {
		table = new SimpleHashtable<>();
		populatedTable = new SimpleHashtable<>();

		populatedTable.put(1, "Lorem");
		populatedTable.put(2, "ipsum");
		populatedTable.put(3, "dolor");
		populatedTable.put(4, "sit");
		populatedTable.put(5, "amet");
		populatedTable.put(6, "Lorem");
		populatedTable.put(7, "text");
		populatedTable.put(8, "sample");
		populatedTable.put(9, "large sample text");
		populatedTable.put(0, "\"escaped\" text");
	}

	// ----------------------------------------------------------//
	//                        T E S T S                          //
	// ----------------------------------------------------------//

	@Test
	public void testDefaultConstrutcor() {
		SimpleHashtable<String, String> table;

		table = new SimpleHashtable<>();
		assertNotNull(table);
	}

	@Test
	public void testConstrutcor() {
		SimpleHashtable<String, String> table;

		table = new SimpleHashtable<>(10);
		assertNotNull(table);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeConstrutcor() {
		@SuppressWarnings("unused")
		SimpleHashtable<String, String> table;

		table = new SimpleHashtable<>(-5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPutNullKey() {
		table.put(null, "value");
	}

	@Test
	public void testPut() {
		table.put("key", "value");
		assertEquals(1, table.size());

		table.put("key2", "value2");
		assertEquals(2, table.size());

		table.put("3", "another Value");
		assertEquals(3, table.size());

		table.put("random key", "random value");
		assertEquals(4, table.size());

		table.put("random key", "updated value");
		assertEquals(4, table.size());

		table.put("key", "first value");
		assertEquals(4, table.size());
	}

	@Test
	public void testGetNullKey() {
		assertNull(populatedTable.get(null));
	}

	@Test
	public void testGet() {
		assertEquals("Lorem", populatedTable.get(1));
		assertEquals("ipsum", populatedTable.get(2));
		assertEquals("dolor", populatedTable.get(3));
		assertEquals("sit", populatedTable.get(4));
		assertEquals("amet", populatedTable.get(5));
		assertEquals("Lorem", populatedTable.get(6));
		assertEquals("text", populatedTable.get(7));
		assertEquals("sample", populatedTable.get(8));
		assertEquals("large sample text", populatedTable.get(9));
		assertEquals("\"escaped\" text", populatedTable.get(0));
	}

	@Test
	public void testGetUnexistingKey() {
		assertNull(populatedTable.get(55));
		assertNull(populatedTable.get("mirko"));
		assertNull(populatedTable.get(-32));
		assertNull(populatedTable.get(148));
	}

	@Test
	public void testContainsKeyNull() {
		assertFalse(populatedTable.containsKey(null));
	}

	@Test
	public void testContainsKey() {
		assertFalse(populatedTable.containsKey(42));
		assertFalse(populatedTable.containsKey("String key"));
		assertFalse(populatedTable.containsKey(table));
		assertFalse(populatedTable.containsKey(Math.PI));

		assertTrue(populatedTable.containsKey(0));
		assertTrue(populatedTable.containsKey(1));
		assertTrue(populatedTable.containsKey(5));
		assertTrue(populatedTable.containsKey(3));
		assertTrue(populatedTable.containsKey(8));
	}

	@Test
	public void testRemove() {
		int size = populatedTable.size();

		populatedTable.remove(null);
		assertEquals(size, populatedTable.size());

		populatedTable.remove(1);
		size--;
		assertEquals(size, populatedTable.size());

		populatedTable.remove(1);
		assertEquals(size, populatedTable.size());

		populatedTable.remove("test string");
		assertEquals(size, populatedTable.size());

		populatedTable.remove(4);
		size--;
		assertEquals(size, populatedTable.size());

		populatedTable.remove(4);
		assertEquals(size, populatedTable.size());
	}

	@Test
	public void testIteratorNotNull() {
		Iterator<TableEntry<Integer, String>> iterator;
		iterator = populatedTable.iterator();

		assertNotNull(iterator);
	}

	@Test
	public void testIterator() {
		Iterator<TableEntry<Integer, String>> iterator;
		iterator = populatedTable.iterator();

		int count = 0;
		while (iterator.hasNext()) {
			TableEntry<Integer, String> element = iterator.next();
			assertNotNull(element);
			count++;
		}
		assertEquals(populatedTable.size(), count);
	}

	@Test
	public void testEmptyTableIterator() {
		Iterator<TableEntry<String, String>> iterator;
		iterator = table.iterator();

		assertNotNull(iterator);
		assertFalse(iterator.hasNext());
	}

	@Test(expected = NoSuchElementException.class)
	public void testIteratorAfterLAstElement() {
		Iterator<TableEntry<String, String>> iterator;
		iterator = table.iterator();

		iterator.next();
	}

	@Test
	public void testIteratorRemove() {
		Iterator<TableEntry<Integer, String>> iterator;
		iterator = populatedTable.iterator();

		int size = populatedTable.size();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
			size--;
			assertEquals(size, populatedTable.size());
		}
	}

	@Test(expected = IllegalStateException.class)
	public void testIteratorRemoveTwice() {
		Iterator<TableEntry<Integer, String>> iterator;
		iterator = populatedTable.iterator();

		iterator.next();
		assertEquals(10, populatedTable.size());
		iterator.remove();
		assertEquals(9, populatedTable.size());
		iterator.remove();
	}

	@Test
	public void testContainsValue() {
		assertTrue(populatedTable.containsValue("Lorem"));
		assertTrue(populatedTable.containsValue("ipsum"));
		assertTrue(populatedTable.containsValue("dolor"));
		assertTrue(populatedTable.containsValue("sit"));

		assertFalse(populatedTable.containsValue("lipsum"));
		assertFalse(populatedTable.containsValue("bla bla bla "));
		assertFalse(populatedTable.containsValue(null));
		assertFalse(populatedTable.containsValue(123.130));
	}

	@Test
	public void testEmptyToString() {
		assertEquals("[]", table.toString());
	}

	@Test
	public void testToString() {
		assertEquals(
			"[0=\"escaped\" text, 1=Lorem, 2=ipsum, 3=dolor, 4=sit, 5=amet, 6=Lorem, 7=text, 8=sample, 9=large sample text]",
			populatedTable.toString());
	}
	
	@Test
	public void testRehashing(){
		SimpleHashtable<Integer, String> table;
		table = new SimpleHashtable<>(4);
		
		table.put(1, "jedan");
		assertTrue(table.containsKey(1));
		assertTrue(table.containsValue("jedan"));
		
		table.put(5, "dva");
		assertTrue(table.containsKey(5));
		assertTrue(table.containsValue("dva"));
		assertTrue(table.containsKey(1));
		assertTrue(table.containsValue("jedan"));
		
		table.put(9, "tri");
		assertTrue(table.containsKey(9));
		assertTrue(table.containsValue("tri"));
		assertTrue(table.containsKey(5));
		assertTrue(table.containsValue("dva"));
		assertTrue(table.containsKey(1));
		assertTrue(table.containsValue("jedan"));
		
		table.put(4, "cetiri");
		assertTrue(table.containsKey(4));
		assertTrue(table.containsValue("cetiri"));
		assertTrue(table.containsKey(9));
		assertTrue(table.containsValue("tri"));
		assertTrue(table.containsKey(5));
		assertTrue(table.containsValue("dva"));
		assertTrue(table.containsKey(1));
		assertTrue(table.containsValue("jedan"));
	}
}
