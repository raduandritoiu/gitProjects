/*
 * VSSTDataTransformToolView.java
 */
package siemens.vsst.ui;

import java.awt.FileDialog;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.util.Date;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import siemens.vsst.data.events.ProgressEvent;
import siemens.vsst.data.events.ProgressEventListener;
import siemens.vsst.data.io.DBFReader;
import siemens.vsst.data.io.SQLiteWriter;
import siemens.vsst.data.parsers.utils.StaticParameters;
import siemens.vsst.ui.utils.FileExtensionFilter;

/**
 * The application's main frame.
 */
public class VSSTDataTransformToolView extends FrameView
        implements ProgressEventListener
{
    private JFileChooser loadDialog;
    private JFileChooser saveDialog;
    private DBFReader reader;
    private SQLiteWriter writer;
    
    
    public VSSTDataTransformToolView(SingleFrameApplication app)
    {
        super(app);

        initComponents();

        this.getFrame().setResizable(false);
        this.getFrame().setIconImage(this.getResourceMap().getImageIcon("Application.appIcon").getImage());

		File dirPath;
		
        loadDialog  = new JFileChooser();//new FileDialog(this.getFrame(), "Select Any DBF File", FileDialog.LOAD);
        loadDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        loadDialog.setDialogType(JFileChooser.OPEN_DIALOG);
        if (!StaticParameters.BROWSE_FOLDERS) {
			dirPath = new File(StaticParameters.INPUT_FOLDER);
			loadDialog.setCurrentDirectory(dirPath);
		}
		
        //loadDialog.setFilenameFilter(new FileExtensionFilter(new String[] {".xml", ".dbf"}));

        /*
        saveDialog  = new FileDialog(getFrame(), "Save SQLite DB File", FileDialog.SAVE);
        saveDialog.setFile("*.db");
        saveDialog.setFilenameFilter(new FileExtensionFilter(new String[] {".db"}));
        */

        saveDialog  = new JFileChooser();
        saveDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
        saveDialog.setDialogType(JFileChooser.SAVE_DIALOG);
        saveDialog.setMultiSelectionEnabled(false);
        saveDialog.setFileFilter(new FileNameExtensionFilter("SQLite Files", "db"));
        if (!StaticParameters.BROWSE_FOLDERS) {
			dirPath = new File(StaticParameters.OUTPUT_FOLDER);
			saveDialog.setCurrentDirectory(dirPath);
		}

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                statusMessageLabel.setText("Ready.");
                statusProgressBar.setValue(0);
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++)
        {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        statusProgressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {

            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName))
                {
                    if (!busyIconTimer.isRunning())
                    {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    statusProgressBar.setVisible(true);
                    statusProgressBar.setIndeterminate(true);
                }
                else if ("done".equals(propertyName))
                {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    statusProgressBar.setVisible(false);
                    statusProgressBar.setValue(0);
                }
                else if ("message".equals(propertyName))
                {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                }
                else if ("progress".equals(propertyName))
                {
                    int value = (Integer) (evt.getNewValue());
                    statusProgressBar.setVisible(true);
                    statusProgressBar.setIndeterminate(false);
                    statusProgressBar.setValue(value);
                }
            }
        });
    }
	
	
    public void handleProgress(ProgressEvent event)
    {
        synchronized (statusProgressBar)
        {
            statusProgressBar.setMaximum((int) event.getMaxValue());
            statusProgressBar.setValue((int) event.getCurrentValue());
        }
    }
	
	
	
    @Action
    public void showAboutBox()
    {
        if (aboutBox == null)
        {
            JFrame mainFrame = VSSTDataTransformToolApp.getApplication().getMainFrame();
            aboutBox = new VSSTDataTransformToolAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        VSSTDataTransformToolApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        selectedFolderInput = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        convertButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        statusProgressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        selectedFolderInput.setEditable(false);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(siemens.vsst.ui.VSSTDataTransformToolApp.class).getContext().getResourceMap(VSSTDataTransformToolView.class);
        selectedFolderInput.setText(resourceMap.getString("selectedFolderInput.text")); // NOI18N
        selectedFolderInput.setName("selectedFolderInput"); // NOI18N

        browseButton.setText(resourceMap.getString("browseButton.text")); // NOI18N
        browseButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        browseButton.setName("browseButton"); // NOI18N
        browseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                browseButtonMouseClicked(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        convertButton.setText(resourceMap.getString("convertButton.text")); // NOI18N
        convertButton.setName("convertButton"); // NOI18N
        convertButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                convertButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(selectedFolderInput, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(140, Short.MAX_VALUE)
                .addComponent(convertButton)
                .addGap(132, 132, 132))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectedFolderInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseButton))
                .addGap(30, 30, 30)
                .addComponent(convertButton)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(siemens.vsst.ui.VSSTDataTransformToolApp.class).getContext().getActionMap(VSSTDataTransformToolView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setText(resourceMap.getString("statusMessageLabel.text")); // NOI18N
        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        statusProgressBar.setName("statusProgressBar"); // NOI18N
        statusProgressBar.setStringPainted(true);

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                .addComponent(statusProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(statusProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void browseButtonMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_browseButtonMouseClicked
    {//GEN-HEADEREND:event_browseButtonMouseClicked
        int result = loadDialog.showDialog(this.getFrame(), "Select Folder");
        if (result != JFileChooser.APPROVE_OPTION)
            return;
        
        selectedFolderInput.setText(loadDialog.getSelectedFile().getName());
        statusProgressBar.setVisible(true);
        statusProgressBar.setIndeterminate(true);
    }//GEN-LAST:event_browseButtonMouseClicked

    private void convertButtonMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_convertButtonMouseClicked
    {//GEN-HEADEREND:event_convertButtonMouseClicked
        if (loadDialog.getSelectedFile() == null)
        {
            JOptionPane.showConfirmDialog(this.getFrame(), "Please Select a DBF Folder First.", "Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }

        reader  = new DBFReader();
        writer  = new SQLiteWriter();
        writer.addProgressListener(this);

        statusProgressBar.setIndeterminate(false);

        int saveDiagResult = saveDialog.showDialog(this.getFrame(), "Save");
        
        if (saveDiagResult != JFileChooser.APPROVE_OPTION)
            return;

        this.getFrame().setEnabled(false);

        synchronized (busyIconTimer)
        {
            busyIconTimer.start();
        }
                
        new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    statusMessageLabel.setText("Reading DBF Files");
					
					System.out.println("************************* START READING **************************");
                    Date startDate = new Date();
					
                    reader.startImport(loadDialog.getSelectedFile().getAbsolutePath());
                    
                    Date endDate = new Date();
					long runningTime = (endDate.getTime() - startDate.getTime())/60000;
                    System.out.println("************************* END READING (ran " + runningTime + " minutes) **************************");
                }
                catch (Exception ex)
                {
                    statusMessageLabel.setText("Error Reading Data.");
                    ex.printStackTrace();

                    synchronized (busyIconTimer)
                    {
                        busyIconTimer.stop();
                        statusAnimationLabel.setIcon(idleIcon);
                    }

                    getFrame().setEnabled(true);

                    return;
                }

                if (reader.getProducts() != null)
                {
                    try
                    {
                        File targetFile = saveDialog.getSelectedFile();

                        if (!targetFile.getName().endsWith(".db"))
                            targetFile  = new File(targetFile.getPath() + ".db");


                        if (targetFile.exists())
                            targetFile.delete();

                        targetFile.createNewFile();

                        statusMessageLabel.setText("Processing " + reader.getProducts().size() + " Entries...");
                        
                        System.out.println("************************* START WRITING **************************");
	                    Date startDate = new Date();
                        
                        writer.writeData(targetFile, reader);
                        
		                Date endDate = new Date();
						long runningTime = (endDate.getTime() - startDate.getTime())/1000;
                        System.out.println("************************* END WRITING (ran " + runningTime + " seconds) **************************");
                    }
                    catch (Exception sEx)
                    {
                        statusMessageLabel.setText("Error Saving SQL Lite");

                        synchronized (busyIconTimer)
                        {
                            busyIconTimer.stop();
                            statusAnimationLabel.setIcon(idleIcon);
                        }

                        getFrame().setEnabled(true);
                        return;
                    }

                }

                statusMessageLabel.setText("Conversion Complete!");
                
                synchronized (messageTimer)
                {
                    messageTimer.restart();
                }

                synchronized (busyIconTimer)
                {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                }

                getFrame().setEnabled(true);
            }
        }).start();
        

    }//GEN-LAST:event_convertButtonMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JButton convertButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JTextField selectedFolderInput;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JProgressBar statusProgressBar;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
}
