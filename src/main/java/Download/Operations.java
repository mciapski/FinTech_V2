package Download;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Operations {
  String getTicker();

  void openConnection(String choiceTicker);

  void getLinkDownloadAndSave(Document document) throws IOException;

  byte[] download(String getLink) throws IOException;

  void save(Element element, byte[] bytes) throws IOException;
  void clean() throws IOException;

  void testReading() throws IOException;
}
