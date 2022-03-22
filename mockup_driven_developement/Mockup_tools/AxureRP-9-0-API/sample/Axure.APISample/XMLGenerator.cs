using System.Collections.Generic;
using System.Xml;

using Axure.Document;
using Axure.Document.Widgets;

namespace Axure.APISample {
    public class XmlGenerator {
        private readonly RPDocument _Document;
        private readonly XmlWriter _Writer;

        public XmlGenerator(RPDocument doc, XmlWriter writer) {
            _Document = doc;
            _Writer = writer;
        }

        public void Generate() {
            _Writer.WriteStartDocument();

            GenerateDocument(_Document);

            _Writer.WriteEndDocument();
        }

        private void GenerateButton(RPButton item) {
            _Writer.WriteStartElement("Button");

            _Writer.WriteStartElement("Text");
            _Writer.WriteString(item.Text);
            _Writer.WriteEndElement();

            GenerateWidget(item);
            _Writer.WriteEndElement();
        }

        private void GenerateCheckbox(RPCheckbox item) {
            _Writer.WriteStartElement("Checkbox");

            _Writer.WriteStartElement("IsChecked");
            _Writer.WriteString(item.IsChecked.ToString());
            _Writer.WriteEndElement();

            GenerateWidget(item);
            _Writer.WriteEndElement();
        }

        private void GenerateConnector(RPConnector item) {
            _Writer.WriteStartElement("Connector");


            if (item.FromWidget != null) {
                _Writer.WriteStartElement("FromWidgetName");
                _Writer.WriteString(item.FromWidget.Name);
                _Writer.WriteEndElement();
            }

            _Writer.WriteStartElement("FromLocation");
            GeneratePoint(item.FromLocation);
            _Writer.WriteEndElement();

            _Writer.WriteStartElement("FromConnectionPoint");
            _Writer.WriteString(item.FromConnectionPoint.ToString());
            _Writer.WriteEndElement();


            if (item.ToWidget != null) {
                _Writer.WriteStartElement("ToWidgetName");
                _Writer.WriteString(item.ToWidget.Name);
                _Writer.WriteEndElement();
            }

            _Writer.WriteStartElement("ToLocation");
            GeneratePoint(item.ToLocation);
            _Writer.WriteEndElement();

            _Writer.WriteStartElement("ToConnectionPoint");
            _Writer.WriteString(item.ToConnectionPoint.ToString());
            _Writer.WriteEndElement();

            GenerateWidget(item);
            _Writer.WriteEndElement();
        }

        private void GenerateDropDownList(RPDropDownList item) {
            _Writer.WriteStartElement("DropDownList");

            _Writer.WriteStartElement("Options");

            // here we store the selected index so we can write an element that looks like:
            // <Option Selected="True">Text goes here...</Option> attribute if the item is selected
            int selIndex = item.SelectedIndex;
            int index = 0;
            foreach (string opt in item.Options) {
                _Writer.WriteStartElement("Option");

                // if the current index equals the selected index, write the "Selected='True'" attribute
                if (index == selIndex) {
                    _Writer.WriteStartAttribute("Selected");
                    _Writer.WriteString("True");
                    _Writer.WriteEndAttribute();
                }

                _Writer.WriteString(opt);
                _Writer.WriteEndElement();
                index++;
            }
            
            _Writer.WriteEndElement();


            GenerateWidget(item);
            _Writer.WriteEndElement();
        }

        private void GenerateDynamicPanel(RPDynamicPanel item) {
            _Writer.WriteStartElement("DynamicPanel");

            _Writer.WriteStartElement("PanelStates");
            foreach (RPPanelDiagram pd in item.PanelStates) {
                GeneratePanelDiagram(pd);
            }
            _Writer.WriteEndElement();


            GenerateWidget(item);
            _Writer.WriteEndElement();
        }

        private void GenerateImage(RPImage item) {
            _Writer.WriteStartElement("Image");

            GenerateWidget(item);
            _Writer.WriteEndElement();
        }

        private void GenerateImageMapRegion(RPImageMapRegion item) {
            _Writer.WriteStartElement("ImageMapRegion");

            GenerateWidget(item);
            _Writer.WriteEndElement();
        }

