/*
 * This file is part of the RUNA WFE project.
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation; version 2.1 
 * of the License. 
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU Lesser General Public License for more details. 
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package ru.runa.wfe.commons;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import com.google.common.io.ByteStreams;

/**
 * Created on 01.12.2005
 * 
 */
public class IOCommons {

    private IOCommons() {
    }

    public static byte[] jarToBytesArray(File file) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JarOutputStream jarOutputStream = new JarOutputStream(byteArrayOutputStream, new Manifest());
        File[] files = listRecursive(file);
        int symbolsToSubstract = (file.isDirectory()) ? file.getAbsolutePath().length() + 1 : file.getAbsolutePath().length()
                - file.getName().length();
        for (int i = 0; i < files.length; i++) {
            JarEntry jarEntry = new JarEntry(files[i].getAbsolutePath().substring(symbolsToSubstract).replace("\\", "/"));
            jarEntry.setTime(files[i].lastModified());
            jarOutputStream.putNextEntry(jarEntry);
            FileInputStream fileInputStream = new FileInputStream(files[i]);
            ByteStreams.copy(fileInputStream, jarOutputStream);
            jarOutputStream.closeEntry();
        }
        jarOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Lists file in given directory and its subdirectories.
     * 
     * @param file
     * @return array of files
     */
    public static File[] listRecursive(File file) {
        File[] result = new File[0];
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                result = (File[]) ArraysCommons.sum(result, listRecursive(files[i]));
            }
        } else {
            result = new File[1];
            result[0] = file;
        }
        return result;
    }

    private static String logDirPath;

    public static String getLogDirPath() {
        if (logDirPath == null) {
            logDirPath = System.getProperty("jboss.server.log.dir");
            if (logDirPath == null) {
                logDirPath = "";
            }
        }
        return logDirPath;
    }

    private static String adminkitScriptsDirPath;

    public static String getInstallationDirPath() {
        return System.getProperty("jboss.home.dir");
    }

    public static String getDeploymentDirPath() {
        String serverDirName = System.getProperty("jboss.server.base.dir");
        boolean jboss7 = System.getProperty("jboss.modules.dir") != null;
        return jboss7 ? serverDirName + "/deployments" : serverDirName + "/" + System.getProperty("jboss.server.name") + "/deploy";
    }

    public static String getAdminkitScriptsDirPath() {
        if (adminkitScriptsDirPath == null) {
            adminkitScriptsDirPath = getInstallationDirPath() + "/adminkit/scripts/";
            if (adminkitScriptsDirPath == null) {
                adminkitScriptsDirPath = "";
            }
        }
        return adminkitScriptsDirPath;
    }

}
