import java.io.*;
import java.net.*;

class FileDownloader {

    final private String fileLocation;
    final private URL url;
    
    FileDownloader(String strURL, String fileLocation) {
        url = toURL(strURL);        
        this.fileLocation = fileLocation;
    }

    private URL toURL(String str) {
        try {
            URL lURL = new URL(str);
            return lURL;
        } catch(MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
 
    public void startDownload() {
        System.out.println("Starting Download...");
        while(!download());
        System.out.println("File downloaded successfully.");
    }

    private boolean download() {
        BufferedInputStream in = null;
        FileOutputStream fos = null;
        BufferedOutputStream bout = null;
        URLConnection connection = null;

        long downloaded = 0;

        try {
            connection = url.openConnection();
            
            File file = new File(fileLocation);
            if(file.exists()) {
                downloaded = file.length();
            }
            
            connection.setRequestProperty("Range", "bytes="+downloaded+"-");
            connection.connect();

            try {
                in = new BufferedInputStream( connection.getInputStream() );
            } catch(IOException e) {
                int responseCode = 0;
                try {
                    responseCode = ((HttpURLConnection)connection).getResponseCode();
                } catch(IOException e1) {
                    e1.printStackTrace();
                }

                if(responseCode == 416) {
                    return true;
                } else {
                    e.printStackTrace();
                    return false;
                }
            }

            fos = downloaded == 0 ?
                    new FileOutputStream(fileLocation) :
                    new FileOutputStream(fileLocation, true);
            
            bout = new BufferedOutputStream(fos, 1024);

            byte[] data = new byte[1024];
            int x = 0;

            System.out.println("Download Progess: " + downloaded + "B.");
            while( (x=in.read(data, 0, 1024))!=-1 ) {
                bout.write(data, 0, x);
                System.out.println("Download Progess: " + downloaded + "B.");
                downloaded++;
            }
            System.out.println("Download Progess: " + downloaded + "B.");

            in.close();
            bout.flush();
            bout.close();
            return false;

        } catch(IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if(in != null) {
                try { in.close(); }
                catch(IOException e) {}
            }

            if(fos != null) {
                try { fos.close(); }
                catch(IOException e) {}
            }

            if(bout != null) {
                try { bout.close(); }
                catch(IOException e) {}
            }

            if( connection!=null )
                ((HttpURLConnection)connection).disconnect();
        }
    } 


}