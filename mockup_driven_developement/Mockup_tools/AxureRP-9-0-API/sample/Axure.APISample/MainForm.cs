using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Xml;

using Axure.Document;

namespace Axure.APISample {
    public partial class MainForm : Form {
        public MainForm() {
            InitializeComponent();
        }

        private void selectSourceFileButton_Click(object sender, EventArgs e) {
            var ofd = new OpenFileDialog {
                Filter = @"Axure RP Files (*.rp)|*.rp"
            };
            if (ofd.ShowDialog() == DialogResult.OK) {
                sourceFileTextBox.Text = ofd.FileName;

                var fileInfo = new FileInfo(ofd.FileName);
                if (string.IsNullOrWhiteSpace(targetFileTextBox.Text)) {
                    targetFileTextBox.Text = fileInfo.DirectoryName + "\\" + fileInfo.Name.Replace(fileInfo.Extension, ".xml");
                }
                if (string.IsNullOrWhiteSpace(imageTargetTextBox.Text)) {
                    imageTargetTextBox.Text = fileInfo.DirectoryName + "\\" + fileInfo.Name.Replace(fileInfo.Extension, "")+"-images";
                }

                if (string.IsNullOrWhiteSpace(angularTargetBox.Text))
                {
                    angularTargetBox.Text = fileInfo.DirectoryName + "\\" + fileInfo.Name.Replace(fileInfo.Extension, "") + "-html";
                }

                if (string.IsNullOrWhiteSpace(sourceXslBox.Text))
                {
                    sourceXslBox.Text = fileInfo.DirectoryName + "\\XSL\\";
                }
            }
        }

        private void selectTargetFileButton_Click(object sender, EventArgs e) {
            var sfd = new SaveFileDialog {
                Filter = @"XML File (*.xml)|*.xml"
            };
            if (sfd.ShowDialog() == DialogResult.OK) targetFileTextBox.Text = sfd.FileName;
        }

        private void selectImageTargetButton_Click(object sender, EventArgs e) {
            var folderDialog = new FolderBrowserDialog();
            if(folderDialog.ShowDialog() == DialogResult.OK) imageTargetTextBox.Text = folderDialog.SelectedPath;
        }

        private void generateButton_Click(object sender, EventArgs e) {
            if(!Directory.Exists(imageTargetTextBox.Text)) {
                var result = MessageBox.Show(imageTargetTextBox.Text + "\ndoesn't exist. Create?", "", MessageBoxButtons.YesNo);
                if (result != DialogResult.Yes) return;
                Directory.CreateDirectory(imageTargetTextBox.Text);
            }

            var doc = RPDocument.Load(sourceFileTextBox.Text);
            if(targetFileTextBox.Text.Trim().Length > 0) {
                var writer = new XmlTextWriter(targetFileTextBox.Text, Encoding.UTF8) {
                    Formatting = Formatting.Indented,
                    Indentation = 2
                };

                var gen = new XmlGenerator(doc, writer);
                gen.Generate();

                writer.Close();

                var trans = new XMLTransformer(doc, targetFileTextBox.Text, sourceXslBox.Text, angularTargetBox.Text);
                trans.Transform();
            }

            if(imageTargetTextBox.Text.Trim().Length > 0) {
                var packageHandles = GetAllPackageHandles(doc.Sitemap.RootNodes).ToList();
                var currentPageNumber = 1;
                foreach(var pkgHandle in packageHandles) {
                    //File.OpenWrite()
                    var package = (RPDiagramPackage)doc.LoadPackage(pkgHandle);
                    if (package == null) continue;

                    var diagramFilename = String.Format(imageTargetTextBox.Text +
                        String.Format(@"\Screenshot{0:000}.jpg", currentPageNumber));
                    var diagramStream = File.Create(diagramFilename);
                    package.Diagram.RenderScreenshot(diagramStream, RPImageFormat.Jpeg);
                    diagramStream.Close();
                    diagramStream.Dispose();

                    var widget = package.Diagram.Widgets.FirstOrDefault();
                    if(widget != null) {
                        var widgetFilename = String.Format(imageTargetTextBox.Text +
                            String.Format(@"\widget{0:000}.jpg", currentPageNumber));
                        var widgetStream = File.Create(widgetFilename);
                        widget.GetScreenshot(widgetStream, RPImageFormat.Jpeg);
                        widgetStream.Close();
                        widgetStream.Dispose();
                    }


                    package.Dispose();
                    currentPageNumber++;
                }
            }

            MessageBox.Show("Generation completed.");
        }

        

        /// <summary>
        /// Recursively gets all of the package handles referred to by a list of tree map nodes
        /// </summary>
        /// <param name="nodes"></param>
        /// <returns></returns>
        private IEnumerable<RPPackageHandle> GetAllPackageHandles(IEnumerable<RPTreeMapNode> nodes) {
            foreach(var node in nodes) {
                // we can have folders or packages, only get the folders
                var nodeValue = node.NodeValue;
                if (nodeValue is RPPackageHandle) yield return (RPPackageHandle)nodeValue;

                foreach(var subHandle in GetAllPackageHandles(node.ChildNodes)) yield return subHandle;
            }
        }

        private void clearPathsLabel_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e) {
            sourceFileTextBox.Text = targetFileTextBox.Text = imageTargetTextBox.Text = angularTargetBox.Text = sourceXslBox.Text = "";
        }

        private void MainForm_Load(object sender, EventArgs e)
        {

        }

        private void selectAngularTargetButton_Click(object sender, EventArgs e)
        {
            var folderDialog = new FolderBrowserDialog();
            if (folderDialog.ShowDialog() == DialogResult.OK) angularTargetBox.Text = folderDialog.SelectedPath;
        }

        private void angularTargetBox_TextChanged(object sender, EventArgs e)
        {

        }

        private void sourceXsltButtonButton_Click(object sender, EventArgs e)
        {
            var folderDialog = new FolderBrowserDialog();
            if (folderDialog.ShowDialog() == DialogResult.OK) sourceXslBox.Text = folderDialog.SelectedPath;
        }

    }
}