package org.marianilascu.asmuthbloom;

import static org.junit.Assert.*;

import java.math.*;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class AsmuthBloomSequenceTest {

	static AsmuthBloomSequence asmuthBloomSequence;
	
	@BeforeClass
	public static void createSequence() {
		asmuthBloomSequence = new AsmuthBloomSequence(6, 4, 8);
		System.out.println("Sequence: ");
		for(BigInteger item : asmuthBloomSequence.getItems()) {
			System.out.print("p" + (asmuthBloomSequence.getItems().indexOf(item)) + ": " + item + " ");
		}
		System.out.println("\n");
	}
	
	@Test
	public void numbersArePrime() {	
		for(BigInteger item : asmuthBloomSequence.getItems()) {
			assertTrue(AsmuthBloomSequence.isPrime(item.intValue()));
		}
	}

	@Test
	public void firstProductIsLowerThanLastProduct() {
		
		BigInteger firstProduct = BigInteger.ONE;
		BigInteger lastProduct = BigInteger.ONE;
		
		System.out.println("\nFirst Product:");
		for(int i = 1; i <= asmuthBloomSequence.getK(); i++) {
			System.out.print(("p" + i) + ": " + asmuthBloomSequence.getItems().get(i) + " ");
			firstProduct = firstProduct.multiply(asmuthBloomSequence.getItems().get(i));
		}
		
		System.out.println("\nLast Product:");
		System.out.print("p0: " + asmuthBloomSequence.getItems().get(0) + " ");
		for(int i = 0; i <= asmuthBloomSequence.getK() - 2; i++) {
			System.out.print(("p" + (asmuthBloomSequence.getN() - i)) + ": " + asmuthBloomSequence.getItems().get(asmuthBloomSequence.getN() - i) + " ");
			lastProduct = lastProduct.multiply(asmuthBloomSequence.getItems().get(asmuthBloomSequence.getN() - i));
		}
		
		lastProduct = lastProduct.multiply(asmuthBloomSequence.getItems().get(0));
		
		assertTrue(firstProduct.compareTo(lastProduct) > 0);
	}
	
	@Test
	@Ignore
	public void testN12K4() {
		assertTrue(asmuthBloomSequence.isAsmuthBloom(asmuthBloomSequence.getItems().toArray(), 12, 4));
		System.out.println("\n");
	}
	
	@Test
	@Ignore
	public void testN7K3() {
		assertTrue(asmuthBloomSequence.isAsmuthBloom(asmuthBloomSequence.getItems().toArray(), 7, 3));
		System.out.println("\n");
	}
	
	@Test
	@Ignore
	public void testN3K2() {
		assertTrue(asmuthBloomSequence.isAsmuthBloom(asmuthBloomSequence.getItems().toArray(), 3, 2));
		System.out.println("\n");
	}
	
	@Test
	@Ignore
	public void testN5K4() {
		assertTrue(asmuthBloomSequence.isAsmuthBloom(asmuthBloomSequence.getItems().toArray(), 5, 4));
		System.out.println("\n");
	}
	
	@Test
	public void testLevels() {
		int[] ns = { 3, 4, 5 };
		int[] ks = { 2, 3, 4 };
		ArrayList<BigInteger[]> levels = asmuthBloomSequence.generateLevels(ns, ks);
		
		for(int i = 0; i < levels.size(); i++) {
			assertTrue(asmuthBloomSequence.isAsmuthBloom(levels.get(i), ns[i], ks[i]));
		}
	}
}