        private void GenerateInlineFrame(RPInlineFrame item) {
            _Writer.WriteStartElement("InlineFrame");

            _Writer.WriteStartElement("FrameTarget");
            GenerateTarget(item.Target);
            _Writer.WriteEndElement();

            GenerateWidget(item);
            _Writer.WriteEndElement();
        }

        private void GenerateListbox(RPListbox item) {
            _Writer.WriteStartElement("Listbox");

            // like for the drop down list we store the selected indicies so we can write an element that looks like:
            // <Option Selected="True">Text goes here...</Option> attribute if the item is selected
            var selectedIndicies = new List<int>(item.SelectedIndicies);
            var index = 0;
            foreach (string opt in item.Options) {
                _Writer.WriteStartElement("Option");

                // if the current index equals the selected index, write the "Selected='True'" attribute
                if (selectedIndicies.Contains(index)) {
                    _Writer.WriteStartAttribute("Selected");
                    _Writer.WriteString("True");
                    _Writer.WriteEndAttribute();
                }

                _Writer.WriteString(opt);
                _Writer.WriteEndElement();
                index++;
            }

            GenerateWidget(item);
            _Writer.WriteEndElement();
        }

        private void GenerateMasterInstance(RPMasterInstance item) {
            _Writer.WriteStartElement("MasterInstance");

            _Writer.WriteStartElement("MasterName");
            _Writer.WriteString(item.Master.PackageInfo.PackageName);
            _Writer.WriteEndElement();

            GenerateWidget(item);
            _Writer.WriteEndElement();
        }

        private void GenerateRadioButton(RPRadioButton item) {
            _Writer.WriteStartElement("RadioButton");

            _Writer.WriteStartElement("IsChecked");
            _Writer.WriteString(item.IsChecked.ToString());
            _Writer.WriteEndElement();

            _Writer.WriteStartElement("Group");
            _Writer.WriteStartAttribute("Name");
            _Writer.WriteString(item.Group);
            _Writer.WriteEndAttribute();
            _Writer.WriteEndElement();

            GenerateWidget(item);
            _Writer.WriteEndElement();
        }

        private void GenerateShape(RPShape item) {
            _Writer.WriteStartElement("Shape");

            _Writer.WriteStartElement("ShapeType");
            _Writer.WriteString(item.ShapeType.ToString());
            _Writer.WriteEndElement();

            GenerateWidget(item);
            _Writer.WriteEndElement();
        }

        private void GenerateTable(RPTable item) {
            _Writer.WriteStartElement("Table");

            _Writer.WriteStartElement("Rows");
            foreach (float rowHeight in item.RowHeights) {
                _Writer.WriteStartElement("Row");
                _Writer.WriteStartAttribute("Height");
                _Writer.WriteString(rowHeight.ToString());
                _Writer.WriteEndAttribute();
                _Writer.WriteEndElement();
            }
            _Writer.WriteEndElement();

            _Writer.WriteStartElement("Columns");
            foreach (float rowWidth in item.RowHeights) {
                _Writer.WriteStartElement("Column");
                _Writer.WriteStartAttribute("Width");
                _Writer.WriteString(rowWidth.ToString());
                _Writer.WriteEndAttribute();
                _Writer.WriteEndElement();
            }
            _Writer.WriteEndElement();

            GenerateWidget(item);
            _Writer.WriteEndElement();
        }

        private void GenerateTableCell(RPTableCell item) {
            _Writer.WriteStartElement("TableCell");

            _Writer.WriteStartElement("Row");
            _Writer.WriteString(item.Row.ToString());
            _Writer.WriteEndElement();

            _Writer.WriteStartElement("Column");
            _Writer.WriteString(item.Column.ToString());
            _Writer.WriteEndElement();

            GenerateWidget(item);
            _Writer.WriteEndElement();
        }

        private void GenerateTextArea(RPTextArea item) {
            _Writer.WriteStartElement("TextArea");
            GenerateWidget(item);
            _Writer.WriteEndElement();
        }

        private void GenerateTextbox(RPTextbox item) {
            _Writer.WriteStartElement("Textbox");
            GenerateWidget(item);
            _Writer.WriteEndElement();
        }

