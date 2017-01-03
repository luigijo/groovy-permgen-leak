package br.com.luigijo.groovy;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.junit.Assert;
import org.junit.Test;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

public class PermGenLeak {

	private static final NumberFormat nf = new DecimalFormat("0.######");

	@Test
	public void test() throws Throwable {

		URL url = getClass().getClassLoader().getResource(".");
		Assert.assertNotNull(url);

		int i = 0;
		Binding bind = new Binding();
		while (true) {
			GroovyScriptEngine gse = new GroovyScriptEngine(new URL[] { url });
			gse.run("Script.groovy", bind);
			printMemory(i++);
		}
	}

	private void printMemory(int i) {
		if (i % 100 == 0) {
			for (MemoryPoolMXBean item : ManagementFactory.getMemoryPoolMXBeans()) {
				if (item.getType() == MemoryType.NON_HEAP && item.getName().contains("Perm Gen")) {
					MemoryUsage usage = item.getUsage();
					double usageMb = usage.getUsed() / (1024d * 1024d);
					double max = usage.getMax() / (1024d * 1024d);
					System.out.println(nf.format(usageMb) + " / " + nf.format(max));
				}
			}
		}
	}

}