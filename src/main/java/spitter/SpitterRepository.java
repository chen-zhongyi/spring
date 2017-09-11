package spitter;

/**
 * Created by 陈忠意 on 2017/7/25.
 */
public interface SpitterRepository{

    void addSpitter(Spitter spitter);

    void updateSpitter(Spitter spitter);

    void deleteSpitter(Long id);

    Spitter findOne(Long id);
}
