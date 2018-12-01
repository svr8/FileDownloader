import java.io.*;

class Main {
    public static void main(String args[])throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String url = "https://www.sample-videos.com/text/Sample-text-file-1000kb.txt";
        // System.out.println("Enter url:");
        // String url = br.readLine();

        String filePath = "file.txt";
        // System.out.println("Enter file path:");
        // String filePath = br.readLine();

        FileDownloader fd = new FileDownloader(url, filePath);
        fd.startDownload();
    }
}