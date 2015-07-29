package com.lastabyss.carbon.instrumentation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Navid
 */
public class CarbonTransformAgent implements ClassFileTransformer {

	// All pretransformed classes should be located in the pretransformedclasses folder inside the jar

	private static Instrumentation instrumentation = null;
	private static CarbonTransformAgent transformer;

	// Public static void main() but for this agent
	public static void agentmain(String string, Instrumentation instrument) {
		instrumentation = instrument;

		LogManager.getLogger().info("[Carbon] Loaded transformer agent!");

		transformer = new CarbonTransformAgent();
		instrumentation.addTransformer(transformer);
	}

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		return null;
	}

	/**
	 * Gets class before transformation.
	 * 
	 * @param className
	 * @return
	 * @throws IOException
	 */
	private static byte[] getPreTransformedClass(String className) throws IOException {
		InputStream stream = CarbonTransformAgent.class.getClassLoader().getResourceAsStream("pretransformedclasses/" + className + ".class");
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[4096];
		while ((nRead = stream.read(data)) != -1) {
			buffer.write(data, 0, nRead);
		}
		buffer.flush();
		return buffer.toByteArray();
	}

	public static void killAgent() {
		instrumentation.removeTransformer(transformer);
	}

}
