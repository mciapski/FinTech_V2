package Download;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Scanner;

public class OperationsImpl implements Operations {

  @Override
  public String getTicker() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Please, choose ticker of your company");
    String choiceTicker = scanner.nextLine();
    return choiceTicker;
  }

  @Override
  public void openConnection(String choiceTicker) {
    try {
      Connection connection = Jsoup.connect("https://stooq.pl/q/d/?s=" + choiceTicker);
      Document document = connection.get();
      getLinkDownloadAndSave(document);
    } catch (IOException | IllegalArgumentException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void getLinkDownloadAndSave(Document document) throws IOException {
    Elements links = document.select("a[href^=\"q/d/l/\"]");
    if (links.size() > 0) {
      if (links.size() > 1) {
        System.out.println("More than one link founded. Downloading first match");
      }
      Element link = links.first();
      assert link != null;
      String linkURL = link.attr("abs:href");
      System.out.println(linkURL);
      save(link, download(linkURL));
    } else {
      clean();
      throw new IllegalArgumentException("Wrong links value");
    }
  }

  @Override
  public byte[] download(String getLink) throws IOException {
    return Jsoup.connect(getLink).ignoreContentType(true).timeout(600000).execute().bodyAsBytes();
  }

  @Override
  public void save(Element element, byte[] bytes) throws IOException {
    String savedFileName = element.text();
    if (!savedFileName.endsWith(".csv")) {
      savedFileName.concat(".").concat("csv");
    }
    FileOutputStream fos = new FileOutputStream(savedFileName);
    fos.write(bytes);
    fos.close();
    System.out.println("File has been downloaded");
  }

  @Override
  public void clean() throws IOException {

    File file = new File("Pobierz dane w pliku csv");
    BufferedWriter reader = new BufferedWriter(new FileWriter(file));
    reader.write(" ");
    reader.close();
    System.out.println("File has been cleaned");
  }

  @Override
  public void testReading() throws IOException {
    File file = new File("Pobierz dane w pliku csv");
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String text = null;
    while ((text = reader.readLine()) != null) {
      System.out.println(text);
    }
  }
}
