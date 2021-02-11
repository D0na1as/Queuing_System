package system.queuing.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
public class Utils {

    @Value("${work.starts}")
    String start;
    @Value("${work.ends}")
    String end;
    @Value("${visit.duration}")
    String duration;


    public String generator() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    public String getDate () {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return form.format(date);
    }

    public String getTime() {
        SimpleDateFormat form = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return form.format(date);
    }

    //Visitors can be excepted per day
    public int maxSlots() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date begin =  format.parse(start);
        Date finish =  format.parse(end);
        Date now = format.parse(getTime());
        if (now.getTime()>=begin.getTime() && now.getTime()<finish.getTime()) {
            long diff = finish.getTime() - now.getTime();
            long diffMinutes = diff / (60 * 1000);
            return (int) (diffMinutes / Integer.parseInt(duration));
        } else {
            long diff = finish.getTime() - begin.getTime();
            long diffMinutes = diff / (60 * 1000);
            return (int) (diffMinutes / Integer.parseInt(duration));
        }
    }

    //Time left info
    public String timeLeft(int queLeft) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date begin = format.parse(start);
        Date finish = format.parse(end);
        Date now = format.parse(getTime());
        long waitingTime;
        int h;
        int min;

        if (now.getTime() < begin.getTime()) {
            waitingTime = begin.getTime() - now.getTime();

            if (queLeft > 1) {
                waitingTime += Integer.parseInt(duration) * (queLeft - 1) * (60 * 1000);
            }
            waitingTime = waitingTime / (60 * 1000);
            h = (int) (waitingTime / 60);
            min = (int) (waitingTime - h * 60);

            if (h==0) return String.format("%02dmin",min);
            else return String.format("%02dh %02dmin", h, min);

        } else if (now.getTime() < finish.getTime()) {

            if (queLeft == 1) {
                return "Wait for Invitation!";

            } else if (queLeft == 2){
                return "Next is Your Turn!";

            } else {
                waitingTime = Integer.parseInt(duration) * (queLeft - 1) * (60 * 1000);
                waitingTime = waitingTime / (60 * 1000);
                h = (int) (waitingTime / 60);
                min = (int) (waitingTime - h * 60);

                if (h==0) return String.format("%02dmin",min);
                else return String.format("%02dh %02dmin", h, min);
            }

        } else return "Won't make it today!";
    }
}
