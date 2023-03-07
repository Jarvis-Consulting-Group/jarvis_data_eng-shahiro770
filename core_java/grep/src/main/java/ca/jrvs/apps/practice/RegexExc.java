package ca.jrvs.apps.practice;

public interface RegexExc {

    /**
     * Returns true if the filename is jpg or jpeg
     *
     * @param filename
     * @return
     */
    public boolean matchJpeg(String filename);

    /**
     * Returns true if ip is valid
     *
     * @param ip
     * @return
     */
    public boolean matchIp(String ip);

    /**
     * Returns true if line is empty
     *
     * @param line
     * @return
     */
    public boolean isEmptyLine(String line);
}
