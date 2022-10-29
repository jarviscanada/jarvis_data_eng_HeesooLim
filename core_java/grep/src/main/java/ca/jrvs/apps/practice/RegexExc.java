package ca.jrvs.apps.practice;

public interface RegexExc {

    /**
     * check if file extension is jpg/jpeg (case-sensitive)
     * @param filename name of the target file
     * @return true if file extension is jpg/jpeg
     */
    public boolean matchJpeg(String filename);


    /**
     * check if the IP address is valid
     * IP address should be between 0.0.0.0 and 999.999.999.999
     * @param ip IP address value
     * @return true if the IP address is valid
     */
    public boolean matchIp(String ip);


    /**
     * check if the line is empty (e.g. empty, white space, tabs)
     * @param line line that needs to be evaluated
     * @return true if the line is empty
     */
    public boolean isEmptyLine(String line);
}
