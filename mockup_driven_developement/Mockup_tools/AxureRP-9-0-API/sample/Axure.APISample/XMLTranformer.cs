using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Xml;
using System.Xml.Xsl;
using Axure.Document;
using Axure.Document.Widgets;

namespace Axure.APISample {
    public class XMLTransformer {
        private readonly RPDocument _Document;
        private string inputPath,xsltPath, exportPath;
        private readonly string XSLT_XML_TO_HTML = "xmltohtml.xsl";
        private readonly string XSLT_XML_TO_TS = "xmltots.xsl";
        private readonly string XSLT_XML_TO_SCSS = "xmltoscss.xsl";
        private readonly string SEPARATOR = "\\";

        public XMLTransformer(RPDocument doc, string pathXML, string xslPath, string outputPath) {
            _Document = doc;
            inputPath = pathXML;
            xsltPath = xslPath;
            exportPath = outputPath;
        }

        public void Transform() {
            string result;
            string inputXML="";

            XmlDocument doc = new XmlDocument();
            doc.Load(inputPath);
            var sw = new StringWriter();
            XmlTextWriter tx = new XmlTextWriter(sw);
            doc.WriteTo(tx);
            inputXML = sw.ToString();

            //Transform XML to HTML
            result = TransformXMLToComponent(inputXML, XSLT_XML_TO_HTML);
            WriteComponent(exportPath+"\\component.html", result);

            //Transform XML to TS
            //result = TransformXMLToComponent(inputXML, XSLT_XML_TO_TS);
            //WriteComponent(exportPath + "\\angular_component.ts", result);

            //Transform XML to (S-) CSS
            //result = TransformXMLToComponent(inputXML, XSLT_XML_TO_SCSS);
            //WriteComponent(exportPath + "\\angular_component.scss", result);
        }


        public string TransformXMLToComponent(string inputXml, string xsltString)
        {
            XslCompiledTransform transform = new XslCompiledTransform();
            if(!xsltPath.EndsWith("\\"))
            {
                xsltPath = xsltPath + SEPARATOR;
            }

            transform.Load(xsltPath + xsltString);  

            StringWriter results = new StringWriter();
            TextReader xmlread = new StringReader(inputXml);
            using (XmlReader reader = XmlReader.Create(xmlread))
            {
                transform.Transform(reader, null, results);
            }
            return results.ToString();
        }

        public void WriteComponent(string outputPath, string outputString)
        {
            File.WriteAllText(outputPath, outputString);
        }

    }
}