        private void GenerateAnnotation(RPAnnotation item) {
            _Writer.WriteStartElement("Annotation");

            _Writer.WriteStartElement("Properties");
            foreach (string propertyName in item.PropertyNames) {
                string propertyValue  = item.GetPropertyValue(propertyName);
                
                // only write the properties that have a value assigned.
                if (propertyValue.Length > 0) {
                    _Writer.WriteStartElement("PropertyValue");
                    _Writer.WriteStartAttribute("PropertyName");
                    _Writer.WriteString(propertyName);
                    _Writer.WriteEndAttribute();

                    _Writer.WriteString(propertyValue);
                    _Writer.WriteEndElement();
                }
            }
            _Writer.WriteEndElement();

            _Writer.WriteEndElement();
        }

        private void GenerateDiagram(RPDiagram item, bool writeDiagTag) {
            if (writeDiagTag) _Writer.WriteStartElement("Diagram");

            _Writer.WriteStartElement("Widgets");
            foreach (RPWidget widget in item.Widgets) {
                GenerateProperWidget(widget);
            }
            _Writer.WriteEndElement();

            if (writeDiagTag) _Writer.WriteEndElement();
        }

        private void GenerateProperWidget(RPWidget widget) {
            if (widget is RPButton) GenerateButton((RPButton)widget);
            else if (widget is RPCheckbox) GenerateCheckbox((RPCheckbox)widget);
            else if (widget is RPConnector) GenerateConnector((RPConnector)widget);
            else if (widget is RPDropDownList) GenerateDropDownList((RPDropDownList)widget);
            else if (widget is RPDynamicPanel) GenerateDynamicPanel((RPDynamicPanel)widget);
            else if (widget is RPImage) GenerateImage((RPImage)widget);
            else if (widget is RPImageMapRegion) GenerateImageMapRegion((RPImageMapRegion)widget);
            else if (widget is RPInlineFrame) GenerateInlineFrame((RPInlineFrame)widget);
            else if (widget is RPListbox) GenerateListbox((RPListbox)widget);
            else if (widget is RPMasterInstance) GenerateMasterInstance((RPMasterInstance)widget);
            else if (widget is RPRadioButton) GenerateRadioButton((RPRadioButton)widget);
            else if (widget is RPShape) GenerateShape((RPShape)widget);
            else if (widget is RPTable) GenerateTable((RPTable)widget);
            else if (widget is RPTableCell) GenerateTableCell((RPTableCell)widget);
            else if (widget is RPTextArea) GenerateTextArea((RPTextArea)widget);
            else if (widget is RPTextbox) GenerateTextbox((RPTextbox)widget);
        }

        private void GenerateDiagramPackage(RPDiagramPackage item) {
            GenerateDiagram(item.Diagram, true);
            GeneratePackageInfo(item.PackageInfo);

            GeneratePackage(item);
        }

        private void GenerateDocument(RPDocument item) {
            _Writer.WriteStartElement("Document");

            _Writer.WriteStartElement("Sitemap");
            GenerateTreeMap(item.Sitemap);
            _Writer.WriteEndElement();

            _Writer.WriteStartElement("Mastermap");
            GenerateTreeMap(item.Mastermap);
            _Writer.WriteEndElement();

            // all pages
            _Writer.WriteStartElement("Pages");
            foreach (RPPackageHandle pageHandle in GetDepthFirstPackageHandles(item.Sitemap)) {
                RPPage page = (RPPage) item.LoadPackage(pageHandle);
                if (page != null) GeneratePage(page);
            }
            _Writer.WriteEndElement();

            // all masters
            _Writer.WriteStartElement("Masters");
            foreach (RPPackageHandle masterHandle in GetDepthFirstPackageHandles(item.Mastermap)) {
                RPMaster master = (RPMaster) item.LoadPackage(masterHandle);
                GenerateMaster(master);
            }
            _Writer.WriteEndElement();


            _Writer.WriteEndElement();
        }

        private void GenerateMaster(RPMaster item) {
            _Writer.WriteStartElement("Master");

            GenerateDiagramPackage(item);

            _Writer.WriteEndElement();
        }

        private void GeneratePackage(RPPackage item) {
        }

        private void GeneratePackageInfo(RPPackageInfo item) {
            _Writer.WriteStartElement("PackageInfo");

            _Writer.WriteStartElement("Name");
            _Writer.WriteString(item.PackageName);
            _Writer.WriteEndElement();

            _Writer.WriteEndElement();
        }

