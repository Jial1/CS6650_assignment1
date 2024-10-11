import java.util.concurrent.ThreadLocalRandom;

public class LiftRideEvent {
  private int skierId;
  private int resortId;
  private int liftId;
  private int seasonId = 2024;
  private int dayId = 1;
  private int time;

  public LiftRideEvent() {
    this.skierId = ThreadLocalRandom.current().nextInt(1, 100001);
    this.resortId = ThreadLocalRandom.current().nextInt(1, 11);
    this.liftId = ThreadLocalRandom.current().nextInt(1, 41);
    this.time = ThreadLocalRandom.current().nextInt(1, 361);
  }

  public int getSkierId() {
    return skierId;
  }

  public int getResortId() {
    return resortId;
  }

  public int getLiftId() {
    return liftId;
  }

  public int getTime() {
    return time;
  }

  public String toJson() {
    return "{"
        + "\"skierId\":" + skierId + ","
        + "\"resortId\":" + resortId + ","
        + "\"liftId\":" + liftId + ","
        + "\"seasonId\":" + seasonId + ","
        + "\"dayId\":" + dayId + ","
        + "\"time\":" + time
        + "}";
  }
}

