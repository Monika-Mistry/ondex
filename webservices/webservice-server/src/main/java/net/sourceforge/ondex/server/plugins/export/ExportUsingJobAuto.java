/* Webservice wrapper of the Exportplugins.
*
*/
//WARNING AUTOGENERATED DO NOT EDIT DIRECTLY AS EDITS WILL BE OVERWRITTED.

package net.sourceforge.ondex.server.plugins.export;

import net.sourceforge.ondex.ONDEXPluginArguments;
import net.sourceforge.ondex.core.ONDEXGraph;
import net.sourceforge.ondex.wsapi.exceptions.*;
import net.sourceforge.ondex.wsapi.plugins.ZipFormat;
import org.apache.log4j.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import java.io.File;
import java.io.StringWriter;
import net.sourceforge.ondex.server.plugins.JobBase;
import net.sourceforge.ondex.wsapi.exceptions.WebserviceException;
import net.sourceforge.ondex.server.result.WSExportResult;



/**
* AutoGenerated
* @author Christian Brenninkmeijer
*/
public class ExportUsingJobAuto extends JobBase{ 

    private static final Logger logger = Logger.getLogger(ExportUsingJobAuto.class);

    public ExportUsingJobAuto() throws CaughtException{
        super();
    }

//Ignoring plugin without status export accstats
//Ignoring discontinuted plugin export attributecount
//Ignoring plugin without status export attrubutestats
//Ignoring plugin without status export clusterinfo
//Ignoring discontinuted plugin export conceptclass
//Ignoring discontinuted plugin export cv
     /**
     * Method to run Export in delimitedfile package.
     *
 	 *    Status: STABLE Tested June 2011 (Artem Lysenko)
     * @param Order
 	 *            (REQUIRED! No Default!) Path to look for in the graph. Groups of concepts and relations separated by # between the groups and by , with the group e.g Protein,Gene#it_with#Protein
     * @param Attributes
 	 *            (Optional. No Default.) [List]Attribute of the concept or relation to parse - valid format is position_in_path:type:subtype. Position numbering starts from 0. Valid combinations are: NN:name, NN:accession:DataSource, NN:cv, NN:gds:AttributeName, NN:evidence, NN:class, NN:pid, NN:description, NN:context:ConceptClass and NN:synonyms Example: 0:accession:TAIR
     * @param Min
 	 *            (Optional. No Default.) Report incomplete entries over or equal to this depth.
     * @param RemoveDuplicates
 	 *            (Optional. Defaults to :false) Remove duplicate entries - on large graphs is slow and can cause the application to run out of memory
     * @param UseAccessionLinks
 	 *            (Optional. Defaults to :false) Write Accessions as Excel links
     * @param TranslateTaxId
 	 *            (Optional. Defaults to :false) Translates TAXID gds values to there scientific name
     ////paramter Zip ignored as compression done by webservice.
     * @param compression
 	 *           (Optional. Defaults to :false)                  This Compression done within the plugin. The legal values are "" in which case the output file's contents are returned as a String. (This is the default) In all other case the file is held on the server and only a url is returned. "url" results in a url pointing to the unzipped file as returned by the plugin. "zip", "tar", "tar.gz" and "gz" request the WebServer to compress the returned file. In all url and compressed url cases the resulting file is available to anyone who knows the url, and remains avaiable until cleaned up by the adminsistrator. 
     * @param graphId
 	 *            (REQUIRED! No Default!) The ID of the Graph
     * @return WSExportResult. Complex return of the URL to which the file will be written and the JobID. (Taverna users need two XML Splitters
     * @throws WebserviceException
     */
    @WebResult(name = "wsExportResult")

