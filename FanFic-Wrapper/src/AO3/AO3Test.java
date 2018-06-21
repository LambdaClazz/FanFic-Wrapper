package AO3;

import java.io.IOException;
import java.util.Scanner;

public class AO3Test {
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		
		String command = null;
		do {
			System.out.println("Enter A Work ID Number (type exit to leave): ");
			command = sc.nextLine();
			
			int num = 0;
			
			try {
				num = Integer.parseInt(command);
			} catch(NumberFormatException e) {
				if(!command.equals("exit"))
					System.out.println("Sorry, that doesn't look like a work id number... Try Again...?");
				else
					break;
			}
			
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			System.out.println("Getting the Work's Info now......");
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			new AO3Work(num);
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		} while(!command.equals("exit"));
		
		sc.close();
		
//		new AO3Work(5562529);
//		new AO3Work(14138205);
//		new AO3Work(977027);
//		new AO3Work(14046996);
//		new AO3Work(976092);
//		new AO3Work(1830178);
	}
}
