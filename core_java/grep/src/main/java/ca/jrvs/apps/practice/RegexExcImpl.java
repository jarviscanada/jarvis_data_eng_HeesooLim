package ca.jrvs.apps.practice;

public class RegexExcImpl implements RegexExc {
    @Override
    public boolean matchJpeg(String filename) {
//        /.+.(jpe?g)/g
        return filename.matches("/.+.(jpe?g)/g");
    }

    @Override
    public boolean matchIp(String ip) {
//        /(([0-9][0-9]{0,2})\.([0-9][0-9]{0,2})\.([0-9][0-9]{0,2})\.([0-9][0-9]{0,2}))/g
        return ip.matches("/(([0-9][0-9]{0,2})\\.([0-9][0-9]{0,2})\\.([0-9][0-9]{0,2})\\.([0-9][0-9]{0,2}))/g");
    }

    @Override
    public boolean isEmptyLine(String line) {
//        ^\s*$
        return line.matches("^\\s*$");
    }
}
