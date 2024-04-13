package com.dskbot.utilities;
/*
*
* @Author: Danukaji Hansanath
* @GitHub: https://github.com/Danukaji-Hansanath
* @Project: DSKBot
* @Class: Utilities
*
* */
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Utilities {


    //file delete method
    public String deleteFile(String path) {
        String msg = "Does not Delete";
        File file = new File(path);
        if (file.exists()) {
            file.delete();
            msg = "Deleted file" + path;
        }
        return msg;
    }



    //delete folder method
    public String deleteDir(File file) {
        String msg = "Does not Delete";
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        msg = "Deleted folder";
        file.delete();
        return msg;
    }



    //get all folders in a directory downloaded files
    public List<String> getFolders(String dir) {
        List<String> folders = new ArrayList<>();
        File folDir = new File(dir);
        if (folDir.isDirectory()) {
            int fdCount = countFolder(dir);
            if (fdCount > 0) {
                File[] files = folDir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isDirectory()) {
                            getFolders(file.toString());
                            folders.add(file.toString());
                        }
                    }
                }
            }
        }
        return folders;
    }



    //get all files in a directory
    public List <String> getFiles(List <String> folders){
        List <String> filePath = new ArrayList<>();
        for(String folder:folders){
            File rFolder = new File(folder);
            if(rFolder.isDirectory()){
                File[] files = rFolder.listFiles();
                for(File exFile : files ){
                    if(exFile.isFile()){
                        filePath.add(exFile.toString());
                    }
                }
            }
        }
        return filePath;
    }



    //filter files
    public List <String> fileFilter(List <String> files){
        List <String> nFiles = new ArrayList<>();
        for(String dir : files){
            String[] videoExtensions = {".mp4",".mkv",".avi",".flv",".wmv",".mov",".webm",".mpg",".mpeg",".3gp",".3g2",".m4v",".f4v",".f4p",".f4a",".f4b",".m4b",".m4a",".m4p",".m4r",".m4v",".mpg",".mp2",".mpeg",".mpe",".mpv",".mpg",".mpeg",".m2v",".m4v",".3gp",".3g2",".flv",".f4v",".f4p",".f4a",".f4b",".webm",".vob"};
            for(String extention:videoExtensions){
                if(dir.toLowerCase().endsWith(extention)){
                    nFiles.add(dir);
                }
            }
        }
        return nFiles;
    }



    //count folder
    public static int countFolder(String dirPath) {
        File dir = new File(dirPath);
        int folderCount = 0;

        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        folderCount++;
                    }

                }
            }
        } else {
            System.out.println("Directory does not exists .... " + dirPath);
        }

        return folderCount;
    }



    //change file name
    public String changeFileName(String filePath){
        String fileName = null;
        File file = new File(filePath);
        fileName = file.getName();
        System.out.println(fileName);
        String newFileName = fileName.replaceAll("\\[YTS\\.MX\\]","[DSK.FILMS]");
        File newFile =new File(file.getParent(),newFileName);
        if(file.renameTo(newFile)){
            System.out.println("File Rename Successfully....");
        }else{
            System.out.println("Failed to rename file....'");
        }
        return newFile.getAbsolutePath().toString();
    }



    //get file caption
    public String caption(String filePath){
        String caption = null;
        File file = new File(filePath);
        caption = file.getName();
        return caption;
    }
    public String capLogo(){
        return "\n \n \n \uD83C\uDF1F \uD835\uDCDC\uD835\uDCF8\uD835\uDCFF\uD835\uDCF2\uD835\uDCEE \uD835\uDCDC\uD835\uDCEA\uD835\uDCF0\uD835\uDCF2\uD835\uDCEC\uD835\uDCEA\uD835\uDCF5 \uD835\uDCDC\uD835\uDCF8\uD835\uDCF7\uD835\uDCFD\uD835\uDCEA\uD835\uDCF0\uD835\uDCEE \uD835\uDCD0\uD835\uDCFB\uD835\uDCFD \uD83C\uDF1F " +
                "\n╭━━━━━•❅•°•⊹✧⊹•°•❅•━━━━━╮\n          \uD835\uDCD3\uD835\uDCE2\uD835\uDCDA \uD835\uDCDF\uD835\uDCFB\uD835\uDCF8\uD835\uDCFE\uD835\uDCED\uD835\uDCF5\uD835\uDD02 \uD835\uDCDF\uD835\uDCFB\uD835\uDCEE\uD835\uDCFC\uD835\uDCEE\uD835\uDCF7\uD835\uDCFD " +
                "  \n┗━━━━━•❅•°•⊹✧⊹•°•❅•━━━━━┛";
    }

}
