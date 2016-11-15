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
public class InstrRetTest {
	@Mock
	private Computer computer;
	private Memory memory;
	private Registers registers;
	
	@Test
	public void testPush(){
		registers = new RegistersImpl(16);
		registers.setRegisterValue(15, 100);

		memory = new MemoryImpl(256);
		memory.setLocation(101, 42);
		
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		
		List<InstructionArgument> arguments = new LinkedList<>();
		
		InstrRet ret = new InstrRet(arguments);
		ret.execute(computer);

		assertEquals(101, registers.getRegisterValue(15));
		assertEquals(42, registers.getProgramCounter());
		
		verify(computer, atMost(5)).getRegisters();
		verify(computer, atMost(5)).getMemory();
	}
}