        private void GeneratePage(RPPage item) {
            _Writer.WriteStartElement("Page");

            if (item.HasInteraction) {
                GenerateInteraction(item.Interaction);
            }

            GenerateDiagramPackage(item);

            _Writer.WriteEndElement();
        }

        private void GeneratePanelDiagram(RPPanelDiagram item) {
            _Writer.WriteStartElement("PanelDiagram");

            GenerateDiagram(item, false);

            _Writer.WriteEndElement();
        }

        private void GenerateTreeMap(RPTreeMap item) {
            _Writer.WriteStartElement("TreeMap");
            
            _Writer.WriteStartElement("RootNodes");
            foreach (RPTreeMapNode node in item.RootNodes) {
                GenerateTreeMapNode(node);
            }
            _Writer.WriteEndElement();

            _Writer.WriteEndElement();
        }

        private void GenerateTreeMapNode(RPTreeMapNode item) {
            _Writer.WriteStartElement("TreeMapNode");

            // Node Type
            _Writer.WriteStartElement("NodeType");
            _Writer.WriteString(item.NodeType.ToString());
            _Writer.WriteEndElement();

            // Value
            _Writer.WriteStartElement("NodeValue");
            if (item.NodeType == RPTreeMapNodeType.Folder) {
                _Writer.WriteString((string)item.NodeValue);
            }
            else if (item.NodeType == RPTreeMapNodeType.PackageHandle) {
                //GeneratePackageId((RPPackageId)item.NodeValue);
                var pkgInfo = _Document.GetPackageInfo((RPPackageHandle) item.NodeValue);
                _Writer.WriteString(pkgInfo.PackageName);
            }
            _Writer.WriteEndElement();

            // Child Nodes
            _Writer.WriteStartElement("ChildNodes");
            foreach (RPTreeMapNode node in item.ChildNodes) {
                GenerateTreeMapNode(node);
            }
            _Writer.WriteEndElement();

            _Writer.WriteEndElement();
        }

        private void GenerateInteraction(RPInteraction item) {
            _Writer.WriteStartElement("Interaction");

            _Writer.WriteStartElement("Events");
            foreach (RPEvent e in item.Events) {
                GenerateEvent(e);
            }
            _Writer.WriteEndElement();

            _Writer.WriteEndElement();
        }

        private void GenerateEvent(RPEvent item) {
            _Writer.WriteStartElement("Event");
            _Writer.WriteStartAttribute("EventType");
            _Writer.WriteString(item.EventType.ToString());
            _Writer.WriteEndAttribute();

            _Writer.WriteStartElement("EventDescription");
            _Writer.WriteString(item.EventDescription);
            _Writer.WriteEndElement();

            _Writer.WriteEndElement();
        }

