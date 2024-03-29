package pw.tdekk.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

class Internet {

    private static final int BUFFER_SIZE = 8192;

    private static final String DEFAULT_USER_AGENT;

    static {
        DEFAULT_USER_AGENT = getDefaultHttpUserAgent();
    }

    private static String getDefaultHttpUserAgent() {
        return "Mozilla/5.0 (" + OperatingSystem.get().getUserAgentPart() + ")" +
                " AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17";
    }

    public static void setProxy(String ip, String port) {
        if (ip == null || port == null) {
            System.clearProperty("http.proxyHost");
            System.clearProperty("http.proxyPort");
            System.clearProperty("https.proxyHost");
            System.clearProperty("https.proxyPort");
        } else {
            System.setProperty("http.proxyHost", ip);
            System.setProperty("http.proxyPort", port);
            System.setProperty("https.proxyHost", ip);
            System.setProperty("https.proxyPort", port);
        }
    }

    private static URLConnection mask(URLConnection url) {
        url.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);
        return url;
    }

    public static List<String> read(String site) {
        try {
            URL url = new URL(site);
            URLConnection connection = url.openConnection();
            if (true) {
                connection = mask(connection);
            }
            try (InputStream stream = connection.getInputStream()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                List<String> source = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    source.add(line);
                }
                return source;
            } catch (IOException e) {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    private static byte[] downloadBinary(InputStream in) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buf = new byte[BUFFER_SIZE];
            int n;
            while ((n = in.read(buf, 0, BUFFER_SIZE)) > 0) {
                out.write(buf, 0, n);
            }
            return out.toByteArray();
        }
    }

    public static File download(String site, String target) {
        try {
            URL url = new URL(site);
            URLConnection connection = url.openConnection();
            if (true) {
                connection = mask(connection);
            }
            try (InputStream stream = connection.getInputStream()) {
                File file = new File(target);
                try (FileOutputStream out = new FileOutputStream(file)) {
                    out.write(downloadBinary(stream));
                    return file;
                }
            }
        } catch (IOException e) {
            return null;
        }
    }
}