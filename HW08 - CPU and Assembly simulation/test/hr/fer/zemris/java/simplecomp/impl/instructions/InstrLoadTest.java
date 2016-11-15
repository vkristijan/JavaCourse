package hr.fer.zemris.java.simplecomp.impl.instructions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;


@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class) 
public class InstrLoadTest {
	@Mock
	private InstructionArgument argument1;
	@Mock
	private InstructionArgument argument2;
	@Mock
	private Computer computer;
	@Mock
	private Memory memory;
	@Mock
	private Registers registers;
	
	@Test
	public void testLoad(){
		Object value = new Object();
		when(argument1.isRegister()).thenReturn(true); 
		when(argument1.getValue()).thenReturn(4); 
		
		when(argument2.isNumber()).thenReturn(true); 
		when(argument2.getValue()).thenReturn(42);
		
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		
		when(memory.getLocation(42)).thenReturn(value);
		
		List<InstructionArgument> arguments = new LinkedList<>();
		arguments.add(argument1);
		arguments.add(argument2);
		
		InstrLoad load = new InstrLoad(arguments);
		load.execute(computer);
		
		verify(argument1, atMost(2)).getValue();
		verify(argument2, atMost(2)).getValue();
		
		verify(computer).getMemory();
		verify(computer).getRegisters();
		
		verify(memory).getLocation(42);
		verify(registers).setRegisterValue(4, value);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalArgument(){
		int address = 1 << 24;
		
		when(argument1.isRegister()).thenReturn(true); 
		when(argument1.getValue()).thenReturn(address); 
		
		List<InstructionArgument> arguments = new LinkedList<>();
		arguments.add(argument1);
		arguments.add(argument2);
		
		InstrLoad load = new InstrLoad(arguments);
		load.execute(computer);
		
		verify(argument1).getValue();
	}
}
