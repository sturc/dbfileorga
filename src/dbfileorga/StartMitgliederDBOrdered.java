package dbfileorga;

public class StartMitgliederDBOrdered {

	public static void main(String[] args) {
			MitgliederDB db = new MitgliederDB(true);
			
			System.out.println(db);
			
			// TODO test your implementation with the following use cases  
			
			// read the a record number e.g. 32 (119;2;44;Albers;Hans;07.10.75;01.05.89;90;25)
			Record rec = db.read(32);
			System.out.println(rec);

			//find and read a record with a given Mitgliedesnummer e.g 95 / without binary search
			rec = db.read(db.findPos("95"));
			System.out.println(rec);
	
			//insert Hans Maier
			int newRecNum = db.insert(new Record("122;2;44;Meier;Hans;07.05.01;01.03.10;120;15"));
			System.out.println(db.read(newRecNum));
			
			//modify (ID95 Steffi Brahms wird zu ID 95 Steffi Bach)
			db.modify(db.findPos("95"), new Record("95;3;13;Bach;Steffi;04.04.06;01.02.16;;5"));
			System.out.println(db.read(db.findPos("95")));
			
			//delete the record with Mitgliedsnummer 97 (97;1;65;Krapp;Theo;10.10.87;01.03.07;115;25) 
			db.delete(db.findPos("97"));
			System.out.println(db);
			
			
	}

}
