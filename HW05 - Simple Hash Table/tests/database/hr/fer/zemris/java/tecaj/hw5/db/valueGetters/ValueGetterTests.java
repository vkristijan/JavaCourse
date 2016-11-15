package hr.fer.zemris.java.tecaj.hw5.db.valueGetters;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
 
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

@SuppressWarnings("javadoc")
public class ValueGetterTests {
	private StudentRecord normalRecord;
	private StudentRecord secondRecord;
	
	@Before
	public void prepareTests(){
		normalRecord = new StudentRecord("jmbag", "lName", "fName", 6);
		secondRecord = new StudentRecord("123455678", "Mason", "Jem", 2);
	}
	
	@Test
	public void testGetJmbag(){
		assertEquals("jmbag", normalRecord.getJmbag());
		assertEquals("123455678", secondRecord.getJmbag());
		
		IFieldValueGetter getter = new JmbagFieldGetter();
		assertEquals("jmbag", getter.get(normalRecord));
		assertEquals("123455678", getter.get(secondRecord));
	}
	
	@Test
	public void testGetFirstName(){
		assertEquals("fName", normalRecord.getFirstName());
		assertEquals("Jem", secondRecord.getFirstName());
		
		IFieldValueGetter getter = new FirstNameFieldGetter();
		assertEquals("fName", getter.get(normalRecord));
		assertEquals("Jem", getter.get(secondRecord));
	}
	
	@Test
	public void testGetLastName(){
		assertEquals("lName", normalRecord.getLastName());
		assertEquals("Mason", secondRecord.getLastName());
		
		IFieldValueGetter getter = new LastNameFieldGetter();
		assertEquals("lName", getter.get(normalRecord));
		assertEquals("Mason", getter.get(secondRecord));
	}

}
