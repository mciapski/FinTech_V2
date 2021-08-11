package Reader;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeFilter;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class HttpReader {
  public static void main(String[] args) {

    try {
      Connection connection = Jsoup.connect("https://www.bankier.pl/gielda/notowania/ranking-popularnosci");
      Document document = connection.get();
      //System.out.println(document.outerHtml());

      Elements walor = document.getElementsByClass("colWalor");
      Elements ticker = document.getElementsByClass("colTicker");
      Elements position = document.getElementsByClass("colPosition");

      StringBuilder sb = new StringBuilder();
      for (int i = 0; i< walor.size();i++) {
        sb.append(position.get(i).text()).append("  ")
            .append(walor.get(i).text()).append("  ").append(ticker.get(i).text()).append("\n");
      }
      System.out.println(sb);

    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}