        private void GenerateWidget(RPWidget item) {
            // Annotation -- without a surrounding tag.
            if (item.IsAnnotated) GenerateAnnotation(item.Annotation);

            int footNum = item.FootnoteNumber;
            if (footNum >= 0) {
                _Writer.WriteStartElement("FootnoteNumber");
                _Writer.WriteString(footNum.ToString());
                _Writer.WriteEndElement();
            }

            if (item.HasInteraction) {
                GenerateInteraction(item.Interaction);
            }

            _Writer.WriteStartElement("Name");
            _Writer.WriteString(item.Name.ToString());
            _Writer.WriteEndElement();

            _Writer.WriteStartElement("Rectangle");
            GenerateRectangle(item.Rectangle);
            _Writer.WriteEndElement();

            _Writer.WriteStartElement("Bold");
            _Writer.WriteString(item.Bold.ToString());
            _Writer.WriteEndElement();

            _Writer.WriteStartElement("BorderColor");
            _Writer.WriteString(item.LineColor.ToString());
            _Writer.WriteEndElement();

            _Writer.WriteStartElement("BorderWidth");
            _Writer.WriteString(item.LineWidth.ToString());
            _Writer.WriteEndElement();

            _Writer.WriteStartElement("FillColor");
            _Writer.WriteString(item.FillColor.ToString());
            _Writer.WriteEndElement();

            _Writer.WriteStartElement("FontName");
            _Writer.WriteString(item.FontName.ToString());
            _Writer.WriteEndElement();

            _Writer.WriteStartElement("FontSize");
            _Writer.WriteString(item.FontSize.ToString());
            _Writer.WriteEndElement();

            _Writer.WriteStartElement("ForeColor");
            _Writer.WriteString(item.ForeColor.ToString());
            _Writer.WriteEndElement();

            _Writer.WriteStartElement("HorizontalAlignment");
            _Writer.WriteString(item.HorizontalAlignment.ToString());
            _Writer.WriteEndElement();

            _Writer.WriteStartElement("Italic");
            _Writer.WriteString(item.Italic.ToString());
            _Writer.WriteEndElement();

            _Writer.WriteStartElement("Underline");
            _Writer.WriteString(item.Underline.ToString());
            _Writer.WriteEndElement();

            _Writer.WriteStartElement("VerticalAlignment");
            _Writer.WriteString(item.VerticalAlignment.ToString());
            _Writer.WriteEndElement();

            if(item is RPTextWidget textItem) {
                _Writer.WriteStartElement("Text");
                _Writer.WriteString(textItem.Text);
                _Writer.WriteEndElement();
            }

            if (item.HasChildren) {
                _Writer.WriteStartElement("Widgets");
                foreach (RPWidget widget in item.Widgets) {
                    GenerateProperWidget(widget);
                }
                _Writer.WriteEndElement();
            }

        }

        private void GenerateTarget(RPTarget target) {
            _Writer.WriteStartElement("Target");

            _Writer.WriteStartElement("TargetType");
            _Writer.WriteString(target.TargetType.ToString());
            _Writer.WriteEndElement();

            if (target.TargetType == RPTargetType.WebUrl) {
                _Writer.WriteStartElement("TargetUrl");
                _Writer.WriteString(target.TargetUrl);
                _Writer.WriteEndElement();
            } else if (target.TargetType == RPTargetType.Page) {
                _Writer.WriteStartElement("TargetPageName");
                var pkgHandle = target.TargetPageHandle;
                var pkgInfo = _Document.GetPackageInfo(pkgHandle);
                _Writer.WriteString(pkgInfo.PackageName);
                _Writer.WriteEndElement();
            }

            
            _Writer.WriteEndElement();
        }

        private void GeneratePoint(RPPoint p) {
            _Writer.WriteStartElement("Point");

            _Writer.WriteStartAttribute("X");
            _Writer.WriteString(p.X.ToString());
            _Writer.WriteEndAttribute();

            _Writer.WriteStartAttribute("Y");
            _Writer.WriteString(p.Y.ToString());
            _Writer.WriteEndAttribute();

            _Writer.WriteEndElement();
        }

        private void GenerateRectangle(RPRectangle rect) {
            _Writer.WriteStartElement("Rectangle");

            _Writer.WriteStartAttribute("X");
            _Writer.WriteString(rect.X.ToString());
            _Writer.WriteEndAttribute();

            _Writer.WriteStartAttribute("Y");
            _Writer.WriteString(rect.Y.ToString());
            _Writer.WriteEndAttribute();

            _Writer.WriteStartAttribute("Width");
            _Writer.WriteString(rect.Width.ToString());
            _Writer.WriteEndAttribute();

            _Writer.WriteStartAttribute("Height");
            _Writer.WriteString(rect.Height.ToString());
            _Writer.WriteEndAttribute();

            _Writer.WriteEndElement();
        }

        private List<RPPackageHandle> GetDepthFirstPackageHandles(RPTreeMap tm) {
            List<RPPackageHandle> returnVal = new List<RPPackageHandle>();
            foreach (RPTreeMapNode node in tm.RootNodes) {
                GetDepthFirstPackageHandlesHelper(node, returnVal);
            }
            return returnVal;
        }

        private void GetDepthFirstPackageHandlesHelper(RPTreeMapNode node, List<RPPackageHandle> ids) {
            if (node.NodeType == RPTreeMapNodeType.PackageHandle) {
                ids.Add((RPPackageHandle)node.NodeValue);
            }

            foreach (RPTreeMapNode child in node.ChildNodes) {
                GetDepthFirstPackageHandlesHelper(child, ids);
            }
        }
    }
}
