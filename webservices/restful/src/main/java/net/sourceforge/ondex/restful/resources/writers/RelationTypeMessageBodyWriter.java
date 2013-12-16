package net.sourceforge.ondex.restful.resources.writers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import net.sourceforge.ondex.core.RelationType;
import net.sourceforge.ondex.export.oxl.Export;
import net.sourceforge.ondex.restful.util.WstxOutputFactoryProvider;

import org.codehaus.stax2.XMLStreamWriter2;

import com.ctc.wstx.io.CharsetNames;
import com.sun.jersey.spi.resource.Singleton;

/**
 * Passes XML encoding of a RelationType to output stream or simply HTML.
 * 
 * @author taubertj
 * 
 */
@Provider
@Singleton
@Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
public class RelationTypeMessageBodyWriter extends Export implements
		MessageBodyWriter<RelationType> {

	public RelationTypeMessageBodyWriter() throws JAXBException {
		super();
	}

	/**
	 * Used to construct the URI linking services.
	 */
	@Context
	private UriInfo ui;

	@Override
	public long getSize(RelationType relationtype, Class<?> clazz, Type type,
			Annotation[] anno, MediaType mediatype) {
		return -1;
	}

	@Override
	public boolean isWriteable(Class<?> clazz, Type type, Annotation[] anno,
			MediaType mediatype) {
		// accept all subclasses of RelationType
		return RelationType.class.isAssignableFrom(clazz);
	}

	@Override
	public void writeTo(RelationType relationtype, Class<?> clazz, Type type,
			Annotation[] anno, MediaType mediatype,
			MultivaluedMap<String, Object> map, OutputStream outstream)
			throws IOException, WebApplicationException {

		// make sure there is a RelationType
		if (relationtype == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);

		// return XML encoding
		if (mediatype.toString().equals(MediaType.APPLICATION_XML)) {
			try {
				// new XML writer for output stream
				XMLStreamWriter2 xmlWriteStream = (XMLStreamWriter2) WstxOutputFactoryProvider.xmlw
						.createXMLStreamWriter(outstream, CharsetNames.CS_UTF8);

				// enable legacy mode for fully expanded meta data
				setLegacyMode(true);

				// export RelationType
				buildRelationType(xmlWriteStream, relationtype);

				// flush out all data
				xmlWriteStream.flush();
			} catch (XMLStreamException e) {
				throw new WebApplicationException(e,
						Response.Status.INTERNAL_SERVER_ERROR);
			}
		}

		// return HTML encoding
		else if (mediatype.toString().equals(MediaType.TEXT_HTML)) {
			String path = ui.getAbsolutePath().getPath();
			if (path.endsWith("/"))
				path = path.substring(0, path.length() - 1);
			String meta = path.substring(0, path.lastIndexOf("/"));
			meta = meta.substring(0, meta.lastIndexOf("/"));

			// simply write HTML code
			OutputStreamWriter writer = new OutputStreamWriter(outstream);
			writer.write("<h2>RelationType</h2>\n");
			writer.write("<table>\n");
			writer.write("<tr><td>id</td><td>");
			writer.write(relationtype.getId().toString());
			writer.write("</td></tr>\n");
			writer.write("<tr><td>fullname</td><td>");
			writer.write(relationtype.getFullname());
			writer.write("</td></tr>\n");
			writer.write("<tr><td>describtion</td><td>");
			writer.write(relationtype.getDescription());
			writer.write("</td></tr>\n");
			writer.write("<tr><td>inverse name</td><td>");
			writer.write(relationtype.getInverseName());
			writer.write("</td></tr>\n");

			// write out relation properties
			writer.write("<tr><td>is antisymmetric</td><td>");
			writer.write(relationtype.isAntisymmetric() + "</td></tr>\n");
			writer.write("<tr><td>is reflexive</td><td>");
			writer.write(relationtype.isReflexive() + "</td></tr>\n");
			writer.write("<tr><td>is symmetric</td><td>");
			writer.write(relationtype.isSymmetric() + "</td></tr>\n");
			writer.write("<tr><td>is transitiv</td><td>");
			writer.write(relationtype.isTransitiv() + "</td></tr>\n");

			// link specialisation of
			RelationType rt = relationtype.getSpecialisationOf();
			writer.write("<tr><td>specialisation of</td><td>");
			if (rt != null) {
				writer.write("<a href=\"");
				writer.write(meta + "/relationtypes/" + rt.getId());
				writer.write("\">");
				writer.write(rt.getId());
				writer.write("</a>");
			}
			writer.write("</td></tr>\n");

			writer.write("</table>\n");
			writer.write("<a href=\"");
			writer.write(path.substring(0, path.lastIndexOf("/")));
			writer.write("\">up</a>");
			writer.flush();
		}
	}
}
