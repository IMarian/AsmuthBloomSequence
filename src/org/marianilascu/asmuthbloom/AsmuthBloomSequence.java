package org.marianilascu.asmuthbloom;

import java.math.*;
import java.util.ArrayList;
import java.util.Random;

public class AsmuthBloomSequence {
	private int n;
	private int bitLength;
	private ArrayList<BigInteger> items;
	private int k;
	
	// start from pn instead of p0 (bigger p0)
	// |p0| = |pn| (nr de biti minim) pt ex. din pdf
	// generate levels(de instante): ex: nr .of level(2-10), nr. of participants(30), praguri(16)
	// parameters: nr. de nivele, vector cu n componente - nr. de participanti total din fiecare nivel,
	// vector cu k componente - alg. de enumerare
	public AsmuthBloomSequence(int n, int k, int bitLength) {
		this.n = n;
		this.k = k;
		this.bitLength = bitLength;
		this.items = new ArrayList<BigInteger>(n + 1);
		this.buildSequence();
	}
	
	public ArrayList<BigInteger> getItems() {
		return this.items;
	}
	
	public int getK() {
		return this.k;
	}
	
	public int getN() {
		return this.n;
	}
	
	private void buildSequence() {
		int[] range = this.generateRandomRange(Math.max(this.bitLength, 4));
		System.out.println("Range: " + range[0] + "-" + range[1]);
		int nr = range[1];
		
		while(this.items.size() != n && nr > range[0]) {
			if(isPrime(nr)) {
				BigInteger candidate = new BigInteger(new Long(nr).toString());
				
				if(!this.items.contains(candidate)) {
					this.items.add(candidate);
				}
			}
			
			nr--;
		}
		
//		for(int i = 0; i < this.n; i++) {
//			BigInteger newElement;
//			do {
//				newElement = this.generateRandomPrime(range[0], range[1]);
//			} while(this.items.contains(newElement));
//			
//			this.items.add(newElement);
//		}
		this.items.sort(null);
//		
		BigInteger a0 = buildA0();
		this.items.add(0, a0);
	}
	
	private BigInteger buildA0() {
		BigInteger firstProduct = BigInteger.ONE;
		BigInteger lastProduct = BigInteger.ONE;
		
		for(int i = 0; i < k; i++) {
			firstProduct = firstProduct.multiply(items.get(i));
		}
		
		for(int i = 0; i < k - 1; i++) {
			lastProduct = lastProduct.multiply(items.get(this.n - i - 1));
		}
		
		BigInteger a0;
		int max = firstProduct.divide(lastProduct).intValue();
		
		do{
			a0 = new BigInteger(new Integer(max).toString());
			max--;
		} while(!isPrime(a0.longValue()));
		
		return a0;
	}
	
//	private BigInteger generateRandomPrime(int minGenerate, int maxGenerate) {
//		Random rand = new Random();
//		BigInteger prime;
//	
//		do {
//			BigInteger generatedPrime = new BigInteger(new Long(rand.nextInt((maxGenerate - minGenerate) + 1) + minGenerate).toString());	
//			prime = generatedPrime;
//		} while(!isPrime(prime.longValue()));
//		
//		return prime;
//	}
	
	private int[] generateRandomRange(int length) {	
		int min = (int)Math.pow(2, this.bitLength - 1);
		int max = (int)Math.pow(2, this.bitLength);
		
//		if(length <= 8) {
//			max += 100;
//		}
		
//		int rangeLength = Math.max(50, (int)Math.pow(10, new Integer(min).toString().length() - 2));
//		
//		System.out.println(rangeLength);
//		
//		int maxGenerate = rand.nextInt((max - min) + 1) + min;
//		int minGenerate = Math.max(maxGenerate - rangeLength, min);
//		
//		if(this.bitLength < 10) {
//			minGenerate = (int)Math.pow(2, this.bitLength - 1);
//		}
//		
//		System.out.println(maxGenerate);
//		System.out.println(minGenerate);
		
		int[] result = new int[] { min, max };
		
		return result;
	}
	
	public ArrayList<BigInteger[]> generateLevels(int[] nrOfParticipants, int[] participantsThresholds) {
		ArrayList<BigInteger[]> levels = new ArrayList<BigInteger[]>();
		

		System.out.print("\n-------------------------------\n");
		for(int i = 0; i < nrOfParticipants.length; i++) {
			System.out.print("Level " + (i + 1) + ": ");
			levels.add(this.generateLevel(nrOfParticipants[i], participantsThresholds[i]));
			System.out.println();
		}
		System.out.println("\n-------------------------------\n");
		
		return levels;
	}
	
	public BigInteger[] generateLevel(int n, int k) {
		BigInteger[] sequence = new BigInteger[n];
		
		for(int i = 1; i <= n; i++) {
			sequence[i - 1] = this.items.get(i);
			System.out.print(sequence[i - 1] + " ");
		}
		
		return sequence;
	}
	
	public static boolean isPrime(long n) {
	    if (n%2 == 0) return false;
	    
	    for(int i = 3; i * i <= n; i += 2) {
	        if(n%i == 0)
	            return false;
	    }
	    return true;
	}
	
	public boolean isAsmuthBloom(Object[] sequence, int n, int k) {
		System.out.println("N=" + n + ", K=" + k);
		
		BigInteger firstProduct = BigInteger.ONE;
		BigInteger lastProduct = BigInteger.ONE;
		
		System.out.println("First Product:");
		for(int i = 0; i < k; i++) {
			System.out.print("p" + (i + 1) + ": " + sequence[i] + " ");
			firstProduct = firstProduct.multiply((BigInteger) sequence[i]);
		}
		System.out.println("= " + firstProduct);
		
		System.out.println("\nLast Product:");
		System.out.print("p0: " + this.getItems().get(0) + " ");
		for(int i = 0; i <= k - 2; i++) {
			System.out.print("p" + (n - i) + ": " + sequence[n - i - 1] + " ");
			lastProduct = lastProduct.multiply((BigInteger) sequence[n - i - 1]);
		}
		
		lastProduct = lastProduct.multiply(this.getItems().get(0));
		System.out.println("= " + lastProduct + "\n");
		
		return firstProduct.compareTo(lastProduct) > 0;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (BigInteger item : items) {
			sb.append(item + "\n");
		}
		
		return sb.toString();
	}
}