    @WebMethod(exclude = false)
    public WSExportResult delimitedfileExport(
           @WebParam(name = "Order")java.lang.String Order,
           @WebParam(name = "Attributes")java.lang.String[] Attributes,
           @WebParam(name = "Min")java.lang.String Min,
           @WebParam(name = "RemoveDuplicates")java.lang.Boolean RemoveDuplicates,
           @WebParam(name = "UseAccessionLinks")java.lang.Boolean UseAccessionLinks,
           @WebParam(name = "TranslateTaxId")java.lang.Boolean TranslateTaxId,
     ////paramter Zip ignored as compression done by webservice.
           @WebParam(name = "compression") java.lang.String compression,
           @WebParam(name = "graphId") Long graphId)
           throws WebserviceException {
        try{
           logger.info("export delimitedfile called");
           net.sourceforge.ondex.export.delimitedfile.Export export =
                   new net.sourceforge.ondex.export.delimitedfile.Export();
           logger.info(");Export created "+export);
           ONDEXGraph graph = webServiceEngine.getGraphToEdit(graphId);
           logger.info("found graph");
           ONDEXPluginArguments arguments = new ONDEXPluginArguments(export.getArgumentDefinitions());
           createArguement(export, arguments, "Order", Order);
           createArguement(export, arguments, "Attributes", Attributes);
           createArguement(export, arguments, "Min", Min);
           createArguement(export, arguments, "RemoveDuplicates", RemoveDuplicates);
           createArguement(export, arguments, "UseAccessionLinks", UseAccessionLinks);
           createArguement(export, arguments, "TranslateTaxId", TranslateTaxId);
           createArguement(export, arguments, "Zip", false);
           ZipFormat zipFormat = ZipFormat.parseString(compression);
           File output = createOutputFile("output",".xml");
           addExportFile(export, "ExportFile", arguments, output);
           logger.info("created arguements");
          ExportJob job = new ExportJob(graph, output, export, arguments);
          return new WSExportResult(job);
        } catch (WebserviceException e)   {
            throw e;
        } catch (Exception e)    {
            throw new CaughtException (e, logger);
        }
    } //WARNING AUTOGENERATED DO NOT EDIT DIRECTLY AS EDITS WILL BE OVERWRITTED.

//Ignoring plugin without status export dot
//Ignoring plugin without status export fasta
//Ignoring discontinuted plugin export goaquality
//Ignoring plugin without status export graphinfo
//Ignoring plugin without status export interactionExpStat
//Ignoring discontinuted plugin export intersectionofcvs
//Ignoring plugin with Webservice include Never annotation export killondex
//Ignoring discontinuted plugin export leells
//Ignoring discontinuted plugin export metadata
//Ignoring discontinuted plugin export metadatacount
//Ignoring plugin without status export ncl_acc_clust
//Ignoring plugin without status export neighbourhood
//Ignoring discontinuted plugin export ontologydistance
//Ignoring plugin without status export owl2
     /**
     * Method to run Export in oxl package.
     *
 	 *    Status: STABLE Tested March 2010 (Artem Lysenko)
 	 *    Authors: Matthew Pocock, Jan Taubert, K Sieren, 
 	 *    Custodians: Jan Taubert, 
     * @param ExcludeConceptsOfConceptClass
 	 *            (Optional. No Default.) [List]This parameter can be used to do some basic filtering on ConceptClass in the export method.
This is especially useful if graphs become to large.
     * @param ExcludeRelationsOfRelationType
 	 *            (Optional. No Default.) [List]This parameter can be used to do some basic filtering on RelationType in the export method.
This is especially useful if graphs become to large.
     * @param ExcludeAttributeWithName
 	 *            (Optional. No Default.) [List]This parameter can be used to exclude a number of attributes from being written in the ondex.xml file.
When **ALL** is used all attributes are excluded and thus no Attribute values will be written.
     * @param IncludeAttributesOfName
 	 *            (Optional. No Default.) [List]This parameter works by setting exclusive inclusions for a set of Attribute Attributes. All other GDSs Attributes not specified will be excluded.
     * @param IncludeOnlyConceptClass
 	 *            (Optional. No Default.) [List]This parameter works by setting exclusive inclusions for a set of Concept Classes. All other Concept Classes not specified will be excluded.
     * @param IncludeOnlyRelationType
 	 *            (Optional. No Default.) [List]This parameter works by setting exclusive inclusions for a set of Relation Types. All other Relation Types not specified will be excluded.
     * @param pretty
 	 *            (Optional. Defaults to :true) When this option is set the output XML is kind of pretty printed. This makes the output larger.
     * @param ExportIsolatedConcepts
 	 *            (Optional. Defaults to :true) When this is option is set, it will export also concepts without any relations.
     ////paramter GZip ignored as compression done by webservice.
     * @param compression
 	 *           (Optional. Defaults to :false)                  This Compression done within the plugin. The legal values are "" in which case the output file's contents are returned as a String. (This is the default) In all other case the file is held on the server and only a url is returned. "url" results in a url pointing to the unzipped file as returned by the plugin. "zip", "tar", "tar.gz" and "gz" request the WebServer to compress the returned file. In all url and compressed url cases the resulting file is available to anyone who knows the url, and remains avaiable until cleaned up by the adminsistrator. 
     * @param graphId
 	 *            (REQUIRED! No Default!) The ID of the Graph
     * @return WSExportResult. Complex return of the URL to which the file will be written and the JobID. (Taverna users need two XML Splitters
     * @throws WebserviceException
     */
    @WebResult(name = "wsExportResult")

