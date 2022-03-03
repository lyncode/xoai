/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="client://java.sun.com/xml/jaxb">client://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.27 at 01:16:00 PM WEST 
//

package io.gdcc.xoai.model.xoai;

import io.gdcc.xoai.xmlio.XmlReader;
import io.gdcc.xoai.xmlio.XmlWritable;
import io.gdcc.xoai.xmlio.XmlWriter;
import io.gdcc.xoai.xmlio.exceptions.XmlReaderException;
import io.gdcc.xoai.xmlio.exceptions.XmlWriteException;

import javax.xml.stream.XMLStreamException;
import java.util.ArrayList;
import java.util.List;

import static io.gdcc.xoai.xmlio.matchers.AttributeMatchers.attributeName;
import static io.gdcc.xoai.xmlio.matchers.QNameMatchers.localPart;
import static io.gdcc.xoai.xmlio.matchers.XmlEventMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.AllOf.allOf;

public class Element implements XmlWritable {
    public static Element parse(XmlReader reader) throws XmlReaderException {
        if (!reader.current(allOf(aStartElement(), elementName(localPart(equalTo("element"))))))
            throw new XmlReaderException("Invalid XML. Expecting entity 'element'");

        if (!reader.hasAttribute(attributeName(localPart(equalTo("name")))))
            throw new XmlReaderException("Invalid XML. Element entities must have a name");

        Element element = new Element(reader.getAttributeValue(localPart(equalTo("name"))));


        while (reader.next(anElement()).current(aStartElement())) {
            if (reader.current(elementName(localPart(equalTo("element"))))) // Nested element
                element.withElement(parse(reader));
            else if (reader.current(elementName(localPart(equalTo("field")))))
                element.withField(Field.parse(reader));
            else throw new XmlReaderException("Unexpected element");
        }

        if (!reader.current(allOf(anEndElement(), elementName(localPart(equalTo("element"))))))
            throw new XmlReaderException("Invalid XML. Expecting end of entity 'element'");

        return element;
    }

    protected List<Field> fields = new ArrayList<Field>();
    protected String name;
    protected List<Element> elements = new ArrayList<Element>();

    public Element(String name) {
        this.name = name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public String getName() {
        return name;
    }

    public Element withName(String value) {
        this.name = value;
        return this;
    }

    public Element withField (Field field) {
        this.fields.add(field);
        return this;
    }
    public Element withField (String name, String value) {
        this.fields.add(new Field(value, name));
        return this;
    }

    public List<Element> getElements() {
        return this.elements;
    }

    public Element withElement(Element element) {
        this.elements.add(element);
        return this;
    }

    @Override
    public void write(XmlWriter writer) throws XmlWriteException {
        try {
            if (this.name != null)
                writer.writeAttribute("name", this.getName());

            for (Field field : this.getFields()) {
                writer.writeStartElement("field");
                field.write(writer);
                writer.writeEndElement();
            }
            for (Element element : this.getElements()) {
                writer.writeStartElement("element");
                element.write(writer);
                writer.writeEndElement();
            }

        } catch (XMLStreamException e) {
            throw new XmlWriteException(e);
        }
    }
}
