/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siemens.vsst.ui.utils;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author michael
 */
public class FileExtensionFilter implements FilenameFilter
{
    String[] extensions;

    public FileExtensionFilter(String[] extensions)
    {
        this.extensions = extensions;
    }

    public boolean accept(File dir, String name)
    {
        for (String ext : this.extensions)
            if (name.endsWith(ext))
                return true;

        return false;
    }


}
