import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;

/**
 * This Class returns the message of the day using the CS230 API.
 * @author Richard James
 */
public class Motd {

    /**
     * This class shifts the revelent characters forward or backwards a space to meet requirements.
     * @param msg The message which is being character shifted.
     * @return The shifted character message.
     */
    private static String crackCode(String msg) {
        String motd = "";
        for (int i = 0; i < msg.length(); i++) {
            if (i % 2 == 0) {
                //character shift forward
                if ((int) msg.charAt(i) == 90) {
                    motd += 'A';
                } else {
                    motd += ((char)((int) msg.charAt(i) + 1));
                }
            } else {
                //character shift back
                if ((int) msg.charAt(i) == 65) {
                    motd += 'Z';
                } else {
                    motd += ((char)((int) msg.charAt(i) - 1));
                }
            }
        }
        return motd;
    }

    /**
     * This method connections to the URL passed through the parameter and fetches the content in the web body.
     * @param url URL being connected to.
     * @return Returns the content in the page.
     * @throws Exception Can throw IOException and MalformedURLException
     */
    private static String getText(URL url) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = reader.readLine();
        conn.disconnect();
        reader.close();
        return line;
    }

    /**
     * This class is the only public class callable. It returns the Message of the Day.
     * @return The Message of the Day.
     * @throws Exception Can throw MalformedURLException
     */
    public static String getMotd() throws Exception {
        String msg = getText(new URL("http://cswebcat.swan.ac.uk/puzzle"));
        String msgDecode = crackCode(msg);
        String motd = getText(new URL("http://cswebcat.swan.ac.uk/message?solution=" + msgDecode));
        return motd;
    }
}