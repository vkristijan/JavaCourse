package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class ObjectMultistackTests {
	private ObjectMultistack stack;
	
	@Before
	public void createStack(){
		stack = new ObjectMultistack();
		
		stack.push("aaa", new ValueWrapper(3));
		stack.push("aaa", new ValueWrapper(4));
		stack.push("aaa", new ValueWrapper(5));
		stack.push("aaa", new ValueWrapper(3.14));
		
		stack.push("b", new ValueWrapper(3));
		stack.push("b", new ValueWrapper(3));
	
		stack.push("c", new ValueWrapper(-1));
		stack.push("dd", new ValueWrapper(42));
		stack.push("eee", new ValueWrapper(55));
		stack.push("ffff", new ValueWrapper(314));
	}
	
	@Test
	public void testConstructorNotNull(){
		ObjectMultistack stack = new ObjectMultistack();
		assertNotNull(stack);
	}
	
	@Test
	public void testPeek(){
		assertEquals(3.14, stack.peek("aaa").getValue());
		assertEquals(3.14, stack.peek("aaa").getValue());
		assertEquals(3.14, stack.peek("aaa").getValue());
		assertEquals(3.14, stack.peek("aaa").getValue());
		
		assertEquals(3, stack.peek("b").getValue());
		assertEquals(-1, stack.peek("c").getValue());
		assertEquals(42, stack.peek("dd").getValue());
		assertEquals(55, stack.peek("eee").getValue());
		assertEquals(314, stack.peek("ffff").getValue());
	}
	
	@Test
	public void testPopAndEmpty(){
		assertEquals(3.14, stack.pop("aaa").getValue());
		assertFalse(stack.isEmpty("aaa"));
		assertEquals(5, stack.pop("aaa").getValue());
		assertFalse(stack.isEmpty("aaa"));
		assertEquals(4, stack.pop("aaa").getValue());
		assertFalse(stack.isEmpty("aaa"));
		assertEquals(3, stack.pop("aaa").getValue());
		assertTrue(stack.isEmpty("aaa"));
		
		assertEquals(3, stack.pop("b").getValue());
		assertFalse(stack.isEmpty("b"));
		assertEquals(3, stack.pop("b").getValue());
		assertTrue(stack.isEmpty("b"));
		
		assertEquals(-1, stack.pop("c").getValue());
		assertTrue(stack.isEmpty("c"));
		assertEquals(42, stack.pop("dd").getValue());
		assertTrue(stack.isEmpty("dd"));
		assertEquals(55, stack.pop("eee").getValue());
		assertTrue(stack.isEmpty("eee"));
		assertEquals(314, stack.pop("ffff").getValue());
		assertTrue(stack.isEmpty("ffff"));
	}
	
	@Test(expected = ObjectMultistackException.class)
	public void testPopOnEmpty(){
		stack.pop("empty");
	}
	
}
