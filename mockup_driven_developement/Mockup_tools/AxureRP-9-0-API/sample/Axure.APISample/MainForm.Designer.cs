namespace Axure.APISample {
    partial class MainForm {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing) {
            if (disposing && (components != null)) {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent() {
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.sourceFileTextBox = new System.Windows.Forms.TextBox();
            this.targetFileTextBox = new System.Windows.Forms.TextBox();
            this.selectSourceFileButton = new System.Windows.Forms.Button();
            this.selectTargetFileButton = new System.Windows.Forms.Button();
            this.generateButton = new System.Windows.Forms.Button();
            this.selectImageTargetButton = new System.Windows.Forms.Button();
            this.imageTargetTextBox = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.clearPathsLabel = new System.Windows.Forms.LinkLabel();
            this.selectAngularTargetButton = new System.Windows.Forms.Button();
            this.angularTargetBox = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.sourceXsltButton = new System.Windows.Forms.Button();
            this.sourceXslBox = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(12, 9);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(165, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Select the source and target files:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(12, 42);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(44, 13);
            this.label2.TabIndex = 1;
            this.label2.Text = "Source:";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(12, 92);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(66, 13);
            this.label3.TabIndex = 2;
            this.label3.Text = "XML Target:";
            // 
            // sourceFileTextBox
            // 
            this.sourceFileTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.sourceFileTextBox.Location = new System.Drawing.Point(105, 39);
            this.sourceFileTextBox.Name = "sourceFileTextBox";
            this.sourceFileTextBox.Size = new System.Drawing.Size(417, 20);
            this.sourceFileTextBox.TabIndex = 3;
            // 
            // targetFileTextBox
            // 
            this.targetFileTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.targetFileTextBox.Location = new System.Drawing.Point(105, 92);
            this.targetFileTextBox.Name = "targetFileTextBox";
            this.targetFileTextBox.Size = new System.Drawing.Size(417, 20);
            this.targetFileTextBox.TabIndex = 4;
            // 
            // selectSourceFileButton
            // 
            this.selectSourceFileButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.selectSourceFileButton.Location = new System.Drawing.Point(528, 37);
            this.selectSourceFileButton.Name = "selectSourceFileButton";
            this.selectSourceFileButton.Size = new System.Drawing.Size(34, 23);
            this.selectSourceFileButton.TabIndex = 5;
            this.selectSourceFileButton.Text = "...";
            this.selectSourceFileButton.UseVisualStyleBackColor = true;
            this.selectSourceFileButton.Click += new System.EventHandler(this.selectSourceFileButton_Click);
            // 
            // selectTargetFileButton
            // 
            this.selectTargetFileButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.selectTargetFileButton.Location = new System.Drawing.Point(528, 87);
            this.selectTargetFileButton.Name = "selectTargetFileButton";
            this.selectTargetFileButton.Size = new System.Drawing.Size(34, 23);
            this.selectTargetFileButton.TabIndex = 6;
            this.selectTargetFileButton.Text = "...";
            this.selectTargetFileButton.UseVisualStyleBackColor = true;
            this.selectTargetFileButton.Click += new System.EventHandler(this.selectTargetFileButton_Click);
            // 
            // generateButton
            // 
            this.generateButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.generateButton.Location = new System.Drawing.Point(487, 171);
            this.generateButton.Name = "generateButton";
            this.generateButton.Size = new System.Drawing.Size(75, 23);
            this.generateButton.TabIndex = 7;
            this.generateButton.Text = "Generate";
            this.generateButton.UseVisualStyleBackColor = true;
            this.generateButton.Click += new System.EventHandler(this.generateButton_Click);
            // 
            // selectImageTargetButton
            // 
            this.selectImageTargetButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.selectImageTargetButton.Location = new System.Drawing.Point(528, 113);
            this.selectImageTargetButton.Name = "selectImageTargetButton";
            this.selectImageTargetButton.Size = new System.Drawing.Size(34, 23);
            this.selectImageTargetButton.TabIndex = 10;
            this.selectImageTargetButton.Text = "...";
            this.selectImageTargetButton.UseVisualStyleBackColor = true;
            this.selectImageTargetButton.Click += new System.EventHandler(this.selectImageTargetButton_Click);
            // 
            // imageTargetTextBox
            // 
            this.imageTargetTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.imageTargetTextBox.Location = new System.Drawing.Point(105, 118);
            this.imageTargetTextBox.Name = "imageTargetTextBox";
            this.imageTargetTextBox.Size = new System.Drawing.Size(417, 20);
            this.imageTargetTextBox.TabIndex = 9;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(12, 118);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(73, 13);
            this.label4.TabIndex = 8;
            this.label4.Text = "Image Target:";
            // 
            // clearPathsLabel
            // 
            this.clearPathsLabel.AutoSize = true;
            this.clearPathsLabel.Location = new System.Drawing.Point(280, 176);
            this.clearPathsLabel.Name = "clearPathsLabel";
            this.clearPathsLabel.Size = new System.Drawing.Size(60, 13);
            this.clearPathsLabel.TabIndex = 11;
            this.clearPathsLabel.TabStop = true;
            this.clearPathsLabel.Text = "Clear paths";
            this.clearPathsLabel.LinkClicked += new System.Windows.Forms.LinkLabelLinkClickedEventHandler(this.clearPathsLabel_LinkClicked);
            // 
            // selectAngularTargetButton
            // 
            this.selectAngularTargetButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.selectAngularTargetButton.Location = new System.Drawing.Point(528, 142);
            this.selectAngularTargetButton.Name = "selectAngularTargetButton";
            this.selectAngularTargetButton.Size = new System.Drawing.Size(34, 23);
            this.selectAngularTargetButton.TabIndex = 14;
            this.selectAngularTargetButton.Text = "...";
            this.selectAngularTargetButton.UseVisualStyleBackColor = true;
            this.selectAngularTargetButton.Click += new System.EventHandler(this.selectAngularTargetButton_Click);
            // 
            // angularTargetBox
            // 
            this.angularTargetBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.angularTargetBox.Location = new System.Drawing.Point(105, 144);
            this.angularTargetBox.Name = "angularTargetBox";
            this.angularTargetBox.Size = new System.Drawing.Size(417, 20);
            this.angularTargetBox.TabIndex = 13;
            this.angularTargetBox.TextChanged += new System.EventHandler(this.angularTargetBox_TextChanged);
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(12, 147);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(80, 13);
            this.label5.TabIndex = 12;
            this.label5.Text = "Angular Target:";
            // 
            // button1
            // 
            this.sourceXsltButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.sourceXsltButton.Location = new System.Drawing.Point(528, 62);
            this.sourceXsltButton.Name = "button1";
            this.sourceXsltButton.Size = new System.Drawing.Size(34, 23);
            this.sourceXsltButton.TabIndex = 17;
            this.sourceXsltButton.Text = "...";
            this.sourceXsltButton.UseVisualStyleBackColor = true;
            this.sourceXsltButton.Click += new System.EventHandler(this.sourceXsltButtonButton_Click);
            // 
            // textBox1
            // 
            this.sourceXslBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.sourceXslBox.Location = new System.Drawing.Point(105, 64);
            this.sourceXslBox.Name = "textBox1";
            this.sourceXslBox.Size = new System.Drawing.Size(417, 20);
            this.sourceXslBox.TabIndex = 16;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(12, 67);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(44, 13);
            this.label6.TabIndex = 15;
            this.label6.Text = "Source XSLT:";
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(567, 235);
            this.Controls.Add(this.sourceXsltButton);
            this.Controls.Add(this.sourceXslBox);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.selectAngularTargetButton);
            this.Controls.Add(this.angularTargetBox);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.clearPathsLabel);
            this.Controls.Add(this.selectImageTargetButton);
            this.Controls.Add(this.imageTargetTextBox);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.generateButton);
            this.Controls.Add(this.selectTargetFileButton);
            this.Controls.Add(this.selectSourceFileButton);
            this.Controls.Add(this.targetFileTextBox);
            this.Controls.Add(this.sourceFileTextBox);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Name = "MainForm";
            this.Text = "Axure RP XML Generator";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox sourceFileTextBox;
        private System.Windows.Forms.TextBox targetFileTextBox;
        private System.Windows.Forms.Button selectSourceFileButton;
        private System.Windows.Forms.Button selectTargetFileButton;
        private System.Windows.Forms.Button generateButton;
        private System.Windows.Forms.Button selectImageTargetButton;
        private System.Windows.Forms.TextBox imageTargetTextBox;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.LinkLabel clearPathsLabel;
        private System.Windows.Forms.Button selectAngularTargetButton;
        private System.Windows.Forms.TextBox angularTargetBox;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Button sourceXsltButton;
        private System.Windows.Forms.TextBox sourceXslBox;
        private System.Windows.Forms.Label label6;
    }
}

