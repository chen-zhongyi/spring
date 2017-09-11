package DI;

import org.springframework.stereotype.Component;

/**
 * Created by 陈忠意 on 2017/7/28.
 */
@Component
public class SgtPeppers implements CompactDisc{

    private String title = "Sgt. Pepper's Lonely Hearts Club Band";
    private String artist = "The Beatles";

    public void play(){
        System.out.println("Playing " + title + " by " + artist);
    }
}
