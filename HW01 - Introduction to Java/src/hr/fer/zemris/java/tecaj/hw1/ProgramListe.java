package hr.fer.zemris.java.tecaj.hw1;

/**
 * This class describes a linked list that contains strings and some helper
 * methods. <br>
 * The available methods are to add a new element, get the list size, sort the
 * list and print all of it's elements.
 * 
 * @version 1.0
 *
 */
class ProgramListe {
	/**
	 * This class describes a single node in a linked list. Every node in the
	 * list has a string with data and a pointer to the next element.
	 *
	 */
	static class CvorListe {
		CvorListe sljedeci;
		String podatak;
	}

	/**
	 * This method starts the program, adds some elements into the list, prints
	 * and sorts the list.
	 * 
	 * @param args
	 *            an array of arguments from the console
	 */
	public static void main(String[] args) {
		CvorListe cvor = null;
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
		System.out.println("Ispisujem listu uz originalni poredak:");
		ispisiListu(cvor);
		cvor = sortirajListu(cvor);
		System.out.println("Ispisujem listu nakon sortiranja:");
		ispisiListu(cvor);
		int vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: " + vel);
	}

	/**
	 * Calculates the number of elements in the list by going through the whole
	 * list. <br>
	 * Time complexity: <strong> O(n) </strong>
	 * 
	 * @param cvor
	 *            the root of the list
	 * @return the number of elements in the list
	 */
	private static int velicinaListe(CvorListe cvor) {
		if (cvor == null)
			return 0;
		return 1 + velicinaListe(cvor.sljedeci);
	}

	/**
	 * Adds an element into the list, and returns the head of the list.
	 * 
	 * @param prvi
	 *            the head of the list
	 * @param podatak
	 *            the element that should be added
	 * @return returns the head of the list with the element inserted
	 */
	private static CvorListe ubaci(CvorListe prvi, String podatak) {
		if (prvi == null) {
			prvi = new CvorListe();
			prvi.podatak = podatak;
			return prvi;
		}

		prvi.sljedeci = ubaci(prvi.sljedeci, podatak);
		return prvi;
	}

	/**
	 * Prints all the elements in the list.
	 * 
	 * @param cvor
	 *            the head of the list.
	 */
	private static void ispisiListu(CvorListe cvor) {
		if (cvor == null) {
			System.out.println();
			return;
		}

		System.out.println(cvor.podatak + " ");
		ispisiListu(cvor.sljedeci);
	}

	/**
	 * Sorts all the elements in the list in an ascending order, using
	 * <a href = "https://en.wikipedia.org/wiki/Bubble_sort"> bubble sort. </a>.
	 * 
	 * @param cvor
	 *            the head of the list
	 * @return the head of the sorted list
	 */
	private static CvorListe sortirajListu(CvorListe cvor) {
		if (velicinaListe(cvor) < 2)
			return cvor;

		boolean sorted = false;
		while (sorted == false) {
			sorted = true;

			for (CvorListe node = cvor; node.sljedeci != null; node = node.sljedeci) {
				if (node.podatak.compareTo(node.sljedeci.podatak) > 0) {
					String tmp = node.podatak;
					node.podatak = node.sljedeci.podatak;
					node.sljedeci.podatak = tmp;

					sorted = false;
				}
			}
		}
		return cvor;
	}
}
