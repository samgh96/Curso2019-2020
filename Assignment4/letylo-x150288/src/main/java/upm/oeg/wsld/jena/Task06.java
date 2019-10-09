package upm.oeg.wsld.jena;

import java.io.InputStream;

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD;

/**
 * Task 06: Modifying ontologies (RDFs)
 * @author elozano
 * @author isantana
 *
 */
public class Task06
{
	public static String ns = "http://somewhere#";
	public static String foafNS = "http://xmlns.com/foaf/0.1/";
	public static String foafEmailURI = foafNS+"email";
	public static String foafKnowsURI = foafNS+"knows";
	public static String stringTypeURI = "http://www.w3.org/2001/XMLSchema#string";
	
	public static void main(String args[])
	{
		String filename = "resources/example5.rdf";
		
		// Create an empty model
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
		
		// Use the FileManager to find the input file
		InputStream in = FileManager.get().open(filename);
	
		if (in == null)
			throw new IllegalArgumentException("File: "+filename+" not found");
	
		// Read the RDF/XML file
		model.read(in, null);
		
		// Create a new class named "Researcher"
		OntClass researcher = model.createClass(ns+"Researcher");
		
		// ** TASK 6.1: Create a new class named "University" **
		OntClass university = model.createClass(ns + "University");
		
		
		// ** TASK 6.2: Add "Researcher" as a subclass of "Person" **
		OntClass person = model.createClass(ns + "Person");
		person.addSubClass(researcher);
		
		// ** TASK 6.3: Create a new property named "worksIn" **
		OntProperty worksIn = model.createOntProperty(ns + "worksIn");
		worksIn.addDomain(researcher);
		worksIn.addDomain(university);
		
		// ** TASK 6.4: Create a new individual of Researcher named "Jane Smith" **
		Individual jane = model.createIndividual(ns + "Jane Smith", researcher);
		
		// ** TASK 6.5: Add to the individual JaneSmith the fullName, given and family names **
		jane.addProperty(VCARD.FN, "Jane Smith");
		jane.addProperty(VCARD.Given, "Jane");
		jane.addProperty(VCARD.Family, "Smith");

		// ** TASK 6.6: Add UPM as the university where John Smith works **
		Individual john = model.createIndividual(ns + "John Smith", researcher);
		john.addProperty(worksIn, "UPM");

		
		model.write(System.out, "RDF/XML-ABBREV");
	}
}
