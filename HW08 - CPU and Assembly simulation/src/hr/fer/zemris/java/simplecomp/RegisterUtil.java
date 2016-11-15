package hr.fer.zemris.java.simplecomp;

/**
 * Utility class with helper methods to work with register descriptors. Every
 * descriptor is an 32 bit integer that has a flag that tells if the descriptor
 * has direct or indirect address. The flag is on the 24<sup>th</sup> bit. The
 * next 16 bits are the offset that is used only if the address is indirect. The
 * last 8 bits are the index of the register used.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class RegisterUtil {
	/**
	 * The bit of the indirect flag.
	 */
	private static final int INDIRECT_FLAG = 24;
	/**
	 * The number of bits used for register index.
	 */
	private static final int REGISTER_BITS = 8;
	/**
	 * The number of bits used for the offset.
	 */
	private static final int OFFSET_LENGTH = 16;

	/**
	 * Returns the register index from the register descriptor. The register
	 * index is defined by the last 8 bits.
	 * 
	 * @param registerDescriptor
	 *            the descriptor that has the register index in it.
	 * @return the register index.
	 */
	public static int getRegisterIndex(int registerDescriptor) {
		int mod = 1 << REGISTER_BITS;
		return registerDescriptor % mod;
	}

	/**
	 * Checks if the given descriptor has indirect or direct addressing.
	 * 
	 * @param registerDescriptor
	 *            the descriptor that should be checked.
	 * @return a {@link Boolean} value <strong>true</strong> if the register
	 *         descriptor has indirect addressing, <strong>false</strong>
	 *         otherwise.
	 */
	public static boolean isIndirect(int registerDescriptor) {
		int flag = registerDescriptor & (1 << INDIRECT_FLAG);
		return flag != 0;
	}

	/**
	 * Returns the offset from the register descriptor. The offset is defined
	 * with the 16 bits from 23 to 8.
	 * 
	 * @param registerDescriptor
	 *            the descriptor for which the offset should be calculated.
	 * @return the offset for the register descriptor.
	 */
	public static int getRegisterOffset(int registerDescriptor) {
		registerDescriptor >>= REGISTER_BITS;
		int mod = 1 << OFFSET_LENGTH;

		short value = (short) (registerDescriptor % mod);
		return value;
	}
}
