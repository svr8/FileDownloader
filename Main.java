import java.io.*;

class Main {
    public static void main(String args[]) throws IOException {

        String filePath;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String url = "https://www.sample-videos.com/text/Sample-text-file-1000kb.txt";
        // System.out.println("Enter url:");
        // String url = br.readLine();

        int Last_Index = url.lastIndexOf("/") + 1;

        String fileName = url.substring(Last_Index);
        String CurrentDir = System.getProperty("user.dir");

        String operSys = System.getProperty("os.name").toLowerCase();

        if (operSys.startsWith("windows")) {
            filePath = CurrentDir + "\\" + fileName;
        } else {
            filePath = CurrentDir + "/" + fileName;
        }

        // System.out.println("Enter file path:");
        // String filePath = br.readLine();

        FileDownloader fd = new FileDownloader(url, filePath);
        fd.startDownload();
    }
}