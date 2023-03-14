import static com.mongodb.client.model.Filters.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static java.util.Arrays.asList;

public class JavaMongodbInsertData {

	public static void main(String[] args) {
		try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
			MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Semana2");
			MongoCollection<Document> gradesCollection = sampleTrainingDB.getCollection("Peliculas");
			CrearDocumentos(gradesCollection);
			BuscarDocumentos(gradesCollection);
			ModificarDocumentos(gradesCollection);
			EliminarDocumentos(gradesCollection);
			
		}
	}

	
	private static void CrearDocumentos(MongoCollection<Document> gradesCollection) {
		List<Document> grades = new ArrayList<>();

		List<Document> peliculas = asList(new Document("titulo", "Harry Potter y la piedra filosofal")
				.append("basadaen", "Harry Potter y la piedra filosofal de J. K. Rowling")
				.append("actores" , asList("Daniel Radcliffe", "Rupert Grint", "Emma Watson"
						, "Robbie Coltrane", "Richard Harris", "Alan Rickman", "Maggie Smith"))
				.append("anio", 2001)
				.append("sinopsis",
					"Harry Potter se ha quedado huérfano y vive en casa de sus abominables tíos y "
					+ "el insoportable primo Dudley. Harry se siente muy triste y solo, hasta que un buen día "
					+ "recibe una carta que cambiará su vida para siempre. En ella le comunican que ha sido aceptado "
					+ "como alumno en el Colegio Hogwarts de Magia."),
				
				new Document("nombre", "Harry Potter y la cámara secreta")
				.append("actores", asList( "Daniel Radcliffe", "Rupert Grint", "Emma Watson"
						, "Toby Jones" , "Kenneth Branagh]"))
				.append("anio", 2003),
				
				new Document("nombre", "Harry Potter and the Prisoner of Azkaban")
				.append("anio", 2004)
				.append("actores", asList( "Daniel Radcliffe", "Rupert Grint", "Emma Watson"
						, "Gary Oldman", "David Thewlis", "Emma Thompson")),	
				
				new Document("titulo", "Harry Potter y el cáliz de fuego")
				.append("anio", 2005).append("basadaen", "Harry Potter y el cáliz de fuego, de J. K. Rowling"),
				
				new Document("titulo", "Harry Potter y la Orden del Fénix")	
				.append("anio", 2007));
		
		grades.addAll( peliculas);

		gradesCollection.insertMany(grades);
		
		FindIterable<Document> buscarTodos = gradesCollection.find();
	    buscarTodos.iterator().forEachRemaining(d -> System.out.println(d.toJson()));

	}
	
	
	private static void BuscarDocumentos(MongoCollection<Document> gradesCollection) {

		FindIterable<Document> buscarTodos = gradesCollection.find();
	    buscarTodos.iterator().forEachRemaining(d -> System.out.println(d.toJson()));

	    BasicDBObject gtQuery = new BasicDBObject();
		gtQuery.put("actores", new BasicDBObject("$in", asList("Emma Watson", "Daniel Radcliffe")));
		FindIterable<Document> c = gradesCollection.find(gtQuery);
	    c.iterator().forEachRemaining(d -> System.out.println(d.toJson()));
	    
	    BasicDBObject gtQuery1 = new BasicDBObject();
		gtQuery1.put("basadaen", new BasicDBObject("$regex", "Stan"));
		gtQuery1.put("anio", 2019);
		FindIterable<Document> c1 = gradesCollection.find(gtQuery1);
	    c1.iterator().forEachRemaining(d -> System.out.println(d.toJson()));
	    
	    BasicDBObject gtQuery4 = new BasicDBObject();
		gtQuery4.put("anio", new BasicDBObject("$lte", 2013));
		FindIterable<Document> c4 = gradesCollection.find(gtQuery4);
	    c4.iterator().forEachRemaining(d -> System.out.println(d.toJson()));

	    BasicDBObject gtQuery5 = new BasicDBObject();
		gtQuery5.put("sinopsis", new BasicDBObject("$regex", "Hogwarts").append("$options", "i"));
		FindIterable<Document> c5 = gradesCollection.find(gtQuery5);
	    c5.iterator().forEachRemaining(d -> System.out.println(d.toJson()));
	    
    
	}
	
	private static void ModificarDocumentos(MongoCollection<Document> gradesCollection) {
		
		  Bson filter = new Document("anio", 2003);
	      Bson newValue = new Document("anio", 2002);
	      Bson updateOperationDocument = new Document("$set", newValue);
	      gradesCollection.updateMany(filter, updateOperationDocument);
	      System.out.println("Document update successfully...");
	      System.out.println("List of the documents after update");
	      

		  Bson filter2 = new Document("anio", 2004);
	      Bson newValue2 = new Document("basadaen", "Harry Potter y el prisionero de Azkaban, de J. K. Rowling.");
	      Bson updateOperationDocument2 = new Document("$set", newValue2);
	      gradesCollection.updateMany(filter2, updateOperationDocument2);
	      System.out.println("Document update successfully...");
	      System.out.println("List of the documents after update");
	      
	      Bson filter3 = new Document("nombre", new BasicDBObject("$regex", "Harry Potter"));
	      Bson updateOperationDocument3 = new Document("$rename", new BasicDBObject().append("nombre", "titulo"));
	      gradesCollection.updateMany(filter3, updateOperationDocument3);
	      System.out.println("Document update successfully...");
	      System.out.println("List of the documents after update");
	      
	      Bson filter4 = new Document("titulo", new BasicDBObject("$regex", "Harry Potter"));
	      Bson newValue4 = new Document("saga", "Harry Potter");
	      Bson updateOperationDocument4 = new Document("$set", newValue4);
	      gradesCollection.updateMany(filter4, updateOperationDocument4);
	      System.out.println("Document update successfully...");
	      System.out.println("List of the documents after update");
	     
	     
        
	}
	
	private static void EliminarDocumentos(MongoCollection<Document> gradesCollection) {
		
	    Bson filter = new Document("titulo", new BasicDBObject("$regex", "Harry Potter y la Orden del Fénix"));
		DeleteResult result = gradesCollection.deleteMany(filter);
		System.out.println(result);
		
		 Bson filter2 = new Document("basadaen", new BasicDBObject("$regex", "cáliz de fuego"));
		DeleteResult result2 = gradesCollection.deleteMany(filter2);
		System.out.println(result2);
		

	}
}