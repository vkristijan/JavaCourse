package hr.fer.zemris.java.simplecomp;

import java.io.IOException;
import java.io.InputStream;

/**
 * Implementation of {@link InputStream} that is used as a wrapper for another
 * {@link InputStream}. This implementation will not close the inner
 * {@link InputStream} once the close method is called.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class CloseProtectedInputStream extends InputStream {
	/**
	 * The {@link InputStream} used in this implementation.
	 */
	private InputStream stream;

	/**
	 * Creates a new {@link CloseProtectedInputStream} with the given
	 * {@link InputStream}.
	 * 
	 * @param stream
	 *            the {@link InputStream} used in this
	 *            {@link CloseProtectedInputStream}.
	 */
	public CloseProtectedInputStream(InputStream stream) {
		this.stream = stream;
	}

	@Override
	public int read() throws IOException {
		return stream.read();
	}

	@Override
	public int read(byte[] b) throws IOException {
		return stream.read(b);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return stream.read(b, off, len);
	}

	@Override
	public void close() throws IOException {
	}
}
