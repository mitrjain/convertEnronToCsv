package com.mitrjain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

public class App 
{
    public static int noOfEmails = 0;
    public static File outputCsv;
    public static BufferedWriter emailBrWriter;;
    public static long emailLimiterPerUser;
    public static long overAllLimitier;
    public static void main( String[] args ) throws IOException, InterruptedException, ClassNotFoundException
    {
        File dir = new File(args[0]); //Starting point - Path to the maildir directory of the extracted enron dataset - {path_to_enron_folder}/maildir.
        App.outputCsv = new File(args[1]); //Path of the csv file where output data will be stored.
        App.overAllLimitier = Long.parseLong(args[2]); // Limiter on how many emails to parse overall. -1 indicates no limit.
        App.emailLimiterPerUser = Long.parseLong(args[3]); // Limiter on how many emails to parse per user. -1 indicates no limit.

        App.emailBrWriter = new BufferedWriter(new FileWriter(outputCsv));
        emailBrWriter.write("\"id\",\"message\"\n");

        //Start of recursive call
        App.traverseDirectory(dir,0);

        emailBrWriter.close();

        System.out.println("No of emails written to  data set = "+App.noOfEmails);            
    }

    public static void traverseDirectory(File dir, int level) throws IOException {
        if(App.overAllLimitier != -1 && App.noOfEmails >= App.overAllLimitier){
            return;
        }

        if(dir==null){
            return;
        }

        if(!dir.isDirectory() && !dir.isFile()){
            return;
        }

        if(level == 3){
            if(dir.isDirectory()){
                return;
            }
            BufferedReader br = new BufferedReader( new FileReader(dir.getAbsolutePath()));
        
            String line = br.readLine();

            StringBuffer email = new StringBuffer();
            while(line != null){
                line=line.trim();
                // Replacing each doublequote (") with a apir of doublequotes ("")
                line = line.replaceAll("\"", "\"\"");
                email.append(line+"\n");
                line=br.readLine();
            }
            //Writing parsed email to the csv file via FileWriter object
            emailBrWriter.write("\""+App.noOfEmails+"\","+"\""+email.toString()+"\"");
            emailBrWriter.write("\n");
            emailBrWriter.flush();

            App.noOfEmails++;
            br.close();
            return;
        }
        else if(level==2){
            long emailsPerUser = 0;
            if(!dir.isDirectory()){
                return;
            }
            for (File file : dir.listFiles()) {
                if(App.emailLimiterPerUser != -1 && emailsPerUser>App.emailLimiterPerUser){
                    break;
                }
                traverseDirectory(file, level+1);
                emailsPerUser++;
            }

        }
        else if(level == 0 || level == 1){
            if(!dir.isDirectory()){
                return;
            }
            for (File file : dir.listFiles()) {
                if(level==0){
                    System.out.println("Going through emails of user: "+file.getName());
                }
                else if(level == 1){
                    System.out.println("- Folder: "+file.getName());
                }
                traverseDirectory(file, level+1);
            }

        }
    }
}

