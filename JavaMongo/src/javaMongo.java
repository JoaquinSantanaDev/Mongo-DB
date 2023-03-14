import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;

public class javaMongo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			Mongo mongo = new Mongo("localhost", 27017);
			DB p = mongo.getDB("Semana2");
			
			ArrayList<Document> docs = new ArrayList<Document>();
			
			MongoCollection<Document> collection = (MongoCollection<Document>) p.getCollection("Peliculas");
			
			Document d1 = new Document("");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		

	}
	

}
