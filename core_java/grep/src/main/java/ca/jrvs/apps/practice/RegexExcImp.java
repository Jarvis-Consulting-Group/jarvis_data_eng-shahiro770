package ca.jrvs.apps.practice;

import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexExcImp implements RegexExc {

    private static Logger logger = LoggerFactory.getLogger(RegexExcImp.class);
    public static void main(String args[]) {
        RegexExcImp rei = new RegexExcImp();
        logger.debug("Result of matchJpeg {}", rei.matchJpeg("abc.jpeg"));
    }
    /**
     * Returns true if the filename is jpg or jpeg
     *
     * @param filename
     * @return
     */
    @Override
    public boolean matchJpeg(String filename) {
        return Pattern.compile("(.+)\\.(jpg|jpeg)")
            .matcher(filename).matches();
    }

    /**
     * Returns true if ip is valid
     *
     * @param ip
     * @return
     */
    @Override
    public boolean matchIp(String ip) {
        return Pattern.compile("\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}")
            .matcher(ip).matches();
    }

    /**
     * Returns true if line is empty
     *
     * @param line
     * @return
     */
    @Override
    public boolean isEmptyLine(String line) {
        return Pattern.compile("^\\s*$")
            .matcher(line).matches();
    }
}