    @WebMethod(exclude = false)
    public WSExportResult oxlExport(
           @WebParam(name = "ExcludeConceptsOfConceptClass")java.lang.String[] ExcludeConceptsOfConceptClass,
           @WebParam(name = "ExcludeRelationsOfRelationType")java.lang.String[] ExcludeRelationsOfRelationType,
           @WebParam(name = "ExcludeAttributeWithName")java.lang.String[] ExcludeAttributeWithName,
           @WebParam(name = "IncludeAttributesOfName")java.lang.String[] IncludeAttributesOfName,
           @WebParam(name = "IncludeOnlyConceptClass")java.lang.String[] IncludeOnlyConceptClass,
           @WebParam(name = "IncludeOnlyRelationType")java.lang.String[] IncludeOnlyRelationType,
           @WebParam(name = "pretty")java.lang.Boolean pretty,
           @WebParam(name = "ExportIsolatedConcepts")java.lang.Boolean ExportIsolatedConcepts,
     ////paramter GZip ignored as compression done by webservice.
           @WebParam(name = "compression") java.lang.String compression,
           @WebParam(name = "graphId") Long graphId)
           throws WebserviceException {
        try{
           logger.info("export oxl called");
           net.sourceforge.ondex.export.oxl.Export export =
                   new net.sourceforge.ondex.export.oxl.Export();
           logger.info(");Export created "+export);
           ONDEXGraph graph = webServiceEngine.getGraphToEdit(graphId);
           logger.info("found graph");
           ONDEXPluginArguments arguments = new ONDEXPluginArguments(export.getArgumentDefinitions());
           createArguement(export, arguments, "ExcludeConceptsOfConceptClass", ExcludeConceptsOfConceptClass);
           createArguement(export, arguments, "ExcludeRelationsOfRelationType", ExcludeRelationsOfRelationType);
           createArguement(export, arguments, "ExcludeAttributeWithName", ExcludeAttributeWithName);
           createArguement(export, arguments, "IncludeAttributesOfName", IncludeAttributesOfName);
           createArguement(export, arguments, "IncludeOnlyConceptClass", IncludeOnlyConceptClass);
           createArguement(export, arguments, "IncludeOnlyRelationType", IncludeOnlyRelationType);
           createArguement(export, arguments, "pretty", pretty);
           createArguement(export, arguments, "ExportIsolatedConcepts", ExportIsolatedConcepts);
           createArguement(export, arguments, "GZip", false);
           ZipFormat zipFormat = ZipFormat.parseString(compression);
           File output = createOutputFile("output",".xml");
           addExportFile(export, "ExportFile", arguments, output);
           logger.info("created arguements");
          ExportJob job = new ExportJob(graph, output, export, arguments);
          return new WSExportResult(job);
        } catch (WebserviceException e)   {
            throw e;
        } catch (Exception e)    {
            throw new CaughtException (e, logger);
        }
    } //WARNING AUTOGENERATED DO NOT EDIT DIRECTLY AS EDITS WILL BE OVERWRITTED.

//Ignoring plugin without status export prolog
//Ignoring discontinuted plugin export relationevidence
//Ignoring plugin without status export sbml
//Ignoring plugin with Webservice include Never annotation export slottableAdapter
//Ignoring discontinuted plugin export specificity
//Ignoring plugin without status export tab
//Ignoring plugin without status export tabdump
//Ignoring plugin without status export tagclusters
//Ignoring plugin without status export xhtml
}