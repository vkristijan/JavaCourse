package hr.fer.zemris.java.simplecomp.impl.instructions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.MemoryImpl;
import hr.fer.zemris.java.simplecomp.impl.RegistersImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;


@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class) 

public class InstrMoveTest {
	@Mock
	private InstructionArgument destination;
	@Mock
	private InstructionArgument source;
	@Mock
	private Computer computer;
	private Memory memory;
	private Registers registers;
	
	/**
	 * Test the move method for 2 registers.
	 */
	@Test
	public void testMoveRegisters(){
		registers = new RegistersImpl(16);
		registers.setRegisterValue(2, 42);

		when(destination.isRegister()).thenReturn(true); 
		when(destination.getValue()).thenReturn(1); 
		
		when(source.isRegister()).thenReturn(true); 
		when(source.getValue()).thenReturn(2);
		
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		
		List<InstructionArgument> arguments = new LinkedList<>();
		arguments.add(destination);
		arguments.add(source);
		
		InstrMove move = new InstrMove(arguments);
		move.execute(computer);

		
		assertEquals(42, registers.getRegisterValue(1));
		verify(computer, atMost(2)).getRegisters();
		verify(computer, never()).getMemory();
		
		verify(destination, atMost(2)).getValue();
		verify(source, atMost(2)).getValue();
	}
	
	/**
	 * Test the move method for a register and a number.
	 */
	@Test
	public void testMoveRegisterNumber(){
		registers = new RegistersImpl(16);

		when(destination.isRegister()).thenReturn(true); 
		when(destination.getValue()).thenReturn(1); 
		
		when(source.isNumber()).thenReturn(true); 
		when(source.getValue()).thenReturn(1337);
		
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		
		List<InstructionArgument> arguments = new LinkedList<>();
		arguments.add(destination);
		arguments.add(source);
		
		InstrMove move = new InstrMove(arguments);
		move.execute(computer);

		
		assertEquals(1337, registers.getRegisterValue(1));
		verify(computer, atMost(2)).getRegisters();
		verify(computer, never()).getMemory();
		
		verify(destination, atMost(2)).getValue();
		verify(source, atMost(2)).getValue();
	}

	/**
	 * Test the move method for 2 registers with offset.
	 */
	@Test
	public void testMoveRegistersWithOffset(){
		Object value = "testValue";
		
		int sourceValue = (12 << 8) + 1;
		sourceValue |= (1 << 24);
		
		int destinationValue = (28 << 8) + 2;
		destinationValue |= (1 << 24);
		
		memory = new MemoryImpl(128);
		memory.setLocation(17, value);
		
		registers = new RegistersImpl(16);
		registers.setRegisterValue(1, 5);
		registers.setRegisterValue(2, 11);

		when(destination.isRegister()).thenReturn(true); 
		when(destination.getValue()).thenReturn(destinationValue); 
		
		when(source.isRegister()).thenReturn(true); 
		when(source.getValue()).thenReturn(sourceValue);
		
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		
		List<InstructionArgument> arguments = new LinkedList<>();
		arguments.add(destination);
		arguments.add(source);
		
		InstrMove move = new InstrMove(arguments);
		move.execute(computer);

		
		assertEquals(value, memory.getLocation(39));
		verify(computer, atMost(2)).getRegisters();
		verify(computer, atMost(2)).getMemory();
		
		verify(destination, atMost(5)).getValue();
		verify(source, atMost(5)).getValue();
	}
}
